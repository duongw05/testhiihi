import request from "@/utils/system/request";

const prefix = '/business/client/api/v1/logging-external'; // Url base


export function searchPageLoggingExternal(model: any, attribute: any) {
    return request({
        url: prefix + `/search/${model}/${attribute}`,
        method: 'post',
    })
}
