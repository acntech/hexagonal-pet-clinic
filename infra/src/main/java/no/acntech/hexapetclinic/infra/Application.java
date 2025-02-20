package no.acntech.hexapetclinic.infra;

import static no.acntech.hexapetclinic.utils.text.TextConstants.TAB;

import jakarta.annotation.PreDestroy;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;
import no.acntech.hexapetclinic.utils.collections.CollectionUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(
    // Spring Boot components only allowed in Application and Infrastructure
    basePackages = {"no.acntech.hexapetclinic.app", "no.acntech.hexapetclinic.infra"})
@ConfigurationPropertiesScan(
    // Spring Boot components only allowed in Application and Infrastructure - hence we can only scan for
    // ConfigurationProperties in their respective config-packages
    basePackages = {"no.acntech.hexapetclinic.app.config", "no.acntech.hexapetclinic.infra.config"}
)
@Slf4j
public class Application {

  public static final String ENV_SPRING_PROFILES_ACTIVE = "SPRING_PROFILES_ACTIVE";

  public static final String PROP_SPRING_PROFILES_ACTIVE = "spring.profiles.active";

  public static final String DEFAULT_PROFILE = "local-h2-tcp";

  public static void main(String[] args) {
    Instant now = Instant.now();
    printSystemTime(now);
    printEnvironmentVariables();
    printSystemProperties();
    runApp(args);
  }

  private static void printSystemTime(Instant now) {
    println("\n\nTime -->");
    println(TAB + "System time:             " + now);
    println(TAB + "System local time:       " + ZonedDateTime.ofInstant(now, ZoneId.systemDefault()));
    println(TAB + "System default timezone: " + TimeZone.getDefault().getDisplayName());
  }

  private static void printEnvironmentVariables() {
    println("\n\nEnv -->");
    println(CollectionUtils.prettyPrint(CollectionUtils.toCensoredCopy(System.getenv())));
  }

  private static void printSystemProperties() {
    println("\n\nSystem properties -->");
    println(CollectionUtils.prettyPrint(CollectionUtils.toCensoredCopy(System.getProperties())));
  }

  private static void runApp(String[] args) {
    SpringApplication application = new SpringApplication(Application.class);
    resolveProfile(args, application);
    addLoggingInitHooks(application);
    application.run(args);
  }

  private static void resolveProfile(String[] args, SpringApplication application) {
    // Check for profile in command-line args
    boolean hasProfileArg = Arrays.stream(args).anyMatch(arg -> arg.startsWith("--" + PROP_SPRING_PROFILES_ACTIVE + "="));

    // Check environment variable
    String envProfile = System.getenv(ENV_SPRING_PROFILES_ACTIVE);

    // Check system property
    String sysPropertyProfile = System.getProperty(PROP_SPRING_PROFILES_ACTIVE);

    // If no profile is set in any of these ways, default to local-h2
    if (!hasProfileArg && (envProfile == null || envProfile.isBlank()) && (sysPropertyProfile == null || sysPropertyProfile.isBlank())) {
      application.setAdditionalProfiles(DEFAULT_PROFILE);
    }

    application.addListeners(event -> {
      if (event instanceof org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent envEvent) {
        String[] activeProfiles = envEvent.getEnvironment().getActiveProfiles();
        println("\n\n----------------------------------------------------------");
        println("Active Spring profile(s): " + String.join(", ", activeProfiles));
        println("----------------------------------------------------------\n\n");
      }
    });
  }

  private static void addLoggingInitHooks(SpringApplication application) {
    application.addListeners(
        (ApplicationListener<ApplicationStartingEvent>) event -> println("ApplicationStartingEvent hook: " + event.getSpringApplication()));

    application.addListeners((ApplicationListener<ApplicationEnvironmentPreparedEvent>) event -> {
      log.info("ApplicationEnvironmentPreparedEvent hook: {}", event.getEnvironment());
    });

    application.addListeners((ApplicationListener<ApplicationContextInitializedEvent>) event ->
        log.info("ApplicationContextInitializedEvent hook: {}", event.getApplicationContext()));

    application.addListeners((ApplicationListener<ApplicationPreparedEvent>) event -> log.info("ApplicationPreparedEvent hook: {}",
        event.getApplicationContext()));

    application.addListeners((ApplicationListener<ApplicationStartedEvent>) event -> {
      log.info("ApplicationStartedEvent hook [{}], time taken [{}]", event.getApplicationContext(), event.getTimeTaken());
      log.info("Application started at {}", Instant.now());
    });

    application.addListeners((ApplicationListener<ApplicationReadyEvent>) event ->
        log.info("ApplicationReadyEvent hook [{}], time taken [{}]", event.getApplicationContext(), event.getTimeTaken()));
  }

  private static void println(String message) {
    System.out.println(message);
  }

  @PreDestroy
  public void destroy() {
    log.warn("@PreDestroy callback: Application terminating at {}", Instant.now());
  }

}
