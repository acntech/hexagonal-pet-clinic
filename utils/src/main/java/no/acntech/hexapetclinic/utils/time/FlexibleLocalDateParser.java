package no.acntech.hexapetclinic.utils.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FlexibleLocalDateParser {

  private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
      // Support various date formats
      .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
      .appendOptional(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
      .appendOptional(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
      .appendOptional(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
      .appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
      .appendOptional(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
      // Create the formatter
      .toFormatter();

  @SuppressWarnings("PMD.PreserveStackTrace")
  public static LocalDate parse(String input) {
    if (input == null) {
      return null;
    }

    LocalDate result;

    try {
      // Try parsing directly as LocalDate
      result = LocalDate.parse(input);
    } catch (DateTimeParseException ignored) {
      // Try parsing with the custom formatter
      try {
        result = LocalDate.from(FORMATTER.parse(input));
      } catch (DateTimeParseException e) {
        throw new DateTimeParseException("Failed to parse LocalDate from input string: " + input, input, e.getErrorIndex());
      }
    }

    return result;
  }
}
