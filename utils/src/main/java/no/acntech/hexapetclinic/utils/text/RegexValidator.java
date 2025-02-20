package no.acntech.hexapetclinic.utils.text;

import java.util.Objects;
import java.util.regex.Pattern;
import no.acntech.hexapetclinic.utils.validation.ValidationException;
import no.acntech.hexapetclinic.utils.validation.Validator;

public class RegexValidator implements Validator<String> {

  private static final int TRUNCATE_MAX_LENGTH = 16;

  public static RegexValidator of(String pattern) {
    return new RegexValidator(pattern);
  }

  private final Pattern pattern;

  public RegexValidator(String pattern) {
    this(Pattern.compile(pattern));
  }

  public RegexValidator(Pattern pattern) {
    this.pattern = pattern;
  }

  @Override
  public void validate(String t) {
    if (!pattern.matcher(t).matches()) {
      throw new ValidationException("String '" + StringUtils.truncate(t, TRUNCATE_MAX_LENGTH, false, true)
          + "' does not match the required regex pattern: " + pattern);
    }
  }

  @Override
  public boolean equals(Object other) {
    return this == other || (other instanceof RegexValidator that && Objects.equals(pattern.pattern(), that.pattern.pattern()));
  }


  @Override
  public int hashCode() {
    return Objects.hash(pattern.pattern());
  }

  @Override
  public String toString() {
    return pattern.pattern();
  }
}
