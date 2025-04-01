import request from "@/utils/system/request";

const prefix = '/business/client/api/v1/system-models'; // Url base

/** Gọi api thêm mới systemModels*/
export function createSystemModels(data: object) {
    return request({
        url: prefix + `/add`,
        method: 'post',
        data: data
    })
}

/** Gọi api update systemModel*/
export function updateSystemModels(id: number, data: object) {
    return request({
        url: prefix + `/update/${id}`,
        method: 'post',
        data: data
    })
}

/**Gọi api tìm kiếm phân trang systemModels*/
export function getSystemModelsPage(data: any, pagination: any) {
    let url = prefix + `/search?page=${pagination.current}&size=${pagination.pageSize}`;
    return request({
        url: url, // Sử dụng URL đã được cập nhật
        method: 'post',
        data: data
    });
}


/** Gọi api lấy systemModel theo id*/
export function getSystemModelsById(id: number) {
    return request({
        url: prefix + `/detail/${id}`,
        method: 'get',
    })
}