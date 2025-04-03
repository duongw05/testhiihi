<template>
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

        <el-table-column :label="'Tên người dùng'" align="left"
                         header-align="left" prop="name" sortable/>
        <el-table-column :label="'Loại khách hàng'" align="left" sortable width="300"
                         header-align="left" prop="customerTypeName"/>
        <el-table-column :label="'Email khách hàng'" align="left"
                         header-align="left" prop="email"/>
        <el-table-column :label="'Số điện thoại'" align="left"
                         header-align="left" prop="tel"/>
        <el-table-column :label="'Ngày tạo'" align="center"
                         header-align="center" prop="createdDate">
        </el-table-column>
        <el-table-column :label="'Hành động'" align="center" fixed="right" width="150">
          <template #default="scope">
            <div class="action-container">
              <el-tooltip content="Xem chi tiết">
                <el-button :icon="Document"
                           style="width: 15px; margin: 0"
                           text type="danger"
                ></el-button>
              </el-tooltip>
              <el-tooltip content="Cập nhật">
                <el-button :icon="Edit"
                           style="width: 15px; margin: 0"
                           text
                           type="danger"
                ></el-button>
              </el-tooltip>
              <el-tooltip content="Xóa">
                <el-button
                    :icon="Delete" style="width: 15px; margin: 0;" text
                    type="danger"
                >
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
</template>

<script lang="ts">
import {defineComponent, reactive, ref} from 'vue'
import {Delete, Document, Download, Edit, Plus, Refresh, Search, Tickets} from "@element-plus/icons";

export default defineComponent({
  computed: {
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
  components: {},
  setup(props, ctx) {
    const tableData = ref([]);
    const pagination = reactive({
      current: 1,
      total: 0,
      pageSize: 10,
      size: 10,
    });

    const handlePageSize = (size: number) => {
      pagination.pageSize = size;
    };

    const handleCurrent = (page: number) => {
      pagination.current = page;
    };

    return {
      pagination,
      tableData,
      handlePageSize,
      handleCurrent
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
</style>