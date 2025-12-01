<template to="body">
  <div v-if="visible" class="modal-overlay" @click="close">
    <div class="modal-content">
      <img src="../assets/img/modal.jpg" class="modal-image" />
    </div>
    <!--<div class="modal-text">
      <h2 style="color:#D50000">DUBAIMALL CENTER</h2>

      <p style="color:#E53935">{{ t("活动优惠项目") }}</p>

      <h3 style="color:#E53935">{{ t("情人节活动") }}</h3>
      <p style="color:#555555">({{ t("会员参加需输入新的情人节活动码") }})</p>

      <p>{{ t("最低充值 500 美元可获得至少 500 美元奖励") }}</p>
      <p>{{ t("升级为黄金会员：") }}</p>
      <p>{{ t("可领取情人节礼包，最高可达 2,000 美元") }}</p>

      <p>{{ t("升级为白金会员：") }}</p>
      <p>{{ t("可获得 价值 6,000 美元的欧洲旅游套餐") }}</p>

      <p>{{ t("特别奖励：") }}</p>

      <p>{{ t("会员升级为钻石会员：") }}</p>
      <p>{{ t("可获得 价值 10,000 美元的钻石专属礼包") }}</p>

      <p style="color:#000000">{{ t("DUBAIMALL CENTER敬呈") }}!</p>

      <p style="color:#555555">{{ t("请您经常关注并更新平台资讯") }}</p>
      <p style="color:#555555">{{ t("以免错过我们的优惠活动") }}!!!</p>
    </div>-->

    <button class="modal-close" @click="close">×</button>
  </div>
</template>

<script setup>
import { ref, defineExpose } from "vue";
import { useLangStore } from "../store/useLangStore";
import { storeToRefs } from "pinia";
import { useI18n } from "vue-i18n";

const { t } = useI18n();
const visible = ref(false);
const show = () => (visible.value = true);
const close = () => (visible.value = false);
const langStore = useLangStore();
const { locale } = storeToRefs(langStore);

defineExpose({ show });
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.7);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999;
}

@keyframes fadeIn {
  from {
    transform: scale(0.9);
    opacity: 0;
  }

  to {
    transform: scale(1);
    opacity: 1;
  }
}

.modal-content {
  position: relative;
  max-width: 90%;
  border-radius: 10px;
  overflow: hidden;
  animation: fadeIn 0.3s ease-in-out;
}

.modal-image {
  display: block;
  width: 100%;
  height: auto;
  max-height: 70vh;
  object-fit: contain;
  z-index: 99;
  border-radius: 10px;
  margin-bottom: 13vh;
}

.modal-text {
  position: absolute;
  bottom: 27%;
  left: 0;
  right: 0;
  padding: 20px;
  color: #333333;
  font-size: 2.5vw;
  text-align: center;
  z-index: 99;
}

.modal-close {
  position: absolute;
  bottom: 15vh; /* 相对于视口高度 */
  left: 50%;
  transform: translateX(-50%);
  background: rgba(0, 0, 0, 0.7);
  color: #fff;
  font-size: 5vw;
  border-radius: 50%;
  width: 10vw;
  height: 10vw;
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #fff;
}

@media (max-width: 320px) {
  .modal-close {
    bottom: 12vh;
    font-size: 5vw;
  }
}

@media (min-width: 768px) {
  .modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    width: 540px;
    margin: 0 auto;
    background-color: rgba(0, 0, 0, 0.7);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 9999;
  }

  @keyframes fadeIn {
    from {
      transform: scale(0.9);
      opacity: 0;
    }

    to {
      transform: scale(1);
      opacity: 1;
    }
  }

  .modal-content {
    position: relative;
    max-width: 90%;
    border-radius: 10px;
    overflow: hidden;
    animation: fadeIn 0.3s ease-in-out;
    top: -79px;
  }

  .modal-image {
    display: block;
    width: 100%;
    height: auto;
    max-height: 75vh;
    object-fit: contain;
    z-index: 99;
    border-radius: 10px;
    margin-top: 10vh;
  }

  .modal-text {
    position: absolute;
    bottom: 25%;
    left: 0;
    right: 0;
    padding: 20px;
    color: #333333;
    text-align: center;
    z-index: 99;
    font-size: 13px;
  }

  .modal-close {
    position: absolute;
    bottom: 13vh; /* 相对于视口高度 */
    left: 50%;
    transform: translateX(-50%);
    background: rgba(0, 0, 0, 0.7);
    color: #fff;
    font-size: 3rem;
    border-radius: 50%;
    width: 3rem;
    height: 3rem;
    border: none;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    border: 1px solid #fff;
  }
}
</style>
