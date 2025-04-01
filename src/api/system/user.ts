import request from '@/utils/system/request'

export function getData(data: object) {
  return request({
    url: '/system/user/list',
    method: 'post',
    baseURL: '/mock',
    data
  })
}

export function add(data: object) {
  return request({
    url: '/system/user/add',
    method: 'post',
    baseURL: '/mock',
    data
  })
}

export function update(data: object) {
  return request({
    url: '/system/user/update',
    method: 'post',
    baseURL: '/mock',
    data
  })
}

export function updateStatus(data: object) {
  return request({
    url: '/system/user/updateStatus',
    method: 'post',
    baseURL: '/mock',
    data
  })
}

export function del(data: object) {
  return request({
    url: '/system/user/del',
    method: 'post',
    baseURL: '/mock',
    data
  })
}