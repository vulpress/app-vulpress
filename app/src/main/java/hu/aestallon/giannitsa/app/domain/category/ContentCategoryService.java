package hu.aestallon.giannitsa.app.domain.category;

import hu.aestallon.giannitsa.app.domain.ConstraintViolationException;
import hu.aestallon.giannitsa.app.domain.ForbiddenOperationException;
import hu.aestallon.giannitsa.app.rest.model.ArticleDetail;
import hu.aestallon.giannitsa.app.rest.model.ArticlePreview;
import hu.aestallon.giannitsa.app.rest.model.Category;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;


public interface ContentCategoryService {

  String SANDBOX  = "sys_sandbox";
  String ARCHIVE  = "sys_archive";
  String HOMILIES = "homilies";
  String ARTICLES = "articles";

  /**
   * Returns a {@code Stream} of categories available to the current user.
   *
   * @return a {@code Stream} of categories which the current user has at least read rights
   */
  Stream<Category> getCategories();

  /**
   * Creates a new category.
   *
   * @param category the {@link Category} to save, not null
   *
   * @return the saved {@link Category}
   *
   * @throws IllegalArgumentException     if the category is improperly initialised
   * @throws ConstraintViolationException if the normalised title for the category is already taken
   * @throws ForbiddenOperationException  if the current user has no rights to save a new category
   */
  Category create(Category category);


  default void delete(Category category) {
    delete(category.getCode());
  }

  /**
   * Deletes a category.
   *
   * <p>
   * With the erasure of the category, all articles it previously contained will be moved to the
   * archive category.
   *
   * <p>
   * This is an idempotent operation: deleting a non-existent category will yield no extensions or
   * other side effects.
   *
   * @param categoryCode the {@code String} unique identifier of a given {@link Category}, not null
   *
   * @throws ForbiddenOperationException if the current user has no rights to delete a category
   */
  void delete(String categoryCode);

  default List<ArticlePreview> articlesOf(Category category) {
    return articlesOf(category.getCode());
  }

  /**
   * Returns the articles contained in the specified category.
   *
   * <p>
   * The provided {@code categoryCode} is a {@link ContentCategory#normalisedTitle()} uniquely
   * identifying a given category in persistent storage.
   *
   * @param categoryCode the {@code String} unique identifier of a given {@link Category}, not null
   *
   * @return the articles of the requested category in {@link ArticlePreview} form
   *
   * @throws ForbiddenOperationException if the current user has no rights to request the contents
   *                                     of the provided category
   */
  List<ArticlePreview> articlesOf(String categoryCode);

  /**
   * Uploads an article in the specified category.
   *
   * @param categoryCode  the {@code String} unique identifier of a given {@link Category}; if null,
   *                      the upload will target the {@link #SANDBOX} category
   * @param articleDetail the {@link ArticleDetail} containing metadata of the upload, not null
   * @param description   the optional {@code String} description of the article
   * @param content       the {@link InputStream} containing the document file the article is
   *                      extracted from, not null
   *
   * @return the persisted {@link ArticleDetail} assembled from the provided data
   *
   * @throws ConstraintViolationException if the desired title for the article is already taken
   * @throws ForbiddenOperationException  if the current user has no rights to upload an article
   */
  ArticleDetail uploadArticle(String categoryCode, ArticleDetail articleDetail, String description,
                              InputStream content);

  ArticleDetail getArticle(String articleCode);

  void deleteArticle(String articleCode);

  void moveArticle(String articleCode, String targetCategory);

}
