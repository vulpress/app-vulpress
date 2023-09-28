<script lang="ts" setup>
import LoginDialog from '@/components/LoginDialog.vue';
import RegistrationDialog from '@/components/RegistrationDialog.vue';
import { articleService, authService } from '@/services';
import { useAppStore } from '@/store/app';
import { useSearchStore } from '@/store/search';
import { storeToRefs } from 'pinia';
import { Ref } from 'vue';
import { computed, ref } from 'vue';
import { useRouter } from 'vue-router';

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
const search = useSearchStore();
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
    case 'register':
      showRegisterDialog.value = true;
      break;
  }
}

function onLoginClosed() {
  showLoginDialog.value = false;
  app.appBarModelChanged();
}

const showRegisterDialog = ref<boolean>(false);

// --------------------------------------------------
// Search Stuff
const queryStr: Ref<string> = ref<string>('');
const showQuery: Ref<boolean> = ref<boolean>(false);

async function doSearch() {
  search.search(queryStr.value);
}
</script>

<template>
  <v-app-bar class="border-strong">
    <v-app-bar-title class="navbar-title" @click="onTitleClicked">{{
      appBarModel?.appName
    }}</v-app-bar-title>

    <Transition>
      <v-text-field
        class="query-field"
        v-if="showQuery"
        single-line
        hide-details
        v-model="queryStr"
        variant="outlined"
        density="compact"
        append-inner-icon="mdi-arrow-right"
        @click:append-inner="doSearch"
      ></v-text-field>
    </Transition>
    <v-btn icon="mdi-magnify" @click="showQuery = !showQuery"></v-btn>
    <v-btn icon>
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
  <v-dialog v-model="showRegisterDialog">
    <registration-dialog @close="showRegisterDialog = false"></registration-dialog>
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

.query-field {
  max-width: 30rem;
}

.v-enter-active,
.v-leave-active {
  transition: all 0.4s ease-in-out;
  transform-origin: right;
}

.v-enter-from {
  transform: scaleX(0);
}
.v-enter-to {
  transform: scaleX(1);
}

.v-leave-from {
  transform: scaleX(1);
  transform-origin: right;
}
.v-leave-to {
  transform: scaleX(0);
}
</style>
