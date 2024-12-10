<script>
import { usePostStore } from "../store/post";
import PostList from "../components/PostList.vue";

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
  components: {
    PostList,
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
        <PostList
          id="post-list"
          :info="'Latest Posts'"
          :posts="postDetails"
          class="pt-2"
        />
      </div>
    </div>
  </div>
</template>
