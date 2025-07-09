import request from '@/utils/system/request'

const prefix = 'client/api/v1/school/'

export function searchSchool(params: any, page = 0, size = 10) {
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
export function getSchoolDetail(id: number) {
    return request({
        url: `${prefix}detail/${id}`,
        method: 'get',
    })
}

export function deleteSchool(id: number) {
    return request({
        url: `${prefix}delete/${id}`,
        method: 'delete',
    });
}

export function exportSchoolExcel(params: any) {
    return request({
        url:  `${prefix}export-excel`,
        method: 'get',
        params,
        responseType: 'blob',
    });
}

export function addSchool(data: object) {
    return request({
        url: prefix + `add`,
        method: 'post',
        data
    })
}
export function updateSchool(data: object) {
    return request({
        url: prefix + `update`,
        method: 'post',
        data
    })
}