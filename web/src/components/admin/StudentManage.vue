<template>
  <el-table :data="studentTable" style="width: 100%">
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
    <el-table-column label="操作" width="300">
      <template #default="scope">
        <el-button size="small" type="info" @click="add_teacher(scope.row)">
          up
        </el-button>
        <el-button size="small" @click="handleEdit(scope.row)">
          Edit
        </el-button>
        <el-button size="small" type="danger" @click="delete_student(scope.row)">
          Delete
        </el-button>
        
      </template>
    </el-table-column>
  </el-table>
  <el-dialog
    title="Edit Student"
    v-model="modifydialog"
    width="70%"
    :before-close="handleClose"
>  
  <el-form :model="studentProfile" label-width="120px">
      <el-form-item label="Username">
        <el-input v-model="studentProfile.username" />
      </el-form-item>
      <el-form-item label="Photo Url">
        <el-input v-model="studentProfile.photo" />
      </el-form-item>
      <el-form-item label="Background Url">
        <el-input v-model="studentProfile.background" />
      </el-form-item>
      <el-form-item label="Username">
        <el-input v-model="studentProfile.declaration" />
      </el-form-item>
      <el-form-item label="Email">
        <el-input v-model="studentProfile.email" />
      </el-form-item>
      <el-form-item label="Join Time">
        <el-col :span="11">
          <el-date-picker
            v-model="studentProfile.joinTime"
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
    let studentTable = ref([]);
    let store = useStore();
    let modifydialog = ref(false);
    let studentProfile = reactive({
      id: '',
      username: '',
      photo: '',
      email: '',
      declaration: '',
      joinTime: '',
      background: '',
    })

    const getStudentTable = () => {
      $.ajax({
        url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/admin/student/get/",
        type: "post",
        headers: {
            Authorization: "Bearer " + store.state.user.token,
        },
        success(resp) {
          studentTable.value = resp;
        },
        error(resp) {
          console.log(resp);
        }
      })
    }
    const handleEdit = (User) => {
      studentProfile.id = User.id;
      studentProfile.username = User.name;
      studentProfile.photo = User.photo;
      studentProfile.email = User.email;
      studentProfile.declaration = User.declaration;
      studentProfile.joinTime = User.joinTime;
      studentProfile.background = User.background;
      modifydialog.value = true;
    }
    const onSubmit = () => {
      console.log("summin")
      modifydialog.value = false
      $.ajax({
        url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/admin/student/modify/",
        type: "post",
        headers: {
            Authorization: "Bearer " + store.state.user.token,
        },
        data: {
          studentId: studentProfile.id,
          studentName: studentProfile.username,
          studentPhoto: studentProfile.photo,
          studentEmail: studentProfile.email,
          studentDeclaration: studentProfile.declaration,
          studentJoinTime: studentProfile.joinTime,
          studentBackground: studentProfile.background,
        },
        success(resp) {
          if (resp.error_message === "success") {
            ElMessage.success("修改信息成功");
            getStudentTable();
          } else if (resp.error_message === "error") {
            ElMessage.error("修改信息失败");
          }
        },
        error(resp) {
          console.log(resp);
        }
      })
    }

    const delete_s = rowdata => {
      $.ajax({
          url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/admin/student/delete/",
          type: "post",
          headers: {
              Authorization: "Bearer " + store.state.user.token,
          },
          data: {
              studentId: rowdata.id,
          },
          success(resp) {
              if (resp.error_message === "success") {
                ElMessage.success("删除用户成功");
                location.reload();
              } else if (resp.error_message === "error") {
                  ElMessage.error("删除用户失败");
              } else {
                  ElMessage.error("未知错误");
              }
          },
          error() {
              ElMessage.error("服务器错误");
          }
      })
      getStudentTable();
    }

    const delete_student = rowdata => {
      ElMessageBox.confirm(
        '当前学生将被删除（删除所有信息，除exercise相关信息）',
        'Warning',
        {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning',
        }
      )
        .then(() => {
            delete_s(rowdata);
      })
      .catch(() => {
        ElMessage({
            type: 'info',
            message: 'Delete canceled',
        })
      })
    }

    const add_teacher = rowdata => {
      $.ajax({
          url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/admin/user/teacher/",
          type: "post",
          headers: {
              Authorization: "Bearer " + store.state.user.token,
          },
          data: {
              userId: rowdata.id,
          },
          success(resp) {
              if (resp.error_message === "success") {
                ElMessage.success("升级至教师成功");
                location.reload();
              } else if (resp.error_message === "error") {
                  ElMessage.error("升级失败");
              } else {
                  ElMessage.error("未知错误");
              }
          },
          error() {
              ElMessage.error("服务器错误");
          }
      })
  }

    getStudentTable();
    return {
      studentTable,
      handleEdit,
      studentProfile,
      modifydialog,
      onSubmit,
      delete_student,
      add_teacher,
    }
  }
}
</script>

<style>

</style>