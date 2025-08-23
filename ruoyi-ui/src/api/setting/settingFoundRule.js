import request from '@/utils/request'

// 查询设置基本原则列表
export function listRule(query) {
  return request({
    url: '/api/settingFoundRule/list',
    method: 'get',
    params: query
  })
}

// 查询设置基本原则详细
export function getRule(id) {
  return request({
    url: '/api/settingFoundRule/' + id,
    method: 'get'
  })
}

// 新增设置基本原则
export function addRule(data) {
  return request({
    url: '/api/settingFoundRule',
    method: 'post',
    data: data
  })
}

// 修改设置基本原则
export function updateRule(data) {
  return request({
    url: '/api/settingFoundRule',
    method: 'put',
    data: data
  })
}

// 删除设置基本原则
export function delRule(id) {
  return request({
    url: '/api/settingFoundRule/' + id,
    method: 'delete'
  })
}
