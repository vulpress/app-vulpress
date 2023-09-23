<script setup lang="ts">
import { useAppStore } from '@/store/app';
import { ref } from 'vue';
import { Router, useRouter } from 'vue-router';

import { UiAction } from '@/api/giannitsa';
import ArticleUploadDialog from '@/components/ArticleUploadDialog.vue';
import GiannitsaCard from '@/components/GiannitsaCard.vue';
import { viewService } from '@/services';
import ArticleUpload from '@/services/article-upload.model';
import ConfirmDialog from '@/components/ConfirmDialog.vue';
import { ViewName } from '@/store/view.constants';
import { storeToRefs } from 'pinia';
import { computed, watch } from 'vue';

const props = defineProps<{ category: string }>();
const app = useAppStore();
const router: Router = useRouter();

const { appBarModel, currentCategory, articles } = storeToRefs(app);

// state init
const actions = ref<UiAction[]>([]);

app.loadArticles(props.category);
refreshActions(props.category);
watch(props, (a, b) => {
  app.loadArticles(a.category);
  refreshActions(a.category);
});
watch(appBarModel, async (a, b) => {
  refreshActions(props.category);
});

async function refreshActions(categoryCode: string) {
  actions.value = await viewService.actions(ViewName.CATEGORY, categoryCode);
}

// action conditions
const showUploadAction = computed<boolean>(() =>
  actions.value.some((a) => 'upload-article' === a.code)
);
const showDeleteAction = computed<boolean>(() =>
  actions.value.some((a) => 'delete-category' === a.code)
);
const disabledDelete = computed<boolean>(
  () => actions.value.find((a) => a.code === 'delete-category')?.disabled ?? true
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

async function onCategoryDeleted() {
  showDeleteDialog.value = false;
  app.deleteCategory(props.category);
}

const showUploadDialog = ref<boolean>(false);
const showDeleteDialog = ref<boolean>(false);
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
    <v-btn v-if="showDeleteAction" color="warning" class="ui-action" :disabled="disabledDelete">
      Delete
      <template v-slot:append>
        <v-icon icon="mdi-trash-can-outline" color="primary"></v-icon>
      </template>
      <v-dialog activator="parent" v-model="showDeleteDialog">
        <confirm-dialog
          title="Delete category"
          text="All elements in this category will be moved to the archive!"
          @no="showDeleteDialog = false"
          @yes="onCategoryDeleted"
        ></confirm-dialog>
      </v-dialog>
    </v-btn>
  </div>
  <div class="card-container">
    <giannitsa-card
      v-for="(a, i) in articles"
      :title="a.title!"
      :text="a.description ?? ''"
      :issue-date="a.issueDate"
      :author="a.author ?? 'System Administrator'"
      @click="onArticleClicked(a.code!)"
    ></giannitsa-card>
  </div>
</template>

<style scoped></style>
