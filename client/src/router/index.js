import { createWebHashHistory, createRouter } from 'vue-router'

// 路由表
const routes = [
  { path: '/market', name: 'market', component: () => import('../components/HelloWorld.vue') },
  { path: '/', name: 'Home', component: () => import('../views/index/index.vue') },
  { path: '/login', name: 'login', component: () => import('../views/login/index.vue') },
  { path: '/register', name: 'register', component: () => import('../views/register/index.vue') },
  { path: '/warehouse', name: 'warehouse', component: () => import('../views/warehouse/index.vue') },
  { path: '/me', name: 'me', component: () => import('../views/me/index.vue') },
  { path: '/company', name: 'company', component: () => import('../views/index/Company.vue') },
  { path: '/rule', name: 'rule', component: () => import('../views/index/rule.vue') },
  { path: '/cooperation', name: 'cooperation', component: () => import('../views/index/cooperation.vue') },
  { path: '/notice', name: 'notice', component: () => import('../views/index/notice.vue') },
  { path: '/withdrawHistory', name: 'withdrawHistory', component: () => import('../views/me/withdrawHistory.vue') },
  { path: '/withdraw', name: 'withdraw', component: () => import('../views/me/withdraw.vue') },
  { path: '/depositHistory', name: 'depositHistory', component: () => import('../views/me/deposit.vue') },
  { path: '/orderHistory', name: 'orderHistory', component: () => import('../views/me/order.vue') },
  { path: '/rewardHistory', name: 'rewardHistory', component: () => import('../views/me/reward.vue') },
  { path: '/groupReport', name: 'groupReport', component: () => import('../views/me/groupReport.vue') },
  { path: '/bankInfo', name: 'bankInfo', component: () => import('../views/me/bankInfo.vue') },
  { path: '/address', name: 'address', component: () => import('../views/me/address.vue') },
  { path: '/orderdetail', name: 'orderdetail', component: () => import('../views/index/orderdetail.vue') },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('../views/NotFound.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes,
})

// 路由守卫：未登录禁止访问其他页面
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token') // 假设你是用 token 存储登录状态
  const whiteList = ['/login', '/register', "/market"] // 白名单路由

  // 用 startsWith 来匹配，避免 query 参数或多斜杠导致白名单失效
  if (whiteList.some(path => to.path.startsWith(path))) {
    return next()
  }

  if (token) {
    // 有 token，放行
    return next()
  } else {
    // 无 token，重定向到登录页
    return next('/market')
  }
})

export default router
