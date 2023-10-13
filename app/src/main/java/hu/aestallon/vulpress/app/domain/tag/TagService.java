package hu.aestallon.vulpress.app.domain.tag;

import java.util.Collection;
import java.util.Optional;

public interface TagService {

  void create(String tag);

  void create(String tag, Collection<String> aliases);

  Optional<Tag> tagFor(String tag);

  Tag addAliases(String tag, Collection<String> aliases);

}
