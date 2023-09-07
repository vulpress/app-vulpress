<template>
  <v-app>
    <v-app-bar class="navbar">
      <v-app-bar-title class="navbar-title">APP GIANNITSA</v-app-bar-title>
      <v-btn icon="mdi-magnify"></v-btn>
      <v-btn icon="mdi-account-circle-outline"></v-btn>
      <v-app-bar-nav-icon></v-app-bar-nav-icon>
    </v-app-bar>
    <router-view></router-view>
  </v-app>
</template>

<script lang="ts" setup>
</script>

<style scoped>
.navbar {
  border: 0.3rem solid #492B7C;
  background: yellow;
}

.navbar-title {
  font-size: 1.5rem;
  font-weight: 700;
  color: #492B7C;
}

</style>
