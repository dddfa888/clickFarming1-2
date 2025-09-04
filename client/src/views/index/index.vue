<template>
  <div class="home" :style="{ backgroundImage: `url(${bgImage})` }">
    <!-- 顶部用户信息 -->
    <div class="user-info">
      <div class="user-info-avatar">
        <img class="avatar" :src="userInfo.headImg || defaultAvatar" @click="triggerUpload" />
        <input
          ref="fileInput"
          type="file"
          accept="image/*"
          style="display:none"
          @change="handleFileChange"
        />
        <!--</el-upload> -->
        <div class="user-details">
          <div>{{ t("你好") }}</div>
          <div class="username">{{ userInfo.loginAccount || "" }}</div>
        </div>
      </div>
      <div class="user-info-balance" @click="router.push('/notice')">
        <img src="../../assets/img/tongzhi.png" alt="通知图标" />
        <span class="notifyNum">{{ notifyNum || 0 }}</span>
      </div>
    </div>

    <!-- 可用余额 -->
    <div class="balance-price">
      <div class="balance-display">
        <div
          style="
            display: flex;
            align-items: center;
            justify-content: space-around;
          "
        >
          <img src="../../assets/img/可用余额.png" alt />
          <p class="balance-label">{{ t("可用余额") }}</p>
        </div>
        <p class="balance-amount">{{ userInfo.accountBalance || 0 }}$</p>
      </div>
      <div class="action-buttons">
        <div
          @click="onDeposit"
          style="
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
          "
        >
          <img src="../../assets/img/提款.png" alt />
          <p>{{ t("提款") }}</p>
        </div>
        <div
          @click="onWithdraw"
          style="
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
          "
        >
          <img src="../../assets/img/收款.png" alt />
          <p>{{ t("取款") }}</p>
        </div>
      </div>
    </div>

    <!-- 视频 -->
    <video class="videos" controls muted loop width="100%" height="200px" :src="videoUrl"></video>

    <h5>{{ t("概述:") }} INGKA CENTRES</h5>
    <!-- 功能按钮 -->
    <div class="info-buttons">
      <div
        class="btn"
        v-for="item in infoBtns"
        :key="item.label"
        @click="handleButtonClick(item.icon)"
      >
        <img style="width: 35px; height: 35px" class="icon-img" :src="item.icon" />
        <div style="text-align: center; margin-top: 5px">{{ item.label }}</div>
      </div>
    </div>

    <!-- 会员等级 -->
    <div class="title">{{ t("会员级别") }}</div>
    <div v-for="item in Recordlist" :key="item.id" class="member-level">
      <div v-if="level != item.id" @click="handleUpgrade(item.id)" class="level-title">{{ t("开锁") }}</div>
      <div class="level-info">
        <div class="col">
          {{ t("升级费") }}
          <br />
          {{ item.joinCost }}$
        </div>
        <div class="col">
          {{ t("折扣") }}
          <br />
          {{ item.minBonus }}%-{{ item.maxBonus }}%
        </div>
        <!--<div class="col">
          {{ t("分配数量") }}
          <br />
          {{ item.buyProdNum }}
          <div class="badge-row">
            <span class="badge">{{ item.gradeName }}</span>
            <span v-if="level === item.id" class="lock-icon">{{ t("当前等级") }}</span>
          </div>
        </div>-->
        <div class="card">
          <!-- 顶部右侧徽章区 -->
          <div class="badge-box" v-if="level === item.id || item.gradeName">
            <span class="badge">{{ item.gradeName }}</span>
            <span v-if="level === item.id" class="lock-icon">{{ t("当前等级") }}</span>
          </div>

          <!-- 主体内容 -->
          <div class="content">
            <span>{{ t("分配数量") }}</span>
            <span>{{ item.buyProdNum }}</span>
          </div>
        </div>
      </div>
    </div>
    <!-- 奖励获得者名单 -->
    <div class="title">{{ t("奖励获得者名单") }}</div>
    <div class="reward">
      <div class="reward-list">
        <div v-for="(reward, index) in rewards" :key="index" class="reward-item">
          <span class="reward-date">{{ reward.date }}</span>
          <span class="reward-message">
            {{
            t("rewardMessage", {
            username: reward.username,
            amount: formatAmount(reward.amount),
            })
            }}
          </span>
        </div>
      </div>
    </div>
    <PromotionModal ref="promoRef" />

    <BaseModal
      v-model="showModal"
      :title="t('确认升级')"
      @confirm="handleConfirm"
      @cancel="handleCancel"
    >
      <template #body>
        <p>{{ t("您想升级到这个级别吗?") }}</p>
      </template>
    </BaseModal>
  </div>
</template>

<script setup>
import { onMounted, ref, computed } from "vue";
import { useRouter } from "vue-router";
import PromotionModal from "../../components/PromotionModal.vue";
import BaseModal from "../../components/BaseModal.vue";
import company from "../../assets/img/company.png";
import rule from "../../assets/img/rule.png";
import cooperation from "../../assets/img/cooperation.png";
import notice from "../../assets/img/notice.png";
import {
  getUserInfo,
  getMemberRecord,
  updateGrade,
  updateAvatar,
  updateUserSimpleFront,
  getCustomerService,
  getUserMessage
} from "../../api/index.js";
import { useI18n } from "vue-i18n";
import { notify } from "../../utils/notify.js";
import defaultAvatar from "../../assets/img/mylogo.png";
const bgImage = new URL("../../assets/img/bg.png", import.meta.url).href;
const videoUrl = new URL("../../assets/videos/INGKA.mp4", import.meta.url).href;

const promoRef = ref();
const router = useRouter();
const Recordlist = ref([]);
const level = ref(null);
const userInfo = ref({});
const { t } = useI18n();
const notifyNum = ref(0);
const showModal = ref(false);
const uid = ref(null);
const avatarUrl = ref(""); // 初始头像
const fileInput = ref(null);
const uploadFile = ref(null);

const configValue = ref("");

onMounted(async () => {
  getCustomerService().then(res => {
    configValue.value = res.data.configValue;
  });

  getUserMessage().then(res => {
    if (res.code === 200) {
      notifyNum.value = res.data;
    }
  });
});

const triggerUpload = () => {
  fileInput.value.click();
};

// 手动处理文件变化
const handleFileChange = async e => {
  const file = e.target.files[0];
  if (!file) return;

  // 简单校验
  // if (!file.type.startsWith("image/")) {
  //   ElMessage.error("只能上传图片格式");
  //   return;
  // }
  // if (file.size / 1024 / 1024 > 2) {
  //   ElMessage.error("图片大小不能超过 2MB");
  //   return;
  // }

  // 调用接口上传
  try {
    const formData = new FormData();
    formData.append("file", file);

    const res = await updateAvatar(formData); // 你自己接口，返回格式根据接口调整
    if (res.code === 200) {
      globalThis.$notify({
        message: t(res.msg),
        type: "success",
        duration: 2000
      });
      // 假设返回头像url为 res.data.avatarUrl，替换为你接口返回字段
      userInfo.value.headImg = res.url;
      updateUserSimpleFront({ headImg: res.url }).then(res => {
        console.log(res);
      });
    } else {
      globalThis.$notify({
        message: t(res.msg),
        type: "warning",
        duration: 2000
      });
    }
  } catch (error) {
    globalThis.$notify({
      message: t(res.msg),
      type: "warning",
      duration: 2000
    });
    console.error(error);
  }

  // 清空选择框，避免同一文件无法触发change
  e.target.value = "";
};

// 这里为了兼容 el-upload 的 before-upload，但真正不使用自动上传
const handleBeforeUpload = () => false;

const getImageUrl = path => {
  return new URL(`../../assets/{path}`, import.meta.url).href;
};

const handleUpgrade = id => {
  uid.value = id;
  showModal.value = true;
};

getUserInfo().then(res => {
  console.log(res.data);
  userInfo.value = res.data;
});
getMemberRecord().then(res => {
  if (res.code === 200) {
    Recordlist.value = res.data.userGrade || "";
    level.value = res.data.level;
  }
});

const handleConfirm = () => {
  let gradeId = uid.value;
  updateGrade(uid.value).then(res => {
    console.log(res);
    if (res.code === 200) {
      globalThis.$notify({
        message: t(res.msg),
        type: "success",
        duration: 2000
      });
    } else {
      globalThis.$notify({
        message: t(res.msg),
        type: "warning",
        duration: 2000
      });
    }
  });
};

const handleCancel = () => {
  console.log("取消了");
};

// 生成随机用户名
const generateUsername = () => {
  const prefix = [
    "rhu",
    "pea",
    "pon",
    "dur",
    "cra",
    "wil",
    "geo",
    "mar",
    "jan",
    "tom"
  ];
  const suffix = ["b", "r", "e", "n", "y", "k", "m", "s", "d", "f"];
  const randomPrefix = prefix[Math.floor(Math.random() * prefix.length)];
  const randomSuffix = suffix[Math.floor(Math.random() * suffix.length)];

  return `${randomPrefix}****${randomSuffix}`;
};

// 生成随机金额 (100-1999.99之间的随机金额)
const generateAmount = () => {
  const whole = 100 + Math.floor(Math.random() * 1900); // 100-1999
  const decimal = Math.floor(Math.random() * 100); // 0-99

  return `${whole.toLocaleString("de-DE")}.${decimal
    .toString()
    .padStart(2, "0")}`;
};

// 格式化金额显示
const formatAmount = amount => {
  return amount.replace(",", "."); // 转换为点分隔的格式
};

// 初始奖励数据
const rewards = ref(
  Array(6)
    .fill()
    .map(() => ({
      date: new Date().toISOString().split("T")[0],
      username: generateUsername(),
      amount: generateAmount()
    }))
);

// 更新6条数据
const updateRewards = () => {
  rewards.value = Array(6)
    .fill()
    .map(() => ({
      date: new Date().toISOString().split("T")[0],
      username: generateUsername(),
      amount: generateAmount()
    }));
};

// 定时更新数据
onMounted(() => {
  promoRef.value?.show();
  updateRewards();
  setInterval(updateRewards, 1000); // 每5秒更新一次
});

const user = {
  name: "Linh198",
  balance: "0.00 $"
};
const infoBtns = computed(() => [
  { label: t("公司简介"), icon: notice },
  { label: t("基本原则"), icon: rule },
  { label: t("开发合作"), icon: cooperation },
  { label: t("通知邮件"), icon: company }
]);

const handleButtonClick = icon => {
  if (icon === notice) {
    router.push({ path: "/company" });
  } else if (icon === rule) {
    router.push({ path: "/rule" });
  } else if (icon === cooperation) {
    router.push({ path: "/cooperation" });
  } else {
    router.push({ path: "/notice" });
  }
};

const onDeposit = () => {
  //console.log("执行提款操作");
  window.open(configValue.value, "_blank");
};

const onWithdraw = () => {
  console.log("执行取款操作");
  router.push({ path: "/withdraw" });
};

function withTimeout(promise, timeout = 5000) {
  return Promise.race([
    promise,
    new Promise((_, reject) =>
      setTimeout(() => reject(new Error("请求超时")), timeout)
    )
  ]);
}

onMounted(async () => {
  promoRef.value?.show();
  updateRewards();

  try {
    const [userRes, memberRes, notifyRes] = await Promise.all([
      withTimeout(getUserInfo(), 5000),
      withTimeout(getMemberRecord(), 5000)
    ]);

    // 设置用户信息
    userInfo.value = userRes.data;

    // 设置等级信息
    if (memberRes.code) {
      Recordlist.value = memberRes.data.userGrade;
      level.value = memberRes.data.level;
    }
  } catch (error) {
    console.error("加载数据失败：", error);
  }

  setInterval(updateRewards, 1000);
});
</script>

<style scoped>
.home {
  padding: 10px;
  min-height: 100vh;
  color: white;
  padding-bottom: 120px;
}

.user-info {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px;
}

.user-info-avatar {
  display: flex;
  align-items: center;
  gap: 10px;
}

.avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  object-fit: cover;
}

.user-details {
  line-height: 1.4;
}

.username {
  font-size: 18px;
  font-weight: bold;
}

.user-info-balance img {
  width: 25px;
  height: 25px;
  cursor: pointer;
}
.notifyNum {
  position: relative;
  top: -20px;
  left: -12px;
  display: inline-block;
  background-color: rgb(244, 67, 54);
  height: 20px;
  text-align: center;
  border-radius: 10px;
  line-height: 21px;
  padding: 0px 5px;
}

.balance-price {
  display: flex;
  max-width: 100%;
  margin: 10px auto;
  align-items: center;
  justify-content: space-between;
}

.balance-display {
  width: 110px;
  height: 90%;
  text-align: center;
  border: 1px solid #fff;
  border-radius: 20px;
}

.reward {
  padding: 20px;
  background-color: transparent;
  backdrop-filter: blur(5px);
  -webkit-backdrop-filter: blur(5px);
  border-radius: 20px;
  font-size: 10px;
  max-width: 600px;
  margin: 0 auto;
  border: 1px solid #7a797d;
}

.reward-list {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
}

.reward-item {
  background-color: transparent;
  backdrop-filter: blur(5px);
  -webkit-backdrop-filter: blur(5px);
  border-radius: 8px;
  display: flex;
  transition: all 0.3s ease;
  animation: fadeIn 0.5s ease-out;
  font-size: 12px;
  color: #fff;
  padding: 5px;
}

.reward-date {
  color: #fff;
}

.reward-message {
  color: #fff;
  white-space: nowrap; /* 不换行 */
  overflow: hidden; /* 超出隐藏 */
  text-overflow: ellipsis; /* 超出显示省略号 */
  max-width: 220px; /* 根据布局调整宽度 */
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }

  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.balance-label {
  font-size: 14px;
}

.balance-amount {
  font-size: 14px;
  font-weight: bold;
  color: #fff;
}

.action-buttons {
  display: flex;
  gap: 10px;
}

.action-btn {
  flex: 1;
  padding: 10px 0;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
  transition: all 0.2s;
}

.withdraw {
  background-color: #4caf50;
  color: white;
}

.deposit {
  background-color: #2196f3;
  color: white;
}

.action-btn:hover {
  opacity: 0.9;
  transform: translateY(-1px);
}

.action-btn:active {
  transform: translateY(0);
}

.videos {
  width: 100%;
  border-radius: 8px;
  margin: 15px 0;
  z-index: 1;
  position: relative;
}

.info-buttons {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  margin: 20px 0;
  padding: 10px;
  font-size: 14px;
  background-color: transparent;
  backdrop-filter: blur(5px);
  -webkit-backdrop-filter: blur(5px);
  border-radius: 10px;
  border: 1px solid #7a797d;
}

.btn {
  display: flex;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
  transition: transform 0.2s;
  padding: 8px;
}

.btn:hover {
  transform: translateY(-3px);
}

.icon-img {
  width: 30px;
  height: 30px;
  margin-bottom: 5px;
}

.member-level {
  font-size: 15px;
  background-color: transparent;
  backdrop-filter: blur(5px);
  -webkit-backdrop-filter: blur(5px);
  border-radius: 10px;
  border: 1px solid #7a797d;
  margin-bottom: 15px;
  padding: 15px;
}

.title {
  font-weight: bold;
  margin-bottom: 10px;
}

.level-info {
  display: flex;
  padding: 10px;
  justify-content: space-between;
}

.col {
  position: relative;
  text-align: center;
  flex: 1;
}

.card {
  position: relative;
  border-radius: 8px;
  /* 其他样式 */
}

.badge-box {
  position: absolute;
  top: -6.86667vw;
  right: -4.86667vw;
  display: flex;
  align-items: center;
  gap: 1.6vw;
  z-index: 1;
}

.badge {
  background-color: #b13330;
  color: #fff;
  font-size: 12px;
  padding: 2px 6px;
  border-radius: 4px;
  white-space: nowrap;
}

.lock-icon {
  background-color: #ffe747;
  color: #000;
  font-size: 12px;
  padding: 2px 6px;
  border-radius: 4px;
  white-space: nowrap;
}

.content {
  display: flex;
  flex-direction: column;
  align-items: center;
}

@media screen and (min-width: 768px) {
  .home {
    max-width: 500px;
    margin: 0 auto;
    padding: 20px;
    padding-bottom: 100px;
  }

  .user-info {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 10px;
  }

  .user-info-avatar {
    display: flex;
    align-items: center;
    gap: 10px;
  }

  .avatar {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    object-fit: cover;
  }

  .user-details {
    line-height: 1.4;
  }

  .username {
    font-size: 18px;
    font-weight: bold;
  }

  .user-info-balance img {
    width: 25px;
    height: 25px;
    cursor: pointer;
  }
  .notifyNum {
    position: relative;
    top: -20px;
    left: -12px;
    display: inline-block;
    background-color: rgb(244, 67, 54);
    height: 20px;
    text-align: center;
    border-radius: 10px;
    line-height: 21px;
    padding: 0px 5px;
  }
  .balance-price {
    display: flex;
    max-width: 100%;
    margin: 10px auto;
    align-items: center;
    justify-content: space-between;
  }

  .balance-display {
    width: 110px;
    height: 90%;
    text-align: center;
    border: 1px solid #fff;
    border-radius: 20px;
  }

  .reward {
    padding: 20px;
    background-color: transparent;
    backdrop-filter: blur(5px);
    -webkit-backdrop-filter: blur(5px);
    border-radius: 20px;
    font-size: 10px;
    max-width: 600px;
    margin: 0 auto;
    border: 1px solid #7a797d;
  }

  .reward-list {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 12px;
  }

  .reward-item {
    background-color: transparent;
    backdrop-filter: blur(5px);
    -webkit-backdrop-filter: blur(5px);
    border-radius: 8px;
    display: flex;
    transition: all 0.3s ease;
    animation: fadeIn 0.5s ease-out;
    font-size: 14px;
    color: #fff;
    padding: 5px;
  }

  .reward-date {
    color: #fff;
  }

  .reward-message {
    color: #fff;
    white-space: nowrap; /* 不换行 */
    overflow: hidden; /* 超出隐藏 */
    text-overflow: ellipsis; /* 超出显示省略号 */
    max-width: 220px; /* 根据布局调整宽度 */
  }

  @keyframes fadeIn {
    from {
      opacity: 0;
      transform: translateY(10px);
    }

    to {
      opacity: 1;
      transform: translateY(0);
    }
  }

  .balance-label {
    font-size: 14px;
  }

  .balance-amount {
    font-size: 14px;
    font-weight: bold;
    color: #fff;
  }

  .action-buttons {
    display: flex;
    gap: 10px;
  }

  .action-btn {
    flex: 1;
    padding: 10px 0;
    border: none;
    border-radius: 4px;
    font-size: 16px;
    cursor: pointer;
    transition: all 0.2s;
  }

  .withdraw {
    background-color: #4caf50;
    color: white;
  }

  .deposit {
    background-color: #2196f3;
    color: white;
  }

  .action-btn:hover {
    opacity: 0.9;
    transform: translateY(-1px);
  }

  .action-btn:active {
    transform: translateY(0);
  }

  .videos {
    width: 100%;
    border-radius: 8px;
    margin: 15px 0;
    z-index: 1;
    position: relative;
  }

  .info-buttons {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 10px;
    margin: 20px 0;
    padding: 10px;
    font-size: 14px;
    background-color: transparent;
    backdrop-filter: blur(5px);
    -webkit-backdrop-filter: blur(5px);
    border-radius: 10px;
    border: 1px solid #7a797d;
  }

  .btn {
    display: flex;
    flex-direction: column;
    align-items: center;
    cursor: pointer;
    transition: transform 0.2s;
    padding: 8px;
  }

  .btn:hover {
    transform: translateY(-3px);
  }

  .icon-img {
    width: 30px;
    height: 30px;
    margin-bottom: 5px;
  }

  .member-level {
    font-size: 15px;
    background-color: transparent;
    backdrop-filter: blur(5px);
    -webkit-backdrop-filter: blur(5px);
    border-radius: 10px;
    border: 1px solid #7a797d;
    margin-bottom: 15px;
    padding: 15px;
  }

  .title {
    font-weight: bold;
    margin-bottom: 10px;
  }

  .level-info {
    display: flex;
    padding: 10px;
    justify-content: space-between;
  }

  .col {
    text-align: center;
    flex: 1;
  }

  .badge {
    display: inline-block;
    width: 20%;
    position: fixed;
    top: 0;
    right: 0;
    background: #b13330;
    color: #ccc;
    border-radius: 5px;
    padding: 2px 5px;
    margin-left: 5px;
    font-size: 12px;
  }

  .lock-icon {
    display: inline-block;
    width: 20%;
    position: fixed;
    top: 0;
    right: 22%;
    color: #000;
    background: #ffe747;
    border-radius: 5px;
    padding: 2px 5px;
    margin-left: 5px;
    font-size: 12px;
  }

  .content {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
}
</style>
