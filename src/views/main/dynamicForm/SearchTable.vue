<template>
  <div class="box search-extend" style="margin-bottom: 0">
    <div class="box" style="margin-bottom: 15px;">
      <el-collapse class="no-border-collapse" v-model="activeNames">
        <el-collapse-item name="1">
          <template #title>
            <h3 class="style-header-box" style="margin-left: 4px">{{ t('message.common.searchInfo') }}</h3>
          </template>
          <div class="layout-container-form flex space-between">
            <el-form
                :model="filters"
                :rules="rules"
                ref="filterForm"
                style="width: 100%"
                class="filter-form"
                label-position="top"
            >
              <el-row :gutter="24" style="margin: 0; width: 100%">
                <el-col :span="8" class="form-item"
                        v-for="(filter, index) in config.searchForm.fields"
                        :key="index"
                >
                  <el-form-item
                      :size="'default'"
                      :label="filter.label"
                      :prop="filter.key"
                  >
                    <template v-if="filter.type === 'text'">
                      <el-input
                          v-model="filters[filter.key]"
                          :placeholder="filter.placeholder"
                      />
                    </template>
                    <template v-else-if="filter.type === 'combobox'">
                      <el-select
                          :size="'default'"
                          v-model="filters[filter.key]"
                          :placeholder="filter.placeholder"
                      >
                        <el-option
                            v-for="option in filter.options"
                            :key="option.value"
                            :label="option.label"
                            :value="option.value"
                        />
                      </el-select>
                    </template>
                    <template v-else-if="filter.type === 'date'">
                      <el-date-picker
                          v-model="filters[filter.key]"
                          type="date"
                          :placeholder="filter.placeholder"
                          format="YYYY-MM-dd"
                          value-format="YYYY-MM-dd"
                          :clearable="true"
                          popper-class="custom-date-picker-popper"
                      />
                    </template>
                    <template v-else-if="filter.type === 'dateRange'">
                      <el-date-picker
                          v-model="filters[filter.key]"
                          type="daterange"
                          range-separator="To"
                          start-placeholder="Start Date"
                          end-placeholder="End Date"
                          format="YYYY-MM-DD"
                          value-format="YYYY-MM-DD"
                          :clearable="true"
                          :popper-append-to-body="true"
                          placement="bottom-start"
                      />
                    </template>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24" class="filter-actions">
                  <el-button :icon="RefreshRight" @click="onReset" size="default" plain>
                    {{ t('message.common.reEnter') }}
                  </el-button>
                  <el-button type="primary" color="var(--system-primary-color)" @click="onSubmit" size="default">
                    {{ t('message.common.search') }}
                  </el-button>
                </el-col>
              </el-row>
            </el-form>
          </div>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
  <div class="box" style="margin-top: 12px">
    <div class="layout-container">
      <div class="button-container-table">
        <h3 style="font-size: 14px; line-height: 16px">{{ t('message.common.searchResults') }} ({{
            pagination.total
          }})</h3>
        <div class="button-group">

          <el-button
              :icon="Upload"
              @click="handleImportClick"
              size="default"
              plain
              style="margin-right: 8px"
          >
            {{ t('message.common.importData') }}
          </el-button>


          <el-button
              :icon="Download"
              @click="handleExportClick"
              size="default"
              plain
              style="margin-right: 8px"
          >
            {{ t('message.common.exportExcel') }}
          </el-button>

          <el-button :icon="Plus" @click="handleAddNew"
                     color="var(--system-primary-color)" size="default" type="primary">
            {{ t('message.common.add') }}
          </el-button>
        </div>
      </div>
    </div>
    <div class="layout-container-form flex space-between">
      <div class="layout-container-table">
        <el-table
            :data="data"
            style="width: 100%"
            v-loading="loading"
            border
            :header-cell-style="{background:'#DDE1E6', color: 'rgba(0,0,0,0.8)'}"
        >
          <el-table-column label="STT" header-align="center" align="center" width="100">
            <template #default="{ $index }">
              {{ (pagination.current - 1) * pagination.pageSize + $index + 1 }}
            </template>
          </el-table-column>
          <el-table-column
              v-for="(column, index) in config.table.columns"
              :key="index"
              :prop="column.key"
              :label="column.label"
              :width="column.width"
              :align="column.align"
              :formatter="column.formatter"
              border
              :header-align="column.headerAlign || 'center'"
          >
          </el-table-column>
          <el-table-column :label="t('message.common.actions')" header-align="center" align="center" width="110"
                           min-width="110"
                           border
                           fixed="right">
            <template #default="{ row }">
              <div class="action-container">
                <el-tooltip v-for="(action, index) in config.table.actions"
                            :content="action.label" :key="index">
                  <el-button style="width: 15px; margin: 0" plain text
                             :icon="action.type === 'edit' ? Edit :  action.type === 'view' ? Document : Delete"
                             :type="action.buttonType"
                             @click="handleAction(action.type, row)"
                  ></el-button>
                </el-tooltip>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <div class="pagination">

          <el-pagination
              :current-page="pagination.current"
              :page-size="pagination.pageSize"
              :page-sizes="[10, 20, 50]"
              :small="true"
              background
              layout="total, sizes, prev, pager, next, jumper"
              :total="pagination.total"
              @size-change="handlePageSize"
              @current-change="handleCurrent"
          />
        </div>
      </div>
    </div>
  </div>
  <dynamic-drawer
      :visible="isPopupVisible"
      :config="config.popup"
      :title="popupTitle"
      :mode="popupMode"
      :initialData="selectedData"
      :onSave="handleSave"
      @close="handleClose"
  />

  <el-dialog
      v-model="importDialogVisible"
      :title="t('message.common.importData', { name: config.title })"
      width="500px"
      :before-close="handleCloseImportDialog"
      destroy-on-close
  >
    <div class="import-dialog-content">
      <el-upload
          class="upload-area"
          drag
          action="#"
          :auto-upload="false"
          :on-change="onImportFileChange"
          :on-remove="onImportFileRemove"
          :file-list="importFileList"
          :limit="1"
          accept=".xls,.xlsx"
      >
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">
          {{ t('message.common.dragOr') }} <em>{{ t('message.common.clickToChooseExcel') }}</em>
        </div>
      </el-upload>

      <div class="upload-tip-text">
        {{ t('message.common.uploadTip') }}
      </div>

      <el-divider>{{ t('message.common.or') }}</el-divider>

      <div class="download-template-area">
        <el-button :icon="Download" @click="downloadSample">
          {{ t('message.common.downloadTemplate') }}
        </el-button>
      </div>
    </div>

    <template #footer>
    <span class="dialog-footer">
      <el-button @click="handleCloseImportDialog">{{ t('message.common.cancel') }}</el-button>
      <el-button
          type="primary"
          @click="confirmImport"
          :loading="importing"
          :disabled="importFileList.length === 0 || importing"
          color="var(--system-primary-color)"
      >
        {{ importing ? t('message.common.uploading') : t('message.common.upload') }}
      </el-button>
    </span>
    </template>
  </el-dialog>


</template>

<script lang="ts">
import {defineComponent, onMounted, reactive, ref, watch} from "vue";
import {ElMessageBox, ElNotification, ElMessage} from "element-plus";
import {Delete, Document, Download, Edit, Key, MoreFilled, Plus, RefreshRight, Upload, UploadFilled} from "@element-plus/icons-vue"; // Đã cập nhật import cho các icon
import DynamicDrawer from "@/views/main/dynamicForm/DynamicDrawer.vue";
import {useI18n} from "vue-i18n";
import {showConfirmDialog} from "@/utils/mixins/mixin";
import {downloadSchoolTemplate} from "@/api/school"; // Đảm bảo đường dẫn đúng cho hàm tải file mẫu

export default defineComponent({
  name: "SearchTable",
  components: {MoreFilled, DynamicDrawer, UploadFilled}, // Thêm UploadFilled icon
  computed: {
    Delete() { return Delete },
    Key() { return Key },
    Edit() { return Edit },
    Document() { return Document },
    RefreshRight() { return RefreshRight },
    Plus() { return Plus },
    Upload() { return Upload; },
    Download() { return Download; },
  },
  props: {
    config: {
      type: Object,
      required: true,
    },
    fetchData: {
      type: Function,
      required: true,
    },
    handleSave: {
      type: Function,
      required: true,
    },
    handleDelete: {
      type: Function,
      required: true,
    },
    // Prop này sẽ nhận hàm xử lý import từ component cha (ví dụ: api.importExcelV2)
    handleImport: {
      type: Function,
      default: null,
    },
    handleExport: {
      type: Function,
      default: null,
    },
  },
  setup(props) {
    const {t, locale} = useI18n();
    const filters = reactive({});
    const rules = reactive({});
    const activeNames = ref(['1'])
    const data = ref([]);
    const loading = ref(false);
    const pagination = ref({
      current: 1,
      total: 0,
      pageSize: 10,
      size: 10,
    });

    const isPopupVisible = ref(false);
    const popupMode = ref("add");
    const popupTitle = ref("");
    const popupTitleName = ref("");
    const selectedData = ref({});

    // === Import related state ===
    const importDialogVisible = ref(false);
    const importFileList = ref<any[]>([]);
    const selectedImportFile = ref<File | null>(null);
    const importing = ref(false);
    const importErrorList = ref([]);

    const handlePageSize = (size: number) => {
      pagination.value.pageSize = size;
      fetchTableData();
    };

    const handleCurrent = (value: number) => {
      pagination.value.current = value;
      fetchTableData();
    }
    const filterForm = ref(null);

    props.config.searchForm.fields.forEach((filter: any) => {
      filters[filter.key] = filter.defaultValue || "";
      if (filter.rules) {
        rules[filter.key] = filter.rules;
      }
    });

    watch(locale, () => {
      popupTitleName.value = props.config.title
    });

    const fetchTableData = async () => {
      try {
        if (loading.value) return;
        loading.value = true;
        const response = await props.fetchData({
          filters,
          page: pagination.value.current,
          pageSize: pagination.value.pageSize,
        });
        data.value = Array.isArray(response.data) ? response.data : [];
        pagination.value.total = response.totalElements || 0;
        pagination.value.current = response.pageable?.pageNumber || 1;
        pagination.value.pageSize = response.pageable?.pageSize || 10;
      } catch (error: any) {
        ElNotification({
          title: 'Lỗi',
          message: error.response?.data?.errorCode || t('message.common.fetchDataError'),
          type: 'error',
          duration: 3 * 1000,
        });
        data.value = [];
      } finally {
        loading.value = false;
      }
    };

    const onSubmit = () => {
      (filterForm.value as any)?.validate((valid: boolean) => {
        if (valid) {
          fetchTableData();
        } else {
          ElNotification({
            title: 'Lỗi',
            message: t('message.common.checkSearchConditions'),
            type: 'error',
            duration: 3 * 1000
          })
        }
      });
    };

    const onReset = () => {
      props.config.searchForm.fields.forEach((filter: any) => {
        filters[filter.key] = filter.defaultValue || "";
      });
      pagination.value.current = 1;
      fetchTableData();
    };

    const handleAction = (type: string, row: any) => {
      if (type === "edit") {
        popupMode.value = "edit";
        popupTitle.value = t('message.common.updateInfo', {name: props.config.title});
        selectedData.value = {...row};
        isPopupVisible.value = true;
      } else if (type === "view") {
        popupMode.value = "view";
        popupTitle.value = t('message.common.viewInfo', {name: props.config.title});
        selectedData.value = {...row};
        isPopupVisible.value = true;
      } else if (type === "delete") {
        remove(row);
      }
    };

    const remove = async (value: any) => {
      await showConfirmDialog(
          t("message.common.delConfirm"),
          t("message.common.delName", {name: popupTitleName.value}),
          t("message.common.del"),
          t("message.common.close"),
          async () => {
            try {
              loading.value = true;
              await props.handleDelete(value)
              loading.value = false;
              await fetchTableData();
            } catch (error) {
              console.error('Lỗi khi xóa:', error);
              ElMessage.error(t('message.common.deleteFailed') || 'Có lỗi xảy ra khi xóa!');
            }
          }
      );
    };

    const handleAddNew = () => {
      popupMode.value = "add";
      popupTitle.value = t('message.common.createInfo', {name: props.config.title});
      selectedData.value = {};
      isPopupVisible.value = true;
    };

    const handleClose = () => {
      selectedData.value = {};
      isPopupVisible.value = false;
      fetchTableData();
    };

    const handleImportClick = () => {
      importDialogVisible.value = true;
      importFileList.value = [];
      selectedImportFile.value = null;
    };

    const onImportFileChange = (file: any, files: any[]) => {
      importFileList.value = [file];
      selectedImportFile.value = file.raw;
    };


    const onImportFileRemove = () => {
      importFileList.value = [];
      selectedImportFile.value = null;
    };

    const confirmImport = async () => {
      if (!selectedImportFile.value) {
        ElMessage.warning(t('message.common.importFileRequired')); // ⚠️ cần thêm key này
        return;
      }

      const formData = new FormData();
      formData.append('file', selectedImportFile.value);

      importing.value = true;
      importErrorList.value = [];

      try {
        if (!props.handleImport) {
          ElMessage.error(t('message.common.importNotConfigured'));
          return;
        }

        const response = await props.handleImport(formData);

        if (
            response instanceof Blob &&
            (response.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' ||
                response.type === 'application/octet-stream')
        ) {
          const url = window.URL.createObjectURL(response);
          const link = document.createElement('a');
          link.href = url;
          link.setAttribute('download', 'ImportError_' + Date.now() + '.xlsx');
          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link);
          window.URL.revokeObjectURL(url);

          ElNotification({
            title: t('message.common.warning'),
            message: t('message.common.importErrorFileDownloaded'),
            type: 'warning',
            duration: 5000,
          });
          return;
        }

        if (response?.errCode || (response?.errors && response.errors.length > 0)) {
          if (Array.isArray(response.errors)) {
            importErrorList.value = response.errors;
          }

          ElMessage.warning(response.errCode || t('message.common.invalidImportData'));
          return;
        }

        ElMessage.success(t('message.common.importSuccess'));
        importDialogVisible.value = false;
        importFileList.value = [];
        selectedImportFile.value = null;
        fetchTableData();
      } catch (error: any) {
        console.error('Import failed:', error);
        const errorMessage =
            error?.response?.data?.message || t('message.common.importFailed');
        ElMessage.error(errorMessage);
      } finally {
        importing.value = false;
      }
    };

    const downloadSample = async () => {
      try {
        const response = await downloadSchoolTemplate();
        const link = document.createElement('a');
        link.href = URL.createObjectURL(response.data);
        link.download = 'SchoolImportTemplate.xlsx';
        document.body.appendChild(link);
        link.click();
        URL.revokeObjectURL(link.href);

        ElMessage.success(t('message.common.downloadTemplateSuccess'));
      } catch (error) {
        console.error('Tải file mẫu lỗi:', error);
        ElMessage.error(t('message.common.downloadTemplateFailed'));
      }
    };

    const handleCloseImportDialog = () => {
      // Đã bỏ ElMessageBox.confirm và trực tiếp đóng dialog
      importDialogVisible.value = false;
      importFileList.value = []; // Reset file list
      selectedImportFile.value = null; // Reset selected file
      importing.value = false; // Đảm bảo tắt trạng thái loading
    };

    // === End Import Logic ===

    const handleExportClick = async () => {
      if (props.handleExport) {
        try {
          await ElMessageBox.confirm(
              t('message.common.exportConfirm') || 'Bạn có chắc chắn muốn xuất dữ liệu ra Excel không?',
              t('message.common.exportTitle') || 'Xác nhận xuất Excel',
              {
                confirmButtonText: t('message.common.agree') || 'Đồng ý',
                cancelButtonText: t('message.common.cancel') || 'Hủy',
                type: 'warning',
              }
          );
          await props.handleExport();
        } catch (error) {
          if (error === 'cancel') {
            ElMessage.info(t('message.common.exportCanceled') || 'Đã hủy thao tác xuất.');
          } else {
            console.error('SearchTable: Lỗi xác nhận xuất:', error);
            ElMessage.error(t('message.common.exportFailed') || 'Xuất Excel thất bại!');
          }
        }
      } else {
        ElNotification({ title: 'Thông báo', message: 'Chức năng Xuất Excel chưa được cấu hình.', type: 'info' });
      }
    };

    onMounted(() => {
      popupTitleName.value = props.config.title;
      fetchTableData();
    });

    return {
      t,
      filters,
      rules,
      data,
      loading,
      pagination,
      handleCurrent,
      handlePageSize,
      filterForm,
      onSubmit,
      onReset,
      handleAction,
      isPopupVisible,
      popupMode,
      popupTitle,
      popupTitleName,
      selectedData,
      handleAddNew,
      handleClose,
      activeNames,
      handleImportClick,
      handleExportClick,
      // Import related
      importDialogVisible,
      importFileList,
      onImportFileChange,
      onImportFileRemove,
      confirmImport,
      downloadSample,
      handleCloseImportDialog,
      importing // trả về trạng thái importing
    };
  },
});
</script>

<style scoped>
.form-item {
  margin-bottom: 0;
}

.title-search {
  font-size: 16px;
  font-weight: 600;
}

.button-container-table {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 10px 10px 0px 10px;
}

.layout-container-form {
  padding: 0;
}

.pagination {
  padding: 15px;
  display: flex;
  justify-content: end;
}

.button-group {
  display: flex;
}

.box {
  background-color: var(--system-container-main-background);
  width: calc(100% - 30px);
  height: calc(100% - 30px);
  margin: 15px;
  display: flex;
  flex-direction: column;
}

.el-select--default {
  width: 100%;
}

.form-item {
  margin-bottom: 20px; /* Khoảng cách giữa các hàng theo chiều dọc */
}

.filter-actions {
  display: flex;
  justify-content: center;
}

.action-container {
  display: flex;
  align-items: center;
  justify-content: center;
}

/* Styles for the import dialog */
.import-dialog-content {
  text-align: center;
  padding: 10px 0;
}

.upload-area {
  margin-bottom: 15px;
}

.upload-tip-text {
  font-size: 12px;
  color: #909399;
  margin-top: 5px;
}

.download-template-area {
  margin-top: 20px;
}

.el-divider {
  margin: 20px 0;
}

/* Custom style for El-Upload drag area */
.upload-area .el-upload-dragger {
  height: 120px; /* Adjust height as needed */
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.upload-area .el-icon--upload {
  font-size: 40px;
  color: #c0c4cc;
}
</style>