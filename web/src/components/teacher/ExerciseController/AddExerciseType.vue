<template>
    <el-row justify="center">
        <el-col :span="24">
            <el-form style="display:inline-block">
                    <el-form-item label="练习类型名称">
                            <el-input type="text" v-model="typename" autocomplete="off" placeholder="请输入练习类型名称"></el-input>
                    </el-form-item>

                    <!-- <el-form-item label="上传背景图片">
                        <el-upload
                        class="upload-demo"
                        drag
                        :action="`http://` + $store.state.url.ip + `:` + $store.state.url.port + `/image/person/photo/upload/`"
                        :headers="headerObj"
                        multiple
                        style="display:inline-block"
                        >
                            <i class="el-icon-upload"></i>
                            <div class="el-upload__text" >将文件拖到此处，或<em>点击上传</em></div>
                            <template #tip>
                                <div class="el-upload__tip">只能上传 jpg/png 文件，且不超过 500kb</div>
                            </template>
                        </el-upload>
                    </el-form-item> -->
                    
                    <el-form-item >
                        <el-button type="primary" @click="submittype" style="margin: 0 auto;width: 100%" size="large" round>提交</el-button>
                    </el-form-item>
                </el-form>
        </el-col>
    </el-row>
</template>

<script>
import $ from "jquery"
import { ref } from "vue"
import { useStore } from "vuex"
import { ElMessage } from 'element-plus'
export default {
    setup() { 
        let typename = ref('');
        const store = useStore();
        const submittype = () => { 
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/teacher/exercise/type/add/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token
                },
                data: {
                    typename: typename.value,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        ElMessage.success("创建习题类型成功");
                        location.reload();
                        typename.value = '';
                    }
                }
            })
        }
        return { 
            submittype,
            typename,
        }
    },
//     methods: {
//     handleAvatarSuccess(res, file) {
//       this.imageUrl = URL.createObjectURL(file.raw)
//     },
//     beforeAvatarUpload(file) {
//       const isJPG = file.type === 'image/jpeg'
//       const isLt2M = file.size / 1024 / 1024 < 2

//       if (!isJPG) {
//         this.$message.error('上传头像图片只能是 JPG 格式!')
//       }
//       if (!isLt2M) {
//         this.$message.error('上传头像图片大小不能超过 2MB!')
//       }
//       return isJPG && isLt2M
//     },
//   },
}
</script>

<style scoped>
/* .avatar-uploader .el-upload {
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
  } */
</style>