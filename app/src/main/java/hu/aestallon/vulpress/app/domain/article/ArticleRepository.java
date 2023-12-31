/*
 * Copyright 2023 Szabolcs Bazil Papp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.aestallon.vulpress.app.domain.article;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {

  @Query("select distinct at.tag_ from article_tag at")
  Set<String> findAllTags();

  @Query( // TODO: Do not select paragraphs!
      """
          select a.* from article a 
          join content_category c on c.id = a.content_category 
          where c.norm_title = :category""")
  Iterable<Article> findArticlesOfCategory(String category);

  @Modifying
  @Query("update article a set a.content_category = :categoryId where a.norm_title = :normTitle")
  boolean moveArticle(@Param("normTitle") String normTitle, @Param("categoryId") Long categoryId);

  @Modifying
  @Query("update article a set a.content_category = :to where a.content_category = :from")
  boolean moveAllArticles(@Param("from") Long from, @Param("to") Long to);

  boolean existsByNormalisedTitle(String normalisedTitle);

  Optional<Article> findByNormalisedTitle(String normalisedTitle);

}
