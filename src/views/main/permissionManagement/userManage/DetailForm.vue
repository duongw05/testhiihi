<template>

  <el-row :gutter="24" style="margin: 2vh">
    <el-col :xs="24" :md="12" :lg="8" class="detail-form">
      <el-avatar :size="100" :src="dataUser.avatar"></el-avatar>
      <el-form label-position="left" label-width="100px">
        <el-form-item>
          <b>{{ dataUser.email }}</b>
        </el-form-item>
        <el-form-item label="Tên">
          <span>{{ dataUser.fullName }}</span>
        </el-form-item>
        <el-form-item label="Ngày sinh">
          <span>{{ dataUser.dobStr }}</span>
        </el-form-item>
        <el-form-item label="Giới tính">
          <span>{{ dataUser.genderStr }}</span>
        </el-form-item>
        <el-form-item label="Số điện thoại">
          <span>{{ dataUser.phone }}</span>
        </el-form-item>
      </el-form>
    </el-col>

<!--    <el-col :xs="24" :md="12" :lg="12" class="detail-form" style="margin-left: 3vh">-->
<!--      <el-form label-position="left" label-width="100px">-->
<!--        <el-form-item>-->
<!--          <b>{{ dataUser.email }}</b>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="Tên">-->
<!--          <span>{{ dataUser.fullName }}</span>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="Ngày sinh">-->
<!--          <span>{{ dataUser.dobStr }}</span>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="Giới tính">-->
<!--          <span>{{ dataUser.genderStr }}</span>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="Số điện thoại">-->
<!--          <span>{{ dataUser.phone }}</span>-->
<!--        </el-form-item>-->
<!--      </el-form>-->
<!--    </el-col>-->
<!--  </el-row>-->

<!--  <el-row :gutter="24" style="margin: 2vh">-->
<!--    <el-col :xs="24" :md="12" :lg="8" class="detail-form">-->
<!--      <el-form label-position="left" label-width="100px">-->
<!--        <el-form-item>-->
<!--          <b>{{ dataUser.email }}</b>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="Tên">-->
<!--          <span>{{ dataUser.fullName }}</span>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="Ngày sinh">-->
<!--          <span>{{ dataUser.dobStr }}</span>-->
<!--        </el-form-item>-->
<!--      </el-form>-->
<!--    </el-col>-->
<!--  </el-row>-->

</template>

<script lang="ts">
import {defineComponent, onMounted, reactive} from "vue";
import {ElRow, ElCol, ElAvatar, ElForm, ElFormItem} from "element-plus";
import {useRouter} from "vue-router";
import {getUserById} from "@/api/user";

export default defineComponent({
  components: {
    ElRow,
    ElCol,
    ElAvatar,
    ElForm,
    ElFormItem,
  },
  props: {},
  setup(props, {emit}) {
    const router = useRouter();
    const dataUser = reactive(
        {
          avatar: "C:\\Users\\VINH\\Pictures\\Camera Roll\\VTS - Thiều Quang Vinh.JPG",
          id: null,
          username: null,
          email: null,
          phone: null,
          fullName: null,
          dob: null,
          gender: null,
          genderStr: null,
          dobStr: null
        });

    const getDataUserById = async () => {
      try {
        let id = router.currentRoute.value.params?.data
        const { data } = await getUserById(id as unknown as number)
        Object.assign(dataUser, { ...data.data })
      } catch (e) {
        console.log(e)
      }
    }

    onMounted( async () => {
      await getDataUserById()
    })

    return {
      dataUser
    }
  },
});
</script>

<style scoped>

.detail-form {
  padding: 2vh;
  background-color: #ffffff;
  border-radius: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  align-items: center;
}

::v-deep(.el-avatar) {
  margin-bottom: 20px;
}

::v-deep(.el-form) {
  width: 100%;
  text-align: center;
}

::v-deep(.el-form-item) {
  margin-bottom: 15px;
  border-bottom: 1px solid #dcdcdc;
  padding-bottom: 10px;
  display: flex;
  justify-content: center;
}

::v-deep(.el-form-item__label) {
  font-weight: bold;
  text-align: left;
  flex: 0 0 100px;
}

::v-deep(.el-form-item__content) {
  flex: 1;
  text-align: left;
}

::v-deep(.el-form-item:last-child) {
  border-bottom: none;
  padding-bottom: 0;
}

</style>