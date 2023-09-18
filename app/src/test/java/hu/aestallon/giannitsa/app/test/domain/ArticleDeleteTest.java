package hu.aestallon.giannitsa.app.test.domain;

import hu.aestallon.giannitsa.app.config.BusinessLogicTest;
import hu.aestallon.giannitsa.app.config.DirtyTest;
import hu.aestallon.giannitsa.app.domain.ForbiddenOperationException;
import hu.aestallon.giannitsa.app.domain.article.ArticleService;
import hu.aestallon.giannitsa.app.domain.category.ContentCategoryRepository;
import hu.aestallon.giannitsa.app.domain.category.ContentCategoryService;
import hu.aestallon.giannitsa.app.domain.util.StringNormaliser;
import hu.aestallon.giannitsa.app.rest.model.ArticleDetail;
import hu.aestallon.giannitsa.app.rest.model.Category;
import hu.aestallon.giannitsa.app.rest.model.Paragraph;
import hu.aestallon.giannitsa.app.test.util.Users;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@BusinessLogicTest
class ArticleDeleteTest {

  @Autowired
  ArticleService            articleService;
  @Autowired
  ContentCategoryService    contentCategoryService;
  @Autowired
  ContentCategoryRepository contentCategoryRepository;

  private String createCategoryAndAnExampleArticle(String categoryTitle, String articleTitle) {
    Users.asAdmin();

    contentCategoryService.create(new Category()
        .title(categoryTitle)
        .description("None"));

    final long c1 =
        contentCategoryRepository
            .findByNormalisedTitle(StringNormaliser.normalise(categoryTitle))
            .get().id();

    final ArticleDetail a = articleService.save(
        new ArticleDetail().title(articleTitle).addParagraphsItem(new Paragraph().text("None")), c1,
        "No description");
    return a.getCode();
  }

  @DirtyTest
  @DisplayName("Administrator can delete an article and it appears in the archive afterwards.")
  void administratorDeletingAnArticle_succeeds() {
    final String articleCode =
        createCategoryAndAnExampleArticle("Example Category", "Example Article");

    contentCategoryService.deleteArticle(articleCode);
    assertThat(contentCategoryRepository.existsByNormalisedTitle("example-category")).isTrue();
    assertThat(contentCategoryService.articlesOf("example-category")).isEmpty();
    assertThat(contentCategoryService.articlesOf(ContentCategoryService.ARCHIVE))
        .hasSize(1)
        .allSatisfy(a -> assertThat(a.getCode()).isEqualTo(articleCode));
  }

  @DirtyTest
  @DisplayName("Plain users cannot delete articles.")
  void plainUserCannotDeleteArticles() {
    final String articleCode =
        createCategoryAndAnExampleArticle("Example Category", "Example Article");

    Users.asPlain();
    assertThrows(
        ForbiddenOperationException.class,
        () -> contentCategoryService.deleteArticle(articleCode));
  }

  @DirtyTest
  @DisplayName("Anonymous users cannot delete articles.")
  void anonymousUserCannotDeleteArticles() {
    final String articleCode =
        createCategoryAndAnExampleArticle("Example Category", "Example Article");

    Users.asAnonymous();
    assertThrows(
        ForbiddenOperationException.class,
        () -> contentCategoryService.deleteArticle(articleCode));
  }
}
