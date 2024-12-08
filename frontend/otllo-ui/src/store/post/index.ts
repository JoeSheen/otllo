import { defineStore } from "pinia";
import { useAuthStore } from "../auth";
import { request } from "../../api/axios-api";

export const usePostStore = defineStore("post", {
  state: () => ({
    posts: null,
  }),
  actions: {
    async getAllPosts(searchValue: string, pageNumber = 0, pageSize = 25) {
      const token = useAuthStore()?.token;
      const url = 'posts?pageNumber=0&pageSize=25&searchValue=""';
      this.posts = (await request("GET", url, null, token)).data;
      return this.posts;
    },
  },
});
