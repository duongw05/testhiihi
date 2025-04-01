<template>
  <div class="demo-collapse no-border-collapse" style="width: 100%;">
    <el-collapse :model-value="activeNames">
      <el-collapse-item name="2">
        <template #title>
          <h3 class="style-header-box">Thông tin bổ sung </h3>
        </template>
        <div class="layout-container-table">
          <template v-for="(field, inx) in config">
            <el-row :gutter="24">
              <el-col :span="6">
                <el-form-item label="Sản phẩm" :size="'default'">
                  <el-select
                      :size="'default'"
                      v-model="field.productId"
                      :disabled="true"
                      clearable
                  >
                    <el-option
                        v-for="option in product"
                        :key="option.code"
                        :label="option.name"
                        :value="option.productId"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <template v-for="(field, index) in field.fields">
                <el-col :span="field.span">
                  <el-form-item size="default" class="form-item"
                                :key="index"
                                :label="field.label"
                                :rules="field.rules"
                                :prop="field.key">
                    <template v-if="field.type === 'TEXT'">
                      <el-input
                          v-model="data[field.key]"
                          :placeholder="field.placeholder"
                          :disabled="isMode === 'view'"
                          clearable
                      />
                    </template>
                    <template v-else-if="field.type === 'COMBOBOX'">
                      <el-select
                          :size="'default'"
                          v-model="data[field.key]"
                          :placeholder="field.placeholder"
                          :disabled="isMode === 'view'"
                          clearable
                      >
                        <el-option
                            v-for="option in field.options"
                            :key="option.valueCode"
                            :label="option.valueLabel"
                            :value="option.valueCode"
                        />
                      </el-select>
                    </template>
                  </el-form-item>
                </el-col>
              </template>
            </el-row>
          </template>
        </div>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>
<script lang="ts">
export default {
  name: 'FormConfigDynamic',
  props: {
    activeNames: {},
    config: {},
    data: {},
    isMode: {},
    product: []
  }
}
</script>
<style scoped>
.form-item {
  margin-bottom: 18px; /* Khoảng cách giữa các hàng theo chiều dọc */
}

</style>