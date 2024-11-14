<script setup>
import { useVuelidate } from "@vuelidate/core";
import { required } from "@vuelidate/validators";
import { useRouter } from "vue-router";
import { request } from "../api/axios-api";
import { useAuthStore } from "../store";

const router = useRouter();
const store = useAuthStore();

const loginDetails = {
  username: "",
  password: "",
};

const rules = {
  username: {
    required,
  },
  password: {
    required,
  },
};
const v$ = useVuelidate(rules, loginDetails);

async function handleLogin() {
  const isFormCorrect = await this.v$.$validate();
  if (isFormCorrect) {
    const { access_token, userDetails } = (
      await request("POST", "auth/login", this.loginDetails)
    ).data;
    this.store.user = userDetails;
    this.router.push("/home");
  }
}
</script>

<template>
  <div>
    <div>Login</div>
    <form name="login-form">
      <div>
        <label for="username">Username</label>
        <input id="username" type="text" v-model="loginDetails.username" />
      </div>
      <div>
        <label for="password">Password</label>
        <input id="password" type="password" v-model="loginDetails.password" />
      </div>
      <button type="submit" v-on:click.prevent="handleLogin()">Login</button>
    </form>

    <button @click="$router.push('/signup')">Sign Up</button>
  </div>
</template>
