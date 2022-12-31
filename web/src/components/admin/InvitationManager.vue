<template>
  <el-table :data="invitationTable" style="width: 100%">
    <el-table-column prop="id" label="帖子ID" width="180" />
    <el-table-column prop="type" label="类型" />
    <el-table-column prop="title" label="帖子主题" width="180" />
    
    <!-- <el-table-column prop="content" label="内容" /> -->
    <el-table-column label="发帖者" >
      <template #default="scope">
        <div style="display: flex; align-items: center">
          <el-avatar :src="scope.row.photo" :size="small"/>
          <a>{{scope.row.name}}</a>
        </div>
      </template>
    </el-table-column>
    <el-table-column prop="createTime" label="发帖时间" />
    
    <el-table-column label="操作">
      <template #default="scope">
        <el-button size="small" @click="handleEdit(scope.row)">
          Edit
        </el-button>
        <el-button size="small" type="danger" @click="delete_invitation(scope.row)">
          Delete
        </el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-dialog
    title="Edit Invitation"
    v-model="modifydialog"
    width="70%"
    :before-close="handleClose"
>  
    <el-form :model="form" label-width="120px">
      <el-form-item label="title">
        <el-input v-model="invitation.title" />
      </el-form-item>
      <el-form-item label="type">
        <el-select v-model="invitation.type" placeholder="please select your invitation type">
          <el-option v-for="i in list" :key="i.id" :label="i.typename" :value="i.id" />
        </el-select>
      </el-form-item>
    </el-form>
    <el-divider />
    <md-editor 
        toolbarsExclude="['link', 'mermaid', 'katex', 'github']"
        @onSave="onSave"
        @on-upload-img="onUploadImg"
        v-model="invitation.content"
    />
    <el-button type="primary" @click="onSubmit">summit</el-button>
  </el-dialog>
</template>

<script>
import $ from "jquery"
import { useStore } from "vuex"
import { reactive, ref } from "vue"
import { ElMessage, ElMessageBox } from 'element-plus'
import MdEditor from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';

export default {
  components: {
    MdEditor,
  },
  setup() {
    let invitationTable = ref([]);
    let store = useStore();
    let modifydialog = ref(false);
    let list = ref([]);
    let invitation = reactive({
      id: "",
      title: "",
      type: 0,
      content: "",
    })

    const gettype = () => {
      $.ajax({
          url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/invitation/type/",
        type: "get",
        headers: {
            Authorization: "Bearer " + store.state.user.token,
        },
          success(resp) {
              list.value = resp;
              console.log(list.value);
          },
          error() {
              console.log("can't no get the exercises type");
          }
      });
    };

    const getinvitationTable = () => {
      $.ajax({
        url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/admin/invitation/get/",
        type: "post",
        headers: {
            Authorization: "Bearer " + store.state.user.token,
        },
        data: {
          typeId: 0,
          isStrain: "none",
        },
        success(resp) {
          invitationTable.value = resp;
        },
        error(resp) {
          console.log(resp);
        }
      })
    }
    const handleEdit = (User) => {
      invitation.id = User.id;
      invitation.title = User.title;
      invitation.type = User.type;
      invitation.content = User.content;
      modifydialog.value = true;
    }
    const onSubmit = () => {
      modifydialog.value = false
      let typeId
        for (const i of list.value) {
            if (i.typename === invitation.type) {
                typeId = i.id;
                break;
            }
        }
      $.ajax({
        url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/admin/invitation/modify/",
        type: "post",
        headers: {
            Authorization: "Bearer " + store.state.user.token,
        },
        data: {
          invitationId: invitation.id,
          invitationTitle: invitation.title,
          invitationType: typeId,
          invitationContent: invitation.content,
        },
        success(resp) {
          if (resp.error_message === "success") {
            ElMessage.success("修改帖子成功");
            location.reload();
            getinvitationTable();
          } else if (resp.error_message === "error") {
            ElMessage.error("修改帖子失败");
          }
        },
        error(resp) {
          console.log(resp);
        }
      })
    }

    const delete_i = rowdata => {
      $.ajax({
          url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/admin/invitation/delete/",
          type: "post",
          headers: {
              Authorization: "Bearer " + store.state.user.token,
          },
          data: {
              invitationId: rowdata.id,
          },
          success(resp) {
              if (resp.error_message === "success") {
                ElMessage.success("删除帖子成功");
                location.reload();
              } else if (resp.error_message === "error") {
                  ElMessage.error("删除帖子失败");
              } else {
                  ElMessage.error("未知错误");
              }
          },
          error() {
              ElMessage.error("服务器错误");
          }
      })
      getinvitationTable();
    }

    const delete_invitation = rowdata => {
      ElMessageBox.confirm(
        '当前帖子将被删除',
        'Warning',
        {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning',
        }
      )
        .then(() => {
          delete_i(rowdata);
      })
      .catch(() => {
        ElMessage({
            type: 'info',
            message: 'Delete canceled',
        })
      })
    }

    const onUploadImg = async (files, callback) => {
      const res = await Promise.all(
        files.map((file) => {
          return new Promise((rev, rej) => {
            const form = new FormData();
            form.append('file', file);
            $.ajax({
              url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/image/person/invitation/upload/",
              type: 'post',
              headers: {
                  Authorization: "Bearer " + store.state.user.token,
              },
              data: form,
              contentType : false,
              processData : false,
              success(res) {
                  console.log("success");
                  console.log(res);
                  rev(res);
              },
              error(error) {
                  console.log("error");
                  console.log(error);
                  rej(error);
              }
            })
          });
        })
      );
      callback(res.map((item) => item));
    };

    getinvitationTable();
    gettype();
    return {
      invitationTable,
      handleEdit,
      invitation,
      modifydialog,
      onSubmit,
      delete_invitation,
      onUploadImg,
      list,
    }
  }
}
</script>

<style>

</style>