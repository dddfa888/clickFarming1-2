<template>
  <div class="distribution-history">
    <div class="header">
      <h2>
        {{ t("发行历史") }}
        <span class="total-amount">{{ totalAmount }} $</span>
      </h2>
      <div class="provider">
        {{ t("数据提供者 Ingka Centres") }}
        <span class="remaining">{{ t("剩余") }}($)</span>
      </div>
    </div>

    <div class="history-list" @scroll="onScroll">
      <div v-for="(item, index) in displayedOrders" :key="index" class="history-item">
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

        <button
          v-if="item.processStatus === 'Waiting'"
          class="send-button"
          @click="Sendbutton(item.id)"
        >{{ t("发送分发") }}</button>
      </div>

      <div v-if="loading" class="loading-text">{{ t("加载中...") }}</div>
      <div v-else-if="noMoreData" class="loading-text">{{ t("已加载全部数据") }}</div>
    </div>

    <ProductModal v-if="showModal" :id="id" @close="showModal = false" @pay="handlePay" />
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import ProductModal from "../../components/ProductModal.vue";
import {
  getOrderList,
  sendDistribution,
  getUserGradeAndBalanceAndDiscount
} from "../../api/index.js";
import { useI18n } from "vue-i18n";

const { t } = useI18n();
const totalAmount = ref(0);
const id = ref(null);
const showModal = ref(false);

const displayedOrders = ref([]); // 当前显示的订单列表
let pageNum = 1; // 当前页码
const pageSize = 20; // 每页条数
const loading = ref(false);
const noMoreData = ref(false);

const Sendbutton = productId => {
  showModal.value = true;
  id.value = productId;
};

const handlePay = () => {
  console.log("用户点击支付");
  showModal.value = false;
  sendDistribution(id.value).then(res => {
    console.log(res);
    if (res.code === 200) {
      globalThis.$notify({
        message: t(res.msg),
        type: "success",
        duration: 2000
      });
      // 可以刷新当前分页数据或者重新请求第一页
      displayedOrders.value = [];
      pageNum = 1;
      noMoreData.value = false;
      loadOrders();
    } else {
      globalThis.$notify({
        message: t(res.msg),
        type: "error",
        duration: 2000
      });
    }
  });
};

// 请求分页订单
const loadOrders = async () => {
  if (loading.value || noMoreData.value) return;
  loading.value = true;

  try {
    const res = await getOrderList(pageNum, pageSize);
    const userRes = await getUserGradeAndBalanceAndDiscount();
    totalAmount.value = userRes.data.userBalance;
    const orders = res.rows || [];

    if (orders.length < pageSize) {
      noMoreData.value = true;
    }

    displayedOrders.value.push(...orders);
    pageNum++;
  } catch (err) {
    console.error("加载订单失败", err);
  } finally {
    loading.value = false;
  }
};

// 滚动加载
const onScroll = e => {
  const target = e.target;
  if (target.scrollTop + target.clientHeight >= target.scrollHeight - 50) {
    loadOrders();
  }
};

onMounted(async () => {
  // 获取用户余额
  const userRes = await getUserGradeAndBalanceAndDiscount();
  totalAmount.value = userRes.data.userBalance;

  // 加载第一页订单
  loadOrders();
});
</script>

<style scoped>
.distribution-history {
  font-family: Arial, sans-serif;
  max-width: 800px;
  margin: 0 auto;
  height: 100vh;
  color: #fff;
  padding: 30px;
  background: url("../../assets/img/BG-kho-B9q9tfZS.png") no-repeat center
    center fixed;
}
.loading-text {
  text-align: center;
  padding: 10px;
  color: #fff;
}

.header {
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #e0e0e0;
}

.header h2 {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 0;
  font-weight: 500;
  font-size: 16px;
}

.total-amount {
  font-weight: bold;
}

.provider {
  display: flex;
  justify-content: space-between;
  margin-top: 5px;
  font-size: 14px;
}

.remaining {
  font-weight: bold;
}

.history-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
  height: calc(100vh - 150px);
  overflow-y: auto;
}

.history-item {
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 15px;
  background-color: transparent;
  backdrop-filter: blur(5px);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.item-header {
  margin-bottom: 15px;
}

.time-code {
  display: flex;
  flex-direction: column;
  font-size: 14px;
}

.product-info {
  margin-bottom: 15px;
}

.product-name {
  display: flex;
  align-items: center;
  margin-bottom: 8px;
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
}

.calculation {
  margin: 15px 0;
  padding: 15px;
  background-color: transparent;
  backdrop-filter: blur(5px);
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

.send-button {
  width: 30%;
  padding: 5px;
  font-size: 12px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.send-button:hover {
  background-color: #2980b9;
}

@media screen and (min-width: 768px) {
  .distribution-history {
    font-family: Arial, sans-serif;
    max-width: 480px;
    margin: 0 auto;
    height: 100vh;
    color: #fff;
    padding: 30px;
    background: url("../../assets/img/BG-kho-B9q9tfZS.png") no-repeat fixed;
    background-size: cover;
  }
  .loading-text {
    text-align: center;
    padding: 10px;
    color: #fff;
  }

  .header {
    margin-bottom: 20px;
    padding-bottom: 10px;
    border-bottom: 1px solid #e0e0e0;
  }

  .header h2 {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin: 0;
    font-weight: 500;
    font-size: 16px;
  }

  .total-amount {
    font-weight: bold;
  }

  .provider {
    display: flex;
    justify-content: space-between;
    margin-top: 5px;
    font-size: 14px;
  }

  .remaining {
    font-weight: bold;
  }

  .history-list {
    width: 100%;
    display: flex;
    flex-direction: column;
    gap: 20px;
    height: calc(100vh - 150px);
    overflow-y: auto;
    scrollbar-width: none;
  }

  .history-item {
    border: 1px solid #e0e0e0;
    border-radius: 8px;
    padding: 15px;
    background-color: transparent;
    backdrop-filter: blur(5px);
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  }

  .item-header {
    margin-bottom: 15px;
  }

  .time-code {
    display: flex;
    flex-direction: column;
    font-size: 14px;
  }

  .product-info {
    margin-bottom: 15px;
  }

  .product-name {
    display: flex;
    align-items: center;
    margin-bottom: 8px;
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
  }

  .calculation {
    margin: 15px 0;
    padding: 15px;
    background-color: transparent;
    backdrop-filter: blur(5px);
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

  .send-button {
    width: 30%;
    padding: 5px;
    font-size: 12px;
    background-color: #3498db;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }

  .send-button:hover {
    background-color: #2980b9;
  }
}
</style>
