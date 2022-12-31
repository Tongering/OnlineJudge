<template>
    <Navbar/>
    <el-row justify="center">
        <el-col :span="20" style="margin-top:20px; margin-bottom:10px">
            <el-card class="box-card">
                <div style="text-align:center">{{ invitationPage.title }}</div>
                <el-divider />
                <span>
                    <el-avatar shape="square" :src="invitationPage.userphoto" />
                    <a>{{ invitationPage.name }}</a>
                </span>
            </el-card>
        </el-col>
    </el-row>
    <el-row justify="center">
        <el-col :span="20" style="margin-top:10px; margin-bottom:10px">
            <el-card class="box-card">
                <md-editor v-model=" invitationPage.content " previewOnly/>
            </el-card>
        </el-col>
    </el-row>
    <el-row justify="center" style="margin-top:10px; margin-bottom:10px">
        <el-col :span="20">
            <el-card class="box-card">
                <el-input
                    v-model="comment"
                    :rows="5"
                    type="textarea"
                    placeholder="Please input your friendly comments."
                />
                <el-button
                style="float:right;"
                type='primary'
                @click="summit_comment"
                    text
                    bg
                    >comment on</el-button
                >
                <br />
                <br />

                <div v-for="i in comment_list" :key="i.id" style="margin-top:20px;margin-left:20px;margin-right:20px">
                    <span>
                        <el-avatar shape="square" size="small" :src="i.photo" />
                        <a>&nbsp;{{ i.username }}</a>
                    </span>
                    <div style="float:right;" v-if="$store.state.user.id == i.userId || $store.state.user.id == invitationPage.userId">
                        <el-button round :icon="Delete" @click="deleteComment(i.id)"></el-button>
                    </div>
                    <div><a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{i.content}}</a></div>
                    
                    <el-divider />
                </div>
            </el-card>
        </el-col>
    </el-row>
    <el-row justify="center" style="margin-top:10px; margin-bottom:10px">
        <el-col :span="20">
            <el-card class="box-card">
                <h5>猜你喜欢</h5>
                <el-row justify="center">
                    <el-col :span="20" v-for="like in likes" :key="like.id" @click="enter_invation_place(like.id)" style="margin-top:10px;margin-bottom:10px">
                        <el-card class="box-card" shadow="hover">
                            <template #header>
                                <div class="card-header">
                                    <span>
                                        <el-avatar shape="square" :src="like.photo" />
                                        <a>{{ like.name }}</a>
                                    </span>
                                    <el-button class="button" text>{{ like.type }}</el-button>
                                </div>
                            </template>
                            <div>{{ like.title }}</div>
                            <md-editor v-model=" like.content " previewOnly/>
                        </el-card>
                    </el-col>
                </el-row>
            </el-card>
        </el-col>
    </el-row>
    <ScrollTop />
</template>

<script>
import Navbar from "@/components/NavBar.vue"
import ScrollTop from "@/components/scroll/ScrollTop.vue"
import $ from "jquery";
import { ref, reactive } from "vue"
import { useStore } from "vuex";
import { useRoute } from "vue-router"
import MdEditor from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import { ElMessage } from 'element-plus'
import router from "@/router/index";
import { Delete } from '@element-plus/icons-vue'

export default {
    components: {
        Navbar,
        MdEditor,
        ScrollTop,
    }, 
    setup() {
        const route = useRoute();
        const store = useStore();
        let invitationPage = reactive({
            title: "",
            name: "",
            userphoto: "",
            userId: "",
            content: "",
        });
        let comment_list = ref([]);
        
        let cho = route.path.split('/');
        let pageId = cho[3];
        let comment = ref('');
        let likes = ref([]);

        const getinvitation = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/invitation/getpage/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    pageId: pageId,
                },
                success(resp) {
                    invitationPage.title = resp.title;
                    invitationPage.name = resp.name;
                    invitationPage.userphoto = resp.photo;
                    invitationPage.userId = resp.userId;
                    invitationPage.content = resp.content;
                },
                error() {
                    console.log("cannot request invitation");
                }
            })
        };

        const summit_comment = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/comment/add/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    invitationId: pageId,
                    content: comment.value,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        ElMessage.success("发表评论成功");
                        get_comment();
                    } else if (resp.error_message === "error") {
                        ElMessage.error("发表评论失败");
                    }
                },
                error() {
                    console.log("comment error");
                }
            })
        }

        const get_comment = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/comment/get/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    invitationId: pageId,
                },
                success(resp) {
                    console.log(resp);
                    comment_list.value = resp;
                },
                error() {
                    console.log("comment get false");
                }
            })
        };

        const getlike = () => { 
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/invitation/brief/",
                type: "get",
                data: {
                    typeId: 0,
                    isStrain: "like",
                },
                success(resp) {
                    console.log("getlike");
                    console.log(resp);
                    likes.value = resp;
                },
                error() {
                    console.log("error");
                }
            })
        }

        const deleteComment = id => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/comment/delete/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    id: id,
                    invitation_user_id: invitationPage.userId,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        ElMessage.success("删除评论成功");
                        location.reload();
                    } else {
                        ElMessage.error("删除评论失败，请检查你的权限");
                    }
                },
                error() {
                    console.log("error");
                }
            })
        }

        const enter_invation_place = invitationId => {
            router.push({ name: "invitation_page", params: { pageId: invitationId } });
        }

        getinvitation();
        get_comment();
        getlike();

        return {
            invitationPage,
            comment,
            summit_comment,
            comment_list,
            enter_invation_place,
            likes,
            Delete,
            deleteComment,
        }
    }
}
</script>

<style>

</style>