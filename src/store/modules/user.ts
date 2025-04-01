import {loginApi} from '@/api/user'
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
    },
    infoChange(state: userState, info: object) {
        state.info = info
    },
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

const getObjectCode = (componentList: any[]) => {
    let result: any[] = [];
    if (!componentList) return result;
    // Duyệt qua từng phần tử trong componentList
    componentList.forEach(component => {
        // Thêm objectId của phần tử hiện tại vào kết quả
        result.push(component.objectCode);

        // Nếu có childObjects, đệ quy vào trong đó để lấy objectId
        if (component.childObjects && component.childObjects.length > 0) {
            result = result.concat(getObjectCode(component.childObjects));
        }
    });

    return result;
}

// actions
const actions = {
    // login by login.vue
    login({commit, dispatch}: ActionContext<userState, userState>, params: any) {
        return new Promise((resolve, reject) => {
            loginApi(params)
                .then(res => {
                    commit('tokenChange', res.data.accessToken)
                    dispatch('getInfo', res.data)
                        .then(infoRes => {
                            resolve(res.data.accessToken)
                        })
                    dispatch('getMenu', res.data)
                        .then(infoRes => {
                            resolve(res.data.accessToken)
                        })
                    dispatch('getPermissions', res.data)
                        .then(infoRes => {
                            resolve(res.data.accessToken)
                        })
                }).catch(err => {
                reject(err)
            })
        })
    },

    getInfo({commit}: ActionContext<userState, userState>, params: any) {
        return new Promise((resolve, reject) => {
            commit('infoChange', params.user)
            resolve(params.user)
        })
    },

    getMenu({commit}: ActionContext<userState, userState>, params: any) {
        return new Promise((resolve, reject) => {
            let paramsMenuUrl = params.user.objectTokens
            let menu: any = []
            if (paramsMenuUrl) {
                paramsMenuUrl.forEach((e: any) => {
                    let servletPath = e.objectUrl;
                    if (!("#" == servletPath)) {
                        menu.push(servletPath.split("\\?")[0]);
                    }
                })
            }
            commit('menuChange', menu)
            resolve(menu)
        })
    },

    getPermissions({commit}: ActionContext<userState, userState>, params: any) {
        return new Promise((resolve, reject) => {
            let paramsComponentList = params.user.componentList
            let permissions: any = getObjectCode(paramsComponentList)
            commit('permissionsChange', permissions)
            resolve(permissions)
        })
    },

    // login out the system after user click the loginOut button
    loginOut({commit}: ActionContext<userState, userState>) {
        localStorage.removeItem('tabs')
        localStorage.removeItem('vuex')
        sessionStorage.removeItem('vuex')
        location.reload()
    },

}

export default {
    namespaced: true,
    state,
    actions,
    getters,
    mutations
}
