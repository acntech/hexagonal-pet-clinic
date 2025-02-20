package no.acntech.hexapetclinic.infra.persistence.mongo.entity.converter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import no.acntech.hexapetclinic.domain.model.framework.PrimitiveValueObject;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class PrimitiveValueObjectReadConverter<P extends Comparable<P>, T extends PrimitiveValueObject<P>> implements Converter<P, T> {

  private final Constructor<T> constructor; // Store the constructor

  public PrimitiveValueObjectReadConverter(Class<T> valueObjectClass, Class<P> primitiveClass) {
    try {
      constructor = valueObjectClass.getConstructor(primitiveClass);
      constructor.setAccessible(true);
    } catch (NoSuchMethodException e) {
      String errorMessage = String.format(
          "Error getting constructor for value object class '%s' with primitive class '%s'. " +
              "Ensure that the value object class '%s' has a constructor accepting a '%s' parameter.",
          valueObjectClass.getName(),
          primitiveClass.getName(),
          valueObjectClass.getName(),
          primitiveClass.getName()
      );
      log.error(errorMessage, e);
      throw new IllegalArgumentException(errorMessage, e);
    }
  }

  @Override
  public T convert(@NonNull P dbData) {
    try {
      return constructor.newInstance(dbData); // Use the stored constructor
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      String errorMessage = String.format(
          "Error converting database value to PrimitiveValueObject. " +
              "Details: dbData='%s', primitiveClass='%s', valueObjectClass='%s'.",
          dbData,
          constructor.getParameterTypes()[0].getName(), // Use constructor parameter type
          constructor.getDeclaringClass().getName()  // Use constructor's declaring class
      );
      log.error(errorMessage, e);
      throw new IllegalArgumentException(errorMessage, e);
    }
  }
}