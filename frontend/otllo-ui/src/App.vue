<script setup>
import { useAuthStore } from "./store/auth";
import Navbar from "./components/Navbar.vue";
import Footer from "./components/Footer.vue";

const store = useAuthStore();
</script>

<template>
  <main>
    <div v-if="store.isAuthenticated()" class="pb-20">
      <div class="fixed w-full">
        <Navbar :username="store.user.username" :userId="store.user.id" />
      </div>
    </div>
    <router-view></router-view>
    <!--div v-if="store.isAuthenticated()">
      <Footer />
    </div-->
  </main>
</template>
