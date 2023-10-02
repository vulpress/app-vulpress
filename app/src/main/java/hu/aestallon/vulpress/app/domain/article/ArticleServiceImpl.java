package hu.aestallon.vulpress.app.domain.article;

import hu.aestallon.vulpress.app.auth.User;
import hu.aestallon.vulpress.app.auth.UserService;
import hu.aestallon.vulpress.app.domain.category.ContentCategory;
import hu.aestallon.vulpress.app.domain.category.ContentCategoryRepository;
import hu.aestallon.vulpress.app.domain.util.StringNormaliser;
import hu.aestallon.vulpress.app.event.ArticleCreated;
import hu.aestallon.vulpress.app.event.ArticlePublished;
import hu.aestallon.vulpress.app.rest.model.ArticleDetail;
import hu.aestallon.vulpress.app.rest.model.ArticlePreview;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

  private static final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);

  private final ApplicationEventPublisher eventPublisher;
  private final ArticleRepository         articleRepository;
  private final ContentCategoryRepository contentCategoryRepository;
  private final UserService               userService;
  private final Clock                     clock;

  @Override
  public ArticleDetail save(ArticleDetail articleDetail, ContentCategory category, String description) {
    final var today = LocalDate.now(clock);
    final var issueDate = (articleDetail.getIssueDate() == null)
        ? today
        : articleDetail.getIssueDate();
    final var published = category.publiclyVisible() && !issueDate.isAfter(today);

    Article article = new Article(
        StringNormaliser.normalise(articleDetail.getTitle()),
        articleDetail.getTitle(),
        (articleDetail.getAuthor() == null)
            ? AggregateReference.to(userService.currentUser().id())
            : null,
        (articleDetail.getAuthor() == null || articleDetail.getAuthor().isBlank())
            ? userService.currentUser().username()
            : articleDetail.getAuthor(),
        AggregateReference.to(category.id()),
        description,
        LocalDateTime.now(clock),
        issueDate,
        published,
        articleDetail.getParagraphs().stream()
            .map(p -> new Article.Paragraph(
                Article.ParagraphType.TEXT,
                p.getText(),
                null))
            .toList(),
        Collections.emptySet());

    article = articleRepository.save(article);
    fireArticleCreatedEvents(article, published);
    return article.toDetail();
  }

  private void fireArticleCreatedEvents(Article article, boolean published) {
    final User user = userService.currentUser();
    eventPublisher.publishEvent(new ArticleCreated(article, user));
    if (published) {
      eventPublisher.publishEvent(new ArticlePublished(article, user));
    }
  }

  @Override
  public List<ArticlePreview> find(String queryStr) {
    final var result = Streamable
        .of(userService.isCurrentUserAdmin()
            ? articleRepository.textSearch(queryStr)
            : articleRepository.textSearchPublic(queryStr))
        .stream()
        .toList();
    if (result.isEmpty()) {
      return Collections.emptyList();
    }

    final var categoriesById = Streamable
        .of(contentCategoryRepository.findAll())
        .stream()
        .collect(Collectors.toMap(ContentCategory::id, ContentCategory::normalisedTitle));
    return result.stream()
        .map(a -> a.toPreview().path(
            "/" + categoriesById.get(a.contentCategory().getId())
            + "/" + a.normalisedTitle()))
        .toList();
  }

}
