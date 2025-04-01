<template>
  <el-dialog
      v-model="visibleShow"
      width="50%"
  >
    <template #title>
      <h3 class="title-dialog">
        Hồ sơ tài khoản
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
                        :label="'Mã nhân viên'"
          >
            <el-input
                v-model="formData.staffCode"
                :disabled="isViewMode"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item size="default" class="form-item"
                        :label="'Tên nhân viên'"
          >
              <el-input
                  v-model="formData.fullName"
                  :disabled="isViewMode"
              />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item size="default" class="form-item"
                        :label="'Email'"
          >
              <el-input
                  v-model="formData.email"
                  :disabled="isViewMode"
              />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item size="default" class="form-item"
                        :label="'SĐT'"
          >
              <el-input
                  v-model="formData.cellphone"
                  :disabled="isViewMode"
              />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item size="default" class="form-item"
                        :label="'Giới tính'"
          >
              <el-input
                  :value="formData.gender == 1 ? 'Nam' : 'Nữ'"
                  :disabled="isViewMode"
              />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="onClose" style="width: 100px" size="default" plain>Quay lại</el-button>
    </div>
  </el-dialog>
</template>

<script lang="ts">
import {defineComponent, reactive, ref, watch} from "vue";
import {useStore} from "vuex";

export default defineComponent({
  name: "UserPopup",
  props: {
    visible: {
      type: Boolean,
      required: true,
    },
  },
  setup(props, {emit}) {
    const store = useStore()
    const visibleShow = ref(props.visible);
    const formData = reactive(store.state.user.info);
    const formRules = reactive({});
    const popupForm = ref(null);

    const isViewMode = ref(true);

    watch(() => props.visible, (newVal) => {
      visibleShow.value = newVal;
      isViewMode.value = true
    });

    const onClose = () => {
      visibleShow.value = false;
      emit("close");
    };

    return {
      formData,
      formRules,
      popupForm,
      isViewMode,
      onClose,
      visibleShow,
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
