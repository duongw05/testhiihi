<template>
  <el-dialog
      :model-value="openImportModal"
      width="500"
      style="text-align: start"
      :destroy-on-close="true"
      :show-close="false"
      :before-close="closeDialog"
  >
    <template #header>
      <span style="color: white">{{ title }}</span>
    </template>
    <div>
      <p>File import</p>
      <el-upload
          ref="upload"
          class="upload-demo"
          :limit="1"
          :on-exceed="handleExceed"
          :on-change="onChangeUpload"
          :auto-upload="false"
      >
        <template #trigger>
          <el-button size="large" type="primary" color="var(--system-primary-color)" :icon="UploadFilled">{{$t('message.menu.attendance.chooseFile')}}</el-button>
        </template>
        <template #tip>
          <div class="el-upload__tip text-red" style="padding-top: 5px">
            <i style="opacity: 70%;">{{ tipTrick }}</i>
          </div>
        </template>
        <template #file="{file}">
          <el-row class="list-file">
            <el-col :span="22">
              <span>{{ file.name }}</span>
            </el-col>
            <el-col :span="2">
              <el-icon color="green" v-if="validateStatus === 1">
                <CircleCheckFilled/>
              </el-icon>
              <el-icon color="orange" v-if="validateStatus === 0">
                <Warning/>
              </el-icon>
              <el-icon color="red" v-if="validateStatus === 2">
                <CircleCloseFilled/>
              </el-icon>
            </el-col>
          </el-row>
        </template>
      </el-upload>
    </div>
    <slot></slot>
    <template #footer>
      <div class="dialog-footer">
        <el-button size="default" @click="closeDialog" class="hover-white">{{$t('message.menu.attendance.cancelBtn')}}</el-button>
        <el-button size="default" type="primary" :disabled="validateStatus !== 0 || !isChooseFile" @click="check" :loading="loading === 'check'">{{$t('message.menu.attendance.check')}}</el-button>
        <el-button size="default" type="primary" color="var(--system-primary-color)" :disabled="validateStatus !== 1" @click="uploadFile" :loading="loading === 'upload'">{{$t('message.menu.attendance.upload')}}</el-button>
      </div>
    </template>
  </el-dialog>

</template>

<script lang="ts">
import {defineComponent, ref} from "vue";
import {CircleCheckFilled, CircleCloseFilled, Close, UploadFilled, Warning} from "@element-plus/icons";
import type {UploadFile, UploadInstance, UploadProps, UploadRawFile} from 'element-plus'
import {genFileId} from "element-plus";

export default defineComponent({
  name: 'ImportFile',
  computed: {
    UploadFilled() {
      return UploadFilled
    },
  },
  components: {Close, CircleCloseFilled, CircleCheckFilled, Warning, UploadFilled},
  // openImportModal: true/false; tipTrick: Dòng cảnh báo, lưu ý; title: chữ trên header modal; validateStatus: biến dùng để chuyển trạng thái validate File
  props: {
    openImportModal: {
      type: Boolean,
      required: true,
      default: () => false
    },
    tipTrick: {
      type: String,
      required: true,
      default: () => ''
    },
    title: {
      type: String,
      required: true,
      default: () => ''
    },
    validateStatus: {
      type: Number,
      required: true,
      default: () => 0
    },
    loading: {
      type: String,
      default: () => false
    },
  },
  // handleCloseImportModal: func đóng modal; uploadImportFile: func xử lý call API; validateFileUpload: func xử lý validateFile
  emits: ['handleCloseImportModal', 'uploadImportFile', 'validateFileUpload', 'handleOnChangeUpload'],
  setup(props, {emit}) {
    const upload = ref<UploadInstance>()
    const fileUpload = ref<UploadFile>()
    const isChooseFile = ref<boolean>(false)
    const uploadFile = () => {
      emit('uploadImportFile', fileUpload.value)
    }
    const check = () => {
      emit('validateFileUpload', fileUpload.value, true)
    }
    const handleExceed: UploadProps['onExceed'] = (files) => {
      emit('validateFileUpload', fileUpload.value, false)
      upload.value!.clearFiles()
      const file = files[0] as UploadRawFile
      file.uid = genFileId()
      upload.value!.handleStart(file)
    }

    const closeDialog = () => {
      isChooseFile.value = false
      emit('handleCloseImportModal', false)
    }

    const onChangeUpload: UploadProps['onChange'] = (file, files) => {
      isChooseFile.value = true
      fileUpload.value = file
      emit('handleOnChangeUpload', file)
    }
    return {
      fileUpload,
      upload,
      uploadFile,
      check,
      onChangeUpload,
      handleExceed,
      closeDialog,
      isChooseFile,
    }
  }
})
</script>


<style scoped lang="scss">
</style>