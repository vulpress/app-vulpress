package hu.aestallon.vulpress.app.domain.category;

import hu.aestallon.vulpress.app.auth.UserService;
import hu.aestallon.vulpress.app.domain.ConstraintViolationException;
import hu.aestallon.vulpress.app.domain.ForbiddenOperationException;
import hu.aestallon.vulpress.app.domain.article.Article;
import hu.aestallon.vulpress.app.domain.article.ArticleRepository;
import hu.aestallon.vulpress.app.domain.article.ArticleService;
import hu.aestallon.vulpress.app.domain.util.StringNormaliser;
import hu.aestallon.vulpress.app.event.ArticlePublished;
import hu.aestallon.vulpress.app.rest.model.ArticleDetail;
import hu.aestallon.vulpress.app.rest.model.ArticlePreview;
import hu.aestallon.vulpress.app.rest.model.Category;
import hu.aestallon.vulpress.app.rest.model.Paragraph;
import hu.aestallon.vulpress.docu.importer.DocumentImportResult;
import hu.aestallon.vulpress.docu.importer.DocumentImporter;
import hu.aestallon.vulpress.docu.model.Document;
import hu.aestallon.vulpress.docu.model.Text;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ContentCategoryServiceImpl implements ContentCategoryService {

  private static final Logger log = LoggerFactory.getLogger(ContentCategoryServiceImpl.class);

  private final ApplicationEventPublisher eventPublisher;
  private final UserService               userService;
  private final ArticleService            articleService;
  private final ArticleRepository         articleRepository;
  private final ContentCategoryRepository contentCategoryRepository;
  private final DocumentImporter          wordDocumentImporter;
  private final Clock                     clock;

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
        LocalDateTime.now(clock));
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
    contentCategoryRepository.deleteById(contentCategory.id());
  }


  @Override
  public List<ArticlePreview> articlesOf(String categoryCode) {
    if (!isCategoryPermitted(categoryCode)) {
      throw new ForbiddenOperationException("Cannot show contents of category: " + categoryCode);
    }
    final boolean currentUserAdmin = userService.isCurrentUserAdmin();

    return Streamable
        .of(articleRepository.findArticlesOfCategory(categoryCode)).stream()
        .map(Article::toPreview)
        .sorted(Comparator
            .comparing(ArticlePreview::getIssueDate).reversed()
            .thenComparing(ArticlePreview::getTitle))
        .filter(a -> currentUserAdmin || !a.getIssueDate().isAfter(LocalDate.now(clock)))
        .toList();
  }

  @Override
  public ArticleDetail uploadArticle(String categoryCode, ArticleDetail articleDetail,
                                     String description, InputStream content) {

    if (!userService.isCurrentUserAdmin()) {
      throw new ForbiddenOperationException("non admins cannot upload!");
    }

    final ContentCategory category = contentCategoryRepository
        .findByNormalisedTitle(categoryCode)
        .orElseThrow(() -> new ConstraintViolationException(
            "no category known with [ " + categoryCode + " ] !!!"));

    final DocumentImportResult result = wordDocumentImporter.doImport(content);
    if (result instanceof DocumentImportResult.Ok ok) {
      return articleService.save(
          fromDocument(ok.document(), articleDetail),
          category,
          description);

    } else if (result instanceof DocumentImportResult.Err err) {
      if (err.throwable() != null) {
        throw new IllegalStateException(err.throwable());

      } else {
        throw new ForbiddenOperationException(err.errorCode());

      }

    } else {
      throw new AssertionError();
    }
  }

  @Override
  public ArticleDetail getArticle(String articleCode) {
    final Article article = articleRepository
        .findByNormalisedTitle(Objects.requireNonNull(articleCode))
        .orElseThrow(() -> new IllegalArgumentException(articleCode + " is unknown!"));
    if (article.contentCategory().getId() == null) {
      throw new ConstraintViolationException(articleCode + " is not associated with any category!");
    }

    final boolean categoryUnavailable = contentCategoryRepository
        .findById(article.contentCategory().getId())
        .filter(this::isCategoryPermitted)
        .isEmpty();
    if (categoryUnavailable) {
      throw new ForbiddenOperationException(articleCode + " is not in an available category!");
    }
    if (!userService.isCurrentUserAdmin() && article.issueDate().isAfter(LocalDate.now(clock))) {
      throw new ForbiddenOperationException(
          articleCode + " is not yet published and cannot be viewed by non-admins!");
    }

    return article.toDetail();
  }

  private boolean isCategoryPermitted(String category) {
    return isCategoryPermitted(contentCategoryRepository
        .findByNormalisedTitle(category)
        .orElse(null));
  }

  private boolean isCategoryPermitted(ContentCategory category) {
    return category != null && (category.publiclyVisible() || userService.isCurrentUserAdmin());
  }

  private static ArticleDetail fromDocument(Document document, ArticleDetail articleDetail) {
    final List<Paragraph> paragraphs = document.content().stream()
        .filter(Text.class::isInstance)
        .map(Text.class::cast)
        .map(Text::content)
        .peek(System.out::println)
        .map(s -> new Paragraph().text(s))
        .peek(System.out::println)
        .toList();
    return articleDetail.paragraphs(paragraphs);
  }

  @Override
  public void deleteArticle(String articleCode) {
    if (!userService.isCurrentUserAdmin()) {
      throw new ForbiddenOperationException("Only administrators can delete an article!");
    }

    Objects.requireNonNull(articleCode, "articleCode cannot be null!");

    ContentCategory archive = contentCategoryRepository.archive();
    articleRepository.moveArticle(articleCode, archive.id());
  }

  @Override
  public void moveArticle(String articleCode, String targetCategory) {
    if (!userService.isCurrentUserAdmin()) {
      throw new ForbiddenOperationException("Only administrators can move an article!");
    }

    Objects.requireNonNull(articleCode, "articleCode cannot be null!");
    Objects.requireNonNull(targetCategory, "targetCategory cannot be null!");

    if (!articleRepository.existsByNormalisedTitle(articleCode)) {
      throw new ConstraintViolationException(
          "[ %s ] article does not exist!".formatted(articleCode));
    }
    final ContentCategory target = contentCategoryRepository
        .findByNormalisedTitle(targetCategory)
        .orElseThrow(() -> new ConstraintViolationException(
            "[ %s ] category does not exist!".formatted(targetCategory)));
    articleRepository.moveArticle(articleCode, target.id());
    if (target.publiclyVisible() && !articleRepository.isPublished(articleCode)) {
      articleRepository.publishByNormalisedTitle(articleCode);
      articleRepository
          .findByNormalisedTitle(articleCode)
          .ifPresent(a -> eventPublisher.publishEvent(new ArticlePublished(
              a,
              userService.currentUser())));
    }
  }
}
