import request from '@/utils/system/request'
import store from "@/store";
const prefix = '/client/api/v1/'

type Pagination = {
    current: number
    pageSize: number
}

export function updateFunctionCatalog(data: object) {
    return request({
        url: prefix + `functionCatalog/update`,
        method: 'post',
        data
    })
}
export function addFunctionCatalog(data: object) {
    return request({
        url: prefix + `functionCatalog/add`,
        method: 'post',
        data
    })
}
export function deleteFunctionCatalog(id: any) {
    return request({
        url: prefix + `functionCatalog/delete/` + id ,
        method: 'delete'
    })
}
export function searchFunctionCatalog(data: object, pagination: Pagination) {
    return request({
        url: prefix + `functionCatalog/search?page=${pagination.current}&size=${pagination.pageSize}`,
        method: 'post',
        data
    })
}