import request from '@/utils/request'

// 查询订单接收记录列表
export function listRecord(query) {
  return request({
    url: '/api/order/list',
    method: 'get',
    params: query
  })
}

// 查询订单接收记录详细
export function getRecord(id) {
  return request({
    url: '/api/order/' + id,
    method: 'get'
  })
}

// 新增订单接收记录
export function addRecord(data) {
  return request({
    url: '/api/order',
    method: 'post',
    data: data
  })
}

// 修改订单接收记录
export function updateRecord(data) {
  return request({
    url: '/api/order',
    method: 'put',
    data: data
  })
}

// 删除订单接收记录
export function delRecord(id) {
  return request({
    url: '/api/order/' + id,
    method: 'delete'
  })
}
