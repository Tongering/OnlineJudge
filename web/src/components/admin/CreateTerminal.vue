<template>
  <el-row justify="center">
    <el-col :span="15">
        <el-table
            ref="singleTableRef"
            :data="terminalTable"
            highlight-current-row
            style="width: 100%" height="250"
            @current-change="handleCurrentChange"
        >
            <el-table-column prop="id" label="用户ID" width="180" />
            <el-table-column prop="name" label="用户名" width="180" />
            <el-table-column label="头像" >
            <template #default="scope">
                <div style="display: flex; align-items: center">
                <el-avatar :src="scope.row.photo" :size="small"/>
                </div>
            </template>
            </el-table-column>
        </el-table>
        <el-form :model="form" label-width="120px">
            <el-form-item label="ip">
                <el-input v-model="ip" />
            </el-form-item>
            <el-form-item label="port">
                <el-input v-model="port" />
            </el-form-item>
            <el-form-item label="terminalUsername">
                <el-input v-model="terminalUsername" />
            </el-form-item>
            <el-form-item label="terminalPassword">
                <el-input v-model="terminalPassword" />
            </el-form-item>
            <el-divider />
            <el-button type="primary" @click="submit">submit</el-button>
        </el-form>
    </el-col>
  </el-row>
</template>

<script>
// import { ElTable } from 'element-plus'  
import $ from "jquery"
import { useStore } from "vuex"
import { ref } from "vue"
import { ElMessage } from 'element-plus'

export default {
    setup() {
        const store = useStore();
        let terminalTable = ref([]);
        let currentRow = ref(0);
        let port = ref("");
        let ip = ref("");
        let terminalUsername = ref("");
        let terminalPassword = ref("");
        let trueTerminal = ref([]);
        let sign = ref("none"); // none表示当前用户没有终端， true表示当前用户有终端
        const getUser = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/admin/student/get/",
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
        const handleCurrentChange = (val) => {
            currentRow.value = val.id;
            console.log(val);
        }

        const submit = () => {
            if (currentRow.value === 0) { // 未选中
                ElMessage.error("请选择开通的用户");
            } else { // 已选中
                $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/admin/terminal/get/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    trueTerminal.value = resp;
                    for (let i in trueTerminal) {
                        if (currentRow.value === i.userId) {
                            sign.value = "true";
                        }
                    }
                    addTerminal();
                },
                error(resp) {
                    console.log(resp);
                }
            })
            }
        }

        const addTerminal = () => {
            if (sign.value === "true") {
                ElMessage.error("当前用户已开通终端");
            } else {
                $.ajax({
                    url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/admin/terminal/add/",
                    type: "post",
                    headers: {
                        Authorization: "Bearer " + store.state.user.token,
                    },
                    data: {
                        userId: currentRow.value,
                        ip: ip.value,
                        port: port.value,
                        terminalUsername: terminalUsername.value,
                        terminalPassword: terminalPassword.value,
                    },
                    success(resp) {
                        if (resp.error_message === "success") {
                            ElMessage.success("当前用户成功开通");
                            currentRow.value = 0;
                            sign.value = "none";
                            ip.value = "";
                            port.value = "";
                            terminalUsername.value = "";
                            terminalPassword.value = "";
                            location.reload();
                        } else {
                            ElMessage.error("开通失败");
                        }

                    },
                    error(resp) {
                        console.log(resp);
                    }
                })
            }
        }
        getUser();
        return {
            terminalTable,
            handleCurrentChange,
            port,
            ip,
            terminalUsername,
            terminalPassword,
            submit,
        }
    }
}
</script>

<style>

</style>