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

package hu.aestallon.vulpress.app.job;

import hu.aestallon.vulpress.app.domain.article.ArticleRepository;
import hu.aestallon.vulpress.app.event.ArticlePublished;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticlePublisher {

  private final ApplicationEventPublisher eventPublisher;
  private final ArticleRepository         articleRepository;

  @Scheduled(cron = "${article.publication.time:0 0 9 * * *}")
  public void execute() {
    articleRepository.findArticlesToPublish().forEach(a -> {
      articleRepository.publishByNormalisedTitle(a.normalisedTitle());
      eventPublisher.publishEvent(new ArticlePublished(a));
    });
  }
}
