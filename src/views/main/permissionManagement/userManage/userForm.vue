<template>
  <el-drawer
      v-model="visibleValue"
      :title="title"
      size="40%"
      @close="closeDialog(formRef)"
  >
    <el-form
        ref="formRef"
        style="width: 100%"
        :model="formData"
        :rules="ruleForm"
        status-icon
        label-position="top"
    >
      <el-row :gutter="24" style="margin: 0">
        <el-col :xs="24" :md="12" :lg="12" class="form-item">
          <el-form-item prop="fullName" :label="$t('message.menu.userManage.fullName')" :size="'default'">
            <el-input v-model="formData.fullName" clearable
                      @blur="deepTrim(formData)"/>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :md="12" :lg="12" class="form-item">
          <el-form-item prop="gender" :label="$t('message.menu.userManage.gender')" :size="'default'"
                        style="display: inherit">
            <el-tooltip :content="$t('message.menu.userManage.gender')" placement="top">
              <el-select v-model="formData.gender"
                         :placeholder="$t('message.common.chooseValue')" style="width: 100%"
                         filterable clearable
                         reserve-keyword>
                <el-option
                    v-for="item in lstGender"
                    :key="item?.code"
                    :label="item.name"
                    :value="item?.code"
                />
              </el-select>
            </el-tooltip>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :md="12" :lg="12" class="form-item">
          <el-form-item prop="phone" :label="$t('message.menu.userManage.phone')" :size="'default'">
            <el-input v-model="formData.phone" clearable
                      @blur="deepTrim(formData)"/>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :md="12" :lg="12" class="form-item">
          <el-form-item prop="email" :label="$t('message.menu.userManage.email')" :size="'default'">
            <el-input v-model="formData.email" clearable
                      @blur="deepTrim(formData)"/>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :md="12" :lg="12" class="form-item">
          <el-form-item :size="'default'" :label="$t('message.menu.userManage.dob')" prop="dob"
                        style="display: inherit">
            <el-date-picker
                v-model="formData.dob"
                type="date"
                size="default"
                format="DD/MM/YYYY"
                value-format="YYYY-MM-DD"
                :placeholder="$t('message.common.chooseValue')"
            />
          </el-form-item>
        </el-col>

        <el-col :xs="24" :md="12" :lg="12" class="form-item">
          <el-form-item prop="username" :label="$t('message.menu.userManage.username')" :size="'default'">
            <el-input v-model="formData.username" clearable
                      @blur="deepTrim(formData)"/>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :md="12" :lg="12" class="form-item">
          <el-form-item prop="password" :label="$t('message.menu.userManage.password')" :size="'default'">
            <el-input v-model="formData.password" clearable show-password
                      @blur="deepTrim(formData)"/>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :md="12" :lg="12" class="form-item">
          <el-form-item prop="confirmPassword" :label="$t('message.menu.userManage.confirmPassword')" :size="'default'">
            <el-input v-model="formData.confirmPassword" clearable show-password
                      @blur="deepTrim(formData)"/>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div class="button-container">
      <el-button @click="closeDialog(formRef)" size="default" type="primary" :icon="CircleCloseFilled"
                 style="min-width: 100px" color="var(--system-primary-color)">Đóng
      </el-button>
      <el-button type="primary" style="min-width: 100px" :loading="loading"
                 size="default"
                 :icon="CirclePlus"
                 color="var(--system-primary-color)"
                 @click="onSubmit(formRef)">
        {{ $t('message.common.add') }}
      </el-button>
    </div>
  </el-drawer>
</template>

<script lang="ts">
import {defineComponent, reactive, ref, watch} from "vue";
import {ElMessage, ElMessageBox, FormInstance} from 'element-plus'
import {deepTrim} from "@/utils/mixins/mixin";
import {useI18n} from "vue-i18n";
import {CircleCloseFilled, CirclePlus, Close} from "@element-plus/icons";
import {useRouter} from "vue-router";
import {createUser} from "@/api/user";
import {validateEmailInvalid} from "@/utils/validate/helpers";

export default defineComponent({
  components: {},
  props: {
    visible: Boolean,
    titleDrawer: {
      type: String,
      default: ''
    }
  },
  methods: {deepTrim},
  computed: {
    CirclePlus() {
      return CirclePlus
    },
    CircleCloseFilled() {
      return CircleCloseFilled
    },
    Close() {
      return Close
    }
  },
  setup(props, {emit}) {
    const router = useRouter();
    const {t} = useI18n();
    let visibleValue: any = ref(false);
    const title = ref('')
    const loading = ref<boolean>(false)
    const formRef = ref<FormInstance>();
    const formData = reactive({
      id: null,
      username: null,
      password: null,
      confirmPassword: null,
      gender: null,
      dob: null,
      fullName: null,
      email: null,
      phone: null,
      enabled: true,
      avatar: null as Blob | null,
      avatarUrl: ''
    });
    const ruleForm = reactive({
      fullName: [{required: true, message: 'Vui lòng nhập Tên người dùng ', trigger: 'blur'}],
      gender: [{required: true, message: 'Vui lòng chọn Giới tính ', trigger: 'blur'}],
      phone: [
        {required: true, message: 'Vui lòng nhập Số điện thoại ', trigger: 'blur'},
        {min: 10, max: 11, message: 'Số điện thoại tối đa 10-11 ký tự', trigger: ['blur', 'change']},
        {
          validator: (rule: any, value: any, callback: any) => {
            if (!value) {
              callback()
            }
            const phonePattern = /^0\d{9,10}$/;
            const containsLetters = /[a-zA-Z]/;

            if (containsLetters.test(value)) {
              callback(new Error('Số điện thoại chỉ cho phép nhập số'));
            } else if (!phonePattern.test(value)) {
              callback(new Error('Số điện thoại phải bắt đầu bằng 0'));
            } else {
              callback();
            }
          },
          trigger: ['blur', 'change']
        }
      ],
      email: [
        {required: true, message: 'Vui lòng nhập Email', trigger: 'blur'},
        {min: 0, max: 100, message: 'Email tối đa 100 ký tự', trigger: ['blur', 'change']},
        {validator: (rule: any, value: any, callback: any) => validateEmailInvalid(rule, value, callback, t, 'Email')}
      ],
      dob: [{required: true, message: 'Vui lòng chọn Ngày sinh ', trigger: 'blur'}],
      username: [
        {required: true, message: 'Vui lòng nhập Tên đăng nhập', trigger: 'blur'},
        {max: 50, message: 'Tên đăng nhập tối đa 50 ký tự', trigger: ['blur', 'change']},
        {
          pattern: /^[a-zA-Z0-9]+$/,
          message: 'Tên đăng nhập chỉ được phép chứa chữ không dấu và số',
          trigger: ['blur', 'change']
        }
      ],
      password: [{required: true, message: 'Vui lòng nhập Mật khẩu', trigger: 'blur'}],
      confirmPassword: [
        {required: true, message: 'Vui lòng nhập Xác nhận mật khẩu', trigger: 'blur'},
        {validator: (rule: any, value: any, callback: any) => validateConfirmPassword(rule, value, callback)}
      ],
    })
    const lstGender = ref([
      {name: t('message.menu.userManage.male'), code: 0},
      {name: t('message.menu.userManage.female'), code: 1},
      {name: t('message.menu.userManage.other'), code: 2}
    ])

    const validateConfirmPassword = (rule: any, value: any, callback: any) => {
      const password = formData.password; // Tìm giá trị của trường password từ form
      if (value !== password) {
        callback(new Error('Mật khẩu xác nhận không khớp'));
      } else {
        callback(); // Xác nhận thành công
      }
    };

    const onSubmit = async (formEl: FormInstance | undefined) => {
      if (!formEl) return
      await formEl.validate(async (valid) => {
        if (valid) {
          try {
            loading.value = true
            await createUser(formData)
            loading.value = false
            visibleValue.value = false
            ElMessage({
              type: 'success',
              message: 'Thêm người dùng thành công!'
            })
          } catch (e) {
            console.log(e)
            loading.value = false
            ElMessage({
              type: 'error',
              message: 'Thêm người dùng thất bại!'
            })
          }
        }
      })
    }

    const closeDialog = (formEl: FormInstance | undefined) => {
      setTimeout(() => {
        visibleValue.value = false;
      }, 100)
      resetForm(formEl)
      emit('close');
    }

    const resetForm = (formEl: FormInstance | undefined) => {
      if (!formEl) return
      formEl.resetFields()
      let data = {
        id: null,
        username: '',
        password: '',
        gender: '',
        role: '',
        fullName: '',
        email: '',
        phone: '',
        avatar: '',
        avatarUrl: ''
      }
      Object.assign(formData, data);
    }

    const beforeAvatarUpload = (file: { type: string; size: number }) => {
      console.log('file.type ', file.type)
      const isJPG = file.type === 'image/jpeg';
      const isPNG = file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG && !isPNG) {
        ElMessage.error('Ảnh đại diện phải là JPG hoặc PNG!');
        return false
      }
      if (!isLt2M) {
        ElMessage.error('Ảnh đại diện không được lớn hơn 2MB!');
        return false
      }
      return true
    }

    const handleAvatarChange = (file: { type: string; size: number; raw: Blob | MediaSource }) => {
      if (beforeAvatarUpload(file)) {
        formData.avatar = file.raw as Blob
        formData.avatarUrl = URL.createObjectURL(file.raw)
      }
    }

    watch(() => props.visible, async () => {
      if (props.visible) {
        visibleValue.value = true
        title.value = props.titleDrawer
      }
    })

    return {
      lstGender,
      formData,
      ruleForm,
      formRef,
      visibleValue,
      title,
      loading,
      onSubmit,
      closeDialog,
      handleAvatarChange,
      beforeAvatarUpload
    }
  }
})
</script>

<style scoped>
.button-container {
  display: flex;
  justify-content: center;
  margin-top: 10px;
  margin-bottom: 6px;
}
</style>