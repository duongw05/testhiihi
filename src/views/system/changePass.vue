<template>
  <div class="limiter" v-loading="loading"
       :element-loading-spinner="svg"
       element-loading-custom-class="loading-custom"
       element-loading-svg-view-box="0 0 200 200">
    <div class="container-login100">
      <div class="wrap-login100">
        <div id="popover1" role="tooltip" v-if="showPopover">
          <p>Quy tắc đặt mật khẩu bao gồm:</p>
          <ul>
            <li>- Ký tự, chữ số</li>
            <li>- Ký tự đặc biệt: ~!@#$%^&*()_+|`-={}[]:";'<>?,./</li>
            <li>- Độ dài tối thiểu là 8</li>
            <li>- Không chứa user và bắt đầu bằng 123, 456, 789, 147, 258, 852, 741, abc, 369</li>
            <li>- Ví dụ về mật khẩu đúng: 12a3@aBa@321</li>
          </ul>
        </div>
        <el-form :model="form" class="fm-v clearfix validate-form" ref="formRef" :rules="formRule"
                 label-position="top" :loading="form.loading">
          <h1 class="w-full text-center">
            ĐỔI MẬT KHẨU
          </h1>
          <el-row style="width: 100%; margin-top: 20px">
            <el-col :span="24">
              <el-form-item :label="'Tên đăng nhập'" size="large" class="item-input"
                            prop="name">
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
            <el-form-item :label="$t('message.system.password')" size="large"
                          prop="password" class="item-input">
              <el-input
                  size="large"
                  ref="password"
                  v-model="form.password"
                  :type="passwordType"
                  :placeholder="$t('message.system.password')"
                  name="password"
                  maxlength="50"
                  style="width: 100%;"
              >
                <template #prepend>
                  <i class="sfont system-mima"></i>
                </template>
                <template #append>
                  <i class="sfont password-icon" :class="passwordType ? 'system-yanjing-guan': 'system-yanjing'"
                     @click="passwordTypeChange"></i>
                </template>
              </el-input>
            </el-form-item>
          </el-row>
          <el-row style="width: 100%">
            <el-form-item :label="$t('message.system.newPassword')" size="large" class="item-input"
                          prop="newPassword">
              <el-input
                  aria-describedby="popover1"
                  class="inputNewPass"
                  size="large"
                  ref="password"
                  v-model="form.newPassword"
                  :type="newPasswordType"
                  :placeholder="$t('message.system.newPassword')"
                  name="password"
                  maxlength="50"
                  style="width: 100%;"
                  @focus="showPopover = true"
                  @blur="showPopover = false"
              >
                <template #prepend>
                  <i class="sfont system-mima"></i>
                </template>
                <template #append>
                  <i class="sfont password-icon" :class="newPasswordType ? 'system-yanjing-guan': 'system-yanjing'"
                     @click="newPasswordTypeChange"></i>
                </template>
              </el-input>
            </el-form-item>
          </el-row>
          <el-row style="width: 100%">
            <el-form-item :label="$t('message.system.confirmPassword')" size="large"
                          class="item-input"
                          prop="confirmPassword">
              <el-input
                  size="large"
                  ref="password"
                  v-model="form.confirmPassword"
                  :type="confPasswordType"
                  :placeholder="$t('message.system.confirmPassword')"
                  name="password"
                  maxlength="50"
                  style="width: 100%;"
              >
                <template #prepend>
                  <i class="sfont system-mima"></i>
                </template>
                <template #append>
                  <i class="sfont password-icon" :class="confPasswordType ? 'system-yanjing-guan': 'system-yanjing'"
                     @click="confPasswordTypeChange"></i>
                </template>
              </el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item :label="'Mã captcha'" size="large" class="item-input"
                          prop="captcha">
              <el-input
                  size="large"
                  v-model="form.captcha"
                  :placeholder="$t('message.system.captcha')"
                  name="password"
                  maxlength="50"
                  style="width: 100%;"
              >
                <template #prepend>
                  <i class="sfont system-mima"></i>
                </template>
                <template #append>
                  <canvas id="captchaCanvas" width="150px" height="50"
                          style="text-align: center; align-items: center"></canvas>
                  <el-button style="align-items: center; height: 100%; font-size: 20px; padding: 0 10px" @click="generateCaptcha">
                    <el-icon>
                      <Refresh/>
                    </el-icon>
                  </el-button>
                </template>
              </el-input>
            </el-form-item>
          </el-row>
        </el-form>
        <el-button class="login100-form-btn" style="width: 100%; margin-top: 20px"
                   color="#EE0033"
                   @click="fnChangePassword"
                   :loading="form.loading"
                   type="primary"
                   size="large">{{ $t('message.system.changePassword') }}
        </el-button>
      </div>
    </div>
  </div>
  <el-row class="login-page" v-loading="loading" v-if="loading"
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
import {useRouter} from 'vue-router'
import {ElMessage, ElNotification, FormInstance} from 'element-plus'
import selectLang from '@/layout/components/functionList/word.vue'
import {useI18n} from 'vue-i18n'
import {changePassword} from "@/api/user";
import {Refresh} from "@element-plus/icons";

export default defineComponent({
  components: {
    Refresh,
    selectLang
  },
  setup() {
    const captchaText = ref("");
    const validateCaptcha = (rule: any, value: any, callback: any) => {
      if (value === '') {
        callback(new Error('Mã captcha không được để trống'))
      } else if (value !== captchaText.value) {
        callback(new Error("Captcha không trùng khớp"))
      } else {
        callback()
      }
    }
    const validatePass = (rule: any, value: any, callback: any) => {
      if (value === '') {
        callback(new Error('Mật khẩu mới không được để trống'))
      } else {
        if (form.newPassword !== '') {
          if (!formRef.value) return
          formRef.value.validateField('confirmPassword')
        }
        callback()
      }
    }
    const validatePass2 = (rule: any, value: any, callback: any) => {
      if (value === '') {
        callback(new Error('Xác nhận mật khẩu không được để trống'))
      } else if (value !== form.newPassword) {
        callback(new Error("Xác nhận mật khẩu không trùng khớp"))
      } else {
        callback()
      }
    }

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
      newPassword: [
        {
          required: true,
          validator: validatePass,
          trigger: 'blur',
        },
      ],
      confirmPassword: [
        {
          required: true,
          validator: validatePass2,
          trigger: 'blur',
        },
      ],
      captcha: [{ required: true, validator: validateCaptcha, trigger: "blur" }],
    });

    const loading = ref(false)
    const store = useStore()
    const router = useRouter()
    let {locale, t} = useI18n()
    let showPopover = ref(false)
    const svg = '<radialGradient id="a7" cx=".66" fx=".66" cy=".3125" fy=".3125" gradientTransform="scale(1.5)"><stop offset="0" stop-color="#EE0033"></stop><stop offset=".3" stop-color="#EE0033" stop-opacity=".9"></stop><stop offset=".6" stop-color="#EE0033" stop-opacity=".6"></stop><stop offset=".8" stop-color="#EE0033" stop-opacity=".3"></stop><stop offset="1" stop-color="#EE0033" stop-opacity="0"></stop></radialGradient><circle transform-origin="center" fill="none" stroke="url(#a7)" stroke-width="15" stroke-linecap="round" stroke-dasharray="200 1000" stroke-dashoffset="0" cx="100" cy="100" r="70"><animateTransform type="rotate" attributeName="transform" calcMode="spline" dur="2.5" values="360;0" keyTimes="0;1" keySplines="0 0 1 1" repeatCount="indefinite"></animateTransform></circle><circle transform-origin="center" fill="none" opacity=".2" stroke="#EE0033" stroke-width="15" stroke-linecap="round" cx="100" cy="100" r="70"></circle>'
    const form = reactive({
      name: '',
      password: '',
      loading: false,
      newPassword: '',
      confirmPassword: '',
      captcha: ''
    })
    const formRef = ref<FormInstance>();
    const passwordType = ref('password');
    const newPasswordType = ref('password');
    const confPasswordType = ref('password');
    const generateCaptcha = () => {
      const canvas = document.getElementById("captchaCanvas") as HTMLCanvasElement;
      const ctx = canvas.getContext("2d");
      if (ctx) {
        const characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        let captcha = "";
        for (let i = 0; i < 6; i++) {
          captcha += characters.charAt(Math.floor(Math.random() * characters.length));
        }

        captchaText.value = captcha;
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        ctx.font = "25px Arial";
        ctx.fillText(captchaText.value, 10, 35);
      }
    };
    onMounted(generateCaptcha);

    const passwordTypeChange = () => {
      passwordType.value === '' ? passwordType.value = 'password' : passwordType.value = ''
    }
    const newPasswordTypeChange = () => {
      newPasswordType.value === '' ? newPasswordType.value = 'password' : newPasswordType.value = ''
    }
    const confPasswordTypeChange = () => {
      confPasswordType.value === '' ? confPasswordType.value = 'password' : confPasswordType.value = ''
    }

    const resetForm = () => {
      form.name = ''
      form.password = ''
      form.loading = false
      form.newPassword = ''
      form.confirmPassword = ''
    }
    const fnChangePassword = async () => {
      await formRef.value.validate(async (valid: boolean) => {
        if (valid) {
          loading.value = true;
          form.loading = true;
          let params = {
            username: form.name,
            password: form.password,
            newPassword: form.newPassword,
            confirmPassword: form.confirmPassword,
          }
          try {
            const axiosResponse = await changePassword(params);
            if (axiosResponse.data.code && axiosResponse.data.code === 'SUCCESS') {
              ElMessage({
                message: axiosResponse.data.message,
                type: 'success',
                duration: 3 * 1000
              });
              resetForm();
              await router.push('/login');
            } else {
              ElMessage({
                message: axiosResponse.data.message,
                type: 'error',
                duration: 3 * 1000
              });
            }
          } catch (e) {
            console.log(e)
          } finally {
            form.loading = false;
            loading.value = false;
          }
        }
      });
    }
    return {
      systemTitle,
      systemSubTitle,
      form,
      passwordType,
      newPasswordType,
      confPasswordType,
      passwordTypeChange,
      fnChangePassword,
      loading,
      svg,
      newPasswordTypeChange,
      confPasswordTypeChange,
      showPopover,
      generateCaptcha,
      captchaText,
      formRule,
      formRef,
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
  //overflow: hidden;
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

.inputNewPass:focus + #popover1 {
  display: block;
}

#popover1 {
  //display: none;
  width: 100%;
  box-sizing: border-box;
  padding: 20px;
  position: absolute;
  left: calc(100% + 10px);
  transform: translateY(100%);
  z-index: 99;
  font-family: Arial, sans-serif;
  max-width: 500px;
  background-color: white;
  border-radius: 10px;

  p {
    font-weight: bold;
    margin-bottom: 8px;
  }

  ul {
    list-style-type: disc;
    padding-left: 20px;
    margin: 0;

    li {
      display: block;
      margin-bottom: 5px;
      line-height: 1.5;
    }
  }
}
.item-input {
  width: 100%;
  margin-bottom: 20px;
  font-weight: bold;
  float: none;
}
</style>
