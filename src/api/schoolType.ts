import request from '@/utils/system/request'

const prefix = 'client/api/v1/schoolType/'

export function searchSchoolType(params: any, page = 0, size = 10) {
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
export function getSchoolTypeDetail(id: number) {
    return request({
        url: `${prefix}detail/${id}`,
        method: 'get',
    })
}

export function deleteSchoolType(id: number) {
    return request({
        url: `${prefix}delete/${id}`,
        method: 'delete',
    });
}

export function exportReportSchoolType(data : object) {
    return request({
        url: prefix + `export`,
        method: 'post',
        data,
        responseType: 'blob'
    })
}

export function addSchoolType(data: object) {
    return request({
        url: prefix + `add`,
        method: 'post',
        data
    })
}
export function updateSchoolType(data: object) {
    return request({
        url: prefix + `update`,
        method: 'post',
        data
    })
}

export function fetchAllSchoolType() {
    return request({
        url: `${prefix}getAll`,
        method: 'get',
    })
}