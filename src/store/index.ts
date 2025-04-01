import { createStore, createLogger } from 'vuex'
import Persistent from './plugins/persistent'
import {userState} from "@/store/modules/user";
import {keepAliveState} from "@/store/modules/keepAlive";
import {appState} from "@/store/modules/app";
import {globalParam} from "@/store/modules/globalParam";
const debug = import.meta.env.MODE !== 'production'
const files= import.meta.globEager('./modules/*.ts')

export interface RootState {
  user: userState,
  keepAlive: keepAliveState,
  app: appState,
  globalParam: globalParam
}

let modules: any = {}
Object.keys(files).forEach((c: string) => {
  const module = files[c].default
  const moduleName: string = c.replace(/^\.\/(.*)\/(.*)\.\w+$/, '$2')
  modules[moduleName] = module
})

const persistent = Persistent({ key: 'vuex', modules, modulesKeys: {
  local: Object.keys(modules),
  session: []
} })

export default createStore<RootState>({
  modules: {
    ...modules
  },
  strict: debug,
  plugins: debug ? [createLogger(), persistent] : [persistent]
})
