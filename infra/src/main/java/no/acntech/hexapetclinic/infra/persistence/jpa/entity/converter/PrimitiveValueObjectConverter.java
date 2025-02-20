package no.acntech.hexapetclinic.infra.persistence.jpa.entity.converter;

import com.google.common.reflect.TypeToken;
import jakarta.persistence.AttributeConverter;
import java.lang.reflect.InvocationTargetException;
import lombok.extern.slf4j.Slf4j;
import no.acntech.hexapetclinic.domain.model.framework.PrimitiveValueObject;

/**
 * Abstract JPA Attribute Converter for converting between a {@link PrimitiveValueObject} and its
 * corresponding primitive representation, as used in database persistence.
 *
 * This class simplifies the process of persisting and retrieving {@link PrimitiveValueObject}
 * instances by mapping the value object to its underlying primitive value (e.g., String, Integer)
 * and vice versa. It leverages reflection to automatically determine the value object and
 * primitive types.
 *
 * Subclasses should extend this class for specific {@link PrimitiveValueObject} implementations
 * to ensure seamless integration with JPA. It is commonly used in scenarios where the entity
 * fields leverage domain-specific value objects that encapsulate validation and domain rules.
 *
 * Key responsibilities:
 * - Convert a {@link PrimitiveValueObject} to its primitive value for database storage.
 * - Convert the primitive value retrieved from the database into the corresponding
 *   {@link PrimitiveValueObject}.
 *
 * Conversion Workflow:
 * 1. During persistence, the {@link #convertToDatabaseColumn(Object)} method extracts the primitive
 *    value from the {@link PrimitiveValueObject} using its `getPrimitive()` method.
 * 2. During retrieval, the {@link #convertToEntityAttribute(Object)} method reconstructs the
 *    {@link PrimitiveValueObject} by invoking its constructor with the primitive value.
 *
 * Error Handling:
 * If the {@link PrimitiveValueObject} class does not provide a public constructor compatible with
 * the primitive type, or if instantiation fails for other reasons, an {@link IllegalArgumentException}
 * is thrown with a detailed error message.
 *
 * Type Parameters:
 * - P: The primitive type (e.g., String, Integer) used to persist the {@link PrimitiveValueObject}.
 * - T: The type of the {@link PrimitiveValueObject}.
 *
 * Example Scenarios:
 * - Seamlessly persisting domain-specific objects such as email addresses, identifiers, or
 *   other value objects encapsulating primitive values.
 *
 * Requirements:
 * - The {@link PrimitiveValueObject} class must have a single-argument constructor accepting the
 *   primitive value.
 */
@Slf4j
public abstract class PrimitiveValueObjectConverter<P extends Comparable<P>, T extends PrimitiveValueObject<P>>
    implements AttributeConverter<T, P> {

  private final Class<T> valueObjectClass;
  private final Class<P> primitiveClass;

  @SuppressWarnings("unchecked")
  public PrimitiveValueObjectConverter() {
    TypeToken<T> valueObjectToken = new TypeToken<T>(getClass()) {
    };
    TypeToken<P> primitiveToken = (TypeToken<P>) valueObjectToken.resolveType(PrimitiveValueObject.class.getTypeParameters()[0]);
    this.valueObjectClass = (Class<T>) valueObjectToken.getRawType();
    this.primitiveClass = (Class<P>) primitiveToken.getRawType();
  }

  @Override
  public P convertToDatabaseColumn(T attribute) {
    return attribute == null ? null : attribute.getPrimitive();
  }

  @Override
  public T convertToEntityAttribute(P dbData) {
    if (dbData == null) {
      return null;
    }

    try {
      return valueObjectClass.getConstructor(primitiveClass).newInstance(dbData);
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      String errorMessage = String.format(
          "Error converting database value to PrimitiveValueObject. " + "Details: dbData='%s', primitiveClass='%s', valueObjectClass='%s'. "
              + "Ensure that the value object class '%s' has a constructor accepting a '%s' parameter.",
          dbData,
          primitiveClass.getName(),
          valueObjectClass.getName(),
          valueObjectClass.getName(),
          primitiveClass.getName()
      );
      log.error(errorMessage, e);
      throw new IllegalArgumentException(errorMessage, e);
    }
  }

}
