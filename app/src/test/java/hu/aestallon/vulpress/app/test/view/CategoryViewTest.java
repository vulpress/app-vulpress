package hu.aestallon.vulpress.app.test.view;

import hu.aestallon.vulpress.app.config.BusinessLogicTest;
import hu.aestallon.vulpress.app.domain.category.ContentCategoryService;
import hu.aestallon.vulpress.app.rest.model.UiAction;
import hu.aestallon.vulpress.app.test.util.Users;
import hu.aestallon.vulpress.app.view.ViewNames;
import hu.aestallon.vulpress.app.view.distributor.ViewServiceDistributor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@BusinessLogicTest
class CategoryViewTest {

  @Autowired
  ViewServiceDistributor viewServiceDistributor;

  @Test
  void genericCategoryActionsForAdmin() throws ViewServiceDistributor.UnknownViewNameException {
    Users.asAdmin();

    List<UiAction> actions = viewServiceDistributor.actions(ViewNames.CATEGORY);
    assertThat(actions)
        .isNotNull()
        .isNotEmpty()
        .containsExactly(
            new UiAction()
                .code("upload-article")
                .title("Upload"),
            new UiAction()
                .code("delete-category")
                .title("Delete"));
  }

  @Test
  void builtInCategoryActionsForAdmin() throws ViewServiceDistributor.UnknownViewNameException {
    Users.asAdmin();

    List<UiAction> actions = viewServiceDistributor.actions(
        ViewNames.CATEGORY,
        ContentCategoryService.ARTICLES);
    assertThat(actions)
        .isNotNull()
        .isNotEmpty()
        .containsExactly(
            new UiAction()
                .code("upload-article")
                .title("Upload"),
            new UiAction()
                .code("delete-category")
                .title("Delete")
                .disabled(true));
  }

  @Test
  void categoryActionsForNonAdmins() throws ViewServiceDistributor.UnknownViewNameException {
    Users.asAnonymous();
    List<UiAction> actions = viewServiceDistributor.actions(
        ViewNames.CATEGORY,
        ContentCategoryService.ARTICLES);
    assertThat(actions)
        .isNotNull()
        .isEmpty();

    Users.asPlain();
    actions = viewServiceDistributor.actions(
        ViewNames.CATEGORY,
        ContentCategoryService.ARTICLES);
    assertThat(actions)
        .isNotNull()
        .isEmpty();
  }
}
