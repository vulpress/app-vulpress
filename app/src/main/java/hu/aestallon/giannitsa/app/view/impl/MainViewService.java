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
public class MainViewService implements ViewService {

  private final UserService userService;

  @Override
  public String name() {
    return ViewNames.MAIN;
  }

  @Override
  public List<UiAction> actions(String identifier) {
    return (userService.isCurrentUserAdmin())
        ? List.of(
        new UiAction())
        : Collections.emptyList();
  }
}
