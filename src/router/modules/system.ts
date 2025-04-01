import type {Route} from '../index.type'
import Layout from '@/layout/index.vue'
import {createNameComponent} from '../createNode'

// @ts-ignore
const route: Route[] = [
    {
        path: '/system',
        component: Layout,
        redirect: '/404',
        hideMenu: true,
        meta: {title: 'message.menu.system.name'},
        children: [
            {
                path: '/404',
                component: createNameComponent(() => import('@/views/system/404.vue')),
                meta: {title: 'message.menu.system.404', hideTabs: true}
            },
            {
                path: '/401',
                component: createNameComponent(() => import('@/views/system/401.vue')),
                meta: {title: 'message.menu.system.401', hideTabs: true}
            },
            {
                path: '/redirect/:path(.*)',
                component: createNameComponent(() => import('@/views/system/redirect.vue')),
                meta: {title: 'message.menu.system.redirect', hideTabs: true}
            }
        ]
    },
    {
        path: '/login',
        component: createNameComponent(() => import('@/views/system/login.vue')),
        hideMenu: true,
        meta: {title: 'message.system.login', hideTabs: true}
    },
    {
        path: '/changePassword',
        component: createNameComponent(() => import('@/views/system/changePass.vue')),
        hideMenu: true,
        meta: {title: 'message.system.changePassword', hideTabs: true}
    },
    {
        path: '/forgotPassword',
        component: createNameComponent(() => import('@/views/system/forgotPassword.vue')),
        hideMenu: true,
        meta: {title: 'message.system.forgotPassword', hideTabs: true}
    },
    // @ts-ignore
    {
        path: "/:pathMatch(.*)",
        component: Layout,
        redirect: "/404",
        hideMenu: true,
        meta: {title: ''},
    },
]

export default route