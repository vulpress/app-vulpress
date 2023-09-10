package hu.aestallon.giannitsa.app.rest.impl;

import hu.aestallon.giannitsa.app.rest.api.ArticlesApiDelegate;
import hu.aestallon.giannitsa.app.rest.model.ArticleDetail;
import org.springframework.http.ResponseEntity;

public class ArticlesApiDelegateImpl implements ArticlesApiDelegate {

  @Override
  public ResponseEntity<ArticleDetail> getArticle(String article) {
    return ArticlesApiDelegate.super.getArticle(article);
  }

}
