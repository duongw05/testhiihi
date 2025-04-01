import type { Route } from '../index.type'
import Layout from '@/layout/index.vue'
import { createNameComponent } from '../createNode'
import {hasPermissionMenu} from "@/router/permission/front";
import {SETTINGS} from "@/constants/menuPermission";
const route: Route[] = [
  {
    path: '/',
    component: Layout,
    redirect: '/',
    meta: { title: 'message.menu.settings.name', icon: 'sfont system-home' },
    hideMenu: !hasPermissionMenu([...SETTINGS]),
    children: [
      {
        path: 'settings',
        component: createNameComponent(() => import('@/views/main/settings/Index.vue')),
        meta: { title: 'message.menu.settings.name', icon: 'sfont system-home', hideClose: true, roles: [...SETTINGS]
        }
      }
    ]
  }
]

export default route