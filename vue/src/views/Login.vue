<script setup lang="ts">
import { type Router, useRouter } from "vue-router";
import { ref } from "vue";
import { authService } from "@/services";

const router: Router = useRouter();

const username = ref("");
const password = ref("");

const loginError = ref<boolean>(false);

const usernameRules: any[] = [(f: any) => !!f || "Username is required"];
const passwordRules: any[] = [(f: any) => !!f || "Password is required"];

function onLoginClicked(event: any) {
  event.preventDefault();

  authService
    .login({
      username: username.value,
      password: password.value,
    })
    .then((success) => {
      router.push({ name: "Main" });
    })
    .catch((error) => {
      loginError.value = true;
    });
}
</script>

<template>
  <div class="container">
    <v-sheet class="loginFormContainer pa-12 padding-none" rounded>
      <p class="title">Welcome Back!</p>
      <v-card
        class="padding-1-hor padding-1-ver"
        width="400"
        rounded
        variant="elevated"
      >
        <v-form
          validate-on="submit"
          class="card"
          @submit.prevent="onLoginClicked"
        >
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
          <v-btn type="submit" variant="elevated" size="large">
            <v-icon
              icon="mdi-login"
              size="large"
              start
              variant="elevated"
              color="primary"
            ></v-icon
            >Login</v-btn
          >
        </v-form>
      </v-card>
    </v-sheet>
  </div>

  <!-- Modal to display when login fails: -->
  <v-dialog v-model="loginError" width="400" rounded>
    <v-card>
      <v-card-text>
        The provided combination of username and password is incorrect!
      </v-card-text>
      <v-card-actions>
        <v-btn color="error" block @click="loginError = false">Close</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<style scoped>
.mb-2 {
  margin-bottom: 8px !important;
}
.container {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  justify-content: center;
  height: 100%;
}
.loginFormContainer {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  flex-grow: 1;
}

.card {
  display: flex !important;
  flex-direction: column !important;
  justify-content: center !important;
}

.v-card:deep(button) {
  flex-grow: 0;
}
</style>
