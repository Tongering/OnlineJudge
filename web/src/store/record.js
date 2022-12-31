import $ from "jquery"
import ModuleUser from "./user"
import ModuleUrl from "./url"
export default {
    state: {
        table_data: [],
        primary: 0,
        intermediate: 0,
        senior: 0,
    },
    getters: {
    },
    mutations: {
        updateTableData(state, list) {
            state.table_data = list;
        },
        updateCount(state, primary, intermediate, senior) {
            state.primary = primary;
            state.intermediate = intermediate;
            state.senior = senior;
        },
        remove(state) {
            state.table_data = [];
            state.primary = 0;
            state.intermediate = 0;
            state.senior = 0;
        },
    },
    actions: {
        getrecord(context) {
            $.ajax({
                url: "http://" + ModuleUrl.state.ip + ":" + ModuleUrl.state.port + "/api/record/get/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + ModuleUser.state.token,
                },
                success(resp) {
                    console.log(resp);
                    context.commit("updateTableData", resp);
                },
                error() {
                    console.log("cannot request the record");
                }
            })
        },
        getcount(context) {
            $.ajax({
                url: "http://" + ModuleUrl.state.ip + ":" + ModuleUrl.state.port + "/api/exercise/count/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + ModuleUser.state.token,
                },
                success(resp) {
                    console.log(resp);
                    context.commit("updateCount", resp.primary, resp.intermediate, resp.senior);
                },
                error() {
                    console.log("cannot request the record record");
                }
            })
        },
    },
    modules: {
    }
  }