import type {Route} from '../index.type'
import Layout from '@/layout/index.vue'
import {createNameComponent} from '../createNode'
import {SETTINGS} from "@/constants/menuPermission";

const route: Route[] = [
    {
        path: '/sales-management',
        component: Layout,
        redirect: '/sales-management',
        meta: {title: 'Quản lý bán hàng', icon: 'iconfont icon-shoplight'},
        // hideMenu: !hasPermissionMenu([...SETTINGS]),
        children: [
            {
                path: 'customer',
                name: 'customer',
                component: createNameComponent(() => import('@/views/main/customer/index.vue')),
                // @ts-ignore
                activeRouter: true,
                meta: {
                    activeBreadcrumb: true,
                    title: 'Quản lý khách hàng', hideClose: false
                },
                children : [
                    {
                        path: 'create-customer/:mode',
                        name: 'customer-create',
                        component: createNameComponent(() => import('@/views/main/customer/customerForm.vue')),
                        meta: {
                            title: 'Tạo khách hàng',
                            hideClose: true,
                        },
                        // @ts-ignore
                        hideMenu: true,
                    },
                    {
                        path: 'update-customer/:data/:mode',
                        name: 'customer-update',
                        component: createNameComponent(() => import('@/views/main/customer/customerForm.vue')),
                        meta: {
                            title: 'Sửa khách hàng',
                            hideClose: true,
                        },
                        hideMenu: true,
                    },
                    {
                        path: 'view-customer/:data/:mode',
                        name: 'customer-view',
                        component: createNameComponent(() => import('@/views/main/customer/customerForm.vue')),
                        meta: {
                            title: 'Chi tiết khách hàng',
                            hideClose: true,
                        },
                        hideMenu: true,
                    },
                ]
            },
            {
                path: 'partner',
                name: 'partner',
                component: createNameComponent(() => import('@/views/main/partner/index.vue')),
                meta: {
                    activeBreadcrumb: true,
                    title: 'Quản lý đối tác', hideClose: false
                },
                // @ts-ignore
                activeRouter: true,
                children : [
                    {
                        path: 'create-partner/:mode',
                        name: 'partner-create',
                        component: createNameComponent(() => import('@/views/main/partner/partnerForm.vue')),
                        // @ts-ignore
                        hideMenu: true,
                        // hideMenu: !hasPermissionMenu([...ADN_INVESTIGATION_REQUEST]),
                        meta: {
                            title: 'Tạo đối tác',
                            hideClose: true,
                        }
                    },
                    {
                        path: 'update-partner/:data/:mode',
                        name: 'partner-update',
                        component: createNameComponent(() => import('@/views/main/partner/partnerForm.vue')),
                        // // @ts-ignore
                        // hideMenu: !hasPermissionMenu([...ADN_INVESTIGATION_REQUEST]),
                        meta: {
                            title: 'Sửa đối tác',
                            hideClose: true,
                        },
                        hideMenu: true,
                    },
                    {
                        path: 'view-partner/:data/:mode',
                        name: 'partner-view',
                        component: createNameComponent(() => import('@/views/main/partner/partnerForm.vue')),
                        // // @ts-ignore
                        // hideMenu: !hasPermissionMenu([...ADN_INVESTIGATION_REQUEST]),
                        meta: {
                            title: 'Chi tiết đối tác',
                            hideClose: true,
                        },
                        hideMenu: true,
                    },
                ]
            },
        ]
    }
]

export default route