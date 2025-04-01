/*
 * @Date: 2022-09-25 20:07:27
 * @Description:
 */
import type { Route } from '../index.type'
import Layout from '@/layout/index.vue'
import { createNameComponent } from '../createNode'
const route: Route[] = [
    {
        path: '/setting',
        component: Layout,
        redirect: '/setting',
        hideMenu: true,
        meta: { title: 'Cài đặt', icon: 'sfont system-24gl-printer' },
        children: [
            {
                path: 'system-index',
                component: createNameComponent(() => import('@/views/main/system/index.vue')),
                meta: { title: 'Quản lý thông báo', icon: 'sfont system-24gl-printer' }
            }
        ]
    }
]

export default route