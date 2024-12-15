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

    openModal: function (id?: string) {
      console.log(id);
    },

    isPostAuthor: function (username: string): boolean {
      return this.store.isPostAuthor(username);
    },

    formatDate(dateString: string): string {
      return formatDate(dateString);
    },
  },
};
</script>

<template>
  <div>
    <div v-if="!isLoading">
      <div>
        <div class="px-2">
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
          <div class="pt-2 pb-2 px-2 text-2xl font-semibold">
            <span>{{ info }}</span>
          </div>
        </div>
        <div>
          <div v-if="postDetails?.details.length > 0">
            <div v-for="details in postDetails.details">
              <div class="border rounded-lg bg-slate-50 mb-4">
                <div class="flex flex-row pt-2 pb-0.5">
                  <div class="px-2">
                    <span class="text-lg font-bold">{{ details.title }}</span>
                  </div>
                  <div
                    v-if="isPostAuthor(details.author)"
                    class="m-0 ml-auto pr-4"
                  >
                    <button
                      class="border-2 border-slate-800 bg-slate-800 rounded-full px-4"
                      v-on:click="openModal(details.id)"
                    >
                      <span class="text-center text-white font-medium"
                        >Edit</span
                      >
                    </button>
                  </div>
                </div>
                <hr class="h-px my-1" />
                <div class="pt-0.5 px-2">
                  <span>{{ details.body }}</span>
                </div>
                <div class="flex flex-row pt-2 pb-2">
                  <div class="px-2">
                    <span class="font-medium text-slate-800">{{
                      details.author
                    }}</span>
                  </div>
                  <div class="m-0 ml-auto px-2">
                    <span
                      v-if="details.createdAt !== details.updatedAt"
                      class="font-medium text-slate-800 px-4"
                    >
                      {{ formatDate(details.updatedAt) }}
                    </span>
                    <span v-else class="font-medium text-slate-800 px-4">
                      {{ formatDate(details.createdAt) }}
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div v-else>
            <span class="px-2 pt-2 pb-1 text-xl font-semibold"
              >No Posts Found</span
            >
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
