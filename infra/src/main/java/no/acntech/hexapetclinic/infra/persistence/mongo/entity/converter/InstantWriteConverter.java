package no.acntech.hexapetclinic.infra.persistence.mongo.entity.converter;

import java.time.Instant;
import java.util.Date;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

/**
 * Converts `Instant` to `Date` when writing to the database.
 */
@WritingConverter
@Component
public class InstantWriteConverter implements Converter<Instant, Date> {

  @Override
  public Date convert(@NonNull Instant source) {
    return Date.from(source);
  }
}
