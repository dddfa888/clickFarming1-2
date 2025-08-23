import request from '@/utils/request'

// 查询设置订购规则列表
export function listRule(query) {
  return request({
    url: '/api/settingOrderRule/list',
    method: 'get',
    params: query
  })
}

// 查询设置订购规则详细
export function getRule(id) {
  return request({
    url: '/api/settingOrderRule/' + id,
    method: 'get'
  })
}

// 新增设置订购规则
export function addRule(data) {
  return request({
    url: '/api/settingOrderRule',
    method: 'post',
    data: data
  })
}

// 修改设置订购规则
export function updateRule(data) {
  return request({
    url: '/api/settingOrderRule',
    method: 'put',
    data: data
  })
}

// 删除设置订购规则
export function delRule(id) {
  return request({
    url: '/api/settingOrderRule/' + id,
    method: 'delete'
  })
}
