<template>
  <el-form
      v-loading="loadingProps"
      :model="systemModelForm"
      :rules="rules"
      ref="formSysModelsRef"
      label-width="auto"
      label-position="top"
  >
    <el-row :gutter="24" style="margin: 15px 0 0">
      <el-col :xs="xspr" :sm="smpr" :md="mdpr">
        <el-form-item size="default" class="form-item" label="Mã danh mục" prop="code" label-width="auto">
          <el-input placeholder="Nhập giá trị" :disabled="formModeProps === MODE_VIEW() || formModeProps === MODE_EDIT()" v-model.trim="systemModelForm.code"
                    autocomplete="off"/>
        </el-form-item>
      </el-col>

      <el-col :xs="xspr" :sm="smpr" :md="mdpr">
        <el-form-item size="default" class="form-item" label="Tên danh mục" prop="name" label-width="auto">
          <el-input placeholder="Nhập giá trị" :disabled="formModeProps === MODE_VIEW()" v-model="systemModelForm.name"
                    autocomplete="off"/>
        </el-form-item>
      </el-col>

      <el-col :xs="24" :sm="24" :md="24">
        <el-form-item size="default" class="form-item" label="Mô tả" prop="description" label-width="auto">
          <el-input placeholder="Nhập giá trị" :disabled="formModeProps === MODE_VIEW()"
                    v-model="systemModelForm.description" autocomplete="off"/>
        </el-form-item>
      </el-col>
    </el-row>

    <!--  Nút hành động-->
    <div class="button-container">
      <el-row :gutter="24" style="margin: 15px 0 0">
        <slot name="formButtons"></slot>
        <el-button
            size="default"
            :icon="Close"
            @click="handleCloseForDialog"
        >
          Đóng
        </el-button>
        <el-button
            v-if="formModeProps === MODE_CREATE()"
            color="var(--system-primary-color)"
            size="default" type="primary"
            :icon="Plus"
            @click="handleAction"
        >
          Thêm mới
        </el-button>

        <el-button
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
  name: "FormSystemModel",
  computed: {
    Edit() {
      return Edit
    },
    Close() {
      return Close
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
    systemModelData: Object,
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
  emits: ["submitFormSystemModel", "closeForDialog"],
  setup(props, {emit}) {
    const formSysModelsRef = ref();
    const systemModelFormDefault = reactive({ // CHỉ dùng để reset form
      id: null,
      code: null,
      name: null,
      description: null,
    });

    const systemModelForm = reactive({
      id: null,
      code: null,
      name: null,
      description: null,
    });

    /** Validate */
    const rules = {
      code: [
        {required: true, message: "Mã danh mục không được để trống.", trigger: "blur"},
        {max: 30, message: "Mã danh mục tối đa 30 ký tự.", trigger: ["change", "blur"]},
        {
          pattern: /^[a-zA-Z0-9_.]+$/,
          message: "Mã danh mục chỉ được chứa các ký tự a-z, A-Z, 0-9 và _.",
          trigger: "blur",
        },
        {max: 30, message: "Mã danh mục tối đa 30 ký tự", trigger: ["blur", "change"]},
      ],
      name: [
        {required: true, message: "Tên danh mục không được để trống.", trigger: "blur"},
        {max: 150, message: "Tên danh mục tối đa 150 ký tự", trigger: ["blur", "change"]},

      ],
      description: [
        {max: 255, message: "Mô tả tối đa 255 ký tự", trigger: ["blur", "change"]},
      ]
    };

    // Đẩy dữ liệu lên khi thêm sửa
    const handleAction = () => {
      if (!formSysModelsRef.value) return;
      formSysModelsRef.value.validate((valid: boolean) => {
        if (valid) {
          if (props.formModeProps === MODE_CREATE) {
            emit("submitFormSystemModel", systemModelForm);
          } else if (props.formModeProps === MODE_EDIT) {
            emit("submitFormSystemModel", systemModelForm);
          }
        }
      });
    };

    const handleCloseForDialog = () => {
      emit("closeForDialog");
      resetForm();
    }


    const resetValidation = () => {
      if (formSysModelsRef.value) {
        formSysModelsRef.value.clearValidate();
      }
    };

    const resetForm = () => {
      Object.assign(systemModelForm, systemModelFormDefault);
      setTimeout(() => resetValidation(), 100);
    }

    watch(
        () => props.systemModelData,
        (newValue) => {
          Object.assign(systemModelForm, newValue);
          setTimeout(() => resetValidation(), 100);
        },
        {deep: true}
    );

    watch(
        () => props.formModeProps,
        (newValue) => {
          if(newValue === MODE_CREATE) {
            resetForm();
          }
        },
    );

    return {
      //Biến
      formSysModelsRef,
      rules,
      systemModelForm,
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