// request.js
import axios from "axios";
import router from "../router/index";
import { showDialog } from "vant";
import i18n from "../i18n";
import { notify } from "./notify";
// import { useRouter } from 'vue-router';
import { showToast } from "vant";

let isTokenExpired = false;
// const router = useRouter();
const baseURL = "http://192.168.1.203:8089/";
const request = axios.create({
  baseURL:
    import.meta.env.MODE === "development"
      ? "/api"
      : "https://cfapi.ceshias.cc/",
  // baseURL: baseURL,
  timeout: 10000,
});

//请求拦截器：每次请求前都从 localStorage 获取最新 token
request.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// 响应拦截器：统一处理 token 失效
request.interceptors.response.use(
  (response) => {
    const res = response.data;
    if ((res.code === 401 || res.message === "token失效") && !isTokenExpired) {
      isTokenExpired = true;
      notify({
        message: i18n.global.t("登录已过期，请重新登录"),
        type: "warning",
        duration: 2000,
      });
      setTimeout(() => {
        localStorage.removeItem("token");
        router.push({ path: "/login" });
        isTokenExpired = false;
        // 刷新页面
      }, 1000);
    }
    return res;
  },
  (error) => {
    if (error.response && error.response.status === 401 && !isTokenExpired) {
      isTokenExpired = true;
      notify({
        message: i18n.global.t("登录已过期，请重新登录"),
        type: "warning",
        duration: 2000,
      });
      // 立马跳转
      setTimeout(() => {
        localStorage.removeItem("token");
        router.push({ path: "/login" });
        isTokenExpired = false;
      }, 1000);
    }
    return Promise.reject(error);
  }
);

export default request;
