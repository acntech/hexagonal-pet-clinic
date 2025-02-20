package no.acntech.hexapetclinic.infra.persistence.mongo.entity.converter;

import lombok.NonNull;
import no.acntech.hexapetclinic.domain.model.framework.PrimitiveValueObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

public class PrimitiveValueObjectWriteConverter<P extends Comparable<P>, T extends PrimitiveValueObject<P>> implements Converter<T, P> {

  @Override
  public P convert(@NonNull T attribute) {
    return attribute.getPrimitive();
  }
}
