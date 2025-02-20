package no.acntech.hexapetclinic.infra.persistence.jpa.entity;

import static no.acntech.hexapetclinic.utils.text.TextConstants.SPACE;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import no.acntech.hexapetclinic.domain.model.Gender;
import no.acntech.hexapetclinic.domain.model.Pet;
import no.acntech.hexapetclinic.domain.model.PetIdentifier;
import no.acntech.hexapetclinic.domain.model.PetType;
import no.acntech.hexapetclinic.infra.persistence.jpa.entity.converter.GenderConverter;
import no.acntech.hexapetclinic.infra.persistence.jpa.entity.converter.PetIdConverter;
import no.acntech.hexapetclinic.infra.persistence.jpa.entity.framework.MutableJpaEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * JPA Entity representing a Pet in the database.
 * This entity stores information about a pet, including its identifier, name, type,
 * breed, gender, birth date, description, and associated owner.
 *
 * The entity supports mutability through methods that allow modification of
 * specific attributes. Any change to these attributes will update the current instance.
 *
 * Features:
 * - Uses a database table named "pets".
 * - Defines constraints and column mappings for all attributes.
 * - Relates to an owner entity through a many-to-one relationship.
 * - Implements the {@link Pet} interface which defines the domain-level contract for a pet.
 * - Extends {@link MutableJpaEntity} which includes auditing properties.
 * - Includes custom converters for certain attributes such as identifier and gender.
 */
@Entity
@Table(name = "pets")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetJpaEntity extends MutableJpaEntity implements Pet {

  @Column(nullable = false, length = PetIdentifier.FIXED_LENGTH)
  @Convert(converter = PetIdConverter.class)
  private PetIdentifier identifier;

  @Column(nullable = false)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PetType type;

  @Column
  private String breed;

  @Convert(converter = GenderConverter.class)
  @Column(nullable = false)
  private Gender gender;

  @Column(nullable = false)
  private LocalDate birthDate;

  @Column(nullable = false, length = 4096)
  private String description;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "owner_id", nullable = false)
  private OwnerJpaEntity owner;

  @Override
  public Pet changeName(@NonNull String name) {
    this.name = name;
    return this;
  }

  @Override
  public Pet changeBirthDate(@NonNull LocalDate birthDate) {
    this.birthDate = birthDate;
    return this;
  }

  @Override
  public Pet changeDescription(@NonNull String description) {
    this.description = description;
    return this;
  }

  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .appendSuper(super.toString())
        .append("owner", this.owner.getFirstName() + SPACE + this.owner.getLastName())
        .append("identifier", this.identifier)
        .append("name", this.name)
        .append("type", this.type)
        .append("gender", gender)
        .append("birthDate", this.birthDate)
        .append("description", this.description)
        .toString();
  }

}