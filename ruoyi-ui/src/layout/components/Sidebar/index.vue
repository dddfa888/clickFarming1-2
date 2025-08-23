<template>
  <div
    :class="{ 'has-logo': showLogo }"
    :style="{
      backgroundColor:
        settings.sideTheme === 'theme-dark'
          ? variables.menuBackground
          : variables.menuLightBackground,
    }"
  >
    <logo v-if="showLogo" :collapse="isCollapse" />
    <el-scrollbar :class="settings.sideTheme" wrap-class="scrollbar-wrapper">
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        :background-color="
          settings.sideTheme === 'theme-dark'
            ? variables.menuBackground
            : variables.menuLightBackground
        "
        :text-color="
          settings.sideTheme === 'theme-dark'
            ? variables.menuColor
            : variables.menuLightColor
        "
        :unique-opened="true"
        :active-text-color="settings.theme"
        :collapse-transition="false"
        mode="vertical"
      >
        <sidebar-item
          v-for="(route, index) in sidebarRouters"
          :key="route.path + index"
          :item="route"
          :base-path="route.path"
          :badge="badgeList"
        />
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script>
import { mapGetters, mapState } from "vuex";
import Logo from "./Logo";
import SidebarItem from "./SidebarItem";
import variables from "@/assets/styles/variables.scss";
import { getMenuDot } from "@/api/menu";

export default {
  components: { SidebarItem, Logo },
  computed: {
    ...mapState(["settings", "msgprompt"]),
    ...mapGetters(["sidebarRouters", "sidebar"]),
    activeMenu() {
      const route = this.$route;
      const { meta, path } = route;
      // if set path, the sidebar will highlight the path you set
      if (meta.activeMenu) {
        return meta.activeMenu;
      }
      return path;
    },
    showLogo() {
      return this.$store.state.settings.sidebarLogo;
    },
    variables() {
      return variables;
    },
    isCollapse() {
      return !this.sidebar.opened;
    },
    promptsData() {
      return this.$store.state.msgprompt.prompts;
    },
  },
  data() {
    return {
      badgeList: {
        userAuth: 0,
        withdrawEmployee: 0,
        withdrawCustomer: 0,
        loanOrder: 0,
      },
      timer: null,
      eventSource: null,
      messages: [],
    };
  },
  mounted() {
    // this.timer = setInterval(() => {
    //   this.getMenuDot();
    // }, 3000);
    // 示例：从本地存储中获取 token（也可以用 cookie）

    // 使用 URL 携带 token
    this.eventSource = new EventSource(
      `${process.env.VUE_APP_BASE_URL}/sse/connect`
    );
    this.eventSource.onopen = () => {
      console.log("SSE 连接已建立");
    };
    // 监听默认事件（event: 不写时会使用这个）
    this.eventSource.onmessage = (event) => {
      console.log("默认事件 message 收到：", event.data);
    };

    // 监听自定义事件 event: CONNECTED
    this.eventSource.addEventListener("CONNECTED", (event) => {
      console.log("🟢 CONNECTED：", event.data);
      // this.messages.push(`连接状态：${event.data}`);
    });

    // 监听自定义事件 event: NOTICE
    this.eventSource.addEventListener("NOTICE", (event) => {
      const data = JSON.parse(event.data);
      console.log("📢 NOTICE 收到数据：", data);
      this.badgeList = data;
      this.$store.dispatch("getPrompt", data);
      // this.messages.push(`通知：${JSON.stringify(data)}`);
    });

    // 错误处理
    this.eventSource.onerror = (err) => {
      console.error("❌ SSE 连接出错：", err);
    };
  },
  watch: {
    promptsData: {
      handler(n, o) {
        this.compareObjects(n, o);
      },
      deep: true,
    },
  },
  beforeDestroy() {
    clearInterval(this.timer);
    if (this.eventSource) {
      this.eventSource.close();
    }
  },

  methods: {
    /** 获取菜单角标 */
    getMenuDot() {
      getMenuDot().then((res) => {
        if (res.code === 200) {
          this.badgeList = res.data;
          this.$store.dispatch("getPrompt", res.data);
        }
      });
    },
    compareObjects(newObj, oldObj) {
      // 遍历对象属性，逐个进行比较
      for (let key in newObj) {
        if (newObj.hasOwnProperty(key)) {
          // 判断两个对象中的值是否发生变化，且值为数字
          if (
            newObj[key] != null &&
            oldObj[key] != null &&
            typeof newObj[key] === "number" &&
            typeof oldObj[key] === "number"
          ) {
            // 如果新值大于旧值，则弹出提示
            if (newObj[key] > oldObj[key]) {
              // if (`${key}` == "finance") {
              //   this.$router.push({ path: "/finance/loanOrder" });
              //   this.$modal.notifySuccess("有新的贷款审核需要处理");
              // }
              if (`${key}` == "withdrawCustomer") {
                //this.$router.push({ path: "/user/withdrawCustomer" });
                this.$modal.notifySuccess("有新的提现审核需要处理");
              }
              if (`${key}` == "withdrawEmployee") {
                //this.$router.push({ path: "/user/withdrawEmployee" });
                this.$modal.notifySuccess("有新的提现审核需要处理");
              }
              // if (`${key}` == "userAuth") {
              //   this.$router.push({ path: "/user/userAuth" });
              //   this.$modal.notifySuccess("有新的认证需要处理");
              // }
              // if (`${key}` == "advanced") {
              //   this.$router.push({ path: "/111/advanced" });
              //   this.$modal.notifySuccess("有新的认证需要处理");
              // }
            }
          }
        }
      }
    },
  },
};
</script>
