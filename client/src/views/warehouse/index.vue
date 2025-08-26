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

    <div class="history-list">
      <div v-for="(item, index) in historyItems" :key="index" class="history-item">
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
import { notify } from "../../utils/notify.js";

const { t } = useI18n();
const totalAmount = ref(null);
const id = ref(null);
const historyItems = ref([]);
const showModal = ref(false);

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
      // 刷新页面
      location.reload();
    } else {
      globalThis.$notify({
        message: t(res.msg),
        type: "error",
        duration: 2000
      });
    }
  });
};

getOrderList().then(res => {
  historyItems.value = res.rows;
});

getUserGradeAndBalanceAndDiscount().then(res => {
  console.log(res.data);
  totalAmount.value = res.data.userBalance;
});

onMounted(async () => {
  try {
    const [orderRes, userRes] = await Promise.all([
      getOrderList(),
      getUserGradeAndBalanceAndDiscount()
    ]);
    // 设置订单列表
    historyItems.value = orderRes.rows;

    // 设置用户余额
    totalAmount.value = userRes.data.userBalance;
  } catch (error) {
    console.error("并发加载数据失败", error);
  }
});
// 可以添加数据更新逻辑
</script>

<style scoped>
.distribution-history {
  font-family: Arial, sans-serif;
  max-width: 800px;
  overflow: auto;
  height: 100vh;
  margin: 0 auto;
  background: url("../../assets/img/BG-kho-B9q9tfZS.png") no-repeat center
    center fixed;
  color: #fff;
  padding: 30px;
  padding-bottom: 105px;
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
  color: #fff;
}

.provider {
  display: flex;
  justify-content: space-between;
  margin-top: 5px;
  font-size: 14px;
  color: #fff;
}

.remaining {
  font-weight: bold;
}

.history-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.history-item {
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 15px;
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

.send-button {
  width: 30%;
  padding: 5px;
  font-size: 12px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.send-button:hover {
  background-color: #2980b9;
}

@media (max-width: 600px) {
  .product-name {
    font-size: 14px;
  }

  .time-code {
    font-size: 12px;
  }
}

@media screen and (min-width: 768px) {
  .distribution-history {
    font-family: Arial, sans-serif;
    max-width: 500px;
    overflow: auto;
    height: 100vh;
    margin: 0 auto;
    background: url("../../assets/img/BG-kho-B9q9tfZS.png") no-repeat center
      center fixed;
    background-size: 100% 100%;
    padding-bottom: 30px;
    color: #fff;
    padding: 20px;
    padding-bottom: 105px;
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
    font-size: 18px;
  }

  .total-amount {
    font-weight: bold;
    color: #fff;
  }

  .provider {
    display: flex;
    justify-content: space-between;
    margin-top: 5px;
    font-size: 14px;
    color: #fff;
  }

  .remaining {
    font-weight: bold;
  }

  .history-list {
    display: flex;
    flex-direction: column;
    gap: 20px;
  }

  .history-item {
    border: 1px solid #e0e0e0;
    border-radius: 8px;
    padding: 15px;
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

  .send-button {
    width: 30%;
    padding: 5px;
    font-size: 12px;
    background-color: #3498db;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.2s;
  }

  .send-button:hover {
    background-color: #2980b9;
  }
}
</style>
