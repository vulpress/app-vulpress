<script setup lang="ts">
import { Category } from '@/api/giannitsa';
import ArticleUpload from '@/services/article-upload.model';
import { watch } from 'vue';
import { Ref, ref } from 'vue';

interface ArticleUplaodDialogProps {
  selectableCategories: Category[];
  defaultCategory: Category;
}

const props = defineProps<ArticleUplaodDialogProps>();
const emit = defineEmits<{
  (event: 'complete', payload: ArticleUpload): void;
  (event: 'close'): void;
}>();

const selectedCategory: Ref<Category | undefined> = ref<Category | undefined>(
  props.defaultCategory
);

const title: Ref<string> = ref<string>('');
const description: Ref<string> = ref<string>('');

const titleRules = [
  (v: any) => {
    if (v) return true;
    return 'This field is mandatory!';
  },
];

const docu = ref();
const fileRules = [(v: any) => (!!v && !!v.length) || 'Uploading a file is mandatory!'];

watch(docu, (a, b) => {
  console.log('a', a);
  console.log('b', b);
});

const valid: Ref<boolean | null> = ref<boolean | null>(null);

function onSubmitClicked() {
  emit('complete', {
    category: selectedCategory.value!,
    title: title.value,
    content: docu.value[0],
    description: description.value,
  });
}
</script>

<template>
  <v-card class="dialog-card" rounded variant="elevated">
    <v-form class="dialog-form" fast-fail v-model="valid" @submit.prevent="onSubmitClicked">
      <v-select
        variant="outlined"
        v-model="selectedCategory"
        label="Upload to:"
        item-title="title"
        item-value="code"
        :items="props.selectableCategories"
      ></v-select>
      <v-text-field
        type="text"
        v-model="title"
        variant="outlined"
        label="Article title"
        :rules="titleRules"
        placeholder="Enter a unique name for a category"
      ></v-text-field>
      <v-textarea
        v-model="description"
        label="Description"
        variant="outlined"
        placeholder="Give a short description for the Article"
      ></v-textarea>
      <v-file-input
        v-model="docu"
        variant="outlined"
        small-chips
        label="Word Document"
        accept="application/vnd.openxmlformats-officedocument.wordprocessingml.document"
        :rules="fileRules"
      ></v-file-input>
      <div class="form-control-container">
        <v-btn class="form-button" color="secondary" @click="$emit('close')">Cancel</v-btn>
        <v-btn class="form-button" color="primary" type="submit" :disabled="!valid">Upload</v-btn>
      </div>
    </v-form>
  </v-card>
</template>

<style scoped>
.form-control-container {
  display: flex;
  flex-direction: row;
  justify-content: center;
  gap: 1rem;
}

.form-button {
  flex: 1;
}
</style>
