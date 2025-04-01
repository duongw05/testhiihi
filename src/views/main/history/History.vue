<template>
  <el-table v-loading="loading"
            :data="tableData"
            row-key="id"
            :header-cell-style="{background:'#DDE1E6', color: 'rgba(0,0,0,0.8)', padding: '8px'}"
            border
  >
    <template #default="scope">
      <el-table-column
          type="index"
          label="STT"
          width="80"
          align="center"
          header-align="center"
          row-key="id"
          border
          default-expand-all
      >
        <template #default="scope">
          {{ scope.row.stt }}
        </template>
      </el-table-column>
      <el-table-column prop="actionCode" :label="'Loại tác động'" width="150"
                       header-align="center" align="left">
        <template #default="scope">
                <span>
                  {{ getValueAction(scope.row.actionCode) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="description" :label="'Nội dung'" min-width="150"
                       header-align="center" align="left">
        <template #default="scope">
                <span>
                  {{ scope.row.description }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="createdDate" :label="'Thời gian tác động'" width="150"
                       header-align="center" align="center">
        <template #default="scope">
                <span>
                  {{ scope.row.createdDate }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="createdBy" :label="'Người tác động'" width="150"
                       header-align="center" align="left">
        <template #default="scope">
                <span>
                  {{ scope.row.createdBy }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="'Hành động'" align="center" fixed="right" width="100">
        <template #default="scope">
          <el-tooltip :content="'Chi tiết'" placement="top">
            <el-button :icon="Document"
                       style="width: 15px; margin: 0"
                       text
                       type="danger"
                @click="viewDetail(scope.row.actionDetailDTOS)"></el-button>
          </el-tooltip>
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
        background
        layout="total, sizes, prev, pager, next, jumper"
        :total="pagination.total"
        @size-change="handlePageSize"
        @current-change="handleCurrent"
    />
  </div>
  <FormHistoryDetail
      :isView="isView"
      :data="data"
      @close="closeView"
  />
</template>
<script lang="ts">
import {defineComponent, onMounted, reactive, ref, watch} from 'vue';
import {ElTable} from "element-plus";
import {Document} from "@element-plus/icons";
import FormHistoryDetail from "@/views/main/history/FormHistoryDetail.vue";
import { AUDIT_ACTION } from '@/constants/actionAudit';
import { searchAudit } from '@/api/actionAudit';
import eventBus from '@/eventBus';


export default defineComponent({
  components: {FormHistoryDetail},
  computed: {
    Document() {
      return Document
    },
  },
  methods: {
    getValueAction(item: any) {
      let tmp = AUDIT_ACTION.filter(e => e.value == item)
      return tmp.length > 0 ? tmp[0]?.label : ''
    }
  },
  name: 'History',
  props: {
    id: Number,
    type: String,
  },
  setup(props, {emit}) {
    let loading = ref(false)
    const pagination = reactive({
      current: 1,
      total: 0,
      pageSize: 10,
      size: 10,
    });

    const tableData = ref([])
    let visible: any = ref(false);
    const isView = ref(false);
    let data = ref([])
    const message = ref<string>('');

    const viewDetail = (item: any) => {
      isView.value = true;
      data.value = item;
    };

    const closeView = async () => {
      isView.value = false;
    };
    const handlePageSize = (size: number) => {
      pagination.pageSize = size;
      pagination.current = 1;
      fetchData();
    };

    const handleCurrent = (page: number) => {
      pagination.current = page;
      fetchData();
    };
    const fetchData = async () => {
      try {
        loading.value = true;
        // Giảm currentPage trước khi lấy dữ liệu
        pagination.current = Math.max(pagination.current, 1); // Đảm bảo currentPage không dưới 1
        pagination.current--;
        let searchQuery = {};
        searchQuery.id = props.id;
        searchQuery.actionCode = props.type;
        tableData.value = []
        const {data} = await searchAudit(searchQuery, pagination);
        tableData.value = data.data.content;
        tableData.value.forEach((e: any, index) => {
          e.stt = pagination.current * pagination.pageSize + index + 1
        })
        pagination.total = data.data.totalElements;
        pagination.current = data.data.pageable.pageNumber + 1;
        loading.value = false;
      } catch (error) {
        console.error(error);
        loading.value = false;
      }
    };

    onMounted(() => {
      eventBus.on('refesh-hisoty', (data) => {
        message.value = data;
        fetchData()
      });
    });

    watch(() => props.id, async () => {
      visible.value = true;
      tableData.value = []
      await fetchData();
    });


    return {
      loading,
      visible,
      tableData,
      pagination,
      handlePageSize,
      handleCurrent,
      fetchData,
      isView,
      data,
      viewDetail,
      closeView
    };
  }
});
</script>

<style scoped>
.pagination {
  padding: 15px;
  display: flex;
  justify-content: end;
}
</style>