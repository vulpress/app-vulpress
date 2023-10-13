package hu.aestallon.vulpress.app.domain.tag;

import hu.aestallon.vulpress.app.domain.ConstraintViolationException;
import hu.aestallon.vulpress.app.domain.util.StringNormaliser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

  private final TagRepository tagRepository;
  private final JdbcAggregateTemplate template;

  @Override
  public void create(String tag) {
    if (tagRepository.existsById(StringNormaliser.normalise(tag))) {
      throw new ConstraintViolationException("tag [" + tag + "] is already taken!");
    }

    final Tag t = Tag.create(tag);
    template.insert(t);
  }

  @Override
  public void create(String tag, Collection<String> aliases) {
    if (tagRepository.existsById(StringNormaliser.normalise(tag))) {
      throw new ConstraintViolationException("tag [" + tag + "] is already taken!");
    }

    final Tag t = Tag.create(tag);
    aliases.stream().map(Tag.Alias::secondary).forEach(t.aliases()::add);
    template.insert(t);
  }

  @Override
  public Optional<Tag> tagFor(String tag) {
    return Streamable.of(tagRepository.findAll()).stream()
        .filter(t -> t.aliases().stream().map(Tag.Alias::tagAlias).anyMatch(tag::equalsIgnoreCase))
        .findFirst();
  }

  @Override
  public Tag addAliases(String tag, Collection<String> aliases) {
    final Tag t = tagFor(tag).orElseThrow();
    aliases.stream().map(Tag.Alias::secondary).forEach(t.aliases()::add);
    return tagRepository.save(t);
  }

  private Map<String, Set<String>> aliasesByTag() {
    return Streamable.of(tagRepository.findAll()).stream()
        .collect(toMap(
            Tag::tagName,
            t -> t.aliases().stream().map(Tag.Alias::tagAlias).collect(toSet())
        ));
  }

}
