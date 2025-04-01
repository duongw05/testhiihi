import request from '@/utils/system/request'
const prefix = 'auth/api'

export function loginApi(data: object) {
  return request({
    url: data?.type === 'VSA' ? 'auth/api/loginVSA' : 'auth/api/login' ,
    method: 'post',
    data
  })
}
export function changePassword(data: object) {
  return request({
    url: prefix + '/changePassword',
    method: 'post',
    data
  })
}
export function forgotPassword(data: object) {
  return request({
    url: prefix + '/forgotPassword',
    method: 'post',
    data
  })
}
export function getInfoApi(data: object) {
  return request({
    url: '/client/api/v1/me/profile',
    method: 'get',
    data
  })
}

export function loginOutApi() {
  return request({
    url: '/client/api/v1/logout',
    method: 'post',
  })
}

export function passwordChange(data: object) {
  return request({
    url: '/client/api/v1/users/'+ data.id +'/changePassword',
    method: 'put',
    data
  })
}

export function getMenuApi() {
  return request({
    url: '/client/api/v1/me/menus',
    method: 'get',
  })
}

export function getPermissionLs() {
  return request({
    url: '/client/api/v1/me/permissions',
    method: 'get',
  })
}