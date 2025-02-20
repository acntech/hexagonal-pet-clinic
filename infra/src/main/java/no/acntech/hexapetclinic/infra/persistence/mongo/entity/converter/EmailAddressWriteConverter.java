package no.acntech.hexapetclinic.infra.persistence.mongo.entity.converter;

import lombok.NonNull;
import no.acntech.hexapetclinic.domain.model.EmailAddress;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@Component
@WritingConverter
public class EmailAddressWriteConverter implements Converter<EmailAddress, String> {

    @Override
    public String convert(@NonNull EmailAddress source) {
        return source.getPrimitive();
    }
}