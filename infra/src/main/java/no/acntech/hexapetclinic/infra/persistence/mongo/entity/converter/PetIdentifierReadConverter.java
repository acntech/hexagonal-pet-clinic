package no.acntech.hexapetclinic.infra.persistence.mongo.entity.converter;

import lombok.NonNull;
import no.acntech.hexapetclinic.domain.model.PetIdentifier;
import no.acntech.hexapetclinic.domain.model.TelephoneNumber;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class PetIdentifierReadConverter implements Converter<String, PetIdentifier> {

    @Override
    public PetIdentifier convert(@NonNull String source) {
        return PetIdentifier.of(source);
    }
}