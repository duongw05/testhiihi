<template>
  <el-drawer
      v-model="visibleValue"
      :title="title"
      size="40%"
      :show-close="false"
      @close="closeDialog(formRef)"
  >
    <template #header>
      <h2>{{ title }}</h2>
      <div style="text-align: right;">
        <div class="button-container">
          <el-button @click="closeDialog(formRef)" :icon="CircleCloseFilled" size="default" style="min-width: 100px"
                     plain> {{ $t('message.common.close') }}
          </el-button>
          <el-button type="primary" style="min-width: 100px" :loading="loading"
                     size="default"
                     :icon="CirclePlus"
                     color="var(--system-primary-color)"
                     @click="onSubmit(formRef)">
            {{ $t('message.common.add') }}
          </el-button>
        </div>
      </div>
    </template>
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
          <el-form-item prop="code" :label="$t('message.menu.groupManage.groupCode')" :size="'default'">
            <el-input :disabled="typeAction !== 'add'" v-model="formData.code" clearable
                      @blur="deepTrim(formData)"/>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :md="12" :lg="12" class="form-item">
          <el-form-item prop="name" :label="$t('message.menu.groupManage.groupName')" :size="'default'">
            <el-input v-model="formData.name" clearable
                      @blur="deepTrim(formData)"/>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :md="24" :lg="24" class="form-item">
          <el-form-item prop="description" :label="$t('message.menu.groupManage.description')" :size="'default'">
            <el-input type="textarea" v-model="formData.description" clearable autocomplete="off"
                      @blur="deepTrim(formData)"/>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </el-drawer>
</template>

<script lang="ts">
import {defineComponent, reactive, ref, watch} from "vue";
import {ElMessageBox, ElNotification, FormInstance} from 'element-plus'
import {deepTrim, handleSuccess, handleErr} from "@/utils/mixins/mixin";
import {useI18n} from "vue-i18n";
import {CircleCloseFilled, CirclePlus, Close} from "@element-plus/icons";
import {useRouter} from "vue-router";
import {updateGroup, addGroup} from "@/api/roleManage/groupUser";


export default defineComponent({
  components: {},
  props: {
    visible: Boolean,
    titleDrawer: {
      type: String,
      default: ''
    },
    initialData: {
      type: Object,
      default: () => ({}),
    },
    mode: {
      type: String,
      default: "add", // "add", "edit", "view"
    },
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
    let typeAction: any = ref(false);
    const title = ref('')
    const loading = ref<boolean>(false)
    const formRef = ref<FormInstance>();
    const formData = reactive({
      id: null,
      description: '',
      code: '',
      name: '',
    });
    const pagination = reactive({
      current: 1,
      total: 0,
      pageSize: 10,
      size: 10,
    });
    const ruleForm = reactive({
      code: [
        {
          required: true,
          message: t('validate.required', {name: t('message.menu.groupManage.groupCode')}),
          trigger: 'blur'
        },
        {
          min: 1,
          max: 50,
          message: t('validate.max', {name: t('message.menu.groupManage.groupCode'), value: 50}),
          trigger: 'blur'
        }
      ],
      name: [
        {
          required: true,
          message: t('validate.required', {name: t('message.menu.groupManage.groupName')}),
          trigger: 'blur'
        },
        {
          min: 1,
          max: 100,
          message: t('validate.max', {name: t('message.menu.groupManage.groupName'), value: 100}),
          trigger: 'blur'
        }
      ],
      description: [
        {
          max: 1000,
          message: t('validate.max', {name: t('message.menu.groupManage.description'), value: 1000}),
          trigger: 'blur'
        }
      ]
    })

    const onSubmit = async (formEl: FormInstance | undefined) => {
      if (!formEl) return
      await formEl.validate(async (valid) => {
        if (valid) {
          try {
            loading.value = true
            if (formData.id) {
              await updateGroup(formData)
              handleSuccess(t, t('message.common.updateSuccess', {name: t('message.menu.groupManage.groupRole')}));
            } else {
              await addGroup(formData)
              handleSuccess(t, t('message.common.addSuccess', {name: t('message.menu.groupManage.groupRole')}));
            }
            emit("success");
            loading.value = false
            visibleValue.value = false
          } catch (e) {
            console.log(e)
            handleErr(t, t('message.common.errServer'));
            loading.value = false
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
        description: '',
        code: '',
        name: '',
      }
      Object.assign(formData, data);
    }

    console.log('router.currentRoute.value.params ', router.currentRoute.value.params.data)

    watch(() => props.visible, async () => {
      if (props.visible) {
        visibleValue.value = true
        title.value = props.titleDrawer
      }
      if (props.mode) {
        typeAction.value = props.mode
      }
      if (props.mode !== 'add') {
        Object.assign(formData, props.initialData);
      }
    })

    return {
      formData,
      pagination,
      ruleForm,
      formRef,
      visibleValue,
      typeAction,
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