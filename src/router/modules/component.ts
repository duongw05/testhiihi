import type { Route } from '../index.type'
import Layout from '@/layout/index.vue'
import { createNameComponent } from '../createNode'
const route: Route[] = [
  {
    path: '/component',
    component: Layout,
    redirect: '/component/table',
    meta: { title: 'message.menu.component.name', icon: 'sfont system-component' },
    alwayShow: true,
    children: [
      {
        path: 'wordEditor',
        component: createNameComponent(() => import('@/views/main/components/wordEditor/index.vue')),
        meta: { title: 'message.menu.component.wordEditor' },
      },
      {
        path: 'dragPane',
        component: createNameComponent(() => import('@/views/main/components/dragPane/index.vue')),
        meta: { title: 'message.menu.component.dragPane' },
      },
      {
        path: 'cutPhoto',
        component: createNameComponent(() => import('@/views/main/components/cutPhoto/index.vue')),
        meta: { title: 'message.menu.component.cutPhoto' },
      },
      {
        path: 'exportExcel',
        component: createNameComponent(() => import('@/views/main/components/exportExcel/index.vue')),
        meta: { title: 'message.menu.component.exportExcel' },
      },
    ]
  }
]

export default route
