<template>
    <div>
        <el-radio v-for="radio in list" :key="radio.id" v-model="radio1" :label="radio.id" @change="getradio(radio.id)" border>{{ radio.typename }}</el-radio>
    </div>
    <el-table :data="exerciselist" stripe style="width: 100%">
        <el-table-column prop="id" label="ID" width="100"> </el-table-column>
        <el-table-column prop="name" label="标题" width="180"> </el-table-column>
        <el-table-column prop="createId" label="创建者名称" width="100"> </el-table-column>
        <el-table-column prop="typeId" label="类型" width="100"> </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180"> </el-table-column>
        <el-table-column prop="space" label="空间限制" width="100"> </el-table-column>
        <el-table-column prop="time" label="时间限制" width="100"> </el-table-column>
        <el-table-column fixed="right" label="操作" width="150">
            <template #default="scope">
                <el-button type="text" size="small" @click="dialogVisible = true;modifyexercise(scope.row)">编辑</el-button>
                <el-button type="text" size="small" @click="deleteexercise(scope.row)">删除</el-button>
            </template>
        </el-table-column>
    </el-table>
    <el-dialog title="修改习题" v-model="dialogVisible" width="30%" :before-close="handleClose">
        <el-form style="display:inline-block">
            <el-form-item label="习题名称">
                    <el-input type="text" v-model="exercise.name" autocomplete="off" placeholder="请输入习题名称"></el-input>
            </el-form-item>
            <el-form-item label="习题类型">
                <div>
                    <el-radio-group v-model="radio">
                        <el-radio-button  v-for="button in list" :key="button.id" :label=button.typename @change="getradiovalue(button.id)" ></el-radio-button>
                    </el-radio-group>
                </div>
            </el-form-item>
            <el-form-item label="时间控制(s)">
                    <el-input type="number" v-model="exercise.time" autocomplete="off" placeholder="请输入时间"></el-input>
            </el-form-item>
            <el-form-item label="空间控制(MB)">
                    <el-input type="number" v-model="exercise.space" autocomplete="off" placeholder="请输入空间"></el-input>
            </el-form-item>
            <el-form-item label="内容">
                <el-input type="textarea" :rows="5" placeholder="请输入内容" v-model="exercise.content"></el-input>
            </el-form-item>
            <el-form-item label="模板">
                <el-input type="textarea" :rows="5" placeholder="请输入模板" v-model="exercise.template"></el-input>
            </el-form-item>
            
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="dialogVisible = false;updateexercise()">确 定</el-button>
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
            dialogVisible: false,
            radio1: "0",
        }
    },
    setup() {
        const store = useStore();
        let type_id = 1;
        let exerciselist = ref([]);
        let list = ref([]);
        let radio = ref('');
        let exercisename = ref('');
        let time = ref();
        let space = ref();
        let textarea = ref('');
        let radiovalue = 1;
        let exercise = reactive({
            id: "",
            name: "",
            content: "",
            createId: "",
            typeId: "",
            createTime: "",
            space: "",
            time: "",
            template: "",
        })

        const getradiovalue = radioId => {
            radiovalue = radioId;
        }
        

        const getradio = radio_id => {
            type_id = radio_id
            console.log(type_id);
            getexericse(type_id);
        }

        const modifyexercise = rowdata => {
            console.log(rowdata.id);
            for (const exercisetemp of exerciselist.value) {
                if (rowdata.id === exercisetemp.id) {
                    exercise.id = exercisetemp.id;
                    exercise.name = exercisetemp.name;
                    exercise.content = exercisetemp.content;
                    exercise.createId = exercisetemp.createId;
                    exercise.typeId = exercisetemp.typeId;
                    exercise.createTime = exercisetemp.createTime;
                    exercise.space = exercisetemp.space;
                    exercise.time = exercisetemp.time;
                    exercise.template = exercisetemp.template;
                    console.log("done");
                    console.log(exercise.id);
                }
            }
            radio.value = 1;
        }

        const updateexercise = () => {
            console.log(exercise.template);
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/exercise/modify/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    id: exercise.id,
                    name: exercise.name,
                    content: exercise.content,
                    typeId: radiovalue,
                    space: exercise.space,
                    time: exercise.time,
                    template: exercise.template,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        ElMessage.success("修改习题成功");
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

        const gettype = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/exercises/type/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token
                },
                success(resp) {
                    list.value = resp;
                },
                error() {
                    console.log("can't no get the exercises type");
                }
            });
        };

        const getexericse = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/exercise/get/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    type_id: type_id,
                    onlyPersonal: "yes",
                },
                success(resp) {
                    exerciselist.value = resp;
                    console.log(exerciselist.value);
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        const deleteexercise = rowdata => { 
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/teacher/exercise/delete/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    id: rowdata.id,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        ElMessage.success("删除习题成功");
                        location.reload();
                    } else { 
                        ElMessage.error("删除习题失败，请检查权限");
                    }
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        gettype();

        return {
            getradio,
            exerciselist,
            list,
            modifyexercise,
            radio,
            exercisename,
            time,
            space,
            textarea,
            exercise,
            getradiovalue,
            updateexercise,
            deleteexercise,
        }
    }

}
</script>

<style scoped>

</style>