import type {Route} from '../index.type'
import Layout from '@/layout/index.vue'
import {createNameComponent} from '../createNode'
import {SETTINGS} from "@/constants/menuPermission";

const route: Route[] = [
    {
        path: '/sale',
        component: Layout,
        redirect: '/sale',
        meta: {title: 'Chính sách bán hàng', icon: 'iconfont icon-goods'},
        // hideMenu: !hasPermissionMenu([...SETTINGS]),
        children: [
            {
                path: 'product-search',
                name: 'product',
                component: createNameComponent(() => import('@/views/main/product/index.vue')),
                // @ts-ignore
                activeRouter: true,
                meta: {
                    activeBreadcrumb: true,
                    title: 'Quản lý sản phẩm', hideClose: false
                },
                children: [
                    {
                        path: 'product-create/:mode',
                        name: 'product-create',
                        component: createNameComponent(() => import('@/views/main/product/productForm.vue')),
                        // @ts-ignore
                        hideMenu: true,
                        meta: {
                            title: 'Tạo sản phẩm',
                            hideClose: true,
                        }
                    },
                    {
                        path: 'product-update/:data/:mode',
                        name: 'product-update',
                        component: createNameComponent(() => import('@/views/main/product/productForm.vue')),
                        hideMenu: true,
                        // @ts-ignore
                        meta: {
                            title: 'Cập nhật sản phẩm',
                            hideClose: true,
                        }
                    },
                    {
                        path: 'product-view/:data/:mode',
                        name: 'product-view',
                        component: createNameComponent(() => import('@/views/main/product/productForm.vue')),
                        hideMenu: true,
                        // @ts-ignore
                        // hideMenu: !hasPermissionMenu([...ADN_INVESTIGATION_REQUEST]),
                        meta: {
                            title: 'Chi tiết sản phẩm',
                            hideClose: true,
                        }
                    },
                ]
            },
            {
                path: 'search',
                name: 'product-offer',
                component: createNameComponent(() => import('@/views/main/productOffer/index.vue')),
                meta: {
                    title: 'Quản lý gói cước', hideClose: false
                },
                children: [
                    {
                        path: 'product-offer-create/:mode',
                        name: 'product-offer-create',
                        component: createNameComponent(() => import('@/views/main/productOffer/productOfferForm.vue')),
                        // @ts-ignore
                        hideMenu: true,
                        meta: {
                            title: 'Tạo gói cước',
                            hideClose: true,
                        }
                    },
                    {
                        path: 'product-offer-update/:data/:mode',
                        name: 'product-offer-update',
                        component: createNameComponent(() => import('@/views/main/productOffer/productOfferForm.vue')),
                        hideMenu: true,
                        // @ts-ignore
                        meta: {
                            title: 'Cập nhật gói cước',
                            hideClose: true,
                        }
                    },
                    {
                        path: 'product-offer-view/:data/:mode',
                        name: 'product-offer-view',
                        component: createNameComponent(() => import('@/views/main/productOffer/productOfferForm.vue')),
                        hideMenu: true,
                        // @ts-ignore
                        // hideMenu: !hasPermissionMenu([...ADN_INVESTIGATION_REQUEST]),
                        meta: {
                            title: 'Chi tiết gói cước',
                            hideClose: true,
                        }
                    },
                ]
            },
        ]
    },
]

export default route