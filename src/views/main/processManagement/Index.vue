<template>
  <div v-loading="loading">
    <!--   Vùng tìm kiếm-->
    <div class="search-extend">
      <div class="box">
        <div class="layout-container-form flex space-between" style="padding-bottom: 15px">
          <el-form
              ref="ruleFormRef"
              :model="searchParam"
              :rules="rulesFormSearch"
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
                      <el-form-item :size="'default'" label="Class thực thi" prop="jobClass" style="display: inherit">
                        <el-select v-model="searchParam.jobClass" placeholder="Chọn giá trị" style="width: 100%"
                                   filterable clearable
                        >
                          <el-option
                              v-for="item in appParams.fetchClassRunJob()"
                              :key="item?.id"
                              :label="item?.valueLabel"
                              :value="item?.valueCode"
                          />
                        </el-select>
                      </el-form-item>

                    </el-col>
                    <el-col :xs="24" :md="24" :lg="12" class="form-item">
                      <el-form-item :size="'default'" label="Trạng thái" prop="jobStatus" style="display: inherit">
                        <el-select v-model="searchParam.jobStatus" placeholder="Chọn giá trị" style="width: 100%"
                                   filterable clearable
                        >
                          <el-option
                              v-for="item in appParams.fetchProcessJobStatus()"
                              :key="item?.id"
                              :label="item?.valueLabel"
                              :value="item?.valueCode"
                          />
                        </el-select>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <div class="button-container center mb-5 mt-5">
                    <el-button :icon="RefreshRight" plain size="default" @click="resetFormSearch">Nhập lại</el-button>
                    <el-button @click="handleSearch" :icon="Search" size="default" type="primary"
                               color="var(--system-primary-color)">
                      Tìm kiếm
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
                               type="primary"
                               @click="handleOpenDialogFormScheduleJobDetail(MODE_CREATE(), null)"
                    >
                      Thêm mới cấu hình tiến trình
                    </el-button>
                  </div>
                </template>
                <div class="layout-container-table flex space-between">
                  <el-table
                      :data="tableData"
                      :header-cell-style="{background:'#DDE1E6', color: 'rgba(0,0,0,0.8)'}"
                      border
                      style="width: 100%; margin-bottom: 20px">
                    <template #default="scope">
                      <el-table-column align="center"
                                       header-align="center"
                                       label="STT"
                                       width="75px">
                        <template #default="scope">
                          {{ (pagination.current - 1) * pagination.pageSize + scope.$index + 1 }}
                        </template>
                      </el-table-column>
<!--                      <el-table-column header-align="center" label="Key tiến trình" prop="jobKey"/>-->
                      <el-table-column header-align="center" label="Tên tiến trình" prop="jobName"/>
                      <el-table-column header-align="center" label="Nhóm" prop="jobGroup"/>
<!--                      <el-table-column header-align="center" align="center" label="Chạy vào lúc"-->
<!--                                       prop="timeStartProcess"/>-->
<!--                      <el-table-column header-align="center" align="center" label="Xử lý dữ liệu vào lúc"-->
<!--                                       prop="timeStartData"/>-->
                      <el-table-column header-align="center" align="center" label="Cron expression"
                                       prop="cronExpression"/>
                      <el-table-column header-align="center" align="center" label="Class thực thi" prop="jobClass"/>
                      <el-table-column header-align="center" align="center" label="Ngày tạo" prop="createdDate"/>
                      <el-table-column header-align="center" label="Trạng thái" prop="jobStatus">
                        <template #default="scope">
                         {{ appParams.fetchProcessJobStatus().find(e => e.valueCode == scope.row.jobStatus)?.valueLabel || '' }}
                        </template>
                      </el-table-column>
                      <el-table-column header-align="center" label="Mô tả" prop="descInfo"/>
                      <el-table-column header-align="center" label="Hành động" align="center" fixed="right" width="200">
                        <template #default="scope">
                          <div class="action-container" style="display: flex; justify-content: center; padding: 5px;">

                            <el-tooltip :content="'Xem chi tiết'" placement="top">
                              <el-button
                                  :icon="Document" style="width: 15px; margin: 0;" text
                                  type="danger"
                                  @click="handleOpenDialogFormScheduleJobDetail(MODE_VIEW(), scope.row)"
                              >
                              </el-button>
                            </el-tooltip>

                            <el-tooltip :content="'Cập nhật'" placement="top">
                              <el-button
                                  :icon="Edit" style="width: 15px; margin: 0;" text
                                  type="danger"
                                  @click="handleOpenDialogFormScheduleJobDetail(MODE_EDIT(), scope.row)"
                              >
                              </el-button>
                            </el-tooltip>

                            <el-tooltip :content="'Xóa'" placement="top">
                              <el-button
                                  :icon="Delete" style="width: 15px; margin: 0;" text
                                  type="danger"
                                  @click="handleDeleteScheduleJobDetail(scope.row.jobId)"
                              >
                              </el-button>
                            </el-tooltip>

                            <el-tooltip :content="'Chạy ngay'" placement="top">
                              <el-button
                                  :icon="CaretRight" style="width: 15px; margin: 0;" text
                                  type="danger"
                                  @click="handleStartNowScheduleJobDetail(scope.row.jobId)"
                              >
                              </el-button>
                            </el-tooltip>

                            <el-tooltip :content="'Tiếp tục'" placement="top">
                              <el-button
                                  :icon="VideoPlay" style="width: 15px; margin: 0;" text
                                  type="danger"
                                  @click="handleResumeScheduleJobDetail(scope.row.jobId)"
                              >
                              </el-button>
                            </el-tooltip>

                            <el-tooltip :content="'Tạm dừng'" placement="top">
                              <el-button
                                  :disabled="scope.row.jobStatus == STATUS_SCHEDULE_JOB_DETAIL.PAUSED"
                                  :icon="VideoPause" style="width: 15px; margin: 0;" text
                                  type="danger"
                                  @click="handlePauseScheduleJobDetail(scope.row.jobId)"
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
    </div>
    <!--Kết thúc phần bảng dữ liệu-->

    <!-- Dialog form scheduleJobDetail -->
    <el-dialog
        @close="handleCloseDialogFormScheduleJobDetail"
        v-model="isVisibleDialogFormScheduleJobDetail"
        width="50%">
      <template #title>
        <h3 class="title-dialog">
          {{ titleDialogFormScheduleJobDetail }}
        </h3>
      </template>
      <FormScheduleJobDetail
          ref="formScheduleJobDetailRef"
          :loading-props="loading"
          :form-mode-props="formModeScheduleJobDetail"
          :xspr="24" :smpr="12" :mdpr="12" :lgpr="12"
          :data-form="scheduleJobDetail"
          @submit-form-system-attribute="handleSubmitFormScheduleJobDetail"
          @close-for-dialog="handleCloseDialogScheduleJobDetail"
      />
    </el-dialog>
  </div>
</template>

<script lang="ts">
import {defineComponent, onMounted, reactive, ref} from 'vue'
import {
  Delete,
  Edit,
  RefreshRight,
  Search,
  Document,
  Plus,
  VideoPlay,
  CircleClose,
  CaretRight, VideoPause
} from "@element-plus/icons";
import TableDmAttribute from "@/views/main/dmAppParams/TableSystemAttribute.vue";
import {MODE_CREATE, MODE_EDIT, MODE_VIEW} from "@/constants/formMode";
import {
  createScheduleJobDetail,
  deleteScheduleJobDetail,
  getScheduleJobDetailById, pauseScheduleJobDetail, resumeScheduleJobDetail,
  searchScheduleJobDetails,
  startNowScheduleJobDetail, updateScheduleJobDetail
} from "@/api/scheduleJobDetail";
import FormScheduleJobDetail from "@/views/main/processManagement/FormScheduleJobDetail.vue";
import {ElMessageBox, ElNotification} from "element-plus";
import {appParams} from "@/constants/app-param";
import {STATUS_SCHEDULE_JOB_DETAIL} from "@/constants/scheduleJobDetail";

export default defineComponent({
  name: "Index",
  methods: {
    MODE_VIEW() {
      return MODE_VIEW
    },
    MODE_CREATE() {
      return MODE_CREATE
    },
    MODE_EDIT() {
      return MODE_EDIT
    }
  },
  components: {FormScheduleJobDetail, TableDmAttribute},
  computed: {
    STATUS_SCHEDULE_JOB_DETAIL() {
      return STATUS_SCHEDULE_JOB_DETAIL
    },
    appParams() {
      return appParams
    },
    VideoPause() {
      return VideoPause
    },
    CaretRight() {
      return CaretRight
    },
    CircleClose() {
      return CircleClose
    },
    VideoPlay() {
      return VideoPlay
    },
    Plus() {
      return Plus
    },
    Delete() {
      return Delete
    },
    Edit() {
      return Edit
    },
    Search() {
      return Search
    },
    RefreshRight() {
      return RefreshRight
    },
    Document() {
      return Document
    }
  },
  setup() {
    const searchParam = reactive({
      jobStatus: null,
      jobClass: null,
    });
    const loading = ref(false);
    const isVisibleDialogFormScheduleJobDetail = ref(false);
    const formModeScheduleJobDetail = ref();
    const scheduleJobDetail = reactive({
      jobId: null,
      jobName: '',
      jobGroup: '',
      jobStatus: '',
      jobClass: '',
      jobKey: '',
      cronExpression: '',
      descInfo: '',
      interfaceName: '',
      repeatTime: null,
      cronJob: null,
      timeStartProcess: '',
      timeStartData: '',
      status: '',
    });

    const formScheduleJobDetailRef = ref();
    const collapseName = ref('1');
    const tableData = ref();
    const rulesFormSearch = ref();
    const titleDialogFormScheduleJobDetail = ref("");

    const resetFormSearch = () => {
      searchParam.jobClass = null;
      searchParam.jobStatus = null;
      fetchData();
    }

    const handleOpenDialogFormScheduleJobDetail = (modeForm: any, data: any) => {
      event.stopPropagation();
      isVisibleDialogFormScheduleJobDetail.value = true;
      if (modeForm === MODE_CREATE) {
        resetScheduleJobDetail();
        formModeScheduleJobDetail.value = MODE_CREATE;
        titleDialogFormScheduleJobDetail.value = "Thêm mới cấu hình tiến trình";
        formScheduleJobDetailRef.value?.resetForm();
      } else if (modeForm === MODE_EDIT) {
        formModeScheduleJobDetail.value = MODE_EDIT;
        titleDialogFormScheduleJobDetail.value = "Cập nhật cấu hình tiến trình";
        fetchScheduleJobDetailById(data.jobId);
      } else {
        formModeScheduleJobDetail.value = MODE_VIEW;
        titleDialogFormScheduleJobDetail.value = "Chi tiết cấu hình tiến trình";
        fetchScheduleJobDetailById(data.jobId);
      }
    }

    const handleCloseDialogFormScheduleJobDetail = () => {
      isVisibleDialogFormScheduleJobDetail.value = false;

    }

    const handleCloseDialogScheduleJobDetail = () => {
      isVisibleDialogFormScheduleJobDetail.value = false;
    }

    const handleSubmitFormScheduleJobDetail = async(data: any) => {
        if(formModeScheduleJobDetail.value === MODE_CREATE) {
          await submitCreateScheduleJobDetail(data);
        }else if(formModeScheduleJobDetail.value === MODE_EDIT) {
          await submitUpdateScheduleJobDetail(data);
        }
    }

    const submitCreateScheduleJobDetail = async(data: any) => {
        try {
          loading.value = true;
          await createScheduleJobDetail(data);
          handleSuccess("Thêm cấu hình tiến trình mới thành công");
          handleCloseDialogFormScheduleJobDetail();
          await fetchData();
          formScheduleJobDetailRef.value?.resetForm();
        } catch (error){
          console.error("Có lỗi xảy ra trong khi thêm mới cấu hình tiến trình: ", error);
        }finally {
          loading.value = false;
        }
    }

    const submitUpdateScheduleJobDetail = async(data: any) => {
      try {
        loading.value = true;
        await updateScheduleJobDetail(data);
        handleSuccess("Cập nhật cấu hình tiến trình thành công");
        handleCloseDialogFormScheduleJobDetail();
        await fetchData();
      } catch (error){
        console.error("Có lỗi xảy ra trong khi cập nhật cấu hình tiến trình: ", error);
      }finally {
        loading.value = false;
      }
    }

    /** Tai cau hinh tien trinh theo id*/
    const fetchScheduleJobDetailById = async (id: any) => {
      try {
        loading.value = true;
        const {data} = await getScheduleJobDetailById(id);
        Object.assign(scheduleJobDetail, data.data);
      } catch (error) {
        console.error("Có lỗi xảy ra trong khi tải chi tiết cấu hình tiến trình: ", error);
      } finally {
        loading.value = false;
      }
    }

    /**Xoa cau hinh tien trinh*/
    const handleDeleteScheduleJobDetail = async (idScheduleJobDetail: any) => {
      ElMessageBox.confirm(
          'Bạn có muốn xóa cấu hình tiến trình này không?',
          'Xác nhận',
          {
            confirmButtonText: "Đồng ý",
            cancelButtonText: "Hủy",
            type: 'warning',
            customClass: 'my-custom-messagebox',
          }
      ).then(async () => {
        try {
          loading.value = true;
          await deleteScheduleJobDetail(idScheduleJobDetail);
          await fetchData();
          handleSuccess("Xóa cấu hình tiến trình thành công");
        } catch(error) {
          console.error("Có lỗi xảy ra trong xóa cấu hình tiến trình: ", error);
        } finally {
          loading.value = false;
        }
      }).catch((e) => {
        console.error(e)
      })
    }

    /***/
    const handleStartNowScheduleJobDetail = async (idScheduleJobDetail: any) => {
      ElMessageBox.confirm(
          'Bạn có muốn chạy ngay cấu hình tiến trình này không?',
          'Xác nhận',
          {
            confirmButtonText: "Đồng ý",
            cancelButtonText: "Hủy",
            type: 'warning',
            customClass: 'my-custom-messagebox',
          }
      ).then(async () => {
        try {
          loading.value = true;
          await startNowScheduleJobDetail(idScheduleJobDetail);
          handleSuccess("Chạy ngay cấu hình tiến trình thành công");
          await fetchData();
        } catch(error) {
          console.error("Có lỗi xảy ra trong khi khởi chạy ngay cấu hình tiến trình: ", error);
        } finally {
          loading.value = false;
        }
      }).catch((e) => {
        console.error(e)
      })
    }

    const handlePauseScheduleJobDetail = async (idScheduleJobDetail: any) => {
      ElMessageBox.confirm(
          'Bạn có muốn tạm dừng chạy cấu hình tiến trình này không?',
          'Xác nhận',
          {
            confirmButtonText: "Đồng ý",
            cancelButtonText: "Hủy",
            type: 'warning',
            customClass: 'my-custom-messagebox',
          }
      ).then(async () => {
        try {
          loading.value = true;
          await pauseScheduleJobDetail(idScheduleJobDetail);
          handleSuccess("Tạm dừng chạy cấu hình tiến trình thành công");
          await fetchData();
        } catch(error) {
          console.error("Có lỗi xảy ra trong khi tạm dừng chạy cấu hình tiến trình: ", error);
        } finally {
          loading.value = false;
        }
      }).catch((e) => {
        console.error(e)
      })
    }

    const handleResumeScheduleJobDetail = async (idScheduleJobDetail: any) => {
      ElMessageBox.confirm(
          'Bạn có muốn tiếp tục chạy cấu hình tiến trình này không?',
          'Xác nhận',
          {
            confirmButtonText: "Đồng ý",
            cancelButtonText: "Hủy",
            type: 'warning',
            customClass: 'my-custom-messagebox',
          }
      ).then(async () => {
        try {
          loading.value = true;
          await resumeScheduleJobDetail(idScheduleJobDetail);
          handleSuccess("Tiếp tục chạy cấu hình tiến trình thành công");
          await fetchData();
        } catch(error) {
          console.error("Có lỗi xảy ra trong khi tiếp tục chạy cấu hình tiến trình: ", error);
        } finally {
          loading.value = false;
        }
      }).catch((e) => {
        console.error(e)
      })
    }

    const handleSuccess = (message: string) => {
      ElNotification({
        title: 'Thành công',
        message,
        type: 'success',
        duration: 3000
      });
    };

    /** Tai du lieu tim kiem cau hinh tien trinh*/
    const fetchData = async () => {
      try {
        loading.value = true;
        const param = {
          pageSize: pagination.pageSize,
          current: pagination.current - 1,
        }
        const {data} = await searchScheduleJobDetails(searchParam, param);
        tableData.value = data.data.content;
        pagination.total = data.data.totalElements;
        pagination.pageSize = data.data.pageable.pageSize;
      } catch (error) {
        console.error("Có lỗi xảy ra trong khi tải danh sách cấu hình tiến trình: ", error);
      } finally {
        loading.value = false;
      }
    }

    const pagination = reactive({
      current: 1,
      total: 0,
      pageSize: 10,
      size: 10,
    });

    const handlePageSize = (size: number) => {
      pagination.pageSize = size;
      pagination.current = 1
      fetchData();
    };

    const handleCurrent = (page: number) => {
      pagination.current = page;
      fetchData();
    };

    const handleSearch = () => {
      fetchData();
    }

    const resetScheduleJobDetail = () => {
      Object.assign(scheduleJobDetail, {
        jobId: null,
        jobName: '',
        jobGroup: '',
        jobStatus: '',
        jobClass: '',
        jobKey: '',
        cronExpression: '',
        descInfo: '',
        interfaceName: '',
        repeatTime: null,
        cronJob: null,
        timeStartProcess: '',
        timeStartData: '',
        status: '',
      });
    }

    // Hook
    onMounted(() => {
      fetchData();
    });

    return {
      //Biến
      searchParam,
      rulesFormSearch,
      collapseName,
      pagination,
      loading,
      tableData,
      titleDialogFormScheduleJobDetail,
      isVisibleDialogFormScheduleJobDetail,
      formModeScheduleJobDetail,
      scheduleJobDetail,
      formScheduleJobDetailRef,
      //Hàm
      resetFormSearch,
      handlePageSize,
      handleCurrent,
      fetchData,
      handleCloseDialogFormScheduleJobDetail,
      handleSubmitFormScheduleJobDetail,
      handleCloseDialogScheduleJobDetail,
      handleOpenDialogFormScheduleJobDetail,
      handleSearch,
      resetScheduleJobDetail,
      handleResumeScheduleJobDetail,
      handlePauseScheduleJobDetail,
      handleStartNowScheduleJobDetail,
      handleDeleteScheduleJobDetail,
      submitUpdateScheduleJobDetail,
      submitCreateScheduleJobDetail,
      handleSuccess
    }
  },
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