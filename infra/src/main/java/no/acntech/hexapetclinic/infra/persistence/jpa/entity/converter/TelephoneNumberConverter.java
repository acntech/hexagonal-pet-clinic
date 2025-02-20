package no.acntech.hexapetclinic.infra.persistence.jpa.entity.converter;

import jakarta.persistence.Converter;
import no.acntech.hexapetclinic.domain.model.TelephoneNumber;
import no.acntech.hexapetclinic.domain.model.framework.PrimitiveValueObject;

/**
 * JPA Attribute Converter for converting between the {@link TelephoneNumber} value object and its
 * corresponding string representation in the database.
 *
 * This converter facilitates the persistence of {@link TelephoneNumber} objects by mapping the
 * primitive value (a {@link String}) stored in the database to the {@link TelephoneNumber} value object,
 * and vice versa. By using the {@code autoApply = true} specification in the {@link Converter} annotation,
 * this conversion is applied automatically for entity fields of type {@link TelephoneNumber}.
 *
 * {@link TelephoneNumber} is a domain-specific value object that encapsulates a telephone number,
 * providing validation of syntax, defined length constraints, and proper encapsulation to ensure the integrity
 * of telephone data within the domain model.
 *
 * This class extends {@link PrimitiveValueObjectConverter}, which provides a reusable framework for converting
 * between any {@link PrimitiveValueObject} and its corresponding primitive representation, enabling seamless
 * integration with JPA for custom value objects.
 *
 * Benefits of using this converter include:
 * - Enforcing domain rules and validation defined within the {@link TelephoneNumber} class.
 * - Ensuring a cleaner domain model by abstracting the conversion logic away from the entities.
 * - Providing automatic application of the conversion logic to all entities containing {@link TelephoneNumber} fields.
 */
@Converter(autoApply = true)
public class TelephoneNumberConverter extends PrimitiveValueObjectConverter<String, TelephoneNumber> {

}