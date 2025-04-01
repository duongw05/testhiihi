import request from "@/utils/system/request"

const prefixUrl = 'api/'

export const getStaffSummary = () => {
    return request({
        url: prefixUrl + 'dashboard/getStaffSummary',
        method: 'get',
    })
}
