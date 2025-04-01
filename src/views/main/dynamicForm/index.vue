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
import {defineComponent, reactive} from 'vue'
import SearchTable from "@/views/main/dynamicForm/SearchTable.vue";

export default defineComponent({
  components: {SearchTable},
  setup() {
    const getValueSelectBox = (value: any) => {
      return [
        {"value": "active", "label": "Hoạt động"},
        {"value": "inactive", "label": "Không hoạt động"}
      ];
    }
    let config = reactive({
      "searchForm": {
        "fields": [
          {
            "type": "text",
            "key": "code",
            "label": "Mã",
            "placeholder": "Nhập Mã",
            "defaultValue": "test",
          },
          {
            "type": "text",
            "key": "name",
            "label": "Tên",
            "placeholder": "Nhập Tên",
            "defaultValue": "test",
            "rules": [
              {"required": true, "message": "Tên không được để trống", "trigger": "blur"}
            ]
          },
          {
            "type": "combobox",
            "key": "status",
            "label": "Trạng thái",
            "placeholder": "Chọn trạng thái",
            "defaultValue": "active",
            "options": getValueSelectBox(1)
          }
        ]
      },
      "table": {
        "columns": [
          {"key": "stt", "label": "STT", "width": "100px", "align": 'center'},
          // {"key": "id", "label": "ID", "width": "200px", "align": 'left'},
          {"key": "code", "label": "Mã", "align": 'left'},
          {"key": "name", "label": "Tên", "align": 'left'},
          {"key": "status", "label": "Trạng thái","width": "150px", "align": 'left'}
        ],
        "actions": [
          {"type": "edit", "label": "Cập nhật", "buttonType": "success"},
          {"type": "view", "label": "Xem chi tiết", "buttonType": "info"},
          {"type": "delete", "label": "Xóa", "buttonType": "danger"}
        ]
      },
      "popup": {
        "fields": [
          {
            "type": "text",
            "key": "name",
            "label": "Tên",
            "placeholder": "Nhập tên",
            "defaultValue": "",
            "rules": [
              {"required": true, "message": "Tên không được để trống", "trigger": "blur"}
            ]
          },
          {
            "type": "text",
            "key": "code",
            "label": "Mã",
            "placeholder": "Nhập mã",
            "defaultValue": "",
            "rules": [
              {"required": true, "message": "Mã không được để trống", "trigger": "blur"}
            ]
          },
          {
            "type": "combobox",
            "key": "status",
            "label": "Trạng thái",
            "placeholder": "Chọn trạng thái",
            "defaultValue": "active",
            "options": getValueSelectBox(1)
          }
        ]
      }
    })
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
          name: `Tên người dùng ${(page - 1) * pageSize + index + 1}`,
          code: `Mã người dùng ${(page - 1) * pageSize + index + 1}`,
          status: (index % 2 === 0 ? "active" : "inactive"),
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

    /** 关闭当前选项卡 */
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