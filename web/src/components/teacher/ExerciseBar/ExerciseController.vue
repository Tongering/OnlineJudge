<template>
    <el-tabs v-model="activeTab">
        <el-tab-pane label="创建习题" name="first" ><AddExerciseVue/></el-tab-pane>
        <el-tab-pane label="管理习题" name="second"><ModifyExercise/></el-tab-pane>
        <el-tab-pane label="创建测试" name="third"><AddExerciseTest/></el-tab-pane>
        <el-tab-pane label="管理测试" name="fourth"><ModifyExerciseTest/></el-tab-pane>
        <el-tab-pane label="创建类型" name="five"><AddExerciseType /></el-tab-pane>
        <el-tab-pane label="管理类型" name="six"><ModifyExerciseType /></el-tab-pane>
    </el-tabs>
</template>

<script>
import AddExerciseVue from '@/components/teacher/ExerciseController/AddExercise.vue';
import ModifyExercise from "@/components/teacher/ExerciseController/ModifyExercise.vue";
import AddExerciseTest from "@/components/teacher/ExerciseController/AddExerciseTest.vue";
import ModifyExerciseTest from "@/components/teacher/ExerciseController/ModifyExerciseTest.vue";
import AddExerciseType from '@/components/teacher/ExerciseController/AddExerciseType.vue';
import ModifyExerciseType from '@/components/teacher/ExerciseController/ModifyExerciseType.vue'
export default {
    components: {
        AddExerciseVue,
        ModifyExercise,
        AddExerciseTest,
        ModifyExerciseTest,
        AddExerciseType,
        ModifyExerciseType,
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