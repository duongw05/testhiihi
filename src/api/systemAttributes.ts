import request from "@/utils/system/request";

const prefix = '/business/client/api/v1/system-attributes'; // Url base

/** Gọi api thêm mới systemAttributes*/
export function createSystemAttributes(data: object) {
    return request({
        url: prefix + `/add`,
        method: 'post',
        data: data
    })
}

/** Gọi api update systemAttributes*/
export function updateSystemAttributes(id: number, data: object) {
    return request({
        url: prefix + `/update/${id}`,
        method: 'post',
        data: data
    })
}

/** Gọi api lấy systemAttribute theo id*/
export function getSystemAttributeById(id: number) {
    return request({
        url: `${prefix}/detail/${id}`,
        method: 'get'
    })
}
export function getAttributes(model: any) {
    return request({
        url: prefix + `/search/${model}`,
        method: 'post'
    })
}

