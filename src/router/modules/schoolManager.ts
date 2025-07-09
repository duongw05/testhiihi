import type { Route } from '../index.type'
import Layout from '@/layout/index.vue'
import { createNameComponent } from '../createNode'

const route: Route[] = [
    {
        path: '/school-management',
        component: Layout,
        redirect: '/school-management',
        meta: {
            title: 'message.menu.schoolManage.self',
            icon: 'iconfont icon-school'
        },
        children: [
            {
                path: 'school-info',
                name: 'school-info',
                component: createNameComponent(() =>
                    import('@/views/main/schoolManagement/SchoolList.vue')
                ),
                meta: {
                    activeBreadcrumb: true,
                    title: 'message.menu.schoolManage.self',
                    hideClose: false
                }
            },
            {
                path: 'school-info/add',
                name: 'school-info-add',
                component: createNameComponent(() =>
                    import('@/views/main/schoolManagement/SchoolForm.vue')
                ),
                meta: {
                    title: 'Thêm Trường Học',
                    activeBreadcrumb: true,

                },
                hideMenu: true
            },
            {
                path: 'school-info/edit/:id',
                name: 'school-info-edit',
                component: createNameComponent(() =>
                    import('@/views/main/schoolManagement/SchoolForm.vue')
                ),
                meta: {
                    title: 'Sửa Trường Học',
                    activeBreadcrumb: true,
                },
                hideMenu: true
            },
            {
                path: 'school-info/view/:id',
                name: 'school-info-view',
                component: createNameComponent(() =>
                    import('@/views/main/schoolManagement/SchoolForm.vue')
                ),
                meta: {
                    title: 'Chi Tiết Trường Học',
                    activeBreadcrumb: true,
                },
                hideMenu: true

            }
        ]
    }
]

export default route
