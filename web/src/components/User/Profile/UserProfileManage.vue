<template>
    <el-row justify="center">
        <el-col :span="24" style="text-align: center;">
          <el-avatar shape="square" :src=$store.state.user.photo />
        </el-col>
    </el-row>
    <el-row justify="center">
        <el-col :span="24" style="text-align: center;">
            <el-form style="display:inline-block;">
                <el-form-item label="修改用户名">
                        <el-input type="text" v-model="username" autocomplete="off" placeholder="请输入用户名"></el-input>
                </el-form-item>
                
                <el-form-item label="修改个人邮箱📫">
                        <el-input type="text" v-model="mail" autocomplete="off" placeholder="请输入个人邮箱"></el-input>
                </el-form-item>

                <el-form-item label="修改个人宣言">
                        <el-input type="text" v-model="description" autocomplete="off" placeholder="请输入个人宣言"></el-input>
                </el-form-item>
                
                <el-form-item >
                    <el-button type="primary" @click="login" style="margin: 0 auto;width: 100%" size="large" round>更新信息</el-button>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>
    <el-divider />
    <el-row>
        <el-col :span="24" style="text-align: center;">
            <div>上传头像</div>
            <el-upload
            class="upload-demo"
            drag
            :action="`http://` + $store.state.url.ip + `:` + $store.state.url.port + `/api/image/person/photo/upload/`"
            :headers="headerObj"
            multiple
            style="display:inline-block"
            >
                <i class="el-icon-upload"></i>
                <div class="el-upload__text" >将文件拖到此处，或<em>点击上传</em></div>
                <template #tip>
                    <div class="el-upload__tip">只能上传 jpg/png 文件，且不超过 2MB</div>
                </template>
            </el-upload>
            
        </el-col>
    </el-row>
    <el-divider />
    <el-row>
        <el-col :span="24" style="text-align: center;">
            <el-button type="danger" disabled>注销用户</el-button>
            <div style="margin-top: 20px">暂不支持自助注销</div>
            <div style="margin-top: 20px">如需注销账户，请发送邮件至 wzhengshimao@sina.cn</div>
            
        </el-col>
    </el-row>

</template>

<script>
import { ref } from "vue";
import { useStore } from "vuex"
import { ElMessage } from 'element-plus'
import $ from "jquery"
export default {
  data() {
    const store = useStore();
    return {
      headerObj: {
        Authorization: "Bearer " + store.state.user.token
      }
    }
  },
  
  setup() {
    let username = ref('');
    let mail = ref('');
    let description = ref('');
    let store = useStore();

    const login = () => {
      $.ajax({
        url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/profile/improve/",
        type: "post",
        headers: {
          Authorization: "Bearer " + store.state.user.token,
        },
        data: {
          username: username.value,
          mail: mail.value,
          description: description.value,
        },
        success(resp) {
          ElMessage.success("更新信息成功");
          console.log(resp);
        },
        error() {
          console.log("cannot improve profile");
        }
      })
    }

    const getinfo = () => {
      $.ajax({
        url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/profile/get/",
        type: "post",
        headers: {
          Authorization: "Bearer " + store.state.user.token,
        },
        success(resp) {
          username.value = resp.username;
          mail.value = resp.mail;
          description.value = resp.description;
        },
        error() {
          console.log("cannot improve profile");
        }
      })
    }
    getinfo();
    return {
      imageUrl: '',
      username,
      mail,
      description,
      login,
    }
  },

  methods: {
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw)
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },
  },
}
</script>

<style scoped>
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409eff;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>