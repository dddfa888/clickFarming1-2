import request from '@/utils/request'

// 查询设置发展合作列表
export function listCooperate(query) {
  return request({
    url: '/api/settingDevCooperate/list',
    method: 'get',
    params: query
  })
}

// 查询设置发展合作详细
export function getCooperate(id) {
  return request({
    url: '/api/settingDevCooperate/' + id,
    method: 'get'
  })
}

// 新增设置发展合作
export function addCooperate(data) {
  return request({
    url: '/api/settingDevCooperate',
    method: 'post',
    data: data
  })
}

// 修改设置发展合作
export function updateCooperate(data) {
  return request({
    url: '/api/settingDevCooperate',
    method: 'put',
    data: data
  })
}

// 删除设置发展合作
export function delCooperate(id) {
  return request({
    url: '/api/settingDevCooperate/' + id,
    method: 'delete'
  })
}
