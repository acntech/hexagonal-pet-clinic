package no.acntech.hexapetclinic.infra.config;

import no.acntech.hexapetclinic.domain.connector.PetDescriptionEnhancer;
import no.acntech.hexapetclinic.infra.adapter.PetDescriptionEnhancerAzureOpenAiAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PetDescriptionEnhancerConfig extends BaseInfraConfig {

  @Bean
  public PetDescriptionEnhancer petDescriptionEnhancer() {
    return new PetDescriptionEnhancerAzureOpenAiAdapter();
  }
}
