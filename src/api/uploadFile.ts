import request from '@/utils/system/request'

const prefix = 'business/client/api/v1/upload';

export function uploadFiles(fileMap: any) {

    return request({
        url: `${prefix}/upload-file`,
        method: 'post',
        data: fileMap,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    });
}

export function getFiles(payload: any) {
    return request({
        url: `${prefix}/getFileUploads`,
        method: 'post',
        data: payload,
    });
}
export async function downFile(data: object) {
    return await request({
        url: `${prefix}/download`,
        method: 'get',
        params: data,
        responseType: 'blob'
    })
}

export function downloadFileByName(data: any) {
    return request({
        url: prefix + `/downloadFileByName`,
        params: data,
        method: 'get',
        responseType: 'blob'
    })
}
