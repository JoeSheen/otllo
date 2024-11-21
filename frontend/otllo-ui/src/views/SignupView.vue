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
  <div class="py-32">
    <div class="mt-0">
      <h1 class="text-center font-bold tracking-tight text-gray-900 text-3xl">
        Otllo
      </h1>
    </div>
    <div class="-mt-6">
      <h2
        class="mt-10 text-center text-2xl/9 font-bold tracking-tight text-gray-900"
      >
        Create your account
      </h2>
    </div>
    <div class="flex flex-row min-h-full justify-center -mt-4 px-8">
      <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
        <form name="signup-form" class="space-y-6">
          <div class="flex flex-row">
            <div class="pr-2 w-1/2">
              <label
                for="firstName"
                class="block text-sm/6 font-medium text-gray-900"
                >First Name</label
              >
              <div class="mt-2">
                <input
                  id="firstName"
                  type="text"
                  autocomplete="given-name"
                  class="block w-full rounded-md border-0 py-1.5 text-gray-900 pl-2 shadow-sm ring-1 ring-inset ring-gray-300 sm:text-sm/6"
                  v-model="signupDetails.firstName"
                />
              </div>
            </div>
            <div class="w-1/2">
              <label
                for="lastName"
                class="block text-sm/6 font-medium text-gray-900"
                >Last Name</label
              >
              <div class="mt-2">
                <input
                  id="lastName"
                  type="text"
                  autocomplete="family-name"
                  class="block w-full rounded-md border-0 py-1.5 text-gray-900 pl-2 shadow-sm ring-1 ring-inset ring-gray-300 sm:text-sm/6"
                  v-model="signupDetails.lastName"
                />
              </div>
            </div>
          </div>
          <div class="flex flex-row">
            <div class="pr-2 w-1/2">
              <label
                for="dateOfBirth"
                class="block text-sm/6 font-medium text-gray-900"
                >Date of Birth</label
              >
              <div class="mt-2">
                <input
                  id="dateOfBirth"
                  type="date"
                  autocomplete="bday"
                  class="block w-full rounded-md border-0 py-1.5 text-gray-900 pl-2 shadow-sm ring-1 ring-inset ring-gray-300 sm:text-sm/6 pr-2"
                  v-model="signupDetails.dateOfBirth"
                />
              </div>
            </div>
            <div class="w-1/2">
              <label
                for="gender"
                class="block text-sm/6 font-medium text-gray-900"
                >Gender</label
              >
              <div class="mt-2">
                <Dropdown
                  id="gender-dropdown"
                  :params="genderOptions"
                  :eventName="'setGender'"
                  class="block w-full rounded-md border-0 py-1.5 text-gray-900 pl-2 shadow-sm ring-1 ring-inset ring-gray-300 sm:text-sm/6"
                  @setGender="(g) => (signupDetails.gender = g)"
                />
              </div>
            </div>
          </div>
          <div class="flex flex-row">
            <div class="pr-2 w-1/2">
              <label
                for="email"
                class="block text-sm/6 font-medium text-gray-900"
                >Email</label
              >
              <div class="mt-2">
                <input
                  id="email"
                  type="email"
                  autocomplete="email"
                  class="block w-full rounded-md border-0 py-1.5 text-gray-900 pl-2 shadow-sm ring-1 ring-inset ring-gray-300 sm:text-sm/6"
                  v-model="signupDetails.email"
                />
              </div>
            </div>
            <div class="w-1/2">
              <label
                for="phoneNumber"
                class="block text-sm/6 font-medium text-gray-900"
                >Phone Number</label
              >
              <div class="mt-2">
                <input
                  id="phoneNumber"
                  type="tel"
                  autocomplete="tel"
                  class="block w-full rounded-md border-0 py-1.5 text-gray-900 pl-2 shadow-sm ring-1 ring-inset ring-gray-300 sm:text-sm/6"
                  v-model="signupDetails.phoneNumber"
                />
              </div>
            </div>
          </div>
          <div class="flex flex-row">
            <div class="pr-2 w-1/2">
              <label
                for="username"
                class="block text-sm/6 font-medium text-gray-900"
                >Username</label
              >
              <div class="mt-2">
                <input
                  id="username"
                  type="text"
                  autocomplete="username"
                  class="block w-full rounded-md border-0 py-1.5 text-gray-900 pl-2 shadow-sm ring-1 ring-inset ring-gray-300 sm:text-sm/6"
                  v-model="signupDetails.username"
                />
              </div>
            </div>
            <div class="w-1/2">
              <label
                for="password"
                class="block text-sm/6 font-medium text-gray-900"
                >Password</label
              >
              <div class="mt-2">
                <input
                  id="password"
                  type="password"
                  autocomplete="new-password"
                  class="block w-full rounded-md border-0 py-1.5 text-gray-900 pl-2 shadow-sm ring-1 ring-inset ring-gray-300 sm:text-sm/6"
                  v-model="signupDetails.password"
                />
              </div>
            </div>
          </div>
          <div>
            <button
              type="submit"
              v-on:click.prevent="handleSignup()"
              class="flex w-full justify-center rounded-md bg-slate-800 px-3 py-1.5 text-sm/6 font-semibold text-white shadow-sm"
            >
              Sign Up
            </button>
          </div>
        </form>
      </div>
    </div>
    <div>
      <div class="mt-10 text-center text-sm/6 text-gray-800">
        Already have an account?
        <button class="font-semibold" @click="$router.push('/')">Login</button>
      </div>
    </div>
  </div>
</template>
