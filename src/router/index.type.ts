import type { DefineComponent } from 'vue'
import type { RouteRecordRaw } from 'vue-router'

export type Route = {
    path: string
    component: DefineComponent<{}, {}, any> | (() => Promise<DefineComponent<{}, {}, any>>)
    meta: Meta

    name?: string
    redirect?: string
    alwayShow?: boolean
    hideMenu?: boolean
    children?: Route[]
    activeRouter?: boolean
} & RouteRecordRaw

export interface Meta {
    title: string
    icon?: string
    cache?: boolean
    hideTabs?: boolean
    activeMenu?: string
    jumpUrl?: string
    [key: string]: any
}