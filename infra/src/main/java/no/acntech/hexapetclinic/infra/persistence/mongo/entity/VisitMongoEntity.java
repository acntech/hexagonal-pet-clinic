package no.acntech.hexapetclinic.infra.persistence.mongo.entity;

import java.beans.ConstructorProperties;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import no.acntech.hexapetclinic.domain.model.Visit;
import no.acntech.hexapetclinic.infra.persistence.mongo.entity.framework.MutableMongoEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * MongoDB entity representing a visit.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "visits")
public class VisitMongoEntity extends MutableMongoEntity implements Visit {

  private Instant time;
  private String description;

  @DBRef(lazy = true)
  private PetMongoEntity pet;

  /**
   * Constructor for deserialization. Maps MongoDB fields to the appropriate constructor parameters.
   */
  @ConstructorProperties({
      "id",
      "createdAt",
      "updatedAt",
      "time",
      "description",
      "pet"
  })
  public VisitMongoEntity(
      Long id,
      Instant createdAt,
      Instant updatedAt,
      Instant time,
      String description,
      PetMongoEntity pet) {
    super(id, createdAt, updatedAt); // Initialize base entity properties
    this.time = time;
    this.description = description;
    this.pet = pet;
  }

  @Override
  public @NonNull Visit changeDescription(@NonNull String description) {
    return new VisitMongoEntity(
        getId(),
        getCreatedAt(),
        getUpdatedAt(),
        this.time,
        description,
        this.pet
    );
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .appendSuper(super.toString())
        .append("pet.id", pet != null ? pet.getId() : null)
        .append("time", time)
        .append("description", description)
        .toString();
  }
}