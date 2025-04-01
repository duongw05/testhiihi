import {createApp} from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/theme-chalk/display.css'
import 'element-plus/dist/index.css'
import 'normalize.css'
import './assets/style/common.scss'
import './theme/modules/chinese/index.scss'
import App from './App.vue'
import store from './store'
import router from './router'
import {getAuthRoutes} from './router/permission'
import i18n from './locale'
import integerFormat from './utils/mixins/integerFormat';
import decimalFormat from './utils/mixins/decimalFormat';

getAuthRoutes().then(() => {
    const app = createApp(App)
    // @ts-ignore
    app.use(ElementPlus, {size: store.state.app.elementSize})
    app.use(store)
    app.use(router)
    app.use(i18n)
    app.mount('#app')
    app.directive('integer-format', integerFormat); // Directive cho số nguyên
    app.directive('decimal-format', decimalFormat);
})

