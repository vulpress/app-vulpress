<script setup lang="ts">
import { Category } from '@/api/giannitsa';
import { Ref } from 'vue';
import { ref } from 'vue';

const emit = defineEmits<{
  (event: 'complete', category: Category): void;
}>();

const title: Ref<string> = ref<string>('');
const description: Ref<string> = ref<string>('');

function onSubmission() {
  emit('complete', { code: '', title: title.value, description: description.value });
}
</script>

<template>
  <v-form @submit.prevent="onSubmission">
    <v-text-field
      type="text"
      v-model="title"
      variant="outlined"
      label="Category title"
      placeholder="Enter a unique name for a category"
    ></v-text-field>
    <v-textarea
      v-model="description"
      label="Description"
      placeholder="Give a short description for the category"
    ></v-textarea>
    <v-btn color="primary" type="submit">Create Category</v-btn>
  </v-form>
</template>

<style scoped></style>
