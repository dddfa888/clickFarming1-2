<template>
  <div v-if="!item.hidden" style="position: relative">
    <template
      v-if="
        hasOneShowingChild(item.children, item) &&
        (!onlyOneChild.children || onlyOneChild.noShowingChildren) &&
        !item.alwaysShow
      "
    >
      <app-link
        v-if="onlyOneChild.meta"
        :to="resolvePath(onlyOneChild.path, onlyOneChild.query)"
      >
        <el-menu-item
          :index="resolvePath(onlyOneChild.path)"
          :class="{ 'submenu-title-noDropdown': !isNest }"
        >
          <item
            :icon="onlyOneChild.meta.icon || (item.meta && item.meta.icon)"
            :title="onlyOneChild.meta.title"
          />
        </el-menu-item>
      </app-link>
    </template>

    <el-submenu
      v-else
      ref="subMenu"
      :index="resolvePath(item.path)"
      popper-append-to-body
    >
      <template slot="title">
        <item
          v-if="item.meta"
          :icon="item.meta && item.meta.icon"
          :title="item.meta.title"
        />
      </template>
      <sidebar-item
        v-for="(child, index) in item.children"
        :key="child.path + index"
        :is-nest="true"
        :item="child"
        :base-path="resolvePath(child.path)"
        class="nest-menu"
      >
      </sidebar-item>
    </el-submenu>
    <el-badge
      :value="item.num"
      class="badge-icon"
      v-if="badgePath.includes(item.path) && item.num > 0"
    ></el-badge>
    <el-badge
      is-dot
      class="badge-icon_2"
      v-if="fabadgePath.includes(item.path) && item.isShow"
    ></el-badge>
  </div>
</template>

<script>
import path from "path";
import { isExternal } from "@/utils/validate";
import Item from "./Item";
import AppLink from "./Link";
import FixiOSBug from "./FixiOSBug";

export default {
  name: "SidebarItem",
  components: { Item, AppLink },
  mixins: [FixiOSBug],
  props: {
    // route object
    item: {
      type: Object,
      required: true,
    },
    isNest: {
      type: Boolean,
      default: false,
    },
    basePath: {
      type: String,
      default: "",
    },
    badge: {
      type: Object,
      // required: true
      default() {
        return {
          userAuth: 0,
          withdrawCustomer: 0,
          withdrawEmployee: 0,
          loanOrder: 0,
        };
      },
    },
  },
  data() {
    this.onlyOneChild = null;
    return {
      fabadgePath: ["/user"],
      badgePath: ["userAuth", "withdrawCustomer", "withdrawEmployee", "loanOrder"],
      badgePathObj: this.badge,
    };
  },
  mounted() {
    console.log(this.item);
    // this.badgePath.forEach((i,n) => {
    //   if(i === this.item.path) {
    //      this.$set(this.item, 'num', n);
    //   }
    // })
    // Object.entries(this.badge).forEach(([key, value]) => {
    //   if (key === this.item.path) {
    //     this.$set(this.item, "num", value); // 将对应的值添加到 item.num
    //   }
    // });
  },
  watch: {
    badge: {
      handler(newVal, oldVal) {
        this.compareAandB(newVal);
      },
      deep: true,
      immediate: true,
    },
  },
  computed: {},
  methods: {
    compareAandB(val) {
      if (this.item.children && Object.keys(val).length > 0) {
        // 先将 val 转换为一个简单的对象或 Map，以便快速查找
        const valueMap = val;

        // 提前缓存 this.item.children
        const itemChildren = this.item.children;

        itemChildren.forEach((i) => {
          // 直接查找是否存在与 i.path 匹配的 val[key]
          if (valueMap[i.path] !== undefined) {
            // 通过直接赋值来更新 num，避免不必要的 $set 调用
            this.$set(i, "num", valueMap[i.path]);
          }
        });

        const updateIsShow = (condition) => {
          // 如果值不变，避免不必要的更新
          if (this.item.isShow !== condition) {
            this.$set(this.item, "isShow", condition);
          }
        };

        const pathConditions = {
          "/user": valueMap.userAuth > 0 || valueMap.withdrawCustomer > 0 || valueMap.withdrawEmployee > 0,
        };

        Object.entries(this.item).forEach(([key, value]) => {
          if (pathConditions[value] !== undefined) {
            updateIsShow(pathConditions[value]);
          }
        });
      }
    },

    hasOneShowingChild(children = [], parent) {
      if (!children) {
        children = [];
      }
      const showingChildren = children.filter((item) => {
        if (item.hidden) {
          return false;
        } else {
          // Temp set(will be used if only has one showing child)
          this.onlyOneChild = item;
          return true;
        }
      });

      // When there is only one child router, the child router is displayed by default
      if (showingChildren.length === 1) {
        return true;
      }

      // Show parent if there are no child router to display
      if (showingChildren.length === 0) {
        this.onlyOneChild = { ...parent, path: "", noShowingChildren: true };
        return true;
      }

      return false;
    },
    resolvePath(routePath, routeQuery) {
      if (isExternal(routePath)) {
        return routePath;
      }
      if (isExternal(this.basePath)) {
        return this.basePath;
      }
      if (routeQuery) {
        let query = JSON.parse(routeQuery);
        return { path: path.resolve(this.basePath, routePath), query: query };
      }
      return path.resolve(this.basePath, routePath);
    },
  },
};
</script>
<style scoped>
.nest-menu {
  position: relative;
}

.badge-icon {
  position: absolute;
  right: 0;
}

.badge-icon_2 {
  position: absolute;
  right: 0;
  top: 0;
}

::v-deep .el-badge__content.is-fixed {
  left: 0;
  right: auto;
}
</style>
