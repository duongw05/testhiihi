<template>
  <div class="limiter" v-loading="loading"
       :element-loading-spinner="svg"
       element-loading-custom-class="loading-custom"
       element-loading-svg-view-box="0 0 200 200">
    <div class="container-login100">
      <div class="wrap-login100">
        <el-form :model="form" class="fm-v clearfix validate-form" label-position="top" :loading="form.loading">
          <h1 class="w-full text-center">
            ĐỔI MẬT KHẨU
          </h1>

          <el-form-item :label="'Tên đăng nhập'" size="large" style="width: 100%; font-weight: bold" :required="true">
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
        </el-form>
        <el-button class="login100-form-btn" style="width: 100%; margin-top: 20px"
                   color="#4dc86f"
                   @click="fnChangePassword"
                   :loading="form.loading"
                   type="primary"
                   size="large">{{ $t('message.system.forgotPassword') }}
        </el-button>
      </div>
    </div>
  </div>
  <el-row class="login-page" v-loading="loading"
          :element-loading-spinner="svg"
          element-loading-custom-class="loading-custom"
          element-loading-svg-view-box="0 0 200 200"
          style="width: 100%">
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
import {changePassword, forgotPassword} from "@/api/user";

export default defineComponent({
  components: {
    selectLang
  },
  setup() {
    const loading = ref(false)
    const store = useStore()
    const router = useRouter()
    let {locale, t} = useI18n()
    const svg =  '<radialGradient id="a7" cx=".66" fx=".66" cy=".3125" fy=".3125" gradientTransform="scale(1.5)"><stop offset="0" stop-color="#4dc86f"></stop><stop offset=".3" stop-color="#4dc86f" stop-opacity=".9"></stop><stop offset=".6" stop-color="#4dc86f" stop-opacity=".6"></stop><stop offset=".8" stop-color="#4dc86f" stop-opacity=".3"></stop><stop offset="1" stop-color="#4dc86f" stop-opacity="0"></stop></radialGradient><circle transform-origin="center" fill="none" stroke="url(#a7)" stroke-width="15" stroke-linecap="round" stroke-dasharray="200 1000" stroke-dashoffset="0" cx="100" cy="100" r="70"><animateTransform type="rotate" attributeName="transform" calcMode="spline" dur="2.5" values="360;0" keyTimes="0;1" keySplines="0 0 1 1" repeatCount="indefinite"></animateTransform></circle><circle transform-origin="center" fill="none" opacity=".2" stroke="#4dc86f" stroke-width="15" stroke-linecap="round" cx="100" cy="100" r="70"></circle>'
    const form = reactive({
      name: '',
      password: '',
      loading: false,
      newPassword: '',
      confirmPassword: ''
    })
    const passwordType = ref('password');
    const newPasswordType = ref('password');
    const confPasswordType = ref('password');
    const passwordTypeChange = () => {
      passwordType.value === '' ? passwordType.value = 'password' : passwordType.value = ''
    }
    const newPasswordTypeChange = () => {
      newPasswordType.value === '' ? newPasswordType.value = 'password' : newPasswordType.value = ''
    }
    const confPasswordTypeChange = () => {
      confPasswordType.value === '' ? confPasswordType.value = 'password' : confPasswordType.value = ''
    }
    const checkForm = () => {
      return new Promise((resolve, reject) => {
        if (form.name === '') {
          ElNotification({
            title: 'Lỗi',
            message: t('message.system.userNameValidate'),
            type: 'error',
            duration: 3 * 1000
          })
          loading.value = false;
          return;
        }
        if (form.password === '') {
          ElNotification({
            title: 'Lỗi',
            message: t('message.system.passwordValidate'),
            type: 'error',
            duration: 3 * 1000
          })
          loading.value = false;
          return;
        }
        if (form.newPassword === '') {
          ElNotification({
            title: 'Lỗi',
            message: t('message.system.passwordValidate'),
            type: 'error',
            duration: 3 * 1000
          })
          loading.value = false;
          return;
        }
        if (form.confirmPassword === '') {
          ElNotification({
            title: 'Lỗi',
            message: t('message.system.passwordValidate'),
            type: 'error',
            duration: 3 * 1000
          })
          loading.value = false;
          return;
        }
        resolve(true)
      })
    }
    const fnForgotPassword = async () => {
      loading.value = true;
      await checkForm()
          .then(() => {
            form.loading = true;
            let params = {
              username: form.name,
              password: form.password,
              newPassword: form.newPassword,
              confirmPassword: form.confirmPassword,
            }
            forgotPassword(params);
            form.loading = false;
          })
      loading.value = false;
    }

    onMounted(() => {

    })
    return {
      systemTitle,
      systemSubTitle,
      form,
      passwordType,
      newPasswordType,
      confPasswordType,
      passwordTypeChange,
      fnChangePassword: fnForgotPassword,
      loading,
      svg,
      newPasswordTypeChange,
      confPasswordTypeChange
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
  text-align: center;
  line-height: 40px;
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
  font-weight: 500;
  font-size: 13px;
  line-height: 40px;
  a {
    cursor: pointer;
    &:hover {
      color: red;
    }
  }
}
</style>
