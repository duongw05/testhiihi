import type {Route} from "@/router/index.type";
import Layout from "@/layout/index.vue";
import {createNameComponent} from "@/router/createNode";

const route: Route[] = [
    {
        path: '/system-setup',
        component: Layout,
        redirect: '/system-setup',
        meta: {title: 'Cấu hình tham số', icon: 'iconfont icon-shoplight'},
        hideMenu: true,
        children: [
            {
                path: 'message-config',
                name: 'message-config',
                component: createNameComponent(() => import('@/views/main/system/index.vue')),
                meta: {
                    title: 'Cấu hình thông báo', hideClose: false
                }
            },
            {
                path: 'categories-shared',
                name: 'categories-shared',
                component: createNameComponent(() => import('@/views/main/dmAppParams/Index.vue')),
                meta: {
                    title: 'Danh mục dùng chung', hideClose: false
                }
            },
            {
                path: 'logging-internal',
                name: 'logging-internal',
                component: createNameComponent(() => import('@/views/main/loggingManagement/loggingInternal/Index.vue')),
                meta: {
                    title: 'Quản lý ghi log', hideClose: false
                }
            },
        ]
    },
    {
        path: '/system-custom',
        component: Layout,
        redirect: '/system-custom',
        meta: {title: 'Tủy chỉnh', icon: 'iconfont icon-shoplight'},
        hideMenu: true,
        children: [
            {
                path: 'process-config',
                name: 'process-config',
                component: createNameComponent(() => import('@/views/main/processManagement/Index.vue')),
                meta: {
                    title: 'Cấu hình tiến trình', hideClose: false
                }
            },
        ]
    },

]
export default route