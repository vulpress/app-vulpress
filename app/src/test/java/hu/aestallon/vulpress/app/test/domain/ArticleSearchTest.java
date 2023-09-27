package hu.aestallon.vulpress.app.test.domain;

import hu.aestallon.vulpress.app.config.BusinessLogicTest;
import hu.aestallon.vulpress.app.config.DirtyTest;
import hu.aestallon.vulpress.app.domain.article.ArticleService;
import hu.aestallon.vulpress.app.domain.category.ContentCategoryRepository;
import hu.aestallon.vulpress.app.rest.model.ArticleDetail;
import hu.aestallon.vulpress.app.rest.model.ArticlePreview;
import hu.aestallon.vulpress.app.rest.model.Paragraph;
import hu.aestallon.vulpress.app.test.util.Dates;
import hu.aestallon.vulpress.app.test.util.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Clock;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@BusinessLogicTest
class ArticleSearchTest {

  private static final ArticleDetail PUBLISHED_1 = new ArticleDetail()
      .title("First Title")
      .issueDate(Dates.YESTERDAY)
      .author("me")
      .paragraphs(Stream.of("a", "b", "c", "d", "e", "f")
          .map(s -> new Paragraph().text(s))
          .toList());
  private static final ArticleDetail PUBLISHED_2 = new ArticleDetail()
      .title("Second Title")
      .issueDate(Dates.YESTERDAY)
      .author("me")
      .paragraphs(Stream.of("evx", "fvx", "gvx", "hvx", "ivx", "gvx")
          .map(s -> new Paragraph().text(s))
          .toList());
  private static final ArticleDetail TO_PUBLISH_1 = new ArticleDetail()
      .title("Third Title")
      .issueDate(Dates.TOMORROW)
      .author("me")
      .paragraphs(Stream.of("a", "qq", "ww")
          .map(s -> new Paragraph().text(s))
          .toList());

  private static final List<ArticleDetail> TEST_ARTICLES = List.of(
      PUBLISHED_1,
      PUBLISHED_2,
      TO_PUBLISH_1);

  @Autowired
  ArticleService articleService;
  @Autowired
  ContentCategoryRepository contentCategoryRepository;
  @Autowired
  Clock clock;

  @BeforeEach
  void beforeEach() {
    final long publicCategoryId = contentCategoryRepository.common().id();
    TEST_ARTICLES.forEach(a -> articleService.save(a, publicCategoryId, "description"));
  }

  @DirtyTest
  void clockTest() {
    assertThat(clock.instant()).isEqualTo(Dates.TEST_INST);
    assertThat(LocalDate.now(clock)).isEqualTo(Dates.TODAY);
  }

  @DirtyTest
  void adminFindsAllArticles_whenSearchingForSharedTitleText() {
    Users.asAdmin();

    List<ArticlePreview> articles = articleService.find("title");
    assertThat(articles).hasSize(3);
    // they are ordered from most recent (even in the future) to oldest:
    assertThat(articles.get(0).getIssueDate()).isEqualTo(Dates.TOMORROW);
    assertThat(articles.get(1).getIssueDate()).isEqualTo(Dates.YESTERDAY);
    assertThat(articles.get(2).getIssueDate()).isEqualTo(Dates.YESTERDAY);
  }

  @DirtyTest
  void adminFindsFutureArticle_whenSearchingForFutureOnlyTitleText() {
    Users.asAdmin();

    List<ArticlePreview> articles = articleService.find("third");
    assertThat(articles).hasSize(1);
    assertThat(articles.get(0).getIssueDate()).isEqualTo(Dates.TOMORROW);
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
    assertThat(articles.get(0).getIssueDate()).isEqualTo(Dates.TOMORROW);
    assertThat(articles.get(1).getIssueDate()).isEqualTo(Dates.YESTERDAY);
  }

  @DirtyTest
  void plainUserOnlyFindsPastArticles_whenSearchingForCommonTitleText() {
    Users.asPlain();

    List<ArticlePreview> articles = articleService.find("title");
    assertThat(articles).hasSize(2);
    // they are ordered from most recent (even in the future) to oldest:
    assertThat(articles.get(0).getIssueDate()).isEqualTo(Dates.YESTERDAY);
    assertThat(articles.get(1).getIssueDate()).isEqualTo(Dates.YESTERDAY);
  }

  @DirtyTest
  void plainUserFindsArticle_whenSearchesForBodyText() {
    Users.asPlain();

    List<ArticlePreview> articles = articleService.find("vx");
    assertThat(articles).hasSize(1);
    assertThat(articles.get(0))
        .returns(Dates.YESTERDAY, ArticlePreview::getIssueDate)
        .returns("Second Title", ArticlePreview::getTitle);
  }

}
