<script setup lang="ts">
import { Category } from '@/api/giannitsa';
import { Ref, ref, watch } from 'vue';

interface MoveArticleDialogProps {
  selectableCategories: Category[];
  defaultCategory: Category;
}

const props = defineProps<MoveArticleDialogProps>();
const emit = defineEmits<{
  (event: 'move', payload: Category): void;
  (event: 'close'): void;
  (event: 'archive'): void;
}>();

const selectedCategory: Ref<Category> = ref<Category>(props.defaultCategory);

function onSubmit() {
  emit('move', selectedCategory.value);
}
</script>

<template>
  <v-card class="dialog-card">
    <v-form class="dialog-form" @submit.prevent="onSubmit">
      <div class="ui-action-container" v-if="props.defaultCategory.code !== 'sys_archive'">
        <span class="ui-action v-btn--size-default"></span>
        <v-btn color="secondary" class="ui-action" @click="$emit('archive')">Archive</v-btn>
      </div>
      <v-select
        variant="outlined"
        v-model="selectedCategory"
        label="Move to:"
        item-title="title"
        item-value="code"
        :items="props.selectableCategories"
        return-object
      ></v-select>
      <div class="ui-action-container">
        <v-btn class="ui-action ui-action-disabled" @click="$emit('close')">Cancel</v-btn>
        <v-btn class="ui-action" color="primary" type="submit">Move</v-btn>
      </div>
    </v-form>
  </v-card>
</template>

<style scoped>
.archive-btn {
  min-height: 36px;
  align-self: flex-end;
  margin-bottom: 1.25rem;
}
</style>
