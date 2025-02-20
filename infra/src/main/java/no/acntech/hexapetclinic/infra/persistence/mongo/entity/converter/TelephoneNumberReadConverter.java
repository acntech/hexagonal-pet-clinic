package no.acntech.hexapetclinic.infra.persistence.mongo.entity.converter;

import lombok.NonNull;
import no.acntech.hexapetclinic.domain.model.TelephoneNumber;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class TelephoneNumberReadConverter implements Converter<String, TelephoneNumber> {

    @Override
    public TelephoneNumber convert(@NonNull String source) {
        return TelephoneNumber.of(source);
    }
}