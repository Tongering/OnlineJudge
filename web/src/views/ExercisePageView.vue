<template>
    <NavBar/>
        <el-row style="display:flex;justify-content:center;margin-top:20px" >
            <el-col :span="22"  >
                <el-card class="box-card" shadow="hover" style="text-align:center">
                    {{ pagetitle }}
                </el-card>
            </el-col>

        </el-row>
        <el-row :gutter="20" style="display:flex;justify-content:center;margin-top:20px">
            <el-col :span="8" style=" margin-bottom: 20px">
                <el-card class="box-card" shadow="hover">
                    <md-editor v-model="pagecontent" previewOnly/>
                </el-card>
                
            </el-col>
            <el-col :span="14">
                <el-card class="box-card" shadow="hover">
                    <div id="codeEditBox">{{ testcode }}</div>
                    <el-button @click="handleValue">点击获取值</el-button>
                    <div v-show="ans">
                        <el-divider />
                        <h3>提交结果：{{ result }}</h3>
                    </div>
                </el-card>
            </el-col>
        </el-row>

    
</template>

<script>
import * as monaco from 'monaco-editor'
import NavBar from "@/components/NavBar.vue"
import { useRoute } from "vue-router"
import { useStore } from "vuex"
import $ from "jquery"
import { onMounted, onUnmounted } from 'vue'; // onMounted表示挂载，onUnmounted表示未挂载，即是否有打开当前界面
import { ElMessage } from 'element-plus'

import MdEditor from 'md-editor-v3';
 import 'md-editor-v3/lib/style.css';
import { ref, toRaw } from "vue"


export default {
    components: {
        NavBar,
        MdEditor,
    },
    setup() {
        const route = useRoute();
        const store = useStore();
        let cho = route.path.split('/');
        let type_id = cho[3];
        let pagecontent = ref('');
        let pagetitle = ref('');
        const editor = ref(null);
        let code = ref('');
        const socketUrl = `ws://${store.state.url.ip}:${store.state.url.port}/websocket/code/${store.state.user.token}/`;
        let socket = null;
        let result = ref('');
        let ans = ref(false);
        let template = ref('');

        onMounted(() => {

            gettemplate();
            initEditor();

            console.log(type_id);

            socket = new WebSocket(socketUrl);
            socket.onopen = () => {
                console.log("connected!")
            }
            socket.onclose = () => {
                console.log("disconnected!");
            }
            socket.onmessage = msg => {
                const data = JSON.parse(msg.data);
                console.log(data);
                console.log(data.error_message);
                if (data.error_message === "success") {
                    console.log(data.result);
                    console.log(data.error_id);
                    if (data.result === "success" && data.error_id === -1) {
                        console.log("AC");
                        result.value = "AC";
                        ans.value = true;
                    } else if (data.result === "Compile error" && data.error_id === -2) { 
                        result.value = "Compile Error";
                        ans.value = true;
                    }
                    else {
                        console.log("Wrong Answer");
                        result.value = "Wrong Answer" + data.error_id;
                        ans.value = true;
                    }
                } else {
                    ElMessage.error("通信错误")
                }
            }
            
        })

        onUnmounted(() => {
            socket.close();
        })

        const gettemplate = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/exercise/template/get/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    type_id: type_id,
                },
                success(resp) {
                    template.value = resp;
                    toRaw(editor.value).setValue(template.value)
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        const getpage = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/exercise/page/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    id: type_id
                },
                success(resp) {
                    console.log(resp);
                    pagecontent.value = resp.content;
                    pagetitle.value = resp.name;
                },
                error(resp) {
                    console.log(resp);
                }
            })
        };

        const initEditor = () => {
            // 初始化编辑器，确保dom已经渲染
            editor.value = monaco.editor.create(document.getElementById('codeEditBox'), {
                value: template.value, //编辑器初始显示文字
                language: 'java', //此处使用的python，其他语言支持自行查阅demo
                theme: 'vs', //官方自带三种主题vs, hc-black, or vs-dark
                selectOnLineNumbers: true,//显示行号
                roundedSelection: false,
                readOnly: false, // 只读
                cursorStyle: 'line', //光标样式
                automaticLayout: true, //自动布局
                glyphMargin: true, //字形边缘
                useTabStops: false,
                fontSize: 15, //字体大小
                autoIndent: true, //自动布局
                quickSuggestionsDelay: 100, //代码提示延时
            });
            // 监听值的变化
            // editor.value.onDidChangeModelContent((val) => {
            //     console.log(val.changes[0].text)
            // })
        }

        const handleValue = () => {
            code.value = toRaw(editor.value).getValue();
            console.log(code.value);
            socket.send(JSON.stringify({
                event: "commit-code",
                code: code.value,
                type_id: type_id,
            }))
           
        }
        getpage();
        return {
            pagecontent,
            pagetitle,
            editor,
            handleValue,
            result,
            ans,
        }
    }
}
</script>

<style scoped>
#codeEditBox {
  width:100%;
  height:60vh;
}
</style>