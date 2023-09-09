<script setup lang="ts">
import GiannitsaCard from '@/components/GiannitsaCard.vue';
import { useAppStore } from '@/store/app';
import { Router, useRouter } from 'vue-router';

const app = useAppStore();
const router: Router = useRouter();

function onCategoryClicked(category: string) {
    router.push({ name: 'category', params: { category } });
}

</script>

<template>
  <h1 class="view-title">Categories</h1>
  <div class="ui-action-container">
    <v-btn color="primary" class="ui-action">New category</v-btn>
    <v-btn color="accent" class="ui-action">Archive</v-btn>
  </div>
  <div class="card-container">
    <giannitsa-card v-for="(c, i) in app.categories" :title="c.title!" :text="c.description ?? ''" @click="onCategoryClicked(c.code)"></giannitsa-card>
  </div>
</template>

<style scoped>

</style>
