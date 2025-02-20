package no.acntech.hexapetclinic.infra.persistence.jpa.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
import no.acntech.hexapetclinic.infra.persistence.jpa.entity.converter.EmailConverter;
import no.acntech.hexapetclinic.infra.persistence.jpa.entity.converter.TelephoneNumberConverter;
import no.acntech.hexapetclinic.infra.persistence.jpa.entity.framework.MutableJpaEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represents a JPA entity for storing owner information in the database.
 * This class includes the owner's personal details, contact information,
 * and the list of their associated pets.
 *
 * It implements the {@link Owner} interface, allowing controlled updates
 * to the entity's state and providing methods to manage associated pets.
 * The entity is mapped to the "owners" database table.
 *
 * Each owner has the following key properties:
 * - Personal details such as first name, last name, address, and city.
 * - Contact details including a unique telephone number and email address.
 * - A collection of pets they own, managed as a one-to-many relationship.
 *
 * Uses JPA annotations for ORM capabilities and incorporates custom converters
 * for specific fields, such as telephone and email.
 */
@Entity
@Table(name = "owners")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OwnerJpaEntity extends MutableJpaEntity implements Owner {

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  private String city;

  @Column(nullable = false, unique = true, length = TelephoneNumber.MAX_LENGTH)
  @Convert(converter = TelephoneNumberConverter.class)
  private TelephoneNumber telephone;

  @Column(nullable = false, unique = true, length = EmailAddress.MAX_LENGTH)
  @Convert(converter = EmailConverter.class)
  private EmailAddress email;

  @Builder.Default
  @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<PetJpaEntity> pets = new HashSet<>();

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
    this.telephone = telephone;
    return this;
  }

  @Override
  public Owner changeEmail(@NonNull EmailAddress email) {
    this.email = email;
    return this;
  }

  @Override
  public Set<Pet> getPets() {
    return Collections.unmodifiableSet(pets);
  }

  @Override
  public Pet registerPet(
      @NonNull String name,
      @NonNull PetType type,
      @NonNull String breed,
      @NonNull Gender gender,
      @NonNull LocalDate birthDate,
      @NonNull String description
  ) {

    // Generate the PetIdentifier
    var petId = PetIdentifier.generate(
        birthDate.getYear(),
        birthDate.getMonthValue(),
        birthDate.getDayOfMonth(),
        gender
    );

    // Use Lombok builder for PetJpaEntity creation
    var pet = PetJpaEntity.builder()
        .identifier(petId)
        .owner(this)
        .name(name)
        .type(type)
        .breed(breed)
        .gender(gender)
        .birthDate(birthDate)
        .description(description)
        .build();

    // Add the new pet to the owner's collection
    pets.add(pet);

    return pet;
  }

  @Override
  public boolean removePet(@NonNull Pet pet) {
    return pets.remove(pet);
  }

  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .appendSuper(super.toString())
        .append("firstName", this.firstName)
        .append("lastName", this.lastName)
        .append("address", this.address)
        .append("city", this.city)
        .append("telephone", this.telephone)
        .append("email", this.email)
        .append("pets", this.pets.stream()
            .map(Pet::getName)
            .toList())  // Converts stream to a list
        .toString();
  }
}