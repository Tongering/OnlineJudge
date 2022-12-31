<template>
  <el-table :data="commentTable" style="width: 100%">
    <el-table-column prop="id" label="评论ID" width="180" />
    <el-table-column label="评论者" >
      <template #default="scope">
        <div style="display: flex; align-items: center">
          <el-avatar :src="scope.row.userPhoto" :size="small"/>
          <a>{{scope.row.userName}}</a>
        </div>
      </template>
    </el-table-column>
    <el-table-column prop="invitationId" label="帖子ID" />
    <el-table-column prop="invitationName" label="帖子名称" />
    <el-table-column prop="content" label="内容" />
    <el-table-column prop="createTime" label="创建时间" />
    <el-table-column label="操作">
      <template #default="scope">
        <el-button size="small" type="danger" @click="delete_comment(scope.row)">
          Delete
        </el-button>
      </template>
    </el-table-column>
  </el-table>
  
</template>

<script>
import $ from "jquery"
import { useStore } from "vuex"
import { ref } from "vue"
import { ElMessage, ElMessageBox } from 'element-plus'

export default {
  setup() {
    let commentTable = ref([]);
    let store = useStore();

    const getCommentTable = () => {
      $.ajax({
        url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/admin/comment/get/",
        type: "post",
        headers: {
            Authorization: "Bearer " + store.state.user.token,
        },
        success(resp) {
          commentTable.value = resp;
        },
        error(resp) {
          console.log(resp);
        }
      })
    }
    
    const delete_t = rowdata => {
      $.ajax({
          url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/admin/comment/delete/",
          type: "post",
          headers: {
              Authorization: "Bearer " + store.state.user.token,
          },
          data: {
              commentId: rowdata.id,
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
      getCommentTable();
    }

    const delete_comment = rowdata => {
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

    getCommentTable();
    return {
      commentTable,
      delete_comment,
    }
  }
}
</script>

<style>

</style>