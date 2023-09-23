<script setup lang="ts">
import { Paragraph } from '@/api/vulpress';

const props = defineProps<{ paragraph: Paragraph }>();
</script>

<template>
  <h2 v-if="props.paragraph.title">{{ props.paragraph.title }}</h2>
  <img v-else-if="props.paragraph.image" :src="props.paragraph.image" />
  <p v-else-if="props.paragraph.text" class="p-text p-margin">{{ props.paragraph.text }}</p>
</template>

<style scoped>
.p-text {
  font-size: 1.2rem;
  font-weight: 400;
  color: black;
  text-align: justify;
}

.p-margin {
  margin-bottom: 1.8rem;
}
</style>
