/*
 * @Date: 2022-09-25 20:07:27
 * @Description: 
 */
import type { Route } from '../index.type'
import Layout from '@/layout/index.vue'
import { createNameComponent } from '../createNode'
import {hasPermissionMenu} from "@/router/permission/front";
import {SETTINGS} from "@/constants/menuPermission";
const route: Route[] = [
  {
    path: '/dynamic',
    component: Layout,
    redirect: '/dynamic/index',
    meta: { title: 'Form dynamic', icon: 'sfont system-24gl-printer' },
    children: [
      {
        path: 'index',
        component: createNameComponent(() => import('@/views/main/dynamicForm/index.vue')),
        meta: { title: 'Form dynamic', icon: 'sfont system-24gl-printer' }
      },
    ]
  }
]

export default route