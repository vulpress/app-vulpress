package hu.aestallon.giannitsa.app.domain.category;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentCategoryRepository extends CrudRepository<ContentCategory, Long> {

  @Query("select c.* from content_category c where c.public_vis is true")
  Iterable<ContentCategory> findPublicCategories();

}
