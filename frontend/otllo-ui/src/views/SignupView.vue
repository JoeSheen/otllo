<script>
import { useVuelidate } from "@vuelidate/core";
import { required, minValue, email, minLength } from "@vuelidate/validators";
import { request } from "../api/axios-api";
import Dropdown from "../components/Dropdown.vue";

export default {
  name: "SignupView",
  setup: function () {
    return {
      v$: useVuelidate(),
      Gender: [
        { text: "Male", value: "MALE", default: false },
        { text: "Female", value: "FEMALE", default: false },
        { text: "Other", value: "OTHER", default: true },
      ],
    };
  },
  components: {
    Dropdown,
  },
  data: function () {
    return {
      signupDetails: {
        firstName: "",
        lastName: "",
        dateOfBirth: "",
        gender: "",
        email: "",
        phoneNumber: "",
        username: "",
        password: "",
        profileImagePath: "",
        visible: true,
        status: "Hey there! I'm on Otllo",
      },
    };
  },
  validations: function () {
    return {
      signupDetails: {
        firstName: { required },
        lastName: { required },
        dateOfBirth: {
          required,
          minValue: (value) => value < new Date().toISOString(),
        },
        email: {
          required,
          email,
        },
        username: {
          required,
          minLengthValue: minLength(3),
          // TODO: add cumstom validator to ensure username starts with '#'
        },
        password: {
          required,
          minLengthValue: minLength(1), //8),
        },
      },
    };
  },
  methods: {
    async handleSignup() {
      const isFormCorrect = await this.v$.$validate();
      if (isFormCorrect) {
        request("POST", "auth/signup", this.signupDetails).then((res) => {
          console.log(res.data);
          //this.$router.push("/home");
        });
      }
    },
  },
};
</script>

<template>
  <div>Sign Up</div>
  <form name="signup-form">
    <div>
      <label for="firstName">First Name</label>
      <input id="firstName" type="text" v-model="signupDetails.firstName" />
    </div>
    <div>
      <label for="lastName">Last Name</label>
      <input id="lastName" type="text" v-model="signupDetails.lastName" />
    </div>
    <div>
      <label for="dateOfBirth">Date of Birth</label>
      <input id="dateOfBirth" type="date" v-model="signupDetails.dateOfBirth" />
    </div>
    <div>
      <label for="gender-dropdown">Gender</label>
      <Dropdown
        id="gender-dropdown"
        :params="Gender"
        :eventName="'setGender'"
        @setGender="(g) => (signupDetails.gender = g)"
      />
    </div>
    <div>
      <label for="email">Email</label>
      <input id="email" type="email" v-model="signupDetails.email" />
    </div>
    <div>
      <label for="username">Username</label>
      <input id="username" type="text" v-model="signupDetails.username" />
    </div>
    <div>
      <label for="password">Password</label>
      <input id="password" type="password" v-model="signupDetails.password" />
    </div>

    <button v-on:click.prevent="handleSignup()" type="submit">Sign Up</button>
  </form>

  <button @click="$router.push('/')">Login</button>
</template>
