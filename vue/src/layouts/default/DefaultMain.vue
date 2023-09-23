<script lang="ts" setup></script>

<template>
  <v-main class="margin-2 main-container">
    <router-view />
  </v-main>
</template>

<style scoped>
.margin-2 {
  margin-top: 2rem;
  margin-bottom: 2rem;
}

.main-container {
  display: flex;
  flex-direction: column;
  justify-items: flex-start;
  align-items: center;
}
</style>
