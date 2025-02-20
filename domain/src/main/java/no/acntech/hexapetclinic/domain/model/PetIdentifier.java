package no.acntech.hexapetclinic.domain.model;

import static org.apache.commons.lang3.Validate.isTrue;

import java.security.SecureRandom;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.regex.Pattern;
import lombok.NonNull;
import no.acntech.hexapetclinic.domain.model.framework.StringValueObject;
import no.acntech.hexapetclinic.utils.text.StringUtils;
import no.acntech.hexapetclinic.utils.validation.ValidationException;

/**
 * Represents a unique identifier for a pet. The identifier is a 16-character string following a specific format defined by the pattern
 * "YYYYmmddGNNNNNNC".
 * <p>
 * The structure of the PetIdentifier is: - YYYY: Year of birth (4 digits) - mm: Month of birth (2 digits) - dd: Day of birth (2 digits) -
 * G: Gender (1 digit, '1' for male, '2' for female) - NNNNNN: Number (6 digits) - C: Control digit (1 digit, computed using a modulus
 * algorithm)
 * <p>
 * The class provides methods to create an instance of PetIdentifier either from an existing String value or by generating one using
 * specific parameters such as the pet's birth date, gender, and a unique number. It validates the syntax and semantics of the string value
 * to ensure conformity with the required format.
 * <p>
 * This class extends {@link StringValueObject}, inheriting its value object semantics and customization options for additional validation.
 */
public class PetIdentifier extends StringValueObject {

  public static final int FIXED_LENGTH = 16;

  private static final String REGEX = "^(\\d{4})(\\d{2})(\\d{2})([12])(\\d{6})(\\d)$";
  private static final Pattern PATTERN = Pattern.compile(REGEX);

  private static final SecureRandom RANDOM = new SecureRandom(); // To generate random unique numbers
  private static final int MIN_UNIQUE = 1;
  private static final int MAX_UNIQUE = 100000;

  public static PetIdentifier of(@NonNull String value) {
    return new PetIdentifier(value);
  }

  public static PetIdentifier generate(int year, int month, int day, @NonNull Gender gender) {
    int uniqueNumber = generateRandomUniqueNumber();
    return generate(year, month, day, gender, uniqueNumber);
  }

  public static PetIdentifier generate(int year, int month, int day, @NonNull Gender gender, int uniqueNumber) {
    validateInput(year, month, day, uniqueNumber);

    String datePart = String.format("%04d%02d%02d", year, month, day);
    String uniquePart = String.format("%06d", uniqueNumber);
    String baseId = datePart + gender.getCode() + uniquePart;

    int controlDigit = computeControlDigit(baseId);
    return new PetIdentifier(baseId + controlDigit);
  }

  private static void validateInput(int year, int month, int day, int uniqueNumber) {
    isTrue(uniqueNumber >= 1 && uniqueNumber <= 100000,
        "Unique number must be between 1 and 100000, but was %d", uniqueNumber);
    isTrue(isValidDate(year, month, day),
        "Invalid date provided: %04d-%02d-%02d", year, month, day);
  }

  private static boolean isValidDate(int year, int month, int day) {
    return !(month < 1 || month > 12 || day < 1 || day > 31) && isValidLocalDate(year, month, day);
  }

  private static boolean isValidLocalDate(int year, int month, int day) {
    try {
      LocalDate.of(year, month, day);
      return true;
    } catch (DateTimeException e) {
      return false;
    }
  }

  private static int generateRandomUniqueNumber() {
    return RANDOM.nextInt(MAX_UNIQUE - MIN_UNIQUE + 1) + MIN_UNIQUE;
  }

  private static int computeControlDigit(String baseId) {
    int sum = 0;
    int weight = 2;
    for (int i = baseId.length() - 1; i >= 0; i--) {
      sum += Character.getNumericValue(baseId.charAt(i)) * weight;
      weight = (weight == 7) ? 2 : weight + 1;
    }
    int mod = sum % 11;
    return (mod == 10) ? 0 : mod;
  }

  private static void validateDate(String datePart) {
    int year = Integer.parseInt(datePart.substring(0, 4));
    int month = Integer.parseInt(datePart.substring(4, 6));
    int day = Integer.parseInt(datePart.substring(6, 8));
    LocalDate.of(year, month, day);
  }

  private static void validateGender(String genderPart) {
    int genderCode = Integer.parseInt(genderPart);
    Gender.fromCode(genderCode);
  }

  private static void validateControlDigit(String value) {
    String baseId = value.substring(0, value.length() - 1);
    int expectedControlDigit = computeControlDigit(baseId);
    int actualControlDigit = Character.getNumericValue(value.charAt(value.length() - 1));
    if (actualControlDigit != expectedControlDigit) {
      throw new ValidationException(String.format(
          "Invalid control digit in PetIdentifier '%s'. Expected: %d, Found: %d",
          value, expectedControlDigit, actualControlDigit));
    }
  }

  public PetIdentifier(@NonNull String value) {
    super(value);
  }

  @Override
  public int getMinLength() {
    return FIXED_LENGTH;
  }

  @Override
  public int getMaxLength() {
    return FIXED_LENGTH;
  }

  @Override
  protected void validateSyntax(String value) {
    if (!PATTERN.matcher(value).matches()) {
      String message = String.format("PetIdentifier '%s' does not match the required format: YYYYmmddGNNNNNNC",
          StringUtils.truncate(value, FIXED_LENGTH + 5));
      throw new ValidationException(message);
    }
  }

  @Override
  protected void validateSemantics(String value) {
    try {
      validateDate(value.substring(0, 8));
      validateGender(value.substring(8, 9));
      validateControlDigit(value);
    } catch (DateTimeException | IllegalArgumentException e) {
      throw new ValidationException("Invalid date or gender in PetIdentifier: " + value, e);
    }
  }

}
