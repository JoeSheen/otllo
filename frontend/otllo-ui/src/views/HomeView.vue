<script setup>
import { useAuthStore } from "../store/auth";

const store = useAuthStore();
</script>

<template>
  <div class="p-10">
    <h1>HOME</h1>
    <div>
      <p>store: {{ store.user }}</p>
    </div>
  </div>
</template>
