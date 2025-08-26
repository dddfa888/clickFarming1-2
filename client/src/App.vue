<template>
  <div>
    <RouterView />
    <!-- 只在指定页面显示 TabBar -->
    <TabBar v-if="showTabBar" />
  </div>
</template>
<script setup>
import { useRoute } from "vue-router";
import { computed } from "vue";
import TabBar from "./components/TabBar.vue";

const route = useRoute();
const tabbarPaths = [
  "/",
  "/warehouse",
  "/cskh",
  "/me",
  "/company",
  "/rule",
  "/cooperation",
  "/notice",
  "/withdraw",
  "/orderHistory",
  "/orderdetail",
  "/address",
  "/bankInfo",
  "/groupReport",
  "/rewardHistory",
  "/depositHistory",
  "/withdrawHistory"
];

const showTabBar = computed(() => tabbarPaths.includes(route.path));
</script>


<style scoped>
html,
body,
#app {
  margin: 0;
  padding: 0;
  overflow: hidden;
  height: 100vh;
  width: 100vw;
}

html,
body {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

@media screen and (min-width: 768px) {
  html,
  body,
  #app {
    margin: 0;
    padding: 0;
    overflow: hidden;
    height: 100%;
    width: 540px;
  }
}

/* 仅隐藏滚动条但仍能滚动 */
::-webkit-scrollbar {
  display: none;
}

html,
body {
  -ms-overflow-style: none; /* IE和Edge */
  scrollbar-width: none; /* Firefox */
}

html,
body {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body,
html,
* {
  cursor: default;
}
</style>
