package hu.aestallon.giannitsa.app.domain.article;

import hu.aestallon.giannitsa.app.auth.User;
import hu.aestallon.giannitsa.app.auth.UserService;
import hu.aestallon.giannitsa.app.domain.category.ContentCategory;
import hu.aestallon.giannitsa.app.domain.util.StringNormaliser;
import hu.aestallon.giannitsa.app.rest.model.ArticleDetail;
import hu.aestallon.giannitsa.app.rest.model.Paragraph;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class ArticleService {

  private final ArticleRepository articleRepository;
  private final UserService       userService;

  public ArticleDetail save(ArticleDetail articleDetail, long category, String description) {
    Article article = new Article(
        StringNormaliser.normalise(articleDetail.getTitle()),
        articleDetail.getTitle(),
        (articleDetail.getAuthor() == null)
            ? AggregateReference.to(userService.currentUser().id())
            : null,
        (articleDetail.getAuthor() == null)
            ? userService.currentUser().username()
            : articleDetail.getAuthor(),
        AggregateReference.to(category),
        description,
        LocalDateTime.now(),
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

}
