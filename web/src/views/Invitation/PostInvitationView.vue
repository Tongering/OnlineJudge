<template>
    <Navbar/>
    <el-row justify="center">
        <el-col :span="20">
            <el-card class="box-card">
                <el-form :model="form" label-width="120px">
                    <el-form-item label="title">
                        <el-input v-model="title" />
                    </el-form-item>
                    <el-form-item label="type">
                        <el-select v-model="type" placeholder="please select your invitation type">
                            <el-option v-for="i in list" :key="i.id" :label="i.typename" :value="i.id" />
                        </el-select>
                    </el-form-item>
                </el-form>
                <el-divider />
                <md-editor 
                toolbarsExclude="['link', 'mermaid', 'katex', 'github']"
                @onSave="onSave"
                @on-upload-img="onUploadImg"
                v-model="invitation_content"/>
                <el-button type="primary" @click="summit">summit</el-button>
            </el-card>
        </el-col>
    </el-row>

</template>

<script>
import Navbar from "@/components/NavBar.vue"
import MdEditor from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import { ref } from "vue";
import { useStore } from "vuex"
import $ from "jquery";
import { ElMessage } from 'element-plus'
export default {
    components: {
        Navbar,
        MdEditor,
    },

    setup() {
        let invitation_content = ref('');
        const store = useStore();
        let list = ref([]);
        let title = ref('');
        let type = ref();
        const onSave = (v) => {
            console.log(v);
        }
        const onUploadImg = async (files, callback) => {
            const res = await Promise.all(
                files.map((file) => {
                    return new Promise((rev, rej) => {
                        const form = new FormData();
                        form.append('file', file);
                        $.ajax({
                            url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/image/person/invitation/upload/",
                            type: 'post',
                            headers: {
                                Authorization: "Bearer " + store.state.user.token,
                            },
                            data: form,
                            contentType : false,
                            processData : false,
                            success(res) {
                                console.log("success");
                                console.log(res);
                                rev(res);
                            },
                            error(error) {
                                console.log("error");
                                console.log(error);
                                rej(error);
                            }
                        })
                    });
                })
            );
            callback(res.map((item) => item));
        };

        const gettype = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/invitation/type/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    list.value = resp;
                },
                error() {
                    console.log("can't no get the exercises type");
                }
            });
        };
        gettype();

        const summit = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/invitation/add/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    title: title.value,
                    typeId: type.value,
                    content: invitation_content.value,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        ElMessage.success("发帖成功");
                        location.reload();
                    } else if (resp.error_message === "error") {
                        ElMessage.error("发帖失败");
                    } else {
                        console.log("can't no get the summit invitation");
                    }
                },
                error() {
                    console.log("can't no get the summit invitation");
                }
            });
        }

        return {
            invitation_content,
            onSave,
            onUploadImg,
            list,
            summit,
            title,
            type,
        }
    }
}
</script>

<style>

</style>