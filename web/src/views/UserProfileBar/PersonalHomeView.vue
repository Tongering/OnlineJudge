<template>
    <div class="common-layout">
    <el-container >
        <el-aside width="200px">
            <el-menu :default-active="currentcho" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" router>
                <el-menu-item index="1" style="display:inline-block">
                    <i class="el-icon-location">
                        <el-avatar shape="square" :src=$store.state.user.photo />
                    </i>
                    <template #title>{{ $store.state.user.username }}</template>
                </el-menu-item>
                <el-divider />
                <el-menu-item :index=userhomepath>
                    <i class="el-icon-menu"></i>
                    <template #title>个人中心</template>
                </el-menu-item>
                
                <el-menu-item :index=userprofilepath >
                    <i class="el-icon-document"></i>
                    <template #title>账号信息</template>
                </el-menu-item>
                <el-menu-item index="/">
                    <i class="el-icon-setting"></i>
                    <template #title>
                        <router-link  :to="{ name: 'home'}">回到首页</router-link>
                    </template>
                </el-menu-item>
            </el-menu>
        </el-aside>
        <el-main>
            <div v-if="isuserhome">
                <UserHome/>
            </div>
            <div v-if="isuserprofile">
                <UserProfileVue />
            </div>
        </el-main>
    </el-container>
</div>
</template>

<script>
import { useStore } from "vuex"
import { useRoute } from 'vue-router'
import UserHome from "@/components/UserHome.vue"
import UserProfileVue from "@/components/User/Profile/UserProfile.vue"
import { computed } from "@vue/reactivity"
import { ref } from "vue"


export default {
    components: {
        UserHome,
        UserProfileVue,
    },
    setup() {
        const route = useRoute();
        const store = useStore();
        let cho = computed(() => route.path.split("/"));
        let currentcho = route.path;
        let current_page = computed(() => cho.value[4]);
        let isuserhome = computed(() => current_page.value === "userhome");
        let isuserprofile = computed(() => current_page.value === "userprofile");

        let userhomepath = "/user/space/" + store.state.user.id + "/userhome/";
        let userprofilepath = "/user/space/" + store.state.user.id + "/userprofile/";

        let tableData = ref([]);

        // let userId = route.params.userId;
        // console.log(userId);

        const getrecord = () => {
            store.dispatch("getrecord");
        };
        const getcount = () => {
            store.dispatch("getcount");
        }
        getrecord();
        getcount();

        return {
            currentcho,
            isuserhome,
            isuserprofile,
            current_page,
            userhomepath,
            userprofilepath,
            tableData,
        }
    }
}
</script>

<style scoped>
.router-link-active {
  text-decoration: none;
  color: yellow;
}
a{
  text-decoration: none;
  color: grey;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
  height: 100vh;
}
</style>