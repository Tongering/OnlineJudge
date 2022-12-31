<template>
  <div class="background-personal" :style="{backgroundImage:'url(' + $store.state.user.background + ')'}">
        <div style="position: absolute; bottom:24px; left:24px ;display: inline-block;">
            <el-avatar shape="square" :src=$store.state.user.photo />
            <span style="margin-left: 20px;font-weight:800;">{{ $store.state.user.username }}</span>
            <div>这个人很懒，什么都没有留下</div>
        </div>
    </div>
</template>

<script>
export default {

}
</script>

<style>

</style>