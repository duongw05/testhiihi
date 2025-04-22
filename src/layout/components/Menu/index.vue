<template>
  <el-scrollbar>
    <el-menu
        class="layout-menu system-scrollbar"
        background-color="var(--system-menu-background)"
        text-color="var(--system-menu-text-color)"
        active-text-color="var(--system-primary-color)"
        :mode="mode"
        :default-active="activeMenu"
        :class="isCollapse? 'collapse': ''"
        :collapse="isCollapse"
        :collapse-transition="false"
        :unique-opened="expandOneMenu"
    >
      <menu-item v-for="(menu, key) in allRoutes" :key="key" :menu="menu" />
    </el-menu>
  </el-scrollbar>
</template>

<script lang="ts">
import {defineComponent, computed, onMounted, onUnmounted} from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useStore } from 'vuex'
import MenuItem from './MenuItem.vue'
export default defineComponent({
  props: {
    mode: {
      type: String,
      default: "vertical"
    }
  },
  components: {
    MenuItem
  },
  setup(props) {
    const { mode } = props
    const store = useStore()
    const isCollapse = computed(() => store.state.app.isCollapse)
    const expandOneMenu = computed(() => store.state.app.expandOneMenu)
    const allRoutes = useRouter().options.routes
    const route = useRoute()
    const activeMenu: any = computed(() => {
      const { meta, path } = route;
      if (meta.activeMenu) {
        return path;
      }
      if (route?.matched && route.matched.length > 2) {
        let tmp = route.matched[route.matched.length - 2]
        if (tmp.meta.activeBreadcrumb){
          return tmp.path;
        }
      }
      return path;
    });

    let intervalId: number | undefined;

    onUnmounted(() => {
      if (intervalId) clearInterval(intervalId);
    });

    return {
      isCollapse,
      expandOneMenu,
      allRoutes,
      activeMenu,
      mode,
    }
  }
})
</script>

<style lang="scss" scoped>
  .el-scrollbar {
    background-color: var(--system-menu-background);
    --system-menu-background: #2E7D32;
    flex: 1;
  }
  .layout-menu {
    width: 100%;
    border: none;
    &.collapse {
      margin-left: 0px;
    }
    :deep() {
      .el-menu-item, .el-sub-menu {
        background-color: var(--system-menu-background) !important;
        --system-menu-background: #2E7D32;
      }
      .el-menu-item i, .el-menu-item-group__title, .el-sub-menu__title i {
        color: var(--system-menu-text-color);
        --system-menu-text-color: #FFFFFF;
      }
      .el-menu-item, .el-sub-menu__title{
          color: #FFFFFF;
        &.is-active {
          background-color: var(--system-primary-color) !important;
          color: var(--system-primary-text-color) !important;
          i {
            color: var(--system-primary-text-color) !important;
          }
          &:hover {
            background-color: var(--system-primary-color) !important;
            color: var(--system-primary-text-color) !important;
          }
        }
        &:hover {
          background-color: var(--system-menu-hover-background) !important;
          --system-menu-hover-background: #66BB6A;

        }
      }
      .el-sub-menu {
        &.is-active {
          >.el-sub-menu__title, >.el-sub-menu__title i {
            color: #FFFFFF;
          }
        }
        .el-menu-item {
          background-color: var(--system-menu-children-background) !important;
          --system-menu-children-background: #4CAF50;
          &.is-active {
            background-color: var(--system-primary-color) !important;
            --system-primary-color: #A5D6A7;
            color: var(--system-primary-text-color) !important;
            &:hover {
              background-color: var(--system-primary-color) !important;
              color: var(--system-primary-text-color) !important;
            }
          }
          &:hover {
            background-color: var(--system-menu-hover-background) !important;
          }
        }
        .el-sub-menu {
          .el-sub-menu__title {
            background-color: var(--system-menu-children-background) !important;
            --system-menu-children-background: #2E7D32;
            &:hover {
              background-color: var(--system-menu-hover-background) !important;
            }
          }
        }
      }
    }
  }
</style>
