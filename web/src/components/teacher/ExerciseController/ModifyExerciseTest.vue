<template>
    <el-table :data="testlist" stripe style="width: 100%">
        <el-table-column prop="id" label="ID" width="100"> </el-table-column>
        <el-table-column prop="exerciseId" label="习题ID" width="180"> </el-table-column>
        <el-table-column prop="input" label="输入样例" width="100"> </el-table-column>
        <el-table-column prop="output" label="输出样例" width="100"> </el-table-column>
        <el-table-column prop="createId" label="创建者名称" width="100"> </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180"> </el-table-column>
        <el-table-column fixed="right" label="操作" width="150">
            <template #default="scope">
                <el-button type="text" size="small" @click="showtable = true;modifytest(scope.row)">编辑</el-button>
                <el-button type="text" size="small" @click="deletetest(scope.row)">删除</el-button>
            </template>
        </el-table-column>
    </el-table>
    <el-dialog title="修改习题" v-model="showtable" width="30%" :before-close="handleClose">
        <el-form style="display:inline-block">
            <el-form-item label="对应习题ID">
                    <el-input type="text" v-model="exercisetest.exerciseId" autocomplete="off" disabled></el-input>
            </el-form-item>
            <el-form-item label="输入样例">
                <el-input type="textarea" :rows="5" v-model="exercisetest.input"></el-input>  
            </el-form-item>
            <el-form-item label="输出样例">
                <el-input type="textarea" :rows="5" v-model="exercisetest.output"></el-input>  
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="showtable = false">取 消</el-button>
                <el-button type="primary" @click="showtable = false;updatetest()">确 定</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script>
import $ from "jquery"
import { useStore } from "vuex"
import { ref, reactive } from "vue"
import { ElMessage } from 'element-plus'
export default {
    data() {
        return {
            showtable: false,
        }
    },
    setup() {
        const store = useStore();
        let testlist = ref([]);
        let exercisetest = reactive({
            id: '',
            exerciseId: '',
            input: '',
            output: '',
            createTime: '',
            createId: '',
        });

        const modifytest = rowdata => {
            console.log(rowdata.id);
            for (const testtemp of testlist.value) {
                if (rowdata.id === testtemp.id) {
                    exercisetest.id = testtemp.id;
                    exercisetest.exerciseId = testtemp.exerciseId;
                    exercisetest.input = testtemp.input;
                    exercisetest.output = testtemp.output;
                    exercisetest.createId = testtemp.createId;
                    exercisetest.createTime = testtemp.createTime;
                    console.log("done");
                }
            }
        }

        const gettest = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/exercise/test/get/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    testlist.value = resp;
                },
                error(resp) {
                    console.log(resp);
                }
            })
        };

        const updatetest = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/exercise/test/modify/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    id: exercisetest.id,
                    input: exercisetest.input,
                    output: exercisetest.output,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        ElMessage.success("修改习题样例成功");
                        location.reload();
                    } else {
                        console.log(resp);
                    }
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        const deletetest = rowdata => { 
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/exercise/test/delete/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    id: rowdata.id
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        ElMessage.success("删除习题样例成功");
                        location.reload();
                    } else {
                        ElMessage.error("删除习题样例失败，请检查权限");
                        console.log(resp);
                    }
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }


        gettest();

        return {
            testlist,
            modifytest,
            exercisetest,
            updatetest,
            deletetest,
        }
    }
}
</script>

<style>

</style>