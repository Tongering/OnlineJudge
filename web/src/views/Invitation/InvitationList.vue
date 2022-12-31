<template>
    
    <Navbar/>

        <el-row
        :span="8"
        v-for="o in list"
        :key="o.id"
        @click="enter_invitation_place(o.id)"
        justify="center"
    >
        <el-col :span="20">
            <el-card class="box-card" shadow="hover">
                <template #header>
                    <div class="card-header">
                        <span>
                            <el-avatar shape="square" :src="o.photo" />
                            <a>{{ o.name }}</a>
                        </span>
                        <el-button class="button" text>{{ o.type }}</el-button>
                    </div>
                </template>
                <div>{{ o.title }}</div>
                <md-editor v-model=" o.content " previewOnly/>
            </el-card>
        </el-col>
    </el-row>
</template>

<script>
import Navbar from "@/components/NavBar.vue";
import $ from "jquery"
import { useStore } from "vuex"
import { useRoute } from "vue-router";
import { ref } from "vue";
import router from "@/router/index";
import MdEditor from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';

export default {
    components: {
        Navbar,
        MdEditor,
    },
    setup() {
        const store = useStore();
        const route = useRoute();
        let list = ref([]);
        let cho = route.path.split('/');
        let type_id = cho[3];
        const getBrief = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/invitation/brief/",
                type: "get",
                data: {
                    typeId: type_id,
                    isStrain: "yes",
                },
                success(resp) {
                    console.log("success");
                    console.log(resp);
                    list.value = resp;
                },
                error() {
                    console.log("error");
                }
            })
        }
        const enter_invitation_place = invitationId => {
            router.push({ name: "invitation_page", params: { pageId: invitationId } });
        }

        getBrief();

        return {
            list,
            enter_invitation_place,
        }
    }
}
</script>

<style>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>