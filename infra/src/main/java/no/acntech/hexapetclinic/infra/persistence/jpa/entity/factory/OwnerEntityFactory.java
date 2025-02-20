package no.acntech.hexapetclinic.infra.persistence.jpa.entity.factory;

import lombok.NonNull;
import no.acntech.hexapetclinic.domain.factory.OwnerFactory;
import no.acntech.hexapetclinic.domain.model.EmailAddress;
import no.acntech.hexapetclinic.domain.model.Owner;
import no.acntech.hexapetclinic.domain.model.TelephoneNumber;
import no.acntech.hexapetclinic.infra.persistence.jpa.entity.OwnerJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Factory class for creating instances of the {@link Owner} domain model.
 *
 * This class implements the {@link OwnerFactory} interface and provides the
 * logic for constructing an {@link Owner} object. It maps the provided attributes
 * such as first name, last name, address, city, telephone number, and email address
 * to an {@link OwnerJpaEntity} instance. The implementation uses the builder pattern
 * provided by the {@link OwnerJpaEntity}.
 *
 * The {@link OwnerJpaEntity} class serves as the JPA representation of the {@link Owner}
 * domain model, enabling the persistence of {@link Owner} instances.
 *
 * This class is annotated with {@code @Component}, making it eligible for Spring
 * dependency injection. The component annotation indicates that this factory
 * is a Spring-managed bean.
 *
 * Responsibilities:
 * - Map raw input attributes into a concrete {@link OwnerJpaEntity}.
 * - Ensure that all required fields are provided and non-null.
 *
 * Constraints:
 * - All required parameters such as first name, last name, address, city,
 *   telephone, and email must be non-null.
 *
 * Example attributes handled:
 * - First name and last name to represent the owner's identity.
 * - Address and city for the owner's residence details.
 * - {@link TelephoneNumber} and {@link EmailAddress} for contact information.
 */
@Component
public class OwnerEntityFactory implements OwnerFactory {

  @Override
  public Owner createOwner(
      @NonNull String firstName,
      @NonNull String lastName,
      @NonNull String address,
      @NonNull String city,
      @NonNull TelephoneNumber telephone,
      @NonNull EmailAddress email
  ) {
    return OwnerJpaEntity.builder()
        .firstName(firstName)
        .lastName(lastName)
        .address(address)
        .city(city)
        .telephone(telephone)
        .email(email)
        .build();
  }
}
