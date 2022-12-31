<template>
  <el-table :data="typelist" stripe style="width: 100%">
        <el-table-column prop="id" label="ID" width="100"> </el-table-column>
        <el-table-column prop="typename" label="类型名称" width="180"> </el-table-column>
        <el-table-column label="头像" >
            <template #default="scope">
                <div style="display: flex; align-items: center">
                    <el-avatar :src="scope.row.photo" :size="small"/>
                </div>
            </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="150">
            <template #default="scope">
                <el-button type="text" size="small" @click="dialogVisible = true;modifytype(scope.row)">编辑</el-button>
                <el-button type="text" size="small" @click="deletetype(scope.row)">删除</el-button>
            </template>
        </el-table-column>
    </el-table>
    <el-dialog title="修改类型" v-model="dialogVisible" width="30%" :before-close="handleClose">
        <el-form style="display:inline-block">
            <el-form-item label="类型名称">
                    <el-input type="text" v-model="update_name" autocomplete="off" placeholder="请输入类型名称"></el-input>
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="dialogVisible = false;updatetype()">确 定</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script>
import $ from "jquery"
import { ref } from "vue"
import { useStore } from "vuex"
import { ElMessage } from 'element-plus'
export default {
    data() {
        return {
            dialogVisible: false,
        }
    },
    setup() {
        const store = useStore();
        let typelist = ref([]);
        let update_name = ref('');
        let update_id = ref('');

        const gettype = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/teacher/invitation/type/get/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    typelist.value = resp;
                },
                error(resp) {
                    ElMessage.error("未能获取帖子类型");
                    console.log(resp);
                }
            })
        };

        const modifytype = rowdata => {
            update_name.value = rowdata.typename;
            update_id.value = rowdata.id;
        };

        const updatetype = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/teacher/invitation/type/modify/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    id: update_id.value,
                    typename: update_name.value,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        ElMessage.success("修改类型名称成功");
                        location.reload();
                        update_id.value = '';
                        update_name.value = '';
                    } else {
                        console.log(resp);
                    }
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        const deletetype = rowdata => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/teacher/invitation/type/delete/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    id: rowdata.id,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        ElMessage.success("删除类型成功");
                        location.reload();
                    } else {
                        ElMessage.error("删除类型失败，请检查你是否具有权限");
                        console.log(resp);
                    }
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        gettype();

        return {
            typelist,
            update_name,
            modifytype,
            updatetype,
            deletetype,
        }
    }
}
</script>

<style>

</style>