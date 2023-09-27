package hu.aestallon.vulpress.app.domain.article;

import hu.aestallon.vulpress.app.rest.model.ArticleDetail;
import hu.aestallon.vulpress.app.rest.model.ArticlePreview;

import java.util.List;

public interface ArticleService {

  ArticleDetail save(ArticleDetail articleDetail, long category, String description);

  List<ArticlePreview> find(String queryStr);

}
