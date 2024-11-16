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
    await this.store.authenticateUser("POST", "auth/login", this.loginDetails);
    this.router.push("/home");
  }
}
</script>

<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-36 px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <h1 class="text-center font-bold tracking-tight text-gray-900 text-3xl">
        Otllo
      </h1>
    </div>
    <div>
      <div class="sm:mx-auto sm:w-full sm:max-w-sm -m-6">
        <h2
          class="mt-10 text-center text-2xl/9 font-bold tracking-tight text-gray-900"
        >
          Login to your account
        </h2>
      </div>
      <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
        <form name="login-form" class="space-y-6">
          <div>
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
                v-model="loginDetails.username"
              />
            </div>
          </div>
          <div>
            <div class="flex items-center justify-between">
              <label
                for="password"
                class="block text-sm/6 font-medium text-gray-900"
                >Password</label
              >
            </div>
            <div class="mt-2">
              <input
                id="password"
                type="password"
                autocomplete="current-password"
                class="block w-full rounded-md border-0 py-1.5 text-gray-900 pl-2 shadow-sm ring-1 ring-inset ring-gray-300 sm:text-sm/6"
                v-model="loginDetails.password"
              />
            </div>
          </div>
          <div>
            <button
              type="submit"
              v-on:click.prevent="handleLogin()"
              class="flex w-full justify-center rounded-md bg-slate-800 px-3 py-1.5 text-sm/6 font-semibold text-white shadow-sm"
            >
              Login
            </button>
          </div>
        </form>
      </div>
      <div>
        <div class="mt-10 text-center text-sm/6 text-gray-800">
          Don't have an account?
          <button class="font-semibold" @click="$router.push('/signup')">
            Sign Up
          </button>
        </div>
        <!--div>
          <div class="text-sm">Reset Password</div>
        </div-->
      </div>
    </div>
  </div>
</template>
