package no.acntech.hexapetclinic.infra.persistence.mongo.entity.converter;

import lombok.NonNull;
import no.acntech.hexapetclinic.domain.model.TelephoneNumber;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

// TelephoneNumberWriteConverter.java
@Component
@WritingConverter
public class TelephoneNumberWriteConverter implements Converter<TelephoneNumber, String> {

    @Override
    public String convert(@NonNull TelephoneNumber source) {
        return source.getPrimitive();
    }
}