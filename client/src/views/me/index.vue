<template>
  <div class="user-center">
    <!-- 用户信息头部 -->
    <div class="user-header">
      <div class="avatar">
        <img src="../../assets/img/mylogo.png" alt />
      </div>

      <div class="user-info">
        <p>{{ userInfo.loginAccount }}</p>
        <p class="user-id">ID:{{ userInfo.invitationCode }}</p>
      </div>
    </div>

    <!-- 会员级别 -->
    <div class="user-level-info">
      <div class="balance-info">
        <p class="balance-label">{{ t("剩余") }}</p>
        <p class="balance-amount">{{ userInfo.accountBalance }} $</p>
      </div>
      <div class="member-level">
        <p class="section-label">{{ t("会员级别") }}</p>
        <p class="level-name">{{ userInfo.levelName }}</p>
      </div>
    </div>

    <!-- 功能按钮区域 -->
    <div class="action-buttons">
      <button class="action-btn" @click="handleAction('deposit')">{{ t("提款") }}</button>
      <button class="action-btn" @click="handleAction('withdraw')">{{ t("取款") }}</button>
      <button class="action-btn" @click="handleAction('withdrawHistory')">{{ t("提款记录") }}</button>
      <button class="action-btn" @click="handleAction('depositHistory')">{{ t("充值记录") }}</button>
      <button class="action-btn" @click="handleAction('rewardHistory')">{{ t("奖励记录") }}</button>
      <button class="action-btn" @click="handleAction('orderHistory')">{{ t("订单历史") }}</button>
      <button class="action-btn" @click="handleAction('groupReport')">{{ t("小组报告") }}</button>
      <button class="action-btn" @click="handleAction('bankInfo')">{{ t("银行信息") }}</button>
      <button class="action-btn" @click="handleAction('address')">{{ t("地址") }}</button>
      <button class="action-btn">{{ t("语言") }}</button>
      <button class="language-btn" @click="toggleDropdown">{{ t(selectedLanguage) }}</button>

      <!-- 语言选择下拉框 -->
      <ul v-if="showDropdown" class="language-dropdown">
        <li
          v-for="lang in languageOptions"
          :key="lang"
          @click.stop="selectLanguage(lang)"
          :class="{ active: lang === selectedLanguage }"
        >{{ t(lang) }}</li>
      </ul>
    </div>

    <!-- 登出按钮 -->

    <button class="logout-btn" @click="handleLogout">{{ t("登出") }}</button>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useI18n } from "vue-i18n";
import { useLangStore } from "../../store/useLangStore";
import { storeToRefs } from "pinia";
import { getUserInfo } from "../../api/index";

const router = useRouter();
const { locale: i18nLocale } = useI18n();
const { t } = useI18n();
const userInfo = ref({});
const langStore = useLangStore();
const { locale: langStoreLocale } = storeToRefs(langStore);

i18nLocale.value = langStoreLocale.value; // 同步 i18n

const langMap = {
  越南语: "vi",
  中国: "zh",
  英语: "en",
  日本: "ja",
  法国: "fr",
  俄罗斯: "ru",
  韩国: "ko"
};
const reverseLangMap = Object.fromEntries(
  Object.entries(langMap).map(([k, v]) => [v, k])
);

const selectedLanguage = ref(reverseLangMap[langStoreLocale.value] || "越南语");
const languageOptions = Object.keys(langMap);
const showDropdown = ref(false);

const toggleDropdown = () => {
  showDropdown.value = !showDropdown.value;
};

const selectLanguage = lang => {
  selectedLanguage.value = lang;
  const langCode = langMap[lang] || "vi";
  langStore.setLocale(langCode);
  i18nLocale.value = langCode;
  showDropdown.value = false;
};

getUserInfo().then(res => {
  userInfo.value = res.data;
});

const handleAction = row => {
  if (row === "deposit") {
    window.open(
      "https://chat.ichatlink.net/widget/standalone.html?eid=6df096f4e9b05ad245f542d63ed1c8d7&language=en",
      "_blank"
    );
    // if (window._MEIQIA) {
    //   window._MEIQIA("showPanel"); // 显示入口按钮
    //   window._MEIQIA("show"); // 直接弹出聊天窗口
    // } else {
    //   console.warn("美洽尚未加载完成");
    // }
  } else if (row === "withdraw") {
    router.push("/withdraw");
  } else if (row === "withdrawHistory") {
    router.push("/withdrawHistory");
  } else if (row === "depositHistory") {
    router.push("/depositHistory");
  } else if (row === "orderHistory") {
    router.push("/orderHistory");
  } else if (row === "rewardHistory") {
    router.push("/rewardHistory");
  } else if (row === "groupReport") {
    router.push("/groupReport");
  } else if (row === "bankInfo") {
    router.push("/bankInfo");
  } else if (row === "address") {
    router.push("/address");
  }
  console.log(row);
};

const handleLogout = () => {
  localStorage.removeItem("token");
  router.push("/login");
};
</script>

<style scoped>
.user-center {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  font-family: "PingFang SC", "Microsoft YaHei", sans-serif;
  background: url("../../assets/img/BG-canhan-B0_5I1_l.png") no-repeat center
    center fixed;
  background-size: cover;
  padding-bottom: 120px;
  min-height: 100vh;
}

.user-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 5px;
}

.user-info {
  color: #fff;
  font-size: 12px;
}

.balance-label {
  margin: 0;
  font-size: 14px;
  color: #fff;
}

.balance-amount {
  margin: 5px 0 0;
  font-weight: bold;
  color: #fff;
}

.section-label {
  margin: 0 0 5px;
  font-size: 14px;
  color: #fff;
}

.avatar > img {
  width: 50px;
  height: 50px;
}

.level-name {
  margin: 0;
  color: #fff;
}

.action-buttons {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 12px;
  margin-bottom: 25px;
}

.action-btn {
  padding: 8px;
  font-size: 14px;
  color: #fff;
  text-align: left;
  border: none;
  box-sizing: border-box;
  border-top: 1px solid #616272;
  cursor: pointer;
  transition: all 0.2s;
  background-color: transparent;
}

.language-btn {
  width: 30%;
  display: flex;
  justify-content: center;
  align-items: center;
  grid-column: span 2;
  background-color: #0d324f;
  color: #fff;
  padding: 4px 8px;
  font-size: 14px;
  border: none;
  border-radius: 10px;
}

.language-arrow {
  margin-left: 5px;
  font-size: 12px;
}

.logout-btn {
  /* width: 20%; */
  margin-left: 40%;
  padding: 10px 15px;
  background-color: #5b0606;
  border: none;
  border-radius: 6px;
  font-size: 12px;
  color: white;
  cursor: pointer;
  transition: background-color 0.2s;
}

.action-buttons {
  grid-template-columns: 1fr;
}

.language-btn {
  grid-column: span 1;
}
.user-level-info {
  width: 200px;
  font-size: 12px;
  display: flex;
  padding: 17px;
  align-items: center;
  justify-content: space-around;
  border: 1px solid #ccc;
  border-radius: 20px;
  text-align: center;
  margin: 0 auto;
  margin-bottom: 15px;
}

.language-dropdown {
  list-style: none;
  padding: 0;
  margin-top: 225px;
  background: #0b1e34;
  color: white;
  border: 1px solid #ccc;
  border-radius: 6px;
  position: absolute;
  width: 100px;
  z-index: 100;
}

.language-dropdown li {
  padding: 8px 2px;
  cursor: pointer;
  font-size: 14px;
  white-space: nowrap;
}

.language-dropdown li:hover,
.language-dropdown li.active {
  background-color: gray;
}

@media screen and (min-width: 768px) {
  .user-center {
    max-width: 500px;
    margin: 0 auto;
    padding: 20px;
    font-family: "PingFang SC", "Microsoft YaHei", sans-serif;
    background: url("../../assets/img/BG-canhan-B0_5I1_l.png") no-repeat center
      center fixed;
    background-size: 100% 100%;
    padding-bottom: 120px;
    min-height: 100vh;
  }

  .user-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-bottom: 5px;
  }

  .user-info {
    color: #fff;
    font-size: 12px;
  }

  .balance-label {
    margin: 0;
    font-size: 14px;
    color: #fff;
  }

  .balance-amount {
    font-size: 18px;
    margin: 5px 0 0;
    /* font-weight: bold; */
    color: #fff;
  }

  .section-label {
    margin: 0 0 5px;
    font-size: 14px;
    color: #fff;
  }

  .avatar > img {
    width: 50px;
    height: 50px;
  }

  .level-name {
    margin: 0;
    color: #fff;
  }

  .action-buttons {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 12px;
    margin-bottom: 25px;
  }

  .action-btn {
    padding: 8px;
    font-size: 16px;
    color: #fff;
    text-align: left;
    border: none;
    box-sizing: border-box;
    border-top: 1px solid #616272;
    cursor: pointer;
    transition: all 0.2s;
    background-color: transparent;
  }

  .language-btn {
    width: 30%;
    display: flex;
    justify-content: center;
    align-items: center;
    grid-column: span 2;
    background-color: #0d324f;
    color: #fff;
    padding: 4px 8px;
    font-size: 16px;
    border: none;
    border-radius: 10px;
  }

  .language-arrow {
    margin-left: 5px;
    font-size: 16px;
  }

  .logout-btn {
    /* width: 20%; */
    margin-left: 40%;
    padding: 10px 19px;
    background-color: #5b0606;
    border: none;
    border-radius: 6px;
    font-size: 16px;
    color: white;
    cursor: pointer;
    transition: background-color 0.2s;
  }

  .action-buttons {
    grid-template-columns: 1fr;
  }

  .language-btn {
    grid-column: span 1;
  }

  .user-level-info {
    width: 8.66667vw;
    height: 0.26667vw;
    font-size: 15px;
    display: flex;
    align-items: center;
    justify-content: space-around;
    border: 1px solid #ccc;
    border-radius: 20px;
    text-align: center;
    margin: 0 auto;
    margin-bottom: 15px;
  }

  .level-name {
    font-size: 1.2vw;
  }

  .language-dropdown {
    list-style: none;
    padding: 0;
    margin-top: 10.5vw;
    background: #0b1e34;
    color: white;
    border: 1px solid #ccc;
    border-radius: 6px;
    position: absolute;
    width: 10.66667vw;
    z-index: 100;
    font-size: 14px;
  }

  .language-dropdown li {
    padding: 10px 2px;
    cursor: pointer;
    font-size: 16px;
    white-space: nowrap;
  }

  .language-dropdown li:hover,
  .language-dropdown li.active {
    background-color: gray;
  }
}
</style>
