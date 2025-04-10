<template>
  <router-view v-slot="{ Component, route }">
    <component :is="Component" :key="route.fullPath"/>
    <div v-if="route.name === 'user-management'">
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
                  <el-collapse-item name="2">
                    <template #title>
                      <h3 class="style-header-box">{{ $t('message.common.advancedSearch') }}</h3>
                    </template>
                    <el-row :gutter="24" style="margin: 0; width: 100%">
                      <el-col :xs="24" :md="6" :lg="8" class="form-item">
                        <el-form-item :size="'default'" :label="$t('message.menu.userManage.fullName')"
                                      prop="fullName"
                                      style="display: inherit">
                          <el-input v-model.lazy.trim="searchQuery.fullName" clearable></el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :md="6" :lg="8" class="form-item">
                        <el-form-item :size="'default'" :label="$t('message.menu.userManage.username')"
                                      prop="username"
                                      style="display: inherit">
                          <el-input v-model.lazy.trim="searchQuery.username" clearable></el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :md="6" :lg="8" class="form-item">
                        <el-form-item :size="'default'" :label="$t('message.menu.userManage.phone')"
                                      prop="phone"
                                      style="display: inherit">
                          <el-input v-model="searchQuery.phone"
                                    @blur="deepTrim(searchQuery)"
                                    clearable></el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :md="6" :lg="8" class="form-item">
                        <el-form-item :size="'default'" :label="$t('message.menu.userManage.email')"
                                      prop="email"
                                      style="display: inherit">
                          <el-input v-model.lazy.trim="searchQuery.email" clearable></el-input>
                        </el-form-item>
                      </el-col>
                    </el-row>
                    <div class="button-container center mb-5 mt-5">
                      <el-button :icon="RefreshRight" plain size="default" @click="resetForm">
                        {{ $t('message.common.reEnter') }}
                      </el-button>
                      <el-button :icon="Search" size="default" type="primary" color="var(--system-primary-color)"
                                 @click="handleSearch">{{
                          $t('message.common.search')
                        }}
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
                    <h3 class="style-header-box" style="width: 200px; text-align: left">
                      {{ $t('message.common.searchResult') }}
                      ({{ pagination.total }})</h3>
                    <div style="display: flex; justify-content: right; width: 100%; margin-right: 20px">
                      <el-button :icon="Download" :loading="loading"
                                 size="default" plain
                                 @click.stop="">{{ $t('message.common.export') }}
                      </el-button>
                      <el-button :icon="Plus" size="default"
                                 color="var(--system-primary-color)"
                                 type="primary"
                                 @click.stop="openDrawer">{{ $t('message.common.add') }}
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

                        <el-table-column :label="$t('message.menu.userManage.username')" align="left"
                                         width="150"
                                         header-align="center" prop="username" sortable/>
                        <el-table-column :label="$t('message.menu.userManage.fullName')" align="left"
                                         width="200"
                                         header-align="center" prop="fullName" sortable/>
                        <el-table-column :label="$t('message.menu.userManage.dob')" align="center"
                                         header-align="center" prop="dobStr" sortable/>
                        <el-table-column :label="$t('message.menu.userManage.gender')" align="left"
                                         header-align="center" prop="genderStr" sortable/>
                        <el-table-column :label="$t('message.menu.userManage.phone')" align="right"
                                         header-align="center" prop="phone" sortable/>
                        <el-table-column :label="$t('message.menu.userManage.email')" align="left"
                                         width="200"
                                         header-align="center" prop="email" sortable/>
                        <el-table-column :label="'Hành động'" align="center" fixed="right" width="100">
                          <template #default="scope">
                            <div class="action-container">
                              <el-tooltip content="Xem thông tin">
                                <el-button :icon="Document"
                                           style="width: 15px; margin: 0"
                                           text
                                           type="info"
                                           @click="navigatePartner(scope.row.id)"
                                ></el-button>
                              </el-tooltip>
                              <el-tooltip :content="'Xóa'" placement="top">
                                <el-button style="width: 15px; margin: 0" type="danger" text :icon="Delete"
                                           @click="delUser(scope.row)">
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
  <UserForm :visible="visibleDrawer" :title-drawer="titleDrawer" @close="handleClose"/>
</template>

<script lang="ts">
import {defineComponent, onMounted, reactive, ref} from 'vue'
import {Delete, Document, Download, Edit, Plus, Refresh, RefreshRight, Search, Tickets} from "@element-plus/icons";
import {deepTrim} from "@/utils/mixins/mixin";
import {validate} from "vee-validate";
import UserForm from "@/views/main/permissionManagement/userManage/userForm.vue";
import {useI18n} from 'vue-i18n';
import {useRouter} from "vue-router";
import {deleteUser, getUserList} from "@/api/user";
import {validateEmailInvalid} from "@/utils/validate/helpers";
import {ElMessage, ElMessageBox, FormInstance} from "element-plus";

export default defineComponent({
  computed: {
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
    }
  },
  components: {
    UserForm
  },
  methods: {
    deepTrim,
    validate
  },
  setup(props, ctx) {
    const ruleFormRef = ref<FormInstance>()
    let loading = ref(false)
    const router = useRouter();
    const {t} = useI18n();
    const visibleDrawer = ref<boolean>(false)
    const tableData = ref([]);
    const titleDrawer = ref('')
    const collapseName = ref('1')
    const pagination = ref({
      current: 1,
      total: 0,
      pageSize: 10,
      size: 10,
    });
    const searchQuery = reactive(
        {
          fullName: null,
          username: null,
          phone: null,
          email: null,
          role: null
        }
    );
    const rules = {
      phone: [
        {min: 10, max: 11, message: 'Số điện thoại tối đa 10-11 ký tự', trigger: ['blur', 'change']},
        {
          validator: (rule: any, value: any, callback: any) => {
            if (!value) {
              callback()
            }
            const phonePattern = /^0\d{9,10}$/;
            const containsLetters = /[a-zA-Z]/;

            if (containsLetters.test(value)) {
              callback(new Error('Số điện thoại chỉ cho phép nhập số'));
            } else if (!phonePattern.test(value)) {
              callback(new Error('Số điện thoại phải bắt đầu bằng 0'));
            } else {
              callback();
            }
          },
          trigger: ['blur', 'change']
        }
      ],
      username: [
        {max: 50, message: 'Tên đăng nhập tối đa 50 ký tự', trigger: ['blur', 'change']},
        {
          pattern: /^[a-zA-Z0-9]+$/,
          message: 'Tên đăng nhập chỉ được phép chứa chữ không dấu và số',
          trigger: ['blur', 'change']
        }
      ],
      email: [
        {min: 0, max: 100, message: 'Email tối đa 100 ký tự', trigger: ['blur', 'change']},
        {validator: (rule: any, value: any, callback: any) => validateEmailInvalid(rule, value, callback, t, 'Email')}
      ]
    }

    const delUser = async (user: any) => {
      ElMessageBox.confirm(
          'Bạn có chắc muốn xóa bản ghi này?',
          'Xác nhận',
          {
            confirmButtonText: 'Đồng ý',
            cancelButtonText: 'Hủy',
            type: 'warning',

          }
      )
          .then(async () => {
            try {
              loading.value = true;
              await deleteUser(user)
              loading.value = false;
              ElMessage({
                type: 'success',
                message: 'Xóa người dùng thành công!'
              })
              await getData();
            } catch (error) {
              console.error(error);
              ElMessage({
                type: 'error',
                message: 'Xóa người dùng thất bại!'
              })
            }
          })
          .catch((e) => {
            console.error(e)
          })
    }

    const handleSearch = () => {
      pagination.value = {
        current: 1,
        total: 0,
        pageSize: 10,
        size: 10,
      };

      ruleFormRef.value?.validate((valid) => {
        if (valid) {
          getData();
        } else {
          tableData.value = []
          console.warn('Validation failed!');
        }
      });
    }

    const getData = async () => {
      try {
        if (loading.value) return;
        loading.value = true;
        pagination.value.current = pagination.value.current - 1;
        const {data} = await getUserList(searchQuery, pagination.value);
        loading.value = false;
        tableData.value = data.data.content;
        pagination.value.current = data.data.number + 1;
        pagination.value.total = data.data.totalElements;
      } catch (e) {
        console.log(e)
        loading.value = false
      }
    }

    const resetForm = () => {
      Object.assign(searchQuery, {
        fullName: null,
        username: null,
        phone: null,
        email: null,
        role: null
      })
      getData()
    }

    const handlePageSize = (size: number) => {
      pagination.value.pageSize = size;
    };

    const handleCurrent = (page: number) => {
      pagination.value.current = page;
    };

    const openDrawer = () => {
      visibleDrawer.value = true
      titleDrawer.value = t('message.menu.userManage.createUser')
    }

    const handleClose = () => {
      visibleDrawer.value = false
      getData()
    }

    const navigatePartner = (id?: any) => {
      router.push({name: 'user-detail', params: {data: id}});
    }

    onMounted(() => {
      handleSearch()
    })

    return {
      ruleFormRef,
      loading,
      titleDrawer,
      visibleDrawer,
      rules,
      searchQuery,
      collapseName,
      pagination,
      tableData,
      navigatePartner,
      handlePageSize,
      handleCurrent,
      resetForm,
      openDrawer,
      handleClose,
      handleSearch,
      delUser
    }
  },
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