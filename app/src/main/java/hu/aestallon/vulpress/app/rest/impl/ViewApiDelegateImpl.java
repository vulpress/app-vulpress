package hu.aestallon.vulpress.app.rest.impl;

import hu.aestallon.vulpress.app.rest.api.ViewApiDelegate;
import hu.aestallon.vulpress.app.rest.model.AppBarModel;
import hu.aestallon.vulpress.app.rest.model.UiAction;
import hu.aestallon.vulpress.app.view.distributor.ViewServiceDistributor;
import hu.aestallon.vulpress.app.view.impl.AppBarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ViewApiDelegateImpl implements ViewApiDelegate {

  private final ViewServiceDistributor viewServiceDistributor;
  private final AppBarService          appBarService;

  @Override
  public ResponseEntity<List<UiAction>> getActions(String viewName, Optional<String> pageName) {
    try {
      return ResponseEntity.ok(viewServiceDistributor.actions(viewName, pageName.orElse(null)));
    } catch (ViewServiceDistributor.UnknownViewNameException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @Override
  public ResponseEntity<AppBarModel> getAppBar() {
    return ResponseEntity.ok(appBarService.model());
  }

}
