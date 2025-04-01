import i18n from '@/locale'
import { isBackMenu, systemTitle } from '@/config'
const { t } = i18n.global

export function changeTitle(name: any) {
  document.title = `${t(systemTitle)} - ${t(name)} `
}
