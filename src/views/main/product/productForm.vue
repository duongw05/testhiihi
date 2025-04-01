<template>
  <div class="search-extend" v-loading="loading">
    <div class="box">
      <div class="layout-container-form flex space-between">
        <el-form
            ref="formRef"
            style="width: 100%"
            :model="dataProduct"
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
                    <el-form-item prop="productGroupId" label="Lĩnh vực" :size="'default'" style="display: inherit">
                      <el-select v-model="dataProduct.productGroupId" placeholder="Chọn giá trị" style="width: 100%"
                                 :disabled="isMode === 'view'"
                                 filterable clearable
                      >
                        <el-option
                            v-for="item in appParams.fetchProductGroupId()"
                            :key="item?.id"
                            :label="item?.valueLabel"
                            :value="item?.valueCode"
                        />
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item prop="code" label="Mã sản phẩm" :size="'default'">
                      <el-input v-model.trim.lazy="dataProduct.code" clearable
                                :disabled="isMode === 'view'"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item prop="name" label="Tên sản phẩm" :size="'default'">
                      <el-input v-model="dataProduct.name" clearable
                                @blur="deepTrim(dataProduct)"
                                :disabled="isMode === 'view'"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item prop="productType" label="Loại sản phẩm" :size="'default'" style="display: inherit">
                      <el-select v-model="dataProduct.productType" placeholder="Chọn giá trị" style="width: 100%"
                                 :disabled="isMode === 'view'"
                                 filterable clearable
                      >
                        <el-option
                            v-for="item in appParams.fetchProductType()"
                            :key="item?.id"
                            :label="item?.valueLabel"
                            :value="item?.valueCode"
                        />
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item prop="bccsCode" label="Mã dịch vụ (BCCS)" :size="'default'" style="display: inherit">
                      <el-select v-model="dataProduct.bccsCode" placeholder="Chọn giá trị" style="width: 100%"
                                 :disabled="isMode === 'view'"
                                 filterable clearable
                      >
                        <el-option
                            v-for="item in appParams.fetchBccsTransCode()"
                            :key="item?.id"
                            :label="item?.valueLabel"
                            :value="item?.valueCode"
                        />
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item :size="'default'" label="Từ ngày hiệu lực" prop="province"
                                  style="display: inherit">
                      <el-date-picker
                          v-model="dataProduct.startDate"
                          type="date"
                          size="default"
                          format="DD/MM/YYYY"
                          value-format="DD/MM/YYYY"
                          placeholder="Từ ngày"
                          :disabled-date="disableStartDate"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item :size="'default'" label="Đến ngày hiệu lực" prop="province"
                                  style="display: inherit">
                      <el-date-picker
                          v-model="dataProduct.endDate"
                          type="date"
                          size="default"
                          format="DD/MM/YYYY"
                          value-format="DD/MM/YYYY"
                          placeholder="Đến ngày"
                          :disabled-date="disableEndDate"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item label="Mô tả" :size="'default'">
                      <el-input v-model="dataProduct.description" clearable
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
                  <h3 class="style-header-box" style="width: 200px; text-align: left">Danh sách mặt hàng</h3>
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
                  <el-table :data="dataProduct.productDetails" border stripe>
                    <el-table-column
                        type="index"
                        label="STT"
                        width="80"
                        align="center"
                        header-align="center"
                    >
                      <template #default="scope">
                        {{ (pagination.current - 1) * pagination.pageSize + scope.$index + 1 }}
                      </template>
                    </el-table-column>

                    <el-table-column header-align="center" align="left" prop="productItemCode" min-width="250">
                      <template #header>
                        <span style="color: var(--el-color-danger);">*</span> <span>Mã mặt hàng</span>
                      </template>
                      <template #default="scope">
                        <el-form-item :rules="rules.productItemCode" :size="'default'"
                                      class="form-table"
                                      :prop="'productDetails.' + scope.$index + '.productItemCode'"
                        >
                          <el-input v-model="dataProduct.productDetails[scope.$index].productItemCode"
                                    :disabled="isMode === 'view'" clearable
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>

                    <el-table-column prop="productItemName" header-align="center"
                                     min-width="250"
                                     align="center">
                      <template #header>
                        <span style="color: var(--el-color-danger);">*</span> <span>Tên mặt hàng</span>
                      </template>
                      <template #default="scope">
                        <el-form-item :rules="rules.productItemName" :size="'default'"
                                      class="form-table"
                                      :prop="'productDetails.' + scope.$index + '.productItemName'"
                        >
                          <el-input v-model="dataProduct.productDetails[scope.$index].productItemName"
                                    :disabled="isMode === 'view'"
                                    style="height: 32px;" clearable
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="productItemAlias" header-align="center"
                                     min-width="250"
                                     align="center">
                      <template #header>
                        <span style="color: var(--el-color-danger);">*</span> <span>Đơn vị tính</span>
                      </template>
                      <template #default="scope">
                        <el-form-item :rules="rules.productItemAlias" :size="'default'"
                                      class="form-table"
                                      :prop="'productDetails.' + scope.$index + '.productItemAlias'"
                        >
                          <el-input v-model="dataProduct.productDetails[scope.$index].productItemAlias"
                                    :disabled="isMode === 'view'"
                                    style="height: 32px;" clearable
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="productItemFinance" header-align="center"
                                     min-width="250"
                                     align="center">
                      <template #header>
                        <span style="color: var(--el-color-danger);">*</span> <span>Mã tài chính</span>
                      </template>
                      <template #default="scope">
                        <el-form-item :rules="rules.productItemFinance" :size="'default'"
                                      class="form-table"
                                      min-width="250"
                                      :prop="'productDetails.' + scope.$index + '.productItemFinance'"
                        >
                          <el-input v-model="dataProduct.productDetails[scope.$index].productItemFinance"
                                    :disabled="isMode === 'view'"
                                    style="height: 32px;" clearable
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="productItemFinancialAffairs" header-align="center"
                                     min-width="250"
                                     align="center">
                      <template #header>
                        <span style="color: var(--el-color-danger);">*</span> <span>Mã vụ việc tài chính</span>
                      </template>
                      <template #default="scope">
                        <el-form-item :rules="rules.productItemFinancialAffairs" :size="'default'"
                                      class="form-table"
                                      :prop="'productDetails.' + scope.$index + '.productItemFinancialAffairs'"
                        >
                          <el-input v-model="dataProduct.productDetails[scope.$index].productItemFinancialAffairs"
                                    :disabled="isMode === 'view'"
                                    style="height: 32px;" clearable
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="productItemFinanceType" header-align="center" align="left" min-width="250">
                      <template #header>
                        <span style="color: var(--el-color-danger);">*</span> <span>Loại giao dịch (ID) </span>
                      </template>
                      <template #default="scope">
                        <el-form-item :size="'default'"
                                      class="form-table"
                                      :prop="'productDetails.' + scope.$index + '.productItemFinanceType'"
                                      :rules="rules.productItemFinanceType"
                        >
                          <el-select v-model="dataProduct.productDetails[scope.$index].productItemFinanceType"
                                     placeholder="Chọn giá trị"
                                     filterable
                                     size="default"
                                     reserve-keyword
                                     :disabled="isMode === 'view'"
                                     clearable>
                            <el-option
                                v-for="item in appParams.fetchBccsTransID()"
                                :key="item?.id"
                                :label="item?.valueLabel"
                                :value="item?.valueCode">
                            </el-option>
                          </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="description" :label="'Mô tả'" header-align="center"
                                     min-width="250"
                                     align="center">
                      <template #default="scope">
                        <el-form-item :rules="rules.description" :size="'default'"
                                      class="form-table"
                                      :prop="'productDetails.' + scope.$index + '.description'"
                        >
                          <el-input v-model="dataProduct.productDetails[scope.$index].description"
                                    clearable
                                    :disabled="isMode === 'view'"
                                    style="height: 32px;"
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>
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
          <div class="demo-collapse no-border-collapse" style="width: 100%;">
            <el-collapse :model-value="activeNames">
              <el-collapse-item name="4">
                <template #title>
                  <h3 class="style-header-box" style="width: 200px; text-align: left">Danh sách tờ trình</h3>
                  <div style="display: flex; justify-content: right; width: 100%; margin-right: 20px"
                       v-if="isMode !== 'view'">
                    <el-button :icon="Plus" size="default"
                               color="var(--system-primary-color)"
                               type="primary" @click="addPolicyProperty()">Thêm
                      mới
                    </el-button>
                  </div>
                </template>
                <div class="layout-container-table">
                  <el-table :data="dataProduct.productOthers" border stripe>
                    <el-table-column
                        type="index"
                        label="STT"
                        width="80"
                        align="center"
                        header-align="center"
                    >
                      <template #default="scope">
                        {{ (pagination.current - 1) * pagination.pageSize + scope.$index + 1 }}
                      </template>
                    </el-table-column>

                    <el-table-column header-align="center" align="left" prop="code">
                      <template #header>
                        <span style="color: var(--el-color-danger);">*</span> <span>Mã tờ trình</span>
                      </template>
                      <template #default="scope">
                        <el-form-item :rules="rulePolicy.code" :size="'default'"
                                      class="form-table"
                                      :prop="'productOthers.' + scope.$index + '.code'"
                        >
                          <el-input v-model="dataProduct.productOthers[scope.$index].code"
                                    :disabled="isMode === 'view'" clearable
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>

                    <el-table-column prop="value" header-align="center"
                                     align="center">
                      <template #header>
                        <span style="color: var(--el-color-danger);">*</span> <span>Tên tờ trình</span>
                      </template>
                      <template #default="scope">
                        <el-form-item :rules="rulePolicy.value" :size="'default'"
                                      class="form-table"
                                      :prop="'productOthers.' + scope.$index + '.value'"
                        >
                          <el-input v-model="dataProduct.productOthers[scope.$index].value"
                                    :disabled="isMode === 'view'"
                                    style="height: 32px;" clearable
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="description" :label="'Mô tả'" header-align="center"
                                     align="center">
                      <template #default="scope">
                        <el-form-item :rules="rulePolicy.description" :size="'default'"
                                      class="form-table"
                                      :prop="'productOthers.' + scope.$index + '.description'"
                        >
                          <el-input v-model="dataProduct.productOthers[scope.$index].description"
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
                        <el-form-item :size="'default'" :prop="'productOthers.' + scope.$index + '.files'">
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
                              <el-button plain size="default" style="height: 32px; width: 100% !important;">Chọn File
                              </el-button>
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
                                     @click="removePolicyProperty(scope.$index)">
                          </el-button>
                        </el-tooltip>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </el-collapse-item>
            </el-collapse>
          </div>

          <div class="demo-collapse no-border-collapse" style="width: 100%;">
            <el-collapse :model-value="activeNames">
              <el-collapse-item name="4">
                <template #title>
                  <h3 class="style-header-box" style="width: 200px; text-align: left">Cài đặt thông tin nâng cao</h3>
                  <div style="display: flex; justify-content: right; width: 100%; margin-right: 20px"
                       v-if="isMode !== 'view'">
                    <el-button :icon="Plus" size="default"
                               color="var(--system-primary-color)"
                               type="primary" @click="handleAction('add', null)">Thêm
                      mới
                    </el-button>
                  </div>
                </template>
                <div class="layout-container-table">
                  <el-table :data="dataProduct.systemConfigs" border stripe>
                    <el-table-column
                        type="index"
                        label="STT"
                        width="80"
                        align="center"
                        header-align="center"
                    >
                      <template #default="scope">
                        {{ (pagination.current - 1) * pagination.pageSize + scope.$index + 1 }}
                      </template>
                    </el-table-column>

                    <el-table-column header-align="left" align="left" prop="code">
                      <template #header>
                        <span>Chức năng</span>
                      </template>
                      <template #default="scope">
                        {{
                          appParams.fetchSystemFeature() && appParams.fetchSystemFeature().find((e => e.valueCode == scope.row.functionCode))?.valueLabel || ''
                        }}
                      </template>
                    </el-table-column>

                    <el-table-column header-align="left" align="left" prop="code">
                      <template #header>
                        <span>Mã cấu hình</span>
                      </template>
                      <template #default="scope">
                        {{ scope.row.codeConfig }}
                      </template>
                    </el-table-column>
                    <el-table-column header-align="left" align="left" prop="code">
                      <template #header>
                        <span>Tên hiển thị</span>
                      </template>
                      <template #default="scope">
                        {{ scope.row.label }}
                      </template>
                    </el-table-column>

                    <el-table-column header-align="left" align="left" prop="code">
                      <template #header>
                        <span>Loại dữ liệu</span>
                      </template>
                      <template #default="scope">
                        {{
                          appParams.fetchSystemConfigType() && appParams.fetchSystemConfigType().find((e => e.valueCode == scope.row.type))?.valueLabel || ''
                        }}
                      </template>
                    </el-table-column>
                    <el-table-column header-align="left" align="left" prop="code">
                      <template #header>
                        <span>Điều kiện</span>
                      </template>
                      <template #default="scope">
                        {{
                          appParams.fetchSystemConfigRequire() && appParams.fetchSystemConfigRequire().find((e => e.valueCode == scope.row.requireOption))?.valueLabel || ''
                        }}
                      </template>
                    </el-table-column>
                    <el-table-column v-if="isMode !== 'view'" :label="'Hành động'" align="center"
                                     fixed="right"
                                     width="200">
                      <template #default="scope">
                        <el-tooltip content="xem chi tiết">
                          <el-button :icon="Document"
                                     style="width: 15px; margin: 0"
                                     text
                                     type="danger"
                                     @click="handleAction('view', scope.row)"
                          ></el-button>
                        </el-tooltip>
                        <el-tooltip content="Cập nhật">
                          <el-button :icon="Edit"
                                     style="width: 15px; margin: 0"
                                     text
                                     type="danger"
                                     @click="handleAction('edit', scope.row)"
                          ></el-button>
                        </el-tooltip>
                        <el-tooltip :content="'Xóa'" placement="top">
                          <el-button style="width: 40px; margin: 0" type="danger" text :icon="Delete"
                                     @click="removeDataConfig(scope.$index)">
                          </el-button>
                        </el-tooltip>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </el-collapse-item>
            </el-collapse>
          </div>
          <div v-if="isMode !== MODE_CREATE()" class="demo-collapse no-border-collapse" style="width: 100%;">
            <el-collapse :model-value="activeNames">
            <el-collapse-item>
              <template #title>
                <h3 class="style-header-box">Lịch sử tác động</h3>
              </template>
              <div class="layout-container-table">
                  <History :id="dataProduct.productId" :type="AUDIT_TYPE_PRODUCT()"></History>
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
  <PopupConfig :visible="isPopupVisible" :title="'Cài đặt thông tin nâng cao'" :mode="popupMode"
               :initial-data="selectedData"
               @closeFormData="handleClose"
  ></PopupConfig>
</template>


<script lang="ts">
import {defineComponent, onMounted, reactive, ref, UnwrapNestedRefs} from "vue";

import {CirclePlus, Delete, Document, Download, Edit, Expand, Plus, Refresh, Search} from "@element-plus/icons";
import {hasPermission} from "@/router/permission/front";
import {ElForm, ElNotification, FormInstance, UploadFile} from "element-plus";
import {useRouter} from "vue-router";
import {useStore} from 'vuex'
import {deepTrim, downloadBlob, genUuid} from "@/utils/mixins/mixin";
import {appParams} from "@/constants/app-param";
import {downFile, getFiles, uploadFiles} from "@/api/uploadFile";
import {PRODUCT_UPLOAD} from "@/constants/const";
import {addProduct, getProductDetail, updateProduct} from "@/api/product";
import dayjs from "dayjs"; // Import thư viện xử lý ngày
import PopupConfig from "@/views/main/product/popupConfigValue.vue";
import History from "@/views/main/history/History.vue";
import {MODE_CREATE} from "@/constants/formMode";
import {AUDIT_TYPE_PRODUCT} from "@/constants/actionAudit";

export default defineComponent({
  components: {History, Expand, PopupConfig},
  props: {
    isView: Boolean,
    mode: {
      type: String,
      default: 'create', // 'create', 'edit', 'view'
    },
  },
  methods: {
    AUDIT_TYPE_PRODUCT() {
      return AUDIT_TYPE_PRODUCT
    },
    MODE_CREATE() {
      return MODE_CREATE
    },
    deepTrim,
    hasPermission,
    disableStartDate(date: any) {
      let today = dayjs().startOf("day");
      let endDate = this.dataProduct.endDate ? dayjs(this.dataProduct.endDate, "DD/MM/YYYY") : null;
      return dayjs(date).isBefore(today) || (endDate && dayjs(date).isAfter(endDate));
    },
    disableEndDate(date: any) {
      let startDate = this.dataProduct.startDate ? dayjs(this.dataProduct.startDate, "DD/MM/YYYY") : null;
      return startDate && dayjs(date).isBefore(startDate);
    }
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
    const dataProduct = reactive(
        {
          id: null,
          productGroupId: null,
          code: null,
          name: null,
          bccsCode: null,
          productType: null,
          description: null,
          startDate: null,
          endDate: null,
          date: [],
          productDetails: [] as any,
          productOthers: [] as any,
          systemConfigs: [] as any,
        });
    let config = reactive([])
    let loading = ref(false);
    const provinceData = ref([]);
    const districtData = ref([]);
    const precinctData = ref([]);
    const propertyData = ref(appParams.listIdentification())
    const valueData = ref([])
    const router = useRouter();
    let isMode = router.currentRoute.value.params?.mode;
    let visible: any = ref(false);
    const isPopupVisible = ref(false);
    const popupMode = ref("add");
    const selectedData = ref({});

    const pagination = reactive({
      current: 1,
      total: 0,
      pageSize: 10,
      size: 10,
    });
    const uploadType = PRODUCT_UPLOAD;
    const isViewFinger = ref(false)
    const checked = ref(false)
    const activeNames = ref(['1', '2', '4']);
    const formRef = ref<FormInstance>();
    const ruleFormRef = ref<InstanceType<typeof ElForm> | null>(null);
    const ruleForm = reactive({
      name: [
        {required: true, message: 'Vui lòng nhập Tên sản phẩm', trigger: 'blur'},
        {min: 1, max: 100, message: 'Tên đối tác tối đa 100 ký tự', trigger: 'blur'}
      ],
      code: [
        {required: true, message: 'Vui lòng nhập Mã sản phẩm', trigger: 'blur'},
        {min: 1, max: 50, message: 'Mã đối tác tối đa 50 ký tự', trigger: 'blur'}
      ],
      productGroupId: [{required: true, message: 'Vui lòng chọn Lĩnh vực', trigger: 'change'}],
      bccsCode: [{required: true, message: 'Vui lòng chọn Mã dịch vụ (BCCS)', trigger: 'change'}],
      productType: [{required: true, message: 'Vui lòng chọn Loại sản phẩm', trigger: 'change'}],
      description: [
        {min: 1, max: 500, message: 'Mô tả tối đa 500 ký tự', trigger: 'blur'}
      ],
    });

    const rules = reactive({
      productItemCode: [
        {required: true, message: 'Vui lòng nhập Mã mặt hàng', trigger: 'blur'},
        {min: 1, max: 50, message: 'Mã mặt hàng tối đa 50 ký tự', trigger: 'blur'}
      ],
      productItemName: [
        {required: true, message: 'Vui lòng nhập Tên mặt hàng', trigger: 'blur'},
        {min: 1, max: 200, message: 'Tên mặt hàng tối đa 200 ký tự', trigger: 'blur'}
      ],
      productItemAlias: [
        {required: true, message: 'Vui lòng nhập Đơn vị tính', trigger: 'blur'},
        {min: 1, max: 100, message: 'Đơn vị tính tối đa 100 ký tự', trigger: 'blur'}
      ],
      productItemFinance: [
        {required: true, message: 'Vui lòng nhập Mã tài chính', trigger: 'blur'},
        {min: 1, max: 50, message: 'Mã tài chính tối đa 50 ký tự', trigger: 'blur'}
      ],
      productItemFinancialAffairs: [
        {required: true, message: 'Vui lòng nhập Mã vụ việc tài chính', trigger: 'blur'},
        {min: 1, max: 50, message: 'Mã vụ việc tài chính tối đa 50 ký tự', trigger: 'blur'}
      ],
      productItemFinanceType: [
        {required: true, message: 'Vui lòng chọn Loại giao dịch (ID)', trigger: 'change'},
      ],
      description: [
        {min: 1, max: 500, message: 'Mô tả tối đa 500 ký tự', trigger: 'blur'}
      ],
    });

    const rulePolicy = reactive({
      code: [
        {required: true, message: 'Vui lòng nhập Mã tờ trình', trigger: 'change'},
        {min: 1, max: 50, message: 'Mã tờ trình tối đa 50 ký tự', trigger: 'blur'}
      ],
      value: [
        {required: true, message: 'Vui lòng nhập Tên tờ trình', trigger: 'change'},
        {min: 1, max: 200, message: 'Tên tờ trình tối đa 200 ký tự', trigger: 'blur'}
      ],
      description: [
        {min: 1, max: 500, message: 'Mô tả tối đa 500 ký tự', trigger: 'blur'}
      ],
    });
    const findById = async () => {
      try {
        let id = router.currentRoute.value.params?.data;
        const {data} = await getProductDetail(id as unknown as number);
        Object.assign(dataProduct, {...data.data});
        dataProduct.productGroupId = String(dataProduct.productGroupId)
        if (dataProduct.productOthers) {
          let ids: any[] = [];
          dataProduct.productOthers?.forEach(item => {
            ids = [item.id, ...ids];
          })
          if (ids && ids.length > 0) {
            const payload = {
              objectIds: ids,
              type: uploadType
            }
            const res = await getFiles(payload);
            let listFile = {};

            dataProduct.productOthers.forEach((item, idx) => {
              res.data.data.forEach(valueFile => {
                if (valueFile.objId === item.id) {
                  listFile[idx] = valueFile.files;
                  item.filesData = valueFile.files;
                }
              });
            });
            Object.assign(fileList, listFile);
          }
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

    function handleError() {
      let message = "";
      if (!dataProduct.productDetails || dataProduct.productDetails.length === 0) {
        message = 'Vui lòng thêm Danh sách mặt hàng';
      }
      if (!dataProduct.productOthers || dataProduct.productOthers.length === 0) {
        message = message && message.length > 0 ? ', Danh sách tờ trình' : 'Vui lòng thêm Danh sách tờ trình';
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
          await processUpload()
          const axiosResponse = await addProduct(dataProduct);
          handleSuccess(axiosResponse.data?.data.message);
        } else if (isMode === 'edit') {
          // @ts-ignore
          if (handleErrorValue) return;
          await processUpload()
          if (filesRemove.value) {
            dataProduct.filesRemove = [...filesRemove.value]
          }
          dataProduct.id = router.currentRoute.value.params?.data;
          const axiosResponse = await updateProduct(dataProduct);
          handleSuccess(axiosResponse.data?.data.message);
        }
      } catch (error) {
        console.error(error);
      } finally {
        loading.value = false;
      }
    };

    const processUpload = async () => {
      const uploadPromises = dataProduct.productOthers.map(async (detail: any) => {
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

    const addProperty = () => {
      event.stopPropagation();
      dataProduct.productDetails.push({
        productItemCode: null,
        productItemName: null,
        productItemAlias: null,
        productItemFinance: null,
        productItemFinancialAffairs: null,
        productItemFinanceType: null,
        description: null,
      });
    };

    const removeProperty = (index: number) => {
      dataProduct.productDetails.splice(index, 1);
    }

    const addPolicyProperty = () => {
      event.stopPropagation();
      dataProduct.productOthers.push({
        code: null,
        name: null,
        description: null,
      });
    };

    const removePolicyProperty = (index: number) => {
      dataProduct.productDetails[index]?.filesData?.forEach(item => {
        handleFileRemove(item)
      })
      dataProduct.productOthers.splice(index, 1);
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
          Object.assign(dataProduct,
              {
                id: null,
                productGroupId: null,
                code: null,
                name: null,
                bccsCode: null,
                productType: null,
                description: null,
                startDate: null,
                endDate: null,
                date: [],
                productDetails: [] as any,
                productOthers: [] as any,
              }
          );
    };

    onMounted(async () => {
      if (isMode === 'edit' || isMode === 'view') {
        await findById();
      }
    });

    //
    const filesRemove = ref([]);
    const fileList = reactive<Record<number, UploadFile[]>>({});
    const handleFileChange = async (index: number, updatedFileList: UnwrapNestedRefs<Record<number, UploadFile[]>> & {}) => {
      fileList[index] = updatedFileList;
      dataProduct.productOthers[index].filesData = updatedFileList;
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
    const handleAction = (type: string, row: any) => {
      event.stopPropagation();
      if (type === "edit") {
        popupMode.value = "edit";
        isPopupVisible.value = true;
        selectedData.value = {...row};
      } else if (type === "add") {
        popupMode.value = "add";
        isPopupVisible.value = true;
      } else if (type === "view") {
        popupMode.value = "view";
        isPopupVisible.value = true;
        selectedData.value = {...row};
      }
    };
    const handleClose = (data: any) => {
      if (data) {
        if (popupMode.value == 'add') {
          data.uuid = genUuid()
          dataProduct.systemConfigs.push(data)
        } else if (popupMode.value == 'edit') {
          const index = dataProduct.systemConfigs.findIndex((item: any) => item.uuid === data.uuid);
          const indexUpdate = dataProduct.systemConfigs.findIndex((item: any) => item.id === data.id);
          if (index !== -1) {
            dataProduct.systemConfigs[index] = {
              ...dataProduct.systemConfigs[index],
              ...data,
            };
          }
          if (indexUpdate !== -1) {
            dataProduct.systemConfigs[indexUpdate] = {
              ...dataProduct.systemConfigs[indexUpdate],
              ...data,
            };
          }
        }
      }
      isPopupVisible.value = false;
    };

    const removeDataConfig = (index: number) => {
      dataProduct.systemConfigs.splice(index, 1);
    }
    return {
      store,
      loading,
      dataProduct,
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
      // availableOptions,
      addProperty,
      removeProperty,
      back,
      disabledDate,
      config,
      rulePolicy,
      handleFileChange,
      handleFilePreview,
      handleFileRemove,
      addPolicyProperty,
      removePolicyProperty,
      handleAction,
      handleClose,
      isPopupVisible,
      popupMode,
      filesRemove,
      removeDataConfig,
      selectedData
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