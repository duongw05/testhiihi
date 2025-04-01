import request from '@/utils/system/request'

export function getData(data: object) {
  return request({
    url: '/work/list',
    method: 'post',
    baseURL: '/mock',
    data
  })
}
