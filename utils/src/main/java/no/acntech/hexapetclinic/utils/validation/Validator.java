package no.acntech.hexapetclinic.utils.validation;

public interface Validator<T> {

  /**
   * Validates the given object.
   *
   * @param t the object to validate
   * @throws ValidationException if the object is not valid
   */
  void validate(T t) throws ValidationException;
}
