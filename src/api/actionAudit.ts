import request from '@/utils/system/request'

const prefix = 'business/client/api/v1/'

export const searchAudit = (data: any, pagination: any) => {
    return request({
        url: prefix + `audit/search?page=${pagination.current}&size=${pagination.pageSize}`,
        method: 'post',
        data
    })
}

