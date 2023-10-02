package hu.aestallon.vulpress.app.domain.article;

import hu.aestallon.vulpress.app.domain.category.ContentCategory;
import hu.aestallon.vulpress.app.rest.model.ArticleDetail;
import hu.aestallon.vulpress.app.rest.model.ArticlePreview;

import java.util.List;

public interface ArticleService {

  ArticleDetail save(ArticleDetail articleDetail, ContentCategory category, String description);

  List<ArticlePreview> find(String queryStr);

}
