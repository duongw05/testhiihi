<template>
  <el-dialog
      v-model="visible"
      width="60%"
      @close="closeDialog(ruleFormRef)"
      :loading="loading"
      class="dialog-form-investigation-request-all"
  >
    <template #title>
      <h3 class="title-dialog">
        Chi tiết lịch sử
      </h3>
    </template>
    <div class="box">
      <div class="layout-container" style="margin: 0 0 15px; width: 100%; max-height: 500px">
        <div class="layout-container-table" style="padding: 0; max-height: 500px">
          <el-table style="width: 100%"
                    v-loading="loading"
                    ref="multipleTableRef"
                    :data="tableData"
                    :header-cell-style="{background:'#DDE1E6', color: 'rgba(0,0,0,0.8)'}"
                    border
          >
            <template #default="scope">
              <el-table-column
                  label="STT"
                  width="80"
                  align="center"
                  header-align="center"
              >
                <template #default="scope">
                  {{ scope.$index + 1 }}
                </template>
              </el-table-column>

              <el-table-column prop="colDisplayName" :label="'Thuộc tính'"
                               header-align="center" align="left">
                <template #default="scope">
                  <span>{{
                      scope.row.tableDisplayName + " - " +
                  scope.row.colDisplayName}}</span>
                </template>
              </el-table-column>
              <el-table-column prop="oldDisplayValue" :label="'Giá trị cũ'"
                               header-align="center" align="left">
                <template #default="scope">
                  <span @click="download(scope.row, scope.row.oldDisplayValue)">  {{
                      scope.row.oldDisplayValue
                    }} </span>
                </template>
              </el-table-column>
              <el-table-column prop="newDisplayValue" :label="'Giá trị mới'"
                               header-align="center" align="left">
                <template #default="scope">
                  <span @click="download(scope.row, scope.row.newDisplayValue)">  {{
                      scope.row.newDisplayValue
                    }} </span>
                </template>
              </el-table-column>
            </template>
          </el-table>
        </div>
      </div>
      <div class="dialog-footer" style="text-align: center; margin-top: 20px">
        <el-button @click="closeDialog(ruleFormRef)" type="primary"
                   color="var(--system-primary-color)"
                   size="default"
                   :icon="CircleCloseFilled" style="min-width: 100px">Đóng
        </el-button>
      </div>
    </div>
  </el-dialog>
</template>

<script lang="ts">
import {defineComponent, ref, watch} from 'vue';
import {ElTable, FormInstance} from 'element-plus';
import {downloadBlob} from "@/utils/mixins/mixin";
import {downloadFileByName} from "@/api/uploadFile";
import {CircleCloseFilled} from "@element-plus/icons";

export default defineComponent({
  computed: {
    CircleCloseFilled() {
      return CircleCloseFilled
    }
  },
  props: {
    isView: Boolean,
    data: Array,
  },
  methods: {},
  setup(props, {emit}) {
    const ruleFormRef = ref<FormInstance>()
    let loading = ref(false)
    const tableData = ref([])
    let visible: any = ref(false);

    const closeDialog = (formEl: FormInstance | undefined) => {
      setTimeout(() => {
        visible.value = false;
      }, 100)
      emit('close');
    };

    const download = async (ele: any, name: any) => {
      if (ele.colName === 'FILE_NAME' && name) {
          let axiosResponse = await downloadFileByName({id : ele.rowId, name : name});
        // Nếu phản hồi thành công và nhận được dữ liệu
        if (axiosResponse.status === 200) {
          downloadBlob(axiosResponse.data, name);
        }
      }
    };


    watch(() => props.isView, async () => {
      visible.value = true;
      tableData.value = props.data
    });

    return {
      loading,
      closeDialog,
      visible,
      ruleFormRef,
      tableData,
      download
    };
  },
});
</script>
<style lang="scss" scoped>
.container {
  padding: 15px;
  background-color: #ffffff;
  margin-bottom: 10px;
}

.pagination {
  padding: 15px;
  display: flex;
  justify-content: end;
}

.title-dialog {
  font-size: 18px;
  line-height: 24px;
  color: white;
  margin-bottom: 5px;
  margin-top: 5px;
}
</style>