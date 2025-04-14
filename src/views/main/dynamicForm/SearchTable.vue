<template>
  <div class="box search-extend" style="margin-bottom: 0">
    <div class="box" style="margin-bottom: 15px;">
      <el-collapse class="no-border-collapse" v-model="activeNames">
        <el-collapse-item name="1">
          <template #title>
            <h3 class="style-header-box" style="margin-left: 4px">Thông tin tìm kiếm</h3>
          </template>
          <div class="layout-container-form flex space-between">    <!-- Form điều kiện tìm kiếm -->
            <el-form
                :model="filters"
                :rules="rules"
                ref="filterForm"
                style="width: 100%"
                class="filter-form"
                label-position="top"
            >
              <el-row :gutter="24" style="margin: 0; width: 100%">
                <el-col :span="24/config.searchForm.fields.length" class="form-item"
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
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24" class="filter-actions">
                  <el-button :icon="RefreshRight" @click="onReset" size="default" plain>Nhập lại</el-button>
                  <el-button type="primary" color="var(--system-primary-color)" @click="onSubmit" size="default">Tìm
                    kiếm
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
        <h3 style="font-size: 14px; line-height: 16px">Kết quả tìm kiếm ({{ pagination.total }})</h3>
        <div class="button-group">
          <el-button :icon="Plus" @click="handleAddNew"
                     color="var(--system-primary-color)" size="default" type="primary">Thêm mới
          </el-button>

        </div>
      </div>
    </div>
    <div class="layout-container-form flex space-between">
      <div class="layout-container-table">
        <!-- Bảng dữ liệu -->
        <el-table
            :data="data"
            style="width: 100%"
            v-loading="loading"
            border
            :header-cell-style="{background:'#DDE1E6', color: 'rgba(0,0,0,0.8)'}"
        >
          <el-table-column
              v-for="(column, index) in config.table.columns"
              :key="index"
              :prop="column.key"
              :label="column.label"
              :width="column.width"
              :align="column.align"
              border
              :header-align="column.headerAlign || 'center'"
          >
          </el-table-column>
          <el-table-column label="Hành động" header-align="center" align="center" width="110" min-width="110"
                           border
                           fixed="right">
            <template #default="{ row }">
              <div class="action-container">
                <el-tooltip v-for="(action, index) in config.table.actions"
                            :content="action.label">
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

          <!-- Phân trang -->
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
<!--  <dynamic-popup-->
<!--      :visible="isPopupVisible"-->
<!--      :config="config.popup"-->
<!--      :title="popupTitle"-->
<!--      :mode="popupMode"-->
<!--      :initialData="selectedData"-->
<!--      :onSave="handleSave"-->
<!--      @close="handleClose"-->
<!--  />-->
  <dynamic-drawer
      :visible="isPopupVisible"
      :config="config.popup"
      :title="popupTitle"
      :mode="popupMode"
      :initialData="selectedData"
      :onSave="handleSave"
      @close="handleClose"
  />
</template>

<script lang="ts">
import {defineComponent, onMounted, reactive, ref, watch} from "vue";
import {ElMessageBox, ElNotification} from "element-plus";
import {Delete, Document, Edit, Key, MoreFilled, Plus, RefreshRight} from "@element-plus/icons";
import DynamicPopup from "@/views/main/dynamicForm/DynamicPopup.vue";
import DynamicDrawer from "@/views/main/dynamicForm/DynamicDrawer.vue";
import {useI18n} from "vue-i18n";

export default defineComponent({
  name: "SearchTable1",
  components: {MoreFilled, DynamicPopup, DynamicDrawer},
  computed: {
    Delete() {
      return Delete
    },
    Key() {
      return Key
    },
    Edit() {
      return Edit
    },
    Document() {
      return Document
    },
    RefreshRight() {
      return RefreshRight
    },
    Plus() {
      return Plus
    }
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
  },
  setup(props) {
    const {t} = useI18n();
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
    const selectedData = ref({});

    const handlePageSize = (size: number) => {
      pagination.value.pageSize = size;
      fetchTableData();
    };

    const handleCurrent = (value) => {
      pagination.value.current = value;
      fetchTableData();
    }
    const filterForm = ref(null);

    // Khởi tạo filters và rules
    props.config.searchForm.fields.forEach((filter: any) => {
      filters[filter.key] = filter.defaultValue || "";
      if (filter.rules) {
        rules[filter.key] = filter.rules;
      }
    });

    const fetchTableData = async () => {
      try {
        if (loading.value) return
        loading.value = true;
        const response = await props.fetchData({
          filters,
          page: pagination.value.current,
          pageSize: pagination.value.pageSize,
        });
        data.value = response.data;
        pagination.value.total = response.totalElements;
        pagination.value.current = response.pageable.pageNumber;
        pagination.value.pageSize = response.pageable.pageSize

      } catch (error) {
        ElNotification({
          title: 'Error',
          message: error.response.data.errorCode,
          type: 'error',
          duration: 3 * 1000
        })
      } finally {
        loading.value = false;
      }
    };

    const onSubmit = () => {
      filterForm.value.validate((valid: boolean) => {
        if (valid) {
          fetchTableData();
        } else {
          ElNotification({
            title: 'Error',
            message: 'Vui lòng kiểm tra lại các điều kiện tìm kiếm!',
            type: 'error',
            duration: 3 * 1000
          })
        }
      });
    };

    const onReset = () => {
      props.config.searchForm.fields.forEach((filter: any) => {
        filters[filter.key] = filter.defaultValue || "";
        if (filter.rules) {
          rules[filter.key] = filter.rules;
        }
      });
      pagination.value.current = 1;
      fetchTableData();
    };

    const changePage = (page: number) => {
      pagination.value.current = page;
      fetchTableData();
    };
    const handleAction = (type: string, row: any) => {
      if (type === "edit") {
        popupMode.value = "edit";
        popupTitle.value = t('message.common.UpdateInfo', { name: t('message.menu.groupManage.groupName') });
        selectedData.value = {...row};
        isPopupVisible.value = true;
      } else if (type === "view") {
        popupMode.value = "view";
        popupTitle.value = t('message.common.UpdateInfo');
        selectedData.value = {...row};
        isPopupVisible.value = true;
      } else if (type === "delete") {
        remove(row);
      }
    };

    const remove = async (value: any) => {
      ElMessageBox.confirm(
          'Bạn có muốn xoá bản ghi này không?',
          'Xóa',
          {
            confirmButtonText: 'Xóa',
            cancelButtonText: 'Hủy',
            customClass: 'my-custom-messagebox',
          }
      )
          .then(async () => {
            try {
              loading.value = true;
              await props.handleDelete(value)
              loading.value = false;
              await fetchTableData();
            } catch (error) {
              console.error(error);
            }
          })
          .catch((e) => {
            console.error(e)
          })
    };

    const handleAddNew = () => {
      popupMode.value = "add";
      popupTitle.value = "Thêm mới thông tin";
      selectedData.value = {};
      isPopupVisible.value = true;
    };

    const handleClose = () => {
      selectedData.value = null;
      isPopupVisible.value = false;
      fetchTableData()
    };

    onMounted(() => {
      fetchTableData()
    });

    return {
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
      changePage,
      handleAction,
      isPopupVisible,
      popupMode,
      popupTitle,
      selectedData,
      handleAddNew,
      handleClose,
      activeNames
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
</style>
