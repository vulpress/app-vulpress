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

const usernameRules: any[] = [
  (f: any) => !!f || 'Email is required',
  (f: any) => {
    if (/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+\@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test(f))
      return true;
    return 'A valid e-mail address is required';
  },
];
const passwordRules: any[] = [
  (f: any) => !!f || 'Password is required',
  (f: any) => f.length >= 8 || 'Password must be at least 8 characters long!',
];
const confirmPasswordRules: any[] = [
  (f: any) => !!f || 'Password must be repeated to confirm it',
  (f: any) => f === password.value || 'Passwords must match',
];

const valid = ref<boolean | null>(null);

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
    <v-form fast-fail v-model="valid" class="dialog-form" @submit.prevent="onSubmit">
      <v-text-field
        type="text"
        id="username"
        v-model="username"
        :rules="usernameRules"
        label="E-mail"
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
      <v-text-field
        type="password"
        id="confirm-password"
        v-model="passwordRepeat"
        :rules="confirmPasswordRules"
        label="Confirm password"
        variant="outlined"
      ></v-text-field>
      <div class="ui-action-container">
        <v-btn class="ui-action ui-action-disabled" @click="$emit('close')">Cancel</v-btn>
        <v-btn class="ui-action" color="primary" :loading="loading" :disabled="!valid" type="submit"
          >Register</v-btn
        >
      </div>
    </v-form>
  </v-card>
</template>

<style scoped></style>
