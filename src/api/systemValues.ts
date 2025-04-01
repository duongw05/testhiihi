import request from "@/utils/system/request";

const prefix = '/business/client/api/v1/system-values'; // Url base


export function getValues(model: any, attribute: any) {
    return request({
        url: prefix + `/search/${model}/${attribute}`,
        method: 'post',
    })
}

/** Gọi api lấy dm_values theo id*/
export function getDmValuesById(id: number) {
    return request({
        url: `${prefix}/${id}`,
        method: 'get'
    })
}

/** Gọi api thêm mới dmValues*/
export function createNewDmValues(data: any) {
    return request({
        url: `${prefix}/create`,
        method: 'post',
        data
    })
}

/** Gọi api cập nhật dmValues*/
export function updateDmValues(data: any) {
    return request({
        url: `${prefix}/update/${data.id}`,
        method: 'put',
        data
    })
}

/** Gọi api xóa dmValues**/
export function deleteDmValues(id: number) {
    return request({
        url: `${prefix}/delete/${id}`,
        method: 'delete'
    })
}