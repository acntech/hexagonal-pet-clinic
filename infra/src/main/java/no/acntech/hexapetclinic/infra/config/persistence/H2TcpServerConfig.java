package no.acntech.hexapetclinic.infra.config.persistence;

import static org.apache.commons.lang3.Validate.isTrue;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PreDestroy;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import lombok.val;
import no.acntech.hexapetclinic.infra.config.BaseInfraConfig;
import no.acntech.hexapetclinic.infra.config.BeanNames;
import no.acntech.hexapetclinic.utils.io.FileUtils;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionTimeoutException;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * Configuration class for H2 database server with TCP connectivity.
 * <p>
 * This class configures an H2 database server that can operate in either in-memory mode or file-based mode, with TCP connectivity for
 * remote access. It provides methods to wait for the database to be ready before returning a DataSource and ensures proper cleanup of
 * database files on application shutdown.
 * </p>
 *
 * <h2>Configuration</h2>
 * <p>
 * To configure H2 in-memory or file-based mode, use the following JDBC URL formats in your application.yml:
 * </p>
 *
 * <ul>
 * <li>In-memory database:
 *
 * <pre>
 *         spring:
 *           datasource:
 *             url: jdbc:h2:tcp://localhost:9092/mem:myMemDb
 * </pre>
 *
 * </li>
 * <li>File-based database:
 *
 * <pre>
 *         spring:
 *           datasource:
 *             url: jdbc:h2:tcp://localhost:9092/./myFileDb
 * </pre>
 *
 * </li>
 * </ul>
 *
 * <h2>Base Directory</h2>
 * <p>
 * The <code>baseDir</code> property specifies the directory where H2 will create database files when operating in file-based mode. If the
 * <code>baseDir</code> is not specified in the application.yml, the server will use the system's temporary directory, which can be accessed
 * via <code>java.io.tmpdir</code>. This allows for easy configuration and ensures that database files are managed in a predictable
 * location.
 * </p>
 *
 * <h2>Cleanup on Exit</h2>
 * <p>
 * The class supports cleanup of database directories upon application shutdown. You can specify patterns for the databases to be deleted in
 * your application.yml configuration. For example:
 * </p>
 *
 * <pre>
 * spring:
 *   h2:
 *     server:
 *       cleanUpOnExitPatterns: "myDb, anotherDb"
 * </pre>
 * <p>
 * The cleanup will remove the specified database directories when the application exits.
 *
 * <h2>Preconditions</h2>
 * <p>
 * The H2 server will only be started if the property <code>spring.h2.server.enabled</code> is set to true.
 * </p>
 */
@Configuration
@ConditionalOnProperty(name = "spring.h2.server.enabled", havingValue = "true")
public class H2TcpServerConfig extends BaseInfraConfig {

  private static final int MAX_CONN_ATTEMPTS = 20;

  @Value("${spring.h2.server.baseDir:#{systemProperties['java.io.tmpdir']}}")
  private String baseDir;

  @Value("${spring.h2.server.cleanUpOnExitPatterns:}")
  private String cleanUpOnExitPatterns; // Comma-separated patterns

  @Bean(name = BeanNames.H2_SERVER, initMethod = "start", destroyMethod = "stop")
  public Server h2Server(@Value("${spring.h2.server.tcp.port:9092}") int port) throws SQLException {
    log.info("Starting H2 server on port {} with baseDir {}", port, baseDir);
    return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", String.valueOf(port), "-baseDir", baseDir, "-ifNotExists");
  }

  @Bean
  @ConfigurationProperties("spring.datasource.hikari")
  public HikariConfig hikariConfig() {
    val hikariConfig = new HikariConfig();
    log.debug("HikariConfig created: {}", hikariConfig);
    return hikariConfig;
  }

  @Bean(name = BeanNames.DATA_SOURCE)
  @DependsOn(BeanNames.H2_SERVER)
  public DataSource dataSource(Server h2Server,
      HikariConfig hikariConfig,
      @Value("${spring.datasource.url}") String jdbcUrl,
      @Value("${spring.datasource.username}") String username,
      @Value("${spring.datasource.password}") String password) {
    isTrue(jdbcUrl != null && username != null && password != null, "dataSource URL, username, and password must be specified.");

    waitForDatabase(h2Server, jdbcUrl, username, password);

    hikariConfig.setJdbcUrl(jdbcUrl);
    hikariConfig.setUsername(username);
    hikariConfig.setPassword(password);

    val ds = new HikariDataSource(hikariConfig);
    log.debug("DataSource created: {}", ds);
    return ds;
  }

  private void waitForDatabase(Server h2Server, String jdbcUrl, String username, String password) {
    log.info("Attempting to connect to database {} with username {}", jdbcUrl, username);

    // Use Awaitility to wait for the database to be ready
    try {
      Awaitility.await()
          .atMost(MAX_CONN_ATTEMPTS, TimeUnit.SECONDS) // Total wait time
          .pollInterval(1, TimeUnit.SECONDS) // Check every second
          .until(() -> {
            log.info("Attempting to connect to database... {}", jdbcUrl);
            log.info("H2 server status: {}", h2Server.getStatus());
            log.info("H2 server isRunning: {}", h2Server.isRunning(true));

            try (Connection ignored = DriverManager.getConnection(jdbcUrl, username, password)) {
              // Connection successful
              log.info("Database is ready.");
              return true; // Exit the loop if connection is successful
            } catch (SQLException e) {
              log.warn("Failed to connect to database: {}", e.getMessage());
              return false; // Keep trying
            }
          });
    } catch (ConditionTimeoutException e) {
      throw new RuntimeException("Database is not reachable after waiting", e);
    }
  }

  @SuppressWarnings("StringSplitter")
  @PreDestroy
  public void cleanUpDatabases() {
    if (cleanUpOnExitPatterns != null && !cleanUpOnExitPatterns.isEmpty()) {
      String[] patterns = cleanUpOnExitPatterns.split(",");
      for (String pattern : patterns) {
        String dbNamePattern = pattern.trim();
        File dbDir = new File(baseDir, dbNamePattern);
        if (dbDir.exists() && dbDir.isDirectory()) {
          FileUtils.deleteDir(dbDir, true);
        }
      }
    }
  }

}
