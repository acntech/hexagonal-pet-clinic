package no.acntech.hexapetclinic.app.config;

import no.acntech.hexapetclinic.domain.connector.PetDescriptionEnhancer;
import no.acntech.hexapetclinic.domain.factory.OwnerFactory;
import no.acntech.hexapetclinic.domain.factory.VisitFactory;
import no.acntech.hexapetclinic.domain.repository.OwnerRepository;
import no.acntech.hexapetclinic.domain.repository.PetRepository;
import no.acntech.hexapetclinic.domain.repository.VisitRepository;
import no.acntech.hexapetclinic.domain.service.RegistrationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegistrationServiceConfig extends BaseAppConfig {

  @Bean
  public RegistrationService registrationService(
      OwnerFactory ownerFactory,
      VisitFactory visitFactory,
      PetRepository petRepository,
      OwnerRepository ownerRepository,
      VisitRepository visitRepository,
      PetDescriptionEnhancer petDescriptionEnhancer
  ) {
    return new RegistrationService(ownerFactory, visitFactory, petRepository, ownerRepository, visitRepository, petDescriptionEnhancer);
  }

}
