import request from '@/utils/system/request'

const prefix = 'business/client/api/v1/product-offer/'

export function searchProductOffer(data: object, pagination: any) {
    return request({
        url: prefix + `search?page=${pagination.current}&size=${pagination.pageSize}`,
        method: 'post',
        data
    })
}
export function getProductOfferDetail(id: number) {
    return request({
        url: `${prefix}detail/${id}`,
        method: 'get',
    })
}

export function deleteProductOffer(id: number) {
    return request({
        url: `${prefix}delete/${id}`,
        method: 'delete',
    });
}

export function exportReportProductOffer(data : object) {
    return request({
        url: prefix + `export`,
        method: 'post',
        data,
        responseType: 'blob'
    })
}

export function addProductOffer(data: object) {
    return request({
        url: prefix + `add`,
        method: 'post',
        data
    })
}
export function updateProductOffer(data: object) {
    return request({
        url: prefix + `update`,
        method: 'post',
        data
    })
}

export function fetchAllProductOffer() {
    return request({
        url: `${prefix}findAllProductOffer`,
        method: 'get',
    })
}