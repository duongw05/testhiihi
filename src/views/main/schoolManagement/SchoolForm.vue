<!--<template>-->
<!--  <el-drawer-->
<!--      :title="formTitle"-->
<!--      v-model="visibleRef"-->
<!--      direction="rtl"-->
<!--      size="900px"-->
<!--      :before-close="handleClose"-->
<!--      destroy-on-close-->
<!--  >-->
<!--    <template #header="{ titleId }">-->
<!--      <div class="custom-drawer-header">-->
<!--        <h4 :id="titleId" class="drawer-title">{{ formTitle }}</h4>-->
<!--        <div class="drawer-header-buttons">-->
<!--          <el-button @click="handleClose">Đóng</el-button>-->
<!--          <el-button v-if="!isView" type="success" @click="handleSubmit">-->
<!--            {{ submitButtonText }}-->
<!--          </el-button>-->
<!--        </div>-->
<!--      </div>-->
<!--    </template>-->

<!--    <el-form-->
<!--        :model="form"-->
<!--        :rules="rules"-->
<!--        ref="formRef"-->
<!--        label-position="top"   class="school-form-layout"-->
<!--    >-->
<!--      <el-row :gutter="20">-->
<!--        <el-col :span="12">-->
<!--          <el-form-item label="Mã trường học" prop="code">-->
<!--            <el-input v-model="form.code" :disabled="isView" placeholder="Nhập Mã trường học"/>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--        <el-col :span="12">-->
<!--          <el-form-item label="Tên trường học" prop="name">-->
<!--            <el-input v-model="form.name" :disabled="isView" placeholder="Nhập Tên trường học"/>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--      </el-row>-->

<!--      <el-row :gutter="20">-->
<!--        <el-col :span="12">-->
<!--          <el-form-item label="Working Offsite">-->
<!--            <el-select v-model="form.workingOffsiteId" placeholder="Chọn Working Offsite" :disabled="isView">-->
<!--              <el-option v-for="i in workingOffsiteList" :key="i.id" :label="i.name" :value="i.id" />-->
<!--            </el-select>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--        <el-col :span="12">-->
<!--          <el-form-item label="Cấp học" prop="schoolTypeId">-->
<!--            <el-select v-model="form.schoolTypeId" placeholder="Chọn Cấp học" :disabled="isView">-->
<!--              <el-option v-for="i in schoolTypeList" :key="i.id" :label="i.name" :value="i.id" />-->
<!--            </el-select>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--      </el-row>-->

<!--      <el-row :gutter="20">-->
<!--        <el-col :span="12">-->
<!--          <el-form-item label="Premium school">-->
<!--            <el-radio-group v-model="form.isPremium" :disabled="isView">-->
<!--              <el-radio :label="1">Có</el-radio>-->
<!--              <el-radio :label="0">Không</el-radio>-->
<!--            </el-radio-group>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--        <el-col :span="12">-->
<!--          <el-form-item label="Chương trình Toán-Khoa">-->
<!--            <el-checkbox-group v-model="form.programs" :disabled="isView">-->
<!--              <el-checkbox label="Toán">Toán</el-checkbox>-->
<!--              <el-checkbox label="Khoa học">Khoa học</el-checkbox>-->
<!--            </el-checkbox-group>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--      </el-row>-->

<!--      <el-row :gutter="20">-->
<!--        <el-col :span="12">-->
<!--          <el-form-item label="Bản đồ">-->
<!--            <el-input v-model="form.mapLink" :disabled="isView" placeholder="Nhập Bản đồ"/>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--        <el-col :span="12">-->
<!--          <el-form-item label="Khu vực (Phường)">-->
<!--            <el-input v-model="form.areaPhuong" :disabled="isView" placeholder="Nhập Khu vực (Phường)"/>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--      </el-row>-->

<!--      <el-row :gutter="20">-->
<!--        <el-col :span="12">-->
<!--          <el-form-item label="Địa chỉ">-->
<!--            <el-input v-model="form.address" :disabled="isView" placeholder="Nhập Địa chỉ"/>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--        <el-col :span="12">-->
<!--          <el-form-item label="Khu vực theo CVCT (Zone)">-->
<!--            <el-input v-model="form.areaCvct" :disabled="isView" placeholder="Nhập Khu vực theo CVCT (Zone)"/>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--      </el-row>-->

<!--      <el-row :gutter="20">-->
<!--        <el-col :span="8">-->
<!--          <el-form-item label="RHTA">-->
<!--            <el-select v-model="form.rhta" placeholder="Chọn RHTA" :disabled="isView">-->
<!--              <el-option v-for="u in userList" :key="u.id" :label="u.fullName" :value="u.id" />-->
<!--            </el-select>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--        <el-col :span="8">-->
<!--          <el-form-item label="Scheduler">-->
<!--            <el-select v-model="form.scheduler" placeholder="Chọn Scheduler" :disabled="isView">-->
<!--              <el-option v-for="u in userList" :key="u.id" :label="u.fullName" :value="u.id" />-->
<!--            </el-select>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--        <el-col :span="8">-->
<!--          <el-form-item label="Scheduler‑cum‑TA">-->
<!--            <el-select v-model="form.schedulerCumTa" placeholder="Chọn Scheduler‑cum‑TA" :disabled="isView">-->
<!--              <el-option v-for="u in userList" :key="u.id" :label="u.fullName" :value="u.id" />-->
<!--            </el-select>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--      </el-row>-->

<!--      <el-row :gutter="20">-->
<!--        <el-col :span="12">-->
<!--          <el-form-item label="Số phút công/ số phút thực tế">-->
<!--            <el-input v-model="form.minutesActual" :disabled="isView" placeholder="Nhập Số phút công/ số phút thực tế" />-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--        <el-col :span="12">-->
<!--          <el-form-item label="Pacing guides">-->
<!--            <el-input v-model="form.pacingGuides" :disabled="isView" placeholder="Nhập Pacing guides" />-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--      </el-row>-->

<!--      <el-row :gutter="20">-->
<!--        <el-col :span="12">-->
<!--          <el-form-item label="Năm học bắt đầu hợp tác">-->
<!--            <el-date-picker-->
<!--                v-model="form.startDate"-->
<!--                type="year"-->
<!--                placeholder="YYYY"-->
<!--                value-format="YYYY"-->
<!--                :disabled="isView"-->
<!--                style="width: 100%;" />-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--        <el-col :span="12">-->
<!--          <el-form-item label="Trạng thái">-->
<!--            <el-select v-model="form.status" :disabled="isView" placeholder="Chọn trạng thái">-->
<!--              <el-option label="Hiệu lực" :value="1" />-->
<!--              <el-option label="Hết hiệu lực" :value="0" />-->
<!--            </el-select>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--      </el-row>-->

<!--      <el-row :gutter="20">-->
<!--        <el-col :span="12">-->
<!--          <el-form-item label="Sơ đồ trường" class="form-item-school-map">-->
<!--            <img v-if="form.schoolMapImage" :src="form.schoolMapImage" class="school-map-image"/>-->
<!--            <el-upload class="upload-demo" action="#" :auto-upload="false" :show-file-list="false" :disabled="isView" :on-change="handleMapImageChange">-->
<!--              <el-button size="small" type="primary" :disabled="isView">Upload</el-button>-->
<!--            </el-upload>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--        <el-col :span="12">-->
<!--          <el-form-item label="Mô tả">-->
<!--            <el-input type="textarea" v-model="form.description" :disabled="isView" :rows="3" placeholder="Nhập mô tả" />-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--      </el-row>-->

<!--    </el-form>-->
<!--  </el-drawer>-->
<!--</template>-->

<!--<script setup lang="ts">-->
<!--import { ref, watch, computed, onMounted } from 'vue';-->
<!--import { ElMessage, ElNotification } from 'element-plus';-->
<!--import { fetchAllSchoolType } from '@/api/schoolType';-->
<!--import { fetchAllWorkingOffsite } from '@/api/workingOffsite';-->
<!--import { fetchAllTblUser } from '@/api/tblUser';-->
<!--import { addSchool, getSchoolDetail, updateSchool } from '@/api/school';-->

<!--const props = defineProps({-->
<!--  visible: Boolean,-->
<!--  mode: String, // 'create' | 'edit' | 'view'-->
<!--  schoolId: Number,-->
<!--});-->
<!--const emit = defineEmits(['update:visible','success']);-->

<!--const visibleRef = ref(props.visible);-->
<!--watch(() => props.visible, v => visibleRef.value = v);-->
<!--watch(() => visibleRef.value, v => emit('update:visible', v));-->

<!--const isView = computed(() => props.mode === 'view');-->
<!--const formTitle = computed(() => props.mode === 'create' ? 'Thêm mới trường học' : props.mode === 'edit' ? 'Cập nhật trường học' : 'Chi tiết trường học');-->
<!--const submitButtonText = computed(() => props.mode === 'create' ? 'Thêm mới' : 'Cập nhật');-->

<!--const formRef = ref();-->
<!--const form = ref<any>({-->
<!--  id:null, name:'', code:'', schoolTypeId:null, workingOffsiteId:null,-->
<!--  isPremium:1, areaPhuong:'', areaCvct:'', programs:[],-->
<!--  rhta:null, scheduler:null, schedulerCumTa:null, status:1,-->
<!--  mapLink:'', address:'', minutesActual:null, startDate:null, schoolMapImage:'', description:'',-->
<!--  pacingGuides: ''-->
<!--});-->

<!--// Rules-->
<!--const rules = {-->
<!--  code:[{required:true,message:'Mã trường học bắt buộc',trigger:'blur'}],-->
<!--  name:[{required:true,message:'Tên trường học bắt buộc',trigger:'blur'}],-->
<!--  schoolTypeId:[{required:true,message:'Cấp học bắt buộc',trigger:'change'}],-->
<!--  // Thêm rules cho các trường khác nếu cần-->
<!--};-->

<!--// Load options for dropdowns-->
<!--const schoolTypeList = ref<any[]>([]);-->
<!--const workingOffsiteList = ref<any[]>([]);-->
<!--const userList = ref<any[]>([]);-->

<!--const loadOptions = async () => {-->
<!--  try {-->
<!--    const [s, w, u] = await Promise.all([fetchAllSchoolType(), fetchAllWorkingOffsite(), fetchAllTblUser()]);-->
<!--    schoolTypeList.value = s.data.data || [];-->
<!--    workingOffsiteList.value = w.data.data || [];-->
<!--    userList.value = u.data.data || [];-->
<!--  } catch { ElNotification.error({title:'Lỗi', message:'Không thể lấy dữ liệu tùy chọn'}); }-->
<!--};-->

<!--// Load school detail for edit/view mode-->
<!--const loadDetail = async () => {-->
<!--  try {-->
<!--    const res = await getSchoolDetail(props.schoolId);-->
<!--    const v = res.data.data;-->
<!--    form.value = {-->
<!--      ...v,-->
<!--      programs: typeof v.programs === 'string' ? v.programs.split(',') : v.programs || [],-->
<!--      isPremium: v.isPremium ?? 0,-->
<!--      minutesActual: v.minutesActual ?? null,-->
<!--      pacingGuides: v.pacingGuides ?? '',-->
<!--      startDate: v.startDate ?? null-->
<!--    };-->
<!--  } catch {-->
<!--    ElNotification.error({ title:'Lỗi', message:'Không thể tải chi tiết trường học.' });-->
<!--  }-->
<!--};-->

<!--// Watch for visibility and schoolId changes-->
<!--watch(() => [props.visible, props.schoolId], ([vis, id]) => {-->
<!--  if (vis) {-->
<!--    if (props.mode === 'create') {-->
<!--      form.value = {-->
<!--        id:null, name:'', code:'', schoolTypeId:null, workingOffsiteId:null,-->
<!--        isPremium:1, areaPhuong:'', areaCvct:'', programs:[],-->
<!--        rhta:null, scheduler:null, schedulerCumTa:null, status:1,-->
<!--        mapLink:'', address:'', minutesActual:null, startDate:null, schoolMapImage:'', description:'',-->
<!--        pacingGuides: ''-->
<!--      };-->
<!--      formRef.value?.clearValidate();-->
<!--    } else if (id) {-->
<!--      loadDetail();-->
<!--    }-->
<!--  } else {-->
<!--    formRef.value?.clearValidate();-->
<!--  }-->
<!--}, { immediate: true });-->

<!--onMounted(loadOptions);-->

<!--const handleClose = () => { visibleRef.value = false; };-->

<!--const handleSubmit = async () => {-->
<!--  try {-->
<!--    await formRef.value.validate();-->
<!--    const dataToSend = {-->
<!--      ...form.value,-->
<!--      programs: Array.isArray(form.value.programs) ? form.value.programs.join(',') : form.value.programs,-->
<!--      minutesActual: form.value.minutesActual || null,-->
<!--      pacingGuides: form.value.pacingGuides || null,-->
<!--      startDate: form.value.startDate || null,-->
<!--    };-->

<!--    if (props.mode==='create') {-->
<!--      await addSchool(dataToSend);-->
<!--      ElMessage.success('Thêm trường học thành công!');-->
<!--    } else if (props.mode==='edit') {-->
<!--      await updateSchool(dataToSend);-->
<!--      ElMessage.success('Cập nhật trường học thành công!');-->
<!--    }-->
<!--    emit('success');-->
<!--    handleClose();-->
<!--  } catch (e) {-->
<!--    console.error('Lỗi khi lưu trường học:', e);-->
<!--    ElMessage.error('Đã có lỗi, vui lòng kiểm tra lại thông tin.');-->
<!--  }-->
<!--};-->

<!--const handleMapImageChange = (file:any) => {-->
<!--  if (file.raw) {-->
<!--    const reader = new FileReader();-->
<!--    reader.onload = e => {-->
<!--      form.value.schoolMapImage = e.target?.result as string;-->
<!--    };-->
<!--    reader.readAsDataURL(file.raw);-->
<!--  }-->
<!--};-->
<!--</script>-->

<!--<style scoped>-->
<!--.custom-drawer-header {-->
<!--  display: flex;-->
<!--  justify-content: space-between;-->
<!--  align-items: center;-->
<!--  padding-right: 20px;-->
<!--  width: 100%;-->
<!--}-->
<!--.drawer-title {-->
<!--  font-size: 20px;-->
<!--  font-weight: bold;-->
<!--  color: #333;-->
<!--}-->
<!--.drawer-header-buttons {-->
<!--  display: flex;-->
<!--  gap: 10px;-->
<!--}-->
<!--.school-form-layout {-->
<!--  display: flex;-->
<!--  flex-direction: column;-->
<!--  padding: 0 20px;-->
<!--}-->
<!--.el-row {-->
<!--  margin-bottom: 18px;-->
<!--}-->
<!--.el-form-item {-->
<!--  margin-bottom: 0 !important;-->
<!--}-->

<!--/* Kích thước và căn chỉnh chung cho input, select, radio, checkbox */-->
<!--.el-form-item :deep(.el-input__inner),-->
<!--.el-form-item :deep(.el-select__wrapper),-->
<!--.el-form-item :deep(.el-date-editor .el-input__wrapper),-->
<!--.el-form-item :deep(.el-textarea__inner) {-->
<!--  height: 36px;-->
<!--  line-height: 36px;-->
<!--  padding: 0 12px;-->
<!--  font-size: 14px;-->
<!--}-->
<!--.el-form-item :deep(.el-textarea__inner) {-->
<!--  height: auto !important;-->
<!--  min-height: 70px !important;-->
<!--  padding: 8px 12px;-->
<!--  line-height: 1.5;-->
<!--}-->

<!--/* Đảm bảo các thành phần bên trong form-item chiếm toàn bộ chiều rộng của cột */-->
<!--.el-form-item :deep(.el-select),-->
<!--.el-form-item :deep(.el-date-editor),-->
<!--.el-form-item :deep(.el-radio-group),-->
<!--.el-form-item :deep(.el-checkbox-group),-->
<!--.el-form-item :deep(.el-input) {-->
<!--  width: 100%;-->
<!--}-->

<!--/* Style cho label khi label-position="top" */-->
<!--:deep(.el-form-item__label) {-->
<!--  font-size: 14px;-->
<!--  line-height: 22px;-->
<!--  padding-bottom: 4px;-->
<!--  height: auto !important;-->
<!--  white-space: normal;-->
<!--}-->

<!--/* Căn chỉnh nội dung của checkbox-group và radio-group nếu cần */-->
<!--.el-form-item :deep(.el-radio-group),-->
<!--.el-form-item :deep(.el-checkbox-group) {-->
<!--  display: flex;-->
<!--  align-items: center;-->
<!--  flex-wrap: wrap; /* Cho phép các mục xuống dòng nếu không đủ chỗ */-->
<!--}-->
<!--.el-form-item :deep(.el-radio),-->
<!--.el-form-item :deep(.el-checkbox) {-->
<!--  margin-right: 20px;-->
<!--  /* Điều chỉnh margin-right cuối cùng nếu cần để tránh khoảng cách thừa */-->
<!--}-->


<!--/* Điều chỉnh style cho form-item Sơ đồ trường */-->
<!--.form-item-school-map :deep(.el-form-item__content) {-->
<!--  display: flex;-->
<!--  flex-direction: column;-->
<!--  align-items: flex-start;-->
<!--}-->
<!--.school-map-image {-->
<!--  max-width: 120px;-->
<!--  max-height: 120px;-->
<!--  margin-bottom: 10px;-->
<!--  border: 1px solid #eee;-->
<!--  padding: 5px;-->
<!--  box-sizing: border-box;-->
<!--}-->

<!--/* Điều chỉnh lại padding của drawer body nếu cần */-->
<!--:deep(.el-drawer__body) {-->
<!--  padding-top: 0px;-->
<!--}-->
<!--</style>-->