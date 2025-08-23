import request from '@/utils/request'

// 查询设置全球化列表
export function listGlobal(query) {
  return request({
    url: '/api/settingGlobal/list',
    method: 'get',
    params: query
  })
}

// 查询设置全球化详细
export function getGlobal(id) {
  return request({
    url: '/api/settingGlobal/' + id,
    method: 'get'
  })
}

// 新增设置全球化
export function addGlobal(data) {
  return request({
    url: '/api/settingGlobal',
    method: 'post',
    data: data
  })
}

// 修改设置全球化
export function updateGlobal(data) {
  return request({
    url: '/api/settingGlobal',
    method: 'put',
    data: data
  })
}

// 删除设置全球化
export function delGlobal(id) {
  return request({
    url: '/api/settingGlobal/' + id,
    method: 'delete'
  })
}
