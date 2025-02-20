package no.acntech.hexapetclinic.domain.model;

import java.util.regex.Pattern;
import no.acntech.hexapetclinic.domain.model.framework.StringValueObject;
import no.acntech.hexapetclinic.utils.text.RegexValidator;
import no.acntech.hexapetclinic.utils.validation.Validator;

/**
 * Value object representing an email address.
 */
public final class EmailAddress extends StringValueObject {

  public static final int MIN_LENGTH = 3;

  // Based on RFC 5321 standard for email addresses
  public static final int MAX_LENGTH = 254;

  private static final String EMAIL_REGEX = "(?i)^(?!\\.)([a-zA-Z0-9._%+-]+)(?<!\\.)@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";


  private static final Validator<String> PATTERN_VALIDATOR = new RegexValidator(Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE));

  /**
   * Factory method to create a new EmailAddress instance.
   *
   * @param value the email address as a string.
   * @return an instance of EmailAddress.
   */
  public static EmailAddress of(String value) {
    return new EmailAddress(value);
  }

  /**
   * Creates a new EmailAddress.
   *
   * @param value the email address as a string.
   */
  public EmailAddress(String value) {
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
   * Validates the syntax of the email address using a pre-defined regex pattern.
   *
   * @param value the email address as a string.
   */
  @Override
  public void validateSyntax(String value) {
    PATTERN_VALIDATOR.validate(value);
  }
}
