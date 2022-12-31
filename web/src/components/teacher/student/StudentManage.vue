<template>
  <div class="demo-collapse">
    <el-collapse v-model="activeNames" @change="handleChange" accordion>
        <el-collapse-item v-for="i in studentList" :key="i.userId" :title="i.userName" :name="i.userId">
            <el-table
                :data="recordList"
                style="width: 100%"
                :row-class-name="tableRowClassName"
            >
                <el-table-column prop="exerciseId" label="记录序号" width="180"> </el-table-column>
                <el-table-column prop="exerciseName" label="题目名称" width="180"> </el-table-column>
                <el-table-column prop="result" label="结果"> </el-table-column>
                <el-table-column prop="recordTime" label="提交时间"> </el-table-column>
            </el-table>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
import { ref } from "vue";
import $ from "jquery"
import { useStore } from "vuex"
export default {
    setup() {
        let activeNames = ref('1');
        const store = useStore();
        let studentList = ref([]);
        const handleChange = data => {
            console.log(data);
            if (data != '') {
                getStudentRecord(data);
            }
        }
        let recordList = ref([]);

        const getStudentList = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/teacher/student/get/list/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    studentList.value = resp;
                    console.log(resp);
                },
                error() {
                    console.log("cannot request student list");
                }
            })
        };

        const getStudentRecord = userId => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/teacher/student/get/record/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    userId: userId,
                },
                success(resp) {
                    recordList.value = resp;
                    console.log(resp);
                },
                error() {
                    console.log("cannot request student list");
                }
            })
        }
        getStudentList();

        return {
            handleChange,
            activeNames,
            recordList,
            studentList,
        }
    }
}
</script>

<style>

</style>