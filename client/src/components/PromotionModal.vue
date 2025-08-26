<template to="body">
  <div v-if="visible" class="modal-overlay" @click="close">
    <div class="modal-content">
      <img
        v-if="locale === 'zh'"
        src="../assets/img/mercado_promo.jpg"
        class="modal-image"
      />
      <img
        v-else-if="locale === 'vi'"
        src="../assets/img/edqpdm4z34c737vtlhg8.jpg"
        class="modal-image"
      />
      <img v-else src="../assets/img/1-英.jpg" class="modal-image" />
    </div>
    <button class="modal-close" @click="close">×</button>
  </div>
</template>

<script setup>
import { ref, defineExpose } from "vue";
import { useLangStore } from "../store/useLangStore";
import { storeToRefs } from "pinia";

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
