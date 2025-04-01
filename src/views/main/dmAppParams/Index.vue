<template>
  <div v-loading="loading">
  <!--   Vùng tìm kiếm-->
  <div class="search-extend">
    <div class="box">
      <div class="layout-container-form flex space-between" style="padding-bottom: 15px">
        <el-form
            ref="ruleFormRef"
            :model="searchQuery"
            :rules="rules"
            style="width: 100%"
        >
          <div class="demo-collapse no-border-collapse">
            <el-collapse :model-value="collapseName">
              <el-collapse-item name="1">
                <template #title>
                  <h3 class="style-header-box">Thông tin tìm kiếm</h3>
                </template>
                <el-row :gutter="24" style="margin: 0; width: 100%">
                  <el-col :xs="24" :md="12" :lg="12" class="form-item">
                    <el-form-item :size="'default'" label="Mã danh mục" prop="codeAtt" style="display: inherit">
                      <el-input v-model.lazy.trim="searchQuery.code" clearable
                                :placeholder="'Nhập giá trị'"></el-input>
                    </el-form-item>

                  </el-col>
                  <el-col :xs="24" :md="24" :lg="12" class="form-item">
                    <el-form-item :size="'default'" label="Tên danh mục" prop="nameAtt" style="display: inherit">
                      <el-input v-model.lazy.trim="searchQuery.name" clearable
                                :placeholder="'Nhập giá trị'"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <div class="button-container center mb-5 mt-5">
                  <el-button :icon="RefreshRight" plain size="default" @click="resetFormSearch">Nhập lại</el-button>
                  <el-button :icon="Search" size="default" type="primary" color="var(--system-primary-color)"
                             @click="validateForm">Tìm kiếm
                  </el-button>
                </div>
              </el-collapse-item>
            </el-collapse>
          </div>
        </el-form>
      </div>
    </div>
  </div>
  <!-- Kết thúc vùng tìm kiếm  -->

  <!--  Vùng bảng dữ liệu -->
  <div class="search-extend">
    <div class="box">
      <div class="layout-container-form flex space-between" style="padding-bottom: 15px">
        <div class="demo-collapse no-border-collapse" style="width: 100%">
          <el-collapse :model-value="collapseName">
            <el-collapse-item name="1">
              <template #title>
                <h3 class="style-header-box" style="width: 200px; text-align: left">Kết quả tìm kiếm
                  ({{ pagination.total }})</h3>
                <div style="display: flex; justify-content: right; width: 100%; margin-right: 20px">
                  <el-button :icon="Plus" size="default"
                             color="var(--system-primary-color)"
                             type="primary" @click="handleOpenDialogFormSystemModel(MODE_CREATE(), null)">Thêm mới danh
                    mục
                  </el-button>
                </div>
              </template>
              <div class="layout-container-table flex space-between">
                <el-table :data="tableData"
                          :header-cell-style="{background:'#DDE1E6', color: 'rgba(0,0,0,0.8)'}"
                          border
                          style="width: 100%; margin-bottom: 20px">

                    <el-table-column type="expand"
                                     default-expand-all>
                      <template #default="scope">
                        <div style="background-color: #f9f9f9; padding-left: 2px;">
                          <TableSystemAttribute ref="tableSystemAttributeRef" :dmModelsCode="scope.row.code"/>
                        </div>
                      </template>
                    </el-table-column>

                    <el-table-column align="center"
                                     header-align="center"
                                     label="STT"
                                     width="75px">
                      <template #default="scope">
                        {{ (pagination.current - 1) * pagination.pageSize + scope.$index + 1 }}
                      </template>
                    </el-table-column>

                    <el-table-column header-align="center" label="Mã danh mục" prop="code"/>
                    <el-table-column header-align="center" label="Tên danh mục" prop="name"/>
                    <el-table-column header-align="center" label="Mô tả" prop="description"/>
                    <el-table-column header-align="center" label="Hành động" align="center" fixed="right" width="150">
                      <template #default="scope">
                        <div class="action-container" style="display: flex; justify-content: center; padding: 5px;">
                          <el-tooltip :content="'Thêm thuộc tính'" placement="top">
                            <el-button
                                :icon="Plus" style="width: 15px; margin: 0;" text
                                type="danger"
                                @click="handleOpenDialogFormSystemAttribute(MODE_CREATE(), scope.row)"
                            >
                            </el-button>
                          </el-tooltip>
                          <span style="border-left: 1px solid #ccc; height: 20px; margin: 0 10px;"></span>

                          <el-tooltip :content="'Xem chi tiết danh mục'" placement="top">
                            <el-button
                                :icon="Document" style="width: 15px; margin: 0;" text
                                type="danger"
                                @click="handleOpenDialogFormSystemModel(MODE_VIEW(), scope.row)"
                            >
                            </el-button>
                          </el-tooltip>

                          <div class="toolbar">
                            <el-dropdown>
                            <span class="el-dropdown-link" style="width: 15px ; margin: 0">
                              <el-icon style="margin-top: 1px">
                                <MoreFilled/>
                              </el-icon>
                            </span>
                              <template #dropdown>
                                <el-dropdown-menu>
                                  <el-dropdown-item>
                                    <el-button
                                        class="custom-button"
                                        :icon="Edit" text
                                        type="danger"
                                        @click="handleOpenDialogFormSystemModel(MODE_EDIT(), scope.row)"
                                    >
                                      Cập nhật danh mục
                                    </el-button>
                                  </el-dropdown-item>
                                  <el-dropdown-item>
                                    <el-button
                                        class="custom-button"
                                        :icon="Delete" text
                                        type="danger"
                                    >
                                      Xóa danh mục
                                    </el-button>
                                  </el-dropdown-item>
                                </el-dropdown-menu>
                              </template>
                            </el-dropdown>
                          </div>
                        </div>
                      </template>
                    </el-table-column>
                </el-table>
              </div>
              <div class="pagination">
                <el-pagination
                    :current-page="pagination.current"
                    :page-size="pagination.pageSize"
                    :page-sizes="[10, 20, 50]"
                    :small="true"
                    :total="pagination.total"
                    background
                    layout="total, sizes, prev, pager, next, jumper"
                    @size-change="handlePageSize"
                    @current-change="handleCurrent"
                />
              </div>
            </el-collapse-item>
          </el-collapse>
        </div>
      </div>
    </div>
  </div>
<!--Kết thúc phần bảng dữ liệu-->

  <!--  Dialog form System Model -->
  <el-dialog
      @close="handleCloseDialogFormSystemModel"
      v-model="isVisibleDialogFormSystemModel"
      width="40%">
    <template #title>
      <h3 class="title-dialog">
        {{ titleDialogFormSystemModel }}
      </h3>
    </template>
    <FormSystemModel
        ref="formSystemModelRef"
        :loading-props="loading"
        :form-mode-props="formModeSystemModel"
        :xspr="24" :smpr="12" :mdpr="12" :lgpr="12"
        :system-model-data="systemModel"
        @submit-form-system-model="handleSubmitFormSystemModels"
        @close-for-dialog="handleCloseDialogFormSystemModel"
    />
  </el-dialog>
  <!--  //-->

  <!-- Dialog form system attribute -->
  <el-dialog
      @close="handleCloseDialogFormSystemAttribute"
      v-model="isVisibleDialogFormSystemAttribute"
      width="40%">
    <template #title>
      <h3 class="title-dialog">
        {{ titleDialogFormSystemAttribute }}
      </h3>
    </template>
    <FormSystemAttribute
        ref="formSystemAttributeRef"
        :loading-props="loading"
        :form-mode-props="formModeSystemAttribute"
        :xspr="24" :smpr="12" :mdpr="12" :lgpr="12"
        :system-attribute-data="systemAttribute"
        @submit-form-system-attribute="handleSubmitFormSystemAttributes"
        @close-for-dialog="handleCloseDialogFormSystemAttribute"
    />
  </el-dialog>
  </div>
</template>

<script lang="ts">
import {defineComponent, onMounted, reactive, ref} from 'vue'
import {MoreFilled, Delete, Document, Edit, Plus, RefreshRight, Search} from "@element-plus/icons";
import {ElNotification, FormInstance} from "element-plus";
import {useRouter} from "vue-router";
import {MODE_CREATE, MODE_EDIT, MODE_VIEW} from "@/constants/formMode";
import FormSystemModel from "@/views/main/dmAppParams/FormSystemModel.vue";
import FormSystemAttribute from "@/views/main/dmAppParams/FormSystemAttribute.vue";
import {createSystemModels, getSystemModelsById, getSystemModelsPage, updateSystemModels} from "@/api/systemModels";
import {createSystemAttributes, updateSystemAttributes} from "@/api/systemAttributes";
import TableSystemAttribute from "@/views/main/dmAppParams/TableSystemAttribute.vue";

export default defineComponent({
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
  computed: {
    Document() {
      return Document
    },
    Delete() {
      return Delete
    },
    Edit() {
      return Edit
    },
    Plus() {
      return Plus
    },
    RefreshRight() {
      return RefreshRight
    },
    Search() {
      return Search
    }
  },
  components: {
    TableSystemAttribute,
    FormSystemAttribute,
    FormSystemModel,
    MoreFilled,
  },
  setup() {
    const searchQuery = reactive({
      code: null,
      name: null,
    });
    const loading = ref(false);
    const ruleFormRef = ref<FormInstance>();
    const rules = {};
    const collapseName = ref('1');
    const tableData = ref([]);
    const pagination = reactive({
      current: 1,
      total: 0,
      pageSize: 10,
      size: 10,
    });

    const router = useRouter();
    const dialogVisibleAtt = ref(false);
    const dmAttForm = reactive(
        {
          valueCode: "",
          modelCode: "",
          valueLabel: "",
          description: ""
        });
    const validateForm = () => {
      ruleFormRef.value?.validate((valid) => {
        if (valid) {
          fetchData();
        }
      });
    }
    const formSystemAttributeRef = ref();
    const formSystemModelRef = ref();
    const isVisibleDialogFormSystemModel = ref(false);
    const titleDialogFormSystemModel = ref();
    const formModeSystemModel = ref(MODE_CREATE);

    const isVisibleDialogFormSystemAttribute = ref(false);
    const titleDialogFormSystemAttribute = ref();
    const modelCodeIsSelected = ref();
    const formModeSystemAttribute = ref(MODE_CREATE);
    const tableSystemAttributeRef = ref();

    const systemModel = reactive({
      id: null,
      code: null,
      name: null,
      description: null,
    });

    const systemAttribute = reactive({
      id: null,
      code: null,
      name: null,
      description: null,
      modelCode: null,
    })

    /** CU systemModel */
    const handleSubmitFormSystemModels = async (data: any) => {
      try {
        loading.value = true;
        if (formModeSystemModel.value === MODE_CREATE) {
          await createSystemModels(data);
          await fetchData();
          handleCloseDialogFormSystemModel();
          handleSuccess("Thêm danh mục mới thành công");
          formSystemModelRef.value?.resetForm();
        } else if (formModeSystemModel.value === MODE_EDIT) {
          await updateSystemModels(data.id, data);
          await fetchData();
          handleCloseDialogFormSystemModel();
          handleSuccess("Cập nhật danh mục thành công");
          formSystemModelRef.value?.resetForm();
        }
      } catch (error: any) {
        if (formModeSystemModel.value === MODE_CREATE) console.error('Có lỗi khi thêm danh mục mới: ', error);
        else if (formModeSystemModel.value === MODE_EDIT) console.error('Có lỗi khi sửa danh mục: ', error);
      } finally {
        loading.value = false;
      }
    }

    /** CU systemAttribute */
    const handleSubmitFormSystemAttributes = async (data: any) => {
      try {
        loading.value = true;
        if (formModeSystemAttribute.value === MODE_CREATE) {
          data.modelCode = modelCodeIsSelected.value;
          await createSystemAttributes(data);
          tableSystemAttributeRef.value?.fetchDmAttributesDataTable();
          handleCloseDialogFormSystemAttribute();
          handleSuccess("Thêm thuộc tính mới thành công");
          formSystemAttributeRef.value?.resetForm();
        }
      } catch (error: any) {
        if (formModeSystemAttribute.value === MODE_CREATE) console.error('Có lỗi khi thêm thuộc tính mới: ', error);
      } finally {
        loading.value = false;
      }
    }

    const handleOpenDialogFormSystemModel = (formMode: string, data: any) => {
      event.stopPropagation();
      resetSystemModel();
      isVisibleDialogFormSystemModel.value = true;
      if (formMode === MODE_CREATE) {
        formModeSystemModel.value = MODE_CREATE;
        titleDialogFormSystemModel.value = 'Thêm mới danh mục';
      } else if (formMode === MODE_EDIT) {
        fetchSystemModelsById(data.id);
        formModeSystemModel.value = MODE_EDIT;
        titleDialogFormSystemModel.value = 'Cập nhật danh mục';
      } else if (formMode === MODE_VIEW) {
        fetchSystemModelsById(data.id);
        formModeSystemModel.value = MODE_VIEW;
        titleDialogFormSystemModel.value = 'Chi tiết danh mục';
      }
    }

    const handleCloseDialogFormSystemModel = () => {
      isVisibleDialogFormSystemModel.value = false;
    }

    const handleOpenDialogFormSystemAttribute = (formMode: string, data: any) => {
      isVisibleDialogFormSystemAttribute.value = true;
      resetSystemAttribute();
      if (formMode === MODE_CREATE) {
        formModeSystemAttribute.value = MODE_CREATE;
        modelCodeIsSelected.value = data?.code;
        titleDialogFormSystemAttribute.value = 'Thêm mới thuộc tính';
      } else if (formMode === MODE_EDIT) {
        formModeSystemAttribute.value = MODE_EDIT;
        titleDialogFormSystemAttribute.value = 'Cập nhật thuộc tính';
      } else if (formMode === MODE_VIEW) {
        formModeSystemAttribute.value = MODE_VIEW;
        titleDialogFormSystemAttribute.value = 'Chi tiết thuộc tính';
      }
    }

    const handleCloseDialogFormSystemAttribute = () => {
      isVisibleDialogFormSystemAttribute.value = false;
    }

    const resetFormSearch = () => {
      Object.assign(searchQuery, {
        code: null,
        name: null,
      });
      fetchData();
    }

    const resetSystemModel = () => {
      systemModel.id = null;
      systemModel.code = null;
      systemModel.name = null;
      systemModel.description = null;
    }

    const resetSystemAttribute = () => {
      systemAttribute.id = null;
      systemAttribute.name = null;
      systemAttribute.code = null;
      systemAttribute.description = null;
      systemAttribute.modelCode = null;
    }

    /** Cập nhật danh mục*/
    const handleUpdateSystemModel = async(data: any) => {
      try {
        loading.value = true;
        // await
      } catch (error) {
        console.error('Có lỗi khi update systemModels: ', error);
      } finally {
        loading.value = false;
      }
    }

    const fetchData = async () => {
      try {
        loading.value = true;
        const param = {
          pageSize: pagination.pageSize,
          current: pagination.current - 1,
        }
        const {data} = await getSystemModelsPage(searchQuery, param);
        tableData.value = data.data.content;
        pagination.total = data.data.totalElements;
        pagination.pageSize = data.data.pageable.pageSize
      } catch (error) {
        console.error('Có lỗi khi lấy dmModels: ', error);
      } finally {
        loading.value = false;
      }
    }

    /** Lấy systemModel theo id*/
    const fetchSystemModelsById = async (id: any) => {
      try {
        loading.value = true;
        const response = await getSystemModelsById(id);
        Object.assign(systemModel, response.data.data);
      } catch (error) {
        console.error("Có lỗi khi lấy systemModel theo id: ", error);
      } finally {
        loading.value = false;
      }
    }

    const handlePageSize = (size: number) => {
      pagination.pageSize = size;
      pagination.current = 1
      fetchData();
    };

    const handleCurrent = (page: number) => {
      pagination.current = page;
      fetchData();
    };

    const handleSuccess = (message: string) => {
      ElNotification({
        title: 'Thành công',
        message,
        type: 'success',
        duration: 3000
      });
    };

    // Hook
    onMounted(() => {
      fetchData();
    });

    return {
      // Biến
      searchQuery,
      rules,
      collapseName,
      ruleFormRef,
      loading,
      tableData,
      dialogVisibleAtt,
      dmAttForm,
      pagination,
      isVisibleDialogFormSystemModel,
      isVisibleDialogFormSystemAttribute,
      titleDialogFormSystemModel,
      titleDialogFormSystemAttribute,
      formModeSystemModel,
      formModeSystemAttribute,
      systemModel,
      systemAttribute,
      modelCodeIsSelected,
      formSystemAttributeRef,
      formSystemModelRef,
      tableSystemAttributeRef,
      // Hàm
      resetFormSearch,
      validateForm,
      handleCloseDialogFormSystemModel,
      handleOpenDialogFormSystemModel,
      handleCloseDialogFormSystemAttribute,
      handleOpenDialogFormSystemAttribute,
      handlePageSize,
      handleCurrent,
      handleSubmitFormSystemModels,
      handleSubmitFormSystemAttributes,
      handleSuccess,
    }
  }
})
</script>

<style scoped lang="scss">
.container {
  padding: 15px;
  background-color: #ffffff;
  margin-bottom: 10px;
}

.box {
  background-color: var(--system-container-main-background);
  width: calc(100% - 30px);
  height: calc(100% - 30px);
  margin: 15px;
  display: flex;
  flex-direction: column;
}

.el-date-picker {
  width: 100% !important;
}

.demo-datetime-picker-icon {
  width: 100%;
}

.pagination {
  padding: 15px;
  display: flex;
  justify-content: end;
}

.feature-button {
  margin-right: 20px;
  margin-top: 10px;
}

.button-container-table {
  padding: 15px 15px 0 15px;
  display: flex;
  justify-content: space-between;
  align-content: center;
  align-items: center;
}

.action-container {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0;
}

.el-collapse {
  border: none;
}

.style-header-box {
  line-height: 24px;
  font-size: 14px;
  color: black
}

.toolbar {
  display: flex;
  align-items: center;
}

.form-item {
  margin-bottom: 18px;
}

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
