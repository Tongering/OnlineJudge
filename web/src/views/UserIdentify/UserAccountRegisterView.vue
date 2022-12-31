<template>
    <el-row type="flex" justify="center" align="middle" style="text-align:center; margin-top:50px">
        <el-col :span="10">
            <img src="https://tongeringmd.oss-cn-guangzhou.aliyuncs.com/website/202210062342046.jpg" style="width: 80%;"/>
        </el-col>
        <el-col :span="10" style="margin-top: 50px;">
            <el-card class="box-card">
                <template #header>
                <div class="card-header">
                    <span>注册 Register</span>
                    <img src="https://tongeringmd.oss-cn-guangzhou.aliyuncs.com/website/202210062327358.png" style="width:250px;position:absolute;"/>
                </div>
                </template>
                <el-form style="display:inline-block">
                    <el-form-item>
                            <el-input type="text" v-model="username" autocomplete="off" placeholder="请输入用户名"></el-input>
                    </el-form-item>
                    
                    <el-form-item>
                        <el-input type="password" v-model="password" autocomplete="off" placeholder="请输入密码"></el-input>
                    </el-form-item>

                    <el-form-item>
                        <el-input type="password" v-model="confirmedPassword" autocomplete="off" placeholder="请再次输入密码"></el-input>
                    </el-form-item>
                    
                    <el-form-item >
                        <el-button type="primary" @click="register" style="margin: 0 auto;width: 100%" size="large" round>提交</el-button>
                    </el-form-item>
                    <el-divider />
                    <span>已经有了账户吗？<router-link class="nav-link active" :to="{name: 'user_login'}" style="display: inline-block;color:#409eff">登录</router-link></span>
                </el-form>
            </el-card>
        </el-col>
    </el-row>
</template>

<script>
import $ from "jquery";
import { ref } from "vue";
import router from "@/router/index";
import { useStore } from "vuex";
import { ElMessage } from 'element-plus'
export default {
    components: {
    
    },
    setup() {
        const store = useStore();
        let username = ref('');
        let password = ref('');
        let confirmedPassword = ref('');
        let error_message = ref('');
        

        const register = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/user/register/",
                type: "post",
                data: {
                    username: username.value,
                    password: password.value,
                    confirmedPassword: confirmedPassword.value,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        console.log("success register");
                        ElMessage({
                            message: '登录成功',
                            type: 'success',
                        })
                        router.push({ name: "user_login" });
                    }
                    else {
                        console.log("error register");
                        error_message.value = resp.error_message;
                        ElMessage.error(resp.error_message);
                    }
                }
            })
        }

        return {
            register,
            username,
            password,
            confirmedPassword,
            error_message,
        }
    }
}
</script>

<style scoped>
.error {
    position: fixed;
    top: 3em;
    right: 1em;
}
</style>