import type {Route} from '../index.type'

import Dashboard from '../modules/dashboard'
import Component from '../modules/component'
import store from "@/store";
import {getPermissionCommon} from "@/router";
import {ADMIN} from "@/constants/menuPermission";
import Customer from "@/router/modules/salesManagement";
import SalePolicy from "@/router/modules/salePolicy";
import systemSetup from "@/router/modules/systemSetup";

const FrontRoutes: Route[] = [
    ...Dashboard,
    // ...Customer,
    // ...SalePolicy,
    // ...systemSetup,
    // ...Component,
    // ...Pages,
    // ...Chart,
    // ...SystemManage,
    // ...SystemMessage,
    // ...Settings,
    // ...Dynamic,
]

export default FrontRoutes

export function hasPermissionMenu(roles: Array<Object>) {
    let listMenu = store.state.user.menuList
    return getPermissionCommon(listMenu, roles) || isSupperAdmin();
}

export function hasPermission(role: String) {
    let permissionList = store.state.user.permissionList
    if (permissionList && permissionList.length > 0) {
        return permissionList.includes(role) || isSupperAdmin();
    }
    return false;
}

export function isSupperAdmin() {
    let user = store.state.user.info?.userName
    let globalAdmin = store.state.globalParam.globalValue?.filter((item: {
        code: any
    }) => item.attCode === ADMIN && item.modelCode === ADMIN && item?.valueCode.toLowerCase() === user?.toLowerCase())
    return globalAdmin && globalAdmin.length > 0;
}
