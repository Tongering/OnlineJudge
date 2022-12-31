import $ from "jquery"
import ModuleUrl from "./url"
export default {
    state: {
        id: "",
        username: "",
        photo: "",
        token: "",
        background: "",
        is_login: false,
    },
    getters: {

    },
    mutations: {
        updateUser(state, user) {
            state.id = user.id;
            state.username = user.username;
            state.photo = user.photo;
            state.background = user.background;
            state.is_login = user.is_login;
        },
        updateToken(state, token) {
            state.token = token;
        },
        logout(state) {
            state.id = "";
            state.username = "";
            state.photo = "";
            state.token = "";
            state.background = "";
            state.is_login = false;
        }
    },
    actions: {
        login(context, data) {
            $.ajax ({
                url: "http://" + ModuleUrl.state.ip + ":" + ModuleUrl.state.port + "/api/user/login/token/",
                type: "post",
                data: {
                    username: data.username,
                    password: data.password,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        localStorage.setItem("jwt_token", resp.token);
                        context.commit("updateToken", resp.token);
                        data.success(resp);
                    }
                    else {
                        data.error(resp);
                    }
                },
                error(resp) {
                    data.error(resp);
                }
            })
        },
        getinfo(context, data) {
            $.ajax({
                url: "http://" + ModuleUrl.state.ip + ":" + ModuleUrl.state.port + "/api/user/login/info/",
                  type: "get",
                  headers: {
                      Authorization: "Bearer " + context.state.token,
                  },
                success(resp) {
                    if (resp.error_message === "success") {
                        context.commit("updateUser", {
                            ...resp,
                            is_login: true,
                        });
                        data.success(resp);
                    } else {
                        data.error(resp);
                        
                    }
                },
                error(resp) {
                    data.error(resp);
                    
                }
            })
        },
        logout(context) {
            localStorage.removeItem("jwt_token");
            context.commit("logout");
        }

    },
    modules: {
    }
}