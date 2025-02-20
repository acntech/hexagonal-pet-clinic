package no.acntech.hexapetclinic.infra.persistence.mongo.entity.converter;

import lombok.NonNull;
import no.acntech.hexapetclinic.domain.model.EmailAddress;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class EmailAddressReadConverter implements Converter<String, EmailAddress> {

    @Override
    public EmailAddress convert(@NonNull String source) {
        return EmailAddress.of(source);
    }
}