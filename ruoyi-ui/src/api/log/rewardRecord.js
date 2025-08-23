import request from '@/utils/request'

// 查询奖励记录列表
export function listRecord(query) {
  return request({
    url: '/api/rewardRecord/list',
    method: 'get',
    params: query
  })
}

// 查询奖励记录详细
export function getRecord(id) {
  return request({
    url: '/api/rewardRecord/' + id,
    method: 'get'
  })
}

// 新增奖励记录
export function addRecord(data) {
  return request({
    url: '/api/rewardRecord',
    method: 'post',
    data: data
  })
}

// 修改奖励记录
export function updateRecord(data) {
  return request({
    url: '/api/rewardRecord',
    method: 'put',
    data: data
  })
}

// 删除奖励记录
export function delRecord(id) {
  return request({
    url: '/api/rewardRecord/' + id,
    method: 'delete'
  })
}
