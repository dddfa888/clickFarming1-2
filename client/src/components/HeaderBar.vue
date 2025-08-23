<!-- src/components/HeaderBar.vue -->
<template>
  <div class="header-bar" @click="toback">
    <van-icon name="arrow-left" />
    <span class="title">{{ title }}</span>
  </div>
</template>

<script setup>
import { defineProps } from "vue";
import { useRouter } from "vue-router";
defineProps({
  title: String,
});
const router = useRouter();
function toback() {
  router.go(-1);
}
</script>

<style scoped>
.header-bar {
  display: flex;
  align-items: center;
  height: 50px;
  background-color: #0b1e34;
  color: white;
  padding: 0 16px;
  font-size: 16px;
}

@media screen and (min-width: 768px) {
  .header-bar {
    display: flex;
    align-items: center;
    width: 100%;
    height: 50px;
    background-color: #0b1e34;
    color: white;
    padding: 0 16px;
    font-size: 16px;
  }
}
</style>
