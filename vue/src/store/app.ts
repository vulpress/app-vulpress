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

  async function appBarModelChanged(reroute?: boolean) {
    appBarModel.value = await viewService.appBarModel();
    categoriesChanged(reroute);
  }

  async function categoriesChanged(reroute?: boolean) {
    categories.value = await articleService.categories();

    if (!categories.value.some((c) => c.code === currentCategory.value?.code)) {
      if (reroute) {
        currentCategory.value = undefined;
        toMain();
      }
    }
  }

  async function deleteCategory(category: string) {
    const res = await articleService.deleteCategory(category);
    if (res) {
      appBarModelChanged(true);
    }
  }

  async function loadArticles(category: string) {
    if (categories.value.length === 0) {
      categories.value = await articleService.categories();
    }

    if (category === 'sys_archive') {
      // TODO: remove this branching here!
      currentCategory.value = {
        code: 'sys_archive',
        title: 'Archive',
      };
    } else {
      currentCategory.value = categories.value.find((c) => c.code === category);
    }

    if (!currentCategory.value) {
      toMain();
    }

    let result: ArticlePreview[] | ApiError = await articleService.articles(category);
    if (isError(result)) {
      // TODO: Handle error!
      return;
    }

    articles.value = result;
  }

  function isError(value: any | ApiError): value is ApiError {
    return (value as ApiError).status !== undefined;
  }

  function toMain() {
    router.push({ name: 'main' });
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
      toMain();
      return undefined;
    }
    return res;
  }

  async function moveArticle(article: string, targetCategory: string) {
    const res: boolean = await articleService.moveArticle(article, targetCategory);
    if (res) {
      if (currentCategory.value) {
        router.push({ name: 'category', params: { category: currentCategory.value.code } });
      } else {
        // TODO: Handle Error!
      }
    }
  }

  async function deleteArticle(article: string) {
    const res: boolean = await articleService.deleteArticle(article);
    if (res) {
      if (currentCategory.value) {
        // loadArticles(currentCategory.value?.code);
        router.push({ name: 'category', params: { category: currentCategory.value.code } });
      } else {
        // TODO: Handle Error!
      }
    }
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
    deleteCategory,
    moveArticle,
    deleteArticle,
    // computed values:
  };
});
