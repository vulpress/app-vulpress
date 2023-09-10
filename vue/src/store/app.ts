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

// Utilities
import { ApiError, AppBarModel, ArticleDetail, ArticlePreview, Category } from '@/api/giannitsa';
import { defineStore } from 'pinia';
import { ref } from 'vue';
import { articleService, viewService } from '@/services';

export const useAppStore = defineStore('app', () => {
  const appBarModel = ref<AppBarModel | undefined>();
  const categories = ref<Category[]>([]);
  const articles = ref<ArticlePreview[]>([]);
  const currentCategory = ref<Category | undefined>();

  async function appBarModelChanged() {
    appBarModel.value = await viewService.appBarModel();
    console.log('Refreshed app-bar-model', appBarModel.value);
    categoriesChanged();
  }

  async function categoriesChanged() {
    categories.value = await articleService.categories();
    console.log('Refreshed categories', categories.value);

    if (!categories.value.some((c) => c.code === currentCategory.value?.code)) {
      currentCategory.value = undefined;
    }
  }

  async function loadArticles(category: string) {
    let result: ArticlePreview[] | ApiError = await articleService.articles(category);
    if (isError(result)) {
      console.log(result);
      return;
    }

    articles.value = result;
  }

  function isError(value: any | ApiError): value is ApiError {
    return (value as ApiError).status !== undefined;
  }

  function loadArticle(category: string, article: string): ArticleDetail | undefined {
    return {
      code: article,
      title: `${article} title`,
      paragraphs: [
        {
          text: 'Lorem ipsum dolor sit amet',
        },
        {
          text: 'Lorem ipsum dolor sit amet',
        },
        {
          text: 'Lorem ipsum dolor sit amet',
        },
        {
          text: 'Lorem ipsum dolor sit amet',
        },
      ],
    };
  }

  return {
    appBarModel,
    appBarModelChanged,
    categories,
    currentCategory,
    articles,
    loadArticles,
    loadArticle,
  };
});
