import request from '@/utils/request'

// 查询用户列表
export function listUser(query) {
  return request({
    url: '/api/user/list',
    method: 'get',
    params: query
  })
}


export function getAllSuperiorUids(query) {
  return request({
    url: '/api/user/getAllSuperiorUids',
    method: 'get',
    params: query
  });
}

// 查询用户详细
export function getUser(uid) {
  return request({
    url: '/api/user/' + uid,
    method: 'get'
  })
}


export function setStatus(uid) {
  return request({
    url: '/api/user/editStatus/' + uid,
    method: 'get'
  })
}

export function setRegisterType(uid) {
  return request({
    url: '/api/user/editRegisterType/' + uid,
    method: 'get'
  })
}



// 新增用户
export function addUser(data) {
  return request({
    url: '/api/user',
    method: 'post',
    data: data
  })
}

export function changeBalance(data) {
  return request({
    url: '/api/user/changeBalance',
    method: 'post',
    data: data
  })
}


export function setBalance(data) {
  return request({
    url: '/api/user/setBalance',
    method: 'post',
    data: data
  })
}

// 修改用户
export function updateUser(data) {
  return request({
    url: '/api/user',
    method: 'put',
    data: data
  })
}

// 修改用户信息
export function updateMUserSimple(data) {
  return request({
    url: '/api/user/updateMUserSimple',
    method: 'post',
    params: data
  })
}


// 修改用户连单数量
export function updateMultiOrderNum(data) {
  return request({
    url: '/api/user/updateMultiOrderNum',
    method: 'put',
    data: data
  })
}


// 删除用户
export function delUser(uid) {
  return request({
    url: '/api/user/' + uid,
    method: 'delete'
  })
}


// 查询订单
export function getOrderList(query) {
  return request({
    url: '/api/order/list',
    method: 'get',
    params: query
  });
}

// 查询订单设置
export function selectByUserId(data) {
  return request({
    url: '/api/userOrderSet/selectByUserId/'+data,
    method: 'get',
    params: data
  });
}

// 保存订单设置
export function updateOrederSet(data) {
  return request({
    url: '/api/userOrderSet/saveOrderSetByUser',
    method: 'post',
    data: data
  });
}
