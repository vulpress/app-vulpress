package hu.aestallon.giannitsa.app.domain.category;

import hu.aestallon.giannitsa.app.auth.UserService;
import hu.aestallon.giannitsa.app.domain.article.ArticleService;
import hu.aestallon.giannitsa.app.rest.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.StreamUtils;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ContentCategoryService {

  private final UserService               userService;
  private final ArticleService            articleService;
  private final ContentCategoryRepository contentCategoryRepository;

  public Stream<Category> getCategories() {
    return userService.isCurrentUserAdmin()
        ? Streamable.of(contentCategoryRepository.findAll()).stream().map(this::dto)
        : Streamable.of(contentCategoryRepository.findPublicCategories()).stream().map(this::dto);
  }

  private Category dto(ContentCategory contentCategory) {
    return new Category()
        .code(contentCategory.normalisedTitle())
        .title(contentCategory.title())
        .description(contentCategory.description());
  }
}
