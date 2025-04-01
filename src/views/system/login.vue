<template>
  <div class="limiter" v-loading="loading"
       :element-loading-spinner="svg"
       element-loading-custom-class="loading-custom"
       element-loading-svg-view-box="0 0 200 200"
       v-if="!loginByVSA">
    <div class="container-login100">
      <div class="wrap-login100">
        <el-form :model="form" class="fm-v clearfix validate-form" label-position="top" :loading="loading" ref="formRef"
                 :rules="formRule">
          <a class="float-l w-full text-center">
            <img src="../../assets/login/logoVSA.png" class="w-half" alt="">
          </a>
          <el-row style="width: 100%">
            <el-col>
              <el-form-item :label="'Tên đăng nhập'" size="large"
                            style="margin-bottom: 20px; width: 100%; font-weight: bold;"
                            prop="name"
              >
                <el-input
                    size="large"
                    v-model="form.name"
                    :placeholder="$t('message.system.userName')"
                    type="text"
                    autofocus
                    maxlength="50"
                >
                  <template #prepend>
                    <i class="sfont system-xingmingyonghumingnicheng"></i>
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row style="width: 100%">
            <el-col>
              <el-form-item :label="$t('message.system.password')" size="large"
                            prop="password"
                            style="width: 100%; margin-bottom: 20px; font-weight: bold">
                <el-input
                    size="large"
                    ref="password"
                    v-model="form.password"
                    :type="passwordType"
                    :placeholder="$t('message.system.password')"
                    name="password"
                    maxlength="50"
                    style="width: 100%;"
                    @keydown.enter="login">
                  <template #prepend>
                    <i class="sfont system-mima"></i>
                  </template>
                  <template #append>
                    <i class="sfont password-icon" :class="passwordType ? 'system-yanjing-guan': 'system-yanjing'"
                       @click="passwordTypeChange"></i>
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <el-button class="login100-form-btn" style="width: 100%; margin-top: 20px"
                   color="#EE0033"
                   @click="login"
                   :loading="loading"
                   type="primary"
                   size="large">{{ $t('message.system.login') }}
        </el-button>
        <div class="container-change-pass">
          <span @click="accountImpact(1)"> {{ $t('message.system.changePassword') }} </span>
          <span @click="accountImpact(2)"> {{ $t('message.system.forgotPassword') }} </span>
        </div>
      </div>
    </div>
  </div>
  <el-row class="login-page" v-loading="loading"
          :element-loading-spinner="svg"
          element-loading-custom-class="loading-custom"
          element-loading-svg-view-box="0 0 200 200"
          v-if="loginByVSA" style="width: 100%">
    <el-col :span="24">
      <div class="left-container">
        <div class="top-container">
        </div>
        <div class="bottom-container">
          <div class="middle-container">
            <img src="../../assets/login/logo_background.png" alt="">
          </div>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script lang="ts">
import {systemSubTitle, systemTitle} from '@/config'
import {defineComponent, onMounted, reactive, ref} from 'vue'
import {useStore} from 'vuex'
import {useRoute, useRouter} from 'vue-router'
import {ElMessage, ElNotification} from 'element-plus'
import selectLang from '@/layout/components/functionList/word.vue'
import loginLeftPng from '@/assets/login/img.png';
import {useI18n} from 'vue-i18n'

export default defineComponent({
  components: {
    selectLang
  },
  setup() {
    const formRule = reactive({
      name: [
        {
          required: true,
          message: 'Tên đăng nhập không được để trống',
          trigger: 'blur',
        },
      ],
      password: [
        {
          required: true,
          message: 'Mật khẩu không được để trống',
          trigger: 'blur',
        },
      ],
    });
    const loading = ref(false)
    const store = useStore()
    const router = useRouter()
    let {locale, t} = useI18n()
    const loginByVSA: boolean = import.meta.env.VITE_LOGIN_IN_BY === 'VSA';
    const svg = '<radialGradient id="a7" cx=".66" fx=".66" cy=".3125" fy=".3125" gradientTransform="scale(1.5)"><stop offset="0" stop-color="#EE0033"></stop><stop offset=".3" stop-color="#EE0033" stop-opacity=".9"></stop><stop offset=".6" stop-color="#EE0033" stop-opacity=".6"></stop><stop offset=".8" stop-color="#EE0033" stop-opacity=".3"></stop><stop offset="1" stop-color="#EE0033" stop-opacity="0"></stop></radialGradient><circle transform-origin="center" fill="none" stroke="url(#a7)" stroke-width="15" stroke-linecap="round" stroke-dasharray="200 1000" stroke-dashoffset="0" cx="100" cy="100" r="70"><animateTransform type="rotate" attributeName="transform" calcMode="spline" dur="2.5" values="360;0" keyTimes="0;1" keySplines="0 0 1 1" repeatCount="indefinite"></animateTransform></circle><circle transform-origin="center" fill="none" opacity=".2" stroke="#EE0033" stroke-width="15" stroke-linecap="round" cx="100" cy="100" r="70"></circle>'
    const form = reactive({
      name: '',
      password: '',
    })
    const formRef = ref(null);
    const passwordType = ref('password')
    const passwordTypeChange = () => {
      passwordType.value === '' ? passwordType.value = 'password' : passwordType.value = ''
    }
    const login = async () => {
      await formRef.value.validate(async (valid: boolean) => {
        if (valid) {
          let params = {
            username: form.name,
            password: form.password
          }
          loginAPI(params);
        }
      })
    }
    const loginVsa = () => {
      let value = router.currentRoute.value.query?.ticket;
      if (value) {
        handleLoginVSA(value)
      } else {
        const passportUrl: string = import.meta.env.VITE_APP_PASSPORT_URL || '';
        const domainCode: string = import.meta.env.VITE_APP_DOMAIN_CODE || '';
        const loginUrl: string = import.meta.env.VITE_APP_LOGIN_URL || '';
        window.location.href = passportUrl + '?appCode=' + domainCode + '&service=' + loginUrl
      }
    }

    function loginAPI(params: any) {
      try {
        loading.value = true;
        store.dispatch('user/login', params)
            .then(async () => {
              await store.dispatch('globalParam/getData', params)
              ElNotification({
                title: 'Thành công',
                message: t('message.common.login'),
                type: 'success',
                duration: 3 * 1000
              })
              await router.push("/dashboard")
            }).finally(() => {
          loading.value = false;
        })
      } catch (e) {
        console.log(e)
      }
    }

    const handleLoginVSA = async (ticket: any) => {
      const loginUrl: string = import.meta.env.VITE_APP_LOGIN_URL || '';
      let params = {
        ticket: ticket,
        type: 'VSA',
        loginUrl: loginUrl
      }
      loginAPI(params);
    }
    const checkVsa = () => {
      let value = router.currentRoute.value.query?.ticket;
      if (value) {
        handleLoginVSA(value)
      }
    }
    const accountImpact = (type: number) => {
      switch (type) {
        case 1:
          router.push("/changePassword")
          break;
        case 2:
          const passportUrl: string = import.meta.env.VITE_RESET_PASS || '';
          window.location.href = passportUrl;
          break;
        default:
          return;
      }
    }
    onMounted(() => {
      if (loginByVSA) {
        loginVsa()
      } else {
        checkVsa()
      }
    })
    return {
      loginLeftPng,
      systemTitle,
      systemSubTitle,
      form,
      passwordType,
      passwordTypeChange,
      login,
      loginVsa,
      loading,
      loginByVSA,
      svg,
      accountImpact,
      formRule,
      formRef
    }
  }
})
</script>

<style lang="scss" scoped>
* {
  margin: 0;
  padding: 0;
  float: left;
}

.limiter {
  width: 100%;
  margin: 0 auto;
  float: left;
}

.container-login100 {
  width: 100%;
  min-height: 100vh;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  padding: 15px;
  background-repeat: no-repeat;
  background-position: center;
  background-size: cover;
  float: left;
  padding-bottom: 60px;
  padding-top: 76px;
  position: relative;
  background-image: url("../../assets/login/background_light_2023.jpg");
  box-sizing: border-box;
}

.wrap-login100 {
  width: 410px;
  background: #fff;
  border-radius: 16px;
  overflow: hidden;
  float: left;
  position: relative;

  padding: 20px;
}

.w-half {
  width: 185px;
  height: 70px;
  margin: 20px auto;
}

.w-full {
  width: 100%;
}

.text-center {
  display: flex;
  text-align: center;
}

.float-l {
  float: left;
}

.login100-form-btn {
  font-size: 16px;
  line-height: 1.1;
  text-transform: uppercase;
  padding: 0 20px;
  width: 100%;
  height: 50px;
}

.login-page {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', 'Helvetica Neue', Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol';
}

.user-layout-login {
  width: 370px;

  label {
    font-size: 14px;
  }

  .getCaptcha {
    display: block;
    width: 100%;

  }

  .forge-password {
    font-size: 14px;
  }

  button.login-button {
    padding: 0 15px;
    font-size: 14px;
    width: 100%;
  }

  .user-login-other {
    text-align: left;
    margin-top: 24px;
    line-height: 22px;

    .item-icon {
      font-size: 24px;
      color: rgba(0, 0, 0, 0.2);
      margin-left: 16px;
      vertical-align: middle;
      cursor: pointer;
      transition: color 0.3s;

      &:hover {
        color: #1890ff;
      }
    }

    .register {
      float: right;
    }
  }
}

.left-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  min-height: 100vh;
  background: #FFFFFF;

  .l-c-title {
    font-style: normal;
    font-weight: bold;
    font-size: 32px;
    line-height: 40px;
    /* identical to box height */
    text-align: center;
    color: #c52f40;
  }
}

.container-change-pass {
  display: flex;
  justify-content: space-between;
  width: 100%;
  margin-top: 10px;
  font-weight: 600;
  font-size: 13px;
  line-height: 40px;
  cursor: pointer;
  color: var(--el-text-color-regular);

  span:hover {
    color: #EE0033;
  }
}
</style>
