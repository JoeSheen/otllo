<script>
import { request } from "../api/axios-api";

export default {
  name: "SignupView",
  data: function () {
    return {
      signupDetails: {},
    };
  },
  methods: {
    handleSignup() {},
  },
};
</script>

<template>
  <form name="signup-form">
    <div>Sign Up</div>
  </form>
  <button @click="$router.push('/')">Login</button>
</template>
