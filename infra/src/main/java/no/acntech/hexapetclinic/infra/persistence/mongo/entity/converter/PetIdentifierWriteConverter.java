package no.acntech.hexapetclinic.infra.persistence.mongo.entity.converter;

import lombok.NonNull;
import no.acntech.hexapetclinic.domain.model.PetIdentifier;
import no.acntech.hexapetclinic.domain.model.TelephoneNumber;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

// TelephoneNumberWriteConverter.java
@Component
@WritingConverter
public class PetIdentifierWriteConverter implements Converter<PetIdentifier, String> {

    @Override
    public String convert(@NonNull PetIdentifier source) {
        return source.getPrimitive();
    }
}