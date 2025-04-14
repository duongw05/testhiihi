import {reactive} from 'vue'
import {createRouter, createWebHistory} from 'vue-router'
import store from '@/store'
import i18n from '@/locale'
import NProgress from '@/utils/system/nprogress'
import {changeTitle} from '@/utils/system/title'
import System from './modules/system'
import {addRoutes} from "@/router/permission";
import {isSupperAdmin} from "@/router/permission/front";

NProgress.configure({showSpinner: false})

let modules = reactive([
    ...System,
])

const {t} = i18n.global

const router = createRouter({
    history: createWebHistory(),
    routes: modules
})

const whiteList = ['/login', '/changePassword', '/forgotPassword']

router.beforeEach(async (to, _from, next) => {
    NProgress.start();
    to.meta.title ? (changeTitle(to.meta.title)) : "" // 动态title
    if (store.state.user.token) {
        to.meta.title ? (changeTitle(to.meta.title)) : "" // 动态title
        if (to.path === '/login') {
            next('/')
            return
        }
        if (!hasPermissionRouter(to) && to.path !== '/404' && to.path !== '/dashboard') {
            next("/404")
        }
        next()
    } else if (whiteList.includes(to.path)) {
        to.meta.title ? (changeTitle(to.meta.title)) : "" // 动态title
        next()
    } else {
        next("/login"); // 全部重定向到登录页
        to.meta.title ? (changeTitle(to.meta.title)) : "" // 动态title
    }
});

export function getPermissionCommon(listMenu: String[], roles: any[]) {
    let permission = false
    if (listMenu && listMenu.length > 0) {
        listMenu.forEach((e: String) => {
            if (!roles || roles.length === 0) {
                return true;
            } else if (roles && roles.length > 0) {
                let roleLs: String[] = [];
                roles.forEach((e1: any) => {
                    roleLs.push(e1.code)
                })
                if (roleLs.includes(e)) {
                    permission = true
                }
            }
        })
    }
    return permission;
}

function hasPermissionRouter(to: any) {
    let permissionList = store.state.user.permissionList
    let roles = to.meta?.roles
    return getPermissionCommon(permissionList, roles) || isSupperAdmin();
}

router.afterEach((to, _from) => {
    const keepAliveComponentsName = store.getters['keepAlive/keepAliveComponentsName'] || []
    // @ts-ignore
    const name = to.matched[to.matched.length - 1].components.default.name
    if (to.meta && to.meta.cache && name && !keepAliveComponentsName.includes(name)) {
        store.commit('keepAlive/addKeepAliveComponentsName', name)
    }
    NProgress.done()
});

export {
    modules
}

export default router
