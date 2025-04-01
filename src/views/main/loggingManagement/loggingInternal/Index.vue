<template>
  <!--   Vùng tìm kiếm-->
  <div class="search-extend">
    <div class="box">
      <div class="layout-container-form flex space-between" style="padding-bottom: 15px">
        <el-form
            ref="ruleFormRef"
            style="width: 100%"
            :model="searchParams"
        >
          <div class="demo-collapse no-border-collapse">
            <el-collapse :model-value="collapseName">
              <el-collapse-item name="1">
                <template #title>
                  <h3 class="style-header-box">Thông tin tìm kiếm</h3>
                </template>
                <el-row :gutter="24" style="margin: 0; width: 100%">
                  <el-col :xs="24" :md="12" :lg="12" class="form-item">
                    <el-form-item :size="'default'" label="Endpoint" prop="codeAtt" style="display: inherit">
                      <el-input v-model="searchParams.endpoint"
                                clearable
                                :placeholder="'Nhập giá trị'"></el-input>
                    </el-form-item>

                  </el-col>
                  <el-col :xs="24" :md="24" :lg="12" class="form-item">
                    <el-form-item :size="'default'" label="Trạng thái phản hồi" prop="nameAtt" style="display: inherit">
                      <el-select filterable v-model="searchParams.responseStatus" placeholder="Tất cả">
                        <el-option :value="null">Tất cả</el-option>
                        <el-option v-for="status in httpStatusList" :key="status.code" :value="status.code">
                          {{ status.code }} - {{ status.message }}
                        </el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>


                </el-row>
                <div class="button-container center mb-5 mt-5">
                  <el-button :loading="loading" @click="handleReset" :icon="RefreshRight" plain size="default">Nhập
                    lại
                  </el-button>
                  <el-button :loading="loading" @click="handleSearch" :icon="Search" size="default" type="primary"
                             color="var(--system-primary-color)">Tìm kiếm
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
              </template>
              <div class="layout-container-table flex space-between">
                <el-table v-loading="loading"
                          :data="tableData"
                          :header-cell-style="{background:'#DDE1E6', color: 'rgba(0,0,0,0.8)'}"
                          border
                          style="width: 100%; margin-bottom: 20px">
                  <template #default="scope">
                    <el-table-column align="center"
                                     header-align="center"
                                     label="STT"
                                     width="60px">
                      <template #default="scope">
                        {{ (pagination.current - 1) * pagination.pageSize + scope.$index + 1 }}
                      </template>
                    </el-table-column>


                    <el-table-column header-align="left" width="500px" label="API Endpoint" prop="endpoint"/>
                    <el-table-column header-align="right" align="right" min-width="120" label="Thời gian phản hồi" prop="durationMs">
                      <template #default="scope">
                        {{ scope.row.durationMs + ' ms' }}
                      </template>
                    </el-table-column>

                    <el-table-column header-align="left" label="HTTP Method" prop="httpMethod"/>
                    <el-table-column header-align="right" align="right" label="Trạng thái phản hồi"
                                     prop="responseStatus"/>
                    <el-table-column header-align="left" label="Người tạo" prop="createdBy"/>
                    <el-table-column header-align="center" align="center" label="Ngày tạo" prop="createdDate"/>
                    <el-table-column header-align="center" label="Hành động" align="center" fixed="right" width="120">
                      <template #default="scope">
                        <div class="action-container" style="display: flex; justify-content: center; padding: 5px;">
                          <el-tooltip :content="'Xem chi tiết'" placement="top">
                            <el-button
                                @click="handleOpenDialogFormApplicationLogInternal(scope.row)"
                                :icon="Document"
                                style="width: 15px; margin: 0;" text
                                type="danger"
                            >
                            </el-button>
                          </el-tooltip>
                        </div>
                      </template>
                    </el-table-column>
                  </template>
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
    <!-- Dialog form application log internal -->
    <el-dialog
        @close="handleCloseDialogFormApplicationLogInternal"
        v-model="isVisibleDialogFormApplicationLogInternal"
        width="70%">
      <template #title>
        <h3 class="title-dialog">
          {{ titleDialogFormApplicationLogInternal }}
        </h3>
      </template>
      <FormApplicationLogInternal
          :loading-props="loading"
          :form-mode-props="formModeApplicationLogInternal"
          :xspr="24" :smpr="12" :mdpr="12" :lgpr="12"
          :application-log-internal-data="applicationLogInternalById"
          @close-for-dialog="handleCloseDialogFormApplicationLogInternal"
      />
    </el-dialog>
  </div>
</template>

<script lang="ts">
import {defineComponent, onMounted, reactive, ref} from 'vue'
import {Document, RefreshRight, Search} from "@element-plus/icons";
import {MODE_VIEW} from "@/constants/formMode";
import {getLoggingInternalById, searchPageLoggingInternal} from "@/api/loggingInternal";
import FormApplicationLogInternal from "@/views/main/loggingManagement/loggingInternal/FormApplicationLogInternal.vue";

export default defineComponent({
  name: "Index",
  methods: {
    MODE_VIEW() {
      return MODE_VIEW
    }
  },
  components: {FormApplicationLogInternal},
  computed: {
    Document() {
      return Document
    },
    Search() {
      return Search
    },
    RefreshRight() {
      return RefreshRight
    }
  },
  setup() {
    const searchParams = reactive({
      responseStatus: null,
      endpoint: null,
    });
    const collapseName = ref('1');
    const loading = ref(false);
    const tableData = ref([]);
    const pagination = reactive({
      current: 1,
      total: 0,
      pageSize: 10,
      size: 10,
    });

    const applicationLogInternalById = reactive({
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

    const httpStatusList = ref([
      {code: 100, message: "Continue"},
      {code: 101, message: "Switching Protocols"},
      {code: 102, message: "Processing"},
      {code: 200, message: "OK"},
      {code: 201, message: "Created"},
      {code: 202, message: "Accepted"},
      {code: 203, message: "Non-Authoritative Information"},
      {code: 204, message: "No Content"},
      {code: 205, message: "Reset Content"},
      {code: 206, message: "Partial Content"},
      {code: 207, message: "Multi-Status"},
      {code: 208, message: "Already Reported"},
      {code: 226, message: "IM Used"},
      {code: 300, message: "Multiple Choices"},
      {code: 301, message: "Moved Permanently"},
      {code: 302, message: "Found"},
      {code: 303, message: "See Other"},
      {code: 304, message: "Not Modified"},
      {code: 305, message: "Use Proxy"},
      {code: 307, message: "Temporary Redirect"},
      {code: 308, message: "Permanent Redirect"},
      {code: 400, message: "Bad Request"},
      {code: 401, message: "Unauthorized"},
      {code: 402, message: "Payment Required"},
      {code: 403, message: "Forbidden"},
      {code: 404, message: "Not Found"},
      {code: 405, message: "Method Not Allowed"},
      {code: 406, message: "Not Acceptable"},
      {code: 407, message: "Proxy Authentication Required"},
      {code: 408, message: "Request Timeout"},
      {code: 409, message: "Conflict"},
      {code: 410, message: "Gone"},
      {code: 411, message: "Length Required"},
      {code: 412, message: "Precondition Failed"},
      {code: 413, message: "Payload Too Large"},
      {code: 414, message: "URI Too Long"},
      {code: 415, message: "Unsupported Media Type"},
      {code: 416, message: "Range Not Satisfiable"},
      {code: 417, message: "Expectation Failed"},
      {code: 418, message: "I'm a teapot"},
      {code: 422, message: "Unprocessable Entity"},
      {code: 423, message: "Locked"},
      {code: 424, message: "Failed Dependency"},
      {code: 426, message: "Upgrade Required"},
      {code: 428, message: "Precondition Required"},
      {code: 429, message: "Too Many Requests"},
      {code: 431, message: "Request Header Fields Too Large"},
      {code: 451, message: "Unavailable For Legal Reasons"},
      {code: 500, message: "Internal Server Error"},
      {code: 501, message: "Not Implemented"},
      {code: 502, message: "Bad Gateway"},
      {code: 503, message: "Service Unavailable"},
      {code: 504, message: "Gateway Timeout"},
      {code: 505, message: "HTTP Version Not Supported"},
      {code: 506, message: "Variant Also Negotiates"},
      {code: 507, message: "Insufficient Storage"},
      {code: 508, message: "Loop Detected"},
      {code: 510, message: "Not Extended"},
      {code: 511, message: "Network Authentication Required"}
    ]);

    const isVisibleDialogFormApplicationLogInternal = ref(false);
    const titleDialogFormApplicationLogInternal = ref();
    const formModeApplicationLogInternal = ref(MODE_VIEW);

    const handleOpenDialogFormApplicationLogInternal = (data: any) => {
      isVisibleDialogFormApplicationLogInternal.value = true;
      formModeApplicationLogInternal.value = MODE_VIEW;
      titleDialogFormApplicationLogInternal.value = 'Chi tiết Logs ';
      fetchApplicationLogRequestById(data.id);
    }

    const handleCloseDialogFormApplicationLogInternal = () => {
      isVisibleDialogFormApplicationLogInternal.value = false;
    }

    const fetchData = async () => {
      try {
        loading.value = true;
        const param = {
          pageSize: pagination.pageSize,
          current: pagination.current - 1,
        }
        const {data} = await searchPageLoggingInternal(searchParams, param);
        tableData.value = data.data.content;
        pagination.total = data.data.totalElements;
        pagination.pageSize = data.data.pageable.pageSize
      } catch (error) {
        console.error('Có lỗi khi lấy application-log-internal: ', error);
      } finally {
        loading.value = false;
      }
    };

    const fetchApplicationLogRequestById = async (id: any) => {
      try {
        loading.value = true;
        const response = await getLoggingInternalById(id);
        Object.assign(applicationLogInternalById, response.data.data);
      } catch (error) {

      } finally {
        loading.value = false;
      }
    }

    const handlePageSize = (size: number) => {
      pagination.pageSize = size;
      pagination.current = 1;
      fetchData();
    };

    const handleCurrent = (page: number) => {
      pagination.current = page;
      fetchData();
    };

    const handleReset = () => {
      searchParams.responseStatus = null;
      searchParams.endpoint = null;
      fetchData();
    }

    const handleSearch = () => {
      fetchData();
    }

    onMounted(() => {
      fetchData();
    })

    return {
      // Biến
      collapseName,
      pagination,
      loading,
      tableData,
      searchParams,
      httpStatusList,
      applicationLogInternalById,
      isVisibleDialogFormApplicationLogInternal,
      titleDialogFormApplicationLogInternal,
      formModeApplicationLogInternal,
      // Hàm
      handlePageSize,
      handleCurrent,
      fetchData,
      handleReset,
      handleSearch,
      fetchApplicationLogRequestById,
      handleOpenDialogFormApplicationLogInternal,
      handleCloseDialogFormApplicationLogInternal
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