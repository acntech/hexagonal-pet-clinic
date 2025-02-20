package no.acntech.hexapetclinic.domain.model;

import java.util.regex.Pattern;
import no.acntech.hexapetclinic.domain.model.framework.StringValueObject;
import no.acntech.hexapetclinic.utils.text.RegexValidator;
import no.acntech.hexapetclinic.utils.validation.Validator;

/**
 * Value object representing a telephone number.
 */
public final class TelephoneNumber extends StringValueObject {

  public static final int MIN_LENGTH = 7;
  public static final int MAX_LENGTH = 15; // Covers most international formats

  // Allows international (+123456789) and local (123-456-7890) numbers
  private static final String TELEPHONE_REGEX = "^(\\+?[0-9]{1,4})?([ .-]?\\(?[0-9]{2,4}\\)?)?[ .-]?[0-9]{3,4}[ .-]?[0-9]{3,4}$";

  private static final Validator<String> PATTERN_VALIDATOR = new RegexValidator(Pattern.compile(TELEPHONE_REGEX));

  /**
   * Factory method to create a new TelephoneNumber instance.
   *
   * @param value the telephone number as a string.
   * @return an instance of TelephoneNumber.
   */
  public static TelephoneNumber of(String value) {
    return new TelephoneNumber(value);
  }

  /**
   * Creates a new TelephoneNumber.
   *
   * @param value the telephone number as a string.
   */
  public TelephoneNumber(String value) {
    super(value);
  }

  @Override
  public int getMinLength() {
    return MIN_LENGTH;
  }

  @Override
  public int getMaxLength() {
    return MAX_LENGTH;
  }

  /**
   * Validates the syntax of the telephone number using a pre-defined regex pattern.
   *
   * @param value the telephone number as a string.
   */
  @Override
  public void validateSyntax(String value) {
    PATTERN_VALIDATOR.validate(value);
  }
}
