package no.acntech.hexapetclinic.utils.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import no.acntech.hexapetclinic.utils.time.FlexibleInstantParser;

public class FlexibleInstantDeserializer extends JsonDeserializer<Instant> {

  @Override
  public Instant deserialize(JsonParser parser, DeserializationContext context) throws IOException {
    String input = parser.getText();
    try {
      // Use the FlexibleInstantParser for deserialization
      return FlexibleInstantParser.parse(input, ZoneId.systemDefault());
    } catch (DateTimeParseException e) {
      throw new IOException("Failed to deserialize Instant: Invalid format for input: " + input, e);
    }
  }
}
