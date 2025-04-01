import request from '@/utils/system/request'

const prefix = 'customer/client/api/v1/area/'

export function getProvince() {
    return request({
        url: prefix + "findAllParentCode",
        method: 'get',
    })
}

export function getProvinceByParentCode(data: object) {
    return request({
        url: prefix + "findAllByParentCode",
        method: 'get',
        params: data,
    })
}