package no.acntech.hexapetclinic.utils.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FlexibleInstantParser {

  private static final int MAX_NANO_FRACTION_DIGITS = 9;

  private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
      // Date and Time
      .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd['T'HH:mm[:ss]]"))
      // Fraction of second
      .optionalStart()
      .appendFraction(ChronoField.NANO_OF_SECOND, 0, MAX_NANO_FRACTION_DIGITS, true)
      .optionalEnd()
      // Time Zone or Offset (with colon, e.g., +HH:MM)
      .optionalStart()
      .appendOffset("+HH:MM", "Z")
      .optionalEnd()
      // Time Zone or Offset (without colon, e.g., +HHMM)
      .optionalStart()
      .appendOffset("+HHMM", "Z")
      .optionalEnd()
      // Time Zone or Offset (just hours, e.g., +HH)
      .optionalStart()
      .appendOffset("+HH", "Z")
      .optionalEnd()
      // Timezone ID (such as America/New_York)
      .optionalStart()
      .appendZoneRegionId()
      .optionalEnd()
      // Create the formatter
      .toFormatter();

  public static Instant parse(String input) {
    return parse(input, ZoneId.systemDefault());
  }

  public static Instant parse(String input, @NonNull ZoneId defaultZoneId) {
    if (input == null) {
      return null;
    }

    Instant result;

    try {
      // Attempt to parse directly as an Instant
      result = Instant.parse(input);
    } catch (DateTimeParseException ignored) {
      // Try parsing with the custom formatter
      TemporalAccessor temporalAccessor = FORMATTER.parseBest(input, ZonedDateTime::from, OffsetDateTime::from, LocalDateTime::from);

      result = switch (temporalAccessor) {
        case ZonedDateTime zonedDateTime -> zonedDateTime.toInstant();
        case OffsetDateTime offsetDateTime -> offsetDateTime.toInstant();
        case LocalDateTime localDateTime -> localDateTime.atZone(defaultZoneId).toInstant();
        default -> throw new DateTimeParseException("Failed to parse Instant from input string: " + input, input, 0);
      };
    }

    return result;
  }
}
