<template>
    <NavBarVue/>
    <el-row justify="center" style="margin-top:20px;margin-bottom:20px">
        <el-col :span="6">
            <el-input v-model="input" size="large" placeholder="请输入要查找的练习" :suffix-icon="Search" @change="searchExercise(); tableVison = true" />
        </el-col>
        <el-divider />
    </el-row>
    <el-row justify="center" style="margin-top:20px; margin-bottom:20px">
        <el-col :span="18">
            <el-table :data="likes" stripe style="width: 100%" v-if="tableVison" :key="likes">
                <el-table-column prop="id" label="ID" />
                <el-table-column label="练习名称">
                    <template #default="scope">
                        <router-link  :to="{ name: 'exercise_page', params: { pageId: scope.row.id}}">{{ scope.row.name }}</router-link>
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" label="创建时间" />
            </el-table>
        </el-col>
    </el-row>
    <el-row>
        <el-col
            :span="8"
            v-for="o in list"
            :key="o.id"
            @click="enter_exercise_place(o.id)"
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

<script scoped>
import NavBarVue from '@/components/NavBar.vue';
import $ from "jquery";
import { useStore } from "vuex";
import { ref } from "vue";
import router from "@/router/index";
import { Search } from '@element-plus/icons-vue'
export default {
    components: {
        NavBarVue,
    },
    data() { 
        return {
            tableVison: false,
        }
    },
    setup() {
        const store = useStore();
        let list = ref([]);
        let input = ref('');
        let likes = ref([]);
        const gettype = () => {
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/exercises/type/",
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
        const enter_exercise_place = exerciseId => {
            router.push({ name: "exercise_choose_type", params: { type: exerciseId } });
        }
        const searchExercise = () => { 
            $.ajax({
                url: "http://" + store.state.url.ip + ":" + store.state.url.port + "/api/exercise/search/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    searchName: input.value,
                },
                success(resp) {
                    
                    likes.value = resp;
                    console.log(likes.value);
                },
                error() {
                    console.log("can't no get the exercises type");
                }
            });
        }

        gettype();

        return {
            currentDate: new Date(),
            list,
            enter_exercise_place,
            Search,
            searchExercise,
            input,
            likes,
        }
    }
}
</script>

<style scoped>
.router-link-active {
  text-decoration: none;
  color: yellow;
}
a{
  text-decoration: none;
  color: grey;
}
.time {
    font-size: 13px;
    color: #999;
  }

  .bottom {
    margin-top: 13px;
    line-height: 12px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .button {
    padding: 0;
    min-height: auto;
  }

  .image {
    width: 100%;
    display: block;
  }
</style>