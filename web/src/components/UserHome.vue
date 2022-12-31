<template>
    <UserBackgroundVue/>
    <el-row >
        <el-col :span="13">
            <div style="margin-left:20px;margin-top:20px;">
                <el-card class="box-card">
                    <template #header>
                        <div class="card-header">
                        <span>练习进度</span>
                        </div>
                    </template>

                    <el-row style="margin-left: 20px">
                        <el-col: :span="5" style="margin-left 20px; margin-right:20px">
                            <div >初级：</div>
                                <el-progress type="circle" :percentage="$store.state.record.primary"></el-progress>
                        </el-col:>
                        <el-col: :span="5" style="margin-left 20px; margin-right:20px">
                            <div >中级：</div>
                                <el-progress type="circle" :percentage="$store.state.record.intermediate"></el-progress>
                        </el-col:>
                        <el-col: :span="5" style="margin-left 20px; margin-right:20px">
                            <div >高级：</div>
                                <el-progress type="circle" :percentage="$store.state.record.senior"></el-progress>
                        </el-col:>
                    </el-row>
                    
                    <!-- <div v-for="o in 4" :key="o" class="text item">{{'列表内容 ' + o }}</div> -->
                </el-card>
            </div>
        </el-col>
        <el-col :span="10">
            <div style="margin-left:20px;margin-top:20px;">
                <el-card class="box-card">
                    <template #header>
                        <div class="card-header">
                        <span>近日练习&emsp;</span>
                        <span style="font-weight:200">&emsp;仅显示近十次记录</span>
                        </div>
                    </template>
                    
                    <div class="block">
                        <el-timeline>
                            <el-timeline-item
                            v-for="(activity, index) in $store.state.record.table_data.slice(0,10)"
                            :key="index"
                            :timestamp="activity.recordTime"
                            >
                            {{activity.exerciseName}}
                            </el-timeline-item>
                        </el-timeline>
                    </div>
                </el-card>
            </div>
        </el-col>
        
    </el-row>    

    
    
</template>

<script>
import UserBackgroundVue from '@/components/User/Profile/UserBackground.vue';

export default {
    components: {
        UserBackgroundVue,
    },
    setup() {

    }
}
</script>

<style>
.background-personal{
    position: relative;
    height:30vh;
    width:80vw;
    background-size: cover;
    background-position: center;
    margin-left:20px;
    margin-top: 20px;
}
</style>