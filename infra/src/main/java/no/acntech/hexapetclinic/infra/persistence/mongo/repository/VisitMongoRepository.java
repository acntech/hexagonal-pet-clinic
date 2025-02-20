package no.acntech.hexapetclinic.infra.persistence.mongo.repository;

import java.util.List;
import no.acntech.hexapetclinic.domain.model.PetIdentifier;
import no.acntech.hexapetclinic.infra.persistence.mongo.entity.VisitMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitMongoRepository extends MongoRepository<VisitMongoEntity, Long> {

  List<VisitMongoEntity> findByPetIdentifier(PetIdentifier identifier);

}
