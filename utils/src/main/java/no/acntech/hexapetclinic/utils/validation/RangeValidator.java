package no.acntech.hexapetclinic.utils.validation;

import static org.apache.commons.lang3.Validate.isTrue;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@EqualsAndHashCode
public class RangeValidator<P extends Comparable<P>> implements Validator<P> {

  private final P inclusiveMin;

  private final P exclusiveMin;

  private final P inclusiveMax;

  private final P exclusiveMax;

  public RangeValidator(P inclusiveMin, P exclusiveMin, P inclusiveMax, P exclusiveMax) {
    this.inclusiveMin = inclusiveMin;
    this.exclusiveMin = exclusiveMin;
    this.inclusiveMax = inclusiveMax;
    this.exclusiveMax = exclusiveMax;

    isTrue(!(inclusiveMin != null && exclusiveMin != null),
        "Both inclusiveMin '%s' and exclusiveMin '%s' are set. They are mutually exclusive.", inclusiveMin, exclusiveMin);

    isTrue(!(inclusiveMax != null && exclusiveMax != null),
        "Both inclusiveMax '%s' and exclusiveMax '%s' are set. They are mutually exclusive.", inclusiveMax, exclusiveMax);
  }

  @Override
  public void validate(P t) {
    if (inclusiveMin != null && t.compareTo(inclusiveMin) < 0) {
      throw new ValidationException("Value '" + t + "' is below the allowed minimum '" + inclusiveMin + "'");
    }

    if (exclusiveMin != null && t.compareTo(exclusiveMin) <= 0) {
      throw new ValidationException("Value '" + t + "' is below the allowed minimum '" + exclusiveMin + "'");
    }

    if (inclusiveMax != null && t.compareTo(inclusiveMax) > 0) {
      throw new ValidationException("Value '" + t + "' is above the allowed maximum '" + inclusiveMax + "'");
    }

    if (exclusiveMax != null && t.compareTo(exclusiveMax) >= 0) {
      throw new ValidationException("Value '" + t + "' is above the allowed maximum '" + exclusiveMax + "'");
    }
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("inclusiveMin", inclusiveMin)
        .append("exclusiveMin", exclusiveMin)
        .append("inclusiveMax", inclusiveMax)
        .append("exclusiveMax", exclusiveMax)
        .toString();
  }
}
