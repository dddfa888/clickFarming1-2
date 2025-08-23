// zh.js
export default {
  message: {
    hello: "你好，世界！",
    logout: "退出登录",
    language: "语言",
    english: "英语",
    chinese: "中文",
    vietnamese: "越南语",
  },
  languages: {
    language: '语言',
    vn: '越南语',
    cn: '中文',
    en: '英语',
    kr: '韩语',
    jp: '日语',
    rs: '俄语',
    fr: '法语'
  },
  loginPage: {
    register: '注册',
    login: '登录',
    account: '账号',
    password: '密码',
    verificationCode: '验证码',
    enterAccount: '请输入您的账号',
    enterPassword: '请输入您的密码',
    rememberMe : '记住密码'
  },

  pageCommon: {
    search: '搜索',
    reset: '重置',
    add: '新增',
    update: '修改',
    delete: '删除',
  },

  // 一级菜单《用户管理》
  muser: {
    userManage: '用户管理',

    userList: '用户列表',
    userGrade: '会员等级',
    withdrawCustomer: '客户提现管理',
    withdrawEmployee: '员工提现管理',
  },

  // 用户列表
  userPage: {
    account: '账号',
    withdrawCustomerBtn: '从客户那里提取资金',
    withdrawEmployeeBtn: '员工充值',
    column: {
      invitationCode: '邀请代码',
      loginAccount: '账号',
      SETTING_ORDER: '订单设置',
      settingOrder: '订单设置',
      action: '行为',
      inviterCode: '推荐人代码',
      regsterTime: '登记时间',
      lastLoginIp: '最后登录IP',
      phoneNumber: '电话号码',
      accountBalance: '账户余额',
      userStatus: '状态',
      userLevel: '等级',
      brushNumber: '当天刷单数量'
    },
    //行内转换
    typeGuest: '真实的',
    typeEmployee: '伪造的',
    typeUnknown: '未知',
    noData: '没有数据',
    statusUnlock: '正常',
    statusLock: '已锁定',

    //《行为》内按钮
    colAct: {
      width: '360',
      changeBalence: '更改账户余额',
      changeBank: '银行修改',
      switchEmp: '转为员工',
      switchGuest: '成为客人',
      orderHistory: '订单接收历史记录',
      lockAccount: '账户锁定',
      unlockAccount: '开帐户',
      deleteAccount: '删除帐户',
      info: '集团信息',
      changeInfo: '帐户信息',
      sendMsg: '发信息'
    },

    //《更改账户余额》弹出窗口
    balForm: {
      title: '更改账户余额',
      account: '账户',
      phone: '电话号码',
      origin: '剩余',
      newNum: '金钱数额',
      selectReason: '选择一个理由',
      noReason: '没有理由',
      adjust: '平衡调整',
      or: '或者',
      cancel: '取消',
      save: '创建新的',
      requireNum: '请输入金钱数额',
      requireReason: '请输入理由',
      "noReason": "没有理由",
      "adjust": "手动调整"
    }
  }

};
