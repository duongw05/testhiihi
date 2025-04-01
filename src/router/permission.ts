
import router, { modules } from './index'
import store from '@/store'
import { isBackMenu } from '@/config'

import FrontRoutes from './permission/front'
import getMenu from './permission/back'

export async function addRoutes() {
  return new Promise<void>((resolve) => {
    if (isBackMenu) {
      getMenu()
      .then((data) => {
        data.forEach(item => {
          modules.push(item)
          router.addRoute(item)
        })
        resolve()
      })
      return
    }    
    FrontRoutes.forEach(item => {
      modules.push(item)
      router.addRoute(item)
    })
    resolve()
  })
}

export async function getAuthRoutes() {
  // if (store.state.user.token) {
    await addRoutes()
  // }
}
