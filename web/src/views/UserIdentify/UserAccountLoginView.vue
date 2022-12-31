<template>
    <el-row type="flex" justify="center" align="middle" style="text-align:center; margin-top:50px">
        <el-col :span="10">
            <img src="https://tongeringmd.oss-cn-guangzhou.aliyuncs.com/website/202210061856294.jpg" style="width: 80%;"/>
        </el-col>
        <el-col :span="10" style="margin-top: 50px;">
            <el-card class="box-card">
                <template #header>
                <div class="card-header">
                    <h4>登录 Login</h4>
                    <img src="https://tongeringmd.oss-cn-guangzhou.aliyuncs.com/website/202210062316222.png" style="width:250px;position:absolute;"/>
                </div>
                </template>
                <el-form style="display:inline-block">
                    <el-form-item>
                            <el-input type="text" v-model="username" autocomplete="off" placeholder="请输入用户名"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-input type="password" v-model="password" autocomplete="off" placeholder="请输入密码"></el-input>
                    </el-form-item>
                    <el-form-item >
                        <el-button type="primary" @click="login" style="margin: 0 auto;width: 100%" size="large" round>登录</el-button>
                    </el-form-item>
                </el-form>
                <el-divider />
                <span>新用户吗？<router-link class="nav-link active" :to="{name: 'user_register'}" style="display: inline-block;color:#409eff">注册</router-link></span>
            </el-card>
        </el-col>
    </el-row>
</template>

<script>
import { useStore } from "vuex";
import { ref } from "vue";
import { ElMessage } from 'element-plus'
import router from '@/router/index'

export default {
    setup() {
        const store = new useStore();
        let username = ref('');
        let password = ref('');

        const login = () => {
            store.dispatch("login", {
                username: username.value,
                password: password.value,
                success() {
                    store.dispatch("getinfo", {
                        success() {
                            console.log("success login");
                            ElMessage({
                                message: '登录成功',
                                type: 'success',
                            });
                            router.push({ name: 'home' });
                        }
                    })
                },
                error() {
                    ElMessage.error("用户名或密码错误")
                },
            })
        };
    return {
        login,
        username,
        password,
    }
  }
}
</script>

<style scoped>
.el-row {
  margin-bottom: 20px;
}
.el-col {
  border-radius: 4px;
}
</style>