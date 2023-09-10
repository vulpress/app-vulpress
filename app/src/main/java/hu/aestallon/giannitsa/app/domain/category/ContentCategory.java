package hu.aestallon.giannitsa.app.domain.category;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("content_category")
public record ContentCategory(
    @Id Long id,
    @Column("title") String title,
    @Column("norm_title") String normalisedTitle,
    @Column("description") String description,
    @Column("built_in") boolean builtIn,
    @Column("public_vis") boolean publiclyVisible,
    @Column("image") UUID imageUuid,
    @Column("created_at") LocalDateTime createdAt
) {
  public ContentCategory {
    description = (description == null) ? "" : description;
    createdAt = (createdAt == null) ? LocalDateTime.now() : createdAt;
  }

  public ContentCategory(String title, String normalisedTitle, String description, boolean builtIn,
                         boolean publiclyVisible, UUID imageUuid, LocalDateTime createdAt) {
    this(null, title, normalisedTitle, description, builtIn, publiclyVisible, imageUuid, createdAt);
  }
}
