<template>
  <el-form
      v-loading="loadingProps"
      :model="systemAttributeForm"
      :rules="rules"
      ref="formRefAtt"
      label-width="auto"
      label-position="top"
  >
    <el-row :gutter="24" style="margin: 15px 0 0">
      <el-col :xs="xspr" :sm="smpr" :md="mdpr">
        <el-form-item  size="default" class="form-item" label="Mã thuộc tính" prop="code" label-width="auto">
          <el-input  placeholder="Nhập giá trị" :disabled="formModeProps === MODE_VIEW() ||  formModeProps === MODE_EDIT()" v-model.trim="systemAttributeForm.code" autocomplete="off" />
        </el-form-item>
      </el-col>

      <el-col :xs="xspr" :sm="smpr" :md="mdpr">
        <el-form-item size="default" class="form-item" label="Tên thuộc tính" prop="name" label-width="auto">
          <el-input  placeholder="Nhập giá trị" :disabled="formModeProps === MODE_VIEW()" v-model="systemAttributeForm.name" autocomplete="off" />
        </el-form-item>
      </el-col>

      <el-col :xs="24" :sm="24" :md="24">
        <el-form-item size="default" class="form-item" label="Mô tả" prop="description" label-width="auto">
          <el-input  placeholder="Nhập giá trị" :disabled="formModeProps === MODE_VIEW()" v-model="systemAttributeForm.description" autocomplete="off" />
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
            v-if="formModeProps === MODE_CREATE()"
            color="var(--system-primary-color)"
            :loading="loadingProps" size="default" type="primary"
            :icon="Plus"
            @click="handleAction"
        >
          Thêm mới
        </el-button>

        <el-button
            v-if="formModeProps === MODE_EDIT()"
            color="var(--system-primary-color)"
            :loading="loadingProps"  size="default" type="primary"
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
  name: "FormSystemAttribute",
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
    systemAttributeData: Object,
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
  emits: ["submitFormSystemAttribute", "closeForDialog"],
  setup(props, { emit }) {

    const formRefAtt = ref();

    const systemAttributeFormDefault = reactive({ // chỉ dùng để reset form
      id: null,
      code: null,
      name: null,
      description: null,
      modelCode: null,
    });

    const systemAttributeForm = reactive({
      id: null,
      code: null,
      name: null,
      description: null,
      modelCode: null,
    });

    /** Validate */
    const rules = {
      code: [
        { required: true, message: "Mã thuộc tính được để trống.", trigger: "blur" },
        { max: 30, message: "Mã thuộc tính tối đa 30 ký tự.", trigger: "blur" },
        {
          pattern: /^[a-zA-Z0-9_.]+$/,
          message: "Mã thuộc tính chỉ được chứa các ký tự a-z, A-Z, 0-9 và _.",
          trigger: "blur",
        },
      ],
      name: [{ required: true, message: "Tên danh mục không được để trống.", trigger: "blur" }],
      description: [
          {max:255, message: "Mô tả tối đa 255 ký tự", trigger: ["blur", "change"]},
      ]
    };

    // Đẩy dữ liệu lên khi thêm sửa
    const handleAction = () => {
      if (!formRefAtt.value) return;
      formRefAtt.value.validate((valid: boolean) => {
        if (valid) {
          if (props.formModeProps === MODE_CREATE) {
            emit("submitFormSystemAttribute", systemAttributeForm);
          } else if (props.formModeProps === MODE_EDIT) {
            emit("submitFormSystemAttribute", systemAttributeForm);
          }
        }
      });
    };


    const resetForm = () => {
     Object.assign(systemAttributeForm, systemAttributeFormDefault);
      setTimeout(() => resetValidation(), 100);
    }

    const resetValidation = () => {
      if (formRefAtt.value) {
        formRefAtt.value.clearValidate();
      }
    };

    watch(
        () => props.systemAttributeData,
        (newValue) => {
          Object.assign(systemAttributeForm, newValue);
        },
        {deep: true, immediate: true}
    );

    watch(
        () => props.systemAttributeData,
        (newValue) => {
          Object.assign(systemAttributeForm, newValue);
        },
        {deep: true}
    );

    const handleCloseForDialog = () => {
      emit("closeForDialog")
    }
    return {
      //Biến
      formRefAtt,
      rules,
      systemAttributeForm,
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