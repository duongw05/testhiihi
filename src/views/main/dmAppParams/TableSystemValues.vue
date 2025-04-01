<template>
  <div>
    <!--  Bảng -->
    <el-table
        :header-cell-style="{background: 'rgba(221,225,230,0.22)'}"
        v-loading="loading"
        :data="dmValuesDataTable"
        border
        style="margin-top: 10px; box-shadow: 0 2px 5px rgba(0,0,0,0.1);">

      <!--      <el-table-column label="" width="120px"/>-->

      <el-table-column
          align="center"
          header-align="center"
          label="STT"
          type="index"
          width="75px">
        <template #default="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column header-align="center" label="Mã giá trị" prop="valueCode"/>
      <el-table-column header-align="center" label="Nhãn giá trị" prop="valueLabel"/>
      <el-table-column header-align="center" label="Mô tả" prop="description"/>

      <!-- Cột hành động -->
      <el-table-column :label="''" align="center" fixed="right" width="150">
        <template #default="scope">
          <div class="toolbar" style="display: flex; justify-content: center;">
            <div class="action-container">
              <el-tooltip content="Xem chi tiết">
                <el-button type="danger"
                           :loading="loading"
                           text
                           :icon="Document"
                           style="width: 20px; margin: 0"
                           @click="handleOpenDialogFormSystemValue(MODE_VIEW(), scope.row.id)">
                </el-button>
              </el-tooltip>

              <el-tooltip content="Cập nhật">
                <el-button :icon="Edit"
                           :loading="loading"
                           text
                           type="danger"
                           style="width: 20px; margin: 0"
                           @click="handleOpenDialogFormSystemValue(MODE_EDIT(), scope.row.id)">
                </el-button>
              </el-tooltip>

              <el-tooltip content="Xóa">
                <el-button text
                           type="danger"
                           :loading="loading"
                           style="width: 20px; margin: 0"
                           :icon="Delete"
                           @click="handleDeleteDmValues(scope.row.id)">
                </el-button>
              </el-tooltip>
            </div>
          </div>
        </template>
      </el-table-column>

    </el-table>
  </div>
  <teleport to="main">
    <!-- Dialog form system value -->
    <el-dialog
        @close="handleCloseDialogFormSystemValue"
        v-model="isVisibleDialogFormSystemValue"
        width="40%">
      <template #title>
        <h3 class="title-dialog">
          {{ titleDialogFormSystemValue }}
        </h3>
      </template>
      <FormSystemValue
          :loading-props="loading"
          :form-mode-props="formModeSystemValue"
          :xspr="24" :smpr="12" :mdpr="12" :lgpr="12"
          :system-value-data="systemValue"
          @submit-form-system-value="handleSubmitFormSystemValue"
          @close-for-dialog="handleCloseDialogFormSystemValue"
      />
    </el-dialog>
  </teleport>
</template>

<script lang="ts">
import {defineComponent, onMounted, reactive, ref} from "vue";
import {Delete, Document, Edit, Setting} from "@element-plus/icons";
import {ElMessageBox, ElNotification} from "element-plus";
import {deleteDmValues, getDmValuesById, getValues, updateDmValues} from "@/api/systemValues";
import FormSystemValue from "@/views/main/dmAppParams/FormSystemValue.vue";
import {MODE_CREATE, MODE_EDIT, MODE_VIEW} from "@/constants/formMode";

export default defineComponent({
  methods: {
    MODE_EDIT() {
      return MODE_EDIT
    },
    MODE_VIEW() {
      return MODE_VIEW
    }
  },
  components: {
    FormSystemValue,
    Setting,
  },
  props: {
    dmModelsCode: String,
    dmAttributeCode: String
  },
  emits: ['closeModal'],
  setup(props, {emit}) {
    const dmValuesDataTable = ref([]); //Hứng dữ liệu load lên bảng

    const loading = ref(false);
    const isVisibleDialogFormSystemValue = ref(false);
    const titleDialogFormSystemValue = ref();
    const formModeSystemValue = ref(MODE_CREATE);

    const handleOpenDialogFormSystemValue = async (formMode: string, data: any) => {
      isVisibleDialogFormSystemValue.value = true;
      if (formMode === MODE_CREATE) {
        formModeSystemValue.value = MODE_CREATE;
        titleDialogFormSystemValue.value = 'Thêm mới giá trị';
      } else if (formMode === MODE_EDIT) {
        formModeSystemValue.value = MODE_EDIT;
        titleDialogFormSystemValue.value = 'Cập nhật giá trị';
        await fetchDmValuesFormById(data);
      } else if (formMode === MODE_VIEW) {
        formModeSystemValue.value = MODE_VIEW;
        titleDialogFormSystemValue.value = 'Chi tiết giá trị';
        await fetchDmValuesFormById(data);
      }
    }
    const systemValue = reactive({
      id: null,
      valueCode: "",
      attCode: "",
      modelCode: "",
      valueLabel: "",
      description: ""
    });
    const handleCloseDialogFormSystemValue = () => {
      isVisibleDialogFormSystemValue.value = false;
    }

    /**Tải dữ liệu cho bảng dm_values*/
    const fetchDmValuesDataTable = async () => {
      try {
        const response = await getValues(props.dmModelsCode, props.dmAttributeCode);
        dmValuesDataTable.value = response.data.data;
      } catch (error) {
        console.error('Có lỗi trong fetchDmAttributesDataTable(): ', error);
      } finally {
      }
    };

    /**
     * Bắt sự kiện xóa giá trị thuộc dmValues*/
    const handleDeleteDmValues = async (id: number) => {
      ElMessageBox.confirm(
          'Bạn có muốn xoá giá trị này không',
          'Xác nhận',
          {
            confirmButtonText: 'Đồng ý',
            cancelButtonText: 'Hủy',
            type: 'warning', customClass: 'my-custom-messagebox',

          }
      )
          .then(async () => {
            try {
              await deleteDmValues(id);
              handleSuccess("Xóa giá trị thành công");
              await fetchDmValuesDataTable();
            } catch (error) {
              console.error(error);
            } finally {

            }
          })
          .catch((e) => {

            console.error(e)
          })
    };

    /**
     * Bắt sự kiện update dm_values*/
    const handleUpdateDmValues = async (data: any) => {
      try {
        loading.value = true;
        await updateDmValues(data);
        await fetchDmValuesDataTable();
        handleSuccess("Cập nhật giá trị thành công");
        handleCloseDialogFormSystemValue();
      } catch (error: any) {
        console.log('Co loi trong handleUpdateDmValues, ', error);
      } finally {
        loading.value = false;
      }
    }

    /**Tải dm_values by id*/
    const fetchDmValuesFormById = async (id: number) => {
      try {
        loading.value = true;
        const response = await getDmValuesById(id);
        Object.assign(systemValue, response.data.data);
      } catch (error) {
        console.error('Có lỗi trong fetchDmValuesFormById: ', error);
      } finally {
        loading.value = false;
      }
    };

    const handleSubmitFormSystemValue = async (data: any) => {
      if(formModeSystemValue.value === MODE_EDIT) {
        await handleUpdateDmValues(data);
      }
    }

    const handleSuccess = (message: string) => {
      ElNotification({
        title: 'Thành công',
        message,
        type: 'success',
        duration: 3000
      });
    };

    onMounted(() => {  // Khởi tạo dữ liệu
      fetchDmValuesDataTable();
    });

    return {
      // Biến
      dmValuesDataTable,
      isVisibleDialogFormSystemValue,
      titleDialogFormSystemValue,
      formModeSystemValue,
      loading,
      systemValue,
      //Hàm
      handleUpdateDmValues,
      handleOpenDialogFormSystemValue,
      handleCloseDialogFormSystemValue,
      handleDeleteDmValues,
      fetchDmValuesDataTable,
      handleSubmitFormSystemValue,
      handleSuccess,
      Delete,
      Document,
      Edit,
      emit,
      props
    }
  }
})
</script>

<style scoped lang="scss">
.action-container {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 13px;
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
