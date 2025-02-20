package no.acntech.hexapetclinic.domain.model.framework;

import lombok.NonNull;

/**
 * A DDD ValueObject holding a single (primitive) value.
 *
 * <p>
 * A SimpleValueObject restricts the underlying value using domain specific rules - e.g. applying range restrictions (for numbers),
 * non-null, non-blank, length restrictions for Strings, etc.
 *
 * <p>
 * A SimpleValueObject is usually constructed using a single-argument constructor and/or with a static <code>of(P p)</code> method.
 */
public interface PrimitiveValueObject<P extends Comparable<P>> extends ValueObject, Comparable<PrimitiveValueObject<P>> {

  /**
   * Returns the underlying primitive value.
   *
   * @return the primitive value
   */
  P getPrimitive();

  /**
   * Compares this object with the specified object for order.
   *
   * @param p the object to be compared
   * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object
   */
  @Override
  default int compareTo(@NonNull PrimitiveValueObject<P> p) {
    return getPrimitive().compareTo(p.getPrimitive());
  }
}
