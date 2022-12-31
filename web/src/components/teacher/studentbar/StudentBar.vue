<template>
  <el-tabs v-model="activeTab">
        <el-tab-pane label="查看学生" name="first" ><StudentManager /></el-tab-pane>
        <el-tab-pane label="编辑学生" name="second"> <StudentAdd /> </el-tab-pane>
    </el-tabs>
</template>

<script>
import StudentManager from "@/components/teacher/student/StudentManage.vue"
import StudentAdd from "@/components/teacher/student/StudentAdd.vue"
export default {
    components: {
        StudentManager,
        StudentAdd,
    }
}
</script>

<style>

</style>