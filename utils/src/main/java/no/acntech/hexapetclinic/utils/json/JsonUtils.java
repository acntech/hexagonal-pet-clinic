package no.acntech.hexapetclinic.utils.json;

import static no.acntech.hexapetclinic.utils.text.TextConstants.NEW_LINE;
import static no.acntech.hexapetclinic.utils.text.TextConstants.SPACE;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.UncheckedIOException;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class JsonUtils {

  // @spotless:off
  public static final ObjectMapper LENIENT_MAPPER = new Jackson2ObjectMapperBuilder()
      .serializationInclusion(JsonInclude.Include.NON_NULL)
      .featuresToEnable(
          SerializationFeature.INDENT_OUTPUT,
          MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES,
          MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
      .featuresToDisable(
          DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
          SerializationFeature.FAIL_ON_EMPTY_BEANS)
      .serializers()
      .build()
      .findAndRegisterModules()
      .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
      .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
      .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
  // @spotless:on

  public static final int DEFAULT_BASE_INDENT = 2;

  public static String prettyPrintWithIndent(@NonNull Object node) {
    return prettyPrintWithIndent(node, SPACE.repeat(DEFAULT_BASE_INDENT));
  }

  public static String prettyPrintWithIndent(@NonNull Object node, @NonNull String baseIndent) {
    try {
      // Get the pretty printed string
      String prettyJson = LENIENT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(node);

      // Add the baseIndent to the start of every line
      return String.join(NEW_LINE, prettyJson.lines().map(line -> baseIndent + line).toArray(String[]::new));
    } catch (JsonProcessingException e) {
      throw new UncheckedIOException("Failed to pretty print JSON", e);
    }
  }

  public static boolean isEmptyJsonObject(@NonNull String jsonData) {
    try {
      JsonNode jsonNode = LENIENT_MAPPER.readTree(jsonData);
      return jsonNode.isObject() && jsonNode.isEmpty();
    } catch (JsonProcessingException e) {
      return false;
    }
  }

  /**
   * Escapes special characters in JSON strings.
   *
   * @param text the input base64EncodedMarkdown
   * @return the escaped base64EncodedMarkdown
   */
  public static String escapeJson(String text) {
    if (text == null) {
      return null;
    }

    return text.replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\b", "\\b")
        .replace("\f", "\\f")
        .replace("\n", "\\nAlternatives")
        .replace("\r", "\\r")
        .replace("\t", "\\t");
  }

}
