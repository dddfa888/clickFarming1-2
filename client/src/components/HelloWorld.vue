<template>
  <div class="container">
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
    <div class="overlay"></div>
    <div class="content">
      <h1>INGKA CENTRES</h1>
      <h2>E-commerce shopping center</h2>
      <p>
        We are 21 years old since the beginning of that adventure in a garage in
        the Saavedra neighborhood, in the city of Buenos Aires. We have come a
        long way, but we are convinced that the best is yet to come.
      </p>
      <div class="buttons">
        <button @click="toLogin">{{ t("登录") }}</button>
        <button @click="toRegister">{{ t("注册") }}</button>
      </div>
    </div>
  </div>
</template>
<script setup>
import { useRouter } from "vue-router";
import { useI18n } from "vue-i18n";
import { useLangStore } from "../store/useLangStore.js";
import { reactive, ref, onMounted } from "vue";

const router = useRouter();
const langStore = useLangStore();
const { locale } = useI18n();
const toLogin = () => {
  router.push({ name: "login" });
};
const toRegister = () => {
  router.push({ name: "register" });
};

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
</script>

<style scoped>
.container {
  position: fixed;
  top: 0;
  left: 0;
  height: 100vh;
  width: 100vw;
  background: url("../assets/img/background.jpg") no-repeat center center/cover;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  color: white;
  font-family: "Segoe UI", sans-serif;
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
  color: #fff;
}
.dropdown-wrapper {
  position: relative;
  background: rgba(255, 255, 255, 0.15);
  padding: 5px 10px;
  border-radius: 6px;
  cursor: pointer;
  color: #fff;

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

.overlay {
  position: absolute;
  inset: 0;
  background: rgba(0, 0, 0, 0.6);
  z-index: 0;
}

.content {
  position: relative;
  z-index: 1;
  max-width: 800px;
  padding: 20px;
}

h1 {
  font-size: 3rem;
  margin-bottom: 2rem;
  font-weight: bold;
}

h2 {
  font-size: 1.5rem;
  margin-bottom: 3rem;
  font-weight: normal;
}

p {
  margin-bottom: 3rem;
  font-size: 1.5rem;
  line-height: 1.5;
}

.buttons {
  display: flex;
  justify-content: center;
  margin-bottom: 2rem;
  gap: 20px;
}

button {
  background-color: #ffe600;
  color: black;
  padding: 0.75rem 2rem;
  border: none;
  font-weight: bold;
  width: 15rem;
  cursor: pointer;
  transition: background 0.3s ease;
}

button:hover {
  background-color: #ffdb00;
}

@media (max-width: 768px) {
  h1 {
    font-size: 1.5rem;
  }
  button {
    text-align: center;
    background-color: #ffe600;
    color: black;
    border: none;
    font-size: 0.7rem;
    width: 7rem;
    transition: background 0.3s ease;
  }

  p {
    font-size: 0.8rem;
  }
}

@media (min-width: 768px) {
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
    color: #fff;
  }
  .dropdown-wrapper {
    position: relative;
    background: rgba(255, 255, 255, 0.15);
    padding: 5px 10px;
    border-radius: 6px;
    cursor: pointer;
    color: #fff;

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
