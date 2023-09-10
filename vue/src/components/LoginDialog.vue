<script setup lang="ts">
import { ref } from 'vue';
import { authService } from '@/services';

const emit = defineEmits<{
  (event: 'readyToClose'): void;
}>();
const username = ref('');
const password = ref('');

const loginError = ref<boolean>(false);

const usernameRules: any[] = [(f: any) => !!f || 'Username is required'];
const passwordRules: any[] = [(f: any) => !!f || 'Password is required'];

async function onLoginClicked(event: any) {
  event.preventDefault();

  let loginResult: boolean = await authService.login({
    username: username.value,
    password: password.value,
  });
  console.log('login succeeded: ', loginResult);
  if (loginResult) {
    console.log('emittin readyToClose');
    emit('readyToClose');
  } else {
    loginError.value = true;
  }
}
</script>

<template>
  <v-sheet class="loginFormContainer pa-12 padding-none" rounded>
    <v-card class="padding-1-hor padding-1-ver" width="400" rounded variant="elevated">
      <v-form validate-on="submit" class="card" @submit.prevent="onLoginClicked">
        <v-text-field
          class="mb-2"
          type="text"
          id="username"
          v-model="username"
          :rules="usernameRules"
          label="Username"
          variant="outlined"
        ></v-text-field>
        <v-text-field
          class="mb-2"
          type="password"
          id="password"
          v-model="password"
          :rules="passwordRules"
          label="Password"
          variant="outlined"
        ></v-text-field>
        <v-btn color="tertiary" @click="$emit('readyToClose')">Cancel</v-btn>
        <v-btn type="submit" variant="elevated" size="large">
          <v-icon icon="mdi-login" size="large" start variant="elevated" color="primary"></v-icon
          >Login</v-btn
        >
      </v-form>
    </v-card>
  </v-sheet>
</template>

<style scoped></style>
