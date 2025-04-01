import request from '@/utils/system/request'

const prefix = 'business/client/api/v1/system-notify'
export function getData(data: object, page: number, pageSize: number) {
  return request({
    url: prefix + `/search?page=${page - 1}&size=${pageSize}`,
    method: 'post',
    data
  })
}

export function add(data: object) {
  return request({
    url: prefix + '/add',
    method: 'post',
    data
  })
}

export function update(data: object) {
  return request({
    url: prefix + '/update',
    method: 'post',
    data
  })
}

export function del(data: object) {
  return request({
    url: prefix + '/delete',
    method: 'post',
    data
  })
}