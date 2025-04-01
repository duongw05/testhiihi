import Vue from 'vue'
import moment from 'moment'
import store from "@/store";
import _ from 'lodash'
import {appParams} from "@/constants/app-param";

export const regexInput = (value: any, regex: string) => {
    if (value) {
       return  value.replace(regex, '').toUpperCase()
    }
    return ''
}

export function deepTrim (obj: any) {
    if (typeof obj === 'object' && Object.entries(obj).length > 0) {
        for (const prop in obj) {
            if (obj.hasOwnProperty(prop)) {
                const type = typeof obj[prop]
                if (obj[prop] !== null && (type === 'string' || type === 'object')) {
                    if (type === 'object') {
                        obj[prop] = deepTrim(obj[prop])
                    } else {
                        obj[prop] = obj[prop].trim()
                    }
                }
            }
        }
    }
    return obj
}

export function formatDate (value: any, format = 'DD-MM-YYYY') {
    if(value) {
        try {
            return moment(value).format(format)
        }catch (err) {
            return ''
        }
    }
    return ''
}

export function getIndexPage (index: number, page: Object) {
    try {
        return (index + 1 ) + ((page.index - 1) * page.size )
    }catch (err) {
        return 0
    }
}

export function getLocation () {
    return store.state.app.lang || navigator.language
}

export function getNameGlobalList (code: any, lstGlobalList: any) {
    if (!code){
        return ''
    }

    const data = _.find(lstGlobalList, x => x.value == code)
    return data.name
}

export function handleInputKeyDown (e : any, callback: Function) {
    if (e.key === 'Enter') {
        callback()
    }
}

export function downloadBlob(blob: any, fileName: string){
    // Tạo URL từ Blob
    const url = window.URL.createObjectURL(blob);

    // Tạo thẻ <a> để tải file
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', fileName); // Đặt tên file khi tải xuống

    // Thêm thẻ <a> vào document và tự động click để tải file
    document.body.appendChild(link);
    link.click();

    // Xóa thẻ <a> sau khi tải xong
    document.body.removeChild(link);
}



/**
 * Chuyển chuỗi số định dạng (có dấu phẩy và dấu chấm) thành Number
 * @param formatted Chuỗi số định dạng (ví dụ: "1,234,567.89")
 * @returns Giá trị Number hoặc null nếu không hợp lệ
 */
export function toNumber(formatted: any): number | null {
    if (!formatted || typeof formatted !== 'string') {
        return null;
    }

    // Xóa dấu phẩy và giữ nguyên dấu chấm
    const cleaned = formatted.replace(/,/g, '');
    const numValue = Number(cleaned);

    return isNaN(numValue) ? null : numValue;
}
export function genUuid() {
    return "10000000-1000-4000-8000-100000000000".replace(/[018]/g, c =>
        (+c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> +c / 4).toString(16)
    );
}