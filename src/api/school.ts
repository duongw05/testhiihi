import request from '@/utils/system/request'

const prefix = 'client/api/v1/school/'

export function searchSchool(params: any, page = 0, size = 10) {
    return request({
        url: `${prefix}search`,
        method: 'get',
        params: {
            ...params,
            page,
            size
        }
    })
}

export function deleteSchool(id: number) {
    return request({
        url: `${prefix}delete/${id}`,
        method: 'delete',
    });
}

export function exportSchoolExcel(data: any) {
    return request({
        url: `${prefix}export-school`,
        method: 'post',
        data,
        responseType: 'blob',
    });
}

export function addSchool(data: object) {
    return request({
        url: prefix + `add`,
        method: 'post',
        data
    })
}
export function updateSchool(data: object) {
    return request({
        url: prefix + `update`,
        method: 'post',
        data
    })
}

export function importSchoolExcel(file: File) {
    const formData = new FormData();
    formData.append('file', file);

    return request({
        url: `${prefix}import-schools`,
        method: 'post',
        data: formData,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    });
}

export function downloadSchoolTemplate() {
    return request({
        url: `${prefix}download-template`,
        method: 'get',
        responseType: 'blob',
    });
}

