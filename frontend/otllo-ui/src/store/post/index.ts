import { defineStore } from "pinia";
import { useAuthStore } from "../auth";
import { request } from "../../api/axios-api";

export const usePostStore = defineStore("post", {
  actions: {
    async getAllPosts(searchValue = "", pageNumber = 0, pageSize = 25) {
      let posts = {};
      const authStore = useAuthStore();
      if (authStore.isAuthenticated()) {
        const url =
          "posts?pageNumber=" +
          pageNumber +
          "&pageSize=" +
          pageSize +
          "&searchValue=" +
          searchValue;
        const token = authStore?.token;
        posts = (await request("GET", url, null, token)).data;
      }
      return posts;
    },
  },
});
