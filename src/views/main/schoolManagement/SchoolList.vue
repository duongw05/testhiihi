<template>
  <div class="box">
    <SearchTable
        :config="schoolConfig"
        :fetchData="fetchSchoolData"
        :handle-save="handleSchoolSaveOrUpdate"
        :handleDelete="handleSchoolDelete"
        :handleExport="handleSchoolExport"
        :handleImport="handleSchoolImport"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue';
import SearchTable from "@/views/main/dynamicForm/SearchTable.vue";
// import { useI18n } from "vue-i18n"; // Bỏ import i18n
import { ElNotification, ElMessage, ElMessageBox } from 'element-plus';

import {
  searchSchool,
  addSchool,
  updateSchool,
  deleteSchool, // Giữ lại nếu bạn có hàm deleteSchool thật sự
  exportSchoolExcel
} from '@/api/school';
import { fetchAllSchoolType } from '@/api/schoolType';
import { fetchAllTblUser } from "@/api/tblUser";
import { fetchAllWorkingOffsite } from "@/api/workingOffsite";
// Import handleErr và handleSuccess từ mixin, nhưng sẽ truyền null cho tham số 't'
import { handleErr, handleSuccess, showConfirmDialog } from '@/utils/mixins/mixin';

// const { t } = useI18n(); // Bỏ khai báo t

const schoolTypeOptions = ref<any[]>([]);
const userList = ref<any[]>([]);
const workingOffsiteList = ref<any[]>([]);

const currentFilterQuery = reactive({
  name: '',
  code: '',
  workingOffsiteId: null,
  areaCvct: '',
  programs: '',
  rhta: null,
  scheduler: null,
  schedulerCumTa: null,
  status: null,
  schoolTypeId: null,
});

const loadSelectOptions = async () => {
  try {
    const [schoolTypeRes, workingOffsiteRes, userRes] = await Promise.all([
      fetchAllSchoolType(),
      fetchAllWorkingOffsite(),
      fetchAllTblUser()
    ]);
    schoolTypeOptions.value = schoolTypeRes?.data?.data || [];
    workingOffsiteList.value = workingOffsiteRes?.data?.data || [];
    userList.value = userRes?.data?.data || [];
  } catch (error) {
    ElNotification({ title: 'Lỗi', message: 'Không thể tải dữ liệu tùy chọn.', type: 'error' });
  }
};

const getStatusOptions = () => {
  return [
    { "value": 1, "label": 'Hiệu lực' },
    { "value": 0, "label": 'Hết hiệu lực' },
  ];
};

const schoolConfig = computed(() => ({
  "title": "Quản lý Trường học",
  "searchForm": {
    "fields": [
      {
        "type": "combobox",
        "key": "schoolTypeId",
        "label": "Cấp học",
        "placeholder": "Chọn Cấp học",
        "defaultValue": null,
        "span": 20,
        "options": schoolTypeOptions.value.map(item => ({ label: item.name, value: item.id }))
      },
      {
        "type": "text",
        "key": "name",
        "label": "Tên trường học",
        "placeholder": "Nhập tên trường học",
        "defaultValue": "",
        "span": 8,
      },
      {
        "type": "text",
        "key": "code",
        "label": "Mã trường học",
        "placeholder": "Nhập mã trường học",
        "defaultValue": "",
        "span": 8,
      },
      {
        "type": "combobox",
        "key": "workingOffsiteId",
        "label": "Working Offsite",
        "placeholder": "Chọn workingOffsite",
        "defaultValue": null,
        "span": 8,
        "options": workingOffsiteList.value.map(item => ({ label: item.name, value: item.id }))
      },
      {
        "type": "text",
        "key": "areaCvct",
        "label": "Khu vực theo CVCT",
        "placeholder": "Nhập khu vực CVCT",
        "defaultValue": "",
        "span": 8
      },
      {
        "type": "combobox",
        "key": "programs",
        "label": "Chương trình Toán-Khoa",
        "placeholder": "Tất cả",
        "defaultValue": "",
        "span": 8,
        "options": [
          { label: "Tất cả", value: "" },
          { label: "Toán", value: "Toán" },
          { label: "Khoa học", value: "Khoa học" }
        ]
      },
      {
        "type": "combobox",
        "key": "rhta",
        "label": "RHTA",
        "placeholder": "Chọn RHTA",
        "defaultValue": null,
        "span": 8,
        "options": userList.value.map(item => ({ label: item.fullName || item.username, value: item.id }))
      },
      {
        "type": "combobox",
        "key": "scheduler",
        "label": "Scheduler",
        "placeholder": "Chọn Scheduler",
        "defaultValue": null,
        "span": 8,
        "options": userList.value.map(item => ({ label: item.fullName || item.username, value: item.id }))
      },
      {
        "type": "combobox",
        "key": "schedulerCumTa",
        "label": "Scheduler-cum-TA",
        "placeholder": "Chọn Scheduler-cum-TA",
        "defaultValue": null,
        "span": 8,
        "options": userList.value.map(item => ({ label: item.fullName || item.username, value: item.id }))
      },
      {
        "type": "combobox",
        "key": "status",
        "label": "Trạng thái",
        "placeholder": "Tất cả",
        "defaultValue": null,
        "span": 8,
        "options": [{ label: "Tất cả", value: null }, ...getStatusOptions()]
      },
    ]
  },
  "table": {
    "columns": [
      { "key": "code", "label": "Mã Trường học", "width": "120px" },
      { "key": "name", "label": "Tên Trường Học" },
      {
        "key": "schoolTypeId",
        "label": "Cấp học",
        "width": "120px",
        "formatter": (row: any) => {
          const schoolType = schoolTypeOptions.value.find(st => st.id === row.schoolTypeId);
          return schoolType ? schoolType.name : 'N/A';
        }
      },
      {
        "key": "workingOffsiteId",
        "label": "Working Offsite",
        "width": "120px",
        "formatter": (row: any) => {
          const offsite = workingOffsiteList.value.find(wo => wo.id === row.workingOffsiteId);
          return offsite ? offsite.name : 'N/A';
        }
      },
      { "key": "areaCvct", "label": "Khu vực theo CVCT", "width": "120px" },
      {
        "key": "programs",
        "label": "Chương trình toán-khoa",
        "width": "120px",
        "formatter": (row: any) => {
          return Array.isArray(row.programs) ? row.programs.join(', ') : row.programs;
        }
      },
      {
        "key": "rhta",
        "label": "RHTA",
        "width": "120px",
        "formatter": (row: any) => {
          const user = userList.value.find(u => u.id === row.rhta);
          return user ? (user.fullName || user.username) : 'N/A';
        }
      },
      {
        "key": "scheduler",
        "label": "Scheduler",
        "width": "120px",
        "formatter": (row: any) => {
          const user = userList.value.find(u => u.id === row.scheduler);
          return user ? (user.fullName || user.username) : 'N/A';
        }
      },
      {
        "key": "schedulerCumTa",
        "label": "Scheduler-cum-TA",
        "width": "120px",
        "formatter": (row: any) => {
          const user = userList.value.find(u => u.id === row.schedulerCumTa);
          return user ? (user.fullName || user.username) : 'N/A';
        }
      },
      {
        "key": "status", "label": "Trạng thái", "width": "120px",
        "formatter": (row: any) => {
          return row.status === 1 ? 'Hiệu lực' : 'Hết hiệu lực';
        },
        "tagTypeFormatter": (row: any) => {
          return row.status === 1 ? 'success' : 'danger';
        }
      }
    ],
    "actions": [
      { "type": "edit", "label": "Sửa", "icon": "Edit" },
      { "type": "view", "label": "Xem", "icon": "View" },
      { "type": "delete", "label": "Xóa", "icon": "Delete", "buttonType": "danger" }
    ]
  },
  "popup": {
    "fields": [
      {
        "type": "text",
        "key": "code",
        "label": "Mã trường học",
        "placeholder": "Nhập mã trường học",
        "rules": [
          { required: true, message: 'Mã trường học không được bỏ trống', trigger: 'blur' },
          { max: 255, message: 'Mã trường học không được vượt quá 255 ký tự', trigger: 'blur' }
        ],
        "span": 12
      },
      {
        "type": "text",
        "key": "name",
        "label": "Tên trường học",
        "placeholder": "Nhập tên trường học",
        "rules": [
          { required: true, message: 'Tên trường học không được bỏ trống', trigger: 'blur' },
          { max: 255, message: 'Tên trường học không được vượt quá 255 ký tự', trigger: 'blur' }
        ],
        "span": 12
      },
      {
        "type": "combobox",
        "key": "schoolTypeId",
        "label": "Cấp học",
        "placeholder": "Chọn cấp học",
        "rules": [{ required: true, message: 'Cấp học không được bỏ trống', trigger: 'change' }],
        "options": schoolTypeOptions.value.map(item => ({ label: item.name, value: item.id })),
        "span": 12
      },
      {
        "type": "combobox",
        "key": "workingOffsiteId",
        "label": "Working Offsite",
        "placeholder": "Chọn nơi làm việc",
        "options": workingOffsiteList.value.map(item => ({ label: item.name, value: item.id })),
        "rules": [{ required: true, message: 'Working Offsite không được bỏ trống', trigger: 'change' }],
        "span": 12
      },
      {
        "type": "radio",
        "key": "isPremium",
        "label": "Premium School",
        "options": [{ label: 'Có', value: 1 }, { label: 'Không', value: 0 }],
        "defaultValue": 1,
        "rules": [{ required: true, message: 'Premium School không được bỏ trống', trigger: 'change' }],
        "span": 12
      },
      {
        "type": "checkbox",
        "key": "programs",
        "label": "Chương trình",
        "options": [
          { label: "Toán", value: "Toán" },
          { label: "Khoa học", value: "Khoa học" },
        ],
        "span": 12,
        "defaultValue": [],
        "rules": [{ type: 'array', required: true, message: 'Vui lòng chọn ít nhất một chương trình', trigger: 'change' }],
      },
      {
        "type": "text",
        "key": "mapLink",
        "label": "Link bản đồ",
        "placeholder": "Link bản đồ",
        "rules": [
          { required: true, message: 'Link bản đồ không được bỏ trống', trigger: 'blur' },
          { max: 2000, message: 'Link bản đồ không được vượt quá 2000 ký tự', trigger: 'blur' }
        ],
        "span": 12
      },
      {
        "type": "text",
        "key": "areaPhuong",
        "label": "Khu vực (Phường)",
        "placeholder": "Nhập khu vực Phường",
        "rules": [
          { required: true, message: 'Khu vực (Phường) không được bỏ trống', trigger: 'blur' },
          { max: 255, message: 'Khu vực (Phường) không được vượt quá 255 ký tự', trigger: 'blur' }
        ],
        "span": 12
      },
      {
        "type": "text",
        "key": "address",
        "label": "Địa chỉ",
        "placeholder": "Nhập địa chỉ",
        "rules": [
          { required: true, message: 'Địa chỉ không được bỏ trống', trigger: 'blur' },
          { max: 500, message: 'Địa chỉ không được vượt quá 500 ký tự', trigger: 'blur' }
        ],
        "span": 12
      },
      {
        "type": "text",
        "key": "areaCvct",
        "label": "Khu vực theo CVCT",
        "placeholder": "Nhập khu vực CVCT",
        "rules": [
          { required: true, message: 'Khu vực theo CVCT không được bỏ trống', trigger: 'blur' },
          { max: 255, message: 'Khu vực theo CVCT không được vượt quá 255 ký tự', trigger: 'blur' }
        ],
        "span": 12
      },
      {
        "type": "text",
        "key": "minuteNum",
        "label": "Số phút chấm công/Số phút thực tế",
        "placeholder": "Nhập số phút chấm công/số phút thực tế",
        "rules": [
          { required: true, message: 'Số phút không được bỏ trống', trigger: 'blur' },
          { pattern: /^\d+$/, message: 'Số phút phải là số nguyên dương', trigger: 'blur' },
          { max: 10, message: 'Số phút không được vượt quá 10 chữ số', trigger: 'blur' }
        ],
        "span": 12
      },
      {
        "type": "combobox",
        "key": "rhta",
        "label": "RHTA",
        "placeholder": "Chọn RHTA",
        "options": userList.value.map(item => ({ label: item.fullName || item.username, value: item.id })),
        "rules": [{ required: true, message: 'RHTA không được bỏ trống', trigger: 'change' }],
        "span": 8
      },
      {
        "type": "combobox",
        "key": "scheduler",
        "label": "Scheduler",
        "placeholder": "Chọn Scheduler",
        "options": userList.value.map(item => ({ label: item.fullName || item.username, value: item.id })),
        "rules": [{ required: true, message: 'Scheduler không được bỏ trống', trigger: 'change' }],
        "span": 8
      },
      {
        "type": "combobox",
        "key": "schedulerCumTa",
        "label": "Scheduler-cum-TA",
        "placeholder": "Chọn Scheduler-cum-TA",
        "options": userList.value.map(item => ({ label: item.fullName || item.username, value: item.id })),
        "rules": [{ required: true, message: 'Scheduler-cum-TA không được bỏ trống', trigger: 'change' }],
        "span": 8
      },
      {
        "type": "date",
        "key": "startYear",
        "label": "Năm học bắt đầu hợp tác",
        "placeholder": "YYYY",
        "format": "YYYY",
        "valueFormat": "YYYY",
        "rules": [{ required: true, message: 'Năm học bắt đầu hợp tác không được bỏ trống', trigger: 'change' }],
        "span": 12
      },
      {
        "type": "text",
        "key": "pacingGuides",
        "label": "Pacing guides",
        "placeholder": "Nhập Pacing guides",
        "rules": [
          { max: 255, message: 'Pacing guides không được vượt quá 255 ký tự', trigger: 'blur' }
        ],
        "span": 12
      },
      {
        "type": "combobox", // Giả định là combobox nếu có nhiều trạng thái
        "key": "status",
        "label": "Trạng thái",
        "placeholder": "Chọn trạng thái",
        "options": getStatusOptions(),
        "defaultValue": 1,
        "rules": [{ required: true, message: 'Trạng thái không được bỏ trống', trigger: 'change' }],
        "span": 12
      },
      {
        "type": "textArea",
        "key": "description",
        "label": "Mô tả",
        "placeholder": "Nhập mô tả",
        "rows": 3,
        "rules": [
          { max: 1000, message: 'Mô tả không được vượt quá 1000 ký tự', trigger: 'blur' }
        ],
        "span": 24
      },
      {
        "type": "upload",
        "key": "schoolMapFiles",
        "label": "Sơ đồ trường học",
        "limit": 1,
        "accept": ".png,.jpg,.jpeg,.pdf",
        "multiple": false,
        "showFileList": true,
        "span": 24
      }
    ]
  }
}));


const fetchSchoolData = async ({ filters, page, pageSize }: any) => {
  try {
    const response = await searchSchool(filters, page - 1, pageSize);
    const content = Array.isArray(response?.data?.data?.content) ? response.data.data.content : [];

    Object.assign(currentFilterQuery, filters);

    return {
      data: content.map((school: any) => ({
        ...school,
        programs: typeof school.programs === 'string' ? school.programs.split(',').map((p: string) => p.trim()) : school.programs || [],
      })),
      totalElements: response?.data?.data?.totalElements || 0,
      pageable: {
        pageNumber: (response.data.data.pageable?.pageNumber || 0) + 1,
        pageSize: response.data.data.pageable?.pageSize || pageSize,
        totalPages: Math.ceil((response.data.data.totalElements || 0) / (response.data.data.pageable?.pageSize || pageSize)),
      },
    };
  } catch (error) {
    ElNotification({ title: 'Lỗi', message: 'Không thể tải danh sách trường học.', type: 'error' });
    return {
      data: [],
      totalElements: 0,
      pageable: { pageNumber: 1, pageSize: pageSize || 10, totalPages: 0 },
    };
  }
};

const handleSchoolSaveOrUpdate = async (value: any) => {
  try {
    console.log('[START] handleSchoolSaveOrUpdate - Input Value:', value);

    const dataToSend = {
      ...value,
      programs: Array.isArray(value.programs) ? value.programs.join(',') : '',
    };

    console.log('[DATA TO SEND]', dataToSend);

    if (value.id) {
      console.log('[UPDATE] Updating school with ID:', value.id);
      await updateSchool(dataToSend);
      console.log('[UPDATE SUCCESS]');
      // handleSuccess(`Cập nhật trường học "${value.name}" thành công!`);
      ElMessage.success(`Cập nhật trường học "${value.name}" thành công!`);
    } else {
      console.log('[CREATE] Adding new school');
      await addSchool(dataToSend);
      console.log('[CREATE SUCCESS]');
      // handleSuccess(`Thêm trường học "${value.name}" thành công!`);
      ElMessage.success(`Thêm trường học "${value.name}" thành công!`);
    }

    console.log('[FETCH] Refreshing table data...');
    await fetchSchoolData({ filters: currentFilterQuery, page: 1, pageSize: 10 });
    console.log('[FETCH SUCCESS]');
  } catch (e) {
    console.error('[ERROR] handleSchoolSaveOrUpdate failed:', e);
    // handleErr('Có lỗi xảy ra khi lưu trường học.');
    ElMessage.error('Có lỗi xảy ra khi lưu trường học.');
  }
};


const handleSchoolDelete = async (
    school: any,
    refreshTable: () => void
) => {
  try {
    await showConfirmDialog(
        'Xóa',
        'Xác nhận xóa',
        `Bạn có chắc chắn muốn xóa trường học "${school.name}" không?`,
        'Xác nhận xóa',
        'Hủy'
    );

    await deleteSchool(school.id);

    handleSuccess(null, `Đã xóa trường học "${school.name}" thành công!`); // Đổi t thành null
    refreshTable();
  } catch (error: any) {
    if (error !== 'cancel') {
      console.error('Delete error:', error);
      handleErr(null, 'Có lỗi xảy ra khi xóa trường học.'); // Đổi t thành null
    }
  }
};

const handleSchoolImport = () => {
  ElNotification({ title: 'Thông báo', message: 'Chức năng Import đang phát triển.', type: 'info' });
};

const handleSchoolExport = async () => {
  try {
    const response = await exportSchoolExcel(currentFilterQuery);

    const blob = new Blob([response.data], {
      type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
    });

    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;

    const timestamp = new Date().toISOString().replace(/[-:.]/g, '').slice(0, 14);
    link.download = `school_export_${timestamp}.xlsx`;

    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    window.URL.revokeObjectURL(url);

    ElMessage.success('Xuất Excel thành công!');
  } catch (error) {
    ElMessage.error('Xuất Excel thất bại!');
  }
};

onMounted(() => {
  loadSelectOptions();
});
</script>

<style lang="scss" scoped>
.box {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: 100vh;
}
</style>