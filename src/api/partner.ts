import request from '@/utils/system/request'

const prefix = 'customer/client/api/v1/partner/'

export function searchPartner(data: object, pagination: any) {
    return request({
        url: prefix + `search?page=${pagination.current}&size=${pagination.pageSize}`,
        method: 'post',
        data
    })
}
export function getPartnerDetail(id: number) {
    return request({
        url: `${prefix}detail/${id}`,
        method: 'get',
    })
}

export function deletePartner(id: number) {
    return request({
        url: `${prefix}delete/${id}`,
        method: 'delete',
    });
}

export function exportReportPartner(data : object) {
    return request({
        url: prefix + `export`,
        method: 'post',
        data,
        responseType: 'blob'
    })
}

export function addPartner(data: object) {
    return request({
        url: prefix + `add`,
        method: 'post',
        data
    })
}
export function updatePartner(data: object) {
    return request({
        url: prefix + `update`,
        method: 'post',
        data
    })
}