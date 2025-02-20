package no.acntech.hexapetclinic.utils.time;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.DurationFormatUtils;

/**
 * Utility class for formatting time durations and instants.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimeFormatUtils {

  public static final String SECONDS_AND_MILLIS_DURATION_FORMAT = "ss.SSS";

  private static final int THREE_FRACTIONAL_DIGITS = 3;

  public static final DateTimeFormatter SIMPLE_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public static final DateTimeFormatter ISO_INSTANT_3_FRACTIONAL_DIGITS =
      new DateTimeFormatterBuilder().appendInstant(THREE_FRACTIONAL_DIGITS).toFormatter();

  public static final DateTimeFormatter ISO_INSTANT_0_FRACTIONAL_DIGITS = new DateTimeFormatterBuilder().appendInstant(0).toFormatter();

  /**
   * Formats the given duration as a string representing seconds and milliseconds.
   *
   * @param duration the duration to format
   * @return a string representing the duration in seconds and milliseconds
   */
  public static String formatDurationAsSecondsAndMillis(Duration duration) {
    return formatDurationAsSecondsAndMillis(duration.toMillis());
  }

  /**
   * Formats a given time duration into a string representation in seconds and milliseconds.
   *
   * @param time the time duration to be formatted
   * @param unit the time unit of the provided duration
   * @return the formatted time duration as a string in the "ss.SSS" format
   */
  public static String formatDurationAsSecondsAndMillis(long time, TimeUnit unit) {
    return DurationFormatUtils.formatDuration(unit.toMillis(time), SECONDS_AND_MILLIS_DURATION_FORMAT);
  }

  /**
   * Formats a given duration in milliseconds into a string with seconds and milliseconds.
   *
   * @param millis the duration in milliseconds to be formatted
   * @return the formatted duration string in the format "ss.SSS"
   */
  public static String formatDurationAsSecondsAndMillis(long millis) {
    return DurationFormatUtils.formatDuration(millis, SECONDS_AND_MILLIS_DURATION_FORMAT);
  }

  /**
   * Formats the given `Instant` using the specified time zone and pattern.
   *
   * @param instant the instant to format
   * @param tz the time zone to apply during formatting
   * @param pattern the pattern describing the date and time format
   * @return a string representation of the formatted instant
   */
  public static String format(Instant instant, TimeZone tz, String pattern) {
    return format(instant, tz.toZoneId(), pattern);
  }

  /**
   * Formats an Instant to a string based on the given pattern and time zone.
   *
   * @param instant the Instant to be formatted
   * @param zoneId the time zone to be used in the formatting
   * @param pattern the pattern describing the date and time format
   * @return the formatted date-time string
   */
  public static String format(Instant instant, ZoneId zoneId, String pattern) {
    return DateTimeFormatter.ofPattern(pattern).format(instant.atZone(zoneId));
  }

  /**
   * Formats an {@link Instant} using the specified {@link DateTimeFormatter} and {@link TimeZone}.
   *
   * @param instant the instant to be formatted
   * @param timeZone the time zone to use for formatting
   * @param dateTimeFormatter the formatter to apply to the instant
   * @return the formatted instant as a string
   */
  public static String format(Instant instant, TimeZone timeZone, DateTimeFormatter dateTimeFormatter) {
    return dateTimeFormatter.format(instant.atZone(timeZone.toZoneId()));
  }

  /**
   * Formats the given {@link Instant} using the specified {@link ZoneId} and {@link DateTimeFormatter}.
   *
   * @param instant the instant to format
   * @param zoneId the time zone to use for formatting
   * @param dateTimeFormatter the formatter to use
   * @return the formatted time string
   */
  public static String format(Instant instant, ZoneId zoneId, DateTimeFormatter dateTimeFormatter) {
    return dateTimeFormatter.format(instant.atZone(zoneId));
  }

  /**
   * Formats an Instant to a simple local date-time string using the provided time zone.
   *
   * @param instant the instant to format
   * @param timeZone the time zone to use for formatting
   * @return a string representing the formatted date and time
   */
  public static String formatSimpleLocalDateTime(Instant instant, TimeZone timeZone) {
    return formatSimpleLocalDateTime(instant, timeZone.toZoneId());
  }

  /**
   * Formats an {@link Instant} into a simple local date-time string using the provided {@link ZoneId}.
   *
   * @param instant the instant to format
   * @param zoneId the time zone identifier to use for formatting
   * @return a formatted local date-time string in the "yyyy-MM-dd HH:mm:ss" pattern
   */
  public static String formatSimpleLocalDateTime(Instant instant, ZoneId zoneId) {
    return SIMPLE_DATE_TIME_FORMATTER.format(instant.atZone(zoneId));
  }
}
