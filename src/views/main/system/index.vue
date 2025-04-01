<template>
  <div class="box ">
    <SearchTable1
        :config="config"
        :fetchData="fetchData"
        :handle-save="handleSaveOrUpdate"
        :handleDelete="handleDelete"
    />
  </div>
</template>

<script lang="ts">
import {defineComponent, reactive, watch} from 'vue'
import SearchTable1 from "@/views/main/dynamicForm/SearchTable.vue";
import {add, del, update, getData} from "@/api/systemMessage";
import {ElMessage} from "element-plus";
import { ref, onMounted } from "vue";
export default defineComponent({
  components: {SearchTable1},
  setup() {
    const dataResponse = ref({});
    const getValueSelectBox = (value: any) => {
      return [
        {"value": 1, "label": "Hoạt động"},
        {"value": 0, "label": "Không hoạt động"}
      ];
    }
    let config = reactive({
      "searchForm": {
        "fields": [
          {
            "type": "text",
            "key": "code",
            "label": "Mã lỗi",
            "placeholder": "Nhập Mã",
            "defaultValue": "",
          },
          {
            "type": "combobox",
            "key": "status",
            "label": "Trạng thái",
            "placeholder": "Chọn trạng thái",
            "defaultValue": 1,
            "options": getValueSelectBox(1)
          }
        ]
      },
      "table": {
        "columns": [
          {"key": "stt", "label": "STT", "width": "100px", "align": 'center'},
          {"key": "code", "label": "Mã lỗi", "align": 'left'},
          {"key": "messageVi", "label": "Nội dung lỗi tiếng Việt", "align": 'left'},
          {"key": "descriptions", "label": "Mô tả lỗi", "align": 'left'},
          {"key": "statusName", "label": "Trạng thái","width": "150px", "align": 'left'},
        ],
        "actions": [
          {"type": "view", "label": "Xem chi tiết", "buttonType": "info"},
          {"type": "edit", "label": "Cập nhật", "buttonType": "success"},
          {"type": "delete", "label": "Xóa", "buttonType": "danger"}
        ]
      },
      "popup": {
        "fields": [
          {
            "type": "text",
            "key": "code",
            "label": "Mã lỗi",
            "placeholder": "Nhập mã lỗi",
            "defaultValue": "",
            "span": 12,
            "rules": [
              {"required": true, "message": "Mã lỗi không được để trống", "trigger": "blur"}
            ]
          },
          {
            "type": "text",
            "key": "messageVi",
            "label": "Nội dung lỗi tiếng Việt",
            "placeholder": "Nhập nội dung",
            "defaultValue": "",
            "span": 12,
            "rules": [
              {"required": true, "message": "Nội dung lỗi tiếng Việt không được để trống", "trigger": "blur"}
            ]
          },
          {
            "type": "combobox",
            "key": "status",
            "label": "Trạng thái",
            "placeholder": "Chọn trạng thái",
            "defaultValue": 1,
            "options": getValueSelectBox(1),
            "span": 12,
            "rules": [
              {"required": true, "message": "Trạng thái không được để trống", "trigger": "blur"}
            ]
          },
          {
            "type": "text",
            "key": "descriptions",
            "label": "Mô tả lỗi",
            "placeholder": "Nhập mô tả lỗi",
            "defaultValue": "",
            "span": 12,
            "rules": [
              {"required": true, "message": "Mô tả lỗi không được để trống", "trigger": "blur"}
            ]
          },
        ]
      }
    })
    const fetchData = async ({filters, page, pageSize}: any) => {
      const dataRes = await getData(filters, page, pageSize);
      const mockResponse = {
        totalElements: dataRes.data.data.totalElements,
        pageable: {
          pageNumber: page,
          pageSize,
          totalPages: Math.ceil(dataRes.data.data.totalElements / pageSize),
        },
        content: dataRes.data.data.content,
      };
      dataResponse.value = mockResponse;
      return {
        data: mockResponse.content,
        totalElements: mockResponse.totalElements,
        pageable: mockResponse.pageable,
        status: dataRes.data.status
      };
    };
    const handleSaveOrUpdate = async (request: any) => {
      console.log("value", request);
      try {
        if(request.id){
          const filter = request.filters;
          const page = request.currentPage;
          const pageSize = request.pageSize;
          const axiosResponse = await update(request)
          ElMessage({
            message: axiosResponse.data.data.message,
            type: 'success',
            duration: 3 * 1000
          });
        }else {
          const axiosResponse = await add(request)
          ElMessage({
            message: axiosResponse.data.data.message,
            type: 'success',
            duration: 3 * 1000
          });
        }
      } catch (error) {
        console.error(error);
        ElMessage({
          message: error.data.data.message,
          type: 'error',
          duration: 3 * 1000
        });
      }
    }
    const handleDelete = async (request: any) => {
      try {
        const axiosResponse = await del(request);
        ElMessage({
          message: axiosResponse.data.data.message,
          type: 'success',
          duration: 3 * 1000
        });

      } catch (error) {
        console.error(error);
        ElMessage({
          message: error.data.data.message,
          type: 'error',
          duration: 3 * 1000
        });
      }
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