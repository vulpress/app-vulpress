package hu.aestallon.vulpress.app.domain.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;

public final class StringNormaliser {

  private static final char[] ROMANISED_GREEK_LETTERS = { // 945 - 969
      'a', 'b', 'g', 'd', 'e', 'z', 'h', 'u', 'i', 'k', 'l', 'm', 'n',
      'j', 'o', 'p', 'r', 'w', 's', 't', 'y', 'f', 'x', 'c', 'v'
  };

  /**
   * Normalises a string, by setting it to lowercase, replacing whitespaces with hyphens, and
   * replacing fancy characters with their URL safe alternatives.
   *
   * @param s the {@code String} to normalise, not null
   *
   * @return the normalised {@code String}
   */
  public static String normalise(String s) {
    String temp = s.toLowerCase().replaceAll("\\s+", "-");
    if (!Normalizer.isNormalized(temp, Normalizer.Form.NFKC)) {
      temp = Normalizer.normalize(temp, Normalizer.Form.NFKC);
      temp = temp.replaceAll("\\p{M}", "");
    }
    char[] arr = temp.toCharArray();
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] >= 'α' && arr[i] <= 'ω') {
        arr[i] = ROMANISED_GREEK_LETTERS[arr[i] - 'α'];
      }
    }
    return new String(arr);
  }

  private StringNormaliser() {}

}
