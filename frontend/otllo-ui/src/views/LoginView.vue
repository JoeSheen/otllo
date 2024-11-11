<script>
import { useVuelidate } from "@vuelidate/core";
import { required } from "@vuelidate/validators";
import { request } from "../api/axios-api";

export default {
  name: "LoginView",
  setup: function () {
    return { v$: useVuelidate() };
  },
  data: function () {
    return {
      loginDetails: {
        username: "",
        password: "",
      },
    };
  },
  validations: function () {
    return {
      loginDetails: {
        username: { required },
        password: { required },
      },
    };
  },
  methods: {
    async handleLogin() {
      const isFormCorrect = await this.v$.$validate();
      if (isFormCorrect) {
        // for now this will do
        request("POST", "auth/login", this.loginDetails).then((value) =>
          console.log(value)
        );
      }
    },
  },
};
</script>

<template>
  <div>Login</div>
  <form name="login-form">
    <div>
      <label for="username">Username</label>
      <input id="username" type="text" v-model="loginDetails.username" />
      <div v-if="v$.loginDetails.username.$error">Username is required</div>
    </div>
    <div>
      <label for="password">Password</label>
      <input id="password" type="password" v-model="loginDetails.password" />
      <div v-if="v$.loginDetails.password.$error">Password is required</div>
    </div>
    <button type="submit" v-on:click.prevent="handleLogin()">Login</button>
  </form>

  <button @click="$router.push('/signup')">Sign Up</button>
</template>
