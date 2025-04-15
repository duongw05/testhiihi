<template>
  <div class="box ">
    <SearchTable
        :config="config"
        :fetchData="fetchDataFunctionCatalog"
        :handle-save="handleSaveOrUpdate"
        :handleDelete="handleDelete"
    />
  </div>

</template>

<script lang="ts">
import {defineComponent, computed, ref, reactive} from 'vue'
import SearchTable from "@/views/main/dynamicForm/SearchTable.vue";
import {useI18n} from "vue-i18n";
import {
  addFunctionCatalog,
  deleteFunctionCatalog,
  searchFunctionCatalog,
  updateFunctionCatalog
} from "@/api/roleManage/funtionCatalog";
import {handleErr, handleSuccess, showConfirmDialog} from '@/utils/mixins/mixin';

export default defineComponent({
  components: {SearchTable},
  setup() {
    const {t} = useI18n();
    const loading = ref(false)
    const pagination = reactive({
      current: 1,
      total: 0,
      pageSize: 10,
      size: 10,
    });
    const filterQuery = reactive(
        {
          status: null,
          functionCode: '',
          functionName: ''
        }
    );
    const getValueSelectBox = (value: any) => {
      return [
        {"value": 1, "label": t('message.common.status.active')},
        {"value": 0, "label": t('message.common.status.inactive')},
      ];
    }
    let config = computed(() => ({
      "title": t('message.menu.functionManage.functionInfo'),
      "searchForm": {
        "fields": [
          {
            "type": "text",
            "key": "functionCode",
            "label": t('message.menu.functionManage.functionCode'),
            "placeholder": t('message.common.placeholder', {name: t('message.menu.functionManage.functionCode')}),
            "defaultValue": "",
          },
          {
            "type": "text",
            "key": "functionName",
            "label": t('message.menu.functionManage.functionName'),
            "placeholder": t('message.common.placeholder', {name: t('message.menu.functionManage.functionName')}),
            "defaultValue": "",
          },
          {
            "type": "combobox",
            "key": "status",
            "label": t('message.menu.functionManage.status'),
            "placeholder": t('message.menu.functionManage.selectStatus'),
            "defaultValue": 1,
            "options": getValueSelectBox(1)
          },
        ]
      },
      "table": {
        "columns": [
          {"key": "functionCode", "label": t('message.menu.functionManage.functionCode'), "align": 'right'},
          {"key": "functionName", "label": t('message.menu.functionManage.functionName'), "align": 'right'},
          {"key": "description", "label": t('message.menu.functionManage.description'), "align": 'right'},
          {
            "key": "status", "label": t('message.menu.functionManage.status'), "width": "150px", "align": 'center',
            "formatter": (row: any, column: any, cellValue: any) => {
              return cellValue === 1 ? t('message.common.status.active') : t('message.common.status.inactive');
            }
          }
        ],
        "actions": [
          {"type": "edit", "label": t('message.common.update'), "buttonType": "success"},
          {"type": "view", "label": t('message.common.view'), "buttonType": "info"},
          {"type": "delete", "label": t('message.common.del'), "buttonType": "danger"}
        ]
      },
      "popup": {
        "fields": [
          {
            "type": "text",
            "key": "functionCode",
            "label": t('message.menu.functionManage.functionCode'),
            "placeholder": t('message.common.placeholder', {name: t('message.menu.functionManage.functionCode')}),
            "rules": [
              {
                required: true,
                message: t('validate.required', {name: t('message.menu.functionManage.functionCode')}),
                trigger: 'blur'
              },
              {
                min: 1,
                max: 50,
                message: t('validate.max', {name: t('message.menu.functionManage.functionCode'), value: 50}),
                trigger: 'blur'
              }
            ],
          },
          {
            "type": "text",
            "key": "functionName",
            "label": t('message.menu.functionManage.functionName'),
            "placeholder": t('message.common.placeholder', {name: t('message.menu.functionManage.functionName')}),
            "rules": [
              {
                required: true,
                message: t('validate.required', {name: t('message.menu.functionManage.functionName')}),
                trigger: 'blur'
              },
              {
                min: 1,
                max: 50,
                message: t('validate.max', {name: t('message.menu.functionManage.functionName'), value: 50}),
                trigger: 'blur'
              }
            ],
          },
          {
            "type": "combobox",
            "key": "status",
            "label": t('message.menu.functionManage.status'),
            "placeholder": t('message.menu.functionManage.selectStatus'),
            "defaultValue": 1,
            "options": getValueSelectBox(1)
          },
          {
            "type": "textArea",
            "key": "description",
            "label": t('message.menu.functionManage.description'),
            "placeholder": t('message.common.placeholder', {name: t('message.menu.functionManage.description')}),
            "rules": [
              {
                min: 1,
                max: 500,
                message: t('validate.max', {name: t('message.menu.functionManage.description'), value: 500}),
                trigger: 'blur'
              }
            ],
          }
        ]
      }
    }));

    const fetchDataFunctionCatalog = async ({filters, page, pageSize}: any) => {
      try {
        loading.value = true;
        const {data} = await searchFunctionCatalog(filters, {current: page - 1, pageSize});
        const content = Array.isArray(data.data.content) ? data.data.content : [];
        Object.assign(filterQuery, filters);
        pagination.current = page;
        pagination.pageSize = pageSize;
        return {
          data: content,
          totalElements: data.data.totalElements || 0,
          pageable: {
            pageNumber: (data.data.pageable?.pageNumber || 0) + 1,
            pageSize: data.data.pageable?.pageSize || pageSize,
            totalPages: Math.ceil((data.data.totalElements || 0) / (data.data.pageable?.pageSize || pageSize)),
          },
        };
      } catch (error) {
        return {
          data: [],
          totalElements: 0,
          pageable: {
            pageNumber: 1,
            pageSize: pageSize || 10,
            totalPages: 0,
          },
        };
      } finally {
        loading.value = false;
      }
    };
    const handleSaveOrUpdate = async (value: any) => {
      try {
        loading.value = true
        if (value.id) {
          await updateFunctionCatalog(value)
          handleSuccess(t, t('message.common.updateSuccess', {name: t('message.menu.functionManage.functionInfo')}));
        } else {
          await addFunctionCatalog(value)
          handleSuccess(t, t('message.common.addSuccess', {name: t('message.menu.functionManage.functionInfo')}));
        }
        await fetchDataFunctionCatalog({
          filters: {
            ...filterQuery,
            status: filterQuery.status === null ? 1 : filterQuery.status
          },
          page: pagination.current,
          pageSize: pagination.pageSize
        })
        loading.value = false
      } catch (e) {
        console.log(e)
        handleErr(t, t('message.common.errServer'));
        loading.value = false
      }
    }

    const handleDelete = async (value: any) => {
      try {
        loading.value = true;
        await deleteFunctionCatalog(value.id);
        loading.value = false;
      } catch (error) {
        console.error(error);
      }
    }

    return {
      filterQuery,
      pagination,
      config,
      fetchDataFunctionCatalog,
      handleSaveOrUpdate,
      handleDelete,
    }
  }
})
</script>

<style lang="scss" scoped>

</style>