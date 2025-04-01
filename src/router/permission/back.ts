
import Layout from '@/layout/index.vue'
import MenuBox from '@/components/menu/index.vue'
import backConfig from './backConfig'
import { getMenuApi } from '@/api/user'

const getMenu = async () => {
  const result = await getMenuApi()
  const backRoutes = getComponents(result.data.list)
  return backRoutes
}

const getComponents = (data: any[], level = 1) => {
  const newData: any[] = data.map((item) => {
    if (item.children) {
      if (level == 1) {
        return {
          ...item,
          component: Layout,
          children: getComponents(item.children, level + 1)
        }
      } else {
        return {
          ...item,
          component: MenuBox,
          children: getComponents(item.children, level + 1)
        }
      }
    } else {
      const [first, end] =  item.component.split('_')
      const component = backConfig[first][end]
      return {
        ...item,
        component,
      }
    }
  })
  return newData
}

export default getMenu