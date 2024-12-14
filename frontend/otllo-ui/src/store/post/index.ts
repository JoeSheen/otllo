import { defineStore } from "pinia";
import { useAuthStore } from "../auth";
import { request } from "../../api/axios-api";
import { CollectionDetails } from "../../types/CollectionDetails.interface";
import { PostDetails } from "../../types/PostDetails.interface";

export const usePostStore = defineStore("post", {
  state: () => ({
    PostDetails: {} as CollectionDetails<PostDetails>,
    authStore: useAuthStore(),
  }),
  actions: {
    async getAllPosts(searchValue = "", pageNumber = 0, pageSize = 25) {
      let postDetails = {} as CollectionDetails<PostDetails>;
      if (this.authStore.isAuthenticated()) {
        const url =
          "posts?pageNumber=" +
          pageNumber +
          "&pageSize=" +
          pageSize +
          "&searchValue=" +
          searchValue;
        const token = this.authStore?.token;
        postDetails = (await request("GET", url, null, token)).data;
      }
      return postDetails;
    },

    isPostAuthor(username: string): boolean {
      return username === this.authStore.user.username;
    },
  },
});
