<script setup>
import { useVuelidate } from "@vuelidate/core";
import { required, email, minValue, minLength } from "@vuelidate/validators";
import { useRouter } from "vue-router";
import { useAuthStore } from "../store";
import Dropdown from "../components/Dropdown.vue";

const router = useRouter();
const store = useAuthStore();

const genderOptions = [
  { text: "Male", value: "MALE", default: false },
  { text: "Female", value: "FEMALE", default: false },
  { text: "Other", value: "OTHER", default: true },
];

const signupDetails = {
  firstName: "",
  lastName: "",
  dateOfBirth: null,
  gender: "",
  email: "",
  phoneNumber: "",
  username: "",
  password: "",
  profileImagePath: null,
  visible: true,
  status: "Hey there! I'm on Otllo",
};

const rules = {
  firstName: {
    required,
  },
  lastName: {
    required,
  },
  dateOfBirth: {
    required,
    minValue: (val) => val < new Date().toISOString(),
  },
  email: {
    required,
    email,
  },
  username: {
    required,
    minLengthValue: minLength(3),
    // TODO: add custom validator to ensure usernae starts with '#'
  },
  password: {
    required,
    minLengthValue: minLength(1), //8),
  },
};
const v$ = useVuelidate(rules, signupDetails);

async function handleSignup() {
  const isFormCorrect = await this.v$.$validate();
  if (isFormCorrect) {
    await this.store.authenticateUser(
      "POST",
      "auth/signup",
      this.signupDetails
    );
    this.router.push("/home");
  }
}
</script>

<template>
  <div>
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
        <input
          id="dateOfBirth"
          type="date"
          v-model="signupDetails.dateOfBirth"
        />
      </div>
      <div>
        <label for="gender-dropdown">Gender</label>
        <Dropdown
          id="gender-dropdown"
          :params="genderOptions"
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
  </div>
</template>
