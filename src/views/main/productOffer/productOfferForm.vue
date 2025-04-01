<template>
  <div class="search-extend" v-loading="loading">
    <div class="box">
      <div class="layout-container-form flex space-between">
        <el-form
            ref="formRef"
            style="width: 100%"
            :model="dataProductOffer"
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
                    <el-form-item prop="productId" label="Sản phẩm" :size="'default'" style="display: inherit">
                      <el-select v-model="dataProductOffer.productId" placeholder="Chọn giá trị" style="width: 100%"
                                 :disabled="isMode === 'view'"
                                 filterable clearable
                      >
                        <el-option
                            v-for="item in productData"
                            :key="item?.productId"
                            :label="item?.name"
                            :value="item?.productId"
                        />
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item prop="productOfferType" label="Loại gói cước" :size="'default'"
                                  style="display: inherit">
                      <el-select v-model="dataProductOffer.productOfferType" placeholder="Chọn giá trị"
                                 style="width: 100%"
                                 :disabled="isMode === 'view'"
                                 filterable clearable
                      >
                        <el-option
                            v-for="item in appParams.fetchProductOfferType()"
                            :key="item?.id"
                            :label="item?.valueLabel"
                            :value="item?.valueCode"
                        />
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item prop="code" label="Mã gói cước" :size="'default'">
                      <el-input v-model.trim.lazy="dataProductOffer.code" clearable
                                :disabled="isMode === 'view'"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item prop="name" label="Tên gói cước" :size="'default'">
                      <el-input v-model="dataProductOffer.name" clearable
                                @blur="deepTrim(dataProductOffer)"
                                :disabled="isMode === 'view'"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item prop="provisionType" label="Hình thức cung cấp" :size="'default'">
                      <el-select v-model="dataProductOffer.provisionType" placeholder="Chọn giá trị" style="width: 100%"
                                 :disabled="isMode === 'view'"
                                 filterable clearable
                      >
                        <el-option
                            v-for="item in appParams.fetchProvisionType()"
                            :key="item?.id"
                            :label="item?.valueLabel"
                            :value="item?.valueCode"
                        />
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item prop="allocateType" label="Loại phân bổ" :size="'default'" style="display: inherit">
                      <el-select v-model="dataProductOffer.allocateType" placeholder="Chọn giá trị" style="width: 100%"
                                 :disabled="isMode === 'view'"
                                 filterable clearable
                      >
                        <el-option
                            v-for="item in appParams.fetchAllocateType()"
                            :key="item?.id"
                            :label="item?.valueLabel"
                            :value="item?.valueCode"
                        />
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item prop="volBase" label="Số tháng tính phí" :size="'default'">
                      <el-input
                          v-model="dataProductOffer.volBase"
                          v-integer-format="{max : 1000}"
                          type="text"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item prop="volPromotion" label="Số tháng miễn phí" :size="'default'">
                      <el-input
                          v-model="dataProductOffer.volPromotion"
                          v-integer-format="{max : 1000}"
                          type="text"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="6" :lg="6" class="form-item">
                    <el-form-item :size="'default'" label="Từ ngày hiệu lực" prop="province"
                                  style="display: inherit">
                      <el-date-picker
                          v-model="dataProductOffer.startDate"
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
                          v-model="dataProductOffer.endDate"
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
                      <el-input v-model="dataProductOffer.description" clearable
                                :disabled="isMode === 'view'"></el-input>
                    </el-form-item>
                  </el-col>

                </el-row>
              </el-collapse-item>
            </el-collapse>
          </div>
          <!--          <div class="demo-collapse no-border-collapse" style="width: 100%;">-->
          <!--            <el-collapse :model-value="activeNames">-->
          <!--              <el-collapse-item name="2">-->
          <!--                <template #title>-->
          <!--                  <h3 class="style-header-box" style="width: 200px; text-align: left">Danh sách mặt hàng</h3>-->
          <!--                  <div style="display: flex; justify-content: right; width: 100%; margin-right: 20px"-->
          <!--                       v-if="isMode !== 'view'">-->
          <!--                    <el-button :icon="Plus" size="default"-->
          <!--                               color="var(&#45;&#45;system-primary-color)"-->
          <!--                               type="primary" @click="addProperty()">Thêm-->
          <!--                      mới-->
          <!--                    </el-button>-->
          <!--                  </div>-->
          <!--                </template>-->
          <!--                <div class="layout-container-table">-->
          <!--                  <el-table :data="dataProductOffer.productDetails" border stripe>-->
          <!--                    <el-table-column-->
          <!--                        type="index"-->
          <!--                        label="STT"-->
          <!--                        width="80"-->
          <!--                        align="center"-->
          <!--                        header-align="center"-->
          <!--                    >-->
          <!--                      <template #default="scope">-->
          <!--                        {{ (pagination.current - 1) * pagination.pageSize + scope.$index + 1 }}-->
          <!--                      </template>-->
          <!--                    </el-table-column>-->
          <!--                    <el-table-column prop="productItemFinanceType" header-align="center" align="left" min-width="250">-->
          <!--                      <template #header>-->
          <!--                        <span style="color: var(&#45;&#45;el-color-danger);">*</span> <span>Mặt hàng </span>-->
          <!--                      </template>-->
          <!--                      <template #default="scope">-->
          <!--                        <el-form-item :size="'default'"-->
          <!--                                      class="form-table"-->
          <!--                                      :prop="'productDetails.' + scope.$index + '.productItemFinanceType'"-->
          <!--                                      :rules="rules.productItemFinanceType"-->
          <!--                        >-->
          <!--                          <el-select v-model="dataProductOffer.productDetails[scope.$index].productItemFinanceType"-->
          <!--                                     placeholder="Chọn giá trị"-->
          <!--                                     filterable-->
          <!--                                     size="default"-->
          <!--                                     reserve-keyword-->
          <!--                                     :disabled="isMode === 'view'"-->
          <!--                                     clearable>-->
          <!--                            <el-option-->
          <!--                                v-for="item in appParams.fetchBccsTransID()"-->
          <!--                                :key="item?.id"-->
          <!--                                :label="item?.valueLabel"-->
          <!--                                :value="item?.valueCode">-->
          <!--                            </el-option>-->
          <!--                          </el-select>-->
          <!--                        </el-form-item>-->
          <!--                      </template>-->
          <!--                    </el-table-column>-->
          <!--                    <el-table-column prop="productItemAlias" header-align="center"-->
          <!--                                     min-width="250"-->
          <!--                                     align="center">-->
          <!--                      <template #header>-->
          <!--                        <span style="color: var(&#45;&#45;el-color-danger);">*</span> <span>Đơn vị tính</span>-->
          <!--                      </template>-->
          <!--                      <template #default="scope">-->
          <!--                        <el-form-item :rules="rules.productItemAlias" :size="'default'"-->
          <!--                                      class="form-table"-->
          <!--                                      :prop="'productDetails.' + scope.$index + '.productItemAlias'"-->
          <!--                        >-->
          <!--                          <el-input v-model="dataProductOffer.productDetails[scope.$index].productItemAlias"-->
          <!--                                    :disabled="isMode === 'view'"-->
          <!--                                    style="height: 32px;" clearable-->
          <!--                          />-->
          <!--                        </el-form-item>-->
          <!--                      </template>-->
          <!--                    </el-table-column>-->
          <!--                    <el-table-column prop="productItemFinanceType" header-align="center" align="left" min-width="250">-->
          <!--                      <template #header>-->
          <!--                        <span style="color: var(&#45;&#45;el-color-danger);">*</span> <span>Cách tính giá </span>-->
          <!--                      </template>-->
          <!--                      <template #default="scope">-->
          <!--                        <el-form-item :size="'default'"-->
          <!--                                      class="form-table"-->
          <!--                                      :prop="'productDetails.' + scope.$index + '.productItemFinanceType'"-->
          <!--                                      :rules="rules.productItemFinanceType"-->
          <!--                        >-->
          <!--                          <el-select v-model="dataProductOffer.productDetails[scope.$index].productItemFinanceType"-->
          <!--                                     placeholder="Chọn giá trị"-->
          <!--                                     filterable-->
          <!--                                     size="default"-->
          <!--                                     reserve-keyword-->
          <!--                                     :disabled="isMode === 'view'"-->
          <!--                                     clearable>-->
          <!--                            <el-option-->
          <!--                                v-for="item in appParams.fetchBccsTransID()"-->
          <!--                                :key="item?.id"-->
          <!--                                :label="item?.valueLabel"-->
          <!--                                :value="item?.valueCode">-->
          <!--                            </el-option>-->
          <!--                          </el-select>-->
          <!--                        </el-form-item>-->
          <!--                      </template>-->
          <!--                    </el-table-column>-->
          <!--                    <el-table-column prop="productItemFinanceType" header-align="center" align="left" min-width="250">-->
          <!--                      <template #header>-->
          <!--                        <span style="color: var(&#45;&#45;el-color-danger);">*</span> <span>Loại giá</span>-->
          <!--                      </template>-->
          <!--                      <template #default="scope">-->
          <!--                        <el-form-item :size="'default'"-->
          <!--                                      class="form-table"-->
          <!--                                      :prop="'productDetails.' + scope.$index + '.productItemFinanceType'"-->
          <!--                                      :rules="rules.productItemFinanceType"-->
          <!--                        >-->
          <!--                          <el-select v-model="dataProductOffer.productDetails[scope.$index].productItemFinanceType"-->
          <!--                                     placeholder="Chọn giá trị"-->
          <!--                                     filterable-->
          <!--                                     size="default"-->
          <!--                                     reserve-keyword-->
          <!--                                     :disabled="isMode === 'view'"-->
          <!--                                     clearable>-->
          <!--                            <el-option-->
          <!--                                v-for="item in appParams.fetchBccsTransID()"-->
          <!--                                :key="item?.id"-->
          <!--                                :label="item?.valueLabel"-->
          <!--                                :value="item?.valueCode">-->
          <!--                            </el-option>-->
          <!--                          </el-select>-->
          <!--                        </el-form-item>-->
          <!--                      </template>-->
          <!--                    </el-table-column>-->
          <!--                    <el-table-column prop="productItemFinance" header-align="center"-->
          <!--                                     min-width="250"-->
          <!--                                     align="center">-->
          <!--                      <template #header>-->
          <!--                        <span style="color: var(&#45;&#45;el-color-danger);">*</span> <span>Block</span>-->
          <!--                      </template>-->
          <!--                      <template #default="scope">-->
          <!--                        <el-form-item :rules="rules.productItemFinance" :size="'default'"-->
          <!--                                      class="form-table"-->
          <!--                                      min-width="250"-->
          <!--                                      :prop="'productDetails.' + scope.$index + '.productItemFinance'"-->
          <!--                        >-->
          <!--                          <el-input v-model="dataProductOffer.productDetails[scope.$index].productItemFinance"-->
          <!--                                    :disabled="isMode === 'view'"-->
          <!--                                    style="height: 32px;" clearable-->
          <!--                          />-->
          <!--                        </el-form-item>-->
          <!--                      </template>-->
          <!--                    </el-table-column>-->
          <!--                    <el-table-column prop="productItemFinance" header-align="center"-->
          <!--                                     min-width="250"-->
          <!--                                     align="center">-->
          <!--                      <template #header>-->
          <!--                        <span style="color: var(&#45;&#45;el-color-danger);">*</span> <span>Giá</span>-->
          <!--                      </template>-->
          <!--                      <template #default="scope">-->
          <!--                        <el-form-item :rules="rules.productItemFinance" :size="'default'"-->
          <!--                                      class="form-table"-->
          <!--                                      min-width="250"-->
          <!--                                      :prop="'productDetails.' + scope.$index + '.productItemFinance'"-->
          <!--                        >-->
          <!--                          <el-input v-model="dataProductOffer.productDetails[scope.$index].productItemFinance"-->
          <!--                                    :disabled="isMode === 'view'"-->
          <!--                                    style="height: 32px;" clearable-->
          <!--                          />-->
          <!--                        </el-form-item>-->
          <!--                      </template>-->
          <!--                    </el-table-column>-->
          <!--                    <el-table-column prop="productItemFinanceType" header-align="center" align="left" min-width="250">-->
          <!--                      <template #header>-->
          <!--                        <span style="color: var(&#45;&#45;el-color-danger);">*</span> <span>VAT</span>-->
          <!--                      </template>-->
          <!--                      <template #default="scope">-->
          <!--                        <el-form-item :size="'default'"-->
          <!--                                      class="form-table"-->
          <!--                                      :prop="'productDetails.' + scope.$index + '.productItemFinanceType'"-->
          <!--                                      :rules="rules.productItemFinanceType"-->
          <!--                        >-->
          <!--                          <el-select v-model="dataProductOffer.productDetails[scope.$index].productItemFinanceType"-->
          <!--                                     placeholder="Chọn giá trị"-->
          <!--                                     filterable-->
          <!--                                     size="default"-->
          <!--                                     reserve-keyword-->
          <!--                                     :disabled="isMode === 'view'"-->
          <!--                                     clearable>-->
          <!--                            <el-option-->
          <!--                                v-for="item in appParams.fetchBccsTransID()"-->
          <!--                                :key="item?.id"-->
          <!--                                :label="item?.valueLabel"-->
          <!--                                :value="item?.valueCode">-->
          <!--                            </el-option>-->
          <!--                          </el-select>-->
          <!--                        </el-form-item>-->
          <!--                      </template>-->
          <!--                    </el-table-column>-->
          <!--                    <el-table-column prop="description" :label="'Mô tả'" header-align="center"-->
          <!--                                     min-width="250"-->
          <!--                                     align="center">-->
          <!--                      <template #default="scope">-->
          <!--                        <el-form-item :rules="rules.description" :size="'default'"-->
          <!--                                      class="form-table"-->
          <!--                                      :prop="'productDetails.' + scope.$index + '.description'"-->
          <!--                        >-->
          <!--                          <el-input v-model="dataProductOffer.productDetails[scope.$index].description"-->
          <!--                                    clearable-->
          <!--                                    :disabled="isMode === 'view'"-->
          <!--                                    style="height: 32px;"-->
          <!--                          />-->
          <!--                        </el-form-item>-->
          <!--                      </template>-->
          <!--                    </el-table-column>-->
          <!--                    <el-table-column v-if="isMode !== 'view'" :label="'Hành động'" align="center"-->
          <!--                                     fixed="right"-->
          <!--                                     width="200">-->
          <!--                      <template #default="scope">-->
          <!--                        <el-tooltip :content="'Xóa'" placement="top">-->
          <!--                          <el-button style="width: 40px; margin: 0" type="danger" text :icon="Delete"-->
          <!--                                     @click="removeProperty(scope.$index)">-->
          <!--                          </el-button>-->
          <!--                        </el-tooltip>-->
          <!--                      </template>-->
          <!--                    </el-table-column>-->
          <!--                  </el-table>-->
          <!--                </div>-->
          <!--              </el-collapse-item>-->
          <!--            </el-collapse>-->
          <!--          </div>-->
          <div class="demo-collapse no-border-collapse" style="width: 100%;">
            <el-collapse :model-value="activeNames">
              <el-collapse-item name="4">
                <template #title>
                  <h3 class="style-header-box" style="width: 200px; text-align: left">Địa bàn áp dụng</h3>
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
                  <el-table :data="dataProductOffer.productOthers" border stripe>
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
                        <span style="color: var(--el-color-danger);">*</span> <span>Tỉnh</span>
                      </template>
                      <template #default="scope">
                        <el-form-item :rules="rulePolicy.province" :size="'default'"
                                      class="form-table"
                                      :prop="'productOthers.' + scope.$index + '.code'"
                        >
                          <el-select v-model="dataProductOffer.productOthers[scope.$index].province"
                                     placeholder="Chọn giá trị" style="width: 100%"
                                     filterable clearable
                                     reserve-keyword
                                     :disabled="isMode === 'view'"
                                     @change="fetchDistrict(dataProductOffer.productOthers[scope.$index].province, scope.$index)"
                          >
                            <el-option
                                v-for="item in provinceData"
                                :key="item?.areaCode"
                                :label="item.name"
                                :value="item?.areaCode"
                            />
                          </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column>

                    <el-table-column prop="value" header-align="center"
                                     align="center">
                      <template #header>
                       <span>Quận</span>
                      </template>
                      <template #default="scope">
                        <el-form-item :rules="rulePolicy.district" :size="'default'"
                                      class="form-table"
                                      :prop="'productOthers.' + scope.$index + '.value'"
                        >
                          <el-select v-model="dataProductOffer.productOthers[scope.$index].district"
                                     placeholder="Chọn giá trị" style="width: 100%"
                                     filterable clearable
                                     reserve-keyword
                                     :disabled="isMode === 'view' || !dataProductOffer.productOthers[scope.$index].province"
                                     @change="fetchPrecinct(dataProductOffer.productOthers[scope.$index].district, scope.$index)"
                          >
                            <el-option
                                v-for="item in dataProductOffer.productOthers[scope.$index].districtData"
                                :key="item?.areaCode"
                                :label="item.name"
                                :value="item?.areaCode"
                            />
                          </el-select>
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="description" :label="'Huyện'" header-align="center"
                                     align="center">
                      <template #default="scope">
                        <el-form-item :rules="rulePolicy.precinct" :size="'default'"
                                      class="form-table"
                                      :prop="'productOthers.' + scope.$index + '.description'"
                        >
                          <el-select v-model="dataProductOffer.productOthers[scope.$index].precinct"
                                     placeholder="Chọn giá trị" style="width: 100%"
                                     filterable clearable
                                     reserve-keyword
                                     :disabled="isMode === 'view' || !dataProductOffer.productOthers[scope.$index].district"
                          >
                            <el-option
                                v-for="item in dataProductOffer.productOthers[scope.$index].precinctData"
                                :key="item?.areaCode"
                                :label="item.name"
                                :value="item?.areaCode"
                            />
                          </el-select>
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
                  <h3 class="style-header-box" style="width: 200px; text-align: left">Khách hàng áp dụng </h3>
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
                  <el-table :data="dataProductOffer.productOthers" border stripe>
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

                    <el-table-column header-align="center" align="left" prop="code" min-width="500">
                      <template #header>
                        <span style="color: var(--el-color-danger);">*</span> <span>Khách hàng</span>
                      </template>
                      <template #default="scope">
                        <el-row :gutter="24" style="margin: 0">
                          <el-col :xs="24" :md="18" :lg="18">
                            <el-form-item :rules="rulePolicy.code" :size="'default'"
                                          class="form-table"
                                          :prop="'productOthers.' + scope.$index + '.code'"
                            >
                              <el-input v-model="dataProductOffer.productOthers[scope.$index].code"
                                        :disabled="isMode === 'view'" clearable
                              />
                            </el-form-item>
                          </el-col>
                          <el-col :xs="24" :md="6" :lg="6">
                            <div class="form-table">
                              <el-button :icon="Search" size="default"
                                         color="var(--system-primary-color)"
                                         type="primary" @click="addPolicyProperty()">Tìm kiếm
                              </el-button>
                            </div>
                          </el-col>
                        </el-row>

                      </template>
                    </el-table-column>

                    <el-table-column prop="value" header-align="center" min-width="200"
                                     align="center">
                      <template #header>
                        <span>Loại giấy tờ</span>
                      </template>
                      <template #default="scope">
                        <el-form-item :rules="rulePolicy.value" :size="'default'"
                                      class="form-table"
                                      :prop="'productOthers.' + scope.$index + '.value'"
                        >
                          <el-input v-model="dataProductOffer.productOthers[scope.$index].value"
                                    :disabled="isMode === 'view'"
                                    style="height: 32px;" clearable
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="description" :label="'Số giấy tờ'" header-align="center"
                                     min-width="200"
                                     align="center">
                      <template #default="scope">
                        <el-form-item :rules="rulePolicy.description" :size="'default'"
                                      class="form-table"
                                      :prop="'productOthers.' + scope.$index + '.description'"
                        >
                          <el-input v-model="dataProductOffer.productOthers[scope.$index].description"
                                    clearable
                                    :disabled="isMode === 'view'"
                                    style="height: 32px;"
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="description" :label="'SĐT'" header-align="center"
                                     min-width="200"
                                     align="center">
                      <template #default="scope">
                        <el-form-item :rules="rulePolicy.description" :size="'default'"
                                      class="form-table"
                                      :prop="'productOthers.' + scope.$index + '.description'"
                        >
                          <el-input v-model="dataProductOffer.productOthers[scope.$index].description"
                                    clearable
                                    :disabled="isMode === 'view'"
                                    style="height: 32px;"
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="description" :label="'Email'" header-align="center" min-width="200"
                                     align="center">
                      <template #default="scope">
                        <el-form-item :rules="rulePolicy.description" :size="'default'"
                                      class="form-table"
                                      :prop="'productOthers.' + scope.$index + '.description'"
                        >
                          <el-input v-model="dataProductOffer.productOthers[scope.$index].description"
                                    clearable
                                    :disabled="isMode === 'view'"
                                    style="height: 32px;"
                          />
                        </el-form-item>
                      </template>
                    </el-table-column>
                    <el-table-column prop="description" :label="'Địa chỉ'" header-align="center" min-width="300"
                                     align="center">
                      <template #default="scope">
                        <el-form-item :rules="rulePolicy.description" :size="'default'"
                                      class="form-table"
                                      :prop="'productOthers.' + scope.$index + '.description'"
                        >
                          <el-input v-model="dataProductOffer.productOthers[scope.$index].description"
                                    clearable
                                    :disabled="isMode === 'view'"
                                    style="height: 32px;"
                          />
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
          <FormConfigDynamic :product="productData" :active-names="activeNames" :config="config" :data="productData"
                             :is-mode="isMode"/>

          <div v-if="isMode !== MODE_CREATE()" class="demo-collapse no-border-collapse" style="width: 100%;">
            <el-collapse :model-value="activeNames">
              <el-collapse-item>
                <template #title>
                  <h3 class="style-header-box">Lịch sử tác động</h3>
                </template>
                <div class="layout-container-table">
                  <History :id="dataProductOffer.productId" :type="AUDIT_TYPE_PRODUCT()"></History>
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
import {addProduct, fetchAllProduct, getProductDetail, updateProduct} from "@/api/product";
import dayjs from "dayjs"; // Import thư viện xử lý ngày
import PopupSearchCustomer from "@/views/main/customer/popupSearchCustomer.vue";
import History from "@/views/main/history/History.vue";
import {MODE_CREATE} from "@/constants/formMode";
import {AUDIT_TYPE_PRODUCT} from "@/constants/actionAudit";
import FormConfigDynamic from "@/views/main/dynamicForm/FormConfigDynamic.vue";
import {getProvince, getProvinceByParentCode} from "@/api/area";

export default defineComponent({
  components: {FormConfigDynamic, History, Expand, PopupConfig: PopupSearchCustomer},
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
      let endDate = this.dataProductOffer.endDate ? dayjs(this.dataProductOffer.endDate, "DD/MM/YYYY") : null;
      return dayjs(date).isBefore(today) || (endDate && dayjs(date).isAfter(endDate));
    },
    disableEndDate(date: any) {
      let startDate = this.dataProductOffer.startDate ? dayjs(this.dataProductOffer.startDate, "DD/MM/YYYY") : null;
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
    const dataProductOffer = reactive(
        {
          id: null,
          productId: null,
          code: null,
          name: null,
          productOfferType: null,
          provisionType: null,
          allocateType: null,
          volBase: null,
          volPromotion: null,
          description: null,
          startDate: null,
          endDate: null,
          date: [],
          productDetails: [] as any,
          productOthers: [] as any,
          systemConfigs: [] as any,
        });
    const productData = ref([]);
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
        {required: true, message: 'Vui lòng nhập Tên gói cước', trigger: 'blur'},
        {min: 1, max: 100, message: 'Tên đối tác tối đa 100 ký tự', trigger: 'blur'}
      ],
      code: [
        {required: true, message: 'Vui lòng nhập Mã gói cước', trigger: 'blur'},
        {min: 1, max: 50, message: 'Mã đối tác tối đa 50 ký tự', trigger: 'blur'}
      ],
      productId: [{required: true, message: 'Vui lòng chọn sản phẩm', trigger: 'change'}],
      provisionType: [{required: true, message: 'Vui lòng chọn Hình thức cung cấp', trigger: 'change'}],
      productOfferType: [{required: true, message: 'Vui lòng chọn Loại gói cước', trigger: 'change'}],
      volBase: [{required: true, message: 'Vui lòng nhập Số tháng tính phí', trigger: 'blur'}],
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
        Object.assign(dataProductOffer, {...data.data});
        dataProductOffer.productGroupId = String(dataProductOffer.productGroupId)
        if (dataProductOffer.productOthers) {
          let ids: any[] = [];
          dataProductOffer.productOthers?.forEach(item => {
            ids = [item.id, ...ids];
          })
          if (ids && ids.length > 0) {
            const payload = {
              objectIds: ids,
              type: uploadType
            }
            const res = await getFiles(payload);
            let listFile = {};

            dataProductOffer.productOthers.forEach((item, idx) => {
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
    const fetchDistrict = async (parentCode: string, index : any) => {
      try {
        if (parentCode) {
          loading.value = true;
          const {data} = await getProvinceByParentCode({parentCode: parentCode});
          districtData.value = data.data;
          dataProductOffer.productOthers[index].districtData = districtData.value
          console.log("index", index)
          console.log("dataProductOffer.productOthers", dataProductOffer.productOthers)
          loading.value = false;
        }
      } catch (error) {
        console.error(error);
        loading.value = false;
      }
    }
    const fetchPrecinct = async (parentCode: string, index : any) => {
      try {
        if (parentCode) {
          loading.value = true;
          const {data} = await getProvinceByParentCode({parentCode: parentCode});
          precinctData.value = data.data;
          dataProductOffer.productOthers[index].precinctData = precinctData.value
          loading.value = false;
        }
      } catch (error) {
        console.error(error);
        loading.value = false;
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
      if (!dataProductOffer.productDetails || dataProductOffer.productDetails.length === 0) {
        message = 'Vui lòng thêm Danh sách mặt hàng';
      }
      if (!dataProductOffer.productOthers || dataProductOffer.productOthers.length === 0) {
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
          const axiosResponse = await addProduct(dataProductOffer);
          handleSuccess(axiosResponse.data?.data.message);
        } else if (isMode === 'edit') {
          // @ts-ignore
          if (handleErrorValue) return;
          await processUpload()
          if (filesRemove.value) {
            dataProductOffer.filesRemove = [...filesRemove.value]
          }
          dataProductOffer.id = router.currentRoute.value.params?.data;
          const axiosResponse = await updateProduct(dataProductOffer);
          handleSuccess(axiosResponse.data?.data.message);
        }
      } catch (error) {
        console.error(error);
      } finally {
        loading.value = false;
      }
    };

    const processUpload = async () => {
      const uploadPromises = dataProductOffer.productOthers.map(async (detail: any) => {
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
      dataProductOffer.productDetails.push({
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
      dataProductOffer.productDetails.splice(index, 1);
    }

    const addPolicyProperty = () => {
      event.stopPropagation();
      dataProductOffer.productOthers.push({
        code: null,
        name: null,
        description: null,
      });
    };

    const removePolicyProperty = (index: number) => {
      dataProductOffer.productDetails[index]?.filesData?.forEach(item => {
        handleFileRemove(item)
      })
      dataProductOffer.productOthers.splice(index, 1);
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
          Object.assign(dataProductOffer,
              {
                id: null,
                productId: null,
                code: null,
                name: null,
                productOfferType: null,
                provisionType: null,
                allocateType: null,
                volBase: null,
                volPromotion: null,
                description: null,
                startDate: null,
                endDate: null,
                date: [],
                productDetails: [] as any,
                productOthers: [] as any,
                systemConfigs: [] as any,
              }
          );
    };

    onMounted(async () => {
      await fetchProduct()
      await fetchProvince()
      if (isMode === 'edit' || isMode === 'view') {
        await findById();
      }
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

    //
    const filesRemove = ref([]);
    const fileList = reactive<Record<number, UploadFile[]>>({});
    const handleFileChange = async (index: number, updatedFileList: UnwrapNestedRefs<Record<number, UploadFile[]>> & {}) => {
      fileList[index] = updatedFileList;
      dataProductOffer.productOthers[index].filesData = updatedFileList;
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
          dataProductOffer.systemConfigs.push(data)
        } else if (popupMode.value == 'edit') {
          const index = dataProductOffer.systemConfigs.findIndex((item: any) => item.uuid === data.uuid);
          const indexUpdate = dataProductOffer.systemConfigs.findIndex((item: any) => item.id === data.id);
          if (index !== -1) {
            dataProductOffer.systemConfigs[index] = {
              ...dataProductOffer.systemConfigs[index],
              ...data,
            };
          }
          if (indexUpdate !== -1) {
            dataProductOffer.systemConfigs[indexUpdate] = {
              ...dataProductOffer.systemConfigs[indexUpdate],
              ...data,
            };
          }
        }
      }
      isPopupVisible.value = false;
    };

    const removeDataConfig = (index: number) => {
      dataProductOffer.systemConfigs.splice(index, 1);
    }
    return {
      store,
      loading,
      dataProductOffer,
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
      selectedData,
      productData,
      fetchDistrict,
      fetchPrecinct
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