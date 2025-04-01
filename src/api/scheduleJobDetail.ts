import request from "@/utils/system/request";

const prefix = 'process/client/api/v1/schedule-job-detail';

/** Goi api tim kiem tien trinh*/
export function searchScheduleJobDetails(data: any, pagination: any) {
    return request({
        url: `${prefix}/search?page=${pagination.current}&size=${pagination.pageSize}`,
        method: 'post',
        data: data
    })
}


/** Goi api tim cau hinh tien trinh theo id*/
export function getScheduleJobDetailById(idScheduleJobDetail: any) {
    return request({
        url: `${prefix}/detail/${idScheduleJobDetail}`,
        method: 'get',
    })
}

/** Goi api them moi cau hinh tien trinh */
export function createScheduleJobDetail(data:  any) {
    return request({
        url: `${prefix}/add`,
        method: 'post',
        data:data
    })
}

/** Goi api cap nhat cau hinh tien trinh */
export function updateScheduleJobDetail(data:  any) {
    return request({
        url: `${prefix}/update`,
        method: 'post',
        data:data
    })
}
/** Goi api xoa cau hinh tien trinh */
export function deleteScheduleJobDetail(id:  any) {
    return request({
        url: `${prefix}/delete/${id}`,
        method: 'delete'
    })
}

/** Goi api startNow tien trinh */
export function startNowScheduleJobDetail(id:  any) {
    return request({
        url: `${prefix}/start-now/${id}`,
        method: 'post'
    })
}

/** Goi api tam dung tien trinh */
export function pauseScheduleJobDetail(id:  any) {
    return request({
        url: `${prefix}/pause/${id}`,
        method: 'post'
    })
}

/** Goi api tiep tuc tien trinh */
export function resumeScheduleJobDetail(id:  any) {
    return request({
        url: `${prefix}/resume/${id}`,
        method: 'post'
    })
}

