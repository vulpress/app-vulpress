<script setup lang="ts">
import { useAppStore } from '@/store/app';
import { Router, useRouter } from 'vue-router';
import { ref } from 'vue';

import GiannitsaCard from '@/components/GiannitsaCard.vue';
import ArticleUploadDialog from '@/components/ArticleUploadDialog.vue';
import ArticleUpload from '@/services/article-upload.model';

const props = defineProps<{ category: string }>();
const app = useAppStore();
const router: Router = useRouter();

app.loadArticles(props.category);

function onArticleClicked(article: string) {
  router.push({ name: 'article', params: { category: props.category, article } });
}

function onBackClicked() {
  router.push({ name: 'main' });
}

async function onArticleUpload(payload: ArticleUpload) {
  await app.uploadArticle(payload);
  showUploadDialog.value = false;
}

const showUploadDialog = ref<boolean>(false);
</script>

<template>
  <header class="category-header">
    <v-btn icon="mdi-arrow-left" @click="onBackClicked" variant="plain"></v-btn>
    <h1 class="view-title">{{ app.currentCategory?.title ?? 'unknown category' }}</h1>
    <span class="header-spacer"></span>
  </header>
  <div class="ui-action-container">
    <v-btn color="primary" class="ui-action">
      Upload
      <v-dialog activator="parent" v-model="showUploadDialog">
        <article-upload-dialog
          :selectable-categories="app.categories"
          :default-category="app.currentCategory!"
          @complete="onArticleUpload"
          @close="showUploadDialog = false"
        ></article-upload-dialog>
      </v-dialog>
    </v-btn>
    <v-btn color="warn" class="ui-action">Delete</v-btn>
  </div>
  <div class="card-container">
    <giannitsa-card
      v-for="(a, i) in app.articles"
      :title="a.title!"
      :text="a.description ?? ''"
      @click="onArticleClicked(a.code!)"
    ></giannitsa-card>
  </div>
</template>

<style scoped></style>
