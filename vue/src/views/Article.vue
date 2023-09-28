<script setup lang="ts">
import { ArticleDetail, Category, UiAction } from '@/api/vulpress';
import { useAppStore } from '@/store/app';
import { Router, useRouter } from 'vue-router';

import ArticleParagraph from '@/components/Paragraph.vue';
import MoveArticleDialog from '@/components/MoveArticleDialog.vue';

import { ref, watch } from 'vue';
import { onMounted } from 'vue';
import { storeToRefs } from 'pinia';
import { ViewName } from '@/store/view.constants';
import { viewService } from '@/services';
import { computed } from 'vue';

const props = defineProps<{
  category: string;
  article: string;
}>();
const app = useAppStore();
const router: Router = useRouter();
const { appBarModel, currentCategory, categories } = storeToRefs(app);

const a = ref<ArticleDetail | undefined>();

const actions = ref<UiAction[]>([]);
watch(props, async (to, fro) => {
  a.value = (await app.getArticle(props.category, props.article))!;
  refreshActions();
});
watch(appBarModel, async (to, fro) => {
  refreshActions();
});

onMounted(async () => {
  a.value = (await app.getArticle(props.category, props.article))!;
  refreshActions();
});
app.setCurrentCategory(props.category);

async function refreshActions() {
  actions.value = await viewService.actions(ViewName.ARTICLE);
}

const renderMoveAction = computed(() => actions.value.some((ac) => 'move-article' === ac.code));

function onBackClicked() {
  router.back();
}

const showMoveDialog = ref<boolean>(false);

function onMove(category: Category) {
  app.moveArticle(props.article, category.code);
}

function onArchive() {
  app.deleteArticle(props.article);
}
</script>

<template>
  <header class="category-header">
    <v-btn icon="mdi-arrow-left" @click="onBackClicked" variant="plain"></v-btn>
    <h1 class="view-title">{{ currentCategory?.title ?? 'unknown category' }}</h1>
    <span class="header-spacer"></span>
  </header>
  <div v-if="renderMoveAction" class="ui-action-container">
    <div class="ui-action-container">
      <span class="ui-action v-btn--size-default"></span>
      <v-btn color="primary" class="ui-action">
        Move
        <v-dialog activator="parent" v-model="showMoveDialog">
          <move-article-dialog
            :default-category="currentCategory!"
            :selectable-categories="categories"
            @move="onMove"
            @close="showMoveDialog = false"
            @archive="onArchive"
          ></move-article-dialog>
        </v-dialog>
      </v-btn>
    </div>
  </div>
  <div class="article-title-container">
    <label class="article-title">{{ a?.title }}</label>
    <span class="article-title-spacer"></span>
    <label class="article-issue-date">{{ a?.issueDate }}</label>
  </div>
  <div class="article-content">
    <article-paragraph v-for="p of a?.paragraphs" :paragraph="p"></article-paragraph>
  </div>
</template>

<style scoped>
.article-title {
  color: var(--gr-text);
  text-align: center;
  font-size: 1.5rem;
  font-weight: 400;
  align-self: baseline;
}

.article-title-container {
  display: flex;
  flex-direction: row;
  min-width: 90vw;
  max-width: 90vw;
  justify-content: space-between;
  margin-top: 3rem;
  margin-bottom: 1rem;
}

.article-title-spacer {
  flex: 1;
}

.article-issue-date {
  font-size: 0.625rem;
  font-weight: 400;
  align-self: baseline;
}

.article-content {
  max-width: 90vw;
}
</style>
