package no.acntech.hexapetclinic.infra.persistence.mongo.entity.factory;

import static org.springframework.util.Assert.isTrue;

import java.time.Instant;
import lombok.NonNull;
import no.acntech.hexapetclinic.domain.factory.VisitFactory;
import no.acntech.hexapetclinic.domain.model.Pet;
import no.acntech.hexapetclinic.domain.model.Visit;
import no.acntech.hexapetclinic.infra.persistence.mongo.entity.PetMongoEntity;
import no.acntech.hexapetclinic.infra.persistence.mongo.entity.VisitMongoEntity;
import org.springframework.stereotype.Component;

@Component
public class VisitMongoEntityFactory implements VisitFactory {

  @Override
  public Visit createVisit(
      @NonNull Pet pet,
      @NonNull Instant time,
      @NonNull String description
  ) {
    isTrue(pet instanceof PetMongoEntity, "Pet must be of type PetMongoEntity");
    return VisitMongoEntity.builder()
        .time(time)
        .description(description)
        .pet((PetMongoEntity) pet)
        .build();
  }

}