<script setup lang="ts">
import { Category } from '@/api/vulpress';
import { Ref } from 'vue';
import { ref } from 'vue';

const emit = defineEmits<{
  (event: 'complete', category: Category): void;
}>();

const title: Ref<string> = ref<string>('');
const description: Ref<string> = ref<string>('');

const titleRules = [
  (v: any) => {
    if (v) return true;
    return 'This field is mandatory!';
  },
];

const valid: Ref<boolean | null> = ref<boolean | null>(null);

function onSubmission(event: any) {
  event.preventDefault();
  emit('complete', { code: '', title: title.value, description: description.value });
}
</script>

<template>
  <v-card variant="outlined" class="dialog-card">
    <v-card-title class="dialog-title">New Category</v-card-title>
    <v-form fast-fail v-model="valid" @submit.prevent="onSubmission" class="dialog-form">
      <v-text-field
        type="text"
        v-model="title"
        variant="outlined"
        label="Category title"
        :rules="titleRules"
        placeholder="Enter a unique name for a category"
      ></v-text-field>
      <v-textarea
        v-model="description"
        label="Description"
        variant="outlined"
        placeholder="Give a short description for the category"
      ></v-textarea>
      <v-btn color="primary" type="submit" :disabled="!valid">Create Category</v-btn>
    </v-form>
  </v-card>
</template>

<style scoped></style>
