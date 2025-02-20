package no.acntech.hexapetclinic.infra.persistence.mongo.entity.converter;

import java.time.Instant;
import java.util.Date;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@ReadingConverter
@Component
public class InstantReadConverter implements Converter<Date, Instant> {

  @Override
  public Instant convert(Date source) {
    return source.toInstant();
  }
}