<template>
    <Navbar/>
    <el-row justify="center" style="margin-top:20px;margin-bottom:20px">
        <el-col :span="6">
            <el-input v-model="searchInput" size="large" placeholder="请输入要查找的帖子" :suffix-icon="Search" @change="searchInvitation" />
        </el-col>
        <el-divider />
    </el-row>
    <el-row justify="center" style="margin-top:20px; margin-bottom:20px">
        <el-col :span="18" v-for="like in likes" :key="like.id" @click="enter_invation_page(like.id)">
                <el-card class="box-card" shadow="hover">
                    <template #header>
                        <div class="card-header">
                            <span>
                                <el-avatar shape="square" :src="like.photo" />
                                <a>{{ like.name }}</a>
                            </span>
                            <el-button class="button" text>{{ like.type }}</el-button>
                        </div>
                    </template>
                    <div>{{ like.title }}</div>
                    <md-editor v-model=" like.content " previewOnly/>
                </el-card>
        </el-col>
    </el-row>
    <el-row>
        <el-col
            :span="8"
            v-for="o in list"
            :key="o.id"
            @click="enter_invation_place(o.id)"
        >
            <el-card :body-style="{ padding: '0px' }" shadow="hover">
            <img
                :src="o.photo" rel="external nofollow" 
                class="image"
            />
            <div style="padding: 14px;">
                <span>{{ o.typename }}</span>
                <div class="bottom">
                </div>
            </div>
            </el-card>
        </el-col>
    </el-row>
</template>

<script>
import Navbar from "@/components/NavBar.vue"
import $ from "jquery";
import { useStore } from "vuex";
import { ref } from "vue";
import router from "@/router/index";
import { Search } from '@element-plus/icons-vue'
import MdEditor from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
export default {
    components: {
        Navbar,
        MdEditor,
    },
    setup() {
        const store = useStore();
        let list = ref([]);
        let likes = ref([]);
        let searchInput = ref('');
        const gettype = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/invitation/type/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    list.value = resp;
                },
                error() {
                    console.log("can't no get the exercises type");
                }
            });
        };
        const enter_invation_place = invitationId => {
            router.push({ name: "invitation_choose_type", params: { type: invitationId } });
        }
        const searchInvitation = () => { 
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/invitation/search/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    search: searchInput.value,
                },
                success(resp) {
                    console.log("getlike");
                    console.log(resp);
                    likes.value = resp;
                },
                error() {
                    console.log("error");
                }
            })
        }

        const enter_invation_page = invitationId => {
            router.push({ name: "invitation_page", params: { pageId: invitationId } });
        }
        gettype();

        return  {
            list,
            likes,
            enter_invation_place,
            enter_invation_page,
            searchInvitation,
            Search,
            searchInput,
        }
    }
}
</script>

<style>

</style>