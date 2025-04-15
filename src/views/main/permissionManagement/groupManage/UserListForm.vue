<template>
  <el-drawer
      v-model="visibleValue"
      :title="title"
      size="40%"
      :show-close="false"
      @close="closeDialog()"
  >
    <template #header>
      <h2>{{ title }}</h2>
      <div style="text-align: right;">
        <div class="button-container">
          <el-button @click="closeDialog()" size="default" :icon="CircleCloseFilled" style="min-width: 100px" plain>
            {{ $t('message.common.close') }}
          </el-button>
          <el-button type="primary" style="min-width: 100px" :loading="loading"
                     size="default"
                     :icon="CirclePlus"
                     color="var(--system-primary-color)"
                     :disabled="formAddUserInRole.userIds.length === 0"
                     @click="addUserToGroup()">
            {{ $t('message.common.add') }}
          </el-button>
        </div>
      </div>
    </template>
    <el-input
        v-model="quickSearch"
        placeholder="Nhập tên người dùng muốn tìm kiếm ..."
        clearable
        @input="filterListUser"
        style="margin-bottom: 12px"
    />

    <el-scrollbar style="max-height: 60vh">
      <el-list v-loading="loading">
        <div
            v-for="item in listUser"
            :key="item.id"
            class="user-list-item"
        >
          <div class="user-info">
            <b>{{ item.username + '_' + item.fullName }}</b>
          </div>
          <el-checkbox
              v-model="item.isActive"
              @change="getUserId(item)"
          />
        </div>
      </el-list>
    </el-scrollbar>
  </el-drawer>
</template>

<script lang="ts">
import {defineComponent, ref, reactive, watch, onMounted} from 'vue';
import {useI18n} from 'vue-i18n';
import {CircleCloseFilled, CirclePlus} from '@element-plus/icons-vue';
import {searchUserNotInGroup, addGroupUser} from "@/api/roleManage/groupUser";
import {handleErr, handleSuccess} from "@/utils/mixins/mixin";

export default defineComponent({
  props: {
    visible: Boolean,
    titleDrawer: {
      type: String,
      default: ''
    },
    groupId: {
      type: Number,
      default: null
    },
  },
  emits: ['close'],
  setup(props, {emit}) {
    const {t} = useI18n();

    const visibleValue = ref(false);
    const title = ref('');
    const groupId = ref<number | null>(null);
    const loading = ref(false);
    const quickSearch = ref('');

    const formAddUserInRole = reactive({
      userIds: []
    });
    const listUser = ref<any[]>([]);

    const getUserId = (item?: any) => {
      if (item.isActive) {
        if (!formAddUserInRole.userIds.includes(item.id)) {
          formAddUserInRole.userIds.push(item.id);
        }
      } else {
        formAddUserInRole.userIds = formAddUserInRole.userIds.filter(id => id !== item.id);
      }
    };

    const filterListUser = async () => {
      try {
        loading.value = true;
        const {data} = await searchUserNotInGroup({
          groupId: groupId.value, quickSearch: quickSearch.value
        });
        console.log('data', data)
        listUser.value = data.data.map((item: any) => ({
          ...item,
        }));
        loading.value = false;
      } catch (error) {
        console.error(error);
        loading.value = false;
      }
    };

    const addUserToGroup = async () => {
      try {
        loading.value = true
        const mappedArray = formAddUserInRole.userIds.map(userId => ({
          userId,
          groupId: groupId.value
        }));
        console.log('mappedArray', mappedArray)
        await addGroupUser(mappedArray);
        handleSuccess(t, t('message.common.addSuccess', {name: t('message.menu.groupManage.userGroupRole')}));
        emit("success");
        loading.value = false
        visibleValue.value = false
      } catch (e) {
        console.log(e)
        handleErr(t, t('message.common.errServer'));
        loading.value = false
      }
    };

    const closeDialog = () => {
      visibleValue.value = false;
      emit('close');
      formAddUserInRole.userIds = [];
    };

    watch(() => props.visible, async () => {
      if (props.visible) {
        visibleValue.value = true
        title.value = props.titleDrawer
        groupId.value = props.groupId;
        await filterListUser();
        quickSearch.value = '';
        listUser.value.forEach(item => {
          item.isActive = false;
        });
      }
    })
    return {
      visibleValue,
      title,
      groupId,
      quickSearch,
      loading,
      listUser,
      formAddUserInRole,
      getUserId,
      filterListUser,
      addUserToGroup,
      closeDialog,
      CirclePlus,
      CircleCloseFilled
    };
  }
});
</script>

<style scoped>
::v-deep(.el-drawer__header .el-drawer__close-btn) {
  display: none !important;
}

.user-list-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 0;
  border-bottom: 1px solid #f0f0f0;
}

</style>
