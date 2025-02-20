package no.acntech.hexapetclinic.infra.persistence.mongo.repository;

import java.util.List;
import no.acntech.hexapetclinic.infra.persistence.mongo.entity.OwnerMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerMongoRepository extends MongoRepository<OwnerMongoEntity, Long> {

    List<OwnerMongoEntity> findByLastName(String lastName);

}
