package hu.aestallon.giannitsa.app.test.domain;

import hu.aestallon.giannitsa.app.config.BusinessLogicTest;
import hu.aestallon.giannitsa.app.domain.ForbiddenOperationException;
import hu.aestallon.giannitsa.app.domain.article.ArticleService;
import hu.aestallon.giannitsa.app.domain.category.ContentCategory;
import hu.aestallon.giannitsa.app.domain.category.ContentCategoryRepository;
import hu.aestallon.giannitsa.app.domain.category.ContentCategoryService;
import hu.aestallon.giannitsa.app.rest.model.ArticleDetail;
import hu.aestallon.giannitsa.app.rest.model.ArticlePreview;
import hu.aestallon.giannitsa.app.rest.model.Category;
import hu.aestallon.giannitsa.app.rest.model.Paragraph;
import hu.aestallon.giannitsa.app.test.util.Users;
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
  void attemptingToDeleteBuiltInCategory_failsWithAnException() {
    assertThrows(ForbiddenOperationException.class, () -> contentCategoryService.delete(
        ContentCategoryService.SANDBOX));
  }

  @Test
  @DirtiesContext
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
}
