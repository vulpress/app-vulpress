package hu.aestallon.giannitsa.app.rest.impl;

import hu.aestallon.giannitsa.app.rest.api.CategoriesApiDelegate;
import hu.aestallon.giannitsa.app.rest.model.ArticlePreview;
import hu.aestallon.giannitsa.app.rest.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public class CategoriesApiDelegateImpl implements CategoriesApiDelegate {
  @Override
  public ResponseEntity<Category> createCategory(String body) {
    return CategoriesApiDelegate.super.createCategory(body);
  }

  @Override
  public ResponseEntity<Void> deleteCategory(String category) {
    return CategoriesApiDelegate.super.deleteCategory(category);
  }

  @Override
  public ResponseEntity<List<ArticlePreview>> getArticles(String category, Integer page) {
    return CategoriesApiDelegate.super.getArticles(category, page);
  }

  @Override
  public ResponseEntity<List<ArticlePreview>> getRecentArticles(String category) {
    return CategoriesApiDelegate.super.getRecentArticles(category);
  }

  @Override
  public ResponseEntity<List<Category>> listCategories() {
    return CategoriesApiDelegate.super.listCategories();
  }

  @Override
  public ResponseEntity<Void> uploadArticle(String category, String title, LocalDate issued,
                                            String author, String description,
                                            MultipartFile documentFile) {
    return CategoriesApiDelegate.super.uploadArticle(category, title, issued, author, description,
        documentFile);
  }
}
