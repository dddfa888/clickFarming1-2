import request from '@/utils/request'

// 查询产品管理列表
export function listManage(query) {
  return request({
    url: '/api/product/list',
    method: 'get',
    params: query
  })
}

// 查询产品管理详细
export function getManage(id) {
  return request({
    url: '/api/product/' + id,
    method: 'get'
  })
}

// 新增产品管理
export function addManage(data) {
  return request({
    url: '/api/product',
    method: 'post',
    data: data
  })
}

// 修改产品管理
export function updateManage(data) {
  return request({
    url: '/api/product',
    method: 'put',
    data: data
  })
}

// 删除产品管理
export function delManage(id) {
  return request({
    url: '/api/product/' + id,
    method: 'delete'
  })
}
