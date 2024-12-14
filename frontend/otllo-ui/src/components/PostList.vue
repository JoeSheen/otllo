<script lang="ts">
import { usePostStore } from "../store/post";
import { CollectionDetails } from "../types/CollectionDetails.interface";
import { PostDetails } from "../types/PostDetails.interface";
import formatDate from "../util/DateFormatter";

export default {
  data() {
    return {
      postDetails: {} as CollectionDetails<PostDetails>,
      isLoading: true,
      store: usePostStore(),
      info: "Latest Posts",
    };
  },
  mounted() {
    this.loadData();
  },
  methods: {
    loadData: async function () {
      const postData = await this.store.getAllPosts();
      if (postData) {
        this.postDetails = postData;
        this.isLoading = false;
      }
    },

    openModal: function () {
      console.log("openModal called");
    },

    isPostAuthor: function (username: string): boolean {
      return this.store.isPostAuthor(username);
    },

    formatDate(s: string): string {
      return formatDate(s);
    },
  },
};
</script>

<template>
  <div>
    <div v-if="!isLoading">
      <div>
        <div>
          <input
            type="button"
            class="cursor-pointer"
            value="Create a new post!"
            v-on:click="openModal()"
          />
        </div>
      </div>
      <div>
        <div>
          <div class="pt-2 pb-1 text-2xl font-semibold">
            <span>{{ info }}</span>
          </div>
        </div>
        <div>
          <div v-if="postDetails?.details.length > 0" class="">
            <div v-for="details in postDetails.details">
              <div class="border rounded-lg bg-slate-50 p-2 mb-4">
                <div class="pb-0.5">
                  <span class="font-medium text-lg">{{ details.title }}</span>
                </div>
                <div class="pb-0.5">
                  <span>{{ details.body }}</span>
                </div>
                <div>
                  <span
                    >{{ details.author }}
                    {{ formatDate(details.createdAt) }}</span
                  >
                  <div v-if="details.createdAt !== details.updatedAt">
                    {{ formatDate(details.updatedAt) }}
                  </div>
                </div>
                <div v-if="isPostAuthor(details.author)">
                  <input
                    type="button"
                    class="cursor-pointer"
                    value="Edit"
                    v-on:click="openModal()"
                  />
                </div>
              </div>
            </div>
          </div>
          <div v-else>
            <span>No Posts Found</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
