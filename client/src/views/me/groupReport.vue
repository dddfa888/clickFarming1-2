<template>
  <div class="company-intro">
    <HeaderBar :title="t('团体')" />
    <!-- Tab 导航栏 -->
    <div class="tab-header">
      <div
        v-for="(tab, index) in tabs"
        :key="index"
        :class="['tab-item', { active: activeTab === index }]"
        @click="activeTab = index"
      >
        {{ tab }}
      </div>
    </div>

    <!-- 内容区域 -->
    <div class="tab-content">
      <div v-show="true">
        <div
          v-if="filteredList.length === 0"
          class="group-report"
          style="color: #fff; padding: 10px"
        >
          {{ t("暂无数据") }}
        </div>
        <div v-for="item in filteredList" :key="item.uid" class="group-report">
          <div class="group-member-item">
            <img src="../../assets/img/3-DHl9k9P6.png" />
            <div>
              <p>{{ item.loginAccount }}</p>
              <p>{{ t("剩余") }}: {{ item.accountBalance.toFixed(2) }} $</p>
            </div>
          </div>
          <div>
            <p>SĐT: {{ item.phoneNumber }}</p>
            <p>{{ t("注册时间") }}: {{ item.createTime }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { getGroupReport } from "../../api/index.js";
import HeaderBar from "../../components/HeaderBar.vue";
import { useI18n } from "vue-i18n";
const { t } = useI18n();

// 定义 tab 数据
const tabs = ref([t("第1级"), t("第2级"), t("第3级"), t("第4级")]);
const activeTab = ref(0); // 当前选中的 tab

const grouplist = ref([]);

getGroupReport().then((res) => {
  grouplist.value = res.data || [];
});

// 计算当前层级要展示的数据
const filteredList = computed(() => {
  return grouplist.value.filter(
    (item) => item.hierarchy === activeTab.value + 1
  );
});
</script>

<style scoped>
.company-intro {
  background: url("../../assets/img/background-D7o_xTde.png") no-repeat center
    center;
  background-size: cover;
  height: 100vh;
  overflow-y: auto;
}

.tab-header {
  display: flex;
  border: 1px solid #fff;
}

.tab-item {
  flex: 1;
  text-align: center;
  padding: 10px;
  cursor: pointer;
  border: 1px solid #fff;
  color: #fff;
  transition: 0.3s;
}

.tab-item.active {
  color: #3b82f6;
}

.tab-content {
  border-radius: 6px;
  margin-top: 10px;
  margin-bottom: 70px;
}

.group-report {
  padding: 10px;
  display: flex;
  color: #fff;
  font-size: 12px;
  align-items: center;
  justify-content: space-between;
}

.group-member-item {
  display: flex;
  align-items: center;
}

.group-member-item > img {
  width: 50px;
  height: 50px;
  border-radius: 50%;
}

@media screen and (min-width: 768px) {
  .company-intro {
    background: url("../../assets/img/background-D7o_xTde.png") no-repeat center
      center;
    background-size: cover;
    width: 540px;
    margin: 0 auto;
    height: 100vh;
    overflow-y: auto;
  }

  .tab-header {
    display: flex;
    border: 1px solid #fff;
  }

  .tab-item {
    flex: 1;
    text-align: center;
    padding: 10px;
    cursor: pointer;
    border: 1px solid #fff;
    color: #fff;
    transition: 0.3s;
  }

  .tab-item.active {
    color: #3b82f6;
  }

  .tab-content {
    border-radius: 6px;
    margin-top: 10px;
    margin-bottom: 70px;
  }

  .group-report {
    padding: 10px;
    display: flex;
    color: #fff;
    font-size: 12px;
    align-items: center;
    justify-content: space-between;
  }

  .group-member-item {
    display: flex;
    align-items: center;
  }

  .group-member-item > img {
    width: 50px;
    height: 50px;
    border-radius: 50%;
  }
}
</style>
