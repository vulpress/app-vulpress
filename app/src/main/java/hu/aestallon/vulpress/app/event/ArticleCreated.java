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

package hu.aestallon.vulpress.app.event;

import hu.aestallon.vulpress.app.auth.User;
import hu.aestallon.vulpress.app.domain.article.Article;
import org.springframework.context.ApplicationEvent;

public class ArticleCreated extends ApplicationEvent implements UserEvent {

  private final User createdBy;

  public ArticleCreated(Article article, User user) {
    super(article);
    this.createdBy = user;
  }

  public Article article() {
    return (Article) getSource();
  }

  @Override
  public User user() {
    return createdBy;
  }
}
