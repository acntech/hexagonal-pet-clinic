package no.acntech.hexapetclinic.utils.text;

import static no.acntech.hexapetclinic.utils.text.TextConstants.ASTERISK;
import static no.acntech.hexapetclinic.utils.text.TextConstants.COMMA;
import static no.acntech.hexapetclinic.utils.text.TextConstants.EMPTY_STRING;

import java.util.AbstractMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;

/**
 * Utility class for performing common string operations. This class cannot be instantiated and all its methods are static.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtils {

  public static final char DEFAULT_PADDING_CHAR = TextConstants.SPACE_CHAR;

  private static final String ELLIPSIS = "...";
  private static final int MIN_MASK = 2;
  private static final int MAX_MASK = 10;

  // Decorate System Properties and
  // Environment Variables into a single Map
  private static final Map<String, String> COMBINED_SYSTEM_ENV_MAP = new AbstractMap<String, String>() {

    @Override
    public String get(Object key) {
      // Check system properties first
      String value = System.getProperty(key.toString());
      if (value != null) {
        return value;
      }
      // Fallback to environment variables
      return System.getenv(key.toString());
    }

    @Override
    public boolean containsKey(Object key) {
      return System.getProperties().containsKey(key.toString()) || System.getenv().containsKey(key.toString());
    }

    @Override
    public @NonNull Set<Entry<String, String>> entrySet() {
      throw new UnsupportedOperationException("entrySet not supported");
    }
  };

  private static final StringSubstitutor SYSTEM_ENV_SUBSTITUTOR =
      new StringSubstitutor(COMBINED_SYSTEM_ENV_MAP).setEnableUndefinedVariableException(false); // I

  /**
   * Generates a padding string of the specified size using the default padding character.
   *
   * @param size the number of padding characters to include in the returned string.
   * @return a string of the specified size filled with the default padding character, or an empty string if the specified size is less than
   * or equal to 0.
   */
  public static String getPadding(int size) {
    return getPadding(size, DEFAULT_PADDING_CHAR);
  }

  /**
   * Generates a string of a specified size, filled with a given character.
   *
   * @param size        The size of the resulting string. If the size is 0 or negative, an empty string is returned.
   * @param paddingChar The character to be repeated in the resulting string.
   * @return A string of the specified size, filled with the specified character.
   */
  public static String getPadding(int size, char paddingChar) {
    if (size <= 0) {
      return EMPTY_STRING; // Return an empty string if size is 0 or negative
    }
    return String.valueOf(paddingChar).repeat(size);
  }

  public static String stripLeadingSlash(String path) {
    if (path == null || path.isEmpty()) {
      return path; // Return as-is if null or empty
    }
    int index = 0;
    while (index < path.length() && path.charAt(index) == '/') {
      index++;
    }
    return path.substring(index);
  }

  /**
   * Removes the specified prefix from the beginning of the given string if it starts with that prefix. If the string does not start with
   * the specified prefix or if either the string or the prefix is null, the original string is returned.
   *
   * @param str    the input string from which the prefix should be removed
   * @param prefix the prefix to remove from the beginning of the string
   * @return the string after removing the prefix, or the original string if the prefix is not present or input is null
   */
  public static String chopPrefix(String str, String prefix) {
    return (str != null && prefix != null && str.startsWith(prefix)) ? str.substring(prefix.length()) : str;
  }

  /**
   * Removes the specified suffix from the given string if the string ends with the suffix. If the string does not end with the suffix or
   * either input is null, the original string is returned.
   *
   * @param str    the input string from which the suffix is to be removed
   * @param suffix the suffix to remove from the string
   * @return the string with the suffix removed if it ends with the suffix, otherwise the original string
   */
  public static String chopSuffix(String str, String suffix) {
    return (str != null && suffix != null && str.endsWith(suffix)) ? str.substring(0, str.length() - suffix.length()) : str;
  }

  /**
   * Truncates a given string to the specified length without additional options.
   *
   * @param text   the string to be truncated
   * @param length the maximum length of the truncated string
   * @return a truncated version of the specified string
   */
  public static String truncate(String text, int length) {
    return truncate(text, length, false, false);
  }

  /**
   * Truncates the specified text to the desired length, optionally adding an ellipsis to the beginning or end.
   *
   * @param text           the text to be truncated
   * @param length         the desired length of the resulting string
   * @param sliceFromStart if true, truncates from the start of the text; otherwise, truncates from the end
   * @param ellipsis       if true, appends or prepends an ellipsis to indicate the text has been truncated
   * @return the truncated string, possibly including an ellipsis
   */
  public static String truncate(String text, int length, boolean sliceFromStart, boolean ellipsis) {
    if (text == null || length <= 0) {
      return EMPTY_STRING;
    }

    String result = text;
    String placeholder = EMPTY_STRING;
    int placeLength = 0;

    if (ellipsis) {
      placeholder = ELLIPSIS;
      placeLength = placeholder.length();
    }

    if (text.length() > length) {
      int newLength = Math.max(1, length - placeLength); // Ensure valid range
      if (sliceFromStart) {
        result = placeholder + text.substring(Math.max(0, text.length() - newLength));
      } else {
        result = text.substring(0, newLength) + placeholder;
      }
    }
    return result;
  }

  /**
   * Masks a given string by replacing its middle part with asterisks (*). The resulting masked string comprises the first and last
   * characters of the original string, with up to {@code MAX_MASK} asterisks in between. If the string's length is less than or equal to
   * {@code MIN_MASK}, it returns the original string unchanged.
   *
   * @param str the string to be masked
   * @return the masked string
   */
  public static String maskString(String str) {
    String result;

    if (str == null) {
      result = null;

    } else if (str.length() <= MIN_MASK) {
      result = str;

    } else {
      // Determine how many characters to mask, but no more than MAX_MASK
      int maskLength = Math.min(MAX_MASK, str.length() - MIN_MASK);

      // Mask the middle part and construct the masked string
      result = str.charAt(0) + ASTERISK.repeat(maskLength) + str.charAt(str.length() - 1);
    }

    return result;
  }


  /**
   * Capitalizes the first character of the provided word.
   *
   * @param word the word to be capitalized. If the word is null or empty, it returns the word as is.
   * @return a new string with the first character in uppercase and the rest of the characters unchanged.
   */
  public static String capitalize(String word) {
    return (word == null || word.isEmpty()) ? word : word.substring(0, 1).toUpperCase(Locale.ROOT) + word.substring(1);
  }

  /**
   * Interpolates the given string by substituting placeholders with values from system properties and environment variables. If the input
   * string contains placeholders in the form ${key}, the method will attempt to replace them by first checking system properties and then
   * environment variables.
   *
   * @param value the input string containing placeholders to be resolved; may be null. If null, the method returns null.
   * @return the interpolated string with placeholders replaced by their corresponding values, or the original string if no placeholders are
   * found or resolvable.
   */
  public static String interpolate(String value) {
    return interpolate(value, Map.of(), true);
  }

  /**
   * Interpolates the given value by replacing variable placeholders with corresponding values from the provided parameters map. Optionally,
   * it can also use system properties and system environment variables as fallback for undefined placeholders.
   *
   * @param value                             the string containing variable placeholders to be interpolated; can be null.
   * @param params                            a map of parameter keys and values used for replacing placeholders in the string.
   * @param fallbackToSystemPropsAndSystemEnv a boolean that determines whether to use system properties and system environment variables as
   *                                          fallback for undefined placeholders.
   * @return the result of interpolation, or null if the input value is null.
   */
  public static String interpolate(String value, @NonNull Map<String, Object> params, boolean fallbackToSystemPropsAndSystemEnv) {
    if (value == null) {
      return null;
    }
    StringSubstitutor substitutor = new StringSubstitutor(params).setEnableUndefinedVariableException(false);
    String result = substitutor.replace(value);
    if (fallbackToSystemPropsAndSystemEnv) {
      result = SYSTEM_ENV_SUBSTITUTOR.replace(result);
    }
    return result;
  }

  public static List<String> splitAndTrim(String input) {
    return splitAndTrim(input, COMMA);
  }

  /**
   * Splits the input string based on the specified regular expression, trims each resulting substring, and filters out any empty resulting
   * strings. If the input string is null or blank, an empty list is returned. If the splitRegex is null or blank, the entire trimmed input
   * string is returned as a single-element list.
   *
   * @param input      the string to be split and trimmed; can be null or blank
   * @param splitRegex the regular expression to split the string; can be null or blank
   * @return a list of non-empty, trimmed substrings resulting from splitting the input string
   */
  public static List<String> splitAndTrim(String input, String splitRegex) {
    List<String> result;

    if (input == null || input.isBlank()) {
      result = List.of();
    } else if (splitRegex == null || splitRegex.isBlank()) {
      result = List.of(input.trim());
    } else {
      result = Stream.of(input.split(splitRegex)) // Split the input string
          .map(String::trim) // Trim each part
          .filter(part -> !part.isEmpty()) // Remove empty strings
          .collect(Collectors.toList());
    }

    return result;
  }


}
