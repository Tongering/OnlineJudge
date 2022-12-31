import { createStore } from 'vuex'
import ModuleUrl from "./url"
import ModuleUser from "@/store/user"
import ModuleRecord from "@/store/record"

export default createStore({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    url: ModuleUrl,
    user: ModuleUser,
    record: ModuleRecord,
  }
})
