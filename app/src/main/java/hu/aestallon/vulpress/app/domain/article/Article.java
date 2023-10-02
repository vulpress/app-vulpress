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

package hu.aestallon.vulpress.app.domain.article;

import hu.aestallon.vulpress.app.auth.User;
import hu.aestallon.vulpress.app.domain.category.ContentCategory;
import hu.aestallon.vulpress.app.rest.model.ArticleDetail;
import hu.aestallon.vulpress.app.rest.model.ArticlePreview;
import hu.aestallon.vulpress.app.rest.model.Paragraph;
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
    @Column("norm_title") String normalisedTitle,
    @Column("title") String title,
    @Column("author_id") AggregateReference<User, Long> author,
    @Column("author_name") String authorName,

    @Column("content_category") AggregateReference<ContentCategory, Long> contentCategory,
    @Column("description") String description,
    @Column("created_at") LocalDateTime createdAt,
    @Column("issue_date") LocalDate issueDate,
    @Column("published") boolean published,
    List<Paragraph> paragraphs,
    Set<ArticleTag> tags) {
  public Article(String normalisedTitle, String title, AggregateReference<User, Long> author,
                 String authorName, AggregateReference<ContentCategory, Long> contentCategory,
                 String description, LocalDateTime createdAt, LocalDate issueDate,
                 boolean published, List<Paragraph> paragraphs, Set<ArticleTag> tags) {
    this(null, normalisedTitle, title, author, authorName, contentCategory, description, createdAt,
        issueDate, published, paragraphs, tags);
  }

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

  public ArticleDetail toDetail() {
    return new ArticleDetail()
        .code(normalisedTitle())
        .title(title())
        .author(authorName())
        .issueDate(issueDate())
        .paragraphs(paragraphs().stream()
            .filter(p -> Article.ParagraphType.TEXT == p.type())
            .map(p -> new hu.aestallon.vulpress.app.rest.model.Paragraph().text(p.content()))
            .toList());
  }

  public ArticlePreview toPreview() {
    return new ArticlePreview()
        .code(normalisedTitle)
        .title(title)
        .author(authorName)
        .issueDate(issueDate)
        .description(description);
  }

}
