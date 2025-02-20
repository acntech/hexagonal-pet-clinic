package no.acntech.hexapetclinic.utils.validation;

/**
 * Exception thrown when an entity is not valid.
 */
public class ValidationException extends RuntimeException {

  // Constructors
  public ValidationException() {
    super();
  }

  public ValidationException(String message) {
    super(message);
  }

  public ValidationException(String message, Throwable cause) {
    super(message, cause);
  }

  public ValidationException(Throwable cause) {
    super(cause);
  }
}
