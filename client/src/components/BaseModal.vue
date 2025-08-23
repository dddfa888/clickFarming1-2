<template>
  <div v-if="modelValue" class="modal-overlay" @click.self="onCancel">
    <div class="modal-content">
      <h3 class="modal-title">{{ title }}</h3>
      <div class="modal-body">
        <slot name="body"></slot>
      </div>
      <div class="modal-footer">
        <button class="btn cancel" @click="onCancel">{{t("取消")}}</button>
        <button class="btn confirm" @click="onConfirm">{{t("确认")}}</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";
import { useI18n } from "vue-i18n";
const { t } = useI18n();

const props = defineProps({
  modelValue: { type: Boolean, required: true },
  title: { type: String, default: "" },
});

const emits = defineEmits(["update:modelValue", "confirm", "cancel"]);

const onCancel = () => {
  emits("update:modelValue", false);
  emits("cancel");
};

const onConfirm = () => {
  emits("update:modelValue", false);
  emits("confirm");
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
}

.modal-content {
  background: #fff;
  padding: 20px;
  width: 300px;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
  animation: fadeIn 0.3s ease;
}

.modal-title {
  color: #333;
}

.modal-body {
  color: #333;
}

.modal-title {
  margin-top: 0;
  margin-bottom: 15px;
  font-size: 18px;
  text-align: center;
}

.modal-body {
  margin-bottom: 20px;
  text-align: center;
}

.modal-footer {
  display: flex;
  justify-content: space-between;
}

.btn {
  padding: 8px 16px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.cancel {
  background: #eee;
  color: #333;
}

.confirm {
  background: #409eff;
  color: white;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: scale(0.9);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}

@media screen and (min-width: 768px) {
  .modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 9999;
  }

  .modal-content {
    background: #fff;
    padding: 20px;
    width: 300px;
    border-radius: 10px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
    animation: fadeIn 0.3s ease;
  }

  .modal-title {
    margin-top: 0;
    margin-bottom: 15px;
    font-size: 18px;
    text-align: center;
  }

  .modal-body {
    margin-bottom: 20px;
    text-align: center;
  }

  .modal-footer {
    display: flex;
    justify-content: space-between;
  }

  .btn {
    padding: 8px 16px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }

  .cancel {
    background: #eee;
    color: #333;
  }

  .confirm {
    background: #409eff;
    color: white;
  }

  @keyframes fadeIn {
    from {
      opacity: 0;
      transform: scale(0.9);
    }
    to {
      opacity: 1;
      transform: scale(1);
    }
  }
}
</style>
