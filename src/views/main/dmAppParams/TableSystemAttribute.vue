<template>
  <div>
    <!--  Bảng -->
    <el-table :header-cell-style="{background: 'rgba(221,225,230,0.34)'}"
              v-loading="loading"
              :data="dmAttributesDataTable"
              border
              style="margin-top: 5px; box-shadow: 0 2px 5px rgba(0,0,0,0.1);">
      <el-table-column type="expand"
                       default-expand-all>
        <template #default="scope">
          <div style="padding-left: 48px; border-radius: 4px;">
            <TableSystemValues ref="tableSystemValuesRef" :dmAttributeCode="scope.row.code"
                               :dmModelsCode="props.dmModelsCode"/>
          </div>
        </template>
      </el-table-column>

      <el-table-column
          align="center"
          header-align="center"
          label="STT"
          type="index"
          width="75px"
      >
        <template #default="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column header-align="center" label="Mã thuộc tính" prop="code"/>
      <el-table-column header-align="center" label="Tên thuộc tính" prop="name"/>
      <el-table-column header-align="center" label="Mô tả" prop="description"/>

      <el-table-column :label="''" align="center" fixed="right" width="150">
        <template #default="scope">
          <div class="action-container" style="display: flex; justify-content: center; padding: 5px;">
            <el-tooltip :content="'Thêm giá trị'" placement="top">
              <el-button type="danger"
                         style="width: 15px; margin: 0;"
                         text
                         :icon="Plus"
                         @click="handleOpenDialogFormSystemValue(MODE_CREATE(), scope.row)">
              </el-button>
            </el-tooltip>

            <span style="border-left: 1px solid #ccc; height: 20px; margin: 0 10px;"></span>

            <el-tooltip :content="'Xem chi tiết thuộc tính'" placement="top">
              <el-button
                  :icon="Document" style="width: 15px; margin: 0;" text
                  type="danger"
                  @click="handleOpenDialogFormSystemAttribute(MODE_VIEW(), scope.row)"
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
                          @click="handleOpenDialogFormSystemAttribute(MODE_EDIT(), scope.row)"
                      >
                        Cập nhật thuộc tính
                      </el-button>
                    </el-dropdown-item>
                    <el-dropdown-item>
                      <el-button
                          class="custom-button"
                          :icon="Delete" text
                          type="danger"
                      >
                        Xóa thuộc tính
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
          ref="formSystemValueRef"
          :loading-props="loading"
          :form-mode-props="formModeSystemValue"
          :xspr="24" :smpr="12" :mdpr="12" :lgpr="12"
          :system-model-data="systemValue"
          @submit-form-system-value="handleSubmitFormSystemValue"
          @close-for-dialog="handleCloseDialogFormSystemValue"
      />
    </el-dialog>

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
  </teleport>

</template>

<script lang="ts">
import {defineComponent, onMounted, reactive, ref} from "vue";
import {MoreFilled, Delete, Edit, Plus, Document} from "@element-plus/icons";
import {ElNotification} from "element-plus";
import {createNewDmValues} from "@/api/systemValues";
import {getAttributes, getSystemAttributeById, updateSystemAttributes} from "@/api/systemAttributes";
import {MODE_CREATE, MODE_EDIT, MODE_VIEW} from "@/constants/formMode";
import FormSystemAttribute from "@/views/main/dmAppParams/FormSystemAttribute.vue";
import FormSystemValue from "@/views/main/dmAppParams/FormSystemValue.vue";
import TableSystemValues from "@/views/main/dmAppParams/TableSystemValues.vue";

export default defineComponent({
  methods: {
    MODE_EDIT() {
      return MODE_EDIT
    },
    MODE_VIEW() {
      return MODE_VIEW
    },
    MODE_CREATE() {
      return MODE_CREATE
    }
  },
  computed: {
    Document() {
      return Document
    },
    Edit() {
      return Edit
    },
    Delete() {
      return Delete
    }
  },
  components: {
    TableSystemValues,
    FormSystemValue,
    FormSystemAttribute,
    MoreFilled
  },
  props: {
    dmModelsCode: String,
  },
  setup(props) {
    const dmAttributesDataTable = ref([]);
    const loading = ref(false);
    const tableSystemValuesRef = ref();
    const formSystemAttributeRef = ref();
    const formSystemValueRef = ref();


    // for sysstemValue
    const systemValue = reactive({
      valueCode: "",
      attCode: "",
      modelCode: "",
      valueLabel: "",
      description: ""
    });
    const isVisibleDialogFormSystemValue = ref(false);
    const titleDialogFormSystemValue = ref();
    const formModeSystemValue = ref(MODE_CREATE);
    const attCodeIsSelected = ref();
    const handleOpenDialogFormSystemValue = (formMode: string, data: any) => {
      isVisibleDialogFormSystemValue.value = true;
      if (formMode === MODE_CREATE) {
        attCodeIsSelected.value = data.code;
        titleDialogFormSystemValue.value = 'Thêm mới giá trị';
      } else if (formMode === MODE_EDIT) {
        titleDialogFormSystemValue.value = 'Cập nhật giá trị';
      } else if (formMode === MODE_VIEW) {
        titleDialogFormSystemValue.value = 'Chi tiết giá trị';
      }
    }

    const handleCloseDialogFormSystemValue = () => {
      isVisibleDialogFormSystemValue.value = false;
    }

    /** thêm mới dmValues */
    const handleSubmitFormSystemValue = async (data: any) => {
      console.log('dataCreateNew: ', data);
      try {
        loading.value = true;
        data.modelCode = props.dmModelsCode;
        data.attCode = attCodeIsSelected.value;
        await createNewDmValues(data);
        tableSystemValuesRef.value?.fetchDmValuesDataTable();
        handleCloseDialogFormSystemValue();
        handleSuccess("Thêm giá trị mới thành công");
        formSystemValueRef.value?.resetForm();
      } catch (error) {
        console.error('Có lỗi trong handleCreateNewDmValues', error);
      } finally {
        loading.value = false;
      }
    }
    // end f sv

    // for systemAttribute
    const systemAttribute = reactive({
      id: null,
      code: null,
      name: null,
      description: null,
      modelCode: null,
    })
    const isVisibleDialogFormSystemAttribute = ref(false);
    const titleDialogFormSystemAttribute = ref();
    const formModeSystemAttribute = ref(MODE_CREATE);
    const handleOpenDialogFormSystemAttribute = async (formMode: string, data: any) => {
      isVisibleDialogFormSystemAttribute.value = true;
      if (formMode === MODE_EDIT) {
        formModeSystemAttribute.value = MODE_EDIT;
        await fetchDmAttributesByID(data.id);
        titleDialogFormSystemAttribute.value = 'Cập nhật thuộc tính';
      } else if (formMode === MODE_VIEW) {
        formModeSystemAttribute.value = MODE_VIEW;
        await fetchDmAttributesByID(data.id);
        titleDialogFormSystemAttribute.value = 'Chi tiết thuộc tính';
      }
    }

    const handleCloseDialogFormSystemAttribute = () => {
      isVisibleDialogFormSystemAttribute.value = false;
    }

    const handleSubmitFormSystemAttributes = async (data: any) => {
      try {
        loading.value = true;
        if (formModeSystemAttribute.value === MODE_EDIT) {
          await updateSystemAttributes(data.id, data);
          await fetchDmAttributesDataTable();
          handleCloseDialogFormSystemAttribute();
          handleSuccess("Cập nhật thuộc tính thành công");
          formSystemAttributeRef.value?.resetForm();
        }
      } catch (error: any) {
        if (formModeSystemAttribute.value === MODE_EDIT) console.error('Có lỗi khi cập nhật thuộc tính: ', error);
      } finally {
        loading.value = false;
      }
    }
    // end f sa


    /**Tải dữ liệu cho bảng systemAttribute*/
    const fetchDmAttributesDataTable = async () => {
      try {
        loading.value = true;
        const response = await getAttributes(props.dmModelsCode);
        dmAttributesDataTable.value = response.data.data;
      } catch (error) {
        console.error('Có lỗi trong fetchDmAttributesDataTable(): ', error);
      } finally {
        loading.value = false;
      }
    };

    /**laasy  systemAttribute theo id*/
    const fetchDmAttributesByID = async (id: any) => {
      try {
        loading.value = true;
        const response = await getSystemAttributeById(id);
        Object.assign(systemAttribute, response.data.data);
      } catch (error) {
        console.error('Có lỗi khi lay systemAttribute theo id: ', error);
      } finally {
        loading.value = false;
      }
    };

    const handleSuccess = (message: string) => {
      ElNotification({
        title: 'Thành công',
        message,
        type: 'success',
        duration: 3000
      });
    };

    /**Khởi tạo dữu liệu*/
    onMounted(() => {
      fetchDmAttributesDataTable();
    });

    return {
      // Biến
      dmAttributesDataTable,
      isVisibleDialogFormSystemValue,
      titleDialogFormSystemValue,
      formModeSystemValue,
      loading,
      systemValue,
      tableSystemValuesRef,
      attCodeIsSelected,
      formModeSystemAttribute,
      titleDialogFormSystemAttribute,
      isVisibleDialogFormSystemAttribute,
      systemAttribute,
      formSystemValueRef,
      formSystemAttributeRef,
      // Hàm
      handleOpenDialogFormSystemValue,
      handleCloseDialogFormSystemValue,
      handleSubmitFormSystemValue,
      handleCloseDialogFormSystemAttribute,
      handleOpenDialogFormSystemAttribute,
      handleSubmitFormSystemAttributes,
      handleSuccess,
      fetchDmAttributesDataTable,
      props,
      Plus,
    }
  }
})
/**Props*/

</script>


<style scoped lang="scss">
.dialog-footer {
  text-align: right;
}

.action-container {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0;
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