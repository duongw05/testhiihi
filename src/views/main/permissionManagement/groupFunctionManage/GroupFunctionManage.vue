<template>
  <div>
    <!-- Vùng thông tin nhóm -->
    <div class="box">
      <div>
        <h3 style="margin-top: 0" class="style-header-box">{{ $t('message.menu.groupFunction.title') }}</h3>
      </div>
      <div style="padding: 15px 0 0 0">
        <el-row :gutter="24" style="width: 100%">
          <el-col :span="8">
            <span style="font-weight: bold; font-size: 13px">{{ $t('message.menu.groupFunction.code') }}: </span>
            <span>{{ props.groupObject.code }}</span>
          </el-col>
          <el-col :span="8">
            <span style="font-weight: bold; font-size: 13px">{{ $t('message.menu.groupFunction.name') }}: </span>
            <span>{{ props.groupObject.name }}</span>
          </el-col>
          <el-col :span="8">
            <span style="font-weight: bold; font-size: 13px">{{ $t('message.menu.groupFunction.description') }}: </span>
            <span>{{ props.groupObject.description }}</span>
          </el-col>
        </el-row>
      </div>
    </div>

    <!-- Form tìm kiếm menu -->
    <div class="layout-container">
      <div class="layout-container-form flex space-between" style="padding-top: 0">
        <el-form
            ref="ruleFormRef"
            style="width: 100%"
            :model="searchQuery"
            :rules="rules"
            status-icon
            @submit.prevent=""
            label-position="top"
        >
          <el-row :gutter="24">
            <el-col :span="24">
              <div class="demo-collapse">
                <el-collapse class="no-border-collapse" v-model="activeNamesFirst">
                  <el-collapse-item name="1" class="el-collapse-item__content-custom">
                    <template #title>
                      <h3 class="style-header-box">{{ $t('message.common.searchInfo') }}</h3>
                    </template>
                    <el-row :gutter="24">
                      <el-col :xs="24" :sm="12" :md="8" :lg="6" class="form-item">
                        <el-form-item prop="menuCode" :label="$t('message.menu.groupFunction.menuCode')" :size="'default'">
                          <el-input v-model.lazy.trim="searchQuery.menuCode"
                                    :placeholder="$t('message.common.placeholder', { name: $t('message.menu.groupFunction.menuCode') })"
                                    clearable/>
                        </el-form-item>
                      </el-col>

                      <el-col :xs="24" :sm="12" :md="8" :lg="6" class="form-item">
                        <el-form-item prop="menuName" :label="$t('message.menu.groupFunction.menuName')" :size="'default'">
                          <el-input v-trim v-model="searchQuery.menuName"
                                    :placeholder="$t('message.common.placeholder', { name: $t('message.menu.groupFunction.menuName') })"
                                    clearable/>
                        </el-form-item>
                      </el-col>

                      <el-col :xs="24" :sm="12" :md="8" :lg="6" class="form-item">
                        <el-form-item prop="permissionCode" :label="$t('message.menu.groupFunction.permissionCode')"
                                      :size="'default'">
                          <el-input v-model.lazy.trim="searchQuery.permissionCode"
                                    :placeholder="$t('message.common.placeholder', { name: $t('message.menu.groupFunction.permissionCode') })"
                                    clearable/>
                        </el-form-item>
                      </el-col>

                      <el-col :xs="24" :sm="12" :md="8" :lg="6" class="form-item">
                        <el-form-item prop="permissionName" :label="$t('message.menu.groupFunction.permissionName')"
                                      :size="'default'">
                          <el-input v-trim v-model="searchQuery.permissionName"
                                    :placeholder="$t('message.common.placeholder', { name: $t('message.menu.groupFunction.permissionName') })"
                                    clearable/>
                        </el-form-item>
                      </el-col>
                    </el-row>
                    <div class="button-container">
                      <el-button :icon="RefreshRight" size="default" :loading="loading" @click="resetTable" plain>
                        {{ $t('message.menu.groupFunction.reset') }}
                      </el-button>
                      <el-button :icon="Search" @click="fetchData" size="default" type="primary"
                                 color="var(--system-primary-color)" :loading="loading">
                        {{ $t('message.menu.groupFunction.search') }}
                      </el-button>
                    </div>
                  </el-collapse-item>
                </el-collapse>
              </div>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </div>

    <!-- Vùng kết quả tìm kiếm -->
    <div class="box">
      <div>
        <div>
          <h3 style="margin-top: 0" class="style-header-box">
            {{ $t('message.menu.groupFunction.searchResults', {total: pagination.total}) }}</h3>
        </div>
        <!-- Bảng danh sách menu cha -->
        <el-table
            :style="cssLevel(tableData[0])"
            border
            :data="tableData"
            :fit="true"
            :header-cell-style="{background:'#DDE1E6', color: 'rgba(0,0,0,0.8)', padding: '8px'}"
            row-key="id"
            :loading="loading"
        >
          <template #default="scope">
            <el-table-column type="expand">
              <template #default="props">
                <!-- Danh sách menu con -->
                <div v-if="hasChildren(props.row)">
                  <el-table
                      :data="props.row.children"
                      :style="cssLevel(props.row.children[0])"
                      border
                      :fit="true"
                      :header-cell-style="{background:'#DDE1E6', color: 'rgba(0,0,0,0.8)', padding: '8px'}"
                      row-key="id"
                      :loading="loading"
                  >
                    <el-table-column type="expand">
                      <template #default="childProps">
                        <!-- Danh sách quyền trong menu con -->
                        <el-table
                            style="width: calc(100% - 48px); margin-bottom: 20px; margin-left: 48px"
                            border
                            :data="childProps.row.groupPermissionResponseDTOS"
                            :header-cell-style="{background:'#DDE1E6', color: 'rgba(0,0,0,0.8)', padding: '8px'}"
                            v-loading="loading"
                            v-if="childProps.row.groupPermissionResponseDTOS && childProps.row.groupPermissionResponseDTOS.length > 0"
                        >
                          <el-table-column type="index" :label="$t('message.menu.groupFunction.index')" :width="100"
                                           align="center" header-align="center">
                            <template #default="scope">
                              {{ scope.$index + 1 }}
                            </template>
                          </el-table-column>
                          <el-table-column :label="$t('message.menu.groupFunction.permissionCode')" prop="permissionCode"
                                           header-align="center" :width="300"/>
                          <el-table-column :label="$t('message.menu.groupFunction.permissionName')" prop="permissionName"
                                           header-align="center" :width="200"/>
                          <el-table-column :label="$t('message.menu.groupFunction.permissionGroup')" prop="group"
                                           header-align="center"/>
                          <el-table-column :label="$t('message.menu.groupFunction.assignPermission')" header-align="center"
                                           align="center" min-width="60" width="120" fixed="right">
                            <template #default="scope">
                              <el-switch
                                  v-model="scope.row.switch"
                                  @change="switchPermissionClicked(scope.row, childProps.row)"
                                  class="ml-2"
                                  style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
                              />
                            </template>
                          </el-table-column>
                        </el-table>
                      </template>
                    </el-table-column>
                    <el-table-column type="index" :label="$t('message.menu.groupFunction.index')" :width="100"
                                     align="center" header-align="center"/>
                    <el-table-column :label="$t('message.menu.groupFunction.menuCode')" prop="code" header-align="center"
                                     :width="300"/>
                    <el-table-column :label="$t('message.menu.groupFunction.menuName')" prop="title" header-align="center"
                                     :width="200"/>
                    <el-table-column :label="$t('message.menu.groupFunction.description')" prop="description"
                                     header-align="center"/>
                    <el-table-column :label="$t('message.menu.groupFunction.assignMenu')" header-align="center"
                                     align="center" min-width="60" width="120" fixed="right">
                      <template #default="scope">
                        <el-switch
                            v-model="scope.row.switch"
                            @change="switchMenuClicked(scope.row, props.row)"
                            class="ml-2"
                            style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
                        />
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
                <!-- Nếu menu cha không có menu con nhưng có quyền, hiển thị quyền trực tiếp -->
                <div v-else>
                  <el-table
                      style="width: calc(100% - 48px); margin-bottom: 20px; margin-left: 48px"
                      border
                      :data="props.row.groupPermissionResponseDTOS"
                      :header-cell-style="{background:'#DDE1E6', color: 'rgba(0,0,0,0.8)', padding: '8px'}"
                      v-loading="loading"
                      v-if="props.row.groupPermissionResponseDTOS && props.row.groupPermissionResponseDTOS.length > 0"
                  >
                    <el-table-column type="index" :label="$t('message.menu.groupFunction.index')" :width="100"
                                     align="center" header-align="center">
                      <template #default="scope">
                        {{ scope.$index + 1 }}
                      </template>
                    </el-table-column>
                    <el-table-column :label="$t('message.menu.groupFunction.permissionCode')" prop="permissionCode"
                                     header-align="center" :width="300"/>
                    <el-table-column :label="$t('message.menu.groupFunction.permissionName')" prop="permissionName"
                                     header-align="center" :width="200"/>
                    <el-table-column :label="$t('message.menu.groupFunction.permissionGroup')" prop="group"
                                     header-align="center"/>
                    <el-table-column :label="$t('message.menu.groupFunction.assignPermission')" header-align="center"
                                     align="center" min-width="60" width="120" fixed="right">
                      <template #default="scope">
                        <el-switch
                            v-model="scope.row.switch"
                            @change="switchPermissionClicked(scope.row, props.row)"
                            class="ml-2"
                            style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
                        />
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </template>
            </el-table-column>

            <el-table-column type="index" :label="$t('message.menu.groupFunction.index')" :width="100" align="center"
                             header-align="center"/>
            <el-table-column :label="$t('message.menu.groupFunction.menuCode')" prop="code" header-align="center"
                             :width="300"/>
            <el-table-column :label="$t('message.menu.groupFunction.menuName')" prop="title" header-align="center"
                             :width="200"/>
            <el-table-column :label="$t('message.menu.groupFunction.description')" prop="description"
                             header-align="center"/>
            <el-table-column :label="$t('message.menu.groupFunction.assignMenu')" header-align="center" align="center"
                             min-width="60" width="120" fixed="right">
              <template #default="scope">
                <el-switch
                    v-model="scope.row.switch"
                    @change="switchMenuClicked(scope.row, null)"
                    class="ml-2"
                    style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
                />
              </template>
            </el-table-column>
          </template>
        </el-table>
        <!-- Phân trang bảng menu cha -->
        <div style="margin: 12px 0px; display: flex; justify-content: flex-end; align-items: center">
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
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {onMounted, reactive, ref} from 'vue';
import {useI18n} from 'vue-i18n';
import {ElMessage} from 'element-plus';
import {RefreshRight, Search} from '@element-plus/icons-vue';
import {useRouter} from 'vue-router';

const {t} = useI18n();
const activeNamesFirst = ref(['1']);
const router = useRouter();

const props = defineProps({
  groupObject: {
    type: Object,
    default: () => ({}),
    required: true
  }
});

// INIT QUERY TABLE
const searchQuery = reactive({
  menuCode: null,
  menuName: null,
  permissionCode: null,
  permissionName: null
});

let loading = ref(false);
const tableData = ref([]);
const pagination = reactive({
  current: 1,
  total: 0,
  pageSize: 10,
  size: 10,
});

// Dữ liệu giả lập
props.groupObject.code = "GROUP_001";
props.groupObject.name = "Quản trị viên";
props.groupObject.description = "Nhóm người dùng quản trị hệ thống";

// Dữ liệu giả lập cho danh sách menu và quyền
const mockMenuData = [
  {
    id: 1,
    code: "DASHBOARD",
    title: "Dashboard",
    description: "Hệ thống",
    parentCode: null,
    switch: false,
    children: [],
    groupPermissionResponseDTOS: []
  },
  {
    id: 2,
    code: "TEST",
    title: "TEST MENU",
    description: "",
    parentCode: null,
    switch: false,
    children: [],
    groupPermissionResponseDTOS: []
  },
  {
    id: 3,
    code: "USER_MANAGEMENT",
    title: "Quản lý người dùng",
    description: "Quản lý người dùng",
    parentCode: null,
    switch: false,
    children: [
      {
        id: 4,
        code: "CONFIG_PERMISSION",
        title: "Quản lý quyền",
        description: "Quản lý quyền",
        parentCode: "USER_MANAGEMENT",
        switch: false,
        children: [],
        groupPermissionResponseDTOS: [
          {
            permissionId: 1,
            permissionCode: "MENUS_MANAGEMENT",
            permissionName: "Quản lý menu",
            group: "Quản lý phân quyền cho nhóm người dùng",
            switch: false
          },
          {
            permissionId: 2,
            permissionCode: "CONFIG_MENU_PERMISSION_FOR_GROUP",
            permissionName: "Quản lý phân quyền cho nhóm người dùng",
            group: "Quản lý phân quyền cho nhóm người dùng",
            switch: false
          }
        ]
      },
      {
        id: 5,
        code: "USER_LIST",
        title: "Danh sách người dùng",
        description: "Danh sách người dùng",
        parentCode: "USER_MANAGEMENT",
        switch: false,
        children: [],
        groupPermissionResponseDTOS: [
          {
            permissionId: 3,
            permissionCode: "DELETE_PERMISSION",
            permissionName: "DELETE_PERMISSION",
            group: "CONFIG_PERMISSION",
            switch: false
          },
          {
            permissionId: 4,
            permissionCode: "ADD_PERMISSION",
            permissionName: "ADD_PERMISSION",
            group: "CONFIG_PERMISSION",
            switch: false
          },
          {
            permissionId: 5,
            permissionCode: "VIEW_PERMISSION",
            permissionName: "VIEW_MENUS",
            group: "CONFIG_PERMISSION",
            switch: false
          },
          {
            permissionId: 6,
            permissionCode: "UPDATE_PERMISSION",
            permissionName: "UPDATE_MENUS",
            group: "CONFIG_PERMISSION",
            switch: false
          },
          {
            permissionId: 7,
            permissionCode: "SEARCH_CONFIG_PERMISSION",
            permissionName: "CONFIG_PERMISSION",
            group: "CONFIG_PERMISSION",
            switch: false
          }
        ]
      }
    ],
  }
];

const handlePageSize = (size: number) => {
  pagination.pageSize = size;
  pagination.current = 1;
  fetchData();
};

const handleCurrent = (page: number) => {
  pagination.current = page;
  fetchData();
};

const resetSearchQuery = () => {
  searchQuery.menuCode = "";
  searchQuery.menuName = "";
  searchQuery.permissionCode = "";
  searchQuery.permissionName = "";
};

const resetTable = async () => {
  resetSearchQuery();
  await fetchData();
};

const fetchData = async () => {
  try {
    loading.value = true;

    // Giả lập dữ liệu trả về từ API searchMenu
    const mockResponse = {
      data: {
        content: mockMenuData,
        totalElements: mockMenuData.length,
        pageable: {
          pageSize: pagination.pageSize
        }
      }
    };

    // Gán dữ liệu giả lập
    tableData.value = mockResponse.data.content;
    pagination.total = mockResponse.data.totalElements;
    pagination.pageSize = mockResponse.data.pageable.pageSize;
  } catch (error) {
    console.error(error);
    ElMessage.error(t('message.common.fetchDataError'));
  } finally {
    loading.value = false;
  }
};

// Hàm đệ quy để cập nhật trạng thái switch cho tất cả menu con
const updateChildrenSwitch = (menu: any, switchValue: boolean) => {
  if (menu.children && menu.children.length > 0) {
    menu.children.forEach((child: any) => {
      child.switch = switchValue;
      // Cập nhật trạng thái switch của danh sách quyền trong menu con
      if (child.groupPermissionResponseDTOS && child.groupPermissionResponseDTOS.length > 0) {
        child.groupPermissionResponseDTOS.forEach((permission: any) => {
          permission.switch = switchValue;
        });
      }
      updateChildrenSwitch(child, switchValue); // Đệ quy để cập nhật menu con cấp sâu hơn
    });
  }
  // Cập nhật trạng thái switch của danh sách quyền trong menu hiện tại
  if (menu.groupPermissionResponseDTOS && menu.groupPermissionResponseDTOS.length > 0) {
    menu.groupPermissionResponseDTOS.forEach((permission: any) => {
      permission.switch = switchValue;
    });
  }
};

// Hàm tìm menu cha và cập nhật trạng thái switch
const updateParentSwitch = (menuId: number, menus: any[]) => {
  for (const menu of menus) {
    if (menu.children && menu.children.some((child: any) => child.id === menuId)) {
      const hasAnyChildAssigned = menu.children.some((child: any) => child.switch);
      menu.switch = hasAnyChildAssigned;
      return true;
    }
    if (menu.children && menu.children.length > 0) {
      const found = updateParentSwitch(menuId, menu.children);
      if (found) {
        const hasAnyChildAssigned = menu.children.some((child: any) => child.switch);
        menu.switch = hasAnyChildAssigned;
        return true;
      }
    }
  }
  return false;
};

// Hàm tìm menu chứa quyền và cập nhật trạng thái switch của menu đó
const updateMenuSwitchForPermission = (permissionId: number, menus: any[]) => {
  for (const menu of menus) {
    if (menu.groupPermissionResponseDTOS && menu.groupPermissionResponseDTOS.some((perm: any) => perm.permissionId === permissionId)) {
      const hasAnyPermissionAssigned = menu.groupPermissionResponseDTOS.some((perm: any) => perm.switch);
      menu.switch = hasAnyPermissionAssigned;
      if (menu.switch) {
        // Nếu menu được bật, cập nhật menu cha
        updateParentSwitch(menu.id, tableData.value);
      }
      return true;
    }
    if (menu.children && menu.children.length > 0) {
      const found = updateMenuSwitchForPermission(permissionId, menu.children);
      if (found) {
        const hasAnyChildAssigned = menu.children.some((child: any) => child.switch);
        menu.switch = hasAnyChildAssigned;
        return true;
      }
    }
  }
  return false;
};

const switchMenuClicked = async (row: any, parentRow: any) => {
  try {
    loading.value = true;
    let groupId = router.currentRoute.value.params?.data || "mock_group_id";

    // Nếu là menu cha (parentRow === null)
    if (!parentRow) {
      // Cập nhật trạng thái switch cho tất cả menu con và quyền
      updateChildrenSwitch(row, row.switch);
    } else {
      // Nếu là menu con, cập nhật trạng thái switch của menu cha và danh sách quyền
      updateChildrenSwitch(row, row.switch); // Cập nhật quyền trong menu con
      updateParentSwitch(row.id, tableData.value); // Cập nhật menu cha
    }

    // Giả lập API linkWithGroupMenu và unlinkWithGroupMenu
    if (row.switch) {
      ElMessage({
        message: t('message.menu.groupFunction.assignSuccess', {name: row.title}),
        type: 'success',
        duration: 3 * 1000
      });
    } else {
      ElMessage({
        message: t('message.menu.groupFunction.unAssignSuccess', {name: row.title}),
        type: 'success',
        duration: 3 * 1000
      });
    }

    fetchData(); // Cập nhật lại dữ liệu
  } catch (error) {
    console.error(error);
    row.switch = !row.switch;
    ElMessage.error(t('message.common.err'));
  } finally {
    loading.value = false;
  }
};

const switchPermissionClicked = async (row: any, menuRow: any) => {
  try {
    loading.value = true;
    let groupId = router.currentRoute.value.params?.data || "mock_group_id";

    // Cập nhật trạng thái switch của menu chứa quyền
    updateMenuSwitchForPermission(row.permissionId, tableData.value);

    // Giả lập API linkWithGroupPermission và unlinkWithGroupPermission
    if (row.switch) {
      ElMessage({
        message: t('message.menu.groupFunction.assignSuccess', {name: row.permissionName}),
        type: 'success',
        duration: 3 * 1000
      });
    } else {
      ElMessage({
        message: t('message.menu.groupFunction.unassignSuccess', {name: row.permissionName}),
        type: 'success',
        duration: 3 * 1000
      });
    }

    fetchData(); // Cập nhật lại dữ liệu
  } catch (error) {
    console.error(error);
    row.switch = !row.switch;
    ElMessage.error(t('message.common.err'));
  } finally {
    loading.value = false;
  }
};

const rules = {
  menuCode: [
    {
      max: 100,
      message: t('message.validate.max', {name: t('message.menu.groupFunction.menuCode'), value: 100}),
      trigger: ['blur', 'change']
    },
    {
      validator: (rule: any, value: any, callback: any) => {
        const regex = /^[a-zA-Z0-9\-_]+$/;
        if (!value) {
          callback();
        } else if (!regex.test(value)) {
          callback(new Error(t('message.validate.regex', {name: t('message.menu.groupFunction.menuCode')})));
        } else {
          callback();
        }
      },
      trigger: ['blur', 'change']
    }
  ],
  menuName: [
    {
      max: 255,
      message: t('message.validate.max', {name: t('message.menu.groupFunction.menuName'), value: 255}),
      trigger: ['blur', 'change']
    }
  ],
  permissionCode: [
    {
      max: 50,
      message: t('message.validate.max', {name: t('message.menu.groupFunction.permissionCode'), value: 50}),
      trigger: ['blur', 'change']
    },
  ],
  permissionName: [
    {
      max: 200,
      message: t('message.validate.max', {name: t('message.menu.groupFunction.permissionName'), value: 200}),
      trigger: ['blur', 'change']
    }
  ],
};

const cssLevel = (row: any) => {
  if (!row) {
    return 'width: 100%';
  }
  if (row.parentCode === null) {
    return 'margin-bottom: 20px; margin-left: 0';
  }
  return 'width: calc(100% - 48px); margin-bottom: 20px; margin-left: 48px';
};

const hasChildren = (row: any) => {
  return row.children && row.children.length > 0;
};

onMounted(() => {
  fetchData();
});
</script>

<style lang="scss" scoped>
.form-item {
  margin-bottom: 20px;
}

.box {
  background-color: #fff;
  padding: 15px;
  margin: 15px;
}

.title-search {
  font-size: 16px;
  font-weight: 600;
}

.button-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.button-container-table {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 10px;
}

.action-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 0px;
}

.el-collapse {
  border: none;
}
</style>