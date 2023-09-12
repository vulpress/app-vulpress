<script lang="ts" setup>
import { AppBarModel } from '@/api/giannitsa';
import { useAppStore } from '@/store/app';
import { storeToRefs } from 'pinia';
import { computed } from 'vue';
import { Ref } from 'vue';
import { useRouter } from 'vue-router';
import LoginDialog from '@/components/LoginDialog.vue';
import { ref } from 'vue';
import { authService } from '@/services';

interface LoginAction {
  code: string;
  name: string;
}

const anonymousLoginActions: LoginAction[] = [
  {
    code: 'register',
    name: 'Register',
  },
  {
    code: 'login',
    name: 'Login',
  },
];
const userLoginActions: LoginAction[] = [
  {
    code: 'profile',
    name: 'Profile',
  },
  {
    code: 'logout',
    name: 'Logout',
  },
];

const app = useAppStore();
const router = useRouter();
const { appBarModel } = storeToRefs(app);
app.appBarModelChanged();

const profileActions = computed(() => {
  return appBarModel.value?.loggedIn ? userLoginActions : anonymousLoginActions;
});

const showLoginDialog = ref<boolean>(false);

function onTitleClicked() {
  router.push({ name: 'main' });
}

function onCategoryClicked(category: string) {
  router.push({ name: 'category', params: { category } });
}

function onProfileActionClicked(code: string) {
  switch (code) {
    case 'login':
      showLoginDialog.value = true;
      break;
    case 'logout':
      authService.logout();
      app.appBarModelChanged();
      break;
  }
}

function onLoginClosed() {
  console.log('invoked onLoginClosed');
  showLoginDialog.value = false;
  app.appBarModelChanged();
}
</script>

<template>
  <v-app-bar class="border-strong">
    <v-app-bar-title class="navbar-title" @click="onTitleClicked">{{
      appBarModel?.appName
    }}</v-app-bar-title>
    <v-btn icon="mdi-magnify"></v-btn>
    <v-btn>
      <v-icon>{{
        appBarModel?.loggedIn ? 'mdi-account-circle' : 'mdi-account-circle-outline'
      }}</v-icon>
      <v-menu activator="parent">
        <v-list class="menu-card">
          <v-list-item
            class="menu-item"
            v-for="action in profileActions"
            :key="action.code"
            :value="action.code"
            @click="onProfileActionClicked(action.code)"
          >
            <v-list-item-title class="menu-item-text">{{ action.name }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-btn>
    <v-app-bar-nav-icon>
      <v-icon>$menu</v-icon>
      <v-menu activator="parent">
        <v-list class="menu-card">
          <v-list-item
            class="menu-item"
            v-for="action in appBarModel?.availableCategories"
            :key="action.code"
            :value="action.code"
            @click="onCategoryClicked(action.code)"
          >
            <v-list-item-title class="menu-item-text">{{ action.title }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-app-bar-nav-icon>
  </v-app-bar>

  <v-dialog v-model="showLoginDialog">
    <login-dialog @ready-to-close="onLoginClosed"></login-dialog>
  </v-dialog>
</template>

<style scoped>
.navbar-title {
  font-size: 1.5rem;
  font-weight: 700;
  color: #492b7c;
}

.menu-card {
  display: inline-flex;
  padding: 0.75rem;
  flex-direction: column;
  align-items: flex-start;
  gap: 0.375rem;
  border-radius: 0.3125rem;
  border: 2px solid var(--gr-primary);
}

.menu-item {
  padding: unset !important;
  margin: unset !important;
}

.menu-item-text {
  color: var(--gr-primary);
  font-size: 1rem;
  font-weight: 700;
}
</style>
