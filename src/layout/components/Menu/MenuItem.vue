<template>
  <template v-if="!menu.hideMenu">
    <el-sub-menu v-if="showMenuType === 2" :index="pathResolve" :show-timeout="0" :hide-timeout="0">
      <template #title>
        <i :class="menu.meta.icon" v-if="menu.meta.icon"></i>
        <span @click.prevent="handleLink(pathResolve, menu?.activeRouter)">{{ isBackMenu ? menu.meta.title : $t(menu.meta.title) }}</span>
      </template>
      <menu-item v-for="(item, key) in menu.children" :key="key" :menu="item" :basePath="pathResolve" />
    </el-sub-menu>
    <app-link v-else-if="showMenuType === 1" :to="pathResolve">
      <el-menu-item :index="pathResolve" v-if="!menu.children[0].children || menu.children[0].children.length === 0">
        <i :class="menu.children[0].meta.icon || menu.meta.icon" v-if="menu.children[0].meta.icon || menu.meta.icon"></i>
        <template #title>{{ isBackMenu ? menu.children[0].meta.title : $t(menu.children[0].meta.title) }}</template>
      </el-menu-item>
      <el-sub-menu v-else :index="pathResolve" :show-timeout="0" :hide-timeout="0">
        <template #title>
          <i :class="menu.children[0].meta.icon || menu.meta.icon" v-if="menu.children[0].meta.icon || menu.meta.icon"></i>
          <span>{{ isBackMenu ? menu.children[0].meta.title : $t(menu.children[0].meta.title) }}</span>
        </template>
        <menu-item v-for="(item, key) in menu.children[0].children" :key="key" :menu="item" :basePath="pathResolve" />
      </el-sub-menu>
    </app-link>
    <a v-else-if="showMenuType === 3" :href="menu.meta.jumpUrl" target="_blank" >
      <el-menu-item :index="pathResolve" class="menu-out-link" :disabled="true">
        <i :class="menu.meta.icon" v-if="menu.meta.icon"></i>
        <template #title>{{ isBackMenu ? menu.meta.title : $t(menu.meta.title) }}</template>
      </el-menu-item>
    </a>
    <app-link v-else :to="pathResolve">
      <el-menu-item :index="pathResolve">
        <i :class="menu.meta.icon" v-if="menu.meta.icon"></i>
        <template #title>
          <div class="badge">
            <span class="text-container">{{ isBackMenu ? menu.meta.title : $t(menu.meta.title) }}</span>
            <span class="badge-count" v-if="menu.name === 'warning-subject' && count.adnDupplicateCount < 100">{{count.adnDupplicateCount}}</span>
            <span class="badge-count" v-if="menu.name === 'warning-subject' && count.adnDupplicateCount >= 100">99+</span>
          </div>
        </template>
      </el-menu-item>
    </app-link>
  </template>
</template>

<script lang="ts">
import {computed, defineComponent, onMounted, reactive, ref} from 'vue'
import appLink from './Link.vue'
import {isBackMenu} from '@/config'
import {useRouter} from "vue-router";
import {Route} from "@/router/index.type";
import {mapState} from "vuex";

export default defineComponent({
  name: 'menu-item',
  props: {
    menu: {
      type: Object,
      required: true
    },
    basePath: {
      type: String,
      default: ''
    }
  },
  components: {
    appLink
  },
  computed: {
    ...mapState(['count'])
  },
  setup(props) {
    let menu = props.menu
    const router = useRouter();
    // todo: 优化if结构
    const showMenuType = computed(() => { // 0: 无子菜单， 1：有1个子菜单， 2：显示上下级子菜单
      if (menu.children && (menu.children.length > 1 || (menu.children.length === 1 && menu.alwayShow))) {
        return 2
      } else if (menu.children && menu.children.length === 1 && !menu.alwayShow) {
        return 1
      } else if (menu.meta && menu.meta.jumpUrl) {
        return 3
      } else {
        return 0
      }
    })
    // todo: 优化多层if
    const pathResolve = computed(() => {
      let path = ''
      if (showMenuType.value === 1) {
        if (menu.children[0].path.charAt(0) === '/') {
          path = menu.children[0].path
        } else {
          let char = '/'
          if (menu.path.charAt(menu.path.length - 1) === '/') {
            char = ''
          }
          path = menu.path + char + menu.children[0].path
        }
      } else {
        path = menu.path
      }
      path = props.basePath ? props.basePath + '/' + path : path
      return path
    })


    onMounted(() => {
      let tmp = props.menu
      if (tmp.children && tmp.children.length > 0) {
        tmp.children = tmp.children.filter((e : any) => !e.hideMenu)
        if (tmp.children.length == 0){
          delete tmp.children;
        }
      }
      menu = {...tmp}
    })

    const handleLink = (item: any, activeRouter: any): any => {
      if (item && activeRouter) {
        router.push(item.toString());
        return;
      }
    };
    return {
      showMenuType,
      pathResolve,
      handleLink,
      isBackMenu,
    }
  }
})
</script>

<style lang="scss" scoped>
.el-sub-menu {
  text-align: left;
}
.el-menu-item {
  text-align: left;
}
.el-menu-item i, .el-sub-menu__title i {
  padding-right: 8px;
}
.menu-out-link {
  cursor: pointer;
  opacity: 1;
}

.badge {
  position: relative;
  font-size: 14px;
}

.badge-count {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  right: -35px;
  color: white;
  font-size: 11px;
  font-weight: bold;
  text-align: center;
  line-height: 8px;
  padding: 6px 10px;
  background-color: red;
  border-radius: 50%;
}

.text-container {
  display: flex;
  //justify-content: center; /* Căn giữa theo chiều ngang */
  align-items: center; /* Căn giữa theo chiều dọc */
  width: 220px;
  line-height: 1.2; /* Đặt chiều cao cố định */
  word-break: break-word;
  overflow-wrap: break-word;
  white-space: normal;
  text-align: left;  /* Chữ căn trái */
}

</style>
