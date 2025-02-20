package no.acntech.hexapetclinic.domain.model.framework;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import no.acntech.hexapetclinic.utils.validation.RangeValidator;
import no.acntech.hexapetclinic.utils.validation.ValidationException;

/**
 * Simple implementation of a PrimitiveValueObject. This class is intended to be subclassed by value objects that wrap a single, primitive
 * value. The primitive value is validated by first checking the range extremes (if any) and then calling the possibly overridden
 * {@link #validate} method.
 */
@EqualsAndHashCode
public class SimpleValueObject<P extends Comparable<P>> implements PrimitiveValueObject<P> {

  private final P primitive;

  /**
   * Protected constructor - subclasses <i>must</i> call this in order for the framework to work. The primitive value is validated by first
   * checking the range extremes (if any) and then calling the possibly overridden {@link #validate} method.
   *
   * @param primitive the primitive value.
   * @throws ValidationException if the primitive is not within range or is otherwise invalid according to business rules.
   */
  protected SimpleValueObject(@NonNull P primitive) {
    validateRange(primitive); // range validation
    validate(primitive); // possibly overridden
    this.primitive = primitive;
  }

  /**
   * Validates the primitive value given in the constructor - throws a (subclass of) {@link RuntimeException} (possibly an
   * {@link IllegalArgumentException} or {@link ValidationException} if the primitive is invalid. The default implementation does nothing.
   * Note that range validation is performed by the constructor prior to calling this method.
   *
   * @param primitive the primitive value.
   */
  protected void validate(P primitive) {
    // Default implementation does nothing.
  }

  /**
   * Returns the underlying primitive value.
   *
   * @return the primitive value
   */
  @JsonValue
  @Override
  public final P getPrimitive() {
    return primitive;
  }

  @Override
  public final String toString() {
    return primitive.toString();
  }

  /**
   * Override to return the inclusive minimum value, the default implementation returns null.
   *
   * @return the inclusive minimum value, or null of not applicable.
   */
  protected P getInclusiveMin() {
    return null;
  }

  /**
   * Override to return the inclusive maximum value, the default implementation returns null.
   *
   * @return the inclusive maximum value, or null of not applicable.
   */
  protected P getInclusiveMax() {
    return null;
  }

  /**
   * Override to return the exclusive minimum value, the default implementation returns null.
   *
   * @return the exclusive minimum value, or null of not applicable.
   */
  protected P getExclusiveMin() {
    return null;
  }

  /**
   * Override to return the exclusive maximum value, the default implementation returns null.
   *
   * @return the exclusive maximum value, or null of not applicable.
   */
  protected P getExclusiveMax() {
    return null;
  }

  private void validateRange(P value) {
    new RangeValidator<>(getInclusiveMin(), getExclusiveMin(), getInclusiveMax(), getExclusiveMax()).validate(value);
  }
}
