package no.acntech.hexapetclinic.infra.config.persistence;

import java.io.IOException;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

/**
 * The DatabaseInitializer class is responsible for initializing the database
 * schema and data during application startup. It implements the CommandLineRunner
 * interface, ensuring its {@code run} method is executed upon application startup.
 *
 * This class attempts to load and execute SQL scripts from the classpath:
 * - schema.sql: for defining the database schema.
 * - data.sql: for populating initial data to the database.
 *
 * If any of these files are not found in the classpath, a warning is logged, and
 * the corresponding initialization step is skipped.
 *
 * The class makes use of a Spring-provided {@code ResourceDatabasePopulator}
 * to execute the provided SQL scripts against the configured {@code DataSource}.
 *
 * Proper logging is implemented to provide insights into the initialization process,
 * including error handling for resource availability checks.
 *
 * Dependencies:
 * - This class requires a {@code DataSource} bean to be available in the Spring
 *   application context in order to execute the database operations.
 *
 * Note:
 * - If no valid schema.sql and data.sql are found in the application's classpath,
 *   the database initialization process will be skipped entirely.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseInitializer implements CommandLineRunner {

  private final DataSource dataSource;

  @Override
  public void run(String... args) throws Exception {
    log.info("Initializing database...");

    // Check if schema.sql and data.sql exist
    Resource schemaResource = new ClassPathResource("schema.sql");
    Resource dataResource = new ClassPathResource("data.sql");

    if (!resourceExists(schemaResource)) {
      log.warn("⚠ schema.sql not found in classpath! Skipping schema initialization.");
    }
    if (!resourceExists(dataResource)) {
      log.warn("⚠ data.sql not found in classpath! Skipping data initialization.");
    }

    // Populate database only if resources exist
    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    if (resourceExists(schemaResource)) {
      populator.addScript(schemaResource);
    }
    if (resourceExists(dataResource)) {
      populator.addScript(dataResource);
    }

    populator.execute(dataSource);

    log.info("Database initialized.");
  }

  private boolean resourceExists(Resource resource) {
    try {
      return resource.exists() && resource.getFile().exists();
    } catch (IOException e) {
      log.error("Error checking resource existence: {}", resource.getFilename(), e);
      return false;
    }
  }
}
