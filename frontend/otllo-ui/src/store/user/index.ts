import { defineStore } from "pinia";
import { useAuthStore } from "../auth";
import { request } from "../../api/axios-api";

export const useUserStore = defineStore("user", {
  actions: {
    async getUserProfile(id: string) {
      const authStore = useAuthStore();
      let user = null;
      if (authStore.isAuthenticated()) {
        const url = "users/" + id + "/profile";
        const token = authStore?.token;
        user = (await request("GET", url, null, token)).data;
      }
      return user;
    },
  },
});
