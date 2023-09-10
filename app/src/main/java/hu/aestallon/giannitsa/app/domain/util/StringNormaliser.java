package hu.aestallon.giannitsa.app.domain.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public final class StringNormaliser {

  /**
   * Normalises a string, by setting it to lowercase, replacing whitespaces with hyphens, and
   * replacing fancy characters with their URL safe alternatives.
   *
   * @param s the {@code String} to normalise, not null
   *
   * @return the normalised {@code String}
   */
  public static String normalise(String s) {
    return URLEncoder.encode(s.toLowerCase().replaceAll("\\s", "-"), StandardCharsets.UTF_8);
  }

  private StringNormaliser() {}

}
