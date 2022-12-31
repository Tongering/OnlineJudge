<template>
    <el-row justify="center">
        <el-col :span="24">
            <el-table
                :data="$store.state.record.table_data"
                style="width: 100%"
                :row-class-name="tableRowClassName"
            >
                <el-table-column prop="id" label="记录序号" width="180"> </el-table-column>
                <el-table-column prop="exerciseName" label="题目名称" width="180"> </el-table-column>
                <el-table-column prop="result" label="结果"> </el-table-column>
                <el-table-column prop="recordTime" label="提交时间"> </el-table-column>
            </el-table>
        </el-col>
    </el-row>
</template>

<script>



export default {
    setup() {
    
    return {

    }
    }
}
</script>

<style>

</style>