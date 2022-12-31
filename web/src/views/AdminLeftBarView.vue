<template>
    <div class="common-layout">
        <el-container >
            <el-aside width="200px">
                <el-menu :default-active="currentcho" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" router>
                    <el-menu-item index="1" style="display:inline-block">
                        <i class="el-icon-location">
                            <el-avatar shape="square" :src=$store.state.user.photo />
                        </i>
                        <template #title>{{ $store.state.user.username }}</template>
                    </el-menu-item>
                    <el-divider />
                    <el-menu-item :index=adminhomepath>
                        <i class="el-icon-menu"></i>
                        <template #title>总览</template>
                    </el-menu-item>
                    
                    <el-menu-item :index=teacherpath >
                        <i class="el-icon-document"></i>
                        <template #title>教师管理</template>
                    </el-menu-item>
                    <el-menu-item :index=studentpath >
                        <i class="el-icon-document"></i>
                        <template #title>学生管理</template>
                    </el-menu-item>
                    <el-menu-item :index=invitationpath >
                        <i class="el-icon-document"></i>
                        <template #title>帖子管理</template>
                    </el-menu-item>
                    <el-menu-item :index=commentpath >
                        <i class="el-icon-document"></i>
                        <template #title>评论管理</template>
                    </el-menu-item>
                    <el-menu-item :index=terminalpath >
                        <i class="el-icon-document"></i>
                        <template #title>终端配置</template>
                    </el-menu-item>
                    <el-menu-item :index=adminsetting >
                        <i class="el-icon-document"></i>
                        <template #title>管理员设置</template>
                    </el-menu-item>
                    <el-menu-item index="/">
                        <i class="el-icon-setting"></i>
                        <template #title><router-link  :to="{ name: 'home'}">回到首页</router-link></template>
                    </el-menu-item>
                </el-menu>
            </el-aside>
            <el-main>
                <div v-if="isadminhome">
                    <AdminAboutVue/>
                </div>
                <div v-if="isteacher">
                    <TeacherManagerVue />
                </div>
                <div v-if="isstudent">
                    <StudentManageVue />
                </div>
                <div v-if="isinvitation">
                    <InvitationManagerVue />
                </div>
                <div v-if="iscomment">
                    <CommentManagerVue />
                </div>
                <div v-if="isterminal">
                    <TerminalSettingVue />
                </div>
                <div v-if="isadminsetting">
                    <AdminBar />
                </div>
            </el-main>
        </el-container>
    </div>
</template>

<script>
import { useRoute } from 'vue-router'
import { computed } from "@vue/reactivity"
import { ref } from "vue"
import router from "@/router/index"
import $ from 'jquery'
import { useStore } from "vuex"
import { ElNotification } from 'element-plus'

import AdminAboutVue from "@/components/admin/AdminAbout.vue"
import AdminBar from "@/components/admin/AdminBar.vue"
import CommentManagerVue from "@/components/admin/CommentManager.vue"
import InvitationManagerVue from "@/components/admin/InvitationManager.vue"
import StudentManageVue from "@/components/admin/StudentManage.vue"
import TeacherManagerVue from "@/components/admin/TeacherManager.vue"
import TerminalSettingVue from "@/components/admin/TerminalSetting.vue"

export default {
    components: {
        AdminAboutVue,
        AdminBar,
        CommentManagerVue,
        InvitationManagerVue,
        StudentManageVue,
        TeacherManagerVue,
        TerminalSettingVue
    },
    setup() {
        const route = useRoute();
        const store = useStore();
        let cho = computed(() => route.path.split("/"));
        let currentcho = route.path;
        let current_page = computed(() => cho.value[3]);

        let isadminhome = computed(() => current_page.value === "adminhome");
        let isteacher = computed(() => current_page.value === "teachermanager");
        let isstudent = computed(() => current_page.value === "studentmanager");
        let isinvitation = computed(() => current_page.value === "invitationmanager");
        let iscomment = computed(() => current_page.value === "commentmanager");
        let isterminal = computed(() => current_page.value === "terminalmanager");
        let isadminsetting = computed(() => current_page.value === "adminsetting");

        let defaultpath = "/tongering/admin/";

        let adminhomepath = defaultpath + "adminhome/";
        let teacherpath = defaultpath + "teachermanager/";
        let studentpath = defaultpath + "studentmanager/";
        let invitationpath = defaultpath + "invitationmanager/";
        let commentpath = defaultpath + "commentmanager/";
        let terminalpath = defaultpath + "terminalmanager/";
        let adminsetting = defaultpath + "adminsetting/";
        let tableData = ref([]);

        const checkadmin = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/admin/check/admin/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "admin_true") {
                        ElNotification({
                            title: 'Success',
                            message: '身份认证成功',
                            type: 'success',
                            duration: 1000,
                        })
                    } else {
                        ElNotification({
                            title: 'Error',
                            message: '请不要尝试我的底线',
                            type: 'error',
                            duration: 1000,
                        })
                        router.push({
                            name: "home",
                        })
                    }
                },
                error(resp) {
                    console.log(resp);
                }
            })
        };
        checkadmin();

        return {
            currentcho,
            current_page,

            isadminhome,
            isteacher,
            isstudent,
            isinvitation,
            iscomment,
            isterminal,
            isadminsetting,

            adminhomepath,
            teacherpath,
            tableData,
            studentpath,
            invitationpath,
            commentpath,
            terminalpath,
            adminsetting,
        }
    }
}
</script>

<style scoped>
.router-link-active {
  text-decoration: none;
  color: yellow;
}
a{
  text-decoration: none;
  color: grey;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
  height: 100vh;
}
</style>