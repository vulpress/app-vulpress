package hu.aestallon.vulpress.app.test.view;

import hu.aestallon.vulpress.app.config.BusinessLogicTest;
import hu.aestallon.vulpress.app.rest.model.AppBarModel;
import hu.aestallon.vulpress.app.rest.model.UiAction;
import hu.aestallon.vulpress.app.test.util.Users;
import hu.aestallon.vulpress.app.view.ViewNames;
import hu.aestallon.vulpress.app.view.distributor.ViewServiceDistributor;
import hu.aestallon.vulpress.app.view.impl.AppBarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@BusinessLogicTest
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
    Users.asAnonymous();

    final List<UiAction> appBarActions = viewServiceDistributor.actions(ViewNames.APP_BAR);
    assertThat(appBarActions)
        .isNotNull()
        .isNotEmpty();
  }

  @Test
  void requestingAppBarModelForAnonymousUser_returnsNoLoggedInState_publicCategories() {
    Users.asAnonymous();

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

  @Test
  void requestingAppBarModelForAdmin_returnsBuiltInCategories_butNotArchive() {
    Users.asAdmin();

    AppBarModel model = appBarService.model();
    assertThat(model)
        .isNotNull();
    assertThat(model.getAppName()).isNotNull();
    assertThat(model.getLoggedIn()).isTrue();
    assertThat(model.getAvailableCategories())
        .isNotNull()
        .hasSize(3)
        .contains(
            new UiAction()
                .code("homilies")
                .title("Homilies"),
            new UiAction()
                .code("articles")
                .title("Articles"),
            new UiAction()
                .code("sys_sandbox")
                .title("Sandbox"));
  }
}
