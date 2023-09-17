<script setup lang="ts">
import { ArticleDetail, Category } from '@/api/giannitsa';
import { useAppStore } from '@/store/app';
import { Router, useRouter } from 'vue-router';

import ArticleParagraph from '@/components/Paragraph.vue';
import MoveArticleDialog from '@/components/MoveArticleDialog.vue';

import { ref } from 'vue';
import { onMounted } from 'vue';

const props = defineProps<{
  category: string;
  article: string;
}>();
const app = useAppStore();
const router: Router = useRouter();

const a = ref<ArticleDetail | undefined>();

onMounted(async () => {
  a.value = (await app.getArticle(props.category, props.article))!;
  console.log('article', a.value);
});

function onBackClicked() {
  router.back();
}

const showMoveDialog = ref<boolean>(false);

function onMove(category: Category) {
  console.log('moving [article] to [category]: ', props.article, category);
  app.moveArticle(props.article, category.code);
}

function onArchive() {
  console.log('deleting [article]: ', props.article);
  app.deleteArticle(props.article);
}
</script>

<template>
  <header class="category-header">
    <v-btn icon="mdi-arrow-left" @click="onBackClicked" variant="plain"></v-btn>
    <h1 class="view-title">{{ app.currentCategory?.title ?? 'unknown category' }}</h1>
    <span class="header-spacer"></span>
  </header>
  <div class="ui-action-container">
    <v-btn color="primary" class="ui-action">
      Move
      <v-dialog activator="parent" v-model="showMoveDialog">
        <move-article-dialog
          :default-category="app.currentCategory!"
          :selectable-categories="app.categories"
          @move="onMove"
          @close="showMoveDialog = false"
          @archive="onArchive"
        ></move-article-dialog>
      </v-dialog>
    </v-btn>
  </div>
  <label class="article-title">{{ a?.title }}</label>
  <article-paragraph v-for="p of a?.paragraphs" :paragraph="p"></article-paragraph>
</template>

<style scoped>
.article-title {
  color: var(--gr-text);
  text-align: center;
  font-size: 1.5rem;
  font-weight: 400;
}
</style>
