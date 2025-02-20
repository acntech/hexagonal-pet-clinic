package no.acntech.hexapetclinic.utils.collections;


import static no.acntech.hexapetclinic.utils.text.TextConstants.EMPTY_STRING;
import static org.apache.commons.lang3.Validate.isTrue;

import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import no.acntech.hexapetclinic.utils.text.TextConstants;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CollectionUtils {

  public static final char DEFAULT_PADDING_CHAR = TextConstants.SPACE_CHAR;
  public static final int DEFAULT_INDENT = 4;
  public static final String DEFAULT_KEY_VALUE_SEPARATOR = ": ";
  public static final List<String> DEFAULT_KEY_PATTERNS = List.of(".*key.*", ".*password$", ".*passwd$", ".*pw$", ".*secret.*");
  public static final String MASKED_PROP_VAL = "*****";

  private static final int INDENT_INCREASE = 2;
  private static final String DEFAULT_LIST_SEPARATOR = ": ";

  // Helper class to store the element and its original index
  private record IndexedElement<T>(int index, T element) {

  }

  public static Map<String, String> toCensoredCopy(@NonNull Properties props) {
    return toCensoredCopy(Maps.newHashMap(Maps.fromProperties(props)), DEFAULT_KEY_PATTERNS, MASKED_PROP_VAL);
  }

  public static Map<String, String> toCensoredCopy(@NonNull Map<String, String> map) {
    return toCensoredCopy(map, DEFAULT_KEY_PATTERNS, MASKED_PROP_VAL);
  }

  public static Map<String, String> toCensoredCopy(@NonNull Map<String, String> map,
      @NonNull List<String> keyPatterns,
      @NonNull String mask) {
    return map.entrySet().stream().collect(Collectors.toMap(Entry::getKey, e -> maskSensitiveKeys(e, keyPatterns, mask)));
  }

  private static String maskSensitiveKeys(Entry<String, String> entry, List<String> keyPatterns, String mask) {
    String key = entry.getKey().toLowerCase(Locale.ROOT);
    String value = entry.getValue();
    for (String pattern : keyPatterns) {
      if (key.matches(pattern)) {
        return mask;
      }
    }
    return value;
  }

  public static String prettyPrint(Map<?, ?> map) {
    return prettyPrint(map, true, DEFAULT_INDENT, DEFAULT_PADDING_CHAR, DEFAULT_KEY_VALUE_SEPARATOR);
  }

  public static String prettyPrint(Map<?, ?> map, int indent) {
    return prettyPrint(map, true, indent, DEFAULT_PADDING_CHAR, DEFAULT_KEY_VALUE_SEPARATOR);
  }

  public static String prettyPrint(@NonNull Map<?, ?> map, boolean sortKeys, int indent, char paddingChar, String keyValueSeparator) {
    isTrue(indent >= 0, "Indent must be non-negative");
    keyValueSeparator = keyValueSeparator != null ? keyValueSeparator : DEFAULT_KEY_VALUE_SEPARATOR;

    List<Entry<?, ?>> effectiveMap = sortKeys ? sortMapEntries(map) : new ArrayList<>(map.entrySet());
    if (effectiveMap.isEmpty()) {
      return EMPTY_STRING;
    }

    int longestKeyLength = getLongestKeyLength(effectiveMap);
    return formatEntries(effectiveMap, sortKeys, indent, paddingChar, keyValueSeparator, longestKeyLength);
  }

  public static String prettyPrint(@NonNull List<?> list) {
    return prettyPrint(list, false, DEFAULT_INDENT, DEFAULT_PADDING_CHAR);
  }

  public static String prettyPrint(@NonNull List<?> list, int indent) {
    return prettyPrint(list, false, indent, DEFAULT_PADDING_CHAR);
  }

  public static <T> String prettyPrint(@NonNull List<T> list, boolean sortItems, int indent, char paddingChar) {
    isTrue(indent >= 0, "Indent must be non-negative");

    List<IndexedElement<T>> indexedList = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
      indexedList.add(new IndexedElement<>(i, list.get(i))); // Store orig_content index and element
    }

    // Sort the list if required
    if (sortItems) {
      indexedList.sort(Comparator.comparing(a -> a.element.toString()));
    }

    // If the list is empty, return an empty string
    if (indexedList.isEmpty()) {
      return EMPTY_STRING;
    }

    StringBuilder sb = new StringBuilder();
    String indentString = String.valueOf(paddingChar).repeat(indent);

    for (int i = 0; i < indexedList.size(); i++) {
      IndexedElement<T> indexedElement = indexedList.get(i);
      int origIndex = indexedElement.index + 1; // Use 1-based index for orig_content element
      T element = indexedElement.element;
      sb.append(indentString)
          .append(i + 1) // Current iteration index (1-based)
          .append(DEFAULT_LIST_SEPARATOR)
          .append(element)
          .append(" (orig index: ")
          .append(origIndex)
          .append(")") // Include orig index if sorted
          .append(System.lineSeparator()); // New line for each element
    }

    return sb.toString();
  }

  private static List<Entry<?, ?>> sortMapEntries(Map<?, ?> map) {
    return map.entrySet()
        .stream()
        .sorted(Comparator.comparing(e -> e.getKey() != null ? e.getKey().toString() : EMPTY_STRING))
        .collect(Collectors.toList());
  }

  private static int getLongestKeyLength(List<Entry<?, ?>> effectiveMap) {
    return effectiveMap.stream().mapToInt(entry -> entry.getKey() != null ? entry.getKey().toString().length() : 0).max().orElse(0);
  }

  private static String formatEntries(List<Entry<?, ?>> mapEntries,
      boolean sortKeys,
      int indent,
      char paddingChar,
      String keyValueSeparator,
      int longestKeyLength) {
    String padding = String.valueOf(paddingChar).repeat(indent);
    return mapEntries.stream()
        .map(entry -> formatEntry(entry, sortKeys, indent, paddingChar, keyValueSeparator, longestKeyLength, padding))
        .collect(Collectors.joining(System.lineSeparator()));
  }

  private static String formatEntry(Entry<?, ?> entry,
      boolean sortKeys,
      int indent,
      char paddingChar,
      String keyValueSeparator,
      int longestKeyLength,
      String padding) {
    String keyString = entry.getKey() != null ? entry.getKey().toString() : TextConstants.NULL;
    String keyPadding = TextConstants.SPACE.repeat(longestKeyLength - keyString.length());
    String valueString = formatValue(entry.getValue(), sortKeys, indent, paddingChar, keyValueSeparator);
    return padding + keyString + keyPadding + keyValueSeparator + valueString;
  }

  private static String formatValue(Object value, boolean sortKeys, int indent, char paddingChar, String keyValueSeparator) {
    if (value instanceof Map) {
      return TextConstants.LF + prettyPrint((Map<?, ?>) value, sortKeys, indent * INDENT_INCREASE, paddingChar, keyValueSeparator);
    }
    return value != null ? value.toString() : TextConstants.NULL;
  }

  public static Map<String, String> transformMap(Map<String, String> map, Function<String, String> transformer) {
    return map.entrySet().stream().collect(Collectors.toMap(e -> transformer.apply(e.getKey()), e -> transformer.apply(e.getValue())));
  }
}
