/*
 * @Date: 2023-03-10 19:34:30
 * @Description:
 */
import { createNameComponent } from '../createNode';

const dashboard = {
  dashboard: createNameComponent(() => import('@/views/main/dashboard/Index.vue')),
}


const component = {
  wordEditor: createNameComponent(() => import('@/views/main/components/wordEditor/index.vue')),
  dragPane: createNameComponent(() => import('@/views/main/components/dragPane/index.vue')),
  cutPhoto: createNameComponent(() => import('@/views/main/components/cutPhoto/index.vue')),
  exportExcel: createNameComponent(() => import('@/views/main/components/exportExcel/index.vue')),
}

const pages = {
  crudTable: createNameComponent(() => import('@/views/main/pages/crudTable/index.vue')),
  card: createNameComponent(() => import('@/views/main/pages/card/index.vue')),
  work: createNameComponent(() => import('@/views/main/pages/work/index.vue')),
  baidu: createNameComponent(() => import('@/views/main/pages/baidu/index.vue')),
}

const menu = {
}

const directive = {
}

const echarts = {
  bar: createNameComponent(() => import('@/views/main/echarts/bar.vue')),
  line: createNameComponent(() => import('@/views/main/echarts/line.vue')),
  pie: createNameComponent(() => import('@/views/main/echarts/pie.vue')),
  radar: createNameComponent(() => import('@/views/main/echarts/radar.vue')),
}

const staffManage = {
  staffManage: createNameComponent(() => import('@/views/main/pages/crudTable/index.vue'))
}

const settings = {
  settings: createNameComponent(() => import('@/views/main/settings/Index.vue')),
}

const allRoutes = {
  dashboard,
  document,
  component,
  pages,
  menu,
  directive,
  echarts,
  print,
  staffManage,
  settings
}

export default allRoutes
