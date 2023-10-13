package hu.aestallon.vulpress.app.domain.tag;

import hu.aestallon.vulpress.app.domain.util.StringNormaliser;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Table("tag_entry")
public record Tag(
    @Id @Column("tag_name") String tagName,
    Set<Alias> aliases) {

  public static Tag create(final String tagName) {
    Objects.requireNonNull(tagName, "tagName cannot be null!");
    if (tagName.isBlank()) {
      throw new IllegalArgumentException("tagName cannot be empty or blank!");
    }

    final var normTagName = StringNormaliser.normalise(tagName);
    final var aliases = new HashSet<Alias>();
    aliases.add(Alias.primary(tagName));

    return new Tag(normTagName, aliases);
  }


  @Table("tag_alias")
  public record Alias(@Column("tag_value") String tagAlias,
                      @Column("is_primary") boolean isPrimary) {

    public Alias {
      Objects.requireNonNull(tagAlias, "alias cannot be null!");
    }

    public static Alias primary(String alias) {
      return new Alias(alias, true);
    }

    public static Alias secondary(String alias) {
      return new Alias(alias, false);
    }

  }

}
