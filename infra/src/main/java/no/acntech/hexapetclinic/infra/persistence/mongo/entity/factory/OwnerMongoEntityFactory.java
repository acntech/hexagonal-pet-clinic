package no.acntech.hexapetclinic.infra.persistence.mongo.entity.factory;

import lombok.NonNull;
import no.acntech.hexapetclinic.domain.factory.OwnerFactory;
import no.acntech.hexapetclinic.domain.model.EmailAddress;
import no.acntech.hexapetclinic.domain.model.Owner;
import no.acntech.hexapetclinic.domain.model.TelephoneNumber;
import no.acntech.hexapetclinic.infra.persistence.mongo.entity.OwnerMongoEntity;
import org.springframework.stereotype.Component;

@Component
public class OwnerMongoEntityFactory implements OwnerFactory {

  @Override
  public Owner createOwner(
      @NonNull String firstName,
      @NonNull String lastName,
      @NonNull String address,
      @NonNull String city,
      @NonNull TelephoneNumber telephone,
      @NonNull EmailAddress email
  ) {
    return OwnerMongoEntity.builder()
        .firstName(firstName)
        .lastName(lastName)
        .address(address)
        .city(city)
        .telephone(telephone.getPrimitive())
        .email(email.getPrimitive())
        .build();
  }
}
