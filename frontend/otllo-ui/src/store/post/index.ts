import { defineStore } from "pinia";
import { useAuthStore } from "../auth";
import { request } from "../../api/axios-api";
import { CollectionDetails } from "../../types/CollectionDetails.interface";
import { PostDetails } from "../../types/PostDetails.interface";

export const usePostStore = defineStore("post", {
  state: () => ({
    postDetails: {} as CollectionDetails<PostDetails>,
    authStore: useAuthStore(),
  }),
  actions: {
    async getAllPosts(searchValue = "", pageNumber = 0, pageSize = 25) {
      if (this.authStore.isAuthenticated()) {
        const url =
          "posts?pageNumber=" +
          pageNumber +
          "&pageSize=" +
          pageSize +
          "&searchValue=" +
          searchValue;
        const token = this.authStore?.token;
        this.postDetails = (await request("GET", url, null, token)).data;
      }
      return this.postDetails;
    },

    isPostAuthor(username: string): boolean {
      return username === this.authStore.user.username;
    },
  },
});
