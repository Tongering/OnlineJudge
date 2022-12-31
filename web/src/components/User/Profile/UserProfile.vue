<template>
    <UserBackgroundVue />
    <el-row style="margin-left: 20px;margin-top:20px;">
        <el-col :span="24">
            <el-tabs type="border-card" v-model="activeTab">
                <el-tab-pane label="个人信息" name="first" ><UserProfileManager/></el-tab-pane>
                <el-tab-pane label="做题记录" name="second"><UserExerciseRecordVue/></el-tab-pane>
                <el-tab-pane label="题解管理" name="third"><UserInvitationVue/></el-tab-pane>
                <el-tab-pane label="添加老师" name="fourth"><TeacherAddVue /></el-tab-pane>
            </el-tabs>
        </el-col>
    </el-row>
</template>

<script>
import UserBackgroundVue from '@/components/User/Profile/UserBackground.vue';
import UserProfileManager from "@/components/User/Profile/UserProfileManage.vue"
import UserExerciseRecordVue from '@/components/User/Exercise/UserExerciseRecord.vue';
import UserInvitationVue from "@/components/User/Invitation/UserInvitation.vue";
import TeacherAddVue from './TeacherAdd.vue';

export default {
    components: {
        UserBackgroundVue,
        UserProfileManager,
        UserExerciseRecordVue,
        UserInvitationVue,
        TeacherAddVue,
    },
    setup() {


        return {
            activeTab: 'second',
        }
    }
}
</script>

<style>

</style>