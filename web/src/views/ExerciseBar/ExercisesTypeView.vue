<template>
    <NavBarVue/>
        <el-row style="display:flex;justify-content:center;margin-top: 20px;">
            <el-col :span="12">
                <el-card class="box-card" style="text-align:center">
                    <div>{{title}}</div>
                </el-card>
            </el-col>
        </el-row>
        <el-row style="display:flex;justify-content:center;margin-top: 20px;">
            <el-col :span="18">
                <el-card class="box-card">
                    <el-table :data="exerciselist" style="width: 100%">
                        <el-table-column prop="id" label="ID" width="100"> </el-table-column>
                        <el-table-column prop="name" label="标题" width="300"> </el-table-column>
                        <el-table-column prop="space" label="空间限制"> </el-table-column>
                        <el-table-column prop="time" label="时间限制"> </el-table-column>
                        <el-table-column fixed="right" label="操作" width="100">
                            <template #default="scope">
                                <el-button type="text" size="small" @click="modifyexercise(scope.row)">进入</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </el-col>
        </el-row>
</template>

<script>
import $ from "jquery";
import { ref } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
import router from "@/router/index/"
import NavBarVue from '@/components/NavBar.vue';

export default {
    components: {
        NavBarVue,
    },
    setup() {
        const route = useRoute();
        const store = useStore();
        let cho = route.path.split('/');
        let type_id = cho[3];
        let exerciselist = ref([]);
        let title = ref('');

        const getexericse = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/exercise/title/get/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    typeId: type_id,
                },
                success(resp) {
                    title.value = resp.typename;
                },
                error(resp) {
                    console.log(resp);
                }
            })

            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/exercise/get/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    type_id: type_id,
                },
                success(resp) {
                    exerciselist.value = resp;
                },
                error(resp) {
                    console.log(resp);
                }
            })
            console.log(exerciselist.value)
            console.log(type_id);
        };

        const modifyexercise = rowdata => {
            console.log(rowdata.id);
            router.push({ name: "exercise_page", params: { pageId: rowdata.id}})
        };

        getexericse();

        return {
            exerciselist,
            modifyexercise,
            title,
        }
    }
}
</script>

<style>

</style>