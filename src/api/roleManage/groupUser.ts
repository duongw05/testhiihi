import request from '@/utils/system/request'
import store from "@/store";
const prefix = '/client/api/v1/'

type Pagination = {
    current: number
    pageSize: number
}

export function getAllGroup() {
    return request({
        url: `${prefix}groups/all`,
        method: 'get',
    })
}
export function updateGroup(data: object) {
    return request({
        url: prefix + `groups/update-group`,
        method: 'post',
        data
    })
}
export function addGroup(data: object) {
    return request({
        url: prefix + `groups/add-group`,
        method: 'post',
        data
    })
}
export function deleteGroup(groupId: any) {
    return request({
        url: prefix + `groups/delete-group/` + groupId ,
        method: 'delete'
    })
}
export function searchGroup(data: object, pagination: Pagination) {
    return request({
        url: prefix + `groups/search-group?page=${pagination.current}&size=${pagination.pageSize}`,
        method: 'post',
        data
    })
}
export function quickSearchGroup(data: object, pagination: Pagination) {
    return request({
        url: prefix + `groups/quick-search-group?page=${pagination.current}&size=${pagination.pageSize}`,
        method: 'post',
        data
    })
}
export function searchUserByGroupId(groupId: any, pagination: Pagination) {
    return request({
        url: prefix + `groups/`+ groupId +'/search-users',
        method: 'post'
    })
}

export function searchUserNotInGroup(data: object) {
    return request({
        url: prefix + `groups/search-users-not-in-group`,
        method: 'post',
        data
    })
}


export function updateGroupUser(data: object) {
    return request({
        url: prefix + `groups/update-group`,
        method: 'post',
        data
    })
}

export function addGroupUser(data: Array<any>) {
    return request({
        url: prefix + `groups/add-user-group-maps`,
        method: 'post',
        data
    })
}
export function deleteGroupUser(userId: any, groupId: any) {
    return request({
        url: `${prefix}groups/delete-user-from-group/${userId}/${groupId}`,
        method: 'delete'
    });
}
