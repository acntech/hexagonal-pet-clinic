package no.acntech.hexapetclinic.infra.persistence.mongo.entity.converter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

/**
 * Converts MongoDB's `Date` to `LocalDate` when reading from the database.
 */
@ReadingConverter
@Component
public class LocalDateReadConverter implements Converter<Date, LocalDate> {

  @Override
  public LocalDate convert(Date source) {
    return source.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }
}
