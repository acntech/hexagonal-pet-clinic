package no.acntech.hexapetclinic.infra.persistence.jpa.entity.factory;

import static org.springframework.util.Assert.isTrue;

import java.time.Instant;
import lombok.NonNull;
import no.acntech.hexapetclinic.domain.factory.VisitFactory;
import no.acntech.hexapetclinic.domain.model.Pet;
import no.acntech.hexapetclinic.domain.model.Visit;
import no.acntech.hexapetclinic.infra.persistence.jpa.entity.PetJpaEntity;
import no.acntech.hexapetclinic.infra.persistence.jpa.entity.VisitJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Factory class for creating instances of the {@link Visit} domain model.
 *
 * This class implements the {@link VisitFactory} interface and provides the logic for constructing
 * a {@link Visit} object. It maps the given attributes such as the associated {@link Pet},
 * the time of the visit, and a descriptive text to a {@link VisitJpaEntity} instance.
 *
 * The {@link VisitJpaEntity} class serves as the JPA representation of the {@link Visit} domain model,
 * enabling the persistence of visits. This factory ensures that the provided {@link Pet} is
 * properly adapted to the required JPA entity type using casting.
 *
 * Responsibilities:
 * - Map input parameters into a valid {@link VisitJpaEntity} representation.
 * - Validate that the associated {@link Pet} is of type {@link PetJpaEntity}.
 * - Ensure non-null attributes are provided for the visit.
 *
 * Constraints:
 * - The {@link Pet} parameter must be of type {@link PetJpaEntity}.
 * - All required fields such as pet, time, and description must be non-null.
 *
 * This class is annotated with {@code @Component}, allowing it to be managed by the Spring Framework
 * for dependency injection when needed.
 */
@Component
public class VisitEntityFactory implements VisitFactory {

  @Override
  public Visit createVisit(
      @NonNull Pet pet,
      @NonNull Instant time,
      @NonNull String description
  ) {
    isTrue(pet instanceof PetJpaEntity, "Pet must be of type PetJpaEntity");
    return VisitJpaEntity.builder()
        .pet((PetJpaEntity) pet)
        .time(time)
        .description(description)
        .build();
  }
}