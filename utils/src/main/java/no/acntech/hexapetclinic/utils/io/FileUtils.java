package no.acntech.hexapetclinic.utils.io;

import java.io.File;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.util.AntPathMatcher;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileUtils {

  public static final String WINDOWS_FILE_URL_PREFIX = "file:///";

  public static final String FILE_URL_PREFIX = "file://";

  public static final String FILESYSTEM_PREFIX = "filesystem:";

  private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

  public static void deleteDir(@NonNull File directory) {
    deleteDir(directory, true);
  }

  public static void deleteDir(@NonNull File directory, boolean recursive) {
    File[] files = directory.listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.isDirectory() && recursive) {
          deleteDir(file, true);
        } else {
          file.delete();
        }
      }
    }
    directory.delete();
  }

  /**
   * Determines whether the given path is included based on the specified include and exclude path patterns. A path is considered included
   * if it matches any of the include patterns and does not match any of the exclude patterns. Note that an empty list of include patterns
   * will include all paths.
   *
   * @param path         the path to be checked
   * @param includePaths a list of patterns against which the path should be included, may be empty to include all paths
   * @param excludePaths a list of patterns against which the path should be excluded
   * @return true if the path is included according to the include and exclude patterns; false otherwise
   */
  public static boolean isIncluded(@NonNull String path, @NonNull List<String> includePaths, @NonNull List<String> excludePaths) {
    // Check if the path matches any include paths
    boolean matchesIncludePath =
        includePaths.isEmpty() || includePaths.stream().anyMatch(includePath -> PATH_MATCHER.match(includePath, path));

    // Check if the path matches any exclude paths
    boolean matchesExcludePath = excludePaths.stream().anyMatch(excludePath -> PATH_MATCHER.match(excludePath, path));

    // Include if it matches any include paths and does not match any exclude paths
    return matchesIncludePath && !matchesExcludePath;
  }


}
