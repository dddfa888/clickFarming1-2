import request from '@/utils/request'

// 查询设置公司简介列表
export function listProfile(query) {
  return request({
    url: '/api/settingComProfile/list',
    method: 'get',
    params: query
  })
}

// 查询设置公司简介详细
export function getProfile(id) {
  return request({
    url: '/api/settingComProfile/' + id,
    method: 'get'
  })
}

// 新增设置公司简介
export function addProfile(data) {
  return request({
    url: '/api/settingComProfile',
    method: 'post',
    data: data
  })
}

// 修改设置公司简介
export function updateProfile(data) {
  return request({
    url: '/api/settingComProfile',
    method: 'put',
    data: data
  })
}

// 删除设置公司简介
export function delProfile(id) {
  return request({
    url: '/api/settingComProfile/' + id,
    method: 'delete'
  })
}
