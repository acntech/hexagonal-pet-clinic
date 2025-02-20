package no.acntech.hexapetclinic.infra.persistence.jpa.entity.converter;

import jakarta.persistence.Converter;
import no.acntech.hexapetclinic.domain.model.Gender;

/**
 * JPA Attribute Converter for converting between the {@link Gender} enumeration
 * and its database representation as a {@link String}.
 *
 * This converter facilitates the persistence of {@link Gender} enum values in a database
 * by storing them as their name representations (e.g., "MALE" or "FEMALE") and retrieving them
 * as the corresponding enum values. It leverages the {@link EnumConverter} class for the
 * base functionality and specifies the {@link Gender} type.
 *
 * The annotation {@code @Converter(autoApply = true)} ensures that this converter is
 * automatically applied wherever the {@link Gender} type is used in JPA entity fields.
 */
@Converter(autoApply = true)
public class GenderConverter extends EnumConverter<Gender> {

  public GenderConverter() {
    super(Gender.class);
  }
}