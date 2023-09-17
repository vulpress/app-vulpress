package hu.aestallon.giannitsa.app.view.impl;

import hu.aestallon.giannitsa.app.auth.UserService;
import hu.aestallon.giannitsa.app.domain.category.ContentCategoryService;
import hu.aestallon.giannitsa.app.rest.model.AppBarModel;
import hu.aestallon.giannitsa.app.rest.model.UiAction;
import hu.aestallon.giannitsa.app.view.ViewNames;
import hu.aestallon.giannitsa.app.view.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppBarService implements ViewService {

  @Value("${app.giannitsa.name:app giannitsa}")
  private String appName;

  private final UserService            userService;
  private final ContentCategoryService contentCategoryService;

  @Override
  public String name() {
    return ViewNames.APP_BAR;
  }

  @Override
  public List<UiAction> actions(String identifier) {
    return contentCategoryService.getCategories()
        .map(c -> new UiAction()
            .code(c.getCode())
            .title(c.getTitle()))
        .toList();
  }

  public AppBarModel model() {
    return new AppBarModel()
        .appName(appName)
        .loggedIn(userService.currentUser() != null)
        .availableCategories(actions(null));
  }

}
