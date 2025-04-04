import type {Route} from '../index.type'
import Layout from '@/layout/index.vue'
import {createNameComponent} from '../createNode'

// @ts-ignore
const route: Route[] = [
    {
        path: '/permission-management',
        component: Layout,
        redirect: '/permission-management',
        meta: {title: 'message.menu.permissionManage', icon: 'iconfont icon-shoplight'},
        children: [
            {
                path: 'function-management',
                name: 'function-management',
                component: createNameComponent(() => import('@/views/main/permissionManagement/functionManage/index.vue')),
                // @ts-ignore
                activeRouter: true,
                meta: {
                    activeBreadcrumb: true,
                    title: 'message.menu.functionManage.self', hideClose: false
                }
            },
            {
                path: 'user-management',
                name: 'user-management',
                component: createNameComponent(() => import('@/views/main/permissionManagement/userManage/index.vue')),
                // @ts-ignore
                activeRouter: true,
                meta: {
                    activeBreadcrumb: true,
                    title: 'message.menu.userManage.self', hideClose: false
                },
                children: [
                    {
                        path: 'user-detail/:data',
                        name: 'user-detail',
                        component: createNameComponent(() => import('@/views/main/permissionManagement/userManage/DetailForm.vue')),
                        meta: {
                            title: 'Thông tin người dùng',
                            hideClose: true,
                        },
                        // @ts-ignore
                        hideMenu: true
                    },
                ]
            },
            {
                path: 'group-management',
                name: 'group-management',
                component: createNameComponent(() => import('@/views/main/permissionManagement/groupManage/index.vue')),
                // @ts-ignore
                activeRouter: true,
                meta: {
                    activeBreadcrumb: true,
                    title: 'message.menu.groupManage.self', hideClose: false
                }
            }
        ]
    }
]

export default route