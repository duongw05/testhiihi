<template>
  <router-view v-slot="{ Component, route }">
    <component :is="Component" :key="route.fullPath"/>
    <div v-if="route.name === 'group-management'">
      <el-row>
        <el-col :xs="24" :md="12" :lg="12">
          <div class="search-extend">
            <div class="box">
              <div class="layout-container-form flex space-between" style="padding-bottom: 15px">
                <div class="demo-collapse no-border-collapse" style="width: 100%">
                  <el-collapse :model-value="collapseName">
                    <el-collapse-item name="1">
                      <template #title>
                        <h3 class="style-header-box">{{ $t('message.menu.groupManage.roleGroupList') }}</h3>
                      </template>
                      <div style="display: flex; justify-content: right; width: 100%; margin-right: 20px">
                        <el-col :xs="24" :md="6" :lg="8" class="form-item" style="margin-right:2%">
                          <el-form-item :size="'default'"
                                        prop="quickSearch"
                                        style="display: inherit">
                            <el-input v-model.lazy.trim="searchQuery.quickSearch" @input="fetchDataGroup"
                                      :placeholder="$t('message.menu.groupManage.quickSearch')" clearable></el-input>
                          </el-form-item>
                        </el-col>
                        <el-button :icon="Plus" size="default"
                                   color="var(--system-primary-color)"
                                   type="primary"
                                   @click.stop="openDrawer()">{{ $t('message.common.add') }}
                        </el-button>
                      </div>
                      <div class="layout-container-table">
                        <el-table v-loading="false"
                                  :data="tableData"
                                  :header-cell-style="{background:'#DDE1E6', color: 'rgba(0,0,0,0.8)'}"
                                  :row-style="(data: any) => data.row.status === 2 ? {background: '#FFF8F8', color: '#424242'} : {}"
                                  border
                                  @row-click="clickRowGetData"
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

                            <el-table-column :label="$t('message.menu.groupManage.groupCode')" align="right"
                                             width="150"
                                             header-align="right" prop="code" sortable/>
                            <el-table-column :label="$t('message.menu.groupManage.groupName')" align="right"
                                             width="200"
                                             header-align="right" prop="name" sortable/>
                            <el-table-column :label="$t('message.menu.groupManage.description')" align="right"
                                             header-align="right" prop="description" sortable/>
                            <el-table-column :label="'Hành động'" align="center" fixed="right" width="100">
                              <template #default="scope">
                                <div class="action-container">
                                  <el-tooltip :content="$t('message.common.authorize')">
                                    <el-button :icon="Setting" circle
                                               style="width: 15px; margin: 0"
                                               text type="primary"
                                    ></el-button>
                                  </el-tooltip>
                                  <el-tooltip :content="$t('message.common.update')">
                                    <el-button :icon="Edit" circle
                                               style="width: 15px; margin: 0"
                                               text type="primary"
                                               @click="openDrawer(scope.row)"
                                    ></el-button>
                                  </el-tooltip>
                                  <el-tooltip :content="$t('message.common.del')">
                                    <el-button :icon="Delete" circle
                                               style="width: 15px; margin: 0"
                                               text type="danger"
                                               @click="delGroup(scope.row)"
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
        </el-col>
        <el-col :xs="24" :md="12" :lg="12">
          <div class="search-extend">
            <div class="box">
              <div class="layout-container-form flex space-between" style="padding-bottom: 15px">
                <div class="demo-collapse no-border-collapse" style="width: 100%">
                  <el-collapse :model-value="collapseName">
                    <el-collapse-item name="2">
                      <template #title>
                        <h3 class="style-header-box">{{ $t('message.menu.groupManage.userList') }}</h3>
                      </template>
                      <div style="display: flex; justify-content: right; width: 100%; margin-right: 20px">
                        <el-col :xs="24" :md="6" :lg="8" class="form-item" style="margin-right:2%">
                          <el-form-item :size="'default'"
                                        prop="quickSearchUser"
                                        style="display: inherit">
                            <el-input v-model.lazy.trim="searchQuery.quickSearchUser" @input="getUserInGroup"
                                      :placeholder="$t('message.menu.groupManage.quickSearch')" clearable></el-input>
                          </el-form-item>
                        </el-col>
                        <el-button :icon="Plus" size="default"
                                   color="var(--system-primary-color)"
                                   type="primary" :disabled="!groupId"
                                   @click.stop="openUserListDrawer">{{ $t('message.common.add') }}
                        </el-button>
                      </div>
                      <div class="layout-container-table">
                        <el-table v-loading="false"
                                  :data="tableDataUser"
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
                                {{ (paginationUser.current - 1) * paginationUser.pageSize + scope.$index + 1 }}
                              </template>
                            </el-table-column>

                            <el-table-column :label="$t('message.menu.userManage.username')" align="right"
                                             width="150"
                                             header-align="right" prop="fullName" sortable/>
                            <el-table-column :label="$t('message.menu.userManage.fullName')" align="right"
                                             width="200"
                                             header-align="right" prop="username" sortable/>
                            <el-table-column :label="$t('message.menu.userManage.email')" align="right"
                                             header-align="right" prop="email" sortable/>
                            <el-table-column :label="'Hành động'" align="center" fixed="right" width="100">
                              <template #default="scope">
                                <div class="action-container">
                                  <el-tooltip :content="$t('message.common.del')">
                                    <el-button :icon="Delete" circle
                                               style="width: 15px; margin: 0"
                                               text type="danger"
                                               @click="delUserInGroup(scope.row)"
                                    ></el-button>
                                  </el-tooltip>
                                </div>
                              </template>
                            </el-table-column>
                          </template>
                        </el-table>
                        <div class="pagination">
                          <el-pagination
                              :current-page="paginationUser.current"
                              :page-size="paginationUser.pageSize"
                              :page-sizes="[10, 20, 50]"
                              :small="true"
                              :total="paginationUser.total"
                              background
                              layout="total, sizes, prev, pager, next, jumper"
                              @size-change="handlePageSizeUser"
                              @current-change="handleCurrentUser"
                          />
                        </div>
                      </div>
                    </el-collapse-item>
                  </el-collapse>
                </div>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>

  </router-view>
  <RoleGroupListForm :visible="visibleDrawer" :initial-data="initialData" :title-drawer="titleDrawer" :mode="typeAction"
                     @close="handleClose" @success="fetchDataGroup"/>
  <UserListForm :visible="userListVisibleDrawer" :group-id="groupId" :title-drawer="titleUerListDrawer"
                @success="getUserInGroup" @close="handleCloseUserListDrawer"/>
</template>

<script lang="ts">
import {defineComponent, onMounted, reactive, ref} from 'vue'
import {
  Delete,
  Document,
  Download,
  Edit,
  Plus,
  Refresh,
  RefreshRight,
  Search,
  Setting,
  Tickets,
  View
} from "@element-plus/icons";
import {deepTrim, showConfirmDialog} from "@/utils/mixins/mixin";
import {validate} from "vee-validate";
import RoleGroupListForm from "@/views/main/permissionManagement/groupManage/RoleGroupListForm.vue";
import UserListForm from "@/views/main/permissionManagement/groupManage/UserListForm.vue";
import {useI18n} from 'vue-i18n';
import {useRouter} from "vue-router";
import {deleteGroup, quickSearchGroup, searchUserInGroup, deleteGroupUser} from "@/api/roleManage/groupUser";
import {ElMessageBox} from "element-plus";

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
    View() {
      return View
    },
    Setting() {
      return Setting
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
    RoleGroupListForm,
    UserListForm
  },
  methods: {
    deepTrim,
    validate
  },
  setup(props, ctx) {
    const router = useRouter();
    const {t} = useI18n();
    const visibleDrawer = ref<boolean>(false)
    const userListVisibleDrawer = ref<boolean>(false)
    const tableData = ref([]);
    const tableDataUser = ref([]);
    let initialData = ref({})
    const titleDrawer = ref('')
    const typeAction = ref('')
    const groupId = ref<number | null>(null);
    const loading = ref(false)
    const titleUerListDrawer = ref('')
    const collapseName = ref(['1', '2'])
    const pagination = reactive({
      current: 1,
      total: 0,
      pageSize: 10,
      size: 10,
    });
    const paginationUser = reactive({
      current: 1,
      total: 0,
      pageSize: 10,
      size: 10,
    });
    const searchQuery = reactive(
        {
          quickSearch: null,
          quickSearchUser: null
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
        quickSearch: null
      })
    }

    const handlePageSize = (size: number) => {
      pagination.pageSize = size;
    };

    const handleCurrent = (page: number) => {
      pagination.current = page;
    };
    const handlePageSizeUser = (size: number) => {
      paginationUser.pageSize = size;
    };

    const handleCurrentUser = (page: number) => {
      paginationUser.current = page;
    };

    const openDrawer = (dataRow?: any) => {
      initialData.value = {...dataRow}
      visibleDrawer.value = true
      titleDrawer.value = t(dataRow ? 'message.menu.groupManage.updateGroup' : 'message.menu.groupManage.createGroup')
      typeAction.value = dataRow ? 'edit' : 'add'
    }
    const clickRowGetData = (dataRow?: any) => {
      groupId.value = dataRow.id
      getUserInGroup(dataRow.id);
    }

    const handleClose = () => {
      visibleDrawer.value = false;
    }
    const openUserListDrawer = () => {
      userListVisibleDrawer.value = true
      titleUerListDrawer.value = t('message.menu.groupManage.createUserToRole')
    }

    const handleCloseUserListDrawer = () => {
      userListVisibleDrawer.value = false;
    }

    const navigatePartner = (id?: any) => {
      router.push({name: 'user-detail', params: {data: id}});
    }

    const delGroup = async (value: any) => {
      await showConfirmDialog(
          t("message.common.delConfirm"),
          t("message.common.delName", {name: t('message.menu.groupManage.groupRole')}),
          t("message.common.del"),
          t("message.common.close"),
          async () => {
            try {
              loading.value = true;
              await deleteGroup(value.id);
              loading.value = false;
              await fetchDataGroup();
            } catch (error) {
              console.error(error);
            }
          }
      );
    };

    const delUserInGroup = async (value: any) => {
      await showConfirmDialog(
          t("message.common.delConfirm"),
          t("message.common.delName", {name: t('message.menu.groupManage.userGroupRole')}),
          t("message.common.del"),
          t("message.common.close"),
          async () => {
            try {
              loading.value = true;
              await deleteGroupUser(value.id, groupId.value);
              loading.value = false;
              await getUserInGroup();
            } catch (error) {
              console.error(error);
            }
          }
      );
    };
    const fetchDataGroup = async () => {
      try {
        loading.value = true;
        pagination.current = Math.max(pagination.current, 1); // Đảm bảo currentPage không dưới 1
        pagination.current--;
        const {data} = await quickSearchGroup(searchQuery, pagination);
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
    const getUserInGroup = async () => {
      try {
        loading.value = true;
        paginationUser.current = Math.max(paginationUser.current, 1); // Đảm bảo currentPage không dưới 1
        paginationUser.current--;
        const param = {
          groupId: groupId.value,
          quickSearch: searchQuery.quickSearchUser
        }
        const {data} = await searchUserInGroup(param, paginationUser);
        tableDataUser.value = data.data.content;
        paginationUser.total = data.data.totalElements;
        paginationUser.current = data.data.pageable.pageNumber + 1;
        paginationUser.pageSize = data.data.pageable.pageSize;
        loading.value = false;
      } catch (error) {
        console.error(error);
        loading.value = false;
      }
    };
    onMounted(() => {
      if (router.currentRoute.value.name === 'group-management') {
        fetchDataGroup();
      }
    });

    return {
      delGroup,
      delUserInGroup,
      getUserInGroup,
      fetchDataGroup,
      loading,
      initialData,
      titleDrawer,
      typeAction,
      groupId,
      titleUerListDrawer,
      visibleDrawer,
      userListVisibleDrawer,
      areaData,
      rules,
      searchQuery,
      collapseName,
      pagination,
      paginationUser,
      tableData,
      tableDataUser,
      navigatePartner,
      handlePageSize,
      handlePageSizeUser,
      handleCurrent,
      handleCurrentUser,
      resetForm,
      openDrawer,
      handleClose,
      clickRowGetData,
      openUserListDrawer,
      handleCloseUserListDrawer
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