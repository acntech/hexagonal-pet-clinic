package no.acntech.hexapetclinic.infra.persistence.jpa.repository.adapter;

import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import no.acntech.hexapetclinic.domain.model.Owner;
import no.acntech.hexapetclinic.domain.repository.OwnerRepository;
import no.acntech.hexapetclinic.infra.persistence.jpa.entity.OwnerJpaEntity;
import no.acntech.hexapetclinic.infra.persistence.jpa.repository.OwnerJpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Adapter implementation of {@link OwnerRepository} that uses a Spring Data JPA repository
 * for data access. This class extends {@link AbstractRepositoryAdapter} to leverage common
 * repository functionality and applies it to the {@link Owner} domain model.
 *
 * The adapter translates domain model entities to JPA entities and vice versa, ensuring
 * separation of concerns between the domain model and the persistence layer. It also
 * provides additional implementations for domain-specific queries.
 *
 * This class is marked with the {@code @Repository} annotation, making it a Spring-managed
 * component for dependency injection.
 *
 * @see OwnerRepository
 * @see Owner
 * @see AbstractRepositoryAdapter
 */
@Repository
@Slf4j
public class OwnerRepositoryAdapter
    extends AbstractRepositoryAdapter<Owner, OwnerJpaEntity, Long, OwnerJpaRepository>
    implements OwnerRepository {

  /**
   * Constructs an OwnerRepositoryAdapter with the given Spring Data JPA repository.
   *
   * @param ownerJpaRepository the JPA repository to be used by this OwnerRepositoryAdapter; must not be null
   */
  public OwnerRepositoryAdapter(OwnerJpaRepository ownerJpaRepository) {
    super(ownerJpaRepository);
  }

  @Override
  public List<Owner> findByLastName(@NonNull String lastName) {
    return jpaRepository.findByLastName(lastName)
        .stream()
        .map(Owner.class::cast)
        .collect(Collectors.toList());
  }

}