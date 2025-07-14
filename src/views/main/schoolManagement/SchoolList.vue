<template>
  <div>
    <SearchTable
        :config="schoolConfig"
        :fetchData="fetchSchoolData"
        :handle-save="handleSchoolSaveOrUpdate"
        :handleDelete="handleSchoolDelete"
        :handleExport="handleSchoolExport"
        :handleImport="handleSchoolImport"
        :loading="isTableLoading"
    />
  </div>
</template>

<script setup lang="ts">
import {ref, reactive, computed, onMounted, nextTick} from 'vue';
import SearchTable from "@/views/main/dynamicForm/SearchTable.vue";

import { ElNotification, ElMessage, ElMessageBox } from 'element-plus';

import {
  searchSchool,
  addSchool,
  updateSchool,
  deleteSchool,
  exportSchoolExcel, importSchoolExcel
} from '@/api/school';
import { fetchAllSchoolType } from '@/api/schoolType';
import { fetchAllTblUser } from "@/api/tblUser";
import { fetchAllWorkingOffsite } from "@/api/workingOffsite";
import {useI18n} from "vue-i18n";

const { t } = useI18n();

const schoolTypeOptions = ref<any[]>([]);
const userList = ref<any[]>([]);
const workingOffsiteList = ref<any[]>([]);
const isTableLoading = ref(false);

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
    { "value": 1, "label": t('message.menu.schoolManage.effective') },
    { "value": 0, "label": t('message.menu.schoolManage.inactive') },
  ];
};

const schoolConfig = computed(() => ({
  title: t('message.menu.schoolManage.self'),
  "searchForm": {
    "fields": [
      {
        "type": "combobox",
        "key": "schoolTypeId",
        "label": t('message.menu.schoolManage.schoolType'),
        "placeholder":  t('message.menu.schoolManage.placeholder.select', { field: t('message.menu.schoolManage.schoolType') }),
        "defaultValue": null,
        "span": 6,
        "options": schoolTypeOptions.value.map(item => ({ label: item.name, value: item.id }))
      },
      {
        "type": "text",
        "key": "name",
        "label": t('message.menu.schoolManage.schoolName'),
        "placeholder": t('message.menu.schoolManage.placeholder.select', { field: t('message.menu.schoolManage.schoolName') }),
        "defaultValue": "",
        "span": 6,
      },
      {
        "type": "text",
        "key": "code",
        "label": t('message.menu.schoolManage.schoolCode'),
        "placeholder": t('message.menu.schoolManage.placeholder.select', { field: t('message.menu.schoolManage.schoolCode') }),
        "defaultValue": "",
        "span": 6,
      },
      {
        "type": "combobox",
        "key": "workingOffsiteId",
        "label": t('message.menu.schoolManage.workingOffsite'),
        "placeholder": t('message.menu.schoolManage.placeholder.select', { field: t('message.menu.schoolManage.workingOffsite') }),
        "defaultValue": null,
        "span": 6,
        "options": workingOffsiteList.value.map(item => ({ label: item.name, value: item.id }))
      },
      {
        "type": "text",
        "key": "areaCvct",
        "label": t('message.menu.schoolManage.areaCvct'),
        "placeholder": t('message.menu.schoolManage.placeholder.select', { field: t('message.menu.schoolManage.areaCvct') }),
        "defaultValue": "",
        "span": 6
      },
      {
        "type": "combobox",
        "key": "programs",
        "label": t('message.menu.schoolManage.programs'),
        "placeholder": t('message.menu.schoolManage.placeholder.all'),
        "defaultValue": "",
        "span": 6,
        "options": [
          { label: t('message.menu.schoolManage.placeholder.all'), value: "" },
          { label: t('message.menu.schoolManage.programMath'), value: "Toán" },
          { label: t('message.menu.schoolManage.programScience'), value: "Khoa học" }
        ]
      },
      {
        "type": "combobox",
        "key": "rhta",
        "label": t('message.menu.schoolManage.rhta'),
        "placeholder": t('message.menu.schoolManage.placeholder.select', { field: t('message.menu.schoolManage.rhta') }),
        "defaultValue": null,
        "span": 6,
        "options": userList.value.map(item => ({ label: item.username, value: item.id }))
      },
      {
        "type": "combobox",
        "key": "scheduler",
        "label": t('message.menu.schoolManage.scheduler'),
        "placeholder": t('message.menu.schoolManage.placeholder.select', { field: t('message.menu.schoolManage.scheduler') }),
        "defaultValue": null,
        "span": 6,
        "options": userList.value.map(item => ({ label: item.username , value: item.id }))
      },
      {
        "type": "combobox",
        "key": "schedulerCumTa",
        "label": t('message.menu.schoolManage.schedulerCumTa'),
        "placeholder": t('message.menu.schoolManage.placeholder.select', { field: t('message.menu.schoolManage.schedulerCumTa') }),
        "defaultValue": null,
        "span": 6,
        "options": userList.value.map(item => ({ label: item.username, value: item.id }))
      },
      {
        "type": "combobox",
        "key": "status",
        "label": t('message.menu.schoolManage.status'),
        "placeholder": t('message.menu.schoolManage.placeholder.all'),
        "defaultValue": null,
        "span": 6,
        "options": [{ label: t('message.menu.schoolManage.placeholder.all'), value: null }, ...getStatusOptions()]
      },
    ]
  },
  "table": {
    "columns": [
      { "key": "code", "label": t('message.menu.schoolManage.schoolCode'), "width": "120px" },
      { "key": "name", "label": t('message.menu.schoolManage.schoolName') },
      {
        "key": "schoolTypeName",
        "label": t('message.menu.schoolManage.schoolType'),
        "width": "120px",
      },
      {
        "key": "workingOffsiteName",
        "label": t('message.menu.schoolManage.workingOffsite'),
        "width": "120px",
      },
      { "key": "areaCvct", "label": t('message.menu.schoolManage.areaCvct'), "width": "120px" },
      {
        "key": "programs",
        "label": t('message.menu.schoolManage.programs'),
        "width": "120px",
        "formatter": (row: any) => {
          return Array.isArray(row.programs) ? row.programs.join(', ') : row.programs;
        }
      },
      {
        "key": "rhtaName",
        "label": t('message.menu.schoolManage.rhta'),
        "width": "120px",
      },
      {
        "key": "schedulerName",
        "label": t('message.menu.schoolManage.scheduler'),
        "width": "120px",
      },
      {
        "key": "schedulerCumTaName",
        "label": t('message.menu.schoolManage.schedulerCumTa'),
        "width": "120px",
      },
      {
        "key": "status", "label": t('message.menu.schoolManage.status'), "width": "120px",
        "formatter": (row: any) => {
          return row.status === 1 ? 'Hiệu lực' : 'Hết hiệu lực';
        },
        "tagTypeFormatter": (row: any) => {
          return row.status === 1 ? 'success' : 'danger';
        }
      }
    ],
    "actions": [
      { "type": "edit", "label": t('message.menu.schoolManage.actions.edit'), "icon": "Edit" },
      { "type": "view", "label": t('message.menu.schoolManage.actions.view'), "icon": "View" },
      { "type": "delete", "label": t('message.menu.schoolManage.actions.delete'), "icon": "Delete", "buttonType": "danger" }
    ]
  },
  "popup": {
    "fields": [
      {
        type: "text",
        key: "code",
        label: t('message.menu.schoolManage.schoolCode'),
        placeholder: t('message.menu.schoolManage.placeholder.input', {
          field: t('message.menu.schoolManage.schoolCode')
        }),
        rules: [
          {
            required: true,
            message: t('message.menu.schoolManage.validation.required', {
              field: t('message.menu.schoolManage.schoolCode')
            }),
            trigger: 'blur'
          },
          {
            max: 100,
            message: t('message.menu.schoolManage.validation.max', {
              field: t('message.menu.schoolManage.schoolCode'),
              max: 100
            }),
            trigger: 'blur'
          }
        ]
      },
      {
        type: "text",
        key: "name",
        label: t('message.menu.schoolManage.schoolName'),
        placeholder: t('message.menu.schoolManage.placeholder.input', {
          field: t('message.menu.schoolManage.schoolName')
        }),
        rules: [
          {
            required: true,
            message: t('message.menu.schoolManage.validation.required', {
              field: t('message.menu.schoolManage.schoolName')
            }),
            trigger: 'blur'
          },
          {
            max: 255,
            message: t('message.menu.schoolManage.validation.max', {
              field: t('message.menu.schoolManage.schoolName'),
              max: 255
            }),
            trigger: 'blur'
          }
        ]
      },
      {
        type: "combobox",
        key: "schoolTypeId",
        label: t('message.menu.schoolManage.schoolType'),
        placeholder: t('message.menu.schoolManage.placeholder.select', {
          field: t('message.menu.schoolManage.schoolType')
        }),
        rules: [
          {
            required: true,
            message: t('message.menu.schoolManage.validation.required', {
              field: t('message.menu.schoolManage.schoolType')
            }),
            trigger: 'blur'
          }
        ],
        options: schoolTypeOptions.value.map(item => ({
          label: item.code,
          value: item.id
        }))
      },
      {
        type: "combobox",
        key: "workingOffsiteId",
        label: t('message.menu.schoolManage.workingOffsite'),
        placeholder: t('message.menu.schoolManage.placeholder.select', {
          field: t('message.menu.schoolManage.workingOffsite')
        }),
        rules: [
          {
            required: true,
            message: t('message.menu.schoolManage.validation.required', {
              field: t('message.menu.schoolManage.workingOffsite')
            }),
            trigger: 'blur'
          }
        ],
        options: workingOffsiteList.value.map(item => ({
          label: item.code,
          value: item.id
        }))
      },
      {
        type: "radio",
        key: "isPremium",
        label: t('message.menu.schoolManage.isPremium'),
        options: [
          { label: t('message.menu.schoolManage.yes'), value: 1 },
          { label: t('message.menu.schoolManage.no'), value: 0 }
        ],
        defaultValue: 1,
        rules: [
          {
            required: true,
            message: t('message.menu.schoolManage.validation.required', {
              field: t('message.menu.schoolManage.isPremium')
            }),
            trigger: 'blur'
          }
        ]
      },
      {
        type: "checkbox",
        key: "programs",
        label: t('message.menu.schoolManage.programs'),
        options: [
          { label: t('message.menu.schoolManage.programMath'), value: "Toán" },
          { label: t('message.menu.schoolManage.programScience'), value: "Khoa học" }
        ],
        defaultValue: [],
        rules: [
          {
            type: 'array',
            required: true,
            message: t('message.menu.schoolManage.validation.minOneProgram'),
            trigger: 'blur'
          }
        ]
      },
      {
        type: "text",
        key: "map",
        label: t('message.menu.schoolManage.map'),
        placeholder: t('message.menu.schoolManage.placeholder.input', {
          field: t('message.menu.schoolManage.map')
        }),
        rules: [
          {
            required: true,
            message: t('message.menu.schoolManage.validation.required', {
              field: t('message.menu.schoolManage.map')
            }),
            trigger: 'blur'
          },
          {
            max: 1000,
            message: t('message.menu.schoolManage.validation.max', {
              field: t('message.menu.schoolManage.map'),
              max: 1000
            }),
            trigger: 'blur'
          }
        ]
      },
      {
        type: "text",
        key: "area",
        label: t('message.menu.schoolManage.area'),
        placeholder: t('message.menu.schoolManage.placeholder.input', {
          field: t('message.menu.schoolManage.area')
        }),
        rules: [
          {
            required: true,
            message: t('message.menu.schoolManage.validation.required', {
              field: t('message.menu.schoolManage.area')
            }),
            trigger: 'blur'
          },
          {
            max: 255,
            message: t('message.menu.schoolManage.validation.max', {
              field: t('message.menu.schoolManage.area'),
              max: 255
            }),
            trigger: 'blur'
          }
        ]
      },
      {
        type: "text",
        key: "address",
        label: t('message.menu.schoolManage.address'),
        placeholder: t('message.menu.schoolManage.placeholder.input', {
          field: t('message.menu.schoolManage.address')
        }),
        rules: [
          {
            required: true,
            message: t('message.menu.schoolManage.validation.required', {
              field: t('message.menu.schoolManage.address')
            }),
            trigger: 'blur'
          },
          {
            max: 255,
            message: t('message.menu.schoolManage.validation.max', {
              field: t('message.menu.schoolManage.address'),
              max: 255
            }),
            trigger: 'blur'
          }
        ]
      },
      {
        type: "text",
        key: "areaCvct",
        label: t('message.menu.schoolManage.areaCvct'),
        placeholder: t('message.menu.schoolManage.placeholder.input', {
          field: t('message.menu.schoolManage.areaCvct')
        }),
        rules: [
          {
            required: true,
            message: t('message.menu.schoolManage.validation.required', {
              field: t('message.menu.schoolManage.areaCvct')
            }),
            trigger: 'blur'
          },
          {
            max: 255,
            message: t('message.menu.schoolManage.validation.max', {
              field: t('message.menu.schoolManage.areaCvct'),
              max: 255
            }),
            trigger: 'blur'
          }
        ]
      },
      {
        type: "text",
        key: "minuteNum",
        label: t('message.menu.schoolManage.minuteNum'),
        placeholder: t('message.menu.schoolManage.placeholder.input', {
          field: t('message.menu.schoolManage.minuteNum')
        }),
        rules: [
          {
            required: true,
            message: t('message.menu.schoolManage.validation.required', {
              field: t('message.menu.schoolManage.minuteNum')
            }),
            trigger: 'blur'
          },
          {
            validator: (rule, value, callback) => {
              if (!value) {
                callback(new Error(t('message.menu.schoolManage.validation.required', {
                  field: t('message.menu.schoolManage.minuteNum')
                })));
              } else if (!/^\d+$/.test(value)) {
                callback(new Error(t('message.menu.schoolManage.validation.number', {
                  field: t('message.menu.schoolManage.minuteNum')
                })));
              } else if (value.length < 1 || value.length > 10) {
                callback(new Error(t('message.menu.schoolManage.validation.length', {
                  field: t('message.menu.schoolManage.minuteNum'),
                  min: 1,
                  max: 10
                })));
              } else {
                callback();
              }
            },
            trigger: 'blur'
          }
        ]
      },
      {
        type: "combobox",
        key: "rhtaId",
        label: t('message.menu.schoolManage.rhta'),
        placeholder: t('message.menu.schoolManage.placeholder.select', {
          field: t('message.menu.schoolManage.rhta')
        }),
        options: userList.value.map(item => ({
          label: item.username,
          value: item.id
        })),
        rules: [
          {
            required: true,
            message: t('message.menu.schoolManage.validation.required', {
              field: t('message.menu.schoolManage.rhta')
            }),
            trigger: 'blur'
          }
        ]
      },
      {
        type: "combobox",
        key: "schedulerId",
        label: t('message.menu.schoolManage.scheduler'),
        placeholder: t('message.menu.schoolManage.placeholder.select', {
          field: t('message.menu.schoolManage.scheduler')
        }),
        options: userList.value.map(item => ({
          label: item.username,
          value: item.id
        })),
        rules: [
          {
            required: true,
            message: t('message.menu.schoolManage.validation.required', {
              field: t('message.menu.schoolManage.scheduler')
            }),
            trigger: 'blur'
          }
        ]
      },
      {
        type: "combobox",
        key: "schedulerCumTaId",
        label: t('message.menu.schoolManage.schedulerCumTa'),
        placeholder: t('message.menu.schoolManage.placeholder.select', {
          field: t('message.menu.schoolManage.schedulerCumTa')
        }),
        options: userList.value.map(item => ({
          label: item.username,
          value: item.id
        })),
        rules: [
          {
            required: true,
            message: t('message.menu.schoolManage.validation.required', {
              field: t('message.menu.schoolManage.schedulerCumTa')
            }),
            trigger: 'blur'
          }
        ]
      },
      {
        type: "date",
        key: "startYear",
        label: t('message.menu.schoolManage.startYear'),
        placeholder: t('message.menu.schoolManage.placeholder.select', {
          field: t('message.menu.schoolManage.startYear')
        }),
        format: "YYYY",
        valueFormat: "DD/MM/YYYY",
        rules: [
          {
            required: true,
            message: t('message.menu.schoolManage.validation.required', {
              field: t('message.menu.schoolManage.startYear')
            }),
            trigger: 'change'
          }
        ]
      },
      {
        type: "combobox",
        key: "status",
        label: t('message.menu.schoolManage.status'),
        placeholder: t('message.menu.schoolManage.placeholder.select', {
          field: t('message.menu.schoolManage.status')
        }),
        defaultValue: 1,
        options: getStatusOptions(),
        rules: [
          {
            required: true,
            message: t('message.menu.schoolManage.validation.required', {
              field: t('message.menu.schoolManage.status')
            }),
            trigger: 'blur'
          }
        ]
      },
      {
        type: "textArea",
        key: "description",
        label: t('message.menu.schoolManage.description'),
        placeholder: t('message.menu.schoolManage.placeholder.input', {
          field: t('message.menu.schoolManage.description')
        }),
        rows: 3,
        rules: [
          {
            max: 1000,
            message: t('message.menu.schoolManage.validation.max', {
              field: t('message.menu.schoolManage.description'),
              max: 1000
            }),
            trigger: 'blur'
          }
        ]
      },
      // {
      //   "type": "upload",
      //   "key": "schoolDiagram",
      //   "label": "Sơ đồ trường học",
      //   "limit": 1,
      //   "accept": ".png,.jpg,.jpeg,.pdf",
      //   "multiple": false,
      //   "showFileList": true,
      // }
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
        programs: typeof school.programs === 'string'
            ? school.programs.split(',').map((p: string) => p.trim())
            : school.programs || [],
      })),
      totalElements: response?.data?.data?.totalElements || 0,
      pageable: {
        pageNumber: (response.data.data.pageable?.pageNumber || 0) + 1,
        pageSize: response.data.data.pageable?.pageSize || pageSize,
        totalPages: Math.ceil(
            (response.data.data.totalElements || 0) /
            (response.data.data.pageable?.pageSize || pageSize)
        ),
      },
    };
  } catch (error) {
    ElNotification({
      title: t('message.menu.common.err'),
      message: t('message.menu.schoolManage.message.loadError'),
      type: 'error',
    });
    return {
      data: [],
      totalElements: 0,
      pageable: { pageNumber: 1, pageSize: pageSize || 10, totalPages: 0 },
    };
  }
};

const handleSchoolSaveOrUpdate = async (value: any) => {
  isTableLoading.value = true;
  try {
    const dataToSend = {
      ...value,
      programs: Array.isArray(value.programs) ? value.programs.join(',') : '',
      schoolDiagram:
          Array.isArray(value.schoolDiagram) && value.schoolDiagram.length > 0
              ? value.schoolDiagram[0].url || value.schoolDiagram[0].response?.url || ''
              : '',
    };

    if (value.id) {
      await updateSchool(dataToSend);
      ElMessage.success(t('message.menu.schoolManage.message.updateSuccess', { name: value.name }));
    } else {
      await addSchool(dataToSend);
      ElMessage.success(t('message.menu.schoolManage.message.addSuccess', { name: value.name }));
    }

    await fetchSchoolData({ filters: currentFilterQuery, page: 1, pageSize: 10 });
    await nextTick();
  } catch (e) {
    console.error('Lỗi khi lưu:', e);
    ElMessage.error(t('message.menu.schoolManage.message.saveError'));
  } finally {
    isTableLoading.value = false;
  }
};

const handleSchoolDelete = async (school: any) => {
  try {
    await deleteSchool(school.id);
    ElMessage.success(t('message.menu.schoolManage.message.deleteSuccess', { name: school.name }));

    await fetchSchoolData({ filters: currentFilterQuery, page: 1, pageSize: 10 });
  } catch (error: any) {
    console.error('Delete error:', error);
    ElMessage.error(t('message.menu.schoolManage.message.deleteError'));
  }
};

const handleSchoolImport = async (formData: FormData) => {
  try {
    const response = await importSchoolExcel(formData.get('file'));

    const contentType = response.type;

    if (
        contentType === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' ||
        contentType === 'application/octet-stream'
    ) {
      return new Blob([response], { type: contentType });
    }

    return response;
  } catch (error) {
    throw error;
  }
};

const handleSchoolExport = async () => {
  try {
    const requestData = { ...currentFilterQuery };
    const response = await exportSchoolExcel(requestData);

    const url = window.URL.createObjectURL(response.data);
    const link = document.createElement('a');
    const fileName = 'report_school_' + new Date().getTime() + '.xlsx';
    link.href = url;
    link.setAttribute('download', fileName);
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    window.URL.revokeObjectURL(url);

    ElMessage.success(t('message.menu.schoolManage.message.exportSuccess'));
  } catch (error: any) {
    if (error === 'cancel') {
      ElMessage.info(t('message.menu.schoolManage.message.exportCancel'));
    } else {
      ElMessage.error(t('message.menu.schoolManage.message.exportFailed'));
      console.error(error);
    }
  }
};



onMounted(() => {
  loadSelectOptions();
});
</script>

<style lang="scss" scoped>

</style>