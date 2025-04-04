<template>
  <router-view v-slot="{ Component, route }">
    <component :is="Component" :key="route.fullPath"/>
    <div v-if="route.name === 'user-management'">
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
                      <h3 class="style-header-box">{{ $t('message.common.advancedSearch') }}</h3>
                    </template>
                    <el-row :gutter="24" style="margin: 0; width: 100%">
                      <el-col :xs="24" :md="6" :lg="8" class="form-item">
                        <el-form-item :size="'default'" :label="$t('message.menu.userManage.fullName')"
                                      prop="fullname"
                                      style="display: inherit">
                          <el-input v-model.lazy.trim="searchQuery.fullname" clearable></el-input>
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
                        <el-form-item :size="'default'" :label="$t('message.menu.userManage.tel')"
                                      prop="tel"
                                      style="display: inherit">
                          <el-input v-model="searchQuery.tel"
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
                      <el-col :xs="24" :md="6" :lg="8" class="form-item">
                        <el-form-item :size="'default'" :label="$t('message.menu.userManage.role')"
                                      prop="province"
                                      style="display: inherit">
                          <el-select v-model="searchQuery.role" clearable filterable
                                     :placeholder="$t('message.common.placeholderAll')"
                                     reserve-keyword
                                     style="width: 100%"
                          >
                            <el-option :value="null" :label="$t('message.common.placeholderAll')"/>
                            <el-option
                                v-for="item in areaData"
                                :key="item?.code"
                                :label="item.name"
                                :value="item?.code"
                            />
                          </el-select>
                        </el-form-item>
                      </el-col>
                    </el-row>
                    <div class="button-container center mb-5 mt-5">
                      <el-button :icon="RefreshRight" plain size="default" @click="resetForm">
                        {{ $t('message.common.reEnter') }}
                      </el-button>
                      <el-button :icon="Search" size="default" type="primary" color="var(--system-primary-color)">{{
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
                      <el-button :icon="Download" :loading="false"
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
                    <el-table v-loading="false"
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
                                         header-align="center" prop="dob" sortable/>
                        <el-table-column :label="$t('message.menu.userManage.gender')" align="center"
                                         header-align="center" prop="gender" sortable/>
                        <el-table-column :label="$t('message.menu.userManage.tel')" align="right"
                                         header-align="center" prop="tel" sortable/>
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
                                           type="danger"
                                           @click="navigatePartner(scope.row.dob)"
                                ></el-button>
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
import {defineComponent, reactive, ref} from 'vue'
import {Delete, Document, Download, Edit, Plus, Refresh, RefreshRight, Search, Tickets} from "@element-plus/icons";
import {deepTrim} from "@/utils/mixins/mixin";
import {validate} from "vee-validate";
import UserForm from "@/views/main/permissionManagement/userManage/userForm.vue";
import {useI18n} from 'vue-i18n';
import {useRouter} from "vue-router";

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
    const router = useRouter();
    const {t} = useI18n();
    const visibleDrawer = ref<boolean>(false)
    const tableData = ref([
      {
        username: 'vinhtq',
        fullName: 'Thiều Quang Vinh',
        dob: '19/08/2002',
        gender: 'Nam',
        tel: '0339874550',
        email: 'vinhtq@nodo.vn'
      },
      {
        username: 'vinhtq2',
        fullName: 'Thiều Quang Vinh 2',
        dob: '19/08/2002',
        gender: 'Nữ',
        tel: '0339874550',
        email: 'vinhtq2@nodo.vn'
      },
      {
        username: 'vinhtq3',
        fullName: 'Thiều Quang Vinh 3',
        dob: '19/08/2002',
        gender: 'Nam',
        tel: '0339874550',
        email: 'vinhtq3@nodo.vn'
      }
    ]);
    const titleDrawer = ref('')
    const collapseName = ref('1')
    const pagination = reactive({
      current: 1,
      total: 0,
      pageSize: 10,
      size: 10,
    });
    const searchQuery = reactive(
        {
          fullname: null,
          username: null,
          tel: null,
          email: null,
          role: null
        }
    );
    const rules = {}
    const areaData = ref([
      {code: 'admin', name: 'Admin'},
      {code: 'hr', name: 'HR'},
      {code: 'scheduler', name: 'Scheduler'},
      {code: 'am', name: 'AM'},
      {code: 'ac', name: 'AC'},
      {code: 'gv', name: 'GV'},
      {code: 'tg', name: 'TG'}
    ]);

    const resetForm = () => {
      Object.assign(searchQuery, {
        fullname: null,
        username: null,
        tel: null,
        email: null,
        role: null
      });
    }

    const handlePageSize = (size: number) => {
      pagination.pageSize = size;
    };

    const handleCurrent = (page: number) => {
      pagination.current = page;
    };

    const openDrawer = () => {
      visibleDrawer.value = true
      titleDrawer.value = t('message.menu.userManage.createUser')
    }

    const handleClose = () => {
      visibleDrawer.value = false;
    }

    const navigatePartner = (id?: any) => {
      router.push({name: 'user-detail', params: {data: id}});
    }

    return {
      titleDrawer,
      visibleDrawer,
      areaData,
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
      handleClose
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