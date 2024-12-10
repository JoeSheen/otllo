<script setup>
const props = defineProps({
  info: String,
  posts: Object,
});
</script>

<template>
  <div>
    <div>
      <div>
        {{ info }}
      </div>
    </div>
    <div>
      <div v-if="posts.details.length > 0">
        <div v-for="details in posts.details">
          <div>
            <div>
              {{ details.title }}
            </div>
            <div>
              {{ details.body }}
            </div>
            <div>
              {{ details.author }} {{ details.createdAt }}
              <div v-if="details.createdAt !== details.updatedAt">
                {{ details.updatedAt }}
              </div>
            </div>
          </div>
          <br />
        </div>
      </div>
      <div v-else>
        <span>No Posts Found</span>
      </div>
    </div>
  </div>
</template>
