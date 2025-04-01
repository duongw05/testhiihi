<template>
  <el-form
      :model="scheduleJobDetailForm"
      :rules="rules"
      ref="formScheduleJobDetailRef"
      label-width="auto"
      label-position="top"
      v-loading="loadingProps"
  >
    <el-row :gutter="24" style="margin: 15px 0 0">

      <el-col :xs="xspr" :sm="smpr" :md="mdpr" v-if="false">
        <el-form-item size="default" class="form-item" label="Key tiến trình" prop="jobKey" label-width="auto">
          <el-input :placeholder="formModeProps !== MODE_VIEW() ? 'Nhập giá trị' : ''"
                    :disabled="formModeProps === MODE_VIEW()" v-model.trim="scheduleJobDetailForm.jobKey"
                    autocomplete="off"/>
        </el-form-item>
      </el-col>

      <el-col :xs="xspr" :sm="smpr" :md="mdpr">
        <el-form-item size="default" class="form-item" label="Tên tiến trình" prop="jobName" label-width="auto">
          <el-input :placeholder="formModeProps !== MODE_VIEW() ? 'Nhập giá trị' : ''"
                    :disabled="formModeProps === MODE_VIEW()" v-model="scheduleJobDetailForm.jobName"
                    autocomplete="off"/>
        </el-form-item>
      </el-col>

      <el-col :xs="xspr" :sm="smpr" :md="mdpr">
        <el-form-item prop="jobClass" label="Class thực thi" :size="'default'" style="display: inherit">
          <el-select v-model="scheduleJobDetailForm.jobClass" placeholder="Chọn giá trị" style="width: 100%"
                     :disabled="formModeProps === MODE_VIEW()"
                     filterable clearable
          >
            <el-option
                v-for="item in appParams.fetchClassRunJob()"
                :key="item?.id"
                :label="item?.valueLabel"
                :value="item?.valueCode"
            />
          </el-select>
        </el-form-item>
      </el-col>

      <el-col :xs="xspr" :sm="smpr" :md="mdpr" v-if="false">
        <el-form-item size="default" class="form-item" label="Interface name" prop="interfaceName" label-width="auto">
          <el-input :placeholder="formModeProps !== MODE_VIEW() ? 'Nhập giá trị' : ''"
                    :disabled="formModeProps === MODE_VIEW()" v-model="scheduleJobDetailForm.interfaceName"
                    autocomplete="off"/>
        </el-form-item>
      </el-col>

      <el-col :xs="xspr" :sm="smpr" :md="mdpr">
        <el-form-item size="default" class="form-item" label="Nhóm tiến trình" prop="jobGroup" label-width="auto">
          <el-select v-model="scheduleJobDetailForm.jobGroup" placeholder="Chọn giá trị" style="width: 100%"
                     :disabled="formModeProps === MODE_VIEW()"
                     filterable clearable
          >
            <el-option
                v-for="item in appParams.fetchProcessJobGroup()"
                :key="item?.id"
                :label="item?.valueLabel"
                :value="item?.valueCode"
            />
          </el-select>
        </el-form-item>
      </el-col>

      <el-col :xs="xspr" :sm="smpr" :md="mdpr">
        <el-form-item size="default" class="form-item" label="Cron expression" prop="cronExpression" label-width="auto">
          <el-input :placeholder="formModeProps !== MODE_VIEW() ? 'Nhập giá trị' : ''"
                    :disabled="formModeProps === MODE_VIEW()" v-model="scheduleJobDetailForm.cronExpression"
                    autocomplete="off"/>
        </el-form-item>
      </el-col>

      <el-col :xs="xspr" :sm="smpr" :md="mdpr" v-if="false">
        <el-form-item size="default" class="form-item" label="Cron job" prop="cronJob" label-width="auto">
          <el-select v-model="scheduleJobDetailForm.cronJob" placeholder="Chọn giá trị" style="width: 100%"
                     :disabled="formModeProps === MODE_VIEW()"
                     filterable clearable
          >
            <el-option
                v-for="item in appParams.fetchProcessCronJob()"
                :key="item?.id"
                :label="item?.valueLabel"
                :value="item?.valueCode"
            />
          </el-select>
        </el-form-item>
      </el-col>

      <el-col :xs="xspr" :sm="smpr" :md="mdpr"  v-if="false">
        <el-form-item size="default" class="form-item" label="Chạy vào lúc" prop="timeStartProcess" label-width="auto">
          <el-input :placeholder="formModeProps !== MODE_VIEW() ? 'Nhập giá trị' : ''"
                    :disabled="formModeProps === MODE_VIEW()" v-model="scheduleJobDetailForm.timeStartProcess"
                    autocomplete="off"/>
        </el-form-item>
      </el-col>

      <el-col :xs="xspr" :sm="smpr" :md="mdpr"  v-if="false">
        <el-form-item size="default" class="form-item" label="Xử lý dữ liệu vào lúc" prop="timeStartData"
                      label-width="auto">
          <el-input :placeholder="formModeProps !== MODE_VIEW() ? 'Nhập giá trị' : ''"
                    :disabled="formModeProps === MODE_VIEW()" v-model="scheduleJobDetailForm.timeStartData"
                    autocomplete="off"/>
        </el-form-item>
      </el-col>

      <el-col :xs="xspr" :sm="smpr" :md="mdpr" v-if="false">
        <el-form-item size="default" class="form-item" label="Thời gian lặp lại" prop="repeatTime" label-width="auto">
          <el-input :placeholder="formModeProps !== MODE_VIEW() ? 'Nhập giá trị' : ''"
                    :disabled="formModeProps === MODE_VIEW()" v-model="scheduleJobDetailForm.repeatTime"
                    autocomplete="off"/>
        </el-form-item>
      </el-col>

      <el-col :xs="xspr" :sm="smpr" :md="mdpr">
        <el-form-item size="default" class="form-item" label="Trạng thái tiến trình" prop="jobStatus" label-width="auto" v-if="formModeProps === MODE_VIEW() || formModeProps === MODE_CREATE()">
          <el-select v-model="scheduleJobDetailForm.jobStatus" placeholder="Chọn giá trị" style="width: 100%"
                     :disabled="formModeProps === MODE_VIEW()"
                     filterable clearable
          >
            <el-option
                v-for="item in appParams.fetchProcessJobStatus()"
                :key="item?.id"
                :label="item?.valueLabel"
                :value="item?.valueCode"
            />
          </el-select>
        </el-form-item>
      </el-col>

      <!--      <el-col :xs="xspr" :sm="smpr" :md="mdpr">-->
      <!--        <el-form-item size="default" class="form-item" label="Ngày tạo" prop="createdDate" label-width="auto">-->
      <!--          <el-input :placeholder="formModeProps !== MODE_VIEW() ? 'Nhập giá trị' : ''"-->
      <!--                    :disabled="formModeProps === MODE_VIEW()" v-model="scheduleJobDetailForm.createdDate"-->
      <!--                    autocomplete="off"/>-->
      <!--        </el-form-item>-->
      <!--      </el-col>-->

      <el-col :xs="24" :sm="24" :md="24">
        <el-form-item size="default" class="form-item" label="Mô tả" prop="descInfo" label-width="auto">
          <el-input type="textarea" :placeholder="formModeProps !== MODE_VIEW() ? 'Nhập giá trị' : ''"
                    :disabled="formModeProps === MODE_VIEW()" v-model="scheduleJobDetailForm.descInfo"
                    autocomplete="off"/>
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
            :loading="loadingProps" size="default" type="primary"
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
import {appParams} from "@/constants/app-param";

export default defineComponent({
  name: "FormScheduleJobDetail",
  computed: {
    appParams() {
      return appParams
    },
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
    dataForm: Object,
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
  setup(props, {emit}) {

    const formScheduleJobDetailRef = ref();

    const defaultScheduleJobDetailForm = reactive({
      jobId: null,
      jobName: '',
      jobGroup: '',
      jobStatus: '',
      jobClass: '',
      jobKey: '',
      cronExpression: '',
      descInfo: '',
      interfaceName: '',
      repeatTime: null,
      cronJob: '',
      timeStartProcess: '',
      timeStartData: '',
      status: '',
    });

    const scheduleJobDetailForm = reactive({
      jobId: null,
      jobName: '',
      jobGroup: '',
      jobStatus: '',
      jobClass: '',
      jobKey: '',
      cronExpression: '',
      descInfo: '',
      interfaceName: '',
      repeatTime: null,
      cronJob: '',
      timeStartProcess: '',
      timeStartData: '',
      status: '',
    });

    /** Validate */
    const rules = {
      jobName: [
        {required: true, message: "Tên tiến trình không được để trống", trigger: ['change', 'blur']},
        {max: 255, message: "Tên tiến trình tối đa 255 ký tự", trigger: ['change', 'blur']},
      ],
      cronExpression: [
        {required: true, message: "Cron expression không được để trống", trigger: ['change', 'blur']},
      ],
      jobClass: [
        {required: true, message: "Class thực thi tiến trình không được để trống", trigger: ['change', 'blur']},
      ],
      jobStatus: [
        {required: true, message: "Trạng thái tiến trình không được để trống", trigger: ['change', 'blur']},
      ],
      jobGroup: [
        {required: true, message: "Nhóm tiến trình không được để trống", trigger: ['change', 'blur']},
      ],
    };

    // Đẩy dữ liệu lên khi thêm sửa
    const handleAction = () => {
      if (!formScheduleJobDetailRef.value) return;
      formScheduleJobDetailRef.value.validate((valid: boolean) => {
        if (valid) {
          if (props.formModeProps === MODE_CREATE) {
            emit("submitFormSystemAttribute", scheduleJobDetailForm);
          } else if (props.formModeProps === MODE_EDIT) {
            emit("submitFormSystemAttribute", scheduleJobDetailForm);
          }
        }
      });
    };

    const resetValidation = () => {
      if (formScheduleJobDetailRef.value) {
        formScheduleJobDetailRef.value.clearValidate();
      }
    };

    const resetForm = () => {
      Object.assign(scheduleJobDetailForm, defaultScheduleJobDetailForm);
      setTimeout(() => resetValidation(), 100);
    }

    watch(
        () => props.dataForm,
        (newValue) => {
          Object.assign(scheduleJobDetailForm, newValue);
          setTimeout(() => resetValidation(), 100);
        },
        {deep: true, immediate: true}
    );

    const handleCloseForDialog = () => {
      emit("closeForDialog");
    }

    return {
      //Biến
      formScheduleJobDetailRef,
      rules,
      scheduleJobDetailForm,
      defaultScheduleJobDetailForm,
      // Hàm
      handleAction,
      handleCloseForDialog,
      resetValidation,
      resetForm
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