package hu.aestallon.giannitsa.app.rest.impl;

import hu.aestallon.giannitsa.app.rest.api.ViewApiDelegate;
import hu.aestallon.giannitsa.app.rest.model.AppBarModel;
import hu.aestallon.giannitsa.app.rest.model.UiAction;
import hu.aestallon.giannitsa.app.view.distributor.ViewServiceDistributor;
import hu.aestallon.giannitsa.app.view.impl.AppBarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ViewApiDelegateImpl implements ViewApiDelegate {

  private final ViewServiceDistributor viewServiceDistributor;
  private final AppBarService          appBarService;

  @Override
  public ResponseEntity<List<UiAction>> getActions(String viewName) {
    try {
      return ResponseEntity.ok(viewServiceDistributor.actions(viewName));
    } catch (ViewServiceDistributor.UnknownViewNameException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @Override
  public ResponseEntity<AppBarModel> getAppBar() {
    return ResponseEntity.ok(appBarService.model());
  }

}
