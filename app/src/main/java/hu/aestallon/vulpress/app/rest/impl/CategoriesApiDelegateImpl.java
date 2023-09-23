package hu.aestallon.vulpress.app.rest.impl;

import hu.aestallon.vulpress.app.domain.category.ContentCategoryService;
import hu.aestallon.vulpress.app.rest.api.CategoriesApiDelegate;
import hu.aestallon.vulpress.app.rest.model.ArticleDetail;
import hu.aestallon.vulpress.app.rest.model.ArticlePreview;
import hu.aestallon.vulpress.app.rest.model.Category;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoriesApiDelegateImpl implements CategoriesApiDelegate {

  private static final Logger LOG = LoggerFactory.getLogger(CategoriesApiDelegateImpl.class);

  private final ContentCategoryService contentCategoryService;

  @Override
  public ResponseEntity<Category> createCategory(Category category) {
    return ResponseEntity.ok(contentCategoryService.create(category));
  }

  @Override
  public ResponseEntity<Void> deleteCategory(String category) {
    contentCategoryService.delete(category);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<List<ArticlePreview>> getArticles(String category, Integer page) {
    // TODO: Handle pagination!
    return ResponseEntity.ok(contentCategoryService.articlesOf(category));
  }

  @Override
  public ResponseEntity<List<ArticlePreview>> getRecentArticles(String category) {
    // TODO: Handle limit!
    return ResponseEntity.ok(contentCategoryService.articlesOf(category));
  }

  @Override
  public ResponseEntity<List<Category>> listCategories() {
    return ResponseEntity.ok(contentCategoryService.getCategories().toList());
  }

  @Override
  public ResponseEntity<ArticleDetail> uploadArticle(String category, String title,
                                                     LocalDate issued,
                                                     String author, String description,
                                                     MultipartFile documentFile) {
    try (final InputStream inputStream = documentFile.getInputStream()) {
      return ResponseEntity.ok(contentCategoryService.uploadArticle(
          category,
          new ArticleDetail()
              .title(title)
              .issueDate(issued)
              .author(author),
          description,
          inputStream));
    } catch (IOException e) {
      LOG.error(e.getMessage(), e);
      return ResponseEntity.internalServerError().build();
    }
  }
}
