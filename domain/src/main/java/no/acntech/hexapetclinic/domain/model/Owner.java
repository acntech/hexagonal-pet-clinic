package no.acntech.hexapetclinic.domain.model;

import java.time.LocalDate;
import java.util.Set;
import lombok.NonNull;
import no.acntech.hexapetclinic.domain.model.framework.Entity;

/**
 * Represents an owner in the domain model. An owner is associated with multiple pets
 * and contains detailed personal information such as name, address, contact information,
 * and email.
 *
 * This interface provides methods to modify the owner's information,
 * manage relationships with their pets, and retrieve aggregated details.
 */
public interface Owner extends Entity<Long> {

  String getFirstName();

  String getLastName();

  String getAddress();

  String getCity();

  TelephoneNumber getTelephone();

  EmailAddress getEmail();

  Set<Pet> getPets();

  Pet registerPet(
      @NonNull String name,
      @NonNull PetType type,
      @NonNull String breed,
      @NonNull Gender gender,
      @NonNull LocalDate birthDate,
      String description
  );

  boolean removePet(@NonNull Pet pet);

  Owner changeFirstName(@NonNull String firstName);

  Owner changeLastName(@NonNull String lastName);

  Owner changeAddress(@NonNull String address);

  Owner changeCity(@NonNull String city);

  Owner changeTelephone(@NonNull TelephoneNumber telephone);

  Owner changeEmail(@NonNull EmailAddress email);
}
