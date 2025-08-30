<template>
  <div class="company-intro" @scroll="handleScroll">
    <HeaderBar :title="t('奖励记录')" />
    <div class="transaction-list">
      <div v-for="(transaction, index) in transactions" :key="index" class="transaction-item">
        <div class="transaction-amount">{{ t("金钱数额") }}:+{{ formatAmount(transaction.amount) }}</div>
        <div class="transaction-time">{{ t("描述") }}：{{ t(transaction.description) }}</div>
        <div class="transaction-balance">{{ t("剩余") }}: {{ formatAmount(transaction.accountBack) }}</div>
      </div>
    </div>

    <!-- 底部提示 -->
    <div v-if="loading" class="loading">{{ t("加载中...") }}</div>
    <div v-if="noMore" class="no-more">{{ t("没有更多了") }}</div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import HeaderBar from "../../components/HeaderBar.vue";
import { getRewardHistory } from "../../api/index.js";
import { useI18n } from "vue-i18n";
const { t } = useI18n();

const transactions = ref([]);
const pageNum = ref(1);
const pageSize = ref(10);
const loading = ref(false);
const noMore = ref(false);

const formatAmount = amount => {
  return amount.toFixed(2).replace(".", ",") + " $";
};

// 加载数据
const loadData = async () => {
  if (loading.value || noMore.value) return;
  loading.value = true;
  try {
    const res = await getRewardHistory(pageNum.value, pageSize.value);
    if (res.rows && res.rows.length > 0) {
      transactions.value.push(...res.rows);
      pageNum.value++;
    } else {
      noMore.value = true;
    }
  } finally {
    loading.value = false;
  }
};

// 滚动触底检测
const handleScroll = e => {
  const el = e.target;
  if (el.scrollTop + el.clientHeight >= el.scrollHeight - 50) {
    loadData();
  }
};

// 首次加载
loadData();
</script>

<style scoped>
.company-intro {
  background: url("../../assets/img/background-D7o_xTde.png") no-repeat center
    center;
  height: 100vh;
  overflow-y: auto;
}

.transaction-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
  padding: 10px;
}

.transaction-item {
  padding: 10px;
  display: flex;
  color: #fff;
  font-size: 13px;
  gap: 5px;
  flex-direction: column;
  border-bottom: 1px solid #e5e7eb;
}

.transaction-amount {
  grid-column: 2;
  grid-row: 1;
}

.transaction-balance {
  grid-column: 1 / span 2;
  grid-row: 2;
  color: #fff;
  font-size: 14px;
}

.transaction-status {
  grid-column: 2;
  grid-row: 2;
  text-align: right;
  color: #2ecc71;
}

.loading,
.no-more {
  text-align: center;
  color: #fff;
}

@media screen and (min-width: 768px) {
  .company-intro {
    background: url("../../assets/img/background-D7o_xTde.png") no-repeat center
      center;
    height: 100vh;
    width: 540px;
    overflow-y: auto;
    margin: 0 auto;
  }

  .transaction-list {
    display: flex;
    flex-direction: column;
    gap: 15px;
    padding: 10px;
  }

  .transaction-item {
    padding: 10px;
    display: flex;
    color: #fff;
    font-size: 13px;
    gap: 5px;
    flex-direction: column;
    border-bottom: 1px solid #e5e7eb;
  }

  .transaction-amount {
    grid-column: 2;
    grid-row: 1;
  }

  .transaction-balance {
    grid-column: 1 / span 2;
    grid-row: 2;
    color: #fff;
    font-size: 14px;
  }

  .transaction-status {
    grid-column: 2;
    grid-row: 2;
    text-align: right;
    color: #2ecc71;
  }

  .loading,
  .no-more {
    text-align: center;
    color: #fff;
  }
}
</style>
