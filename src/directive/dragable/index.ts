
import type { Directive, DirectiveBinding } from 'vue'
interface Position {
  x: number,
  y: number
}
interface Mouse {
  down: Position,
  move: Position
}
interface ElType extends HTMLElement {
  __mouseDown__: any,
  __mouseUp__: any,
  __mouseMove__: any,
  __parentDom__: HTMLElement,
  __position__: Position
}
const directive: Directive = {
  mounted: (el: ElType, binding: DirectiveBinding) => {
    setParentDom(el, binding, false)
    let parentDomRect: DOMRect
    let elDomRect: DOMRect
    let mouseData: Mouse = {
      down: { x: 0, y: 0},
      move: { x: 0, y: 0 }
    }
    let mouseDown: boolean = false
    el.__position__ = {
      x: 0,
      y: 0
    }
    let bodyUserSelect: string = 'text'
    
    function handleMouseDown(e: MouseEvent) {
      if (e.button !== 0) {
        return
      }
      mouseData.down = {
        x: e.clientX,
        y: e.clientY
      }
      mouseDown = true
      parentDomRect = el.__parentDom__.getBoundingClientRect()
      elDomRect = el.getBoundingClientRect()
      bodyUserSelect = document.querySelector('body')!.style.userSelect
      document.querySelector('body')!.style.userSelect = "none"
    }
    function handleMouseMove(e: MouseEvent) {
      if (!mouseDown) {
        return
      }
      mouseData.move = {
        x: e.clientX,
        y: e.clientY
      }
      setPosition()
    }
    function handleMouseUp(e: MouseEvent) {
      if (mouseDown) {
        mouseDown = false
        document.querySelector('body')!.style.userSelect = bodyUserSelect
      }
    }
    function setPosition() {
      const x = mouseData.move.x + elDomRect.x - parentDomRect.x - mouseData.down.x
      const y = mouseData.move.y + elDomRect.y - parentDomRect.y - mouseData.down.y
      if (x < 0) {
        el.__position__.x = 0
      } else if (x > parentDomRect.width - elDomRect.width) {
        el.__position__.x = parentDomRect.width - elDomRect.width
      } else {
        el.__position__.x = x
      }
      if (y < 0) {
        el.__position__.y = 0
      } else if (y > parentDomRect.height - elDomRect.height) {
        el.__position__.y = parentDomRect.height - elDomRect.height
      } else {
        el.__position__.y = y
      }
      el.style.cssText += `
        position: absolute;
        z-index: 100;
        left: ${ el.__position__.x }px;
        top: ${ el.__position__.y }px;
      `
    }
    el.__mouseDown__ = handleMouseDown
    el.__mouseMove__ = handleMouseMove
    el.__mouseUp__ = handleMouseUp
    el.addEventListener('mousedown', el.__mouseDown__)
    document.addEventListener('mousemove', el.__mouseMove__)
    document.addEventListener('mouseup', el.__mouseUp__)
  },
  updated(el, binding) {
    setParentDom(el, binding, true)
  },
  beforeUnmount(el: ElType) {
    document.removeEventListener('mousedown', el.__mouseDown__)
    document.removeEventListener('mousemove', el.__mouseMove__)
    document.removeEventListener('mouseup', el.__mouseUp__)
  }
}
function setParentDom(el: ElType, binding: DirectiveBinding, updated: boolean) {
  
  const array = [
    { name: 'father', dom: el.parentElement }
  ]

  let parentDom: HTMLElement | HTMLBodyElement
  if (binding.value) {
    const findArr = array.find((arr) => {
      return arr.name === binding.value
    })
    if (findArr && findArr.dom) {
      parentDom = findArr.dom
    } else {
      parentDom = document.querySelector(binding.value) || array[0].dom as HTMLElement || array[1].dom
    }
  } else {
    parentDom = array[0].dom as HTMLElement || array[1].dom
  }
  const parentDomRect = parentDom.getBoundingClientRect()
  const elDomRect = el.getBoundingClientRect()
  if (el.__parentDom__) {
    el.__parentDom__.style.position = 'static'
  }
  el.__parentDom__ = parentDom
  el.__parentDom__.style.position = 'relative'
  
  if (updated) {
    el.__position__ = {
      x: elDomRect.x - parentDomRect.x,
      y: elDomRect.y - parentDomRect.y
    }
    // return
    el.style.cssText += `
      position: absolute;
      z-index: 100;
      left: ${ el.__position__.x }px;
      top: ${ el.__position__.y }px;
    `
  }
}
export default directive