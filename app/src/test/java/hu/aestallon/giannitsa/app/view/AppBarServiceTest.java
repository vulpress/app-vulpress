package hu.aestallon.giannitsa.app.view;

import hu.aestallon.giannitsa.app.rest.model.AppBarModel;
import hu.aestallon.giannitsa.app.rest.model.UiAction;
import hu.aestallon.giannitsa.app.config.AnonymousUserTest;
import hu.aestallon.giannitsa.app.view.distributor.ViewServiceDistributor;
import hu.aestallon.giannitsa.app.view.impl.AppBarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AnonymousUserTest
class AppBarServiceTest {

  @Autowired
  ViewServiceDistributor viewServiceDistributor;
  @Autowired
  AppBarService appBarService;

  @Test
  void contextLoads() {

  }

  @Test
  void requestingAppBarActionsForAnonymousUser_shouldReturnPublicCategories() throws Exception {
    final List<UiAction> appBarActions = viewServiceDistributor.actions(ViewNames.APP_BAR);
    assertThat(appBarActions)
        .isNotNull()
        .isNotEmpty();
  }

  @Test
  void requestingAppBarModelForAnonymousUser_returnsNoLoggedInState_publicCategories() {
    AppBarModel model = appBarService.model();
    assertThat(model)
        .isNotNull();
    assertThat(model.getAppName()).isNotNull();
    assertThat(model.getLoggedIn()).isFalse();
    assertThat(model.getAvailableCategories())
        .isNotNull()
        .hasSize(2)
        .contains(
            new UiAction()
                .code("homilies")
                .title("Homilies"),
            new UiAction()
                .code("articles")
                .title("Articles"));
  }
}
