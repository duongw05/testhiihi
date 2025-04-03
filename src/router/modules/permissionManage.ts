import type {Route} from '../index.type'
import Layout from '@/layout/index.vue'
import { createNameComponent } from '../createNode'

const route: Route[] = [
    {
        path: '/function-management',
        component: Layout,
        redirect: '/function-management',
        meta: {title: 'Quản lý phân quyền', icon: 'iconfont icon-shoplight'},
        children: [
            {
                path: 'function-management',
                name: 'function-management',
                component: createNameComponent(() => import('@/views/main/permissionManagement/functionManage/index.vue')),
                // @ts-ignore
                activeRouter: true,
                meta: {
                    activeBreadcrumb: true,
                    title: 'Quản lý chức năng', hideClose: false
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
                    title: 'Quản lý người dùng', hideClose: false
                }
            },
            {
                path: 'group-management',
                name: 'group-management',
                component: createNameComponent(() => import('@/views/main/permissionManagement/groupManage/index.vue')),
                // @ts-ignore
                activeRouter: true,
                meta: {
                    activeBreadcrumb: true,
                    title: 'Quản lý nhóm', hideClose: false
                }
            }
        ]
    }
]

export default route