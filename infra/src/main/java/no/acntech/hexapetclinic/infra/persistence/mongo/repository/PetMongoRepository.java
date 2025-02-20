package no.acntech.hexapetclinic.infra.persistence.mongo.repository;

import java.util.List;
import lombok.NonNull;
import no.acntech.hexapetclinic.domain.model.PetIdentifier;
import no.acntech.hexapetclinic.infra.persistence.mongo.entity.PetMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetMongoRepository extends MongoRepository<PetMongoEntity, Long> {

    PetMongoEntity findByIdentifier(@NonNull PetIdentifier identifier);

    List<PetMongoEntity> findByName(@NonNull String name);

}
