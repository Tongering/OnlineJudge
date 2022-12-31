<template>
  <el-row justify="center">
    <el-col :span="20">
      <el-card class="box-card">
        <el-row justify="center">
          <el-col :span="3">
            <el-progress type="circle" :percentage="studentCount" >
              <span>Student</span>
            </el-progress>
          </el-col>
          <el-col :span="3">
            <el-progress type="circle" :percentage="teacherCount" >
              <span>Teacher</span>
            </el-progress>
          </el-col>
          <el-col :span="3">
            <el-progress type="circle" :percentage="adminCount" >
              <span>Admin</span>
            </el-progress>
          </el-col>
          <el-divider />
          <div>People Panel</div>
        </el-row>
      </el-card>
    </el-col>
  </el-row>
  <el-row justify="center">
    <el-col :span="10">
      <el-card class="box-card">
        <el-row justify="center">
          <el-col :span="5">
            Code Running
          </el-col>
          <el-divider />
        </el-row>
        <el-row justify="center">
          <el-timeline>
            <el-timeline-item v-for="i in lists" :key="i.id" :timestamp="i.recordTime" placement="top" @click="goexercise(i.codeId)">
                <span>
                  <el-avatar shape="square" size="small" :src="i.userPhoto" />
                  <div>
                    <a>{{ i.userName }}</a>
                    <a>&emsp;</a>
                    <a>{{ i.codeName }}</a>
                  </div>
                  <div>
                    <a>{{ i.codeType }}</a>
                    &emsp;
                    <a>{{ i.recordResult }}</a>
                  </div>
                </span>
            </el-timeline-item>
          </el-timeline>
        </el-row>
      </el-card>
    </el-col>
    <el-col :span="10">
      <el-card class="box-card">
        <el-row justify="center">
          <el-col :span="5">
            Invitation New
          </el-col>
          <el-divider />
        </el-row>
        <el-row justify="center">
          <el-timeline>
            <el-timeline-item v-for="i in invitationList" :key="i.id" :timestamp="i.recordTime" placement="top" @click="enter_invitation_place(i.id)">
                <span>
                  <el-avatar shape="square" size="small" :src="i.photo" />
                  <div>
                    <a>{{i.name}}</a>
                    <a>{{i.type}}</a>
                  </div>
                  <div>
                    <a>{{i.title}}</a>
                  </div>
                  <div>
                    <a>{{i.content}}</a>
                  </div>
                </span>
            </el-timeline-item>
          </el-timeline>
        </el-row>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
import $ from "jquery"
import { useStore } from "vuex"
import { ref } from "vue"
import router from '@/router/index'


export default {
  setup() {
    const store = useStore();
    let adminCount = ref(0);
    let teacherCount = ref(0);
    let studentCount = ref(0);
    const lists = ref([]);
    const invitationList = ref([]);

    const getCount = () => {
      $.ajax({
        url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/user/count/get/",
        type: "post",
        headers: {
            Authorization: "Bearer " + store.state.user.token,
        },
        success(resp) {
          adminCount.value = resp.admin;
          teacherCount.value = resp.teacher;
          studentCount.value = resp.student;
        },
        error(resp) {
          console.log(resp);
        }
      })
    }
    const goexercise = exerciseId => {
      router.push({ name: 'exercise_page', params: { pageId: exerciseId }});
    }

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
          console.log(resp)
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
    getCount();
    getBrief();



    return {
      adminCount,
      teacherCount,
      studentCount,
      lists,
      goexercise,
      invitationList,
      enter_invitation_place,
    }
  }

}
</script>

<style>

</style>