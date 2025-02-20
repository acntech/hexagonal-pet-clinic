package no.acntech.hexapetclinic.infra.persistence.jpa.repository.adapter;

import java.util.List;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import no.acntech.hexapetclinic.domain.model.Pet;
import no.acntech.hexapetclinic.domain.model.PetIdentifier;
import no.acntech.hexapetclinic.domain.repository.EntityNotFoundException;
import no.acntech.hexapetclinic.domain.repository.PetRepository;
import no.acntech.hexapetclinic.infra.persistence.jpa.entity.PetJpaEntity;
import no.acntech.hexapetclinic.infra.persistence.jpa.repository.PetJpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Adapter implementation for the {@link PetRepository} interface. This class bridges the domain model (Pet)
 * and the corresponding JPA entity (PetJpaEntity), allowing seamless interaction between the business layer
 * and the persistence layer.
 *
 * Responsibilities:
 * - Delegates core CRUD operations to the underlying Spring Data JPA repository.
 * - Implements domain-specific query methods such as finding pets by identifier or name.
 * - Ensures consistency between the domain model and the JPA model during conversion and data retrieval.
 * - Leverages the base functionality provided by {@link AbstractRepositoryAdapter}.
 *
 * Delegates persistence operations to a {@link PetJpaRepository}, which interacts with the database.
 */
@Repository
@Slf4j
public class PetRepositoryAdapter
    extends AbstractRepositoryAdapter<Pet, PetJpaEntity, Long, PetJpaRepository>
    implements PetRepository {

  /**
   * Constructs a PetRepositoryAdapter with the given Spring Data JPA repository.
   *
   * @param petJpaRepository the JPA repository to be used by this PetRepositoryAdapter; must not be null
   */
  public PetRepositoryAdapter(PetJpaRepository petJpaRepository) {
    super(petJpaRepository);
  }

  @Override
  @NonNull
  public Pet findByIdentifier(@NonNull PetIdentifier petIdentifier) {
    Pet pet = jpaRepository.findByIdentifier(petIdentifier);
    if (pet == null) {
      throw new EntityNotFoundException(String.format("Pet with identifier '%s' not found", petIdentifier));
    }
    return pet;
  }

  @Override
  @NonNull
  public List<Pet> findByName(@NonNull String name) {
    return jpaRepository.findByName(name)
        .stream()
        .map(entity -> (Pet) entity) // Cast each entity to Pet
        .toList(); // Collect as a List
  }

}