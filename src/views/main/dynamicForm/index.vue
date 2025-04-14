<template>
  <div class="box ">
    <SearchTable
        :config="config"
        :fetchData="fetchData"
        :handle-save="handleSaveOrUpdate"
        :handleDelete="handleDelete"
    />
  </div>

</template>

<script lang="ts">
import {defineComponent, computed} from 'vue'
import SearchTable from "@/views/main/dynamicForm/SearchTable.vue";
import {useI18n} from "vue-i18n";

export default defineComponent({
  components: {SearchTable},
  setup() {
    const {t} = useI18n();
    const getValueSelectBox = (value: any) => {
      return [
        {"value": "1", "label": t('message.common.status.active')},
        {"value": "0", "label": t('message.common.status.inactive')},
      ];
    }
    let config = computed(() => ({
      "title": t('message.menu.functionManage.functionInfo'),
      "searchForm": {
        "fields": [
          {
            "type": "text",
            "key": "code",
            "label": t('message.menu.functionManage.functionCode'),
            "placeholder": t('message.common.placeholder', {name: t('message.menu.functionManage.functionCode')}),
            "rules": [
              {
                required: true,
                message: t('validate.required', {name: t('message.menu.functionManage.functionCode')}),
                trigger: 'blur'
              },
            ],
            "defaultValue": "",
          },
          {
            "type": "text",
            "key": "name",
            "label": t('message.menu.functionManage.functionName'),
            "placeholder": t('message.common.placeholder', {name: t('message.menu.functionManage.functionName')}),
            "defaultValue": "",
          },{
            "type": "dateRange",
            "key": "name1",
            "label": t('message.menu.functionManage.functionName'),
            "placeholder": t('message.common.placeholder', {name: t('message.menu.functionManage.functionName')}),
            "defaultValue": "",
          },
        ]
      },
      "table": {
        "columns": [
          {"key": "stt", "label": "STT", "width": "100px", "align": 'center'},
          // {"key": "id", "label": "ID", "width": "200px", "align": 'left'},
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

    const fetchData = async ({filters, page, pageSize}: any) => {
      console.log("filter", filters)
      // Dữ liệu cố định
      const mockResponse = {
        totalElements: 50,
        pageable: {
          pageNumber: page,
          pageSize,
          totalPages: Math.ceil(50 / pageSize),
        },
        content: Array.from({length: pageSize}, (_, index) => ({
          stt: (page - 1) * pageSize + index + 1,
          id: (page - 1) * pageSize + index + 1,
          functionCode: `Tên người dùng ${(page - 1) * pageSize + index + 1}`,
          functionName: `Mã người dùng ${(page - 1) * pageSize + index + 1}`,
          description: `Mô tả ${(page - 1) * pageSize + index + 1}`,
          status: (index % 2 === 0 ? 1 : 0),
        })),
      };

      // Trả về dữ liệu giả lập
      return {
        data: mockResponse.content,
        totalElements: mockResponse.totalElements,
        pageable: mockResponse.pageable,
      };
    };
    const handleSaveOrUpdate = (value: any) => {
      console.log("value", value);
    }
    const handleDelete = (value: any) => {
      console.log("value", value);
    }

    return {
      config,
      fetchData,
      handleSaveOrUpdate,
      handleDelete,
    }
  }
})
</script>

<style lang="scss" scoped>

</style>