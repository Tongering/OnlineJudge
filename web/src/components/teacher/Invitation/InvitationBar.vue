<template>
  <el-tabs v-model="activeTab">
        <el-tab-pane label="创建帖子类型" name="first" ><AddInvitationTypeVue/></el-tab-pane>
        <el-tab-pane label="管理帖子类型" name="second"><ModifyInvitationTypeVue/></el-tab-pane>
    </el-tabs>
</template>

<script>
import AddInvitationTypeVue from './AddInvitationType.vue';
import ModifyInvitationTypeVue from './ModifyInvitationType.vue';
export default {
    components: {
        AddInvitationTypeVue,
        ModifyInvitationTypeVue,
    }

}
</script>

<style>

</style>