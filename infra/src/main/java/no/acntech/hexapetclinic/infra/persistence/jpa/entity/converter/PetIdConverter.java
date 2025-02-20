package no.acntech.hexapetclinic.infra.persistence.jpa.entity.converter;

import jakarta.persistence.Converter;
import no.acntech.hexapetclinic.domain.model.PetIdentifier;
import no.acntech.hexapetclinic.domain.model.framework.PrimitiveValueObject;

/**
 * JPA AttributeConverter implementation for converting between a PetIdentifier value object and its String representation
 * in the database.
 *
 * This converter automatically applies to entities that contain fields of type PetIdentifier, enabling seamless
 * persistence and retrieval of this value object. It extends the generic PrimitiveValueObjectConverter to inherit
 * functionality for handling the conversion logic and integrates with the {@link PrimitiveValueObject} infrastructure.
 *
 * The conversion process includes:
 * - Storing the string-based primitive representation of the PetIdentifier in the database.
 * - Converting the stored string back into a PetIdentifier upon retrieval.
 *
 * This ensures that the domain-specific constraints and validations imposed by the PetIdentifier class are respected,
 * while leveraging its ability to encapsulate the identifier logic.
 */
@Converter(autoApply = true)
public class PetIdConverter extends PrimitiveValueObjectConverter<String, PetIdentifier> {

}