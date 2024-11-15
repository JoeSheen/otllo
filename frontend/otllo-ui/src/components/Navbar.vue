<script setup>
import { useRouter } from "vue-router";

const router = useRouter();

const props = defineProps({
  username: String,
});
</script>

<template>
  <nav>
    <div>
      <div>
        <button @click="router.push('/')">
          <span>Otllo</span>
        </button>
      </div>
      <div>
        <input placeholder="Search..." />
      </div>
      <div>
        <button>
          <span>{{ username }}</span>
        </button>
      </div>
    </div>
  </nav>
</template>
