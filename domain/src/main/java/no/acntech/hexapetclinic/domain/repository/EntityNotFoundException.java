package no.acntech.hexapetclinic.domain.repository;

import no.acntech.hexapetclinic.domain.DomainException;

/**
 * Exception thrown when an entity is not found.
 */
public class EntityNotFoundException extends DomainException {

  // Constructors
  public EntityNotFoundException() {
    super();
  }

  public EntityNotFoundException(String message) {
    super(message);
  }

  public EntityNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public EntityNotFoundException(Throwable cause) {
    super(cause);
  }
}
