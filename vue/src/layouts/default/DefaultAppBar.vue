<script lang="ts" setup>
import { AppBarModel } from '@/api/giannitsa';
import { useAppStore } from '@/store/app';
import { storeToRefs } from 'pinia';
import { Ref } from 'vue';

const app = useAppStore();
const { appBarModel } = storeToRefs(app);
app.appBarModelChanged();
</script>

<template>
  <v-app-bar class="border-strong">
    <v-app-bar-title class="navbar-title">{{ appBarModel?.appName }}</v-app-bar-title>
    <v-btn icon="mdi-magnify"></v-btn>
    <v-btn
      :icon="appBarModel?.loggedIn ? 'mdi-account-circle-outline' : 'mdi-account-circle-full'"
    ></v-btn>
    <v-app-bar-nav-icon></v-app-bar-nav-icon>
  </v-app-bar>
</template>

<style scoped>
.navbar-title {
  font-size: 1.5rem;
  font-weight: 700;
  color: #492b7c;
}
</style>
