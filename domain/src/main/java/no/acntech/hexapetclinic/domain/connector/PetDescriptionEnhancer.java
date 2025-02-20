package no.acntech.hexapetclinic.domain.connector;

import lombok.NonNull;
import no.acntech.hexapetclinic.domain.model.PetType;

/**
 * Defines an interface for enhancing pet descriptions based on various pet attributes.
 * Implementations of this interface provide a mechanism to generate richer and more detailed
 * descriptions for pets, factoring in their type, breed, and initial description input.
 */
public interface PetDescriptionEnhancer {

  String enhanceDescription(@NonNull PetType petType, @NonNull String breed, @NonNull String description);

}
