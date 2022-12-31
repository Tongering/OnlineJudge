<template>
  <el-row justify="center">
        <el-col :span="24">
            <el-table
                :data="user_invitation"
                style="width: 100%"
                :row-class-name="tableRowClassName"
            >
                <el-table-column prop="id" label="帖子ID" width="180"> </el-table-column>
                <el-table-column prop="title" label="标题" width="180"> </el-table-column>
                <el-table-column prop="typeName" label="所属类型"> </el-table-column>
                <el-table-column prop="createTime" label="发帖时间"> </el-table-column>
                <el-table-column fixed="right" label="编辑" width="100">
                    <template #default="scope">
                        <el-button type="primary" size="small" :icon="Edit" circle  @click="modifydialog = true;modify_invitation(scope.row)"/>
                    </template>
                </el-table-column>
                <el-table-column fixed="right" label="删除" width="100">
                    <template #default="scope">
                        <el-button type="danger" size="small" :icon="Delete" circle @click="delete_invitation(scope.row)"/>
                    </template>
                </el-table-column>
                <el-table-column fixed="right" label="查看" width="100">
                    <template #default="scope">
                        <el-button size="small" :icon="Search" circle @click="click_invitation(scope.row)"/>
                    </template>
                </el-table-column>
            </el-table>
        </el-col>
        <el-dialog
            title="Terminal"
            v-model="modifydialog"
            width="70%"
            :before-close="handleClose"
        >  
            <el-form :model="form" label-width="120px">
                <el-form-item label="title">
                    <el-input v-model="invitation.title" />
                </el-form-item>
                <el-form-item label="type">
                    <el-select v-model="invitation.type" placeholder="please select your invitation type">
                        <el-option v-for="i in list" :key="i.id" :label="i.typename" :value="i.id" />
                    </el-select>
                </el-form-item>
            </el-form>
            <el-divider />
            <md-editor 
                toolbarsExclude="['link', 'mermaid', 'katex', 'github']"
                @onSave="onSave"
                @on-upload-img="onUploadImg"
                v-model="invitation.content"
            />
            <el-button type="primary" @click="submit">submit</el-button>
        </el-dialog>
    </el-row>
</template>

<script>
import { ref, reactive } from "vue";
import { useStore } from "vuex";
import MdEditor from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import $ from "jquery";
import { ElMessage, ElMessageBox } from 'element-plus'
import { Delete, Edit, Search } from '@element-plus/icons-vue'
import router from '@/router/index'
export default {
    components: {
        MdEditor,
    },
    setup() {
        const store = useStore();
        let user_invitation = ref([]);
        let modifydialog = ref(false);
        let modify_type = ref('');
        let list = ref([]);
        let invitation = reactive({
            id: "",
            title: "",
            type: 0,
            content: "",
        })

        const getInvitation = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/invitation/type/get/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token
                },
                success(resp) {
                    user_invitation.value = resp;
                },
                error() {
                    console.log("connot get the invitation by yourself");
                }
            })
        }

        const modify_invitation = rowdata => {
            for (const invitationtemp of user_invitation.value) {
                if (rowdata.id === invitationtemp.id) {
                    invitation.id = invitationtemp.id;
                    invitation.title = invitationtemp.title;
                    invitation.type = invitationtemp.typeName;
                    invitation.content = invitationtemp.content;
                }
            }

            modifydialog.value = true;
        }

        const gettype = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/invitation/type/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    list.value = resp;
                    console.log(list.value);
                },
                error() {
                    console.log("can't no get the exercises type");
                }
            });
        };

        const submit = () => {
            let typeId
            for (const i of list.value) {
                if (i.typename === invitation.type) {
                    typeId = i.id;
                    break;
                }
            }
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/invitation/update/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    invitationId: invitation.id,
                    invitationTitle: invitation.title,
                    invitationType: typeId,
                    invitationContent: invitation.content,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        ElMessage.success("修改帖子成功");
                        location.reload();
                    } else if (resp.error_message === "error") {
                        ElMessage.error("修改帖子失败");
                    } else {
                        ElMessage.error("未知错误");
                    }
                },
                error() {
                    ElMessage.error("服务器错误");
                }
            });
            getInvitation();
        }

        const delete_comp = rowdata => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/invitation/delete/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    invitationId: rowdata.id,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        ElMessage.success("删除帖子成功");
                        location.reload();
                    } else if (resp.error_message === "error") {
                        ElMessage.error("删除帖子失败，您不是帖主");
                    } else {
                        ElMessage.error("未知错误");
                    }
                },
                error() {
                    ElMessage.error("服务器错误");
                }
            })
            getInvitation();
        }

        const delete_invitation = rowdata => {
            ElMessageBox.confirm(
                '当前帖子将被删除',
                'Warning',
                {
                confirmButtonText: 'OK',
                cancelButtonText: 'Cancel',
                type: 'warning',
                }
            )
                .then(() => {
                    delete_comp(rowdata);
            })
            .catch(() => {
                ElMessage({
                    type: 'info',
                    message: 'Delete canceled',
                })
            })
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

        const click_invitation = rowdata => {
            router.push({ name: 'invitation_page', params: { pageId: rowdata.id }});
        }
        
        gettype();
        getInvitation()

        return {
            user_invitation,
            Delete,
            Edit,
            Search,
            modify_invitation,
            modifydialog,
            invitation,
            modify_type,
            list,
            submit,
            delete_invitation,
            click_invitation,
            onUploadImg,
        }
    }
}
</script>

<style>

</style>