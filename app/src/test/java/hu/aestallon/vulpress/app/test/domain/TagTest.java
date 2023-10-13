package hu.aestallon.vulpress.app.test.domain;

import hu.aestallon.vulpress.app.config.BusinessLogicTest;
import hu.aestallon.vulpress.app.config.DirtyTest;
import hu.aestallon.vulpress.app.domain.tag.Tag;
import hu.aestallon.vulpress.app.domain.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@BusinessLogicTest
class TagTest {

  @Autowired
  TagService tagService;

  @DirtyTest
  void savedTagCanBeRetrievedByPrimaryAlias() {
    assertThat(tagService.tagFor("Summer")).isEmpty();

    tagService.create("Summer");
    Tag summer = tagService.tagFor("Summer").get();
    assertThat(summer)
        .returns("summer", Tag::tagName)
        .satisfies(t -> assertThat(t.aliases())
            .hasSize(1)
            .allSatisfy(a -> assertThat(a).isEqualTo(Tag.Alias.primary("Summer"))));
  }

  @DirtyTest
  void savedTagCanBeRetrievedBySecondaryAlias() {
    assertThat(tagService.tagFor("Summer")).isEmpty();

    tagService.create("Summer", List.of("Vacation"));
    Tag summer = tagService.tagFor("Vacation").get();
    assertThat(summer)
        .returns("summer", Tag::tagName);
    assertThat(summer.aliases())
        .hasSize(2)
        .containsExactlyInAnyOrder(
            Tag.Alias.primary("Summer"),
            Tag.Alias.secondary("Vacation"));
  }

  @DirtyTest
  void modifiedTagCanBeFoundByNewAlias() {
    assertThat(tagService.tagFor("Summer")).isEmpty();

    tagService.create("Summer", List.of("Vacation"));
    assertThat(tagService.tagFor("Holidays")).isEmpty();

    tagService.addAliases("summer", List.of("Holidays"));
    assertThat(tagService.tagFor("Holidays")).isPresent();
  }
}
