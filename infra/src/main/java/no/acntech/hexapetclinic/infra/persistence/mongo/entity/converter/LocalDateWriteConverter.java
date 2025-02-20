package no.acntech.hexapetclinic.infra.persistence.mongo.entity.converter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@WritingConverter
@Component
public class LocalDateWriteConverter implements Converter<LocalDate, Date> {

  @Override
  public Date convert(LocalDate source) {
    return Date.from(source.atStartOfDay(ZoneId.systemDefault()).toInstant());
  }
}
