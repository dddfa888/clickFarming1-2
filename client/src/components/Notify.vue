<template>
  <div class="notify-container">
    <div
      v-for="(item, index) in notices"
      :key="item.id"
      :class="['notify', item.type]"
      :style="{ top: `${index * 70 + 20}px` }"
      @mouseenter="pauseTimer(item)"
      @mouseleave="resumeTimer(item)"
    >
      <div class="notify-content">
        <div class="text-content">
          <strong class="title">{{ item.title }}</strong>
          <p class="message">{{ item.message }}</p>
        </div>
      </div>
      <span class="close-btn" @click="close(item.id)">×</span>
      <div class="progress-bar" :style="{ width: item.progress + '%' }"></div>
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue";

const notices = reactive([]);

const addNotice = options => {
  const id = Date.now() + Math.random();

  const notice = reactive({
    // ✅ 包裹为响应式对象
    id,
    ...options,
    progress: 100,
    timer: null,
    frameId: null,
    startTime: 0,
    remaining: options.duration
  });

  notices.push(notice);

  startTimer(notice);
};

defineExpose({ addNotice });

// 计时动画
const startTimer = item => {
  item.startTime = performance.now();
  item.timer = setTimeout(() => close(item.id), item.duration);
  const updateProgress = timestamp => {
    const elapsed = timestamp - item.startTime;
    const percent = Math.max(100 - (elapsed / item.duration) * 100, 0);
    item.progress = percent;
    if (percent > 0) {
      item.frameId = requestAnimationFrame(updateProgress);
    }
  };
  item.frameId = requestAnimationFrame(updateProgress);
};

const pauseTimer = item => {
  clearTimeout(item.timer);
  cancelAnimationFrame(item.frameId);
  item.remaining = (item.progress / 100) * item.duration;
};

const resumeTimer = item => {
  item.startTime = performance.now();
  item.timer = setTimeout(() => close(item.id), item.remaining);
  const updateProgress = timestamp => {
    const elapsed = timestamp - item.startTime;
    const percent = Math.max(100 - (elapsed / item.remaining) * 100, 0);
    item.progress = percent;
    if (percent > 0) {
      item.frameId = requestAnimationFrame(updateProgress);
    }
  };
  item.frameId = requestAnimationFrame(updateProgress);
};

const close = id => {
  const index = notices.findIndex(n => n.id === id);
  if (index !== -1) notices.splice(index, 1);
};
</script>
<style scoped>
.notify-container {
  position: fixed;
  top: 20px;
  right: 20px;
  z-index: 9999;
}
.notify {
  position: absolute;
  right: 0;
  min-width: 250px;
  padding: 3px 6px;
  margin-bottom: 10px;
  border-radius: 4px;
  color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
  animation: fadeIn 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.notify.success {
  background-color: #67c23a;
}
.notify.warning {
  background-color: #e6a23c;
}
.notify.error {
  background-color: #f56c6c;
}
.notify.info {
  background-color: #409eff;
}
.close-btn {
  cursor: pointer;
  font-size: 18px;
  margin-left: 12px;
}
@keyframes shrink {
  from {
    width: 100%;
  }
  to {
    width: 0%;
  }
}

.progress-bar {
  position: absolute;
  bottom: 0;
  left: 0;
  height: 3px;
  background-color: #fff;
  border-radius: 0 0 4px 4px;
  animation-fill-mode: forwards;
}
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
@media (min-width: 768px) {
  .notify-container {
    position: fixed;
    top: 20px;
    right: 20px;
    z-index: 9999;
  }
  .notify {
    position: absolute;
    right: 0;
    min-width: 250px;
    padding: 3px 6px;
    margin-bottom: 10px;
    border-radius: 4px;
    color: #fff;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
    animation: fadeIn 0.3s ease;
  }
  .notify.success {
    background-color: #67c23a;
  }
  .notify.warning {
    background-color: #e6a23c;
  }
  .notify.error {
    background-color: #f56c6c;
  }
  .notify.info {
    background-color: #409eff;
  }
  .close-btn {
    cursor: pointer;
    font-size: 18px;
    margin-left: 12px;
  }
  @keyframes shrink {
    from {
      width: 100%;
    }
    to {
      width: 0%;
    }
  }

  .progress-bar {
    position: absolute;
    bottom: 0;
    left: 0;
    height: 3px;
    background-color: #fff;
    border-radius: 0 0 4px 4px;
    animation-fill-mode: forwards;
  }
  @keyframes fadeIn {
    from {
      opacity: 0;
      transform: translateY(-10px);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }
}
</style>