import request from '@/utils/system/request'

export function getData(data: object) {
  return request({
    url: '/card/list',
    method: 'post',
    baseURL: '/mock',
    data
  })
}

export function add(data: object) {
  return request({
    url: '/card/add',
    method: 'post',
    baseURL: '/mock',
    data
  })
}

export function update(data: object) {
  return request({
    url: '/card/update',
    method: 'post',
    baseURL: '/mock',
    data
  })
}

export function del(data: object) {
  return request({
    url: '/card/del',
    method: 'post',
    baseURL: '/mock',
    data
  })
}