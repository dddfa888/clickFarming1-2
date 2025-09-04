<!-- src/components/ProductModal.vue -->
<template>
  <div class="modal-backdrop" @click.self="close">
    <div class="modal-box">
      <h3 class="modal-title">{{ t("产品信息") }}</h3>

      <div class="info">
        <p>{{ t("时间") }}: {{ data.createTime }}</p>
        <p>{{ t("代码") }}: {{ data.productId }}</p>
        <div class="product">
          <img :src="data.productImageUrl" class="product-img" />
          <div class="product-desc">
            <p>{{ data.productName }}</p>
            <p>{{ data.unitPrice }} $ x {{ data.number }}</p>
          </div>
        </div>

        <p>{{ t("分配总额") }}: {{ data.totalAmount }} $</p>
        <p>{{ t("利润") }}: {{ data.profit }} $</p>
        <p class="highlight">{{ t("退款金额") }}: {{ data.refundAmount }} $</p>
      </div>

      <div class="actions">
        <button class="btn btn-pay" @click="onPay">{{ t("支付") }}</button>
        <button class="btn btn-cancel" @click="close">{{ t("取消") }}</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, toRefs, ref } from "vue";
import { getOrderById } from "../api/index.js";
import { useI18n } from "vue-i18n";
const props = defineProps({
  id: {
    type: [String, Number],
    required: true
  }
});

const { id } = toRefs(props);
const { t } = useI18n();
const data = ref({});
getOrderById(id.value).then(res => {
  data.value = res.data;
});

const emit = defineEmits(["close", "pay"]);
const close = () => emit("close");
const onPay = () => emit("pay");
</script>

<style scoped>
.modal-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.6);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 999;
}

.modal-box {
  width: 90%;
  max-width: 350px;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(8px);
  border-radius: 12px;
  padding: 20px;
  color: #fff;
  box-shadow: 0 0 12px rgba(0, 0, 0, 0.3);
}

.modal-title {
  text-align: center;
  font-weight: bold;
  font-size: 18px;
  margin-bottom: 15px;
}

.product {
  display: flex;
  margin: 10px 0;
}

.product-img {
  width: 70px;
  height: 70px;
  object-fit: cover;
  margin-right: 10px;
  border-radius: 6px;
}

.product-desc {
  flex: 1;
  font-size: 13px;
}

.highlight {
  color: orange;
  font-weight: bold;
}

.actions {
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
}

.btn {
  flex: 1;
  margin: 0 5px;
  padding: 8px;
  border: none;
  border-radius: 6px;
  font-size: 14px;
  cursor: pointer;
}

.btn-pay {
  background-color: #0094ff;
  color: white;
}

.btn-cancel {
  background-color: #ff4d4f;
  color: white;
}

@media screen and (min-width: 768px) {
  .modal-backdrop {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0, 0, 0, 0.6);
    display: flex;
    width: 540px;
    margin: 0 auto;
    align-items: center;
    justify-content: center;
    z-index: 999;
  }

  .modal-box {
    width: 90%;
    max-width: 350px;
    background: rgba(255, 255, 255, 0.1);
    backdrop-filter: blur(8px);
    border-radius: 12px;
    padding: 20px;
    color: #fff;
    box-shadow: 0 0 12px rgba(0, 0, 0, 0.3);
  }

  .modal-title {
    text-align: center;
    font-weight: bold;
    font-size: 18px;
    margin-bottom: 15px;
  }

  .product {
    display: flex;
    margin: 10px 0;
  }

  .product-img {
    width: 70px;
    height: 70px;
    object-fit: cover;
    margin-right: 10px;
    border-radius: 6px;
  }

  .product-desc {
    flex: 1;
    font-size: 13px;
  }

  .highlight {
    color: orange;
    font-weight: bold;
  }

  .actions {
    display: flex;
    justify-content: space-around;
    margin-top: 20px;
  }

  .btn {
    flex: 1;
    margin: 0 5px;
    padding: 8px;
    border: none;
    border-radius: 6px;
    font-size: 14px;
    cursor: pointer;
  }

  .btn-pay {
    background-color: #0094ff;
    color: white;
  }

  .btn-cancel {
    background-color: #ff4d4f;
    color: white;
  }
}
</style>
