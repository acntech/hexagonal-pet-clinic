package no.acntech.hexapetclinic.domain.model;

/**
 * Enumerates the various pet types recognized within the domain model.
 * PetType is used to classify pets into distinct categories, allowing for
 * better organization and differentiation in the system.
 *
 * This enumeration provides a predefined set of constants, representing
 * common and specific types of pets that the system can handle, including
 * dogs, cats, birds, and other less common types.
 */
public enum PetType {
  DOG,
  CAT,
  BIRD,
  FISH,
  RABBIT,
  HAMSTER,
  TURTLE,
  SNAKE,
  LIZARD,
  HORSE,
  OTHER;
}
