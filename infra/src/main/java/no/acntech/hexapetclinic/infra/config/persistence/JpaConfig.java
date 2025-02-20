package no.acntech.hexapetclinic.infra.config.persistence;

import no.acntech.hexapetclinic.infra.config.BaseInfraConfig;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration class for setting up JPA-specific configurations.
 *
 * This class is marked as a Spring {@code @Configuration} and is responsible
 * for enabling JPA functionality within the application.
 *
 * Features included in this configuration:
 *
 * - {@code @EntityScan}: Specifies the base packages to scan for JPA entities.
 * - {@code @EnableJpaRepositories}: Configures the base packages to locate Spring Data JPA repositories.
 * - {@code @EnableJpaAuditing}: Enables JPA Auditing, allowing automatic handling of auditing fields (e.g., createdAt, updatedAt).
 *
 * This class extends {@code BaseInfraConfig}, inheriting generic configuration mechanisms
 * such as logging capabilities.
 */
@Configuration
@EntityScan(basePackages = {"no.acntech.hexapetclinic.infra.persistence.jpa.entity"})
@EnableJpaRepositories(basePackages = {"no.acntech.hexapetclinic.infra.persistence.jpa.repository"})
@EnableJpaAuditing
public class JpaConfig extends BaseInfraConfig {

}