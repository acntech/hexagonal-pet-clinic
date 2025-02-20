package no.acntech.hexapetclinic.infra.persistence.jpa.entity.converter;

import jakarta.persistence.Converter;
import no.acntech.hexapetclinic.domain.model.EmailAddress;
import no.acntech.hexapetclinic.domain.model.framework.PrimitiveValueObject;

/**
 * JPA Attribute Converter for converting between the {@link EmailAddress} value object and its
 * corresponding database representation as a {@link String}.
 *
 * This converter facilitates persistence of {@link EmailAddress} objects by mapping the primitive
 * value stored in the database to the value object, and vice versa. The conversion is applied
 * automatically in JPA due to the {@code autoApply = true} specification in the {@link Converter}
 * annotation.
 *
 * {@link EmailAddress} represents an email address as a value object with validations and constraints,
 * ensuring that only valid email addresses conforming to defined rules can be persisted or retrieved.
 *
 * This class extends {@link PrimitiveValueObjectConverter}, which provides a reusable mechanism for
 * converting any {@link PrimitiveValueObject} to and from its primitive value.
 */
@Converter(autoApply = true)
public class EmailConverter extends PrimitiveValueObjectConverter<String, EmailAddress> {

}