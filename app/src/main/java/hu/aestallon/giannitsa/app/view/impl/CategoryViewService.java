package hu.aestallon.giannitsa.app.view.impl;

import hu.aestallon.giannitsa.app.auth.UserService;
import hu.aestallon.giannitsa.app.rest.model.UiAction;
import hu.aestallon.giannitsa.app.view.ViewNames;
import hu.aestallon.giannitsa.app.view.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryViewService implements ViewService {

  private final UserService userService;

  @Override
  public String name() {
    return ViewNames.CATEGORY;
  }

  @Override
  public List<UiAction> actions() {
    return (userService.isCurrentUserAdmin())
        ? List.of(
        new UiAction()
            .code("upload-article")
            .title("Upload"),
        new UiAction()
            .code("delete-category")
            .title("Delete"))
        : Collections.emptyList();
  }

}
