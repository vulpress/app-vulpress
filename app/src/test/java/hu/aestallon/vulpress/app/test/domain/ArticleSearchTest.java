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

package hu.aestallon.vulpress.app.test.domain;

import hu.aestallon.vulpress.app.config.BusinessLogicTest;
import hu.aestallon.vulpress.app.config.DirtyTest;
import hu.aestallon.vulpress.app.domain.article.ArticleService;
import hu.aestallon.vulpress.app.domain.category.ContentCategory;
import hu.aestallon.vulpress.app.domain.category.ContentCategoryRepository;
import hu.aestallon.vulpress.app.rest.model.ArticleDetail;
import hu.aestallon.vulpress.app.rest.model.ArticlePreview;
import hu.aestallon.vulpress.app.rest.model.Paragraph;
import hu.aestallon.vulpress.app.test.util.Users;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@BusinessLogicTest
class ArticleSearchTest {

  private static final LocalDate YESTERDAY = LocalDate.now().minusDays(1L);
  private static final LocalDate TOMORROW  = LocalDate.now().plusDays(1L);

  private static final ArticleDetail PUBLISHED_1  = new ArticleDetail()
      .title("First Title")
      .issueDate(YESTERDAY)
      .author("me")
      .paragraphs(Stream.of("a", "b", "c", "d", "e", "f")
          .map(s -> new Paragraph().text(s))
          .toList());
  private static final ArticleDetail PUBLISHED_2  = new ArticleDetail()
      .title("Second Title")
      .issueDate(YESTERDAY)
      .author("me")
      .paragraphs(Stream.of("evx", "fvx", "gvx", "hvx", "ivx", "gvx")
          .map(s -> new Paragraph().text(s))
          .toList());
  private static final ArticleDetail TO_PUBLISH_1 = new ArticleDetail()
      .title("Third Title")
      .issueDate(TOMORROW)
      .author("me")
      .paragraphs(Stream.of("a", "qq", "ww")
          .map(s -> new Paragraph().text(s))
          .toList());

  private static final List<ArticleDetail> TEST_ARTICLES = List.of(
      PUBLISHED_1,
      PUBLISHED_2,
      TO_PUBLISH_1);

  @Autowired
  ArticleService            articleService;
  @Autowired
  ContentCategoryRepository contentCategoryRepository;
  @Autowired
  Clock                     clock;

  @BeforeEach
  void beforeEach() {
    final long publicCategoryId = contentCategoryRepository.common().id();
    TEST_ARTICLES.forEach(a -> articleService.save(a, publicCategoryId, "description"));
  }

  @DirtyTest
  void adminFindsAllArticles_whenSearchingForSharedTitleText() {
    Users.asAdmin();

    List<ArticlePreview> articles = articleService.find("title");
    assertThat(articles).hasSize(3);
    // they are ordered from most recent (even in the future) to oldest:
    assertThat(articles.get(0).getIssueDate()).isEqualTo(TOMORROW);
    assertThat(articles.get(1).getIssueDate()).isEqualTo(YESTERDAY);
    assertThat(articles.get(2).getIssueDate()).isEqualTo(YESTERDAY);
  }

  @DirtyTest
  void adminFindsFutureArticle_whenSearchingForFutureOnlyTitleText() {
    Users.asAdmin();

    List<ArticlePreview> articles = articleService.find("third");
    assertThat(articles).hasSize(1);
    assertThat(articles.get(0).getIssueDate()).isEqualTo(TOMORROW);
  }

  @DirtyTest
  void adminFindsNoArticle_whenSearchingForBogusText() {
    Users.asAdmin();
    assertThat(articleService.find("bogus-textus")).isEmpty();
  }

  @DirtyTest
  void adminFindsBothFutureAndPastArticles_whenSearchingForCommonText() {
    Users.asAdmin();

    List<ArticlePreview> articles = articleService.find("a");
    assertThat(articles).hasSize(2);
    // they are ordered from most recent (even in the future) to oldest:
    assertThat(articles.get(0).getIssueDate()).isEqualTo(TOMORROW);
    assertThat(articles.get(1).getIssueDate()).isEqualTo(YESTERDAY);
  }

  @DirtyTest
  void adminFindsBothArticles_whenSearchingForCommonText_notInFirstParagraph() {
    Users.asAdmin();

    List<ArticlePreview> articles = articleService.find("f");
    assertThat(articles).hasSize(2);
    // they are ordered from most recent (even in the future) to oldest:
    assertThat(articles.get(0).getIssueDate()).isEqualTo(YESTERDAY);
    assertThat(articles.get(1).getIssueDate()).isEqualTo(YESTERDAY);
  }

  @DirtyTest
  void plainUserOnlyFindsPastArticles_whenSearchingForCommonTitleText() {
    Users.asPlain();

    List<ArticlePreview> articles = articleService.find("title");
    assertThat(articles).hasSize(2);
    // they are ordered from most recent (even in the future) to oldest:
    assertThat(articles.get(0).getIssueDate()).isEqualTo(YESTERDAY);
    assertThat(articles.get(1).getIssueDate()).isEqualTo(YESTERDAY);
  }

  @DirtyTest
  void plainUserFindsArticle_whenSearchesForBodyText() {
    Users.asPlain();

    List<ArticlePreview> articles = articleService.find("vx");
    assertThat(articles).hasSize(1);
    assertThat(articles.get(0))
        .returns(YESTERDAY, ArticlePreview::getIssueDate)
        .returns("Second Title", ArticlePreview::getTitle);
  }

  @DirtyTest
  void extraArticleAddedToSandboxIsSearchableForAdmin_butNotForOthers() {
    Users.asAdmin();

    final ArticleDetail article = new ArticleDetail()
        .title("Fourth Title")
        .issueDate(YESTERDAY)
        .author("me")
        .paragraphs(Stream.of("ivx")
            .map(s -> new Paragraph().text(s))
            .toList());
    final ContentCategory sandbox = contentCategoryRepository
        .findByNormalisedTitle("sys_sandbox")
        .orElseThrow();
    articleService.save(article, sandbox.id(), "Custom description");

    List<ArticlePreview> articles = articleService.find("ivx");
    assertThat(articles).hasSize(2);
    assertThat(articles.stream().map(ArticlePreview::getTitle))
        .contains("Second Title", "Fourth Title");

    Users.asPlain();
    articles = articleService.find("ivx");
    assertThat(articles).hasSize(1);
    assertThat(articles.stream().map(ArticlePreview::getTitle))
        .containsExactly("Second Title");

  }

}
