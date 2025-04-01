import {ActionContext} from 'vuex'
import {getData} from "@/api/global";

export interface globalParam
{
    globalValue: any,
}

const state = (): globalParam => ({
    globalValue: [],
})

// getters
const getters = {
    globalValue(state: globalParam) {
        return state.globalValue
    }
}

// mutations
const mutations = {
    globalValue(state: globalParam, token: string) {
        state.globalValue = token
    },
}

// actions
const actions = {
    // login by login.vue
    getData({commit, dispatch}: ActionContext<globalParam, globalParam>, params: any) {
        return new Promise((resolve, reject) => {
            getData(params)
                .then(res => {
                    commit('globalValue', res.data.data)
                    resolve(res.data.data)
                }).catch(err => {
                reject(err)
            })
        })
    },
}

export default {
    namespaced: true,
    state,
    actions,
    getters,
    mutations
}
