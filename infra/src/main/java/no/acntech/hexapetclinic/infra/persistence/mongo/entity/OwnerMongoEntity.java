package no.acntech.hexapetclinic.infra.persistence.mongo.entity;

import java.beans.ConstructorProperties;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import no.acntech.hexapetclinic.domain.model.EmailAddress;
import no.acntech.hexapetclinic.domain.model.Gender;
import no.acntech.hexapetclinic.domain.model.Owner;
import no.acntech.hexapetclinic.domain.model.Pet;
import no.acntech.hexapetclinic.domain.model.PetIdentifier;
import no.acntech.hexapetclinic.domain.model.PetType;
import no.acntech.hexapetclinic.domain.model.TelephoneNumber;
import no.acntech.hexapetclinic.infra.persistence.mongo.entity.framework.MutableMongoEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * MongoDB entity representing an owner.
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document(collection = "owners")
public class OwnerMongoEntity extends MutableMongoEntity implements Owner {

  private String firstName;
  private String lastName;
  private String address;
  private String city;

  @Field("telephone")
  private String telephone;

  @Field("email")
  private String email;

  @Builder.Default
  @DBRef(lazy = true) // Store references to PetMongoEntity in the pets collection
  private Set<PetMongoEntity> pets = new HashSet<>();

  @ConstructorProperties({
      "id",
      "createdAt",
      "updatedAt",
      "firstName",
      "lastName",
      "address",
      "city",
      "telephone",
      "email",
      "pets"
  })
  public OwnerMongoEntity(
      Long id,
      Instant createdAt,
      Instant updatedAt,
      String firstName,
      String lastName,
      String address,
      String city,
      String telephone,
      String email,
      Set<PetMongoEntity> pets) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.city = city;
    this.telephone = telephone;
    this.email = email;
    this.pets = pets == null ? new HashSet<>() : pets;
  }

  @Override
  public Pet registerPet(
      @NonNull String name,
      @NonNull PetType type,
      @NonNull String breed,
      @NonNull Gender gender,
      @NonNull LocalDate birthDate,
      String description) {
    // Create a new PetMongoEntity for the pet
    var identifier = PetIdentifier.generate(
        birthDate.getYear(),
        birthDate.getMonthValue(),
        birthDate.getDayOfMonth(),
        gender
    );

    var pet = PetMongoEntity.builder()
        .identifier(identifier.getPrimitive())
        .owner(this) // Set the owner reference here
        .name(name)
        .type(type)
        .breed(breed)
        .gender(gender)
        .birthDate(birthDate)
        .description(description)
        .build();

    pets.add(pet);
    return pet;
  }

  public @NonNull TelephoneNumber getTelephone() {
    return TelephoneNumber.of(telephone);
  }

  public @NonNull EmailAddress getEmail() {
    return EmailAddress.of(email);
  }

  @Override
  public @NonNull Set<Pet> getPets() {
    return Collections.unmodifiableSet(pets);
  }

  @Override
  public boolean removePet(@NonNull Pet pet) {
    return pets.remove(pet);
  }

  @Override
  public Owner changeFirstName(@NonNull String firstName) {
    this.firstName = firstName;
    return this;
  }

  @Override
  public Owner changeLastName(@NonNull String lastName) {
    this.lastName = lastName;
    return this;
  }

  @Override
  public Owner changeAddress(@NonNull String address) {
    this.address = address;
    return this;
  }

  @Override
  public Owner changeCity(@NonNull String city) {
    this.city = city;
    return this;
  }

  @Override
  public Owner changeTelephone(@NonNull TelephoneNumber telephone) {
    this.telephone = telephone.getPrimitive();
    return this;
  }

  @Override
  public Owner changeEmail(@NonNull EmailAddress email) {
    this.email = email.getPrimitive();
    return this;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .appendSuper(super.toString())
        .append("firstName", firstName)
        .append("lastName", lastName)
        .append("address", address)
        .append("city", city)
        .append("telephone", telephone)
        .append("email", email)
//        .append("pets", pets)
        .toString();
  }

}