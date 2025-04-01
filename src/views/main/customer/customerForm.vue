<template>
  <div class="search-extend" v-loading="loading">
    <div class="box">
      <div class="layout-container-form flex space-between">
        <el-form
            ref="formRef"
            style="width: 100%"
            :model="dataCustomer"
            :rules="ruleForm"
            status-icon
            @submit.prevent=""
            label-position="top"
        >
          <div class="demo-collapse no-border-collapse" style="width: 100%;">
            <el-collapse :model-value="activeNames">
              <el-collapse-item name="2">
                <template #title>
                  <h3 class="style-header-box">Thông tin chung</h3>
                </template>
                <el-row :gutter="24" style="margin: 0">
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item prop="customerType" label="Loại khách hàng" :size="'default'"
                                  style="display: inherit">
                      <el-select v-model="dataCustomer.customerType" placeholder="Chọn giá trị" style="width: 100%"
                                 :disabled="isMode === 'view'"
                                 filterable clearable
                      >
                        <el-option
                            v-for="item in appParams.fetchCustomerType()"
                            :key="item?.id"
                            :label="item?.valueLabel"
                            :value="item?.valueCode"
                        />
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item prop="name" label="Tên khách hàng" :size="'default'">
                      <el-input v-model="dataCustomer.name" clearable
                                @blur="deepTrim(dataCustomer)"
                                :disabled="isMode === 'view'"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item prop="tel" label="Số điện thoại" :size="'default'">
                      <el-input v-model="dataCustomer.tel" clearable
                                @blur="deepTrim(dataCustomer)"
                                :disabled="isMode === 'view'"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item prop="email" label="Email khách hàng" :size="'default'">
                      <el-input v-model="dataCustomer.email" clearable
                                @blur="deepTrim(dataCustomer)"
                                :disabled="isMode === 'view'"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item prop="province" label="Tỉnh/Thành phố" :size="'default'"
                                  style="display: inherit">
                      <el-select v-model="dataCustomer.province" placeholder="Chọn giá trị" style="width: 100%"
                                 filterable clearable
                                 reserve-keyword
                                 :disabled="isMode === 'view'"
                                 @change="dataCustomer.district=null;
                                          dataCustomer.precinct = null;
                                          fetchDistrict(dataCustomer.province as string);">
                        <el-option
                            v-for="item in provinceData"
                            :key="item?.areaCode"
                            :label="item.name"
                            :value="item?.areaCode"
                        />
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item prop="district" label="Quận/Huyện" :size="'default'" style="display: inherit">
                      <el-tooltip content="Quận/Huyện"
                                  placement="top">
                        <el-select v-model="dataCustomer.district" placeholder="Chọn giá trị" style="width: 100%"
                                   filterable clearable
                                   reserve-keyword
                                   @change="fetchPrecinct(dataCustomer.district as string);
                                            dataCustomer.precinct = null;"
                                   :disabled="isMode === 'view' || !dataCustomer.province">
                          <el-option
                              v-for="item in districtData"
                              :key="item?.areaCode"
                              :label="item.name"
                              :value="item?.areaCode"
                          />
                        </el-select>
                      </el-tooltip>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item label="Phường/Xã" :size="'default'" style="display: inherit">
                      <el-tooltip content="Phường/Xã" placement="top">
                        <el-select v-model="dataCustomer.precinct"
                                   placeholder="Chọn giá trị" style="width: 100%"
                                   filterable clearable
                                   reserve-keyword
                                   :disabled="isMode === 'view' || !dataCustomer.district">
                          <el-option
                              v-for="item in precinctData"
                              :key="item?.areaCode"
                              :label="item.name"
                              :value="item?.areaCode"
                          />
                        </el-select>
                      </el-tooltip>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="12" :lg="12" class="form-item">
                    <el-form-item label="Địa chỉ cụ thể" :size="'default'">
                      <el-input v-model="dataCustomer.address" clearable
                                :disabled="isMode === 'view'"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-collapse-item>
            </el-collapse>
          </div>
          <div class="demo-collapse no-border-collapse" style="width: 100%;">
            <el-collapse :model-value="activeNames">
              <el-collapse-item name="2">
                <template #title>
                  <h3 class="style-header-box" style="width: 300px; text-align: left">Thông tin giấy tờ khách hàng</h3>
                  <div style="display: flex; justify-content: right; width: 100%; margin-right: 20px"
                       v-if="isMode !== 'view'">
                    <el-button :icon="Plus" size="default"
                               color="var(--system-primary-color)"
                               type="primary" @click="addProperty()">Thêm
                      mới
                    </el-button>
                  </div>
                </template>
                <div class="layout-container-table">
                  <el-table :data="dataCustomer.customerDetails" border stripe>
                    <el-table-column
                        type="index"
                        label="STT"
                        width="120"
                        align="center"
                        header-align="center"
                    >
                      <template #default="scope">
                        {{ (pagination.current - 1) * pagination.pageSize + scope.$index + 1 }}
                      </template>
                    </el-table-column>

                    <el-table-column prop="type" header-align="center" align="left">
                      <template #header>
                        <span style="color: var(--el-color-danger);">*</span> <span>Loại giấy tờ</span>
                      </template>
                      <template #default="scope">
                        <el-form-item :size="'default'"
                                      class="form-table"
                                      :prop="'customerDetails.' + scope.$index + '.type'"
                                      :rules="rules.type"
                        >
                          <el-select v-model="dataCustomer.customerDetails[scope.$index].type"
                                     placeholder="Chọn giá trị"
                                     filterable
                                     size="default"
                                     reserve-keyword
                                     :disabled="isMode === 'view'"
                                     clearable>
                            <el-option
                                v-for="item in availableOptions(scope.$index)"
                                :key="item?.id"
                                :label="item?.valueLabel"
                                :value="item?.valueCode">
                            </el-option>
                          </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column>

                    <el-table-column prop="value" header-align="center"
                                     align="center">
                      <template #header>
                        <span style="color: var(--el-color-danger);">*</span> <span>Số giấy tờ</span>
                      </template>
                      <template #default="scope">
                        <el-form-item :rules="rules.value" :size="'default'"
                                      class="form-table"
                                      :prop="'customerDetails.' + scope.$index + '.value'"
                        >
                          <el-input v-model="dataCustomer.customerDetails[scope.$index].value"
                                    :disabled="isMode === 'view'" clearable
                                    style="height: 32px;"
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="description" :label="'Mô tả'" header-align="center"
                                     align="center">
                      <template #default="scope">
                        <el-form-item :rules="rules.description" :size="'default'"
                                      class="form-table"
                                      :prop="'customerDetails.' + scope.$index + '.description'"
                        >
                          <el-input v-model="dataCustomer.customerDetails[scope.$index].description"
                                    :disabled="isMode === 'view'" clearable
                                    style="height: 32px;"
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="files" :label="'Tệp đính kèm'" header-align="center"
                                     align="center">
                      <template #default="scope">
                        <div v-if="isMode === 'view'">
                          <div v-for="(file, index) in fileList[scope.$index] || []" :key="index">
                            <span @click="handleFilePreview(file)" style="cursor: pointer">{{ file.name }}</span>
                          </div>
                        </div>
                        <el-upload
                            v-else
                            :disabled="isMode == 'view'"
                            :limit="5"
                            class="upload-demo" drag
                            :file-list="fileList[scope.$index] || []"
                            :auto-upload="false"
                            :on-change="(file, fileList) => handleFileChange(scope.$index, fileList)"
                            :on-preview="handleFilePreview"
                            :on-remove="handleFileRemove"
                            :style="isMode !== 'view'? { border: 'none' }: null"
                        >
                          <template v-if="isMode !== 'view'">
                            <el-button plain size="default" style="height: 32px; width: 100% !important;">Chọn File
                            </el-button>
                          </template>
                        </el-upload>
                      </template>
                    </el-table-column>
                    <el-table-column v-if="isMode !== 'view'" :label="'Hành động'" align="center"
                                     fixed="right"
                                     width="120">
                      <template #default="scope">
                        <el-tooltip :content="'Xóa'" placement="top">
                          <el-button style="width: 40px; margin: 0" type="danger" text :icon="Delete"
                                     @click="removeProperty(scope.$index)">
                          </el-button>
                        </el-tooltip>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </el-collapse-item>
            </el-collapse>
          </div>
          <FormConfigDynamic :product="productData" :active-names="activeNames" :config="config" :data="dataCustomer"
                             :is-mode="isMode"/>
          <div v-if="isMode !== MODE_CREATE()" class="demo-collapse no-border-collapse" style="width: 100%;">
            <el-collapse :model-value="activeNames">
              <el-collapse-item>
                <template #title>
                  <h3 class="style-header-box">Lịch sử tác động</h3>
                </template>
                <div class="layout-container-table">
                  <History :id="dataCustomer.customerId" :type="AUDIT_TYPE_CUSTOMER()"></History>
                </div>
              </el-collapse-item>
            </el-collapse>
          </div>
        </el-form>
      </div>
    </div>
  </div>
  <div class="button-container">
    <el-button :icon="Refresh" size="default" @click="back" plain>Quay lại</el-button>
    <el-button v-if="isMode === 'create'" :icon="Add" size="default" type="primary"
               color="var(--system-primary-color)"
               @click="submitForm(formRef)" :loading="loading">
      Thêm mới
    </el-button>
    <el-button v-else-if="isMode === 'edit'" :icon="Edit" size="default" type="primary"
               color="var(--system-primary-color)"
               @click="submitForm(formRef)">
      Cập nhật
    </el-button>
  </div>

</template>


<script lang="ts">
import {computed, defineComponent, onMounted, reactive, ref, watch} from "vue";

import {CirclePlus, Delete, Document, Download, Edit, Plus, Refresh, Search, Upload} from "@element-plus/icons";
import {hasPermission} from "@/router/permission/front";
import {ElForm, ElNotification, FormInstance, UploadFile} from "element-plus";
import {useRouter} from "vue-router";
import {useStore} from 'vuex'
import {getProvince, getProvinceByParentCode} from "@/api/area";
import {deepTrim, downloadBlob} from "@/utils/mixins/mixin";
import {appParams} from "@/constants/app-param";
import {phoneValidator, validateEmailInvalid} from "@/utils/validate/helpers";
import {addCustomer, getCustomerDetail, updateCustomer} from "@/api/customer";
import FormConfigDynamic from "@/views/main/dynamicForm/FormConfigDynamic.vue";
import {getDataConfigDynamic} from "@/api/systemDynamicConfig";
import {downFile, getFiles, uploadFiles} from "@/api/uploadFile";
import {CUSTOMER_UPLOAD} from "@/constants/const";
import {fetchAllProduct} from "@/api/product";
import History from "@/views/main/history/History.vue";
import {AUDIT_TYPE_CUSTOMER} from "@/constants/actionAudit";
import {MODE_CREATE} from "@/constants/formMode";

export default defineComponent({
  components: {History, FormConfigDynamic},
  props: {
    isView: Boolean,
    investigationRequest: Object,
    mode: {
      type: String,
      default: 'create', // 'create', 'edit', 'view'
    },
  },
  methods: {
    MODE_CREATE() {
      return MODE_CREATE
    },
    AUDIT_TYPE_CUSTOMER() {
      return AUDIT_TYPE_CUSTOMER
    },
    deepTrim,
    hasPermission
  },
  computed: {
    Upload() {
      return Upload
    },
    appParams() {
      return appParams
    },
    Plus() {
      return Plus
    },
    Download() {
      return Download
    },
    Refresh() {
      return Refresh
    },
    Delete() {
      return Delete
    }, Edit() {
      return Edit
    },
    Add() {
      return CirclePlus
    },
    Search() {
      return Search
    },
    Document() {
      return Document
    },
  },
  setup(props, {emit}) {
    const handleFilePreview = async (file: { uuid: any; name: string; }) => {
      if (file && file.uuid) {
        let params = {filename: file.uuid}
        let axiosResponse = await downFile(params);
        if (axiosResponse?.status === 200) {
          downloadBlob(axiosResponse.data, file.name);
        }
      }
    };
    const handleFileRemove = (file) => {
      if (file.uuid) {
        let fileRemove = {fileName: file.name, uuid: file.uuid}
        filesRemove.value.push(fileRemove)
      }
    };

    const filesRemove = ref([]);
    const store = useStore()
    const dataCustomer = reactive(
        {
          customerId: null,
          customerType: null,
          tel: null,
          name: null,
          email: null,
          province: null,
          district: null,
          precinct: null,
          address: null,
          customerDetails: [] as any,
          customValue: [],
          filesRemove: []
        });
    let loading = ref(false);
    const productData = ref([]);
    const provinceData = ref([]);
    const districtData = ref([]);
    const precinctData = ref([]);
    const propertyData = ref(appParams.listIdentification())
    const valueData = ref([]);
    const router = useRouter();
    const fileList = reactive<Record<number, UploadFile[]>>({});
    let isMode = router.currentRoute.value.params?.mode;
    let visible: any = ref(false);
    const pagination = reactive({
      current: 1,
      total: 0,
      pageSize: 10,
      size: 10,
    });
    const isViewFinger = ref(false)
    const checked = ref(false)
    const activeNames = ref(['1', '2']);
    const formRef = ref<FormInstance>();
    let config = reactive([]);
    const linkImg = ref();
    const ruleFormRef = ref<InstanceType<typeof ElForm> | null>(null);
    const ruleForm = reactive({
      name: [
        {required: true, message: 'Vui lòng nhập Tên khách hàng', trigger: 'blur'},
        {min: 1, max: 100, message: 'Tên khách hàng tối đa 100 ký tự', trigger: 'blur'}
      ],
      customerType: [
        {required: true, message: 'Vui lòng chọn Loại khách hàng', trigger: 'blur'},
      ],
      address: [
        {min: 1, max: 500, message: 'Địa chỉ cụ thể tối đa 500 ký tự', trigger: 'blur'}
      ],
      tel: [
        {validator: (rule: any, value: any, callback: any) => phoneValidator(rule, value, callback, 'Số điện thoại đối tác không đúng định dạng')},
      ],
      email: [
        {validator: (rule: any, value: any, callback: any) => validateEmailInvalid(rule, value, callback, 'Email đối tác không đúng định dạng')},
        {min: 1, max: 100, message: 'Email đối tác tối đa 100 ký tự', trigger: 'blur'}
      ],
      district: [{required: true, message: 'Vui lòng chọn Quận/huyện', trigger: 'change'}],
      province: [{required: true, message: 'Vui lòng chọn Tỉnh/thành phố', trigger: 'change'}],
    });

    const rules = reactive({
      type: [
        {required: true, message: 'Vui lòng chọn Loại giấy tờ', trigger: 'change'},
      ],
      value: [
        {required: true, message: 'Vui lòng nhập Số giấy tờ', trigger: 'change'},
        {min: 1, max: 50, message: 'Số giấy tờ tối đa 50 ký tự', trigger: 'blur'}
      ],
      description: [
        {min: 1, max: 500, message: 'Mô tả tối đa 500 ký tự', trigger: 'blur'}
      ],
      files: []
    });

    const fetchProvince = async () => {
      try {
        loading.value = true;
        const {data} = await getProvince();
        provinceData.value = data.data;
        loading.value = false;
      } catch (error) {
        console.error(error);
        loading.value = false;
      }
    }
    const fetchDistrict = async (parentCode: string) => {
      try {
        if (parentCode) {
          loading.value = true;
          const {data} = await getProvinceByParentCode({parentCode: parentCode});
          districtData.value = data.data;
          loading.value = false;
        }
      } catch (error) {
        console.error(error);
        loading.value = false;
      }
    }
    const fetchProduct = async () => {
      try {
        const {data} = await fetchAllProduct();
        productData.value = data.data;
      } catch (error) {
        console.error(error);
        loading.value = false;
      }
    }
    const fetchPrecinct = async (parentCode: string) => {
      try {
        if (parentCode) {
          loading.value = true;
          const {data} = await getProvinceByParentCode({parentCode: parentCode});
          precinctData.value = data.data;
          loading.value = false;
        }
      } catch (error) {
        console.error(error);
        loading.value = false;
      }
    }

    watch(() => dataCustomer.province, () => {
      precinctData.value = [];
      if (dataCustomer.province) {
        fetchDistrict(dataCustomer.province);
      }
    })

    watch(() => dataCustomer.district, () => {
      if (dataCustomer.district)
        fetchPrecinct(dataCustomer.district);
    })
    const findById = async () => {
      try {
        let id = router.currentRoute.value.params?.data;
        const {data} = await getCustomerDetail(id as unknown as number);
        Object.assign(dataCustomer, {...data.data});
        if (dataCustomer.customValue) {
          dataCustomer.customValue.forEach(item => {
            dataCustomer[item?.codeConfig] = item.value;
          });
        }
        if (dataCustomer.customerDetails) {
          let ids = [];
          dataCustomer.customerDetails?.forEach(item => {
            ids = [item.id, ...ids];
          })
          const payload = {
            objectIds: ids,
            type: CUSTOMER_UPLOAD
          }

          const res = await getFiles(payload);
          let listFile = {};

          dataCustomer.customerDetails.forEach((item, idx) => {
            res.data.data.forEach(valueFile => {
              if (valueFile.objId === item.id) {
                listFile[idx] = valueFile.files;
                item.filesData = valueFile.files;
              }
            });
          });
          Object.assign(fileList, listFile);
        }

      } catch (error) {
        console.error('Lỗi khi lấy dữ liệu:', error);
      }
    }
    const handleSuccess = (message: string) => {
      ElNotification({
        title: 'Thành công',
        message,
        type: 'success',
        duration: 3000
      });
      back();
    };

    const processUpload = async () => {
      const uploadPromises = dataCustomer.customerDetails.map(async (detail: any) => {
        if (detail.filesData) {
          const formD = new FormData();
          for (const file of detail.filesData) {
            if (!file.id) {
              formD.append("fileMap", file.raw);
            }
          }
          if (formD.has("fileMap")) { // Chỉ upload nếu có file mới
            const res = await uploadFiles(formD);
            detail.fileInfo = res.data.data;
          }
        }
      });
      await Promise.all(uploadPromises); // Đợi tất cả các upload hoàn thành
    };
    const submitForm = async (formEl: FormInstance | undefined) => {
      if (!formEl) return;
      try {
        const validFormEl = await new Promise((resolve) => {
          if (formEl) {
            formEl.validate((valid, fields) => {
              resolve(valid);
            });
          } else {
            resolve(true);
          }
        });

        if (validFormEl) {
          loading.value = true;
          let handleErrorValue = handleError();
          if (isMode === 'create') {
            if (handleErrorValue) return;
            await buildAddCustomValue()
            await processUpload();
            let axiosResponse = await addCustomer(dataCustomer);
            handleSuccess(axiosResponse.data?.data.message)
          } else if (isMode === 'edit') {
            if (handleErrorValue) return;
            // @ts-ignore
            await buildUpdateCustomValue()
            await processUpload();
            if (filesRemove.value) {
              dataCustomer.filesRemove = [...filesRemove.value]
            }
            dataCustomer.id = router.currentRoute.value.params?.data;
            let axiosResponse = await updateCustomer(dataCustomer);
            handleSuccess(axiosResponse.data?.data.message)
          }
        } else {
          console.log("Một trong hai form không hợp lệ");
        }
      } catch (error) {
        console.error(error);
        loading.value = false;
      } finally {
        loading.value = false;
      }
    };

    function handleError() {
      let message = "";
      if (!dataCustomer.customerDetails || dataCustomer.customerDetails.length === 0) {
        message = 'Vui lòng thêm Thông tin giấy tờ khách hàng';
      }
      if (message && message.length > 0) {
        ElNotification({
          title: 'Lỗi',
          message: message,
          type: 'error',
          duration: 3 * 1000
        })
        return true
      }
    }

    const handleFileChange = async (index: number, updatedFileList: UploadFile[]) => {
      fileList[index] = updatedFileList;
      dataCustomer.customerDetails[index].filesData = updatedFileList;
    };

    const selectedValueCodes = computed(() => {
      return dataCustomer.customerDetails.map(item => item.type).filter(Boolean);
    })
    const availableOptions = (index: number) => {
      return propertyData.value.filter(item =>
          !selectedValueCodes.value.includes(item?.valueCode) ||
          dataCustomer.customerDetails[index].type === item?.valueCode
      );
    }
    const addProperty = () => {
      event.stopPropagation();
      dataCustomer.customerDetails.push({
        type: null,
        value: null,
        description: null,
        files: null,
        fileInfo: null,
        filesData: null,
      });
    };

    const handleOpenImg = () => {
      isViewFinger.value = true;
    }
    const handleCloseImg = () => {
      isViewFinger.value = false;
    }

    const removeProperty = (index: number) => {
      // Xóa phần tử khỏi mảng `customerDetails` theo index
      dataCustomer.customerDetails[index]?.filesData?.forEach(item => {
        handleFileRemove(item)
      })
      dataCustomer.customerDetails.splice(index, 1);
    }

    const back = () => {
      router.back();
    };
    const buildAddCustomValue = async () => {
      let allFields = config.flatMap(e => e.fields);
      allFields.forEach(item => {
        const key = item.key;
        if (dataCustomer.hasOwnProperty(key)) {
          // @ts-ignore
          dataCustomer.customValue.push({
            label: key,
            value: dataCustomer[key],
            codeConfig: key
          });
          // delete dataCustomer[key];
        }
      });
    };
    const buildUpdateCustomValue = async () => {
      let customValuesMap = new Map();
      if (dataCustomer.customValue) {
        customValuesMap = new Map(dataCustomer.customValue.map(cv => [cv.codeConfig, cv.id]));
      }

      let allFields = config.flatMap(e => e.fields);
      dataCustomer.customValue = allFields.map(item => ({
        label: item.key,
        value: dataCustomer[item.key],
        codeConfig: item.key,
        id: customValuesMap.get(item.key) || null
      }));

    }

    const disabledDate = (date) => {
      const today = new Date();
      return date.getTime() > today.getTime();
    };
    const resetForm = async () => {
      ruleFormRef.value?.clearValidate();
      districtData.value = [],
          precinctData.value = [],
          Object.assign(dataCustomer,
              {
                id: null,
                customerType: null,
                tel: null,
                name: null,
                email: null,
                province: null,
                district: null,
                precinct: null,
                address: null,
                customerDetails: [] as any,
                customValue: []
              }
          );
    };

    onMounted(async () => {
        await fetchProvince();
        await fetchProduct();
        if (isMode === 'edit' || isMode === 'view') {
          await findById();
        }
        let axiosResponse = await getDataConfigDynamic({functionCode: "CUSTOMER"});
        Object.assign(config, {...axiosResponse.data.data});
    });

    return {
      store,
      loading,
      dataCustomer,
      provinceData,
      districtData,
      precinctData,
      linkImg,
      valueData,
      propertyData,
      isMode,
      router,
      rules,
      ruleForm,
      resetForm,
      visible,
      formRef,
      ruleFormRef,
      submitForm,
      fileList,
      pagination,
      isViewFinger,
      checked,
      activeNames,
      config,
      // hàm
      fetchProvince,
      fetchDistrict,
      fetchPrecinct,
      availableOptions,
      addProperty,
      removeProperty,
      handleOpenImg,
      handleCloseImg,
      back,
      disabledDate,
      handleFileChange,
      handleFilePreview,
      handleFileRemove,
      productData
    }

  }
})
</script>

<style scoped>
.form-item {
  margin-bottom: 18px; /* Khoảng cách giữa các hàng theo chiều dọc */
}

.button-container {
  display: flex;
  justify-content: center; /* Căn giữa theo chiều ngang */
  margin-top: 20px; /* Thêm khoảng cách trên nếu cần */
  margin-bottom: 30px;
}

.box {
  background-color: var(--system-container-main-background);
  width: calc(100% - 30px);
  height: calc(100% - 30px);
  margin: 15px;
  display: flex;
  flex-direction: column;
}

.upload-demo {
  width: 100%;
  min-height: 50px;
  margin-top: 20px;
}

</style>