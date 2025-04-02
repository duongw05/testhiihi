<template>
  <div class="login-container">
    <div class="language-switch">
      <select-lang/>
    </div>
    <div class="login-image">
    </div>
    <div class="login-form">
      <div class="company-info">
        <img class="company-logo" width="10%" :src="loginLeftPng" alt="Login Image">
      </div>
      <div class="welcome-message">
      </div>
      <el-form :model="form" @submit.native.prevent="submit" label-position="left" label-width="100px">
        <el-form-item :label="$t('message.system.userName') + ':'">
          <el-input
              size="large"
              v-model="form.name"
              :placeholder="$t('message.system.userName')"
              type="text"
              maxlength="50"
          >
            <template #prepend>
              <i class="sfont system-xingmingyonghumingnicheng"></i>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item :label="$t('message.system.password') + ':'">
          <el-input
              size="large"
              ref="password"
              v-model="form.password"
              :type="passwordType"
              :placeholder="$t('message.system.password')"
              name="password"
              maxlength="50"
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
        <el-form-item>
          <el-button style="font-family: sans-serif;font-size: 18px;font-weight: bold; margin-top: 10px;"
                     :loading="form.loading"
                     type="primary" color="var(--system-primary-color)" native-type="submit" size="large">{{ $t('message.system.login') }}
          </el-button>
        </el-form-item>
      </el-form>
      <div class="company-address">
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {systemSubTitle, systemTitle} from '@/config'
import {defineComponent, reactive, ref} from 'vue'
import {useStore} from 'vuex'
import {useRoute, useRouter} from 'vue-router'
import {ElMessage} from 'element-plus'
import selectLang from '@/layout/components/functionList/word.vue'
import loginLeftPng from '@/assets/login/logo.png';
import {useI18n} from 'vue-i18n'
import { getAuthRoutes } from '@/router/permission'

export default defineComponent({
  components: {
    selectLang
  },
  setup() {
    const store = useStore()
    const router = useRouter()
    const route = useRoute()
    let {locale, t} = useI18n()
    const form = reactive({
      name: 'admin',
      password: '123456',
      loading: false
    })
    const passwordType = ref('password')
    const passwordTypeChange = () => {
      passwordType.value === '' ? passwordType.value = 'password' : passwordType.value = ''
    }
    const checkForm = () => {
      return new Promise((resolve, reject) => {
        if (form.name === '') {
          ElMessage.warning({
            message: t('message.system.userNameValidate'),
            type: 'warning'
          });
          return;
        }
        if (form.password === '') {
          ElMessage.warning({
            message: t('message.system.passwordValidate'),
            type: 'warning'
          })
          return;
        }
        resolve(true)
      })
    }
    const submit = () => {
      checkForm()
          .then(() => {
            form.loading = true
            let params = {
              username: form.name,
              password: form.password
            }
            store.dispatch('user/login', params)
                .then(async () => {
                  // await store.dispatch('globalParam/getData', params)
                  ElMessage.success({
                    message: t('message.common.login'),
                    type: 'success',
                    showClose: true,
                    duration: 1000
                  })
                  // console.log("store", localStorage.getItem('vuex'))
                  setTimeout(async () => {
                    // await getAuthRoutes()
                    await router.push("/dashboard")
                  }, 200)
                  // await router.push(route.query.redirect as RouteLocationRaw || '/')
                }).finally(() => {
              form.loading = false
            })
          })
    }
    return {
      loginLeftPng,
      systemTitle,
      systemSubTitle,
      form,
      passwordType,
      passwordTypeChange,
      submit
    }
  }
})
</script>

<style lang="scss" scoped>
/* Container styles */
.login-container {
  display: flex;
  width: 100vw;
  height: 100vh;
  //background: #fff url('@/assets/login/bg.png') no-repeat center center;
  background-size: cover;
  position: relative;
}

/* Language switch styles */
.language-switch {
  position: absolute;
  top: 20px;
  right: 20px;
  font-size: 24px;
  color: #fff;
  cursor: pointer;
}

.language-switch i:hover {
  color: #2196F3;
}

/* Image section styles */
.login-image {
  flex: 1;
  display: flex;
  justify-content: center;
  flex-direction: column; /* Ensure items are stacked vertically */
  align-items: center;
  background: rgb(255, 255, 255) url('@/assets/login/logo_full.png') no-repeat center center;
  background-size: 40%;
  padding: 20px;
  margin-top: -10%;
}

/* Company name styles in login-image */
.company-name {
  font-size: 47px;
  font-weight: bold;
  color: rgb(225, 14, 14); /* Blue color for company name */
  text-align: center;
  margin-top: 50%; /* Ensure no additional margin */
}

.company-name-2 {
  font-size: 47px;
  font-weight: bold;
  color: rgb(225, 14, 14); /* Blue color for company name */
  text-align: center;
  margin-top: 0;
}

.company-name-1 {
  color: rgb(225, 14, 14); /* Blue color for company name */
  text-align: center;
  margin: 10px 0 0 0; /* Ensure no additional margin */
}

/* Form section styles */
.login-form {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: rgb(255, 255, 255); /* White transparent background to ensure text readability */
  padding: 40px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); /* Adding shadow to form */
  border-radius: 10px; /* Rounded corners for form */
  margin-top: -10%;
}

/* Company info styles inside login-form */
.company-info {
  text-align: center;
  margin-bottom: 20px;
}

.company-logo {
  width: 240px; /* Adjust the size as needed */
}

/* Welcome message styles */
.welcome-message {
  text-align: center;
  margin-bottom: 30px;
  //color: #2196F3; /* Blue color for welcome message */
  font-size: 32px; /* Increased font size for welcome message */
}

/* Form element styles */
.el-form {
  width: 100%;
  max-width: 500px; /* Increased max-width for form */
}

.el-form-item {
  font-size: 20px; /* Increased font size for form labels */
  margin-bottom: 20px; /* Increased margin between form items */
}

.el-form-item label {
  font-size: 20px !important; /* Ensures the font size for labels */
}

.el-input__inner {
  border-radius: 10px; /* More rounded corners for inputs */
  font-size: 20px; /* Increased font size for input text */
  padding: 15px; /* Increased padding for input fields */
}

.el-button {
  width: 100%;
  font-size: 20px; /* Increased font size for button text */
  padding: 15px; /* Increased padding for button */
  border-radius: 10px; /* Rounded corners for button */
}

/* Company address styles */
.company-address {
  position: absolute;
  left: 50%; /* Move to the horizontal center */
  bottom: 20px; /* Distance from the bottom */
  transform: translateX(-50%); /* Center horizontally */
  text-align: center;
  font-size: 18px; /* Font size for company address */
  color: #000000; /* Text color for company address */
  font-weight: bold;
}

/* Responsive styles */
@media screen and (max-width: 1024px) {
  .login-container {
    display: flex;
    width: 100vw;
    height: 100vh;
  }

  .el-button {
    width: 100%;
    font-size: 20px; /* Increased font size for button text */
    padding: 15px; /* Increased padding for button */
    border-radius: 10px; /* Rounded corners for button */
  }

  .login-image {
    /* Image section styles remain the same */
    display: none;
  }

  .company-logo {
    position: relative; /* Di chuyển vị trí của company-logo */
    top: -40px; /* Đẩy lên trên phần login-image */
  }

  .company-name {
    font-size: 24px; /* Decreased font size for smaller screens */
  }

  .login-form {
    padding: 20px; /* Added padding for smaller screens */
  }

  .welcome-message {
    font-size: 28px; /* Decreased font size for smaller screens */
  }

  .el-form {
    max-width: 400px; /* Decreased max-width for smaller screens */
  }

  .company-logo {
    max-width: 180px; /* Adjust the size as needed */
  }

  .el-form-item,
  .el-form-item label,
  .el-input__inner,
  .el-button {
    font-size: 18px; /* Decreased font size for form elements on smaller screens */
  }
  .company-name {
    font-size: 18px; /* Decreased font size for smaller screens */
  }
  .company-name-1 {
    font-size: 18px; /* Decreased font size for smaller screens */
  }
  .company-address {
    width: 70%;
    font-size: 12px; /* Decreased font size for smaller screens */
    //bottom: 50px; /* Adjust bottom position for smaller screens */
  }
}


@media screen and (min-width: 1025px) and (max-width : 1920px) {

  .company-name {
    font-size: 34px;
    text-align: center;
    margin-top: 55% ; /* Ensure no additional margin */
  }

  .company-name-1 {
    text-align: center;
    margin: 30px 0 0 0; /* Ensure no additional margin */
  }

  .company-name-2 {
    font-size: 34px;
  }

  .company-address {
    margin-bottom: 40px;
  }
}


@media screen and (max-height: 470px) {
  .login-container {
    display: flex;
    width: 100vw;
    height: 100vh;
    background: #fff url('@/assets/login/bg.png') no-repeat center;
  }

  .el-button {
    width: 100%;
    //background-color: #2196F3; /* Blue button */
    //border-color: #2196F3;
    font-size: 20px; /* Increased font size for button text */
    padding: 15px; /* Increased padding for button */
    border-radius: 10px; /* Rounded corners for button */
  }

  .login-image {
    /* Image section styles remain the same */
    display: none;
  }

  .company-logo {
    position: relative; /* Di chuyển vị trí của company-logo */
    top: 10px; /* Đẩy lên trên phần login-image */
  }

  .company-name {
    font-size: 24px; /* Decreased font size for smaller screens */
  }

  .login-form {
    padding: 20px; /* Added padding for smaller screens */
  }

  .welcome-message {
    font-size: 28px; /* Decreased font size for smaller screens */
  }

  .el-form {
    max-width: 400px; /* Decreased max-width for smaller screens */
  }

  .company-logo {
    max-width: 100px; /* Adjust the size as needed */
  }

  .el-form-item,
  .el-form-item label,
  .el-input__inner,
  .el-button {
    font-size: 18px; /* Decreased font size for form elements on smaller screens */
  }
  .company-name {
    font-size: 18px; /* Decreased font size for smaller screens */
  }
  .company-name-1 {
    font-size: 18px; /* Decreased font size for smaller screens */
  }
  .company-address {
    display: none;
  }
}

</style>
