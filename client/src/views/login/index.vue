<template>
  <div class="login-container">
    <!--分开的语言选择区域 -->
    <div class="language-selector">
      <span class="label">{{ $t("语言") }}</span>
      <div class="dropdown-wrapper" @click="toggleLangList">
        {{ t(selectedLanguage) }}
        <ul v-if="showLangList" class="lang-dropdown">
          <li
            v-for="(lang, index) in languageList"
            :key="index"
            @click.stop="selectLanguage(lang)"
            :class="{ active: lang === selectedLanguage }"
          >{{ t(lang) }}</li>
        </ul>
      </div>
    </div>

    <div class="login-footer">
      <span @click="router.push('/login')">{{ t("登录") }}</span>
      <span @click="router.push('/register')">{{ t("注册") }}</span>
    </div>

    <!-- 登录表单 -->
    <div class="login-form">
      <form @submit.prevent="onSubmit">
        <!-- 用户名 -->
        <div class="form-group">
          <i class="icon user-icon"></i>
          <input type="text" v-model="form.loginAccount" :placeholder="t('请填写用户名')" />
        </div>

        <!-- 密码框（含明密文切换） -->
        <div class="form-group password-group">
          <i class="icon lock-icon"></i>
          <input
            :type="showPassword ? 'text' : 'password'"
            v-model="form.loginPassword"
            :placeholder="t('请填写密码')"
          />
          <!-- 眼睛 SVG 图标 -->
          <span class="eye-icon" @click="togglePasswordVisibility">
            <svg
              v-if="showPassword"
              xmlns="http://www.w3.org/2000/svg"
              class="icon-eye"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="2"
              stroke-linecap="round"
              stroke-linejoin="round"
            >
              <path d="M1 1l22 22" />
              <path
                d="M17.94 17.94A10.94 10.94 0 0112 20C7.03 20 2.73 16.11 1 12a18.84 18.84 0 014.21-5.69M9.88 9.88A3 3 0 0114.12 14.12"
              />
            </svg>

            <svg
              v-else
              xmlns="http://www.w3.org/2000/svg"
              class="icon-eye"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="2"
              stroke-linecap="round"
              stroke-linejoin="round"
            >
              <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z" />
              <circle cx="12" cy="12" r="3" />
            </svg>
          </span>
        </div>

        <!-- 登录按钮 -->
        <div class="login-btn" style="margin: 16px">
          <button type="submit">{{ t("现在登录") }}</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { login } from "../../api/index.js";
import { useI18n } from "vue-i18n";
import { useLangStore } from "../../store/useLangStore.js";
import { notify } from "../../utils/notify.js";

const router = useRouter();
const form = reactive({
  loginAccount: "",
  loginPassword: ""
});

const showPassword = ref(false);

const togglePasswordVisibility = () => {
  showPassword.value = !showPassword.value;
};

const langStore = useLangStore();
const { locale } = useI18n();
const { t } = useI18n();
const showLangList = ref(false);
const langMap = {
  越南语: "vi",
  中国: "zh",
  英语: "en",
  日本: "ja",
  法国: "fr",
  俄罗斯: "ru",
  韩国: "ko"
};
const languageList = Object.keys(langMap);
const reverseLangMap = Object.fromEntries(
  Object.entries(langMap).map(([k, v]) => [v, k])
);

// 初始化选中语言
const selectedLanguage = ref(reverseLangMap[langStore.locale]);
locale.value = langStore.locale;

function selectLanguage(lang) {
  selectedLanguage.value = lang;
  const langCode = langMap[lang] || "vi";
  langStore.setLocale(langCode);
  locale.value = langCode;
  showLangList.value = !showLangList.value;
}

function toggleLangList() {
  showLangList.value = !showLangList.value;
}

function onSubmit(values) {
  login(form).then(res => {
    if (res.code === 200) {
      globalThis.$notify({
        message: t("操作成功"),
        type: "success",
        duration: 2000
      });
      localStorage.setItem("token", res.data.token);
      if (window._MEIQIA) {
        window._MEIQIA("client", {
          name: res.data.loginAccount, // 假设后端返回了用户名
          tel: res.data.phoneNumber // 假设后端返回了手机号
        });
      }
      router.push("/");
    } else {
      globalThis.$notify({
        message: t(res.msg),
        type: "error",
        duration: 2000
      });
    }
  });
}

// function onSubmit(values) {
//   login(form).then(res => {
//     if (res.code === 200) {
//       globalThis.$notify({
//         message: t("操作成功"),
//         type: "success",
//         duration: 2000
//       });
//       localStorage.setItem("token", res.data.token);
//       router.push("/");
//     } else {
//       globalThis.$notify({
//         message: t(res.msg),
//         type: "error",
//         duration: 2000
//       });
//     }
//   });
// }
</script>

<style scoped>
.login-container {
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: center;
  min-height: 100vh;
  width: 100%;
  padding: 20px;
  box-sizing: border-box;
  background: url("../../assets/img/login-bg.png") no-repeat center center /
    cover;
}

/* 登录表单 */
.login-form {
  width: 100%;
  max-width: 500px;
  /* background: rgba(255, 255, 255, 0.08); */
  border-radius: 10px;
  padding: 10px;
  box-sizing: border-box;
}

.form-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 16px;
  border-radius: 30px;
  width: 100%;
  border: 1px solid #181818;
  box-shadow: 0 6px 6px rgba(0, 0, 0, 0.1);
  position: relative;
  margin-bottom: 16px;
}

.form-group input {
  padding: 12px 16px;
  border-radius: 999px; /* 完全圆形输入框 */
  border: 1px solid #ccc;
  font-size: 14px;
  outline: none;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  background: #c5c5c5;
}

.login-btn {
  display: flex;
  justify-content: center;
}

.login-btn button {
  width: 70%;
  padding: 10px;
  border: none;
  border-radius: 25px;
  background-color: #1989fa;
  color: white;
  font-size: 14px;
  cursor: pointer;
  border: 1px solid #171717;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.8);
}

.login-btn button:hover {
  background-color: #147dd1;
}

.login-footer {
  display: flex;
  justify-content: space-between;
  width: 100%;
  max-width: 500px;
  align-items: center;
  color: #fff;
}
/* 语言选择器 */
.language-selector {
  position: absolute;
  top: 20px;
  right: 20px;
  display: flex;
  align-items: center;
  font-size: 14px;
  color: #fff;
  z-index: 20;
}
.language-selector .label {
  margin-right: 6px;
  color: #000;
}
.dropdown-wrapper {
  position: relative;
  background: rgba(255, 255, 255, 0.15);
  padding: 5px 10px;
  border-radius: 6px;
  cursor: pointer;
  color: #000;
  user-select: none;
}
.lang-dropdown {
  position: absolute;
  top: 35px;
  right: 0;
  background: #e5e5e5;
  color: #000;
  list-style: none;
  padding: 5px 0;
  margin: 0;
  border-radius: 4px;
  width: 100px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
  z-index: 999;
}
.lang-dropdown li {
  padding: 8px 12px;
  cursor: pointer;
  transition: background-color 0.3s;
}
.lang-dropdown li:hover,
.lang-dropdown li.active {
  background-color: #d3d3d3;
}

.icon {
  position: absolute;
  left: 10px;
  top: 50%;
  transform: translateY(-50%);
}

.eye-icon {
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
  width: 24px;
  height: 24px;
}

.icon-eye {
  width: 24px;
  height: 24px;
  color: #999;
}

/* PC端适配 */
@media (min-width: 768px) {
  .login-container {
    position: relative;
    display: flex;
    flex-direction: column;
    justify-content: flex-end;
    align-items: center;
    min-height: 100vh;
    width: 540px;
    margin: 0 auto;
    padding: 20px;
    box-sizing: border-box;
    background: url("../../assets/img/login-bg.png") no-repeat center center /
      cover;
  }

  .icon {
    position: absolute;
    left: 10px;
    top: 50%;
    transform: translateY(-50%);
  }

  .eye-icon {
    position: absolute;
    right: 10px;
    top: 50%;
    transform: translateY(-50%);
    cursor: pointer;
    width: 24px;
    height: 24px;
  }

  .icon-eye {
    width: 24px;
    height: 24px;
    color: #999;
  }

  .form-group {
    display: flex;
    flex-direction: column;
    margin-bottom: 16px;
    border-radius: 30px;
    width: 100%;
    border: 1px solid #181818;
    box-shadow: 0 6px 6px rgba(0, 0, 0, 0.1);
    position: relative;
    margin-bottom: 16px;
  }

  .form-group input {
    padding: 12px 16px;
    border-radius: 999px; /* 完全圆形输入框 */
    border: 1px solid #ccc;
    font-size: 14px;
    outline: none;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    background: #c5c5c5;
  }

  .login-btn {
    display: flex;
    justify-content: center;
  }

  .login-btn button {
    width: 50%;
    padding: 10px;
    border: none;
    border-radius: 25px;
    background-color: #1989fa;
    color: white;
    font-size: 14px;
    cursor: pointer;
    border: 1px solid #171717;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.8);
  }

  .login-btn button:hover {
    background-color: #147dd1;
  }

  .login-footer {
    display: flex;
    justify-content: space-between;
    width: 100%;
    max-width: 500px;
    align-items: center;
    color: #fff;
  }

  /* 语言选择器 */
  .language-selector {
    position: absolute;
    top: 20px;
    right: 20px;
    display: flex;
    align-items: center;
    font-size: 14px;
    color: #fff;
    z-index: 20;
  }
  .language-selector .label {
    margin-right: 6px;
    color: #000;
  }
  .dropdown-wrapper {
    position: relative;
    background: rgba(255, 255, 255, 0.15);
    padding: 5px 10px;
    border-radius: 6px;
    cursor: pointer;
    color: #000;
    user-select: none;
  }
  .lang-dropdown {
    position: absolute;
    top: 35px;
    right: 0;
    background: #e5e5e5;
    color: #000;
    list-style: none;
    padding: 5px 0;
    margin: 0;
    border-radius: 4px;
    width: 100px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
    z-index: 999;
  }
  .lang-dropdown li {
    padding: 8px 12px;
    cursor: pointer;
    transition: background-color 0.3s;
  }
  .lang-dropdown li:hover,
  .lang-dropdown li.active {
    background-color: #d3d3d3;
  }
}
</style>
