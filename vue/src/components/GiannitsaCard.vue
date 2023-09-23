<script setup lang="ts">
interface GiannitsaCardProps {
  title: string;
  text: string;
  issueDate?: string;
  author?: string;
}

const props = defineProps<GiannitsaCardProps>();
</script>

<template>
  <v-card class="my-card">
    <v-card-title class="my-card-title">{{ props.title }}</v-card-title>
    <v-card-text class="my-card-text">{{ props.text }}</v-card-text>
    <div v-if="props.issueDate || props.author" class="my-card-meta">
      <p class="meta-text">{{ props.issueDate }}</p>
      <span class="meta-span"></span>
      <p class="meta-text">{{ props.author }}</p>
    </div>
  </v-card>
</template>

<style scoped>
.my-card {
  width: 90vw;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 0.5rem;

  background-color: unset;
  border-radius: 6px;
  border: 1px solid var(--gr-primary);
  box-shadow: 2px 4px 4px 0px var(--gr-primary);
}

.my-card-title {
  color: var(--gr-text, #301551);
  text-align: center;
  font-size: 1.5rem;
  font-weight: 400;
}

.my-card-text {
  color: var(--gr-text, #301551);
  text-align: justify;
  font-size: 1rem;
}

.my-card-meta {
  display: flex;
  flex-direction: row;
  padding: 0.5rem 1rem;
  align-self: stretch;
}

.meta-text {
  color: var(--gr-text);
  font-size: 0.625rem;
}

.meta-span {
  flex: 1;
}
</style>
