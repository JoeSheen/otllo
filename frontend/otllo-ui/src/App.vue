<script setup>
import { useAuthStore } from "./store";
import Navbar from "./components/Navbar.vue";

const store = useAuthStore();
</script>

<template>
  <main>
    <div v-if="store.isAuthenticated()">
      <Navbar :username="store.user.username" :userId="store.user.id" />
    </div>
    <router-view></router-view>
  </main>
</template>
