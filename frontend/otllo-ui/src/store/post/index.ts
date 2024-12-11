import { defineStore } from "pinia";
import { useAuthStore } from "../auth";
import { request } from "../../api/axios-api";
import { CollectionDetails } from "../../types/CollectionDetails.interface";
import { PostDetails } from "../../types/PostDetails.interface";

export const usePostStore = defineStore("post", {
  actions: {
    async getAllPosts(searchValue = "", pageNumber = 0, pageSize = 25) {
      let postDetails = {} as CollectionDetails<PostDetails>;
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
        postDetails = (await request("GET", url, null, token)).data;
      }
      return postDetails;
    },
  },
});
