interface Option<T>  {
  name: keyof optionKey<T>
  value: optionValue<T>
}

type optionKey<T> = {
  [name in keyof T]: string
}

type optionValue<T> = {
  value: T[keyof T]
}

export interface appState {
  isCollapse: boolean,
  contentFullScreen: boolean,
  showLogo: boolean,
  fixedTop: boolean,
  showTabs: boolean,
  expandOneMenu: boolean,
  elementSize: string,
  lang: string,
  theme: {
    primaryColor: '#409eff',
  },
  menuList: Array<unknown>,
  [key:string]:unknown
}

const state = () => ({
  isCollapse: false,
  contentFullScreen: false,
  showLogo: true,
  fixedTop: false,
  showTabs: true,
  expandOneMenu: true,
  elementSize: 'small',
  lang: 'vn',
  theme: {
    state: {
      style: 'light',
      primaryColor: '#EE0033',
      menuType: 'side'
    }
  },
  menuList: []
})

// mutations
const mutations = {
  isCollapseChange(state: appState, type: boolean) {
    state.isCollapse = type
  },
  contentFullScreenChange(state: appState, type: boolean) {
    state.contentFullScreen = type
  },
  menuListChange(state: appState, arr: []) {
    state.menuList = arr
  },
  stateChange(state: appState, option: Option<appState>) {
    state[option.name] = option.value
  }
}

// actions
const actions = {}

export default {
  namespaced: true,
  state,
  actions,
  mutations
}
