import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import vuetify from "./plugins/vuetify";
import { loadFonts } from "./plugins/webfontloader";
import axios from "axios";
import VueFinder from "vuefinder/dist/vuefinder";
import store from "./store";

loadFonts();
const app = createApp(App);
app.config.globalProperties.$axios = axios;

app.use(VueFinder).use(router).use(vuetify).use(store).mount("#app");
