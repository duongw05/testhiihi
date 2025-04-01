import request from '@/utils/system/request'

const prefix = 'business/client/api/v1/product/'

export function searchProduct(data: object, pagination: any) {
    return request({
        url: prefix + `search?page=${pagination.current}&size=${pagination.pageSize}`,
        method: 'post',
        data
    })
}
export function getProductDetail(id: number) {
    return request({
        url: `${prefix}detail/${id}`,
        method: 'get',
    })
}

export function deleteProduct(id: number) {
    return request({
        url: `${prefix}delete/${id}`,
        method: 'delete',
    });
}

export function exportReportProduct(data : object) {
    return request({
        url: prefix + `export`,
        method: 'post',
        data,
        responseType: 'blob'
    })
}

export function addProduct(data: object) {
    return request({
        url: prefix + `add`,
        method: 'post',
        data
    })
}
export function updateProduct(data: object) {
    return request({
        url: prefix + `update`,
        method: 'post',
        data
    })
}

export function fetchAllProduct() {
    return request({
        url: `${prefix}findAllProduct`,
        method: 'get',
    })
}