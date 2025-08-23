import request from '@/utils/request'

// 查询设置收款账户列表
export function listAccount(query) {
  return request({
    url: '/api/settingReceiveAccount/list',
    method: 'get',
    params: query
  })
}

// 查询设置收款账户详细
export function getAccount(id) {
  return request({
    url: '/api/settingReceiveAccount/' + id,
    method: 'get'
  })
}

// 新增设置收款账户
export function addAccount(data) {
  return request({
    url: '/api/settingReceiveAccount',
    method: 'post',
    data: data
  })
}

// 修改设置收款账户
export function updateAccount(data) {
  return request({
    url: '/api/settingReceiveAccount',
    method: 'put',
    data: data
  })
}

// 删除设置收款账户
export function delAccount(id) {
  return request({
    url: '/api/settingReceiveAccount/' + id,
    method: 'delete'
  })
}
