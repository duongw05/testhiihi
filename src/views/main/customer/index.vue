<template>
  <router-view v-slot="{ Component, route }">
    <component :is="Component" :key="route.fullPath"/>
    <div v-if="route.name === 'customer'">
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
                        <el-form-item :size="'default'" label="Tìm kiếm nhanh" prop="querySearch"
                                      style="display: inherit">
                          <el-input v-model.lazy.trim="searchQuery.querySearch" clearable
                                    :suffix-icon="Search"
                                    @blur="validateForm('BASIC')"
                                    placeholder="Tìm kiếm theo tên, số điện thoại, email hoặc số giấy tờ"></el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :md="24" :lg="24" class="form-item" v-show="false">
                        <el-form-item :size="'default'" label="Tìm kiếm nhanh" prop="barcode" style="display: inherit">
                          <el-input v-model.lazy.trim="searchQuery.querySearch" clearable
                                    :suffix-icon="Search"
                                    placeholder="Tìm kiếm theo tên, số điện thoại, email hoặc số giấy tờ"></el-input>
                        </el-form-item>
                      </el-col>
                    </el-row>
                    <!--                <div class="button-container center mb-5 mt-5">-->
                    <!--                  <el-button :icon="RefreshRight" plain size="default" @click="resetForm">Nhập lại</el-button>-->
                    <!--                  <el-button :icon="Search" size="default" type="primary" color="var(&#45;&#45;system-primary-color)"-->
                    <!--                             @click="validateForm('BASIC')">Tìm kiếm-->
                    <!--                  </el-button>-->
                    <!--                </div>-->
                  </el-collapse-item>
                </el-collapse>
              </div>
            </el-form>
          </div>
        </div>
      </div>
      <div class="search-extend">
        <div class="box">
          <div class="layout-container-form flex space-between" style="padding-bottom: 15px">
            <el-form
                ref="ruleFormRefAdvance"
                :model="searchQuery"
                :rules="rules"
                style="width: 100%"
            >
              <div class="demo-collapse no-border-collapse">
                <el-collapse :model-value="collapseName">
                  <el-collapse-item name="2">
                    <template #title>
                      <h3 class="style-header-box">Tìm kiếm nâng cao</h3>
                    </template>
                    <el-row :gutter="24" style="margin: 0; width: 100%">
                      <el-col :xs="24" :md="6" :lg="8" class="form-item">
                        <el-form-item :size="'default'" label="Loại khách hàng" prop="customerType"
                                      style="display: inherit">
                          <el-select v-model="searchQuery.customerType" clearable filterable
                                     placeholder="Tất cả"
                                     reserve-keyword
                                     style="width: 100%"
                          >
                            <el-option :value="null" label="Tất cả"/>
                            <el-option
                                v-for="item in appParams.fetchCustomerType()"
                                :key="item?.id"
                                :label="item.valueLabel"
                                :value="item?.valueCode"
                            />
                          </el-select>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :md="6" :lg="8" class="form-item">
                        <el-form-item :size="'default'" label="Tên khách hàng" prop="name"
                                      style="display: inherit">
                          <el-input v-model.lazy.trim="searchQuery.name" clearable></el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :md="6" :lg="8" class="form-item">
                        <el-form-item :size="'default'" label="Số điện thoại" prop="tel"
                                      style="display: inherit">
                          <el-input v-model="searchQuery.tel"
                                    @blur="deepTrim(searchQuery)"
                                    clearable></el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :md="6" :lg="8" class="form-item">
                        <el-form-item :size="'default'" label="Email khách hàng" prop="email"
                                      style="display: inherit">
                          <el-input v-model.lazy.trim="searchQuery.email" clearable></el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :md="6" :lg="8" class="form-item">
                        <el-form-item :size="'default'" label="Tỉnh/Thành phố" prop="province"
                                      style="display: inherit">
                          <el-select v-model="searchQuery.province" clearable filterable
                                     placeholder="Tất cả"
                                     reserve-keyword
                                     style="width: 100%"
                          >
                            <el-option :value="null" label="Tất cả"/>
                            <el-option
                                v-for="item in areaData"
                                :key="item?.areaCode"
                                :label="item.name"
                                :value="item?.areaCode"
                            />
                          </el-select>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :md="6" :lg="8" class="form-item">
                        <el-form-item :size="'default'" label="Mã số thuế" prop="taxCode"
                                      style="display: inherit">
                          <el-input v-model.lazy.trim="searchQuery.taxCode" clearable></el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :md="6" :lg="8" class="form-item">
                        <el-form-item :size="'default'" label="Ngày tạo" prop="date"
                                      style="display: inherit">
                          <el-date-picker
                              v-model="searchQuery.date"
                              type="daterange"
                              format="DD/MM/YYYY"
                              value-format="DD/MM/YYYY"
                              start-placeholder="Từ ngày"
                              end-placeholder="Đến ngày"
                          />
                        </el-form-item>
                      </el-col>
                    </el-row>
                    <div class="button-container center mb-5 mt-5">
                      <el-button :icon="RefreshRight" plain size="default" @click="resetForm">Nhập lại</el-button>
                      <el-button :icon="Search" size="default" type="primary" color="var(--system-primary-color)"
                                 @click="validateForm('ADVANCE')">Tìm kiếm
                      </el-button>
                    </div>
                  </el-collapse-item>
                </el-collapse>
              </div>
            </el-form>
          </div>
        </div>
      </div>
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
                      <el-button :icon="Download" :loading="loading"
                                 size="default" plain @click="exportExcelCustomer()">Xuất báo cáo
                      </el-button>
                      <el-button :icon="Plus" size="default"
                                 color="var(--system-primary-color)"
                                 type="primary" @click="navigatePartner('create')">Thêm
                        mới
                      </el-button>
                    </div>
                  </template>
                  <div class="layout-container-table">
                    <el-table v-loading="loading"
                              :data="tableData"
                              :header-cell-style="{background:'#DDE1E6', color: 'rgba(0,0,0,0.8)'}"
                              :row-style="(data: any) => data.row.status === 2 ? {background: '#FFF8F8', color: '#424242'} : {}"
                              border
                              style="width: 100%"
                    >
                      <template #default="scope">
                        <el-table-column
                            align="center"
                            header-align="center"
                            label="STT"
                            type="index"
                            width="80"
                        >
                          <template #default="scope">
                            {{ (pagination.current - 1) * pagination.pageSize + scope.$index + 1 }}
                          </template>
                        </el-table-column>

                        <el-table-column :label="'Tên khách hàng'" align="left"
                                         header-align="left" prop="name" sortable/>
                        <el-table-column :label="'Loại khách hàng'" align="left" sortable width="300"
                                         header-align="left" prop="customerTypeName"/>
                        <el-table-column :label="'Email khách hàng'" align="left"
                                         header-align="left" prop="email"/>
                        <el-table-column :label="'Số điện thoại'" align="left"
                                         header-align="left" prop="tel"/>
                        <el-table-column :label="'Ngày tạo'" align="center"
                                         header-align="center" prop="createdDate">
                        </el-table-column>
                        <el-table-column :label="'Hành động'" align="center" fixed="right" width="150">
                          <template #default="scope">
                            <div class="action-container">
                              <el-tooltip content="Xem chi tiết">
                                <el-button :icon="Document"
                                           style="width: 15px; margin: 0"
                                           text type="danger"
                                           @click="navigatePartner('view', scope.row.customerId)"
                                ></el-button>
                              </el-tooltip>
                              <el-tooltip content="Cập nhật">
                                <el-button :icon="Edit"
                                           style="width: 15px; margin: 0"
                                           text
                                           type="danger"
                                           @click="navigatePartner('edit', scope.row.customerId)"
                                ></el-button>
                              </el-tooltip>
                              <el-tooltip content="Xóa">
                                <el-button
                                    :icon="Delete" style="width: 15px; margin: 0;" text
                                    type="danger"
                                    @click="removePartner(scope.row)"
                                >
                                </el-button>
                              </el-tooltip>
                            </div>
                          </template>
                        </el-table-column>
                      </template>
                    </el-table>
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
                  </div>
                </el-collapse-item>
              </el-collapse>
            </div>
          </div>
        </div>
      </div>
    </div>
  </router-view>
</template>


<script lang="ts">
import {defineComponent, h, onMounted, reactive, ref} from 'vue'
import {useRouter} from "vue-router";
import {
  Delete,
  Document,
  Download,
  Edit,
  MoreFilled,
  Plus,
  Printer,
  Refresh,
  RefreshRight,
  Search,
  Tickets
} from "@element-plus/icons";
import {ElMessageBox, ElNotification, FormInstance} from "element-plus";
import {validate} from "vee-validate";
import {hasPermission} from "@/router/permission/front";
import {deepTrim, downloadBlob} from "@/utils/mixins/mixin";
import {appParams} from "@/constants/app-param";
import {delCustomer, exportCustomer, searchCustomer} from "@/api/customer";
import {getProvince} from "@/api/area";


export default defineComponent({
  components: {MoreFilled, Printer, Tickets},
  methods: {
    deepTrim,
    validate,
    hasPermission
  },
  computed: {
    appParams() {
      return appParams
    },
    RefreshRight() {
      return RefreshRight
    },
    Download() {
      return Download
    },
    Document() {
      return Document
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
    Refresh() {
      return Refresh
    },
    Plus() {
      return Plus
    },
    Tickets() {
      return Tickets
    },
  },

  setup() {
    const searchQuery = reactive(
        {
          querySearch: null,
          customerType: null,
          name: null,
          tel: null,
          email: null,
          province: null,
          date: [],
          fromDate: null,
          toDate: null,
          status: null,
          taxCode: null,
          docsType: null,
          type: null
        }
    );
    const checkTaxCode = () => {
      if (searchQuery.taxCode) {
        const value = appParams.listIdentification();
        searchQuery.docsType = value.filter(item => item.valueLabel === 'Mã số thuế')[0]?.valueCode;
      }
    }

    const router = useRouter();
    const formMode = ref<'create' | 'edit' | 'view'>('create');
    const loading = ref(false);
    const tableData = ref([]);
    const areaData = ref([]);
    const ruleFormRef = ref<FormInstance>();
    const ruleFormRefAdvance = ref<FormInstance>();
    const formatDate = ref('')
    const collapseName = ref('1')

    const rules = {
      name: [
        {
          max: 50,
          message: 'Tên khách hàng không được vượt quá 50 ký tự',
        }
      ],
      tel: [
        {
          max: 15,
          message: 'Số điện thoại không được vượt quá 15 ký tự',
        }
      ],
      email: [
        {
          max: 50,
          message: 'Email khách hàng không được vượt quá 50 ký tự',
        }
      ],
    };

    const pagination = reactive({
      current: 1,
      total: 0,
      pageSize: 10,
      size: 10,
    });

    const navigatePartner = (action: string, id?: any) => {
      switch (action) {
        case 'create':
          router.push({name: 'customer-create', params: {mode: 'create'}});
          break;
        case 'view':
          router.push({name: 'customer-view', params: {data: id, mode: 'view'}});
          break;
        case 'edit':
          router.push({name: 'customer-update', params: {data: id, mode: 'edit'}});
          break;
        default:
          console.error('Invalid action:', action);
      }
    };

    const validateForm = (type: any) => {
      searchQuery.type = type
      if (type === 'BASIC') {
        ruleFormRef.value?.validate((valid) => {
          if (valid) {
            fetchData();
          }
        });
      } else {
        ruleFormRefAdvance.value?.validate((valid) => {
          if (valid) {
            fetchData();
          }
        });
      }
    }

    const fetchData = async () => {
      try {
        loading.value = true;
        pagination.current = Math.max(pagination.current, 1); // Đảm bảo currentPage không dưới 1
        pagination.current--;
        if (searchQuery.date && searchQuery.date.length > 0) searchQuery.fromDate = searchQuery.date[0]
        if (searchQuery.date && searchQuery.date.length > 1) searchQuery.toDate = searchQuery.date[1]
        checkTaxCode();
        const {data} = await searchCustomer(searchQuery, pagination);
        tableData.value = data.data.content;
        pagination.total = data.data.totalElements;
        pagination.current = data.data.pageable.pageNumber + 1;
        pagination.pageSize = data.data.pageable.pageSize;
        loading.value = false;
      } catch (error) {
        console.error(error);
        loading.value = false;
      }
    };

    const exportExcelCustomer = async () => {
      event.stopPropagation();
      try {
        loading.value = true
        checkTaxCode();
        let axiosResponse = await exportCustomer(searchQuery)
        if (axiosResponse.status === 200) {
          downloadBlob(axiosResponse.data, 'Báo cáo khách hàng.xlsx');
        }
      } catch (error) {
        loading.value = false
        console.error("Error exporting the store list:", error);
      } finally {
        await fetchData()
        loading.value = false
      }
    };

    const fetchArea = async () => {
      try {
        loading.value = true;
        const {data} = await getProvince();
        areaData.value = data.data;
        loading.value = false;
      } catch (error) {
        console.error(error);
        loading.value = false;
      }
    }

    const removePartner = (row: object) => {
      ElMessageBox({
        title: 'Xóa đối tác',
        message: h('p', null, [
          h('span', {style: 'font-size: 15px'}, "Bạn có muốn xoá đối tác " + row?.name + "? "),
        ]),
        showCancelButton: true,
        confirmButtonText: 'Xóa',
        cancelButtonText: 'Hủy',
        customClass: 'my-custom-messagebox',
      }).then(async () => {
        try {
          let axiosResponse = await delCustomer(row?.customerId);
          ElNotification({
            title: 'Thành công',
            message: axiosResponse.data?.data.message,
            type: 'success',
            duration: 3 * 1000
          })
          await fetchData();
        } catch (error) {
          console.error(error);
        }
      })
          .catch((e) => {
            console.error(e)
          })
    }

    const resetForm = () => {
      Object.assign(searchQuery, {
        querySearch: null,
        customerType: null,
        name: null,
        tel: null,
        email: null,
        province: null,
        date: [],
        fromDate: null,
        toDate: null,
        status: null,
        taxCode: null,
      });
      fetchData();
    }

    const handlePageSize = (size: number) => {
      pagination.pageSize = size;
      fetchData();
    };

    const handleCurrent = (page: number) => {
      pagination.current = page;
      fetchData();
    };


    onMounted(() => {
      if (router.currentRoute.value.name === 'customer') {
        fetchData();
        fetchArea();
        document.addEventListener('keypress', (e) => {
          if (e.key === 'Enter') {
            searchQuery.type = 'BASIC'
            fetchData()
          }
        })
      }
    });

    return {
      searchQuery,
      formMode,
      loading,
      tableData,
      areaData,
      ruleFormRef,
      ruleFormRefAdvance,
      rules,
      pagination,
      formatDate,
      collapseName,
      // Hàm
      navigatePartner,
      validateForm,
      fetchData,
      fetchArea,
      resetForm,
      removePartner,
      handlePageSize,
      handleCurrent,
      exportExcelCustomer
    }
  }
})

</script>

<style lang="scss" scoped>
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
  justify-content: space-between; /* Căn về bên phải */
  align-content: center;
  align-items: center;
}

.action-container {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0px;
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
  margin-bottom: 18px; /* Khoảng cách giữa các hàng theo chiều dọc */
}
</style>
