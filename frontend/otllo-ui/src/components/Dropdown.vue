<script setup>
import { ref } from "vue";

const props = defineProps({
  params: Array,
});

let selected = null;
props.params.forEach((el) => {
  if (el.default) {
    selected = ref(el.value);
  }
});

const options = ref(props.params);
</script>

<template>
  <div>
    <select v-model="selected" @mouseup="$emit('setGender', selected)">
      <option v-for="option in options" :value="option.value">
        {{ option.text }}
      </option>
    </select>
  </div>
</template>
