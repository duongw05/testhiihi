
import type { Directive } from 'vue'
interface ElType extends HTMLDivElement {
  __mouseDown__: any,
  __mouseUp__: any,
  __mouseMove__: any,
  __sizeChange__: any
}
interface DataDialog {
  x: number
  y: number
  width: number
  height: number
  marginTop?: string|number
  top?: number
  bottom?: number
  left?: number
  right?: number
}
const drag: Directive = {
  mounted(el: ElType, binding) {
    if (binding.value) {
      handleElShow(el)
    }
  },
  updated(el, binding) {
    if (binding.value) {
      handleElShow(el)
    } else {
      handleElHide(el)
    }
  },
  beforeUnmount(el: ElType) {
    handleElHide(el)
  }
}

async function handleElShow(el: ElType) {
  await myPromise()
  const dialog = el.querySelector('.el-dialog') as HTMLElement
  if (!dialog) {
    return
  }
  const header = el.querySelector('.el-dialog__header') as HTMLElement
  const dialogMask = el.querySelector('.el-overlay') as HTMLElement
  dialogMask.style.cssText += "overflow: hidden;"
  header.style.cursor = 'move'
  let dragStatus = false
  let data = {
    window: {
      width: 0,
      height: 0,
    },
    dialog: {
      x: 0,
      y: 0,
      width: 0,
      height: 0,
      marginTop: ""
    } as DataDialog,
    mouse: {
      x: 0,
      y: 0
    },
    drag: {
      x: 0,
      y: 0
    }
  }
  header.addEventListener('mousedown', mouseDown)
  document.addEventListener('mousemove', mouseMove)
  document.addEventListener('mouseup', mouseUp)
  window.addEventListener('resize', sizeChange)
  function handlePosition() {
    if (data.mouse.x - data.drag.x >= data.dialog.x) {
      data.drag.x = data.mouse.x - data.dialog.x
    }
    if (data.drag.x - data.mouse.x >= data.window.width - (data.dialog.x + data.dialog.width)) {
      data.drag.x = data.mouse.x + data.window.width - data.dialog.x - data.dialog.width
    }
    if (data.mouse.y - data.drag.y >= data.dialog.y) {
      data.drag.y = data.mouse.y - data.dialog.y
    }
    if (data.drag.y - data.mouse.y >= data.window.height - (data.dialog.y + data.dialog.height)) {
      data.drag.y = data.mouse.y + data.window.height - data.dialog.y - data.dialog.height
    }
    setPosition()
  }
  function setPosition() {
    let top = data.drag.y - data.mouse.y + data.dialog.y
    let left = data.drag.x - data.mouse.x + data.dialog.x
    dialog.style.cssText += `position: absolute; top: calc(${top}px - ${data.dialog.marginTop}); left: ${left}px;`
  }
  function mouseDown(e: any) {
    if (e.button !== 0) {
      return
    }
    data.window = {
      width: document.body.clientWidth,
      height: document.body.clientHeight
    }
    data.dialog = dialog.getBoundingClientRect()
    data.dialog.marginTop = window.getComputedStyle(dialog).marginTop
    data.mouse = {
      x: e.clientX,
      y: e.clientY
    }
    dragStatus = true
  }
  function mouseMove(e: any) {
    if (dragStatus) {
      data.drag = {
        x: e.clientX,
        y: e.clientY
      }
      dialogMask.style.userSelect = "none"
      handlePosition()
    }  
  }
  function mouseUp(e: any) {
    dialogMask.style.userSelect = "auto"
    dragStatus = false
  }
  function sizeChange(e: any) {
  }
  function myPromise() {
    return new Promise((resolve: any, reject: any) => {
      setTimeout(() => {
        resolve()
      }, 500)
    })
  }
  el.__mouseDown__ = mouseDown
  el.__mouseUp__ = mouseUp
  el.__mouseMove__ = mouseMove
  el.__sizeChange__ = sizeChange
}

function handleElHide(el: ElType) {
  document.removeEventListener('mousedown', el.__mouseDown__)
  document.removeEventListener('mousemove', el.__mouseMove__)
  document.removeEventListener('mouseup', el.__mouseUp__)
  window.removeEventListener('resize', el.__sizeChange__)
}

export default drag