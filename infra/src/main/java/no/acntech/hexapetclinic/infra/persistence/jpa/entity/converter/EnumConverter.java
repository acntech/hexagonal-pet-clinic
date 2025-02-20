package no.acntech.hexapetclinic.infra.persistence.jpa.entity.converter;

import jakarta.persistence.AttributeConverter;
import java.util.Locale;
import lombok.NonNull;

/**
 * A generic JPA Attribute Converter for persisting and retrieving Java Enum types as Strings in the database.
 *
 * This converter is designed to simplify the persistence of enumerations in JPA by allowing enum values
 * to be stored as their string representations in the database and subsequently retrieved as the corresponding
 * enum type.
 *
 * @param <T> the type of the Enum that this converter handles
 */
public class EnumConverter<T extends Enum<T>> implements AttributeConverter<T, String> {

  private final Class<T> enumType;

  protected EnumConverter(@NonNull Class<T> enumType) {
    this.enumType = enumType;
  }

  @Override
  public String convertToDatabaseColumn(T attribute) {
    return attribute != null ? attribute.name() : null;
  }

  @Override
  public T convertToEntityAttribute(String dbData) {
    return dbData != null ? Enum.valueOf(enumType, dbData.toUpperCase(Locale.ROOT)) : null;
  }
}
