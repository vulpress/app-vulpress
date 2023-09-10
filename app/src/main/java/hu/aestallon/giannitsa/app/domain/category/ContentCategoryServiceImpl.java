package hu.aestallon.giannitsa.app.domain.category;

import hu.aestallon.giannitsa.app.auth.UserService;
import hu.aestallon.giannitsa.app.domain.ConstraintViolationException;
import hu.aestallon.giannitsa.app.domain.ForbiddenOperationException;
import hu.aestallon.giannitsa.app.domain.article.ArticleRepository;
import hu.aestallon.giannitsa.app.domain.article.ArticleService;
import hu.aestallon.giannitsa.app.domain.util.StringNormaliser;
import hu.aestallon.giannitsa.app.rest.model.ArticleDetail;
import hu.aestallon.giannitsa.app.rest.model.ArticlePreview;
import hu.aestallon.giannitsa.app.rest.model.Category;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ContentCategoryServiceImpl implements ContentCategoryService {

  private static final Logger LOG = LoggerFactory.getLogger(ContentCategoryServiceImpl.class);

  private final UserService               userService;
  private final ArticleService            articleService;
  private final ArticleRepository         articleRepository;
  private final ContentCategoryRepository contentCategoryRepository;

  @Override
  public Stream<Category> getCategories() {
    return userService.isCurrentUserAdmin()
        ? Streamable.of(contentCategoryRepository.findAll()).stream().map(this::dto)
        : Streamable.of(contentCategoryRepository.findPublicCategories()).stream().map(this::dto);
  }

  private Category dto(ContentCategory contentCategory) {
    return new Category()
        .code(contentCategory.normalisedTitle())
        .title(contentCategory.title())
        .description(contentCategory.description());
  }

  @Override
  public Category create(Category category) {
    if (!userService.isCurrentUserAdmin()) {
      throw new ForbiddenOperationException("Cannot perform operation!");
    }
    Objects.requireNonNull(category, "category cannot be null!");
    if (category.getCode() != null && !category.getCode().isBlank()) {
      throw new IllegalArgumentException("code for new category must not be initialised!");
    }

    final String title = category.getTitle();
    if (title == null || title.isBlank()) {
      throw new IllegalArgumentException("category title cannot be null or empty!");
    }

    final String normalisedTitle = StringNormaliser.normalise(title);
    if (contentCategoryRepository.existsByNormalisedTitle(normalisedTitle)) {
      throw new ConstraintViolationException(
          "category name [ %s ] is already taken!".formatted(normalisedTitle));
    }

    ContentCategory entity = new ContentCategory(title, normalisedTitle,
        category.getDescription(), false, true, null,
        LocalDateTime.now());
    entity = contentCategoryRepository.save(entity);
    return dto(entity);
  }

  private boolean isIdentifierFree(String identifier) {
    return !contentCategoryRepository.existsByNormalisedTitle(identifier);
  }

  @Override
  public void delete(String categoryCode) {
    if (!userService.isCurrentUserAdmin()) {
      throw new ForbiddenOperationException("Cannot perform operation!");
    }

    if (isIdentifierFree(categoryCode)) {
      return;
    }

    final ContentCategory contentCategory = contentCategoryRepository
        .findByNormalisedTitle(categoryCode)
        .get();
    if (contentCategory.builtIn()) {
      throw new ForbiddenOperationException(
          "Cannot delete category [ %s ] for it is built in!".formatted(categoryCode));
    }

    final ContentCategory archive = contentCategoryRepository.archive();
    articleRepository.moveAllArticles(contentCategory.id(), archive.id());
  }


  @Override
  public List<ArticlePreview> articlesOf(String categoryCode) {
    return Streamable
        .of(articleRepository.findArticlesOfCategory(categoryCode)).stream()
        .map(a -> new ArticlePreview()
            .code(a.normalisedTitle())
            .title(a.title())
            .description(a.description()))
        .toList();
  }

  @Override
  public ArticleDetail uploadArticle(String categoryCode, ArticleDetail articleDetail,
                                     String description, InputStream content) {
    return null;
  }
}
