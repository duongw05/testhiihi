import axios, {AxiosError, AxiosInstance, AxiosRequestConfig, AxiosResponse} from 'axios'
import store from '@/store'
import {ElMessage, ElNotification} from 'element-plus'

const baseURL: any = import.meta.env['VITE_API_URL']
const appLogin: any = import.meta.env['VITE_APP_LOGIN_URL']

const service: AxiosInstance = axios.create({
    baseURL: baseURL,
    timeout: 60000
})

service.interceptors.request.use(
    // @ts-ignore
    (config: AxiosRequestConfig) => {
        if (store.getters['user/token']) {
            // @ts-ignore
            config.headers['Authorization'] = store.state.user.token
        }
        let lang = store.state.app.lang
        // @ts-ignore
        config.headers['Accept-Language'] = lang ? lang === 'vn' ? 'vi' : lang : 'vi';
        return config
    },
    (error: AxiosError) => {
        return Promise.reject(error)
    }
)

service.interceptors.response.use(
    (response: AxiosResponse) => {
        const res = response
        if (res.status === 200) {
            return res
        } else {
            showError(res)
            return Promise.reject(res)
        }
    },
    (error: AxiosError) => {
        const badMessage: any = error.response || error
        const code = parseInt(badMessage.toString().replace('Error: Request failed with status code ', ''))
        showError({code, message: badMessage})
        return Promise.reject(error)
    }
)

// 错误处理
function showError(error: any) {
    console.log("error", error)
    if (([401].includes(error.message.status))) {
        store.dispatch('user/loginOut')
        ElNotification({
            title: 'Lỗi',
            message: error.message?.data?.detailMessage || error.message?.data?.message || error.message.message || error.message?.data?.data?.message,
            type: 'error',
            duration: 3 * 1000
        })
        window.location.href = appLogin
    } else {
        ElNotification({
            title: 'Lỗi',
            message: error.message?.data?.detailMessage || error.message?.data?.message || error.message.message || error.message?.data?.data?.message,
            type: 'error',
            duration: 3 * 1000
        })
    }

}

export default service
