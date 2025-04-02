import {getInfoApi, getMenuApi, getPermissionLs, loginApi, loginOutApi} from '@/api/user'
import {ActionContext} from 'vuex'

export interface userState {
    token: string,
    info: object,
    menuList: any,
    permissionList: any,
}

const state = (): userState => ({
    token: '',
    info: {},
    menuList: [],
    permissionList: [],
})

// getters
const getters = {
    token(state: userState) {
        return state.token
    }
}

// mutations
const mutations = {
    tokenChange(state: userState, token: string) {
        state.token = token
    },
    infoChange(state: userState, info: object) {
        state.info = info
    },
    menuChange(state: userState, info: object) {
        state.menuList = info
    },
    permissionsChange(state: userState, info: object) {
        state.permissionList = info
    },
}

// actions
const actions = {
    // login by login.vue
    login({commit, dispatch}: ActionContext<userState, userState>, params: any) {
        return new Promise((resolve, reject) => {
            loginApi(params)
                .then(async res => {
                    commit('tokenChange', res.data.accessToken)
                    dispatch('getInfo', {token: res.data.accessToken})
                        .then(infoRes => {
                            resolve(res.data.accessToken)
                        })
                    dispatch('getMenu', {token: res.data.accessToken})
                        .then(infoRes => {
                            resolve(res.data.accessToken)
                        })
                    dispatch('getPermissionLs', {token: res.data.accessToken})
                        .then(infoRes => {
                            resolve(res.data.accessToken)
                        })
                }).catch(err => {
                reject(err)
            })
        })
    },
    // get user info after user logined
    getInfo({commit}: ActionContext<userState, userState>, params: any) {
        return new Promise((resolve, reject) => {
            getInfoApi(params)
                .then(res => {
                    commit('infoChange', res.data)
                    resolve(res.data.info)
                })
        })
    },

    getMenu({commit}: ActionContext<userState, userState>) {
        return new Promise((resolve, reject) => {
            getMenuApi()
                .then(res => {
                    commit('menuChange', res.data)
                    resolve(res.data)
                })
        })
    },

    getPermissionLs({commit}: ActionContext<userState, userState>) {
        return new Promise((resolve, reject) => {
            getPermissionLs()
                .then(res => {
                    commit('permissionsChange', res.data)
                    resolve(res.data)
                })
        })
    },

    // login out the system after user click the loginOut button
    loginOut({commit}: ActionContext<userState, userState>) {
        loginOutApi()
            .then(res => {

            })
            .catch(error => {

            })
            .finally(() => {
                localStorage.removeItem('tabs')
                localStorage.removeItem('vuex')
                sessionStorage.removeItem('vuex')
                location.reload()
            })
    }
}

export default {
    namespaced: true,
    state,
    actions,
    getters,
    mutations
}
