package no.acntech.hexapetclinic.utils.log;

import static lombok.AccessLevel.PRIVATE;
import static no.acntech.hexapetclinic.utils.text.TextConstants.DASH;
import static no.acntech.hexapetclinic.utils.text.TextConstants.EMPTY_STRING;
import static no.acntech.hexapetclinic.utils.text.TextConstants.NEW_LINE;
import static no.acntech.hexapetclinic.utils.text.TextConstants.PIPE;
import static no.acntech.hexapetclinic.utils.text.TextConstants.SPACE;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.event.Level;

@NoArgsConstructor(access = PRIVATE)
public final class LogUtils {

  private final static int DEFAULT_FRAME_PADDING_SIZE = 5;

  public static void log(@NonNull Logger logger, Level level, String message) {
    log(logger, level, message, (Object[]) null);
  }

  @SuppressWarnings("AnnotateFormatMethod")
  public static void log(@NonNull Logger logger, Level level, String message, Object... args) {
    if (level == null) {
      level = Level.DEBUG;
    }

    if (!logger.isEnabledForLevel(level)) {
      return;
    }

    if (message == null) {
      message = EMPTY_STRING;
    }

    Throwable error = extractThrowable(args);
    if (args != null && args.length > 0) {
      message = String.format(message, args);
    }

    logMessage(logger, level, message, error);
  }

  private static Throwable extractThrowable(Object[] args) {
    if (args != null && args.length > 0 && args[args.length - 1] instanceof Throwable) {
      return (Throwable) args[args.length - 1];
    }
    return null;
  }

  public static void logFramedMessage(Logger logger, String message) {
    logFramedMessage(logger, Level.INFO, message, DEFAULT_FRAME_PADDING_SIZE);
  }

  @SuppressWarnings("StringSplitter")
  public static void logFramedMessage(Logger logger, Level level, String message, int paddingSize) {
    String padding = SPACE.repeat(paddingSize);

    // Add padding to each line and frame it
    String[] lines = message.split(NEW_LINE);
    StringBuilder framedMessage = new StringBuilder();
    for (String line : lines) {
      framedMessage.append(PIPE).append(padding).append(line).append(padding).append(PIPE).append(System.lineSeparator());
    }

    // Calculate border length dynamically
    int borderLength = framedMessage.toString().lines().findFirst().orElse(EMPTY_STRING).length();
    String border = DASH.repeat(borderLength);

    // Combine border and framed content
    String result = border + System.lineSeparator() + framedMessage + border;

    log(logger, level, result);
  }

  private static void logMessage(Logger logger, Level level, String message, Throwable error) {
    switch (level) {
      case TRACE -> logAtLevel(logger::trace, message, error);
      case DEBUG -> logAtLevel(logger::debug, message, error);
      case INFO -> logAtLevel(logger::info, message, error);
      case WARN -> logAtLevel(logger::warn, message, error);
      case ERROR -> logAtLevel(logger::error, message, error);
      default -> throw new IllegalArgumentException("Unsupported log level: " + level);
    }
  }

  private static void logAtLevel(LogAction logAction, String message, Throwable error) {
    if (error != null) {
      logAction.log(message, error);
    } else {
      logAction.log(message);
    }
  }

  @FunctionalInterface
  private interface LogAction {

    void log(String message, Throwable... error);
  }
}
