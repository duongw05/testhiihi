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
          <el-form-item :label="$t('message.menu.userManage.gender')" :size="'default'" style="display: inherit">
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
          <el-form-item prop="tel" :label="$t('message.menu.userManage.tel')" :size="'default'">
            <el-input v-model="formData.tel" clearable
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
            <el-input v-model="formData.password" clearable
                      @blur="deepTrim(formData)"/>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :md="12" :lg="12" class="form-item">
          <el-form-item prop="confirmPassword" :label="$t('message.menu.userManage.confirmPassword')" :size="'default'">
            <el-input v-model="formData.confirmPassword" clearable
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
                 @click="onSubmit">
        {{ $t('message.common.add') }}
      </el-button>
    </div>
  </el-drawer>
</template>

<script lang="ts">
import {defineComponent, reactive, ref, watch} from "vue";
import {ElMessageBox, FormInstance} from 'element-plus'
import {deepTrim} from "@/utils/mixins/mixin";
import {useI18n} from "vue-i18n";
import {CircleCloseFilled, CirclePlus, Close} from "@element-plus/icons";
import {useRouter} from "vue-router";

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
      username: '',
      password: '',
      confirmPassword: '',
      gender: '',
      dob: '',
      role: '',
      fullName: '',
      email: '',
      tel: '',
      enabled: true,
    });
    const ruleForm = reactive({})
    const lstGender = ref([
      {name: t('message.menu.userManage.male'), code: '0'},
      {name: t('message.menu.userManage.female'), code: '1'},
      {name: t('message.menu.userManage.other'), code: '2'}
    ])

    const onSubmit = () => {
      loading.value = true
      setTimeout(() => {
        loading.value = false
        visibleValue.value = false
      }, 400)
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
        phone: ''
      }
      Object.assign(formData, data);
    }

    console.log('router.currentRoute.value.params ', router.currentRoute.value.params.data)

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
      closeDialog
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