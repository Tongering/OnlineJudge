<template>
  <el-table :data="barTable" style="width: 100%">
    <el-table-column prop="id" label="ID" width="180" />
    <el-table-column label="横幅" >
      <template #default="scope">
        <div style="display: flex; align-items: center">
          <el-avatar :src="scope.row.photo" :size="large"/>
        </div>
      </template>
    </el-table-column>
    <el-table-column label="操作" width="300">
      <template #default="scope">
        <el-button size="small" type="danger" @click="delete_bar(scope.row)">
          delete
        </el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-divider />
  <el-dialog
    title="Add Bar"
    v-model="modifydialog"
    width="70%"
    :before-close="handleClose"
>  
  <el-form :model="studentProfile" label-width="120px">
      <el-form-item label="id">
        <el-input v-model="id" />
      </el-form-item>
      <el-form-item label="url">
        <el-input v-model="url" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="add_bar">Submit</el-button>
        <el-button @click="modifydialog = false">Cancel</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
  <el-button size="small" type="danger" @click="(modifydialog = true)">
          add
    </el-button>
</template>

<script>
import $ from "jquery"
import { useStore } from "vuex"
import { ref } from "vue"
import { ElMessage } from 'element-plus'
export default {
    setup() {
        let store = useStore();
        let barTable = ref([]);
        let id = ref('');
        let url = ref('');
        let modifydialog = ref(false);

        const getStudentTable = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/admin/bar/get/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    barTable.value = resp;
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }
        const delete_bar = rowdata => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/admin/bar/delete/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    id: rowdata.id,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        ElMessage.success("删除封面成功");
                        location.reload();
                    } else {
                        ElMessage.error("删除封面失败");
                    }
                    
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }
        const add_bar = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/admin/bar/add/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    id: id.value,
                    url: url.value,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        ElMessage.success("添加封面成功");
                        modifydialog.value = false;
                        location.reload();
                    } else {
                        ElMessage.error("添加封面失败");
                    }
                    
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }
        getStudentTable();
        return {
            barTable,
            delete_bar,
            add_bar,
            id,
            url,
            modifydialog,
        }
    }
}
</script>

<style>

</style>