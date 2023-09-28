<script setup lang="ts">
import { ArticlePreview } from '@/api/vulpress';
import GiannitsaCard from '@/components/GiannitsaCard.vue';
import { useSearchStore } from '@/store/search';
import { storeToRefs } from 'pinia';
import { useRouter } from 'vue-router';

const search = useSearchStore();
const router = useRouter();

const { queryStr, searchResult } = storeToRefs(search);

function onArticleClicked(a: ArticlePreview) {
  router.push(a.path!);
}
</script>

<template>
  <header class="category-header">
    <h1 class="view-title">"{{ queryStr }}": ({{ searchResult.length }})</h1>
  </header>
  <div class="card-container">
    <giannitsa-card
      v-for="a in searchResult"
      :title="a.title!"
      :text="a.description ?? ''"
      :issue-date="a.issueDate"
      :author="a.author ?? 'System Administrator'"
      @click="onArticleClicked(a)"
    ></giannitsa-card>
  </div>
</template>

<style scoped></style>
