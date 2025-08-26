// main.js
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import Vant from 'vant'
import i18n from './i18n'
import 'vant/lib/index.css'
import router from './router'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import Notify from './components/Notify.vue'

const container = document.createElement('div')
document.body.appendChild(container)
const app = createApp(App)
const pinia = createPinia()
// 创建通知管理器

const notifyApp = createApp(Notify)
const notifyInstance = notifyApp.mount(container)

globalThis.$notify = notifyInstance.addNotice
pinia.use(piniaPluginPersistedstate)
app.use(pinia)
app.use(router)
app.use(i18n)
app.use(Vant)
app.mount('#app')