<!-- src/views/CompanyIntro.vue -->
<template>
  <div class="company-intro">
    <HeaderBar :title="t('公司简介')" />
    <div class="content-box">
      <p v-html="companyProfile"></p>
      <p></p>
      <!-- 可将长文本继续补充 -->
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import HeaderBar from "../../components/HeaderBar.vue";
import { getcompanyProfile } from "../../api/index";
import { useLangStore } from "../../store/useLangStore";

import { useI18n } from "vue-i18n";
const { t } = useI18n();
const langStore = useLangStore();
const companyProfile = ref("");
getcompanyProfile(langStore.locale).then((res) => {
  companyProfile.value = res.data.content;
  console.log(res.data.content);
});
</script>

<style scoped>
.company-intro {
  background: url("../../assets/img/background-D7o_xTde.png") no-repeat center
    center;
  height: 100vh;
  overflow-y: auto;
}

.content-box {
  background-color: transparent !important;
  -webkit-backdrop-filter: blur(6px) !important;
  backdrop-filter: blur(6px) !important;
  margin: 12px;
  padding: 12px;
  border-radius: 10px;
  border: 1px solid #7a797d;
  color: white;
  line-height: 1.8;
  font-size: 14px;
  margin-bottom: 80px;
}

@media screen and (min-width: 768px) {
  .company-intro {
    background: url("../../assets/img/background-D7o_xTde.png") no-repeat center
      center;
    width: 540px;
    margin: 0 auto;
    height: 100vh;
    overflow-y: auto;
  }

  .content-box {
    background-color: transparent !important;
    -webkit-backdrop-filter: blur(6px) !important;
    backdrop-filter: blur(6px) !important;
    margin: 12px;
    padding: 12px;
    border-radius: 10px;
    border: 1px solid #7a797d;
    color: white;
    line-height: 1.8;
    font-size: 14px;
    margin-bottom: 80px;
  }
}
</style>
