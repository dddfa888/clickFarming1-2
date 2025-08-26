import request from "../utils/index"

// 登录
export function login(data) {
    return request({
        url: '/api/login',
        method: 'post',
        data
    })
}

// 注册
export function register(data) {
    return request({
        url: '/api/register',
        method: 'post',
        data
    })
}

// 小组报告
export function getGroupReport() {
    return request({
        url: '/api/user/getUpToFourLevelInviters',
        method: 'get',
    })
}
// 提现
export function withdraw(data) {
    return request({
        url: '/api/withdraw/add',
        method: 'post',
        data
    })
}

// 获取个人的提现记录
export function getWithdrawRecord(pageNum, pageSize) {
    return request({
        url: `/api/withdraw/userList?pageNum=${pageNum}&pageSize=${pageSize}`, // data
        method: 'get'
    })
}

// 获取个人账变记录
export function getDepositRecord(pageNum,pageSize) {
    return request({
        url: `/api/records/userList?pageNum=${pageNum}&pageSize=${pageSize}`,
        method: 'get'
    })
}


// 获取用户个人信息
export function getUserInfo() {
    return request({
        url: '/api/user/userInfo',
        method: 'get',
    })
}

// 修改用户个人信息
export function updateUserFront(data) {
    return request({
        url: '/api/user/updateUserFront',
        method: 'post',
        data
    })
}


// 修改用户个人信息，不含校验密码
export function updateUserSimpleFront(data) {
    return request({
        url: '/api/user/updateUserSimpleFront',
        method: 'post',
        data
    })
}

// 创建订单
export function createOrder(data) {
    return request({
        url: '/api/order/insertOrderByUser',
        method: 'post',
        data
    })
}

// 订单列表
export function getOrderList() {
    return request({
        url: '/api/order/listByUser',
        method: 'get',
    })
}

// 获取会员记录（包含当前的用户的等级）
export function getMemberRecord() {
    return request({
        url: '/api/grade/userList',
        method: 'get',
    })
}

// 订单历史
export function getOrderHistory() {
    return request({
        url: '/api/order/listByUser',
        method: 'get',
    })
}

// 奖励历史记录
export function getRewardHistory() {
    return request({
        url: '/api/rewardRecord/selectSimpleByUserId',
        method: 'get',
    })
}

// 用id查询订单
export function getOrderById(id) {
    return request({
        url: `/api/order/${id}`,
        method: 'get',
    })
}

// 用户等级、余额、折扣
export function getUserGradeAndBalanceAndDiscount() {
    return request({
        url: '/api/records/getUserProfitInfo',
        method: 'get',
    })
}

// 发送分发（支付订单）
export function sendDistribution(id) {
    return request({
        url: `/api/order/payOrder/${id}`,
        method: 'put',
    })
}

// 获取用户通知 
export function getUserNotification() {
    return request({
        url: '/api/notify/userList',
        method: 'get',
    })
}


// 获取用户通知数量
export function getUserNotifyNum() {
    return request({
        url: '/api/notify/countNumByUser',
        method: 'get',
    })
}

//公司简介
export function getcompanyProfile(lang) {
    return request({
        url: `/api/settingComProfile/getByLang?lang=${lang}`,
        method: "get"
    })
}

// 基本原则
export function getBasicPrinciple(lang) {
    return request({
        url: `/api/settingFoundRule/getByLang?lang=${lang}`,
        method: "get"
    })
}

// 开发合作
export function getPartnership(lang) {
    return request({
        url: `/api/settingDevCooperate/getByLang?lang=${lang}`,
        method: "get"
    })
}


// 修改用户信息
export function updateUserInfo(data) {
    return request({
        url: '/api/user/updateUserBank',
        method: 'post',
        data
    })
}


// 更改会员等级 接口：

export function updateGrade(params) {
    return request({
        url: `/api/user/updateGrade?gradeId=${params}`,
        method: 'post'
    })
}


// 上传头像
export function updateAvatar(data) {
    return request({
        url: '/common/upload',
        method: 'post',
        data
    })
}