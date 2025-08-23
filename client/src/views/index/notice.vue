<!-- src/views/CompanyIntro.vue -->
<template>
  <div class="company-intro">
    <HeaderBar :title="t('通知邮件')" />
    <div class="content-box">
      <div class="notice-box" v-for="item in noticeList" :key="item.uid">
        <div class="notice-title">
          <p>{{ item.title }}</p>
          <p>{{ item.createTime }}</p>
        </div>
        <p v-html="item.content"></p>
      </div>
    </div>
  </div>
</template>

<script setup>
import HeaderBar from "../../components/HeaderBar.vue";
import { ref } from "vue";
import { getUserNotification } from "../../api/index";
import { useI18n } from "vue-i18n";
const { t } = useI18n();
const noticeList = ref([]);
getUserNotification().then((res) => {
  console.log(res.data);
  noticeList.value = res.data;
});
</script>

<style scoped>
.company-intro {
  background: url("../../assets/img/background-D7o_xTde.png") no-repeat center
    center;
  height: 100vh;
  overflow-y: auto;
  /* padding-bottom: 70px; */
}

.content-box {
  margin: 8px;
  padding: 10px;
  color: white;
  font-size: 12px;
  overflow: auto;
  border-top: 1px solid #dad8da;
}

.notice-box {
  border-bottom: 1px solid #dad8da;
}

.notice-title {
  display: flex;
}
.notice-title p {
  padding-right: 12px;
}

@media screen and (min-width: 768px) {
  .company-intro {
    background: url("../../assets/img/background-D7o_xTde.png") no-repeat center
      center;
    height: 100vh;
    overflow-y: auto;
    width: 540px;
    margin: 0 auto;
    /* padding-bottom: 70px; */
  }

  .content-box {
    margin: 8px;
    padding: 10px;
    color: white;
    font-size: 12px;
    overflow: auto;
    border-top: 1px solid #dad8da;
  }

  .notice-box {
    border-bottom: 1px solid #dad8da;
  }

  .notice-title {
    display: flex;
  }
  .notice-title p {
    padding-right: 12px;
  }
}
</style>
