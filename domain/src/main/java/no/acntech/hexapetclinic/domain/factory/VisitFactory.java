package no.acntech.hexapetclinic.domain.factory;

import java.time.Instant;
import lombok.NonNull;
import no.acntech.hexapetclinic.domain.model.Pet;
import no.acntech.hexapetclinic.domain.model.Visit;

/**
 * Factory interface for creating instances of the {@link Visit} domain model.
 *
 * This interface defines a method for constructing a {@link Visit} with
 * the necessary attributes such as the associated {@link Pet}, the time
 * of the visit, and a descriptive text about the visit.
 *
 * Implementations of this interface can define specific construction logic
 * or mappings between different data formats (e.g., DTOs, JPA entities, or domain objects).
 */
public interface VisitFactory {

  Visit createVisit(
      @NonNull Pet pet,
      @NonNull Instant time,
      @NonNull String description
  );

}