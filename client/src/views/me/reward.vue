<template>
  <div class="company-intro">
    <HeaderBar :title="t('奖励记录')" />
    <div class="transaction-list">
      <div v-for="(transaction, index) in transactions" :key="index" class="transaction-item">
        <div class="transaction-amount">{{ t("金钱数额") }}:+{{ formatAmount(transaction.amount) }}</div>
        <div class="transaction-time">{{ t("描述") }}：{{ t(transaction.description) }}</div>
        <div class="transaction-balance">{{ t("剩余") }}: {{ formatAmount(transaction.accountBack) }}</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import HeaderBar from "../../components/HeaderBar.vue";
import { getRewardHistory } from "../../api/index.js";
import { useI18n } from "vue-i18n";
const { t } = useI18n();
const transactions = ref([]);

const formatAmount = amount => {
  return amount.toFixed(2).replace(".", ",") + " $";
};

getRewardHistory().then(res => {
  console.log(res.data);
  transactions.value = res.data;
});
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
}
</style>
