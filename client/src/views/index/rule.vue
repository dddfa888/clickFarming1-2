<!-- src/views/CompanyIntro.vue -->
<template>
  <div class="company-intro">
    <HeaderBar :title="t('基本原则')" />
    <div class="content-box">
      <p v-html="companyProfile"></p>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import HeaderBar from "../../components/HeaderBar.vue";
import { useI18n } from "vue-i18n";
import { getBasicPrinciple } from "../../api/index";
import { useLangStore } from "../../store/useLangStore";

const { t } = useI18n();
const langStore = useLangStore();
const companyProfile = ref("");

getBasicPrinciple(langStore.locale).then((res) => {
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
  font-size: 15px;
}
.content-box > p {
  margin: 5px;
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
    font-size: 15px;
  }
  .content-box > p {
    margin: 5px;
  }
}
</style>
