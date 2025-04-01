import request from "@/utils/system/request";

const prefix = '/business/client/api/v1/application-log-internal'; // Url base

export function searchPageLoggingInternal(data: any, pagination: any) {
    return request({
        url: prefix + `/search?page=${pagination.current}&size=${pagination.pageSize}`,
        method: 'post',
        data: data,
    })
}

export function getLoggingInternalById(id: any) {
    return request({
        url: prefix + `/detail/${id}`,
        method: 'get',
    })
}
