<template>
  <el-drawer
      v-model="visibleShow"
      :title="title"
      size="40%"
      :show-close="false"
  >
    <template #header>
      <h2 class="title-dialog">{{ title }}</h2>
      <el-button @click="onClose" style="width: 100px" size="default" :icon="CircleCloseFilled"  plain>{{ $t('message.common.close') }}</el-button>
      <el-button type="primary" v-if="!isViewMode" color="var(--system-primary-color)" @click="onSubmit"
                 :icon="CirclePlus" style="width: 100px" size="default">
        {{ formData['id'] ? $t('message.common.update') : $t('message.common.add') }}
      </el-button>
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
        <template v-for="(field, index) in config.fields">
          <el-col :span="field.type === 'textArea' ? 24 : field.span || 12">
            <el-form-item size="default" class="form-item"
                          :key="index"
                          :label="field.label"
                          :prop="field.key">
              <template v-if="field.type === 'text'">
                <el-input
                    v-model="formData[field.key]"
                    :placeholder="field.placeholder"
                    :disabled="field.disabled || isViewMode"
                />
              </template>
              <template v-else-if="field.type === 'combobox'">
                <el-select
                    :size="'default'"
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
              <template v-else-if="field.type === 'textArea'">
                <el-input
                    type="textarea"
                    v-model="formData[field.key]"
                    :placeholder="field.placeholder"
                    :disabled="field.disabled || isViewMode"
                    :rows="field.rows || 4"
                />
              </template>
              <template v-else-if="field.type  === 'date'">
                <el-date-picker
                    v-model="formData[field.key]"
                    type="date"
                    :placeholder="field.placeholder"
                    :format="field.format"
                    :value-format="field.valueFormat"
                    :clearable="true"
                    popper-class="custom-date-picker-popper"
                />
              </template>
              <template v-else-if="field.type  === 'dateRange'">
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
            </el-form-item>
          </el-col>
        </template>
      </el-row>
    </el-form>
  </el-drawer>
</template>

<script lang="ts">
import {defineComponent, reactive, ref, watch, nextTick} from "vue";
import {FormInstance} from "element-plus"; // Import FormInstance từ element-plus
import {CircleCloseFilled, CirclePlus} from '@element-plus/icons-vue';
import {useI18n} from "vue-i18n";
export default defineComponent({
  name: "DynamicPopup",
  props: {
    visible: {
      type: Boolean,
      required: true,
    },
    config: {
      type: Object,
      required: true,
    },
    title: {
      type: String,
      default: "Popup",
    },
    mode: {
      type: String,
      default: "add", // "add", "edit", "view"
    },
    initialData: {
      type: Object,
      default: () => ({}),
    },
    onSave: {
      type: Function,
      required: true,
    },
  },
  setup(props, {emit}) {
    const {t} = useI18n();
    const visibleShow = ref(props.visible);
    const formData = reactive<Record<string, any>>({});
    const formRules = reactive<Record<string, any[]>>({});
    const popupForm = ref<FormInstance | null>(null);
    const actionForm = ref(props.mode);
    const isViewMode = ref(false);

    const resetForm = () => {
      Object.keys(formData).forEach((key) => {
        formData[key] = "";
      });
    };

    watch(() => props.visible, (newVal) => {
      visibleShow.value = newVal;
      isViewMode.value = props.mode === "view";
      props.config.fields.forEach((field: any) => {
        if (field.rules) {
          formRules[field.key] = field.rules;
        }
      });
      if (newVal) {
        resetForm();
        props.config.fields.forEach((field: any) => {
          if (field.rules) {
            formRules[field.key] = field.rules;
          }
          if (field.type === 'combobox' && field.defaultValue !== undefined) {
            formData[field.key] = field.defaultValue;
          }
        });
        if (props.mode !== "add") {
          Object.assign(formData, props.initialData);
        }
        nextTick(() => {
          popupForm.value?.clearValidate();
        });
      }
    });

    const onClose = () => {
      popupForm.value?.clearValidate();
      emit("close");
    };

    const onSubmit = () => {
      popupForm.value?.validate((valid: boolean) => {
        if (valid) {
          props.onSave(formData); // Gọi hàm onSave từ component cha
          setTimeout(() => {
            onClose();
          }, 100);
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
      actionForm,
      CirclePlus,
      CircleCloseFilled
    };
  },
});
</script>

<style scoped>
.dialog-footer {
  text-align: right;
}

.el-select--default {
  width: 100%;
}

.button-container {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.title-dialog {
  font-size: 18px;
  line-height: 24px;
  color: #3d3d3d;
  margin-bottom: 5px;
  margin-top: 5px;
  text-align: left;
}
</style>