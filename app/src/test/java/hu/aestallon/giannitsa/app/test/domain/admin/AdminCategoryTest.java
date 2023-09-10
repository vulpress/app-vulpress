package hu.aestallon.giannitsa.app.test.domain.admin;

import hu.aestallon.giannitsa.app.config.AdminUserTest;
import hu.aestallon.giannitsa.app.domain.category.ContentCategoryService;
import hu.aestallon.giannitsa.app.rest.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@AdminUserTest
class AdminCategoryTest {

  @Autowired
  ContentCategoryService contentCategoryService;

  @Test
  void adminCanSeeAllTheCategories() {
    List<Category> categories = contentCategoryService.getCategories().toList();
    assertThat(categories)
        .isNotNull()
        .hasSize(4);
  }

  @Test
  void whenAdminDeletesANonsenseCategory_noExceptionIsThrown() {
    assertDoesNotThrow(() -> contentCategoryService.delete("goat-sharpener"));
  }

  @Test
  @DirtiesContext
  void adminCanCreateANewCategory() {
    List<Category> categories = contentCategoryService.getCategories().toList();
    assertThat(categories)
        .isNotNull()
        .hasSize(4);

    final Category category = contentCategoryService.create(new Category()
        .title("Custom Category 1")
        .description("My Description"));
    assertThat(category.getCode()).isEqualTo("custom-category-1");

    categories = contentCategoryService.getCategories().toList();
    assertThat(categories)
        .isNotNull()
        .hasSize(5)
        .contains(category);
  }

}
