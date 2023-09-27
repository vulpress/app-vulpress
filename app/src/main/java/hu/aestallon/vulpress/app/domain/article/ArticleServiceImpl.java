package hu.aestallon.vulpress.app.domain.article;

import hu.aestallon.vulpress.app.auth.UserService;
import hu.aestallon.vulpress.app.domain.util.StringNormaliser;
import hu.aestallon.vulpress.app.rest.model.ArticleDetail;
import hu.aestallon.vulpress.app.rest.model.ArticlePreview;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

  private static final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);

  private final ArticleRepository articleRepository;
  private final UserService       userService;
  private final Clock             clock;

  @Override
  public ArticleDetail save(ArticleDetail articleDetail, long category, String description) {
    Article article = new Article(
        StringNormaliser.normalise(articleDetail.getTitle()),
        articleDetail.getTitle(),
        (articleDetail.getAuthor() == null)
            ? AggregateReference.to(userService.currentUser().id())
            : null,
        (articleDetail.getAuthor() == null || articleDetail.getAuthor().isBlank())
            ? userService.currentUser().username()
            : articleDetail.getAuthor(),
        AggregateReference.to(category),
        description,
        LocalDateTime.now(clock),
        (articleDetail.getIssueDate() == null)
            ? LocalDate.now()
            : articleDetail.getIssueDate(),
        articleDetail.getParagraphs().stream()
            .map(p -> new Article.Paragraph(
                Article.ParagraphType.TEXT,
                p.getText(),
                null))
            .toList(),
        Collections.emptySet());

    article = articleRepository.save(article);
    return article.toDetail();
  }

  @Override
  public List<ArticlePreview> find(String queryStr) {
    return Streamable
        .of(userService.isCurrentUserAdmin()
            ? articleRepository.textSearch(queryStr)
            : articleRepository.textSearchPublic(queryStr))
        .stream()
        .map(Article::toPreview)
        .toList();
  }

}
