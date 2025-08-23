<template>
  <div class="distribution-history">
    <HeaderBar :title="t('订单历史')" style="width: 500px" />
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
          <span>{{ t("退款金额") }}:</span>
          <span class="amount highlight">{{ item.refundAmount }} $</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import HeaderBar from "../../components/HeaderBar.vue";
import { getOrderHistory } from "../../api/index.js";
import { useI18n } from "vue-i18n";
const { t } = useI18n();

const totalAmount = ref("532,94");

const historyItems = ref([]);
const showModal = ref(false);

const handlePay = () => {
  console.log("用户点击支付");
  showModal.value = false;
};

getOrderHistory().then(res => {
  console.log(res.rows);
  historyItems.value = res.rows;
});

// 可以添加数据更新逻辑
</script>

<style scoped>
.distribution-history {
  background-image: url("../../assets/img/background-D7o_xTde.png");
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center top;
  color: #fff;
  background-attachment: scroll; /* 移除 fixed，避免 iOS bug */
  padding-bottom: 70px;
}
.history-list {
  display: flex;
  align-items: center;
  flex-direction: column;
  gap: 20px;
  overflow: auto;
  height: 100vh;
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
    background-image: url("../../assets/img/background-D7o_xTde.png");
    background-repeat: no-repeat;
    background-size: cover;
    background-position: center top;
    color: #fff;
    width: 540px;
    margin: 0 auto;
    background-attachment: scroll; /* 移除 fixed，避免 iOS bug */
    padding-bottom: 90px;
  }
  .history-list {
    display: flex;
    align-items: center;
    flex-direction: column;
    gap: 20px;
    overflow: auto;
    height: 100vh;
  }

  ::-webkit-scrollbar {
    display: none;
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
