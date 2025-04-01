
export const closeCurrentTab = (nextPath?: string) => {
  const tab = document.getElementById('vueAdminBoxTabCloseSelf')
  if (nextPath) {
    tab?.setAttribute('nextpath', nextPath)
  }
  tab?.click()
  if (nextPath) {
    setTimeout(() => {
      tab?.removeAttribute('nextpath')
    }, 100)
  }
}

export const closeOtherTab = () => {
  const tab = document.getElementById('vueAdminBoxTabCloseOther')
  tab?.click()
}

export const closeAllTab = () => {
  const tab = document.getElementById('vueAdminBoxTabCloseAll')
  tab?.click()
}

export const refreshCurrentTab = () => {
  const tab = document.getElementById('vueAdminBoxTabRefresh')
  tab?.click()
}