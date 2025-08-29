<template>
  <div class="distribution-history">
    <HeaderBar :title="t('订单历史')" style="width: 500px" />

    <div class="history-list" @scroll="onScroll">
      <div
          v-for="(item, index) in historyItems"
          :key="index"
          class="history-item"
      >
        <div class="item-header">
          <div class="time-code">
            <span class="time">{{ t("时间") }}: {{ item.createTime }}</span>
            <span class="code">{{ t("代码") }}: {{ item.productId }}</span>
          </div>
        </div>

        <div class="product-info">
          <div class="product-name">
            <img :src="item.productImageUrl" alt />
            <div>{{ item.productName }}</div>
          </div>
          <div class="product-price">
            {{ item.unitPrice }} $
            <span class="quantity">x {{ item.number }}</span>
          </div>
        </div>

        <div class="calculation">
          <div class="calc-row">
            <span>{{ t("分配总额") }}:</span>
            <span class="amount">{{ item.totalAmount }} $</span>
          </div>
          <div class="calc-row">
            <span>{{ t("利润") }}:</span>
            <span class="amount">{{ item.profit }} $</span>
          </div>
          <div class="calc-row">
            <span>{{ t("退款金额") }}:</span>
            <span class="amount highlight">{{ item.refundAmount }} $</span>
          </div>
        </div>
      </div>

      <div v-if="loading" class="loading-text">
        加载中...
      </div>
      <div
          v-else-if="historyItems.length >= total && total > 0"
          class="loading-text"
      >
        已加载全部数据
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import HeaderBar from "../../components/HeaderBar.vue";
import { getOrderHistory } from "../../api/index.js";
import { useI18n } from "vue-i18n";
const { t } = useI18n();

// 数据源
const historyItems = ref<any[]>([]);
const pageNum = ref(1);
const pageSize = ref(20);
const total = ref(0);
const loading = ref(false);

// 加载数据
const loadHistory = async () => {
  if (loading.value) return;
  if (total.value && historyItems.value.length >= total.value) return; // 已加载全部

  loading.value = true;
  try {
    const res = await getOrderHistory(pageNum.value, pageSize.value);
    historyItems.value.push(...res.rows);
    total.value = res.total;
    pageNum.value += 1; // 下一页
  } catch (error) {
    console.error("加载订单历史失败:", error);
  } finally {
    loading.value = false;
  }
};

// 滚动监听
const onScroll = (e: Event) => {
  const target = e.target as HTMLElement;
  // 距离底部 50px 内触发加载下一页
  if (target.scrollTop + target.clientHeight >= target.scrollHeight - 50) {
    loadHistory();
  }
};

onMounted(() => {
  loadHistory();
});
</script>

<style scoped>
.distribution-history {
  background-image: url("../../assets/img/background-D7o_xTde.png");
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center top;
  color: #fff;
  background-attachment: scroll;
  padding-bottom: 70px;
}

.history-list {
  display: flex;
  align-items: center;
  flex-direction: column;
  gap: 20px;
  overflow: auto;
  height: 100vh;
  padding: 10px;
}

.history-item {
  width: 85%;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 10px;
  background-color: transparent;
  backdrop-filter: blur(5px);
  -webkit-backdrop-filter: blur(5px);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.item-header {
  margin-bottom: 15px;
}

.time-code {
  display: flex;
  flex-direction: column;
  font-size: 14px;
  color: #fff;
}

.time {
  margin-bottom: 3px;
}

.code {
  font-family: monospace;
  color: #fff;
}

.product-info {
  margin-bottom: 15px;
}

.product-name {
  font-weight: 500;
  margin-bottom: 8px;
  line-height: 1.4;
  display: flex;
  align-items: center;
}

.product-name > img {
  width: 30%;
  margin-right: 10px;
}

.product-price {
  font-weight: bold;
  text-align: right;
}

.quantity {
  font-weight: normal;
  color: #fff;
}

.calculation {
  margin: 15px 0;
  padding: 15px;
  background-color: transparent;
  backdrop-filter: blur(5px);
  -webkit-backdrop-filter: blur(5px);
  border-radius: 6px;
}

.calc-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
}

.calc-row:last-child {
  margin-bottom: 0;
}

.amount {
  font-weight: bold;
}

.highlight {
  color: #e74c3c;
}

.loading-text {
  color: #fff;
  padding: 10px;
  text-align: center;
  font-size: 14px;
}
</style>
