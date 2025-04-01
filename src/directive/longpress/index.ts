
import type { Directive, DirectiveBinding } from 'vue'

const directive: Directive = {
  mounted(el: HTMLElement, binding: DirectiveBinding) {
    if (typeof binding.value !== 'function') {
      throw 'callback must be a function'
    }
    let pressTimer: any = null
    let start = (e: MouseEvent|TouchEvent) => {
      if (e.button) {
        if (e.type === 'click' && e.button !== 0) {
          return
        }
      }
      if (pressTimer === null) {
        pressTimer = setTimeout(() => {
          handler(e)
        }, 1000)
      }
    }
    let cancel = (e) => {
      if (pressTimer !== null) {
        clearTimeout(pressTimer)
        pressTimer = null
      }
    }
    const handler = (e: MouseEvent | TouchEvent) => {
      binding.value(e)
    }
    el.addEventListener('mousedown', start)
    el.addEventListener('touchstart', start)
    el.addEventListener('click', cancel)
    el.addEventListener('mouseout', cancel)
    el.addEventListener('touchend', cancel)
    el.addEventListener('touchcancel', cancel)
  },
}

export default directive