<script setup lang="ts">
import { Category } from '@/api/giannitsa';
import GiannitsaCard from '@/components/GiannitsaCard.vue';
import NewCategoryDialog from '@/components/NewCategoryDialog.vue';

import { articleService, viewService } from '@/services';
import { useAppStore } from '@/store/app';
import { ViewName } from '@/store/view.constants';
import { storeToRefs } from 'pinia';
import { onMounted } from 'vue';
import { Ref } from 'vue';
import { ref } from 'vue';
import { watch } from 'vue';
import { Router, useRouter } from 'vue-router';

const app = useAppStore();
const router: Router = useRouter();

const { appBarModel } = storeToRefs(app);
const actions = ref<string[]>([]);

onMounted(() => {
  refreshActions();
});
watch(appBarModel, async (a, b) => {
  refreshActions();
});

async function refreshActions() {
  actions.value = (await viewService.actions(ViewName.MAIN)).map((a) => a.code);
}

function onCategoryClicked(category: string) {
  router.push({ name: 'category', params: { category } });
}

function onArchiveClicked() {
  onCategoryClicked('sys_archive');
}

const showNewCategoryDialog: Ref<boolean> = ref<boolean>(false);

async function onNewCategoryCreated(category: Category) {
  showNewCategoryDialog.value = false;
  let res = await articleService.createCategory(category);
  console.log('Create category result: ', res);
  app.appBarModelChanged();
}
</script>

<template>
  <h1 class="view-title">Categories</h1>
  <div v-if="actions.length > 0" class="ui-action-container">
    <v-btn color="primary" class="ui-action"
      >New category
      <v-dialog activator="parent" v-model="showNewCategoryDialog">
        <new-category-dialog @complete="onNewCategoryCreated"></new-category-dialog>
      </v-dialog>
    </v-btn>
    <v-btn color="secondary" class="ui-action" @click="onArchiveClicked">Archive</v-btn>
  </div>
  <div class="card-container">
    <giannitsa-card
      v-for="(c, i) in app.categories"
      :title="c.title!"
      :text="c.description ?? ''"
      @click="onCategoryClicked(c.code)"
    ></giannitsa-card>
  </div>
</template>

<style scoped></style>
