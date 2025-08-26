// src/i18n.js
import { createI18n } from 'vue-i18n'
import zh from './locales/zh';
import en from './locales/en';
import vi from './locales/vi';
import ko from './locales/ko';
import fr from './locales/fr';
import ru from './locales/ru';
import ja from "./locales/ja"

const i18n = createI18n({
    legacy: false,
    globalInjection: true,
    locale: localStorage.getItem('locale') || 'vi', 
    fallbackLocale: 'en', 
     missing(locale, key) {
    console.warn(`Lỗi xác thực`);
     },
    messages: {
        vi,
        zh,
        en,
        ko,
        fr,
        ru,
        ja
    },
});

export default i18n;
