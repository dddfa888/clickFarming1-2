import request from '@/utils/request'

// 查询存款取款记录列表
export function listWithdraw(query) {
  return request({
    url: '/api/withdraw/list',
    method: 'get',
    params: query
  })
}

// 查询存款取款记录详细
export function getWithdraw(id) {
  return request({
    url: '/api/withdraw/' + id,
    method: 'get'
  })
}

/**
 * 一键员工提现
 * @returns {*}
 */
export function oneClickAgree(type) {
  return request({
    url: '/api/withdraw/oneClickAgree',
    method: 'get',
    params: { type } // GET请求自动拼接为 ?type=xxx
  });
}

// 新增存款取款记录
export function addWithdraw(data) {
  return request({
    url: '/api/withdraw',
    method: 'post',
    data: data
  })
}

/**
 * 驳回
 * @param data
 * @returns {*}
 */
export function reject(data) {
  return request({
    url: '/api/withdraw/reject',
    method: 'post',
    data: data
  })
}

/**
 * 同意
 * @param data
 * @returns {*}
 */
export function agree(data) {
  return request({
    url: '/api/withdraw/agree',
    method: 'post',
    data: data
  })
}

// 修改存款取款记录
export function updateWithdraw(data) {
  return request({
    url: '/api/withdraw',
    method: 'put',
    data: data
  })
}

// 删除存款取款记录
export function delWithdraw(id) {
  return request({
    url: '/api/withdraw/' + id,
    method: 'delete'
  })
}
