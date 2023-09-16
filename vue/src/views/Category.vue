<script setup lang="ts">
import { useAppStore } from '@/store/app';
import { Router, useRouter } from 'vue-router';
import { onMounted, ref } from 'vue';

import GiannitsaCard from '@/components/GiannitsaCard.vue';
import ArticleUploadDialog from '@/components/ArticleUploadDialog.vue';
import ArticleUpload from '@/services/article-upload.model';
import { storeToRefs } from 'pinia';
import { watch } from 'vue';
import { viewService } from '@/services';
import { ViewName } from '@/store/view.constants';
import { computed } from 'vue';

const props = defineProps<{ category: string }>();
const app = useAppStore();
const router: Router = useRouter();

const { appBarModel, currentCategory, articles } = storeToRefs(app);

// state init
const actions = ref<string[]>([]);

app.loadArticles(props.category);
refreshActions();
watch(props, (a, b) => {
  app.loadArticles(a.category);
  refreshActions();
});
watch(appBarModel, async (a, b) => {
  refreshActions();
});

async function refreshActions() {
  actions.value = (await viewService.actions(ViewName.CATEGORY)).map((a) => a.code);
}

// action conditions
const showUploadAction = computed<boolean>(() => actions.value.some((a) => 'upload-article' === a));
const showDeleteAction = computed<boolean>(() =>
  actions.value.some((a) => 'delete-category' === a)
);
// event handlers
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
    <h1 class="view-title">{{ currentCategory?.title ?? 'unknown category' }}</h1>
    <span class="header-spacer"></span>
  </header>
  <div class="ui-action-container">
    <v-btn v-if="showUploadAction" color="primary" class="ui-action">
      Upload
      <v-dialog activator="parent" v-model="showUploadDialog">
        <article-upload-dialog
          :selectable-categories="app.categories"
          :default-category="currentCategory!"
          @complete="onArticleUpload"
          @close="showUploadDialog = false"
        ></article-upload-dialog>
      </v-dialog>
    </v-btn>
    <v-btn v-if="showDeleteAction" color="warn" class="ui-action">Delete</v-btn>
  </div>
  <div class="card-container">
    <giannitsa-card
      v-for="(a, i) in articles"
      :title="a.title!"
      :text="a.description ?? ''"
      @click="onArticleClicked(a.code!)"
    ></giannitsa-card>
  </div>
</template>

<style scoped></style>
