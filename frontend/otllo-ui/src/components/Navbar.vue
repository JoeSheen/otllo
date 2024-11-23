<script setup>
import { useRouter } from "vue-router";

const router = useRouter();

const props = defineProps({
  username: String,
  userId: String,
});
</script>

<template>
  <div>
    <nav class="flex items-center flex-wrap bg-slate-800 p-6 pl-10 shadow">
      <div>
        <div class="flex items-center flex-shrink-0 mr-8">
          <button @click="router.push('/home')">
            <span
              class="self-center text-3xl font-semibold whitespace-nowrap text-white hover:italic"
              >Otllo</span
            >
          </button>
        </div>
      </div>
      <div class="flex items-center flex-shrink-0 mr-6 w-1/3">
        <input
          type="text"
          placeholder="Search..."
          class="placeholder-gray-500 w-full rounded-full px-3 pl-4 py-1 outline-none"
        />
      </div>
      <div class="absolute right-0 p-6 pr-12">
        <button @click="router.push('/users/' + userId + '/profile')">
          <span
            class="self-center text-xl font-semibold whitespace-nowrap text-white hover:italic"
            >{{ username }}</span
          >
        </button>
      </div>
    </nav>
  </div>
</template>
