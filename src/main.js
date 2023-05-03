import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import VueSidebarMenu from 'vue-sidebar-menu'
import 'vue-sidebar-menu/dist/vue-sidebar-menu.css'

const app = createApp(App);
app.use(router);
app.use(store);
app.use(VueSidebarMenu);
app.component('font-awesome-icon', FontAwesomeIcon, );
app.mount('#app');

