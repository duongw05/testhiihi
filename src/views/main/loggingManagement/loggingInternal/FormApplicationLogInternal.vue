<template>
  <el-form
      :model="applicationLogInternalForm"
      ref="formRefApplicationLogInternal"
      label-width="auto"
      label-position="top"
  >
    <el-row :gutter="24" style="margin: 15px 0 0; max-height: 400px; overflow-y: scroll">
      <el-col :xs="xspr" :sm="smpr" :md="mdpr">
        <el-form-item size="default" class="form-item" label="Endpoint" prop="endpoint" label-width="auto">
          <el-input :disabled="true" v-model.trim="applicationLogInternalForm.endpoint" autocomplete="off"/>
        </el-form-item>
      </el-col>

      <el-col :xs="xspr" :sm="smpr" :md="mdpr">
        <el-form-item size="default" class="form-item" label="HTTP Method" prop="httpMethod" label-width="auto">
          <el-input :disabled="true" v-model.trim="applicationLogInternalForm.httpMethod" autocomplete="off"/>
        </el-form-item>
      </el-col>

      <el-col :xs="xspr" :sm="smpr" :md="mdpr">
        <el-form-item size="default" class="form-item" label="Người tạo" prop="createdBy" label-width="auto">
          <el-input :disabled="true" v-model.trim="applicationLogInternalForm.createdBy" autocomplete="off"/>
        </el-form-item>
      </el-col>

      <el-col :xs="xspr" :sm="smpr" :md="mdpr">
        <el-form-item size="default" class="form-item" label="Ngày tạo" prop="createdDate" label-width="auto">
          <el-input :disabled="true" v-model.trim="applicationLogInternalForm.createdDate" autocomplete="off"/>
        </el-form-item>
      </el-col>

      <el-col :xs="xspr" :sm="smpr" :md="mdpr">
        <el-form-item size="default" class="form-item" label="Trạng thái phản hồi" prop="responseStatus"
                      label-width="auto">
          <el-input :disabled="true" v-model.trim="applicationLogInternalForm.responseStatus" autocomplete="off"/>
        </el-form-item>
      </el-col>

      <el-col :xs="xspr" :sm="smpr" :md="mdpr">
        <el-form-item size="default" class="form-item" label="Thời gian phản hồi" prop="durationMs" label-width="auto">
          <el-input :disabled="true" v-model.trim="applicationLogInternalForm.durationMs" autocomplete="off"/>
        </el-form-item>
      </el-col>

      <el-col :xs="xspr" :sm="smpr" :md="mdpr">
        <el-form-item size="default" class="form-item" label="Service name" prop="serviceName" label-width="auto">
          <el-input :disabled="true" v-model.trim="applicationLogInternalForm.serviceName" autocomplete="off"/>
        </el-form-item>
      </el-col>

      <el-col :xs="24" :sm="24" :md="24">
        <el-form-item size="default" class="form-item" label="Request headers" prop="requestHeaders" label-width="auto">
          <el-input type="textarea" :disabled="true" v-model.trim="applicationLogInternalForm.requestHeaders"
                    autocomplete="off"/>
        </el-form-item>
      </el-col>

      <el-col :xs="24" :sm="24" :md="24">
        <el-form-item size="default" class="form-item" label="Request body" prop="requestBody" label-width="auto">
          <el-input type="textarea" :disabled="true" v-model.trim="applicationLogInternalForm.requestBody"
                    autocomplete="off"/>
        </el-form-item>
      </el-col>

      <el-col :xs="24" :sm="24" :md="24">
        <el-form-item size="default" class="form-item" label="Response body" prop="responseBody" label-width="auto">
          <el-input type="textarea" :disabled="true" v-model.trim="applicationLogInternalForm.responseBody"
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
            plain
            :icon="Close"
            @click="handleCloseForDialog"
        >
          Đóng
        </el-button>
      </el-row>
    </div>

  </el-form>
</template>

<script lang="ts">
import {defineComponent, reactive, watch} from 'vue'
import {Close} from "@element-plus/icons";

export default defineComponent({
  name: "FormApplicationLogInternal",
  computed: {
    Close() {
      return Close
    }
  },
  props: {
    formModeProps: String,
    applicationLogInternalData: Object,
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
  emits: ["closeForDialog"],
  setup(props, {emit}) {
    const applicationLogInternalForm = reactive({
      id: null,
      createdDate: null,
      serviceName: "",
      httpMethod: "",
      endpoint: "",
      requestHeaders: "",
      requestBody: "",
      responseStatus: null,
      responseBody: "",
      durationMs: null,
      createdBy: "",
      metadata: "",
    });

    const handleCloseForDialog = () => {
      emit("closeForDialog");
    }

    watch(
        () => props.applicationLogInternalData,
        (newValue) => {
          Object.assign(applicationLogInternalForm, newValue);
        },
        {deep: true}
    );
    return {
      // BIến
      applicationLogInternalForm,
      // Hàm
      handleCloseForDialog,
    }
  }
})
</script>

<style scoped lang="scss">

.button-container {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

</style>