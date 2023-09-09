<script setup lang="ts">
import { ArticleDetail } from '@/api/giannitsa';
import { useAppStore } from '@/store/app';
import { Router, useRouter } from 'vue-router';

import ArticleParagraph from '@/components/Paragraph.vue';


const props = defineProps<{
    category: string,
    article: string
}>();
const app = useAppStore();
const router: Router = useRouter();

const a: ArticleDetail = app.loadArticle(props.category, props.article)!;
console.log('article', a);

function onBackClicked() {
    router.back();
}

</script>

<template>
    <header class="category-header">
        <v-btn icon="mdi-arrow-left" @click="onBackClicked" variant="plain"></v-btn>
        <h1 class="view-title">{{ app.currentCategory?.title ?? 'unknown category' }}</h1>
        <span class="header-spacer"></span>
    </header>
    <div class="ui-action-container">
        <v-btn color="primary" class="ui-action">Move</v-btn>
    </div>
    <label class="article-title">{{ a.title }}</label>
    <article-paragraph v-for="p of a.paragraphs" :paragraph="p"></article-paragraph>
</template>

<style scoped>
.article-title {
    color: var(--gr-text);
    text-align: center;
    font-size: 1.5rem;
    font-weight: 400;
}
</style>