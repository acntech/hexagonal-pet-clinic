package no.acntech.hexapetclinic.infra.persistence.jpa.entity;

import static no.acntech.hexapetclinic.utils.text.TextConstants.SPACE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import no.acntech.hexapetclinic.domain.model.Pet;
import no.acntech.hexapetclinic.domain.model.Visit;
import no.acntech.hexapetclinic.infra.persistence.jpa.entity.framework.MutableJpaEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * JPA Entity representing a Visit in the database.
 * This entity is used to store information about a visit associated with a specific pet.
 *
 * Features:
 * - Maps to the database table "visits".
 * - Implements the {@link Visit} interface, providing domain-level functionality
 *   for managing visits.
 * - Extends {@link MutableJpaEntity} to include auditing attributes.
 * - Defines a many-to-one relationship with {@link PetJpaEntity}, representing
 *   the association between a visit and its corresponding pet.
 *
 * Fields:
 * - pet: The {@link PetJpaEntity} entity associated with the visit.
 * - time: The timestamp indicating when the visit occurred.
 * - description: A textual description of the visit.
 *
 * Functionality:
 * - Retrieves the associated pet using the {@link #getPet()} method.
 * - Allows updating the visit's description using the {@link #changeDescription(String)} method.
 * - Overrides the {@code toString()} method to provide a string representation
 *   of the entity, including details about the visit, its associated pet, and owner information.
 */
@Entity
@Table(name = "visits")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitJpaEntity extends MutableJpaEntity implements Visit {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "pet_id", nullable = false)
  private PetJpaEntity pet;

  @Column(nullable = false)
  private Instant time;

  @Column(nullable = false)
  private String description;

  @Override
  public Pet getPet() {
    return pet;
  }

  @Override
  public Visit changeDescription(@NonNull String description) {
    this.description = description;
    return this;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).appendSuper(super.toString())
        .append("owner", pet.getOwner().getFirstName() + SPACE + pet.getOwner().getLastName())
        .append("pet", pet)
        .append("time", time)
        .append("description", description)
        .toString();
  }
}
