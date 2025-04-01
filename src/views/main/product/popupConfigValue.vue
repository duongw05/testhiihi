<template>
  <el-dialog
      v-model="visibleShow"
      width="70%"
      @close="onClose('close')"
  >
    <template #title>
      <h3 class="title-dialog">
        {{ title }}
      </h3>
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
        <el-col :span="12">
          <el-form-item size="default" class="form-item"
                        :label="'Chức năng'"
                        :prop="'functionCode'">
            <el-select
                :size="'default'"
                v-model="formData.functionCode"
                :disabled="isViewMode"
            >
              <el-option
                  v-for="item in appParams.fetchSystemFeature()"
                  :key="item?.id"
                  :label="item?.valueLabel"
                  :value="item?.valueCode"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item size="default" class="form-item"
                        :label="'Mã cấu hình'"
                        :prop="'codeConfig'">
            <el-input
                v-model.lazy.trim="formData.codeConfig"
                :disabled="isViewMode"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item size="default" class="form-item"
                        :label="'Tên hiển thị'"
                        :prop="'label'">
            <el-input
                v-model="formData.label"
                :disabled="isViewMode"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item size="default" class="form-item"
                        :label="'Kích thước'"
                        :prop="'span'">
            <el-input
                v-model="formData.span"
                v-integer-format="{max : 24}"
                type="text"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item size="default" class="form-item"
                        :label="'Điều kiện rằng buộc'"
                        :prop="'requireOption'">
            <el-select
                :size="'default'"
                v-model="formData.requireOption"
                :disabled="isViewMode"
            >
              <el-option
                  v-for="item in appParams.fetchSystemConfigRequire()"
                  :key="item?.id"
                  :label="item?.valueLabel"
                  :value="item?.valueCode"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item size="default" class="form-item"
                        :label="'Loại dữ liệu'"
                        :prop="'type'">
            <el-select
                :size="'default'"
                v-model="formData.type"
                :disabled="isViewMode"
            >
              <el-option
                  v-for="item in appParams.fetchSystemConfigType()"
                  :key="item?.id"
                  :label="item?.valueLabel"
                  :value="item?.valueCode"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12" v-if="type === 'COMBOBOX'">
          <el-form-item size="default" class="form-item"
                        :label="'Mã cấu hình model'"
                        :prop="'selectBoxModel'">
            <el-input
                v-model="formData.selectBoxModel"
                :disabled="isViewMode"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12" v-if="type === 'COMBOBOX'">
          <el-form-item size="default" class="form-item"
                        :label="'Mã cấu hình thuộc tính'"
                        :prop="'selectBoxAtt'">
            <el-input
                v-model="formData.selectBoxAtt"
                :disabled="isViewMode"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item size="default" class="form-item"
                        :label="'Giá trị mặc định'"
                        :prop="'defaultValue'">
            <el-input
                v-model="formData.defaultValue"
                :disabled="isViewMode"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item size="default" class="form-item"
                        :label="'Placeholder'"
                        :prop="'placeholder'">
            <el-input
                v-model="formData.placeholder"
                :disabled="isViewMode"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item size="default" class="form-item"
                        :label="'Giá trị tối thiểu'"
                        :prop="'minValue'">
            <el-input
                v-model="formData.minValue"
                :disabled="isViewMode"
                v-integer-format="{max : 1_000_000_000_000}"
                placeholder="Nhập tối đa 1,000,000,000,000"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item size="default" class="form-item"
                        :label="'Giá trị tối đa'"
                        :prop="'maxValue'">
            <el-input
                v-model="formData.maxValue"
                :disabled="isViewMode"
                v-integer-format="{max : 1_000_000_000_000}"
                placeholder="Nhập tối đa 1,000,000,000,000"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="onClose('close')" style="width: 100px" size="default" plain>Quay lại</el-button>
      <el-button type="primary" v-if="!isViewMode" color="var(--system-primary-color)" @click="onSubmit"
                 style="width: 100px" size="default">
        {{ mode !== 'add' ? 'Cập nhật' : 'Thêm mới' }}
      </el-button>
    </div>
  </el-dialog>
</template>

<script lang="ts">
import {defineComponent, reactive, ref, toRefs, watch} from "vue";
import {appParams} from "@/constants/app-param";
import {toNumber} from "@/utils/mixins/mixin";

export default defineComponent({
  name: "DynamicPopup",
  computed: {
    appParams() {
      return appParams
    }
  },
  methods: {},
  props: {
    visible: {
      type: Boolean,
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
  },
  setup(props, {emit}) {
    const visibleShow = ref(props.visible);
    const formData = reactive({
      uuid: null,
      functionCode: null,
      codeConfig: null,
      label: null,
      type: null,
      minValue: null,
      maxValue: null,
      requireOption: null,
      selectBoxModel: null,
      selectBoxAtt: null,
      span: null,
      defaultValue: null,
      placeholder: null,
    });
    const {minValue, maxValue, type} = toRefs(formData);
    const validateMinValue = (rule: any, value: any, callback: any) => {
      // @ts-ignore
      if (value && maxValue.value && toNumber(value) > toNumber(maxValue.value)) {
        callback(new Error("Giá trị tối thiểu không được lớn hơn giá trị tối đa"));
      } else {
        callback();
      }
    }
    const validateMaxValue = (rule: any, value: any, callback: any) => {
      // @ts-ignore
      if (value !== null && maxValue.value !== null && toNumber(value) < toNumber(minValue.value)) {
        callback(new Error("Giá trị tối đa không được nhỏ hơn giá trị tối thiểu"));
      } else {
        callback();
      }
    }
    const formRules = reactive({
      codeConfig: [
        {required: true, message: 'Vui lòng nhập Mã cấu hình', trigger: 'blur'},
        {min: 1, max: 50, message: 'Mã cấu hình tối đa 50 ký tự', trigger: 'blur'}
      ],
      label: [
        {required: true, message: 'Vui lòng nhập Tên hiển thị', trigger: 'blur'},
        {min: 1, max: 100, message: 'Tên hiển thị tối đa 100 ký tự', trigger: 'blur'}
      ],
      span: [
        {required: true, message: 'Vui lòng nhập Kích thước', trigger: 'blur'},
      ],
      functionCode: [{required: true, message: 'Vui lòng chọn Chức năng'}],
      type: [{required: true, message: 'Vui lòng chọn Loại dữ liệu', trigger: 'change'}],
      requireOption: [{required: true, message: 'Vui lòng chọn Điều kiện rằng buộc', trigger: 'change'}],
      defaultValue: [
        {min: 1, max: 500, message: 'Giá trị mặc định tối đa 500 ký tự', trigger: 'blur'}
      ],
      minValue: [
        {validator: validateMinValue, trigger: "blur"}
      ],
      maxValue: [
        {validator: validateMaxValue, trigger: "blur"}
      ],
      selectBoxModel: [
        {required: true, message: 'Vui lòng nhập Mã cấu hình model', trigger: 'blur'},
        {min: 1, max: 100, message: 'Mã cấu hình model tối đa 100 ký tự', trigger: 'blur'}
      ],
      selectBoxAtt: [
        {required: true, message: 'Vui lòng nhập Mã cấu hình thuộc tính', trigger: 'blur'},
        {min: 1, max: 100, message: 'Mã cấu hình thuộc tính tối đa 100 ký tự', trigger: 'blur'}
      ],
      placeholder: [
        {min: 1, max: 100, message: 'Placeholder tối đa 300 ký tự', trigger: 'blur'}
      ],
    });

    const popupForm = ref(null);
    const actionForm = ref(props.mode);

    const isViewMode = ref(false);
    const resetForm = () => {
      Object.assign(formData, {
        uuid: null,
        functionCode: null,
        codeConfig: null,
        label: null,
        type: null,
        minValue: null,
        maxValue: null,
        requireOption: null,
        selectBoxModel: null,
        selectBoxAtt: null,
        span: null,
        defaultValue: null,
        placeholder: null,
      })
    };
    watch(() => props.visible, (newVal) => {
      visibleShow.value = newVal;
      isViewMode.value = props.mode === "view";
      resetForm();
      if (props.mode !== "add") {
        Object.assign(formData, props.initialData);
      }
      setTimeout(() => {
        popupForm.value.clearValidate()
      }, 150)
    });

    const onClose = async (item: any) => {
      let data: any = null
      if (item === 'submit') {
        data = {...formData}
      }
      resetForm()
      await popupForm.value.clearValidate()
      setTimeout(() => {
        emit("closeFormData", data);
      }, 100)
    };

    const onSubmit = () => {
      popupForm.value.validate((valid: boolean) => {
        if (valid) {
          onClose('submit');
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
      type,
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
  color: white;
  margin-bottom: 5px;
  margin-top: 5px;
  text-align: left;
}

</style>
