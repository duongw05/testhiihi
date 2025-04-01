import type { Route } from '../index.type'
import Layout from '@/layout/index.vue'
import { createNameComponent } from '../createNode'
const route: Route[] = [
  {
    path: '/pages',
    component: Layout,
    redirect: '/pages/crudTable',
    meta: { title: 'message.menu.pageCard.name', icon: 'sfont system-pageCard' },
    alwayShow: true,
    children: [
      {
        path: 'crudTable',
        component: createNameComponent(() => import('@/views/main/pages/crudTable/index.vue')),
        meta: { title: 'message.menu.pageCard.crudTable', cache: false, roles: ['admin', 'editor'] }
      },
      {
        path: 'card',
        component: createNameComponent(() => import('@/views/main/pages/card/index.vue')),
        meta: { title: 'message.menu.pageCard.card', cache: true }
      },
      {
        path: 'cardDetail',
        component: createNameComponent(() => import('@/views/main/pages/card/detail/index.vue')),
        meta: { title: 'message.menu.pageCard.card', cache: false },
      },
      {
        path: 'work',
        component: createNameComponent(() => import('@/views/main/pages/work/index.vue')),
        meta: { title: 'message.menu.pageCard.work' }
      }
    ]
  }
]

export default route
