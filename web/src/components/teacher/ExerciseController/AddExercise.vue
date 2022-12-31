<template>
    <el-row justify="center">
        <el-col :span="24">
            <el-form style="display:inline-block">
                    <el-form-item label="习题名称">
                            <el-input type="text" v-model="exercisename" autocomplete="off" placeholder="请输入习题名称"></el-input>
                    </el-form-item>
                    
                    <el-form-item label="习题类型">
                        <div>
                            <el-radio-group v-model="radio">
                                <el-radio-button  v-for="button in buttons" :key="button.id" :label=button.typename @change="getradiovalue(button.id)" ></el-radio-button>
                            </el-radio-group>
                        </div>
                    </el-form-item>

                    <el-form-item label="时间控制(s)">
                            <el-input type="number" v-model="time" autocomplete="off" placeholder="请输入时间"></el-input>
                    </el-form-item>

                    <el-form-item label="空间控制(MB)">
                            <el-input type="number" v-model="space" autocomplete="off" placeholder="请输入空间"></el-input>
                    </el-form-item>

                    <el-form-item>
                        <el-input type="textarea" :rows="5" placeholder="请输入内容" v-model="textarea"></el-input>
                    </el-form-item>
                    
                    <el-form-item>
                        <el-input type="textarea" :rows="5" placeholder="请输入模板" v-model="template"></el-input>
                    </el-form-item>
                    
                    <el-form-item >
                        <el-button type="primary" @click="summitexercise" style="margin: 0 auto;width: 100%" size="large" round>提交</el-button>
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

    data() {
        return {
            radio: "初级",
        }
    },
    
    setup() {
        const store = useStore();
        let buttons = ref([]);
        let exercisename = ref('');
        let radiovalue = 1;
        let textarea = ref('');
        let template = ref('');
        let space = ref('');
        let time = ref('');
        
        const gettype = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/exercises/type/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token
                },
                success(resp) {
                    buttons.value = resp;
                },
                error() {
                    console.log("can't no get the exercises type");
                }
            });
        };
        const getradiovalue = radioId => {
            radiovalue = radioId;
        }
        const summitexercise = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/exercise/add/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token
                },
                data: {
                    name: exercisename.value,
                    type_id: radiovalue,
                    time: time.value,
                    space: space.value,
                    content: textarea.value,
                    template: template.value,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        ElMessage.success("创建习题成功");
                        location.reload();
                        exercisename.value = '';
                        radiovalue = 1;
                        time.value = '';
                        space.value = '';
                        textarea.value = '';
                        template.value = '';
                    }
                }
            })
        };

        gettype();
        
        return {
            buttons,
            getradiovalue,
            summitexercise,
            exercisename,
            textarea,
            space,
            time,
            template,
        }
    }
}
</script>

<style>

</style>