package no.acntech.hexapetclinic.infra.persistence.mongo.repository.adapter;

import java.util.List;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import no.acntech.hexapetclinic.domain.model.Owner;
import no.acntech.hexapetclinic.domain.repository.OwnerRepository;
import no.acntech.hexapetclinic.infra.persistence.mongo.entity.OwnerMongoEntity;
import no.acntech.hexapetclinic.infra.persistence.mongo.repository.OwnerMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * MongoDB-based implementation of the OwnerRepository (domain) interface, extending the AbstractMongoRepositoryAdapter
 * to leverage Spring Data MongoDB functionality.
 *
 * @see no.acntech.hexapetclinic.domain.repository.OwnerRepository
 * @see AbstractMongoRepositoryAdapter
 */
@Repository
@Slf4j
public class OwnerMongoRepositoryAdapter
    extends AbstractMongoRepositoryAdapter<Owner, OwnerMongoEntity, Long, OwnerMongoRepository>
    implements OwnerRepository {

  /**
   * Constructs an OwnerMongoRepositoryAdapter with the given Spring Data MongoDB repository.
   *
   * @param ownerMongoRepository the MongoDB repository to be used by this OwnerMongoRepositoryAdapter; must not be null
   */
  public OwnerMongoRepositoryAdapter(OwnerMongoRepository ownerMongoRepository) {
    super(ownerMongoRepository);
  }

  @Override
  public List<Owner> findByLastName(@NonNull String lastName) {
    return mongoRepository.findByLastName(lastName)
        .stream()
        .map(owner -> (Owner) owner) // Explicit casting to Owner
        .toList();
  }
}
