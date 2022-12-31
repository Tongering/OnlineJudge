<template>
    <el-table :data="terminalTable" style="width: 100%">
      <el-table-column prop="userId" label="用户ID" width="180" />
      <el-table-column label="终端者" >
      <template #default="scope">
        <div style="display: flex; align-items: center">
          <el-avatar :src="scope.row.photo" :size="small"/>
          <a>{{scope.row.name}}</a>
        </div>
      </template>
    </el-table-column>
      <el-table-column prop="ip" label="Ip" />
      <el-table-column prop="port" label="端口" />
      <el-table-column prop="terminalName" label="终端用户名" />
      <el-table-column prop="terminalPassword" label="终端密码" />
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">
            Edit
          </el-button>
          <el-button size="small" type="danger" @click="delete_terminal(scope.row)">
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
    <el-form :model="terminalProfile" label-width="120px">
        <el-form-item label="ip">
          <el-input v-model="terminalProfile.ip" />
        </el-form-item>
        <el-form-item label="port">
          <el-input v-model="terminalProfile.port" />
        </el-form-item>
        <el-form-item label="terminalName">
          <el-input v-model="terminalProfile.terminalName" />
        </el-form-item>
        <el-form-item label="terminalPassword">
          <el-input v-model="terminalProfile.terminalPassword" />
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
      let terminalTable = ref([]);
      let store = useStore();
      let modifydialog = ref(false);
      let terminalProfile = reactive({
        userId: '',
        ip: '',
        port: '',
        terminalName: '',
        terminalPassword: '',
        photo: '',
        name: '',
      })
  
      const getTerminalTable = () => {
        $.ajax({
          url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/admin/terminal/get/",
          type: "post",
          headers: {
              Authorization: "Bearer " + store.state.user.token,
          },
          success(resp) {
            terminalTable.value = resp;
          },
          error(resp) {
            console.log(resp);
          }
        })
      }
      const handleEdit = (User) => {
        terminalProfile.userId = User.userId;
        terminalProfile.ip = User.ip;
        terminalProfile.port = User.port;
        terminalProfile.terminalName = User.terminalName;
        terminalProfile.terminalPassword = User.terminalPassword;
        terminalProfile.photo = User.photo;
        terminalProfile.name = User.name;
        modifydialog.value = true;
      }
      const onSubmit = () => {
        modifydialog.value = false
        $.ajax({
          url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/admin/terminal/modify/",
          type: "post",
          headers: {
              Authorization: "Bearer " + store.state.user.token,
          },
          data: {
              userId: terminalProfile.userId,
              ip: terminalProfile.ip,
              port: terminalProfile.port,
              terminalUsername: terminalProfile.terminalName,
              terminalPassword: terminalProfile.terminalPassword,
          },
          success(resp) {
            if (resp.error_message === "success") {
              ElMessage.success("修改信息成功");
              getTerminalTable();
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
            url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/admin/terminal/delete/",
            type: "post",
            headers: {
                Authorization: "Bearer " + store.state.user.token,
            },
            data: {
                terminalId: rowdata.id,
            },
            success(resp) {
                if (resp.error_message === "success") {
                  ElMessage.success("删除终端成功，请检查服务器是否删除此账号");
                  location.reload();
                } else if (resp.error_message === "error") {
                    ElMessage.error("删除终端失败");
                } else {
                    ElMessage.error("未知错误");
                }
            },
            error() {
                ElMessage.error("服务器错误");
            }
        })
        getTerminalTable();
      }
  
      const delete_terminal = rowdata => {
        ElMessageBox.confirm(
          '当前终端将被删除',
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
  
      getTerminalTable();
      return {
        terminalTable,
        handleEdit,
        terminalProfile,
        modifydialog,
        onSubmit,
        delete_terminal,
      }
    }
  }
  </script>
  
  <style>
  
  </style>