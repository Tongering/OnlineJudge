<template>
  <el-input v-model="searchtext" class="w-50 m-2" placeholder="Please Input" @change="searchStudent" :suffix-icon="Search"/>
</template>

<script>
import { ref } from "vue"
import { Search } from '@element-plus/icons-vue'
export default {
    setup() {
        let searchtext = ref('');
        const searchStudent = () => {
            console.log(searchtext);
        }
        return {
            Search,
            searchStudent,
            searchtext,
        }
    }
}
</script>

<style>

</style>