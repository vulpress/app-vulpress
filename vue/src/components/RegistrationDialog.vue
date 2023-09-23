<script setup lang="ts">
import { AuthenticationRequest } from '@/api/vulpress';
import { registrationService } from '@/services';
import { Ref, ref } from 'vue';

const emit = defineEmits<{
  (event: 'close'): void;
}>();

const username: Ref<string> = ref<string>('');
const password: Ref<string> = ref<string>('');
const passwordRepeat: Ref<string> = ref<string>('');

const loading: Ref<boolean> = ref<boolean>(false);

async function onSubmit() {
  loading.value = true;
  const request: AuthenticationRequest = {
    username: username.value,
    password: password.value,
  };
  const ok: boolean = await registrationService.register(request);
  loading.value = false;

  if (ok) {
    emit('close');
  } else {
    // TODO: Show registration error!
  }
}
</script>

<template>
  <v-card class="dialog-card">
    <v-card-title class="dialog-title">Register</v-card-title>
    <v-form class="dialog-form" @submit.prevent="onSubmit">
      <v-text-field
        type="text"
        id="username"
        v-model="username"
        label="Username"
        variant="outlined"
      ></v-text-field>
      <v-text-field
        type="password"
        id="password"
        v-model="password"
        label="Password"
        variant="outlined"
      ></v-text-field>
      <v-text-field
        type="password"
        id="confirm-password"
        v-model="passwordRepeat"
        label="Confirm password"
        variant="outlined"
      ></v-text-field>
      <div class="ui-action-container">
        <v-btn class="ui-action ui-action-disabled" @click="$emit('close')">Cancel</v-btn>
        <v-btn class="ui-action" color="primary" :loading="loading" type="submit">Register</v-btn>
      </div>
    </v-form>
  </v-card>
</template>

<style scoped></style>
