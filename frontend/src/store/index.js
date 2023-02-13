import { createStore } from "vuex";
import mutations from "./mutations";
import actions from "./actions";
import createPersistedState from "vuex-persistedstate";

export default createStore({
  state: {
    // folders: [],
    counter: 10,
  },
  getters: {
    // folderItems(state) {
    //   return state.folders;
    // },
    items(state) {
      return state.counter;
    },
  },
  mutations,
  actions,
  plugins: [
    createPersistedState({
      paths: ["counter"],
    }),
  ],
});
