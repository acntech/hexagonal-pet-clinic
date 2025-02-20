package no.acntech.hexapetclinic.infra.persistence.mongo.entity;

import java.beans.ConstructorProperties;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import no.acntech.hexapetclinic.domain.model.Gender;
import no.acntech.hexapetclinic.domain.model.Pet;
import no.acntech.hexapetclinic.domain.model.PetIdentifier;
import no.acntech.hexapetclinic.domain.model.PetType;
import no.acntech.hexapetclinic.domain.model.Visit;
import no.acntech.hexapetclinic.infra.persistence.mongo.entity.framework.MutableMongoEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * MongoDB entity representing a pet.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "pets") // Specifies MongoDB collection name as "pets"
public class PetMongoEntity extends MutableMongoEntity implements Pet {

  @Field("identifier") // Specifies MongoDB field name as "identifier"
  private String identifier;

  private String name;
  private PetType type;
  private String breed;
  private Gender gender;
  private LocalDate birthDate;
  private String description;

  @DBRef(lazy = true) // Reference to the "owners" collection
  private OwnerMongoEntity owner;

  @Builder.Default
  @DBRef(lazy = true) // References to the "visits" collection
  private Set<VisitMongoEntity> visits = Set.of();

  /** Constructor for Spring Data MongoDB mapping */
  @ConstructorProperties({
      "id",
      "createdAt",
      "updatedAt",
      "identifier",
      "name",
      "type",
      "breed",
      "gender",
      "birthDate",
      "description",
      "owner",
      "visits"
  })
  public PetMongoEntity(
      Long id,
      Instant createdAt,
      Instant updatedAt,
      String identifier,
      String name,
      PetType type,
      String breed,
      Gender gender,
      LocalDate birthDate,
      String description,
      OwnerMongoEntity owner,
      Set<VisitMongoEntity> visits) {
    super(id, createdAt, updatedAt); // Initialize superclass fields
    this.identifier = identifier;
    this.name = name;
    this.type = type;
    this.breed = breed;
    this.gender = gender;
    this.birthDate = birthDate;
    this.description = description;
    this.owner = owner;
    this.visits = visits;
  }

  public @NonNull PetIdentifier getIdentifier() {
    return PetIdentifier.of(identifier);
  }

  public @NonNull Set<Visit> getVisits() {
    return Set.copyOf(visits);
  }

  @Override
  public @NonNull Pet changeName(@NonNull String name) {
    this.name = name;
    return this;
  }

  @Override
  public @NonNull Pet changeBirthDate(@NonNull LocalDate birthDate) {
    this.birthDate = birthDate;
    return this;
  }

  @Override
  public @NonNull Pet changeDescription(@NonNull String description) {
    this.description = description;
    return this;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .appendSuper(super.toString())
        .append("identifier", identifier)
        .append("name", name)
        .append("type", type)
        .append("breed", breed)
        .append("gender", gender)
        .append("birthDate", birthDate)
        .append("description", description)
//        .append("visits", visits)
        .toString();
  }
}