import request from '@/utils/system/request'

const prefix = 'client/api/v1/tblUser/'

export function searchTblUser(params: any, page = 0, size = 10) {
    return request({
        url: `${prefix}search`,
        method: 'get',
        params: {
            ...params,
            page,
            size
        }
    })
}
export function getTblUserDetail(id: number) {
    return request({
        url: `${prefix}detail/${id}`,
        method: 'get',
    })
}

export function deleteTblUser(id: number) {
    return request({
        url: `${prefix}delete/${id}`,
        method: 'delete',
    });
}

export function exportReportTblUser(data : object) {
    return request({
        url: prefix + `export`,
        method: 'post',
        data,
        responseType: 'blob'
    })
}

export function addTblUser(data: object) {
    return request({
        url: prefix + `add`,
        method: 'post',
        data
    })
}
export function updateTblUser(data: object) {
    return request({
        url: prefix + `update`,
        method: 'post',
        data
    })
}

export function fetchAllTblUser() {
    return request({
        url: `${prefix}findAll`,
        method: 'get',
    })
}