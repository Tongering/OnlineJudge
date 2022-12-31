<template>
  <el-table :data="adminTable" style="width: 100%">
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
        <el-button size="small" type="info" @click="drop_admin(scope.row)">
          down
        </el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import $ from "jquery"
import { useStore } from "vuex"
import { ref } from "vue"
import { ElMessage } from 'element-plus'
export default {
  setup() {
    let store = useStore();
        let adminTable = ref([]);
        const getStudentTable = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/admin/admin/get/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    adminTable.value = resp;
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        const drop_admin = rowdata => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/admin/admin/delete/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    userId: rowdata.id,
                },
              success(resp) {
                console.log(resp);
                    if (resp.error_message === "success") {
                      ElMessage.success("删除管理员成功");
                      location.reload();
                    } else {
                        ElMessage.error("删除管理员失败");
                    }
                    
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }
        getStudentTable();
        return {
            adminTable,
            drop_admin,
        }
  }
}
</script>

<style>

</style>