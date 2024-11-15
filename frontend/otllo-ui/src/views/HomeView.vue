<script setup>
import Navbar from "../components/Navbar.vue";
import { useAuthStore } from "../store";

const store = useAuthStore();
</script>

<template>
  <div>
    <Navbar :username="store.user.username" />
    <h1>HOME</h1>
    <div>
      <p>store: {{ store.user }}</p>
    </div>
  </div>
</template>
