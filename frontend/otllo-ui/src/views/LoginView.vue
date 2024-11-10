<script>
import { request } from "../api/axios-api";

export default {
  name: "LoginView",
  data: function () {
    return {
      loginDetails: {
        username: "",
        password: "",
      },
    };
  },
  methods: {
    handleLogin() {
      if (
        this.loginDetails.username != "" &&
        this.loginDetails.password != ""
      ) {
        // for now this will do
        request("POST", "auth/login", this.loginDetails).then((value) =>
          console.log(value.data)
        );
      }
    },
  },
};
</script>

<template>
  <form name="login-form">
    <div>Login</div>
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

  <button @click="$router.push('/signup/')">Sign Up</button>
</template>
