<template>
  <el-table :data="teacherTable" style="width: 100%">
    <el-table-column prop="id" label="用户ID" width="180" />
    <el-table-column prop="name" label="用户名" width="180" />
    <el-table-column label="头像" >
      <template #default="scope">
        <div style="display: flex; align-items: center">
          <el-avatar :src="scope.row.photo" :size="small"/>
        </div>
      </template>
    </el-table-column>
    <el-table-column prop="email" label="邮箱" />
    <el-table-column prop="declaration" label="个人宣言" />
    <el-table-column prop="joinTime" label="加入时间" />
    <el-table-column label="背景图片" >
      <template #default="scope">
        <div style="display: flex; align-items: center">
          <el-avatar :src="scope.row.background" :size="small"/>
        </div>
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template #default="scope">
        <el-button size="small" @click="handleEdit(scope.row)">
          Edit
        </el-button>
        <el-button size="small" type="danger" @click="delete_teacher(scope.row)">
          Delete
        </el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-dialog
    title="Edit Teacher"
    v-model="modifydialog"
    width="70%"
    :before-close="handleClose"
>  
  <el-form :model="teacherProfile" label-width="120px">
      <el-form-item label="Username">
        <el-input v-model="teacherProfile.username" />
      </el-form-item>
      <el-form-item label="Photo Url">
        <el-input v-model="teacherProfile.photo" />
      </el-form-item>
      <el-form-item label="Background Url">
        <el-input v-model="teacherProfile.background" />
      </el-form-item>
      <el-form-item label="Username">
        <el-input v-model="teacherProfile.declaration" />
      </el-form-item>
      <el-form-item label="Email">
        <el-input v-model="teacherProfile.email" />
      </el-form-item>
      <el-form-item label="Join Time">
        <el-col :span="11">
          <el-date-picker
            v-model="teacherProfile.joinTime"
            type="datetime"
            placeholder="Pick date and time"
            style="width: 100%"
          />
        </el-col>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">Submit</el-button>
        <el-button @click="modifydialog = false">Cancel</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
import $ from "jquery"
import { useStore } from "vuex"
import { reactive, ref } from "vue"
import { ElMessage, ElMessageBox } from 'element-plus'

export default {
  setup() {
    let teacherTable = ref([]);
    let store = useStore();
    let modifydialog = ref(false);
    let teacherProfile = reactive({
      id: '',
      username: '',
      photo: '',
      email: '',
      declaration: '',
      joinTime: '',
      background: '',
    })

    const getTeacherTable = () => {
      $.ajax({
        url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/admin/teacher/get/",
        type: "post",
        headers: {
            Authorization: "Bearer " + store.state.user.token,
        },
        success(resp) {
          teacherTable.value = resp;
        },
        error(resp) {
          console.log(resp);
        }
      })
    }
    const handleEdit = (User) => {
      teacherProfile.id = User.id;
      teacherProfile.username = User.name;
      teacherProfile.photo = User.photo;
      teacherProfile.email = User.email;
      teacherProfile.declaration = User.declaration;
      teacherProfile.joinTime = User.joinTime;
      teacherProfile.background = User.background;
      modifydialog.value = true;
    }
    const onSubmit = () => {
      console.log("summin")
      modifydialog.value = false
      $.ajax({
        url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/admin/teacher/modify/",
        type: "post",
        headers: {
            Authorization: "Bearer " + store.state.user.token,
        },
        data: {
          teacherId: teacherProfile.id,
          teacherName: teacherProfile.username,
          teacherPhoto: teacherProfile.photo,
          teacherEmail: teacherProfile.email,
          teacherDeclaration: teacherProfile.declaration,
          teacherJoinTime: teacherProfile.joinTime,
          teacherBackground: teacherProfile.background,
        },
        success(resp) {
          if (resp.error_message === "success") {
            ElMessage.success("修改信息成功");
            getTeacherTable();
            location.reload();
          } else if (resp.error_message === "error") {
            ElMessage.error("修改信息失败");
          }
        },
        error(resp) {
          console.log(resp);
        }
      })
    }

    const delete_t = rowdata => {
      $.ajax({
          url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/admin/teacher/delete/",
          type: "post",
          headers: {
              Authorization: "Bearer " + store.state.user.token,
          },
          data: {
              teacherId: rowdata.id,
          },
          success(resp) {
              if (resp.error_message === "success") {
                ElMessage.success("删除教师成功，当前用户已成为普通用户（学生）");
                location.reload();
              } else if (resp.error_message === "error") {
                  ElMessage.error("删除教师失败");
              } else {
                  ElMessage.error("未知错误");
              }
          },
          error() {
              ElMessage.error("服务器错误");
          }
      })
      getTeacherTable();
    }

    const delete_teacher = rowdata => {
      ElMessageBox.confirm(
        '当前教师将被删除',
        'Warning',
        {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning',
        }
      )
        .then(() => {
            delete_t(rowdata);
      })
      .catch(() => {
        ElMessage({
            type: 'info',
            message: 'Delete canceled',
        })
      })
    }

    getTeacherTable();
    return {
      teacherTable,
      handleEdit,
      teacherProfile,
      modifydialog,
      onSubmit,
      delete_teacher,
    }
  }
}
</script>

<style>

</style>