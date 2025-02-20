package no.acntech.hexapetclinic.infra.persistence.mongo.repository.adapter;

import java.util.List;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import no.acntech.hexapetclinic.domain.model.Pet;
import no.acntech.hexapetclinic.domain.model.PetIdentifier;
import no.acntech.hexapetclinic.domain.repository.EntityNotFoundException;
import no.acntech.hexapetclinic.domain.repository.PetRepository;
import no.acntech.hexapetclinic.infra.persistence.mongo.entity.PetMongoEntity;
import no.acntech.hexapetclinic.infra.persistence.mongo.repository.PetMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * MongoDB-based implementation of the PetRepository (domain) interface, extending the AbstractMongoRepositoryAdapter
 * to leverage Spring Data MongoDB functionality.
 *
 * @see no.acntech.hexapetclinic.domain.repository.PetRepository
 * @see AbstractMongoRepositoryAdapter
 */
@Repository
@Slf4j
public class PetMongoRepositoryAdapter
    extends AbstractMongoRepositoryAdapter<Pet, PetMongoEntity, Long, PetMongoRepository>
    implements PetRepository {

  /**
   * Constructs a PetMongoRepositoryAdapter with the given Spring Data MongoDB repository.
   *
   * @param petMongoRepository the MongoDB repository to be used by this PetMongoRepositoryAdapter; must not be null
   */
  public PetMongoRepositoryAdapter(PetMongoRepository petMongoRepository) {
    super(petMongoRepository);
  }

  @Override
  @NonNull
  public Pet findByIdentifier(@NonNull PetIdentifier identifier) {
    Pet pet = mongoRepository.findByIdentifier(identifier);
    if (pet == null) {
      throw new EntityNotFoundException(String.format("Pet with identifier '%s' not found", identifier));
    }
    return pet;
  }

  @Override
  @NonNull
  public List<Pet> findByName(@NonNull String name) {
    return mongoRepository.findByName(name)
        .stream()
        .map(pet -> (Pet) pet) // Explicit casting to Pet
        .toList();
  }
}
