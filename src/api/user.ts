import request from '@/utils/system/request'
import store from "@/store";
const prefix = '/client/api/v1/'

type Pagination = {
  current: number
  pageSize: number
}
export function loginApi(data: object) {
  return request({
    url: '/client/api/v1/login',
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
    url: '/client/api/v1/users/changePassword',
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

/** Gọi api lấy cây menu*/
export function callApiGetMenuTree() {
  let user = store.state.user;
  const idUser = user?.info?.id;
  console.log(idUser);
  return request({
    url: '/client/api/v1/menu_mangagement/get-tree' + "/" + idUser,
    // url: '/api/menu/list',
    method: 'get',
  })
}

export function getPermissionLs() {
  return request({
    url: '/client/api/v1/me/permissions',
    method: 'get',
  })
}


export function getUserList(data: object, pagination: Pagination) {
  return request({
    url: prefix + `users/search?page=${pagination.current}&size=${pagination.pageSize}`,
    method: 'post',
    data
  })
}
export function createUser(data: object) {
  return request({
    url: prefix + 'users/add',
    method: 'post',
    data
  })
}

export function updateUser(data: object) {
  return request({
    url: prefix + 'users/update',
    method: 'post',
    data
  })
}

export function getUserById(id: number) {
  return request({
    url: prefix +'users/detail/' + id,
    method: 'get',
  })
}

export function deleteUser(data: object) {
  return request({
    url: prefix +'users/delete/' + data?.id,
    method: 'delete',
    data
  })
}

export function changeStatusUser(data: object) {
  return request({
    url: prefix +'users/change-status/' + data,
    method: 'put',
  })
}

export function changePassword(data: object) {
  return request({
    url: prefix + `users/changePassword`,
    method: 'put',
    data
  })
}
export function changePasswordByUser(data: object) {
  return request({
    url: prefix + `users/changePasswordByUser`,
    method: 'put',
    data
  })
}

/**Gọi api cập nhật profile của user*/
export function updateUserProfile(data: object) {
  return request({
    url: `${prefix}users/update-profile`,
    method: 'put',
    data
  })
}

export function getAllUer() {
  return request({
    url: `${prefix}users/get-all`,
    method: 'post',
  })
}