<template>
  <Navbar/>
  <div class="block text-center">
    <el-carousel height="383px" v-if="imgList.length > 0">
      <el-carousel-item v-for="item in imgList" :key="item.id">
        <img :src="item.photo" class="image"/>
      </el-carousel-item>
    </el-carousel>
  </div>
  <el-row justify="center">
    <el-col :span="5">
      <el-card>
        <el-timeline>
          <el-timeline-item v-for="i in lists" :key="i.id" :timestamp="i.recordTime" placement="top" @click="goexercise(i.codeId)">
            <el-card>
              <span>
                <el-avatar shape="square" size="small" :src="i.userPhoto" />
                <a>{{ i.userName }}</a>
                <a>{{ i.codeName }}</a>
                <a>{{ i.codeType }}</a>
                <a>{{ i.recordResult }}</a>
              </span>
            </el-card>
          </el-timeline-item>
        </el-timeline>
      </el-card>
    </el-col>
    <el-col :span="15">
      <el-card>
        <el-card v-for="i in invitationList" :key="i.id" @click="enter_invitation_place(i.id)" style="margin-top:10px;margin-bottom:10px">
          <template #header>
              <div class="card-header">
                <span>
                  <el-avatar shape="square" :src="i.photo" />
                  <a>{{ i.name }}</a>
                </span>
                <el-button class="button" text>{{ i.type }}</el-button>
              </div>
          </template>
          <div>{{ i.title }}</div>
          <md-editor v-model=" i.content " previewOnly/>
        </el-card>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
import Navbar from "@/components/NavBar.vue"
import $ from "jquery"
import { useStore } from "vuex"
import { ref } from "vue"
import router from '@/router/index'
import MdEditor from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
export default {
    components: {
    Navbar,
    MdEditor,
    },
  setup() {
    const store = useStore();
    const lists = ref([]);
    const imgList = ref([]);
    const invitationList = ref([]);
    const getRecord = () => {
      $.ajax({
        url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/show/record/",
        type: "get",
        success(resp) {
          lists.value = resp;
        },
        error() {
          console.log("cannot get the show record");
        }
      })
    };
    const goexercise = exerciseId => {
      router.push({ name: 'exercise_page', params: { pageId: exerciseId }});
    }
    const getbarphoto = () => {
      $.ajax({
        url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/bar/photo/",
        type: "get",
        success(resp) {
          imgList.value = resp;
          console.log(resp);
        },
        error() {
          console.log("cannot get the bar photo");
        }
      })
    }
    const getBrief = () => {
      $.ajax({
        url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/invitation/brief/",
        type: "get",
        data: {
          typeId: 0,
          isStrain: "yes",
        },
        success(resp) {
          invitationList.value = resp;
        },
        error() {
          console.log("error");
        }
      })
    }
    const enter_invitation_place = invitationId => {
      router.push({ name: "invitation_page", params: { pageId: invitationId } });
    }

    getRecord();
    getbarphoto();
    getBrief();

    return {
      lists,
      goexercise,
      imgList,
      invitationList,
      enter_invitation_place,
    }
  }

}
</script>

<style>

</style>