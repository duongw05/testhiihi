<template>
  <el-dialog v-model="visibleValue" width="50%">
    <template #title>
      <h3 class="title-dialog">
        Đổi mật khẩu
      </h3>
    </template>
    <el-form :model="form" :rules="rules" ref="ruleForm" label-width="150px" label-position="top"
             style="padding: 12px;">
      <el-row :gutter="24">
        <el-col :span="12">
          <el-form-item size="default" label="Tên đăng nhập" prop="username">
            <el-input v-model="form.username" :disabled="true"></el-input>
          </el-form-item>

          <el-form-item size="default" label="Mật khẩu mới" prop="newPassword">
            <el-input v-model="form.newPassword" show-password maxlength="31"
                      placeholder="Nhập giá trị"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item size="default" label="Mật khẩu cũ" prop="oldPassword">
            <el-input v-model="form.oldPassword" show-password maxlength="31"
                      placeholder="Nhập giá trị"></el-input>
          </el-form-item>
          <el-form-item size="default" label="Xác nhận mật khẩu" prop="passwordConfirm">
            <el-input type="password" v-model="form.passwordConfirm" maxlength="31"
                      show-password placeholder="Nhập giá trị"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button size="default" type="primary" :icon="CircleCloseFilled"
                 style="min-width: 100px" color="var(--system-primary-color)"
                 @click="onClose">Đóng
      </el-button>
      <el-button type="primary" style="min-width: 100px"
                 size="default"
                 :icon="Edit"
                 color="var(--system-primary-color)"
                 @click="submit">
        {{ $t('message.common.update') }}
      </el-button>
    </div>
  </el-dialog>
</template>

<script lang="ts">
import type {LayerType} from '@/components/layer/index.vue'
import {Ref, watch} from 'vue'
import {defineComponent, ref} from 'vue'
import {ElMessage, FormInstance} from 'element-plus'
import {useStore} from 'vuex'
import {passwordChange} from '@/api/user'
import Layer from '@/components/layer/index.vue'
import {CircleCloseFilled, CirclePlus, Edit} from "@element-plus/icons";

export default defineComponent({
  computed: {
    Edit() {
      return Edit
    },
    CircleCloseFilled() {
      return CircleCloseFilled
    }
  },
  components: {
    Layer
  },
  props: {
    visible: {
      type: Boolean,
      default: true
    }
  },
  setup(props, {emit}) {
    const visibleValue = ref<boolean>(props.visible)
    const ruleForm: Ref<FormInstance | null> = ref(null)
    const layerDom: Ref<LayerType | null> = ref(null)
    const store = useStore()
    let form = ref({
      id: store.state.user.info.id,
      username: store.state.user.info.username,
      oldPassword: '',
      newPassword: '',
      passwordConfirm: ''
    })
    const rules = {
      oldPassword: [{required: true, message: 'Vui lòng nhập Mật khẩu cũ', trigger: 'blur'},
        {min: 6, max: 30, message: 'Mật khẩu cũ từ 6 đến 30 ký tự', trigger: 'blur'}],
      newPassword: [{required: true, message: 'Vui lòng nhập Mật khẩu mới', trigger: 'blur'},
        {min: 6, max: 30, message: 'Mật khẩu mới từ 6 đến 30 ký tự', trigger: 'blur'}],
      passwordConfirm: [{required: true, message: 'Vui lòng nhập Xác nhận mật khẩu', trigger: 'blur'},
        {min: 6, max: 30, message: 'Xác nhận mật khẩu từ 6 đến 30 ký tự', trigger: 'blur'},
        {validator: (rule: any, value: any, callback: any) => validateConfirmPass(rule, value, callback)}],
    }

    const validateConfirmPass = (rule: any, value: any, callback: any) => {
      if (value) {
        if (form.value.newPassword !== value) {
          callback('Mật khẩu xác nhận không trùng khớp')
        } else {
          callback()
        }
      }
      callback()
    }

    const submit = () => {
      if (ruleForm.value) {
        ruleForm.value.validate((valid) => {
          if (valid) {
            let params = {
              id: form.value.id,
              passwordOld: form.value.oldPassword,
              password: form.value.newPassword
            }
            passwordChange(params)
                .then(res => {
                  ElMessage({
                    type: 'success',
                    message: 'Đổi mật khẩu thành công!'
                  })
                  layerDom.value && layerDom.value.close()
                  setTimeout(() => {
                    store.dispatch('user/loginOut')
                  }, 2000)
                })
          } else {
            return false;
          }
        });
      }
    }

    const onClose = () => {
      visibleValue.value = false;
      emit("close");
    };

    watch(() => props.visible, () => {
      if (props.visible) {
        visibleValue.value = true
      }
    })

    return {
      visibleValue,
      form,
      rules,
      layerDom,
      ruleForm,
      submit,
      onClose
    }
  }
})
</script>

<style lang="scss" scoped>
.title-dialog {
  font-size: 18px;
  line-height: 24px;
  color: #000000;
  margin-bottom: 5px;
  margin-top: 5px;
}

.dialog-footer {
  text-align: center;
}

</style>
