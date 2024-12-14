<script lang="ts">
import { usePostStore } from "../store/post";
import { CollectionDetails } from "../types/CollectionDetails.interface";
import { PostDetails } from "../types/PostDetails.interface";

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
          <div>
            {{ info }}
          </div>
        </div>
        <div>
          <!-- TODO: replace with infinite scroll -->
          <div v-if="postDetails?.details.length > 0">
            <div v-for="details in postDetails.details">
              <div>
                <span>{{ details.title }}</span>
                <div>
                  <span>{{ details.body }}</span>
                </div>
                <div>
                  <span>{{ details.author }} {{ details.createdAt }}</span>
                  <div v-if="details.createdAt !== details.updatedAt">
                    {{ details.updatedAt }}
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
