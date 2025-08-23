import request from '@/utils/request'

// 查询通知列表
export function listNotify(query) {
  return request({
    url: '/api/notify/list',
    method: 'get',
    params: query
  })
}

// 查询通知详细
export function getNotify(uid) {
  return request({
    url: '/api/notify/' + uid,
    method: 'get'
  })
}

// 新增通知
export function addNotify(data) {
  return request({
    url: '/api/notify',
    method: 'post',
    data: data
  })
}

// 修改通知
export function updateNotify(data) {
  return request({
    url: '/api/notify',
    method: 'put',
    data: data
  })
}

// 删除通知
export function delNotify(uid) {
  return request({
    url: '/api/notify/' + uid,
    method: 'delete'
  })
}
