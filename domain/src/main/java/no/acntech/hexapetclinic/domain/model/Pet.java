package no.acntech.hexapetclinic.domain.model;

import java.time.LocalDate;
import lombok.NonNull;
import no.acntech.hexapetclinic.domain.model.framework.Entity;

/**
 * Represents a Pet entity which holds essential information about a pet, such as its identifier, name, type, breed, gender,
 * birth date, description, and owner details. The Pet is designed as an immutable interface where changes to its attributes
 * return a modified copy of the instance.
 */
public interface Pet extends Entity<Long> {

  PetIdentifier getIdentifier();

  String getName();

  PetType getType();

  String getBreed();

  Gender getGender();

  LocalDate getBirthDate();

  String getDescription();

  Owner getOwner();

  Pet changeName(@NonNull String name);

  Pet changeBirthDate(@NonNull LocalDate birthDate);

  Pet changeDescription(@NonNull String description);
}
