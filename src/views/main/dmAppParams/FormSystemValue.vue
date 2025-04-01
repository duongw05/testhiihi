<template>
  <el-form
      v-loading="loadingProps"
      :model="systemValueForm"
      :rules="rules"
      ref="formSysValuesRef"
      label-width="auto"
      label-position="top"
  >

    <el-row :gutter="24" style="margin: 15px 0 0">
      <el-col :xs="xspr" :sm="smpr" :md="mdpr">
        <el-form-item  size="default" class="form-item" label="Mã giá trị" prop="valueCode" label-width="auto">
          <el-input placeholder="Nhập giá trị" :disabled="formModeProps === MODE_VIEW() || formModeProps === MODE_EDIT()" v-model.trim="systemValueForm.valueCode" autocomplete="off" />
        </el-form-item>
      </el-col>

      <el-col :xs="xspr" :sm="smpr" :md="mdpr">
        <el-form-item size="default" class="form-item" label="Nhãn giá trị" prop="valueLabel" label-width="auto">
          <el-input  placeholder="Nhập giá trị" :disabled="formModeProps === MODE_VIEW()" v-model="systemValueForm.valueLabel" autocomplete="off" />
        </el-form-item>
      </el-col>

      <el-col :xs="24" :sm="24" :md="24">
        <el-form-item size="default" class="form-item" label="Mô tả" prop="description" label-width="auto">
          <el-input  placeholder="Nhập giá trị" :disabled="formModeProps === MODE_VIEW()" v-model="systemValueForm.description" autocomplete="off" />
        </el-form-item>
      </el-col>
    </el-row>

    <!--  Nút hành động-->
    <div class="button-container">
      <el-row :gutter="24" style="margin: 15px 0 0">
        <slot name="formButtons"></slot>

        <el-button
            :loading="loadingProps" size="default"
            :icon="Close"
            @click="handleCloseForDialog"
        >
          Đóng
        </el-button>
        <el-button
            :loading="loadingProps"
            v-if="formModeProps === MODE_CREATE()"
            color="var(--system-primary-color)"
            size="default" type="primary"
            :icon="Plus"
            @click="handleAction"
        >
          Thêm mới
        </el-button>

        <el-button
            :loading="loadingProps"
            v-if="formModeProps === MODE_EDIT()"
            color="var(--system-primary-color)"
            size="default" type="primary"
            @click="handleAction"
            :icon="Edit"
        >
          Cập nhật
        </el-button>
      </el-row>
    </div>

  </el-form>
</template>
<script lang="ts">
import {defineComponent, reactive, ref, watch} from "vue";
import {MODE_CREATE, MODE_EDIT, MODE_VIEW} from "@/constants/formMode";
import {Close, Edit, Plus} from "@element-plus/icons";

export default defineComponent({
  name: "FormSystemValue",
  computed: {
    Close() {
      return Close
    },
    Edit() {
      return Edit
    },
    Plus() {
      return Plus
    }
  },
  methods: {
    MODE_EDIT() {
      return MODE_EDIT
    },
    MODE_CREATE() {
      return MODE_CREATE
    },
    MODE_VIEW() {
      return MODE_VIEW
    }
  },
  props: {
    formModeProps: String,
    systemValueData: Object,
    // Nnóm các props hỗ trợ chia cột cho các input tơng ứng với xs sm md lg
    xspr: {
      type: Number,
      required: false,
    },
    smpr: {
      type: Number,
      required: false,
    },
    mdpr: {
      type: Number,
      required: false,
    },
    lgpr: {
      type: Number,
      required: false,
    },
    loadingProps: { // loading đồng bôj theo loading của cha
      type: Boolean,
      required: false
    },
  },
  emits: ["submitFormSystemValue", "closeForDialog"],
  setup(props, { emit }) {
    const formSysValuesRef = ref();
    const defaultSystemValueForm= reactive({ // chỉ dùng để reset form
      id: null,
      valueCode: "",
      attCode: "",
      modelCode: "",
      valueLabel: "",
      description: ""
    });

    const systemValueForm= reactive({
      id: null,
      valueCode: "",
      attCode: "",
      modelCode: "",
      valueLabel: "",
      description: ""
    });

    /** Validate */
    const rules = {
      valueCode: [
        { required: true, message: 'Mã giá trị không được để trống.', trigger: 'blur' },
        { max: 255, message: 'Mã giá trị tối đa 30 ký tự.', trigger: 'blur' },
        {
          pattern: /^[a-zA-Z0-9_.]+$/,
          message: 'ValueCode chỉ được chứa các ký tự a-z, A-Z, 0-9 và _.',
          trigger: 'blur'
        }
      ],
      valueLabel: [
        { required: true, message: 'Nhãn giá trị không được để trống.', trigger: 'blur' },
        { max: 150, message: 'Nhãn giá trị tối đa 150 ký tự.', trigger: 'blur' },
      ],
      description: [
        {max:255, message: "Mô tả tối đa 255 ký tự", trigger: ["blur", "change"]},
      ]
    };

    // Đẩy dữ liệu lên khi thêm sửa
    const handleAction = () => {
      if (!formSysValuesRef.value) return;
      formSysValuesRef.value.validate((valid: boolean) => {
        if (valid) {
          if (props.formModeProps === MODE_CREATE) {
            emit("submitFormSystemValue", systemValueForm);
          } else if (props.formModeProps === MODE_EDIT) {
            emit("submitFormSystemValue", systemValueForm);
          }
        }
      });
    };

    const resetValidation = () => {
      if (formSysValuesRef.value) {
        formSysValuesRef.value.clearValidate();
      }
    };

    const resetForm = () => {
      Object.assign(systemValueForm, defaultSystemValueForm);
      setTimeout(() => resetValidation(), 100);
    }

    watch(
        () => props.systemValueData,
        (newValue) => {
         Object.assign(systemValueForm, newValue);
        },
        {deep: true, immediate: true}
    );

    const handleCloseForDialog = () => {
      emit("closeForDialog");
    }

    return {
      //Biến
      formSysValuesRef,
      rules,
      systemValueForm,
      // Hàm
      handleAction,
      handleCloseForDialog,
      resetForm,
    };
  },
});

</script>
<style scoped lang="scss">
.button-container {
  display: flex;
  justify-content: center; /* Căn giữa theo chiều ngang */
  margin-bottom: 10px;
}
</style>