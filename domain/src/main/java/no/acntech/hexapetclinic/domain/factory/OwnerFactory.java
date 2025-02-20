package no.acntech.hexapetclinic.domain.factory;

import lombok.NonNull;
import no.acntech.hexapetclinic.domain.model.EmailAddress;
import no.acntech.hexapetclinic.domain.model.Owner;
import no.acntech.hexapetclinic.domain.model.TelephoneNumber;

/**
 * Factory interface for creating instances of the {@link Owner} domain model.
 *
 * This interface defines a method for constructing an {@link Owner} with
 * the necessary attributes such as first name, last name, address, city,
 * telephone number, and email address.
 *
 * Implementations of this interface can define specific construction logic
 * or mappings between different data formats (e.g., DTOs, JPA entities, or domain objects).
 */
public interface OwnerFactory {

  Owner createOwner(
      @NonNull String firstName,
      @NonNull String lastName,
      @NonNull String address,
      @NonNull String city,
      @NonNull TelephoneNumber telephone,
      @NonNull EmailAddress email
  );


}
