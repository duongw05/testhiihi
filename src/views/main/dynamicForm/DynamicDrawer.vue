<template>
  <el-drawer
      v-model="visibleShow"
      :title="title"
      size="40%"
      :show-close="false"
  >
    <template #header>
      <h2 class="title-dialog">{{ title }}</h2>
      <el-button @click="onClose" style="width: 100px" size="default" plain>{{ $t('message.common.close') }}</el-button>
      <el-button type="primary" v-if="!isViewMode" color="var(--system-primary-color)" @click="onSubmit" style="width: 100px" size="default">
        {{formData['id'] ? $t('message.common.update')  : $t('message.common.add')}}
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
          <el-col :span="field.span">
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
            </el-form-item>
          </el-col>
        </template>
      </el-row>
    </el-form>
  </el-drawer>
</template>

<script lang="ts">
import {defineComponent, reactive, ref, watch} from "vue";

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
    const visibleShow = ref(props.visible);
    const formData = reactive({});
    const formRules = reactive({});
    const popupForm = ref(null);
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
      resetForm();
      props.config.fields.forEach((field: any) => {
        if (field.rules) {
          formRules[field.key] = field.rules;
        }
        if ("add" === props.mode) {
          formData[field.key] = field.defaultValue || "";
        }
      });
      if (props.mode !== "add") {
        Object.assign(formData, props.initialData);
      }
    });

    const onClose = () => {
      console.log("vaof daay may lan")
      popupForm.value.clearValidate()
      resetForm()
      emit("close");
    };

    const onSubmit = () => {
      popupForm.value.validate((valid: boolean) => {
        if (valid) {
          props.onSave(formData); // Gọi hàm onSave từ cha
          setTimeout(() => {
            onClose();
          }, 100)
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
      actionForm
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
