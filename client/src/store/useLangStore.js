import { defineStore } from 'pinia';

export const useLangStore = defineStore('lang', {
  state: () => ({
    locale: localStorage.getItem('locale') || 'vi'
  }),
  actions: {
    setLocale(lang) {
      this.locale = lang;
      localStorage.setItem('locale', lang);
    }
  },
  persist: true, // 开启持久化 pinia-plugin-persistedstate
});
