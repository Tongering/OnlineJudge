<template>
    <div class="common-layout">
        <el-container>
            <el-aside width="200px">
                <el-menu :default-active="currentcho" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" router>
                    <el-menu-item index="1" style="display:inline-block">
                        <i class="el-icon-location">
                            <img src="https://tongeringmd.oss-cn-guangzhou.aliyuncs.com/website/202210072302980.png" style="width:60px"/>
                        </i>
                        <template #title>{{ $store.state.user.username }}</template>
                    </el-menu-item>
                    <el-divider />
                    <!-- <el-sub-menu index="1">
                        <template #title>
                            <img :src=$store.state.user.photo style="width:60px"/>
                            <i class="el-icon-location"></i>
                            <span>导航一</span>
                        </template>
                        <el-menu-item-group>
                            <template #title>分组一</template>
                            <el-menu-item index="1-1">选项1</el-menu-item>
                            <el-menu-item index="1-2">选项2</el-menu-item>
                        </el-menu-item-group>
                        <el-menu-item-group title="分组2">
                            <el-menu-item index="1-3">选项3</el-menu-item>
                        </el-menu-item-group>
                        <el-sub-menu index="1-4">
                            <template #title>选项4</template>
                            <el-menu-item index="1-4-1">选项1</el-menu-item>
                        </el-sub-menu>
                    </el-sub-menu> -->
                    <el-menu-item :index=studentpath>
                        <i class="el-icon-menu"></i>
                        <template #title>学生管理</template>
                    </el-menu-item>

                    <el-menu-item :index=exercisepath>
                        <i class="el-icon-menu"></i>
                        <template #title>习题管理</template>
                    </el-menu-item>
                    
                    <el-menu-item :index=invitationpath >
                        <i class="el-icon-document"></i>
                        <template #title>帖子模块</template>
                    </el-menu-item>
                    <el-menu-item index="/">
                        <i class="el-icon-setting"></i>
                        <template #title><router-link  :to="{ name: 'home'}">回到首页</router-link></template>
                    </el-menu-item>
                </el-menu>
            </el-aside>
            <el-main>
                <el-row justify="center">
                    <el-col :span="18">
                        <div v-if="isstudent">
                            <StudentManageVue />
                        <!-- <UserHome/> -->
                        </div>
                        <div v-if="isexercise">
                            <ExerciseController />
                        </div>
                        <div v-if="isinvitation">
                            <InvitationBar />
                        </div>
                    </el-col>
                </el-row>
            </el-main>
        </el-container>
    </div>
</template>

<script>
import { useStore } from "vuex"
import { useRoute } from 'vue-router'
import ExerciseController from "@/components/teacher/ExerciseBar/ExerciseController.vue"
import StudentManageVue from "@/components/teacher/student/StudentManage.vue"
import InvitationBar from "@/components/teacher/Invitation/InvitationBar.vue"
import { computed } from "@vue/reactivity"
import $ from "jquery"
import router from "@/router/index"
import { ElNotification } from 'element-plus'

export default {
    components: {
        ExerciseController,
        StudentManageVue,
        InvitationBar,
    },
    setup() {
        const route = useRoute();
        const store = useStore();
        let cho = computed(() => route.path.split("/"));
        let currentcho = route.path;
        let current_page = computed(() => cho.value[3]);
        let isstudent = computed(() => current_page.value === "student");
        let isexercise = computed(() => current_page.value === "exercise");
        let isinvitation = computed(() => current_page.value === "invitation");

        let defaultpath = "/tongering/teacher/";

        let studentpath = defaultpath + "student/"
        let exercisepath = defaultpath + "exercise/";
        let invitationpath = defaultpath + "invitation/";

        console.log(cho.value);

        // let userId = route.params.userId;
        // console.log(userId);

        const checkteacher = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/teacher/check/teacher/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "teacher_true") {
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

        checkteacher();

        return {
            currentcho,
            current_page,
            isstudent,
            isexercise,
            isinvitation,
            studentpath,
            exercisepath,
            invitationpath,
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