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
import { ArticleDetail, ArticlePreview, Category } from '@/api/giannitsa';
import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useAppStore = defineStore('app', () => {
  //#region mockCategories
  const mockCategories: Category[] = [
    {
      code: 'category-1',
      title: 'Category 1',
      description: 'Category 1 Description',
    },
    {
      code: 'category-2',
      title: 'Category 2',
      description: 'Category 2 Description',
    },
    {
      code: 'category-3',
      title: 'Category 3',
      description: 'Category 3 Description',
    },
    {
      code: 'category-4',
      title: 'Category 4',
      description: 'Category 4 Description',
    },
  ];
  //#endregion

  const categories = ref<Category[]>(mockCategories);
  const articles = ref<ArticlePreview[]>([]);

  const currentCategory = ref<Category | undefined>();

  function fetchArticles(category: string): void {
    articles.value = [
      {
        code: `${category}-article-1`,
        title: `${category} Article 1`,
        description: `${category} Article 1 Description`,
      },
      {
        code: `${category}-article-2`,
        title: `${category} Article 2`,
        description: `${category} Article 2 Description`,
      },
      {
        code: `${category}-article-3`,
        title: `${category} Article 3`,
        description: `${category} Article 3 Description`,
      },
      {
        code: `${category}-article-4`,
        title: `${category} Article 4`,
        description: `${category} Article 4 Description`,
      },
    ];
    currentCategory.value = categories.value.find((c) => c.code === category);
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

  return { categories, currentCategory, articles, fetchArticles, loadArticle };
});
