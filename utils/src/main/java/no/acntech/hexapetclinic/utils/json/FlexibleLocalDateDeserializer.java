package no.acntech.hexapetclinic.utils.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import no.acntech.hexapetclinic.utils.time.FlexibleLocalDateParser;

public class FlexibleLocalDateDeserializer extends JsonDeserializer<LocalDate> {

  @Override
  public LocalDate deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
    String dateValue = parser.getText();
    try {
      return FlexibleLocalDateParser.parse(dateValue);
    } catch (DateTimeParseException e) {
      throw new IOException("Failed to deserialize LocalDate: Invalid date format: " + dateValue, e);
    }
  }
}
