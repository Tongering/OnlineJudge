<template>
  <el-row justify="center">
    <el-col :span="20">
      <el-tabs v-model="activeTab">
          <el-tab-pane label="创建终端" name="first" ><CreateTerminalVue /></el-tab-pane>
          <el-tab-pane label="终端面板" name="second" ><TerminalPanelVue /></el-tab-pane>
      </el-tabs>
    </el-col>
  </el-row>
</template>

<script>
import CreateTerminalVue from './CreateTerminal.vue';
import TerminalPanelVue from './TerminalPanel.vue';
export default {
  components: {
    CreateTerminalVue,
    TerminalPanelVue,
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