<template>
    <nav class="navbar navbar-expand-lg" style="background-color: #e3f2fd;">
    <div class="container">
        <router-link class="navbar-brand" :to="{name: 'home'}">Online Judge</router-link>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <!-- <a class="nav-link active" aria-current="page" href="#">Friends Square</a> -->
                    <!-- <router-link class="nav-link active" :to="{name: 'terminal'}">Terminal</router-link> -->
                    <a class="nav-link active" @click="terminal_check">Terminal</a>
                </li>
                <li class="nav-item">
                    <router-link class="nav-link active" :to="{name: 'invitation_choose'}">Invitation</router-link>
                </li>
                <li class="nav-item">
                    <router-link class="nav-link active" :to="{name: 'exercise_choose'}">Exercises</router-link>
                </li>
            </ul>
            <ul class="navbar-nav" v-if="$store.state.user.is_login">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        {{ $store.state.user.username }}
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><router-link class="dropdown-item" :to="{name: 'personal_home_userhome', params: {userId: $store.state.user.id}}">我的主页</router-link></li>
                        <!-- <li><router-link class="dropdown-item" :to="{name: 'user_bot_index'}">我的bot</router-link></li> -->
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="#" @click="logout">退出</a></li>
                    </ul>
                </li>
                <li class="nav-item">
                    <router-link class="nav-link active" :to="{name: 'post_invitation'}">Post</router-link>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="http://119.29.201.212:8099/">Git</a>
                </li>
            </ul>
            <ul class="navbar-nav" v-else>
                <ul class="navbar-nav">
                    <router-link class="nav-link active" :to="{name: 'user_login'}">登录</router-link>
                </ul>
                <ul class="navbar-nav">
                    <router-link class="nav-link active" :to="{name: 'user_register'}">注册</router-link>
                </ul>
            </ul>
            
        </div>
    </div>
    </nav>
    <el-dialog
        title="Terminal"
        v-model="dialogVisible"
        width="70%"
        :before-close="handleClose"
        draggable
        >
        <div v-if="(dialogVisible === true)">
            <TerminalViewVue />
        </div>
       
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
import { useRoute } from "vue-router";
import { computed } from "@vue/reactivity";
import { useStore } from 'vuex';
import TerminalViewVue from '@/views/TerminalView.vue';
import router from "@/router/index";
import { ref } from "vue"
export default {
    components: {
        TerminalViewVue,
    },
    setup() {
        const store = useStore();
        const route = useRoute();
        let dialogVisible = ref(false);
        let route_name = computed(() => route.name);

        const logout = () => {
            store.dispatch("logout");
            store.dispatch("remove");
        }
        const handleClose = () => {
            dialogVisible.value = false;
        }
        const terminal_check = () => {
            if (store.state.user.is_login === false) {
                router.push({ name: "user_login" });
            } else {
                dialogVisible.value = true;
            }
        }
        return {
            handleClose,
            route_name,
            logout,
            terminal_check,
            dialogVisible,
        }
    }
}
</script>

<style scoped>

</style>