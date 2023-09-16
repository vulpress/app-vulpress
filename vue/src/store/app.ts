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
import { articleService, viewService } from '@/services';
import ArticleUpload from '@/services/article-upload.model';
import { defineStore } from 'pinia';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

export const useAppStore = defineStore('app', () => {
  const router = useRouter();

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
      router.push({ name: 'main' });
    }
  }

  function currentCategoryChanged() {}

  async function loadArticles(category: string) {
    let result: ArticlePreview[] | ApiError = await articleService.articles(category);
    if (isError(result)) {
      console.log(result);
      return;
    }

    articles.value = result;
    if (category === 'sys_archive') {
      // TODO: remove this branching here!
      currentCategory.value = {
        code: 'sys_archive',
        title: 'Archive',
      };
    } else {
      currentCategory.value = categories.value.find((c) => c.code === category);
    }
  }

  function isError(value: any | ApiError): value is ApiError {
    return (value as ApiError).status !== undefined;
  }

  async function uploadArticle(payload: ArticleUpload): Promise<ArticleDetail | ApiError> {
    let res = await articleService.uploadArticle(payload);
    if (payload.category.code === currentCategory.value?.code) {
      loadArticles(currentCategory.value.code);
    }
    return res;
  }

  async function getArticle(category: string, article: string): Promise<ArticleDetail | undefined> {
    const res = await articleService.loadArticle(category, article);
    if (isError(res)) {
      console.log('Article load result: ', res);
      return undefined;
    }
    console.log('Article load result: ', res);
    return res;
  }

  return {
    // refs:
    appBarModel,
    categories,
    currentCategory,
    articles,

    // methods:
    appBarModelChanged,
    loadArticles,
    uploadArticle,
    getArticle,
    // computed values:
  };
});
