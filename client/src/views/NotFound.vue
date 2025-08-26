<!-- views/NotFound.vue -->
<template>
  <div class="not-found">
    <h1>{{ $t("404 页面不存在") }}</h1>
    <p>{{ $t("您访问的页面未找到") }}</p>
  </div>
</template>

<script setup>
import { useI18n } from "vue-i18n";
const { t } = useI18n();
</script>

<style scoped>
.not-found {
  padding: 50px;
  text-align: center;
  color: #666;
}
</style>
