package hu.aestallon.vulpress.app.view.impl;

import hu.aestallon.vulpress.app.auth.UserService;
import hu.aestallon.vulpress.app.rest.model.UiAction;
import hu.aestallon.vulpress.app.view.ViewNames;
import hu.aestallon.vulpress.app.view.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleViewService implements ViewService {

  private final UserService userService;

  @Override
  public String name() {
    return ViewNames.ARTICLE;
  }

  @Override
  public List<UiAction> actions(String identifier) {
    return (userService.isCurrentUserAdmin())
        ? List.of(new UiAction().code("move-article").title("Move"))
        : Collections.emptyList();
  }

}
