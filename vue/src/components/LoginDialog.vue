<script setup lang="ts">
import { Ref, ref } from 'vue';
import { authService } from '@/services';

const emit = defineEmits<{
  (event: 'readyToClose'): void;
}>();
const username = ref('');
const password = ref('');

const loginError = ref<boolean>(false);

const usernameRules: any[] = [(f: any) => !!f || 'Username is required'];
const passwordRules: any[] = [(f: any) => !!f || 'Password is required'];

const valid: Ref<boolean | null> = ref<boolean | null>(null);

async function onLoginClicked(event: any) {
  event.preventDefault();

  let loginResult: boolean = await authService.login({
    username: username.value,
    password: password.value,
  });
  if (loginResult) {
    emit('readyToClose');
  } else {
    loginError.value = true;
  }
}
</script>

<template>
  <v-card class="dialog-card" rounded variant="elevated">
    <v-form class="dialog-form" fast-fail v-model="valid" @submit.prevent="onLoginClicked">
      <v-text-field
        type="text"
        id="username"
        v-model="username"
        :rules="usernameRules"
        label="Username"
        variant="outlined"
      ></v-text-field>
      <v-text-field
        type="password"
        id="password"
        v-model="password"
        :rules="passwordRules"
        label="Password"
        variant="outlined"
      ></v-text-field>
      <v-btn color="primary" type="submit" :disabled="!valid">Login</v-btn>
    </v-form>
  </v-card>
</template>

<style scoped></style>
