import { createStore } from "vuex";
import mutations from "./mutations";
import actions from "./actions";

export default createStore({
  plugins: [
    //
  ],
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
});
