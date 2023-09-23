package hu.aestallon.giannitsa.app.test.view;

import hu.aestallon.giannitsa.app.config.BusinessLogicTest;
import hu.aestallon.giannitsa.app.rest.model.UiAction;
import hu.aestallon.giannitsa.app.test.util.Users;
import hu.aestallon.giannitsa.app.view.ViewNames;
import hu.aestallon.giannitsa.app.view.distributor.ViewServiceDistributor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@BusinessLogicTest
class MainViewTest {

  @Autowired
  ViewServiceDistributor viewServiceDistributor;

  @Test
  void mainViewActionsForAdmin() throws ViewServiceDistributor.UnknownViewNameException {
    Users.asAdmin();

    List<UiAction> actions = viewServiceDistributor.actions(ViewNames.MAIN);
    assertThat(actions)
        .isNotNull()
        .isNotEmpty()
        .containsExactly(
            new UiAction()
                .code("create-category")
                .title("New Category"),
            new UiAction()
                .code("archive")
                .title("Archive"));
  }

  @Test
  void mainViewActionsForNonAdmin() throws ViewServiceDistributor.UnknownViewNameException {
    Users.asAnonymous();
    List<UiAction> actions = viewServiceDistributor.actions(ViewNames.MAIN);
    assertThat(actions)
        .isNotNull()
        .isEmpty();

    Users.asPlain();
    actions = viewServiceDistributor.actions(ViewNames.MAIN);
    assertThat(actions)
        .isNotNull()
        .isEmpty();

  }
}
