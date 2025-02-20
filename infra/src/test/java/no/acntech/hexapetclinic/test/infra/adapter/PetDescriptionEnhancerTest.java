package no.acntech.hexapetclinic.test.infra.adapter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import lombok.extern.slf4j.Slf4j;
import no.acntech.hexapetclinic.domain.connector.PetDescriptionEnhancer;
import no.acntech.hexapetclinic.domain.model.PetType;
import no.acntech.hexapetclinic.infra.adapter.PetDescriptionEnhancerAzureOpenAiAdapter;
import org.junit.jupiter.api.Test;

@Slf4j
public class PetDescriptionEnhancerTest {

  @Test
  void testEnhanceDescription_withRealAzureOpenAi() {
    // Arrange

    PetDescriptionEnhancer petDescriptionEnhancer = new PetDescriptionEnhancerAzureOpenAiAdapter();

    PetType petType = PetType.DOG;
    String breed = "Golden Retriever";
    String description = "A loyal and intelligent dog that loves to play.";

    // Act
    String enhancedDescription = petDescriptionEnhancer.enhanceDescription(petType, breed, description);

    log.debug("Enhanced Description:\n\n{}", enhancedDescription);

    // Assert
    assertNotNull(enhancedDescription, "The enhanced description should not be null");
    assertTrue(
        enhancedDescription.toLowerCase().contains("golden retriever"),
        "The enhanced description should contain factual breed details"
    );
    assertTrue(
        enhancedDescription.length() > description.length(),
        "The enhanced description should be enhanced and longer than the original one"
    );
  }


}
