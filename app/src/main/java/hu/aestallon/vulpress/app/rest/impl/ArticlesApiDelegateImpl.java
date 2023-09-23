package hu.aestallon.vulpress.app.rest.impl;

import hu.aestallon.vulpress.app.domain.category.ContentCategoryService;
import hu.aestallon.vulpress.app.rest.api.ArticlesApiDelegate;
import hu.aestallon.vulpress.app.rest.model.ArticleDetail;
import hu.aestallon.vulpress.app.rest.model.ArticleMoveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticlesApiDelegateImpl implements ArticlesApiDelegate {

  private final ContentCategoryService contentCategoryService;

  @Override
  public ResponseEntity<ArticleDetail> getArticle(String article) {
    return ResponseEntity.ok(contentCategoryService.getArticle(article));
  }

  @Override
  public ResponseEntity<Void> deleteArticle(String article) {
    contentCategoryService.deleteArticle(article);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<Void> moveArticle(ArticleMoveRequest articleMoveRequest) {
    contentCategoryService.moveArticle(
        articleMoveRequest.getArticle(),
        articleMoveRequest.getTargetCategory());
    return ResponseEntity.ok().build();
  }

}
