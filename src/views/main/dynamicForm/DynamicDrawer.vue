<template>
  <el-drawer
      v-model="visibleShow"
      :title="title"
      size="40%"
      :show-close="false"
  >
    <template #header>
      <h2 class="title-dialog">{{ title }}</h2>
      <el-button @click="onClose" style="width: 100px" size="default" :icon="CircleCloseFilled" plain>
        {{ $t('message.common.close') }}
      </el-button>
      <el-button
          type="primary"
          v-if="!isViewMode"
          color="var(--system-primary-color)"
          @click="onSubmit"
          :icon="CirclePlus"
          style="width: 100px"
          size="default"
      >
        {{ formData['id'] ? $t('message.common.update') : $t('message.common.add') }}
      </el-button>
    </template>

    <el-form
        :model="formData"
        :rules="formRules"
        ref="popupForm"
        style="padding: 12px"
        label-width="120px"
        label-position="top"
    >
      <el-row :gutter="24">
        <template v-for="(field, index) in config.fields" :key="field.key || index">
          <el-col :span="field.type === 'textArea' ? 24 : field.span || 12">
            <el-form-item :label="field.label" :prop="field.key" class="form-item">
              <!-- Text -->
              <template v-if="field.type === 'text'">
                <el-input
                    v-model="formData[field.key]"
                    :placeholder="field.placeholder"
                    :disabled="field.disabled || isViewMode"
                />
              </template>

              <!-- Text Area -->
              <template v-else-if="field.type === 'textArea'">
                <el-input
                    type="textarea"
                    v-model="formData[field.key]"
                    :placeholder="field.placeholder"
                    :disabled="field.disabled || isViewMode"
                    :rows="field.rows || 4"
                />
              </template>

              <!-- Combobox -->
              <template v-else-if="field.type === 'combobox'">
                <el-select
                    v-model="formData[field.key]"
                    :placeholder="field.placeholder"
                    :disabled="field.disabled || isViewMode"
                >
                  <el-option
                      v-for="option in field.options"
                      :key="option.value"
                      :label="option.label"
                      :value="option.value"
                  />
                </el-select>
              </template>

              <!-- Date -->
              <template v-else-if="field.type === 'date'">
                <el-date-picker
                    v-model="formData[field.key]"
                    type="year"
                    :placeholder="field.placeholder"
                    :format="field.format"
                    :disabled="field.disabled || isViewMode"
                    :value-format="field.valueFormat"
                    :clearable="true"
                    popper-class="custom-date-picker-popper"
                />
              </template>

              <!-- Date Range -->
              <template v-else-if="field.type === 'dateRange'">
                <el-date-picker
                    v-model="formData[field.key]"
                    type="daterange"
                    :range-separator="field.rangeSeparator"
                    :start-placeholder="field.placeholderStart"
                    :end-placeholder="field.placeholderEnd"
                    :format="field.format"
                    :value-format="field.valueFormat"
                    :clearable="true"
                    placement="bottom-start"
                />
              </template>

              <!-- Radio -->
              <template v-else-if="field.type === 'radio'">
                <el-radio-group
                    v-model="formData[field.key]"
                    :disabled="field.disabled || isViewMode"
                >
                  <el-radio
                      v-for="option in field.options"
                      :key="option.value"
                      :label="option.value"
                  >
                    {{ option.label }}
                  </el-radio>
                </el-radio-group>
              </template>

              <!-- Checkbox -->
              <template v-else-if="field.type === 'checkbox'">
                <el-checkbox-group
                    v-model="formData[field.key]"
                    :disabled="field.disabled || isViewMode"
                >
                  <el-checkbox
                      v-for="option in field.options"
                      :key="option.value"
                      :label="option.value"
                  >
                    {{ option.label }}
                  </el-checkbox>
                </el-checkbox-group>
              </template>

              <template v-else-if="field.type === 'upload'">
                <el-upload
                    :limit="field.limit || 1"
                    :file-list="formData[field.key] || []"
                    :auto-upload="false"
                    :on-change="(file, fileList) => handleFileChange(file, fileList, field.key)"
                    :on-remove="(file, fileList) => handleRemoveFile(file, fileList, field.key)"
                    :show-file-list="true"
                    list-type="picture"
                >
                  <el-button type="primary" size="default" :disabled="isViewMode">
                    {{ field.buttonText || 'Chọn ảnh' }}
                  </el-button>
                </el-upload>
              </template>

            </el-form-item>
          </el-col>
        </template>
      </el-row>
    </el-form>
  </el-drawer>
</template>

<script lang="ts">
import { defineComponent, reactive, ref, watch, nextTick } from 'vue';
import { FormInstance } from 'element-plus';
import { CircleCloseFilled, CirclePlus } from '@element-plus/icons-vue';
import { useI18n } from 'vue-i18n';

export default defineComponent({
  name: 'DynamicPopup',
  props: {
    visible: { type: Boolean, required: true },
    config: { type: Object, required: true },
    title: { type: String, default: 'Popup' },
    mode: { type: String, default: 'add' }, // "add", "edit", "view"
    initialData: { type: Object, default: () => ({}) },
    onSave: { type: Function, required: true }
  },
  setup(props, { emit }) {
    const { t } = useI18n();
    const visibleShow = ref(props.visible);
    const formData = reactive<Record<string, any>>({});
    const formRules = reactive<Record<string, any[]>>({});
    const popupForm = ref<FormInstance | null>(null);
    const isViewMode = ref(false);

    const resetForm = () => {
      Object.keys(formData).forEach((key) => {
        formData[key] = '';
      });
    };

    watch(() => props.visible, async (newVal) => {
      visibleShow.value = newVal;
      isViewMode.value = props.mode === 'view';

      props.config.fields.forEach((field: any) => {
        if (field.rules) {
          formRules[field.key] = field.rules;
        }
      });

      if (newVal) {
        resetForm();

        props.config.fields.forEach((field: any) => {
          if (field.defaultValue !== undefined) {
            formData[field.key] = field.defaultValue;
          } else if (field.type === 'checkbox' || field.type === 'upload') {
            formData[field.key] = [];
          }
        });

        if (props.mode !== 'add') {
          Object.assign(formData, props.initialData);
        }

        await nextTick();
        popupForm.value?.clearValidate();
      }
    });

    const onClose = () => {
      popupForm.value?.clearValidate();
      emit('close');
    };

    const onSubmit = () => {
      popupForm.value?.validate((valid: boolean) => {
        if (valid) {
          props.onSave(formData);
          setTimeout(() => {
            onClose();
          }, 100);
        }
      });
    };

    const handleUploadSuccess = (response: any, file: any, key: string) => {
      if (!formData[key]) formData[key] = [];
      formData[key].push(file);
    };

    return {
      formData,
      formRules,
      popupForm,
      isViewMode,
      onClose,
      onSubmit,
      visibleShow,
      CirclePlus,
      CircleCloseFilled,
      handleUploadSuccess
    };
  }
});
</script>

<style scoped>
.dialog-footer {
  text-align: right;
}

.el-select--default {
  width: 100%;
}

.title-dialog {
  font-size: 22px;
  line-height: 30px;
  color: #3d3d3d;
  margin: 10px 0;
  text-align: left;
  font-weight: bold;
}

/* Tăng kích thước chữ chung */
.el-form-item__label,
.el-input__inner,
.el-select,
.el-button,
.el-textarea__inner {
  font-size: 16px !important;
}

/* Tăng padding giữa các item */
.el-form-item {
  margin-bottom: 20px;
}

/* Upload text */
.el-upload__text {
  font-size: 16px;
}
</style>
