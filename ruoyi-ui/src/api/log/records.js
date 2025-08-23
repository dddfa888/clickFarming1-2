import request from '@/utils/request'

// 查询账变记录列表
export function listRecords(query) {
  return request({
    url: '/api/records/list',
    method: 'get',
    params: query
  })
}

// 查询账变记录详细
export function getRecords(uuid) {
  return request({
    url: '/api/records/' + uuid,
    method: 'get'
  })
}

// 新增账变记录
export function addRecords(data) {
  return request({
    url: '/api/records',
    method: 'post',
    data: data
  })
}

// 修改账变记录
export function updateRecords(data) {
  return request({
    url: '/api/records',
    method: 'put',
    data: data
  })
}

// 删除账变记录
export function delRecords(uuid) {
  return request({
    url: '/api/records/' + uuid,
    method: 'delete'
  })
}
