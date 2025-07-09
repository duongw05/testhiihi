import request from '@/utils/system/request'

const prefix = 'client/api/v1/workingOffsite/'

export function searchWorkingOffsite(params: any, page = 0, size = 10) {
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
export function getWorkingOffsiteDetail(id: number) {
    return request({
        url: `${prefix}detail/${id}`,
        method: 'get',
    })
}

export function deleteWorkingOffsite(id: number) {
    return request({
        url: `${prefix}delete/${id}`,
        method: 'delete',
    });
}

export function exportReportWorkingOffsite(data : object) {
    return request({
        url: prefix + `export`,
        method: 'post',
        data,
        responseType: 'blob'
    })
}

export function addWorkingOffsite(data: object) {
    return request({
        url: prefix + `add`,
        method: 'post',
        data
    })
}
export function updateWorkingOffsite(data: object) {
    return request({
        url: prefix + `update`,
        method: 'post',
        data
    })
}

export function fetchAllWorkingOffsite() {
    return request({
        url: `${prefix}findAll`,
        method: 'get',
    })
}