<script>
import { usePostStore } from "../store/post";

export default {
  data() {
    return {
      postDetails: null,
      isLoading: true,
    };
  },
  mounted() {
    this.loadData();
  },
  methods: {
    loadData: async function () {
      const store = usePostStore();

      const postData = await store.getAllPosts();
      if (postData) {
        this.postDetails = postData;
        this.isLoading = false;
      }
    },

    openModal: function () {
      console.log("openModal");
    },
  },
};
</script>

<template>
  <div class="bg-slate-100 p-4">
    <div v-if="!isLoading">
      <div>
        <div>
          <input
            type="button"
            class="cursor-pointer"
            value="What's on your mind?"
            v-on:click="openModal()"
          />
        </div>
      </div>
      <div>
        <div v-if="postDetails.details.length > 0" class="h-20 overflow-y-auto">
          <li v-for="details in postDetails.details">
            {{ details }}
          </li>
        </div>
        <div v-else>
          <span>No Posts Found</span>
        </div>
      </div>
    </div>
  </div>
</template>
