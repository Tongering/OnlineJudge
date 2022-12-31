<template>
    <el-button type="primary" @click="dialogVisible = true"  style="margin: 0 auto;width: 100%" size="large" round>Terminal</el-button>
        <el-dialog
        title="Terminal"
        v-model="dialogVisible"
        width="70%"
        :before-close="handleClose"
        >
        <TerminalViewVue />
        <!-- <span>这是一段信息</span>
        <template #footer>
            <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
            </span>
        </template> -->
    </el-dialog>
</template>

<script>
import TerminalViewVue from '@/views/TerminalView.vue';
export default {
    components: {
        TerminalViewVue,
    },
    data() {
      return {
        dialogVisible: false,
      }
    },
    setup() {

    }
}
</script>

<style>

</style>