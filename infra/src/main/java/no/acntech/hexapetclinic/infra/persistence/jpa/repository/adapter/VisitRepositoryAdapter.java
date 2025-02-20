package no.acntech.hexapetclinic.infra.persistence.jpa.repository.adapter;

import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import no.acntech.hexapetclinic.domain.model.PetIdentifier;
import no.acntech.hexapetclinic.domain.model.Visit;
import no.acntech.hexapetclinic.domain.repository.VisitRepository;
import no.acntech.hexapetclinic.infra.persistence.jpa.entity.VisitJpaEntity;
import no.acntech.hexapetclinic.infra.persistence.jpa.repository.VisitJpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Adapter implementation for the VisitRepository interface, bridging the domain-level
 * Visit entities with the persistence mechanism provided by a Spring Data JPA repository.
 *
 * The VisitRepositoryAdapter is responsible for:
 * - Delegating CRUD operations to the underlying VisitJpaRepository.
 * - Implementing additional behavior specific to visits, such as retrieving visits by pet ID.
 *
 * This adapter extends AbstractRepositoryAdapter to leverage shared behavior and
 * implements VisitRepository to provide domain-specific functionality.
 */
@Repository
@Slf4j
public class VisitRepositoryAdapter
    extends AbstractRepositoryAdapter<Visit, VisitJpaEntity, Long, VisitJpaRepository>
    implements VisitRepository {

  /**
   * Constructs a VisitRepositoryAdapter with the given Spring Data JPA repository.
   *
   * @param visitJpaRepository the JPA repository used by this adapter
   */
  public VisitRepositoryAdapter(VisitJpaRepository visitJpaRepository) {
    super(visitJpaRepository);
  }

  @Override
  public List<Visit> findByPetId(@NonNull PetIdentifier petIdentifier) {
    return jpaRepository.findByPetId(petIdentifier)
        .stream()
        .map(entity -> (Visit) entity) // Cast each VisitEntity to Visit
        .collect(Collectors.toList());
  }
}