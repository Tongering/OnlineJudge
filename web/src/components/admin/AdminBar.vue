<template>
    <el-row justify="center">
        <el-col :span="20">
            <el-tabs v-model="activeTab">
                <el-tab-pane label="教师视图" name="first" ><AdminTeacherVue /></el-tab-pane>
                <el-tab-pane label="学生视图" name="second" ><AdminStudentVue /></el-tab-pane>
                <el-tab-pane label="管理员视图" name="third" ><AdminAdminVue /></el-tab-pane>
                <el-tab-pane label="封面管理" name="forth" ><AdminBarSettingVue /></el-tab-pane>
            </el-tabs>
        </el-col>
    </el-row>
</template>

<script>
import AdminStudentVue from './AdminStudent.vue';
import AdminTeacherVue from './AdminTeacher.vue';
import AdminBarSettingVue from './AdminBarSetting.vue';
import AdminAdminVue from './AdminAdmin.vue';
export default {
    components: {
        AdminStudentVue,
        AdminTeacherVue,
        AdminBarSettingVue,
        AdminAdminVue,
    },
    setup() {
        return {
            activeTab: 'first',
        }
    }
}
</script>

<style>

</style>