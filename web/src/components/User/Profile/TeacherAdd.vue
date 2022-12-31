<template>
    <el-input v-model="searchtext" class="w-50 m-2" placeholder="Please Input" @change="searchTeacher" :suffix-icon="Search"/>
    <ul v-infinite-scroll="load" class="infinite-list" style="overflow: auto">
    <el-row justify="center">
        <el-col :span="5" v-for="i in searchresult" :key="i.userId">
            <el-card shadow="hover" @click="addTeacher(i.userId)">
                <el-avatar :size="50" :src="i.userPhoto" />
                <a>{{i.userName}}</a>
            </el-card>
        </el-col>
    </el-row>

  </ul>
  </template>
  
<script>
import { ref } from "vue"
import { Search } from '@element-plus/icons-vue'
import $ from "jquery"
import { useStore } from "vuex"
import { ElMessage } from 'element-plus'
export default {
    setup() {
        const store = useStore();
        let searchtext = ref('');
        let searchresult = ref([]);
        const searchTeacher = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/search/teacher/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token
                },
                data: {
                    name: searchtext.value,
                },
                success(resp) {
                    searchresult.value = resp;
                    console.log(resp)
                },
                error() {
                    console.log("can't no get the search result");
                }
            });
        }
        const addTeacher = userId => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/add/teacher/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token
                },
                data: {
                    id: userId,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        ElMessage.success("添加老师成功");
                        location.reload();
                    } else if (resp.error_message === "error" ) {
                        ElMessage.error("当前老师已存在");
                    } else if (resp.error_message === "same") {
                        ElMessage.error("不可添加自己为老师");
                    }
                },
                error() {
                    console.log("can't no get the search result");
                }
            });
        }

        return {
            Search,
            searchTeacher,
            searchtext,
            searchresult,
            addTeacher,
        }
    }
}
</script>

<style>

</style>