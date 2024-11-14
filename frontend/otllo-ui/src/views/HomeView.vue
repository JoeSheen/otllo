<script setup>
import { useAuthStore } from "../store";

const store = useAuthStore();
</script>

<template>
  <div>
    <h1>HOME</h1>
    <div>
      <p>store: {{ store.user }}</p>
    </div>
  </div>
</template>
