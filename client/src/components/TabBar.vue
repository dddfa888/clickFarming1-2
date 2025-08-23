<template>
  <div class="tabbar">
    <div
      class="tabbar-item"
      v-for="(item, index) in tabs.slice(0, 2)"
      :key="index"
      :class="{ active: currentRoute === item.path }"
      @click="navigate(item.path, item.name)"
    >
      <img :src="currentRoute === item.path ? item.iconActive : item.icon" class="tabbar-icon" />
      <span>{{ item.name }}</span>
    </div>

    <div class="tabbar-middle" @click="onCenterClick">
      <img :src="centerIcon" class="center-icon" />
    </div>

    <div
      class="tabbar-item"
      v-for="(item, index) in tabs.slice(2)"
      :key="index + 2"
      :class="{ active: currentRoute === item.path }"
      @click="navigate(item.path, item.name)"
    >
      <img :src="currentRoute === item.path ? item.iconActive : item.icon" class="tabbar-icon" />
      <span>{{ item.name }}</span>
    </div>
  </div>
  <!-- 客服弹窗组件 -->
</template>


<script setup>
import { ref, computed } from "vue";
import { useRouter, useRoute } from "vue-router";
import { useI18n } from "vue-i18n";

const { t } = useI18n();
const getImageUrl = name =>
  new URL(`../assets/img/${name}`, import.meta.url).href;

const router = useRouter();
const route = useRoute();
const showCustomerService = ref(false);

const tabs = computed(() => [
  {
    name: t("首页"),
    path: "/",
    icon: getImageUrl("home.svg"),
    iconActive: getImageUrl("home-active.svg")
  },
  {
    name: t("仓库"),
    path: "/warehouse",
    icon: getImageUrl("warehouse.svg"),
    iconActive: getImageUrl("warehouse-active.svg")
  },
  {
    name: "CSKH", // 客服，不跳转
    path: "",
    icon: getImageUrl("service.svg"),
    iconActive: getImageUrl("service-active.svg")
  },
  {
    name: t("我"),
    path: "/me",
    icon: getImageUrl("user.svg"),
    iconActive: getImageUrl("user-active.svg")
  }
]);

const centerIcon = getImageUrl("center.svg");
const currentRoute = computed(() => route.path);

const navigate = (path, name = "") => {
  if (name === "CSKH") {
    if (window.Tawk_API && typeof window.Tawk_API.maximize === "function") {
      window.Tawk_API.maximize();
    } else {
      console.warn("Tawk API not ready yet.");
    }
    return;
  }

  if (path.startsWith("http")) {
    window.open(path, "_blank");
  } else if (path !== currentRoute.value) {
    router.push(path);
  }
};

const onCenterClick = () => {
  router.push("/orderdetail");
};
</script>


<style scoped>
.tabbar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  max-width: 100%;
  height: 60px;
  background: url("../assets/img/backgroundnavigate-WyoR1lk0.png") no-repeat
    center center;
  background-size: cover;
  display: flex;
  justify-content: space-around;
  align-items: center;
  z-index: 999;
  padding: 0 10px;
  margin: 0 auto;
}

.tabbar-item {
  flex: 1;
  display: flex;
  font-size: 12px;
  flex-direction: column;
  align-items: center;
  color: #fff;
  cursor: pointer;
}

.tabbar-item.active {
  color: #fff;
  font-weight: bold;
}

.tabbar-icon {
  width: 24px;
  height: 24px;
  margin-bottom: 4px;
}

.tabbar-middle {
  width: 70px;
  height: 70px;
  background-color: #0e7edb;
  border-radius: 50%;
  margin-top: -90px;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px solid #fff;
  cursor: pointer;
}

.center-icon {
  width: 40px;
  height: 40px;
}

/* PC端适配 */
@media (min-width: 768px) {
  .tabbar {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    max-width: 520px;
    height: 70px;
    background: url("../assets/img/backgroundnavigate-WyoR1lk0.png") no-repeat
      center center;
    background-size: cover;
    display: flex;
    justify-content: space-around;
    align-items: center;
    z-index: 999;
    padding: 0 10px;
    margin: 0 auto;
  }

  .tabbar-item {
    flex: 1;
    display: flex;
    font-size: 12px;
    flex-direction: column;
    align-items: center;
    color: #fff;
    cursor: pointer;
  }

  .tabbar-item.active {
    color: #fff;
    font-weight: bold;
  }

  .tabbar-icon {
    width: 24px;
    height: 24px;
    margin-bottom: 4px;
  }

  .tabbar-middle {
    width: 70px;
    height: 70px;
    background-color: #0e7edb;
    border-radius: 50%;
    margin-top: -90px;
    display: flex;
    justify-content: center;
    align-items: center;
    border: 1px solid #fff;
    cursor: pointer;
  }

  .center-icon {
    width: 40px;
    height: 40px;
  }
}
</style>
