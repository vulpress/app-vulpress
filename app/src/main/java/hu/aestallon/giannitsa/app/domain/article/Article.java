/*
 * Copyright 2023 Szabolcs Bazil Papp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.aestallon.giannitsa.app.domain.article;

import hu.aestallon.giannitsa.app.auth.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Table("article")
public record Article(
    @Id Long id,
    @Column("title") String title,
    @Column("norm_title") String normalisedTitle,
    @Column("author_name") String authorName,
    @Column("author_id") AggregateReference<User, Long> author,
    @Column("description") String description,
    @Column("created_at") LocalDateTime createdAt,
    @Column("issue_date") LocalDate issueDate,
    List<Paragraph> paragraphs,
    Set<ArticleTag> tags) {

  public enum ParagraphType {
    TITLE, ILLUSTRATION, TEXT
  }

  @Table("paragraph")
  public record Paragraph(
      @Column("type") ParagraphType type,
      @Column("content") String content,
      @Column("image") UUID imageUuid) {}

  @Table("article_tag")
  public record ArticleTag(@Column("tag_") String tag) {}

  public Article(String normalisedTitle, String title, String authorName,
                 AggregateReference<User, Long> author,
                 String description, LocalDateTime createdAt, LocalDate issueDate,
                 List<Paragraph> paragraphs, Set<ArticleTag> tags) {
    this(null, normalisedTitle, title, authorName, author, description, createdAt, issueDate,
        paragraphs, tags);
  }

}
