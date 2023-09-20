package hu.aestallon.giannitsa.app.test.domain;

import hu.aestallon.giannitsa.app.config.BusinessLogicTest;
import hu.aestallon.giannitsa.app.domain.ConstraintViolationException;
import hu.aestallon.giannitsa.app.domain.ForbiddenOperationException;
import hu.aestallon.giannitsa.app.domain.article.Article;
import hu.aestallon.giannitsa.app.domain.article.ArticleService;
import hu.aestallon.giannitsa.app.domain.category.ContentCategory;
import hu.aestallon.giannitsa.app.domain.category.ContentCategoryRepository;
import hu.aestallon.giannitsa.app.domain.category.ContentCategoryService;
import hu.aestallon.giannitsa.app.rest.model.ArticleDetail;
import hu.aestallon.giannitsa.app.rest.model.ArticlePreview;
import hu.aestallon.giannitsa.app.rest.model.Category;
import hu.aestallon.giannitsa.app.rest.model.Paragraph;
import hu.aestallon.giannitsa.app.test.util.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@BusinessLogicTest
class ArticleMoveTest {

  @Autowired
  ArticleService            articleService;
  @Autowired
  ContentCategoryService    contentCategoryService;
  @Autowired
  ContentCategoryRepository contentCategoryRepository;


  @Test
  @DirtiesContext
  @DisplayName("Deleting a category removes the category and moves its contents to the archive.")
  void deletingContentsOfAnEntireCategoryWorks() {
    Users.asAdmin();

    final Category c = contentCategoryService.create(new Category().title("My Test Category"));
    final String categoryCode = c.getCode();
    assertThat(categoryCode).isNotNull().isNotEmpty();
    assertThat(contentCategoryService.articlesOf(categoryCode)).isEmpty();

    final ContentCategory contentCategory = contentCategoryRepository
        .findByNormalisedTitle(categoryCode)
        .orElseThrow();
    assertThat(contentCategory)
        .returns(true, ContentCategory::publiclyVisible)
        .returns(false, ContentCategory::builtIn);
    final long categoryId = contentCategory.id();
    ArticleDetail a1 = articleService.save(
        new ArticleDetail()
            .title("Test article 1")
            .addParagraphsItem(new Paragraph().text("some text")),
        categoryId,
        "Some description");
    ArticleDetail a2 = articleService.save(
        new ArticleDetail()
            .title("Test article 2")
            .addParagraphsItem(new Paragraph().text("some text")),
        categoryId,
        "Some description");

    var sandBoxArticles = contentCategoryService.articlesOf(categoryCode);
    assertThat(sandBoxArticles).hasSize(2);
    assertThat(sandBoxArticles.stream().map(ArticlePreview::getCode))
        .containsExactly("test-article-1", "test-article-2");

    contentCategoryService.delete(categoryCode);
    // category is built in, thus cannot be deleted:
    assertThat(contentCategoryRepository.existsByNormalisedTitle(categoryCode))
        .isFalse();
    // all its contents are moved to the archive:
    assertThat(contentCategoryService.articlesOf(categoryCode)).isEmpty();
    assertThat(contentCategoryService.articlesOf(ContentCategoryService.ARCHIVE)).hasSize(2);
  }

  @Test
  @DisplayName("Categories marked as BUILT_IN cannot be deleted.")
  void attemptingToDeleteBuiltInCategory_failsWithAnException() {
    Users.asAdmin();

    assertThrows(ForbiddenOperationException.class, () -> contentCategoryService.delete(
        ContentCategoryService.SANDBOX));
  }

  @Test
  @DirtiesContext
  @DisplayName("Moving an article between categories succeeds as administrator.")
  void movingAnArticleFromOneCategoryToAnother_isSuccessful() {
    Users.asAdmin();

    final Category c = contentCategoryService.create(new Category().title("My Test Category"));
    final String categoryCode = c.getCode();
    assertThat(categoryCode).isNotNull().isNotEmpty();
    assertThat(contentCategoryService.articlesOf(categoryCode)).isEmpty();
    assertThat(contentCategoryService.articlesOf(ContentCategoryService.SANDBOX)).isEmpty();

    final ContentCategory contentCategory = contentCategoryRepository
        .findByNormalisedTitle(categoryCode)
        .orElseThrow();
    assertThat(contentCategory)
        .returns(true, ContentCategory::publiclyVisible)
        .returns(false, ContentCategory::builtIn);

    final long categoryId = contentCategory.id();
    ArticleDetail a1 = articleService.save(
        new ArticleDetail()
            .title("Test article 1")
            .addParagraphsItem(new Paragraph().text("some text")),
        categoryId,
        "Some description");
    assertThat(contentCategoryService.articlesOf(categoryCode)).hasSize(1);

    contentCategoryService.moveArticle(a1.getCode(), ContentCategoryService.SANDBOX);
    assertThat(contentCategoryService.articlesOf(categoryCode)).isEmpty();
    assertThat(contentCategoryService.articlesOf(ContentCategoryService.SANDBOX)).hasSize(1);
  }

  @Test
  @DirtiesContext
  @DisplayName("Administrators can delete empty categories.")
  void administratorCreatingANewCategory_thenDeletingIt_succeeds() {
    Users.asAdmin();

    final Category category = contentCategoryService.create(new Category()
        .title("Example Category")
        .description("None"));
    assertThat(category).isNotNull();
    assertThat(category.getCode()).isEqualTo("example-category");
    assertThat(contentCategoryRepository.findByNormalisedTitle("example-category")).isPresent();

    contentCategoryService.delete("example-category");
  }

  @Test
  @DirtiesContext
  @DisplayName("Plain users cannot delete categories.")
  void plainUserAttemptingToDelete_notBuiltInCategory_fails() {
    Users.asAdmin();

    final Category category = contentCategoryService.create(new Category()
        .title("Example Category")
        .description("None"));
    assertThat(category).isNotNull();
    assertThat(category.getCode()).isEqualTo("example-category");
    assertThat(contentCategoryRepository.findByNormalisedTitle("example-category")).isPresent();

    Users.asPlain();

    assertThrows(
        ForbiddenOperationException.class,
        () -> contentCategoryService.delete("example-category"));
  }

  @Test
  @DirtiesContext
  @DisplayName("Anonymous users cannot delete categories.")
  void anonymousUserAttemptingToDelete_notBuiltInCategory_fails() {
    Users.asAdmin();

    final Category category = contentCategoryService.create(new Category()
        .title("Example Category")
        .description("None"));
    assertThat(category).isNotNull();
    assertThat(category.getCode()).isEqualTo("example-category");
    assertThat(contentCategoryRepository.findByNormalisedTitle("example-category")).isPresent();

    Users.asAnonymous();

    assertThrows(
        ForbiddenOperationException.class,
        () -> contentCategoryService.delete("example-category"));
  }

  @Test
  @DirtiesContext
  @DisplayName("Plain users cannot move articles.")
  void plainUserAttemptingToMoveArticleBetweenCategories_fails() {
    createTwoCategoriesAndAnExampleArticle();

    Users.asPlain();
    assertThrows(
        ForbiddenOperationException.class,
        () -> contentCategoryService.moveArticle("article-alpha", "example-category-2"));
  }

  private void createTwoCategoriesAndAnExampleArticle() {
    Users.asAdmin();

    final Category category1 = contentCategoryService.create(new Category()
        .title("Example Category 1")
        .description("None"));
    final Category category2 = contentCategoryService.create(new Category()
        .title("Example Category 2")
        .description("None"));

    final long c1 = contentCategoryRepository
        .findByNormalisedTitle("example-category-1")
        .get().id();
    final long c2 = contentCategoryRepository
        .findByNormalisedTitle("example-category-2")
        .get().id();

    final ArticleDetail a = articleService.save(
        new ArticleDetail()
            .title("Article Alpha")
            .addParagraphsItem(new Paragraph().text("None")),
        c1,
        "No description");
  }

  @Test
  @DirtiesContext
  @DisplayName("Anonymous users cannot move articles.")
  void anonymousUserAttemptingToMoveArticleBetweenCategories_fails() {
    createTwoCategoriesAndAnExampleArticle();

    Users.asAnonymous();
    assertThrows(
        ForbiddenOperationException.class,
        () -> contentCategoryService.moveArticle("article-alpha", "example-category-2"));
  }

  @Test
  @DirtiesContext
  @DisplayName("Targeting non-existent category with a move fails.")
  void administratorAttemptingToMoveArticleToUnknownCategory_fails() {
    createTwoCategoriesAndAnExampleArticle();
    assertThrows(
        ConstraintViolationException.class,
        () -> contentCategoryService.moveArticle("article-alpha", "example-category-3"));
  }

  @Test
  @DirtiesContext
  @DisplayName("Targeting non-existent category with a move fails.")
  void administratorAttemptingToMoveUnknownArticle_fails() {
    createTwoCategoriesAndAnExampleArticle();
    assertThrows(
        ConstraintViolationException.class,
        () -> contentCategoryService.moveArticle("article-beta", "example-category-2"));
  }


}
