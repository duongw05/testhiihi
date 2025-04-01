
import { createI18n, LocaleMessages, VueMessageType } from 'vue-i18n'
import store from '@/store'

const files= import.meta.globEager('./modules/*.ts')

let messages: LocaleMessages<VueMessageType> = {}
Object.keys(files).forEach((c: string) => {
  const module = files[c].default
  const moduleName: string = c.replace(/^\.\/(.*)\/(.*)\.\w+$/, '$2')
  messages[moduleName] = module
})

const lang = store.state.app.lang || navigator.language
const locale = lang.indexOf('vn') !== -1 ? 'vn' : 'vn'

const i18n = createI18n({
  __VUE_I18N_LEGACY_API__: false,
  __VUE_I18N_FULL_INSTALL__: false,
  locale: locale,
  fallbackLocale: 'vn',
  messages
})

document.getElementsByTagName('html')[0].setAttribute('lang', locale)

export default i18n
