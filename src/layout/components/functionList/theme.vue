<template>
<!--  <div :title="$t('message.system.setting.name')" @click="drawerChange(true)" class="item_option">-->
<!--    <el-icon>-->
<!--      <Setting/>-->
<!--    </el-icon>-->
<!--  </div>-->
<!--  <el-drawer-->
<!--      :title="'Cài đặt hệ thống'"-->
<!--      v-model="drawer"-->
<!--      size="300px"-->
<!--      :show-close="false"-->
<!--      direction="rtl">-->
<!--    <template v-for="item in lstOptions">-->
<!--      <div class="lst_menu">-->
<!--        <label style="font-size: 14px; font-weight: bold">{{ item.label }}</label>-->
<!--        <hr>-->
<!--        <template v-if="item.optionsChild" v-for="itemChild in item.optionsChild">-->
<!--          <el-button v-if="itemChild.label" class="theme-box" :icon="itemChild.icon" size="default" type="text"-->
<!--                     :disabled="itemChild.isDisable" @click="selectOption(itemChild.event)">-->
<!--            {{ itemChild.label }}-->
<!--          </el-button>-->
<!--        </template>-->
<!--      </div>-->
<!--    </template>-->
<!--  </el-drawer>-->
</template>

<script lang="ts">
import {defineComponent, ref, reactive, watch} from 'vue'
import {useStore} from 'vuex'
import {useI18n} from 'vue-i18n'
import themeIcon from './theme/theme-icon.vue'
import themeColor from './theme/theme-color.vue'
import type {Colors} from '@/theme/index'
import {style} from '@/theme/index'
import {
  Collection,
  Eleme,
  Guide,
  List,
  Operation,
  Postcard,
  ScaleToOriginal,
  Setting,
  SetUp, Stamp,
  Tickets, Tools,
  WindPower
} from "@element-plus/icons";
import MenuItem from "@/layout/components/Menu/MenuItem.vue";
import { useRouter } from 'vue-router';
interface Option {
  name: string,
  value: boolean,
  store: string
}

interface State {
  style: string,
  primaryColor: string,
  menuType: string
}

export default defineComponent({
  components: {
    Tickets,
    WindPower,
    Eleme,
    MenuItem,
    Setting,
    List,
    Operation,
    Guide,
    themeIcon,
    themeColor,

  },
  setup() {
    const router = useRouter();
    const lstOptions = [
      {
        icon: '',
        label: 'Cấu hình tham số',
        optionsChild: [
          {
            icon: SetUp,
            label: 'Cấu hình thông báo',
            event: 1,
            isDisable: false
          },
          {
            icon: Collection,
            label: 'Danh mục dùng chung',
            event: 2,
            isDisable: false
          },
          {
            icon: Collection,
            label: 'Quản lý ghi log',
            event: 3,
            isDisable: false
          },
        ],
      },
      {
        icon: '',
        label: 'Tùy chỉnh',
        optionsChild: [
          {
            icon: Postcard,
            label: 'Tùy chỉnh quản lý hồ sơ',
            event: 3,
            isDisable: false
          },
          {
            icon: ScaleToOriginal,
            label: 'Tùy chỉnh quản lý yêu cầu',
            event: 4,
            isDisable: false
          },
          {
            icon: Tickets,
            label: 'Cấu hình công việc',
            event: 5,
            isDisable: false
          },
          {
            icon: Tools,
            label: 'Cấu hình tiến trình',
            event: 'process-config',
            isDisable: false
          },
          {
            icon: Tools,
            label: 'Quản lý kết quả tiến trình',
            event: 'process-result-management',
            isDisable: false
          },
          {
            icon: Stamp,
            label: 'Tùy chỉnh đấu nối',
            event: 8,
            isDisable: true
          },
        ],
      }
    ];
    const store = useStore()
    const {t} = useI18n()
    const state: State = reactive({
      style: store.state.app.theme.state.style,
      primaryColor: store.state.app.theme.state.primaryColor,
      primaryTextColor: store.state.app.theme.state.primaryTextColor,
      menuType: store.state.app.theme.state.menuType
    })
    const themeColorArr = [
      // { color: '#409eff', textColor: '#fff', tip: 'message.system.setting.primaryColor.blue' },
      // {color: '#EE0033', textColor: '#fff', tip: 'message.system.setting.primaryColor.red'},
      // { color: '#ac25e6', textColor: '#fff', tip: 'message.system.setting.primaryColor.violet' },
      { color: '#4dc86f', textColor: '#fff', tip: 'message.system.setting.primaryColor.green' },
      // { color: '#13c2c2', textColor: '#fff', tip: 'message.system.setting.primaryColor.cyan' },
      // { color: '#333', textColor: '#fff', tip: 'message.system.setting.primaryColor.black' }
    ]
    const setTheme = () => {
      const userTheme = style[state.style]
      const body = document.getElementsByTagName('body')[0]
      body.setAttribute('data-theme', state.style)
      body.style.setProperty('--system-primary-color', state.primaryColor)
      for (let i in userTheme) {
        if (i === 'name') {
          continue;
        }
        const item: any = userTheme[i as keyof Colors]
        for (let y in item) {
          let cssVarName = '--system-' + i + '-' + y.replace(/([A-Z])/g, "-$1").toLowerCase()
          body.style.setProperty(cssVarName, item[y])
        }
      }
    }
    watch(state, (newVal) => {
      const theme = {
        state: {
          ...state
        }
      }
      store.commit('app/stateChange', {
        name: 'theme',
        value: theme
      })
      setTheme()
    })
    let drawer = ref(false)
    const options = reactive([
      {name: 'message.system.setting.other.showLogo', value: store.state.app.showLogo, store: 'showLogo'},
      {name: 'message.system.setting.other.showBreadcrumb', value: store.state.app.showTabs, store: 'showTabs'},
      {
        name: 'message.system.setting.other.keepOnlyOneMenu',
        value: store.state.app.expandOneMenu,
        store: 'expandOneMenu'
      }
    ])
    const drawerChange = (value: boolean) => {
      drawer.value = value
    }
    const change = (option: Option) => {
      store.commit(`app/stateChange`, {name: option.store, value: option.value})
    }
    const selectOption = (event: any) => {
      switch (event) {
        case 1:
          router.push('/system-setup/message-config');
          drawer.value = false;
          break;
        case 2:
          router.push('/system-setup/categories-shared');
          drawer.value = false;
          break;
        case 'process-config':
          router.push('/system-custom/process-config');
          drawer.value = false;
          break;
        case 3:
          router.push('/system-setup/logging-internal');
          drawer.value = false;
          break;
        default:
          return;
      }
    }
    setTheme()
    return {
      drawer,
      options,
      state,
      style,
      themeColorArr,
      drawerChange,
      change,
      selectOption,
      lstOptions
    }
  }
})
</script>

<style lang="scss" scoped>
i {
  margin-right: 10px;
  cursor: pointer;
  &:focus {
    outline: none;
  }
}
.list {
  padding: 0 20px;
  &-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 15px;

    span {
      font-size: 16px;
    }
  }
}
h3 {
  color: rgba(0, 0, 0, .85);
  font-size: 14px;
  line-height: 22px;
  text-align: left;
}
.lst_menu {
  margin-bottom: 10px;
}
.theme-box {
  width: 100%;
  font-size: 14px;
  text-align: left;
  display: inline;
}
.theme-box:hover {
  background-color: #f1f1f1;
}
.el-button + .el-button {
  margin-left: 0 !important;
}
.el-icon {
  margin-left: 25px;
  width: 1.3rem !important;
}
.el-icon svg {
  margin-top: 12px;
  width: 1.2rem;
  height: 1.1rem;
}
</style>