package no.acntech.hexapetclinic.infra.persistence.mongo.repository.adapter;

import java.util.List;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import no.acntech.hexapetclinic.domain.model.PetIdentifier;
import no.acntech.hexapetclinic.domain.model.Visit;
import no.acntech.hexapetclinic.domain.repository.VisitRepository;
import no.acntech.hexapetclinic.infra.persistence.mongo.entity.VisitMongoEntity;
import no.acntech.hexapetclinic.infra.persistence.mongo.repository.VisitMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * MongoDB-based implementation of the VisitRepository (domain) interface, extending the AbstractMongoRepositoryAdapter
 * to leverage Spring Data MongoDB functionality.
 *
 * @see no.acntech.hexapetclinic.domain.repository.VisitRepository
 * @see AbstractMongoRepositoryAdapter
 */
@Repository
@Slf4j
public class VisitMongoRepositoryAdapter
    extends AbstractMongoRepositoryAdapter<Visit, VisitMongoEntity, Long, VisitMongoRepository>
    implements VisitRepository {

  /**
   * Constructs a VisitMongoRepositoryAdapter with the given Spring Data MongoDB repository.
   *
   * @param visitMongoRepository the MongoDB repository to be used by this adapter; must not be null
   */
  public VisitMongoRepositoryAdapter(VisitMongoRepository visitMongoRepository) {
    super(visitMongoRepository);
  }

  @Override
  public List<Visit> findByPetIdentifier(@NonNull PetIdentifier petIdentifier) {
    return mongoRepository.findByPetIdentifier(petIdentifier) // Convert PetIdentifier to Long
        .stream()
        .map(visit -> (Visit) visit) // Explicit casting to Visit
        .toList();
  }
}
