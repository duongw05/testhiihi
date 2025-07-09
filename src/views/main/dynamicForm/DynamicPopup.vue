<template>
  <el-dialog v-model="visibleShow" width="50%" :before-close="onClose">
    <template #title>
      <h3 class="title-dialog">{{ title }}</h3>
    </template>

    <el-form
        :model="formData"
        :rules="formRules"
        ref="popupForm"
        style="padding: 12px 12px 0 12px"
        label-width="120px"
        label-position="top"
    >
      <el-row :gutter="24">
        <template v-for="(field, index) in config.fields" :key="field.key || index">
          <el-col :span="field.span || 12">
            <el-form-item
                size="default"
                class="form-item"
                :label="field.label"
                :prop="field.key"
            >
              <el-input
                  v-if="field.type === 'text'"
                  v-model="formData[field.key]"
                  :placeholder="field.placeholder"
                  :disabled="field.disabled || isViewMode"
              />

              <el-input
                  v-else-if="field.type === 'textarea' || field.type === 'textArea'"
                  type="textarea"
                  v-model="formData[field.key]"
                  :placeholder="field.placeholder"
                  :disabled="field.disabled || isViewMode"
                  :rows="field.rows || 3"
              />

              <el-select
                  v-else-if="field.type === 'combobox'"
                  v-model="formData[field.key]"
                  :placeholder="field.placeholder"
                  :disabled="field.disabled || isViewMode"
                  style="width: 100%"
              >
                <el-option
                    v-for="option in field.options"
                    :key="option.value"
                    :label="option.label"
                    :value="option.value"
                />
              </el-select>

              <el-date-picker
                  v-else-if="field.type === 'date'"
                  v-model="formData[field.key]"
                  :type="field.dateType || 'date'"
                  :placeholder="field.placeholder"
                  :format="field.format"
                  :value-format="field.valueFormat"
                  :disabled="field.disabled || isViewMode"
                  style="width: 100%"
              />

            </el-form-item>
          </el-col>
        </template>
      </el-row>
    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button @click="onClose" style="width: 100px" size="default" plain>Quay lại</el-button>
      <el-button
          v-if="!isViewMode"
          type="primary"
          color="var(--system-primary-color)"
          @click="onSubmit"
          style="width: 100px"
          size="default"
      >
        {{ formData['id'] ? 'Cập nhật' : 'Thêm mới' }}
      </el-button>
    </div>
  </el-dialog>
</template>

<script lang="ts">
import { defineComponent, reactive, ref, watch } from "vue";
// import { Plus } from "@element-plus/icons-vue"; // Không cần Plus nếu bỏ Upload

export default defineComponent({
  name: "DynamicPopup",
  props: {
    visible: { type: Boolean, required: true },
    config: { type: Object, required: true },
    title: { type: String, default: "Popup" },
    mode: { type: String, default: "add" }, // "add", "edit", "view"
    initialData: { type: Object, default: () => ({}) },
    onSave: { type: Function, required: true },
  },
  setup(props, { emit }) {
    const visibleShow = ref(props.visible);
    const formData = reactive<Record<string, any>>({});
    const formRules = reactive<Record<string, any>>({});
    const popupForm = ref<any>(null);
    const isViewMode = ref(false);

    const resetFormAndRules = () => {
      if (popupForm.value) popupForm.value.clearValidate();

      // Clear all existing keys in formData
      Object.keys(formData).forEach(key => delete formData[key]);

      props.config.fields.forEach(field => {
        // Set form rules
        if (field.rules) {
          formRules[field.key] = field.rules;
        } else {
          delete formRules[field.key];
        }

        // Set default values based on mode and field type
        if (props.mode === "add") {
          // Chỉ giữ lại các loại text, textarea, combobox, date
          if (["text", "textarea", "textArea"].includes(field.type)) {
            formData[field.key] = field.defaultValue || "";
          } else if (["combobox", "date"].includes(field.type)) {
            formData[field.key] = field.defaultValue ?? null;
          } else {
            // Đối với các loại không được hỗ trợ (radio, checkbox, upload), đặt null hoặc undefined
            formData[field.key] = null;
          }
        }
      });

      // Populate formData with initialData if in edit/view mode
      if (props.mode !== "add") {
        Object.assign(formData, props.initialData);
        // Ensure all fields from config are present, even if initialData doesn't have them
        props.config.fields.forEach((field: any) => {
          if (!(field.key in formData)) {
            if (["text", "textarea", "textArea"].includes(field.type)) {
              formData[field.key] = field.defaultValue || '';
            } else if (["combobox", "date"].includes(field.type)) {
              formData[field.key] = field.defaultValue ?? null;
            } else {
              formData[field.key] = null;
            }
          }
        });
      }
    };

    watch(() => props.visible, (newVal) => {
      visibleShow.value = newVal;
      isViewMode.value = props.mode === "view";
      if (newVal) {
        resetFormAndRules();
      } else {
        // Reset form when dialog is closed to ensure clean state for next open
        resetFormAndRules();
      }
    }, { immediate: true });

    const onClose = () => {
      resetFormAndRules();
      emit("close");
    };

    const onSubmit = () => {
      popupForm.value.validate((valid: boolean) => {
        if (valid) {
          const dataToSave = { ...formData };
          // Logic cho upload file (nếu có) đã bị loại bỏ
          props.onSave(dataToSave);
          onClose();
        }
      });
    };

    return {
      formData,
      formRules,
      popupForm,
      isViewMode,
      onClose,
      onSubmit,
      visibleShow,
      // Plus, // Không cần Plus nữa
    };
  }
});
</script>

<style scoped>
.dialog-footer {
  text-align: right;
  padding: 10px 12px 12px 12px;
  border-top: 1px solid #ebeef5;
}

.title-dialog {
  font-size: 18px;
  line-height: 24px;
  color: #303133;
  margin-bottom: 5px;
  margin-top: 5px;
  text-align: left;
}

:deep(.el-dialog__header) {
  background-color: var(--system-primary-color, #409EFF);
  color: white;
  padding: 15px 20px;
}

:deep(.el-dialog__headerbtn .el-dialog__close) {
  color: white;
}

.el-form-item :deep(.el-form-item__label) {
  text-align: left;
  padding-bottom: 4px;
  line-height: 22px;
  white-space: normal;
  justify-content: flex-start;
}

.el-form-item :deep(.el-form-item__content) {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.el-form-item {
  margin-bottom: 18px !important;
}

:deep(.el-dialog__body) {
  padding: 12px 12px 0px 12px;
}

.el-form-item :deep(.el-input__inner),
.el-form-item :deep(.el-select__wrapper),
.el-form-item :deep(.el-date-editor .el-input__wrapper),
.el-form-item :deep(.el-textarea__inner) {
  height: 36px;
  line-height: 36px;
  padding: 0 12px;
  font-size: 14px;
  box-sizing: border-box;
}

.el-form-item :deep(.el-textarea__inner) {
  height: auto !important;
  min-height: 70px !important;
  padding: 8px 12px;
  line-height: 1.5;
}
</style>