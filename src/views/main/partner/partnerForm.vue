<template>
  <div class="search-extend" v-loading="loading">
    <div class="box">
      <div class="layout-container-form flex space-between" style="padding-bottom: 15px">
        <el-form
            ref="formRef"
            style="width: 100%"
            :model="dataPartner"
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
                    <el-form-item prop="partnerType" label="Loại đối tác" :size="'default'" style="display: inherit">
                      <el-select v-model="dataPartner.partnerType" placeholder="Chọn giá trị" style="width: 100%"
                                 :disabled="isMode === 'view'"
                                 filterable clearable
                      >
                        <el-option
                            v-for="item in appParams.listPartnerType()"
                            :key="item?.id"
                            :label="item?.valueLabel"
                            :value="item?.valueCode"
                        />
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item prop="code" label="Mã đối tác" :size="'default'">
                      <el-input v-model="dataPartner.code" clearable
                                @blur="deepTrim(dataPartner)"
                                :disabled="isMode === 'view'"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item prop="codeSap" label="Mã đối tác SAP" :size="'default'">
                      <el-input v-model="dataPartner.codeSap" clearable
                                @blur="deepTrim(dataPartner)"
                                :disabled="isMode === 'view'"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item prop="name" label="Tên đối tác" :size="'default'">
                      <el-input v-model="dataPartner.name" clearable
                                @blur="deepTrim(dataPartner)"
                                :disabled="isMode === 'view'"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item prop="tel" label="Số điện thoại" :size="'default'">
                      <el-input v-model="dataPartner.tel" clearable
                                @blur="deepTrim(dataPartner)"
                                :disabled="isMode === 'view'"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item prop="email" label="Email" :size="'default'">
                      <el-input v-model="dataPartner.email" clearable
                                @blur="deepTrim(dataPartner)"
                                :disabled="isMode === 'view'"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item prop="province" label="Tỉnh/Thành phố" :size="'default'"
                                  style="display: inherit">
                      <el-select v-model="dataPartner.province" placeholder="Chọn giá trị" style="width: 100%"
                                 filterable clearable
                                 reserve-keyword
                                 :disabled="isMode === 'view'"
                                 @change="
                           dataPartner.district=null;
                           dataPartner.precinct = null;
                           fetchDistrict(dataPartner.province as string);"
                      >
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
                        <el-select v-model="dataPartner.district" placeholder="Chọn giá trị" style="width: 100%"
                                   filterable clearable
                                   reserve-keyword
                                   @change="fetchPrecinct(dataPartner.district as string);
                                   dataPartner.precinct = null;"
                                   :disabled="isMode === 'view' || !dataPartner.province">
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
                      <el-tooltip content="Phường/Xã"
                                  placement="top">
                        <el-select v-model="dataPartner.precinct"
                                   placeholder="Chọn giá trị" style="width: 100%"
                                   filterable clearable
                                   reserve-keyword
                                   :disabled="isMode === 'view' || !dataPartner.district">
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
                    <el-form-item label="Địa chỉ" :size="'default'">
                      <el-input v-model="dataPartner.address" clearable
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
                  <h3 class="style-header-box" style="width: 200px; text-align: left">Thông tin người đại diện</h3>
                  <div style="display: flex; justify-content: right; width: 100%; margin-right: 20px" v-if="isMode !== 'view'">
                    <el-button :icon="Plus" size="default"
                               color="var(--system-primary-color)"
                               type="primary" @click="addProperty()">Thêm
                      mới
                    </el-button>
                  </div>
                </template>
                <div class="layout-container-table">
                  <el-table :data="dataPartner.partnerDetails" border stripe>
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

                    <el-table-column header-align="center" align="left" prop="representName">
                      <template #header>
                        <span style="color: var(--el-color-danger);">*</span> <span>Người đại diện</span>
                      </template>
                      <template #default="scope">
                        <el-form-item :rules="rules.representName" :size="'default'"
                                      class="form-table"
                                      :prop="'partnerDetails.' + scope.$index + '.representName'"
                        >
                          <el-input v-model="dataPartner.partnerDetails[scope.$index].representName"
                                    :disabled="isMode === 'view'" clearable
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>

                    <el-table-column prop="type" header-align="center" align="left">
                      <template #header>
                        <span style="color: var(--el-color-danger);">*</span> <span>Loại giấy tờ</span>
                      </template>
                      <template #default="scope">
                        <el-form-item :size="'default'"
                                      class="form-table"
                                      :prop="'partnerDetails.' + scope.$index + '.type'"
                                      :rules="rules.type"
                        >
                          <el-select v-model="dataPartner.partnerDetails[scope.$index].type"
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

                    <el-table-column prop="identificationNumber" header-align="center"
                                     align="center">
                      <template #header>
                        <span style="color: var(--el-color-danger);">*</span> <span>Số giấy tờ</span>
                      </template>
                      <template #default="scope">
                        <el-form-item :rules="rules.identificationNumber" :size="'default'"
                                      class="form-table"
                                      :prop="'partnerDetails.' + scope.$index + '.identificationNumber'"
                        >
                          <el-input v-model="dataPartner.partnerDetails[scope.$index].identificationNumber"
                                    :disabled="isMode === 'view'"
                                    style="height: 32px;" clearable
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="description" :label="'Mô tả'" header-align="center"
                                     align="center">
                      <template #default="scope">
                        <el-form-item :rules="rules.description" :size="'default'"
                                      class="form-table"
                                      :prop="'partnerDetails.' + scope.$index + '.description'"
                        >
                          <el-input v-model="dataPartner.partnerDetails[scope.$index].description"
                                    clearable
                                    :disabled="isMode === 'view'"
                                    style="height: 32px;"
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <!---->
                    <el-table-column prop="files" :label="'Tệp đính kèm'" header-align="center"
                                     align="center">
                      <template #default="scope">
                        <el-form-item :rules="rules.files" :size="'default'"
                                      :prop="'partnerDetails.' + scope.$index + '.files'">
                          <div v-if="isMode === 'view'">
                            <div v-for="(file, index) in fileList[scope.$index] || []" :key="index">
                              <span @click="handleFilePreview(file)" style="cursor: pointer">{{ file.name }}</span>
                            </div>
                          </div>
                          <el-upload
                               v-else
                              class="upload-demo" drag
                              :limit="5"
                              :file-list="fileList[scope.$index] || []"
                              :auto-upload="false"
                              :on-change="(file, fileList) => handleFileChange(scope.$index, fileList)"
                              :on-preview="handleFilePreview"
                              :on-remove="handleFileRemove"
                          >
                            <template v-if="isMode !== 'view'">
                              <el-button plain size="default"  style="height: 32px; width: 100% !important;">Chọn File</el-button>
                            </template>
                          </el-upload>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <!--                    -->
                    <el-table-column v-if="isMode !== 'view'" :label="'Hành động'" align="center"
                                     fixed="right"
                                     width="200">
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
          <FormConfigDynamic :product="productData" :active-names="activeNames" :config="config" :data="dataPartner" :is-mode="isMode"/>
          <div v-if="isMode !== MODE_CREATE()" class="demo-collapse no-border-collapse" style="width: 100%;">
            <el-collapse :model-value="activeNames">
              <el-collapse-item>
                <template #title>
                  <h3 class="style-header-box">Lịch sử tác động</h3>
                </template>
                <div class="layout-container-table">
                  <History :id="dataPartner.partnerId" :type="AUDIT_TYPE_PARTNER()"></History>
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

import {CirclePlus, Delete, Document, Download, Edit, Plus, Refresh, Search} from "@element-plus/icons";
import {hasPermission} from "@/router/permission/front";
import {ElForm, ElNotification, FormInstance, UploadFile} from "element-plus";
import {useRouter} from "vue-router";
import {useStore} from 'vuex'
import {getProvince, getProvinceByParentCode} from "@/api/area";
import {deepTrim, downloadBlob} from "@/utils/mixins/mixin";
import {appParams} from "@/constants/app-param";
import {phoneValidator, validateEmailInvalid} from "@/utils/validate/helpers";
import {addPartner, getPartnerDetail, updatePartner} from "@/api/partner";
import {getDataConfigDynamic} from "@/api/systemDynamicConfig";
import FormConfigDynamic from "@/views/main/dynamicForm/FormConfigDynamic.vue";
import {downFile, getFiles, uploadFiles} from "@/api/uploadFile";
import {PARTNER_UPLOAD} from "@/constants/const";
import {fetchAllProduct} from "@/api/product";
import History from "@/views/main/history/History.vue";
import {MODE_CREATE} from "@/constants/formMode";
import {AUDIT_TYPE_PARTNER} from "@/constants/actionAudit";

export default defineComponent({
  components: {History, FormConfigDynamic},
  props: {
    isView: Boolean,
    mode: {
      type: String,
      default: 'create', // 'create', 'edit', 'view'
    },
  },
  methods: {
    AUDIT_TYPE_PARTNER() {
      return AUDIT_TYPE_PARTNER
    },
    MODE_CREATE() {
      return MODE_CREATE
    },
    deepTrim,
    hasPermission
  },
  computed: {
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
    const store = useStore()
    const dataPartner = reactive(
        {
          id: null,
          code: null,
          codeSap: null,
          name: null,
          tel: null,
          email: null,
          barcode: null,
          province: null,
          district: null,
          precinct: null,
          address: null,
          partnerDetails: [] as any,
          customValue: []
        });
    let config = reactive([])
    let loading = ref(false);
    const productData = ref([]);
    const provinceData = ref([]);
    const districtData = ref([]);
    const precinctData = ref([]);
    const propertyData = ref(appParams.listIdentification())
    const valueData = ref([])
    const router = useRouter();
    let isMode = router.currentRoute.value.params?.mode;
    let visible: any = ref(false);
    const pagination = reactive({
      current: 1,
      total: 0,
      pageSize: 10,
      size: 10,
    });
    const uploadType = PARTNER_UPLOAD;
    const isViewFinger = ref(false)
    const checked = ref(false)
    const activeNames = ref(['1', '2']);
    const formRef = ref<FormInstance>();
    const ruleFormRef = ref<InstanceType<typeof ElForm> | null>(null);
    const ruleForm = reactive({
      name: [
        {required: true, message: 'Vui lòng nhập Tên đối tác', trigger: 'blur'},
        {min: 1, max: 100, message: 'Tên đối tác tối đa 100 ký tự', trigger: 'blur'}
      ],
      code: [
        {required: true, message: 'Vui lòng nhập Mã đối tác', trigger: 'blur'},
        {min: 1, max: 50, message: 'Mã đối tác tối đa 50 ký tự', trigger: 'blur'}
      ],
      codeSap: [
        {min: 1, max: 50, message: 'Mã đối tác SAP tối đa 50 ký tự', trigger: 'blur'}
      ],
      address: [
        {min: 1, max: 500, message: 'Địa chỉ đối tác tối đa 500 ký tự', trigger: 'blur'}
      ],
      tel: [
        {validator: (rule: any, value: any, callback: any) => phoneValidator(rule, value, callback, 'Số điện thoại đối tác không đúng định dạng')},
      ],
      email: [
        {validator: (rule: any, value: any, callback: any) => validateEmailInvalid(rule, value, callback, 'Email đối tác không đúng định dạng')},
        {min: 1, max: 100, message: 'Email đối tác tối đa 100 ký tự', trigger: 'blur'}
      ],
      partnerType: [{required: true, message: 'Vui lòng chọn loaị đối tác', trigger: 'change'}],
      district: [{required: true, message: 'Vui lòng chọn Quận/huyện', trigger: 'change'}],
      province: [{required: true, message: 'Vui lòng chọn Tỉnh/thành phố', trigger: 'change'}],
    });

    const rules = reactive({
      representName: [
        {required: true, message: 'Vui lòng nhập Người đại diện', trigger: 'change'},
        {min: 1, max: 100, message: 'Người đại diện tối đa 100 ký tự', trigger: 'blur'}
      ],
      type: [
        {required: true, message: 'Vui lòng chọn Loại giấy tờ', trigger: 'change'},
      ],
      identificationNumber: [
        {required: true, message: 'Vui lòng nhập Số giấy tờ', trigger: 'change'},
        {min: 1, max: 50, message: 'Số giấy tờ tối đa 50 ký tự', trigger: 'blur'}
      ],
      description: [
        {min: 1, max: 500, message: 'Mô tả tối đa 500 ký tự', trigger: 'blur'}
      ],
      files: []
    });
    const fetchProduct = async () => {
      try {
        const {data} = await fetchAllProduct();
        productData.value = data.data;
      } catch (error) {
        console.error(error);
        loading.value = false;
      }
    }
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

    watch(() => dataPartner.province, () => {
      precinctData.value = [];
      if (dataPartner.province) {
        fetchDistrict(dataPartner.province);
      }
    })

    watch(() => dataPartner.district, () => {
      if (dataPartner.district)
        fetchPrecinct(dataPartner.district);
    })
    const findById = async () => {
      try {
        let id = router.currentRoute.value.params?.data;
        const {data} = await getPartnerDetail(id as unknown as number);
        Object.assign(dataPartner, {...data.data});
        if (dataPartner.customValue) {
          dataPartner.customValue.forEach(item => {
            dataPartner[item?.codeConfig] = item.value;
          });
        }

        if (dataPartner.partnerDetails) {
          let ids = [];
          dataPartner.partnerDetails?.forEach(item => {
            ids = [item.id, ...ids];
          })
          const payload = {
            objectIds: ids,
            type: uploadType
          }
          const res = await getFiles(payload);
          let listFile = {};

          dataPartner.partnerDetails.forEach((item, idx) => {
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

    const validateForm = (formEl: FormInstance | undefined): Promise<boolean> => {
      return new Promise((resolve) => {
        formEl?.validate((valid) => resolve(valid));
      });
    };

    const buildAddCustomValue = () => {
      let allFields = config.flatMap(e => e.fields);
      allFields.forEach(item => {
        const key = item.key;
        if (dataPartner.hasOwnProperty(key)) {
          // @ts-ignore
          dataPartner.customValue.push({
            label: key,
            value: dataPartner[key],
            codeConfig: key
          });
          // delete dataPartner[key];
        }
      });
    };

    const buildUpdateCustomValue = () => {
      const customValuesMap = new Map(dataPartner.customValue.map(cv => [cv.codeConfig, cv.id]));
      let allFields = config.flatMap(e => e.fields);
      // @ts-ignore
      dataPartner.customValue = allFields.map(item => ({
        label: item.key,
        value: dataPartner[item.key],
        codeConfig: item.key,
        id: customValuesMap.get(item.key) || null
      }));
    };

    const handleSuccess = (message: string) => {
      ElNotification({
        title: 'Thành công',
        message,
        type: 'success',
        duration: 3000
      });
      back();
    };
    const handleFileRemove = (file: { uuid: any; }) => {
      if (file.uuid) {
        let fileRemove = {fileName: file.name, uuid: file.uuid}
        filesRemove.value.push(fileRemove)
      }
    };
    const submitForm = async (formEl: FormInstance | undefined) => {
      if (!formEl) return;
      loading.value = true;
      try {
        const isValid = await validateForm(formEl);
        if (!isValid) {
          console.error("Form không hợp lệ");
          return;
        }
        let handleErrorValue = handleError();
        if (isMode === 'create') {
          if (handleErrorValue) return;
          buildAddCustomValue();
          await processUpload()
          const axiosResponse = await addPartner(dataPartner);
          handleSuccess(axiosResponse.data?.data.message);
        } else if (isMode === 'edit') {
          if (handleErrorValue) return;
          buildUpdateCustomValue();
          // @ts-ignore
          await processUpload()
          if (filesRemove.value) {
            dataPartner.filesRemove = [...filesRemove.value]
          }
          dataPartner.id = router.currentRoute.value.params?.data;
          const axiosResponse = await updatePartner(dataPartner);
          handleSuccess(axiosResponse.data?.data.message);
        }
      } catch (error) {
        console.error(error);
      } finally {
        loading.value = false;
      }
    };

    function handleError() {
      let message = "";
      if (!dataPartner.partnerDetails || dataPartner.partnerDetails.length === 0) {
        message = 'Vui lòng thêm Thông tin người đại diện';
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


    const processUpload = async () => {
      const uploadPromises = dataPartner.partnerDetails.map(async (detail: any) => {
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


    const selectedValueCodes = computed(() => {
      return dataPartner.partnerDetails.map(item => item.type).filter(Boolean);
    })
    const availableOptions = (index: number) => {
      return propertyData.value.filter(item =>
          !selectedValueCodes.value.includes(item?.valueCode) ||
          dataPartner.partnerDetails[index].type === item?.valueCode
      );
    }
    const addProperty = () => {
      event.stopPropagation();
      dataPartner.partnerDetails.push({
        representName: null,
        type: null,
        identificationNumber: null,
        description: null,
        files: null,
        fileInfo: null,
        filesData: null,
      });
    };

    const removeProperty = (index: number) => {
      dataPartner.partnerDetails[index]?.filesData?.forEach(item => {
        handleFileRemove(item)
      })
      dataPartner.partnerDetails.splice(index, 1);
    }

    const back = () => {
      router.back();
    };

    const disabledDate = (date) => {
      const today = new Date();
      return date.getTime() > today.getTime();
    };
    const resetForm = async () => {
      ruleFormRef.value?.clearValidate();
      districtData.value = [],
          precinctData.value = [],
          Object.assign(dataPartner,
              {
                id: null,
                code: null,
                codeSap: null,
                name: null,
                tel: null,
                email: null,
                barcode: null,
                province: null,
                district: null,
                precinct: null,
                address: null,
                partnerDetails: [] as any,
              }
          );
    };

    onMounted(async () => {
      await fetchProduct();
      await fetchProvince();
      if (isMode === 'edit' || isMode === 'view') {
        await findById();
      }
      let axiosResponse = await getDataConfigDynamic({functionCode: "PARTNER"});
      Object.assign(config, {...axiosResponse.data.data});
    });

    //
    const filesRemove = ref([]);
    const fileList = reactive<Record<number, UploadFile[]>>({});
    const handleFileChange = async (index: number, updatedFileList: UploadFile[]) => {
      fileList[index] = updatedFileList;
      dataPartner.partnerDetails[index].filesData = updatedFileList;
    };
    const handleFilePreview = async (file: { uuid: any; name: string; }) => {
      if (file && file.uuid) {
        let params = {filename: file.uuid}
        let axiosResponse = await downFile(params);
        if (axiosResponse?.status === 200) {
          downloadBlob(axiosResponse.data, file.name);
        }
      }
    };
    //

    return {
      store,
      loading,
      dataPartner,
      provinceData,
      districtData,
      precinctData,
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
      // hàm
      fetchProvince,
      fetchDistrict,
      fetchPrecinct,
      availableOptions,
      addProperty,
      removeProperty,
      back,
      disabledDate,
      config,
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