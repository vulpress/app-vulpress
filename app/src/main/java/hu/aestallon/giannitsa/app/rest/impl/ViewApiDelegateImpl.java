package hu.aestallon.giannitsa.app.rest.impl;

import hu.aestallon.giannitsa.app.rest.api.ViewApiDelegate;
import hu.aestallon.giannitsa.app.rest.model.AppBarModel;
import hu.aestallon.giannitsa.app.rest.model.UiAction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ViewApiDelegateImpl implements ViewApiDelegate {

  @Override
  public ResponseEntity<List<UiAction>> getActions(String viewName) {
    return ViewApiDelegate.super.getActions(viewName);
  }

  @Override
  public ResponseEntity<AppBarModel> getAppBar() {
    return ViewApiDelegate.super.getAppBar();
  }

}
