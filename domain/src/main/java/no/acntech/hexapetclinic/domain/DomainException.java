package no.acntech.hexapetclinic.domain;

/**
 * Represents a generic exception in the domain layer. This is a runtime exception and is used to signal any domain-specific error
 * conditions.
 */
public class DomainException extends RuntimeException {

  // Constructors
  public DomainException() {
    super();
  }

  public DomainException(String message) {
    super(message);
  }

  public DomainException(String message, Throwable cause) {
    super(message, cause);
  }

  public DomainException(Throwable cause) {
    super(cause);
  }
}
