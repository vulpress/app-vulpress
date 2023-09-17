package hu.aestallon.giannitsa.app.view.impl;

import hu.aestallon.giannitsa.app.auth.UserService;
import hu.aestallon.giannitsa.app.domain.category.ContentCategoryService;
import hu.aestallon.giannitsa.app.rest.model.UiAction;
import hu.aestallon.giannitsa.app.view.ViewNames;
import hu.aestallon.giannitsa.app.view.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;


@Service
@RequiredArgsConstructor
public class CategoryViewService implements ViewService {

  private static final Supplier<UiAction> UPLOAD = () -> new UiAction()
      .code("upload-article")
      .title("Upload");
  private static final Supplier<UiAction> DELETE = () -> new UiAction()
      .code("delete-category")
      .title("Delete");

  private final UserService userService;

  @Override
  public String name() {
    return ViewNames.CATEGORY;
  }

  @Override
  public List<UiAction> actions(String identifier) {
    if (!userService.isCurrentUserAdmin()) {
      return Collections.emptyList();
    }

    if (identifier != null && ContentCategoryService.BUILT_IN_CATEGORIES.contains(identifier)) {
      return List.of(UPLOAD.get(), DELETE.get().disabled(true));
    }
    return List.of(UPLOAD.get(), DELETE.get());
  }


}
