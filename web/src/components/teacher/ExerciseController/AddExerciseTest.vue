<template>
    <el-row style="display:flex;justify-content:center;">
        <el-col :span="24">
            <el-form style="display:inline-block" :key="keyNum">
                <el-form-item label="对应习题ID">
                        <el-input type="text" v-model="exerciseId" autocomplete="off" placeholder="请输入习题ID">{{exerciseId}}</el-input>
                </el-form-item>
                <el-form-item label="输入样例">
                    <el-input type="textarea" :rows="5" placeholder="请输入样例" v-model="inputtext">{{inputtext}}</el-input>
                </el-form-item>
                <el-form-item label="输出样例">
                    <el-input type="textarea" :rows="5" placeholder="请输入样例" v-model="outputtext">{{outputtext}}</el-input>
                </el-form-item>
                <el-form-item >
                    <el-button type="primary" @click="summitexercisetest" style="margin: 0 auto;width: 100%" size="large" round>提交</el-button>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>
</template>

<script>
import $ from "jquery";
import { useStore } from "vuex";
import { ref } from "vue";
import { ElMessage } from 'element-plus'
export default {
    setup() {
        const store = useStore();
        let exerciseId = ref('');
        let inputtext = ref('');
        let outputtext = ref('');
        const summitexercisetest = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/exercise/test/add/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token
                },
                data: {
                    exerciseId: exerciseId.value,
                    input: inputtext.value,
                    output: outputtext.value,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        ElMessage.success("创建测试样例成功");
                        exerciseId.value = '';
                        inputtext.value = '';
                        outputtext.value = '';
                        location.reload();
                    }
                }
            })
        }


        return {
            exerciseId,
            inputtext,
            outputtext,
            summitexercisetest,
        }
    }

}
</script>

<style scoped>

</style>