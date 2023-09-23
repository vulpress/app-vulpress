package hu.aestallon.vulpress.app.domain.category;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContentCategoryRepository extends CrudRepository<ContentCategory, Long> {

  @Override
  @Query("select c.* from content_category c where c.norm_title <> 'sys_archive'")
  @NonNull Iterable<ContentCategory> findAll();

  @Query("select c.* from content_category c where c.public_vis is true")
  Iterable<ContentCategory> findPublicCategories();

  Optional<ContentCategory> findByNormalisedTitle(String normalisedTitle);

  boolean existsByNormalisedTitle(String normalisedTitle);

  @Query("select c.* from content_category c where c.norm_title = 'sys_archive' limit 1")
  ContentCategory archive();

}
