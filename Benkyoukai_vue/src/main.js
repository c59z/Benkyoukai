import { createApp } from 'vue'
// import './style.css' // 默认css

import App from './App.vue'
import 'bootstrap/dist/js/bootstrap.min'
import 'bootstrap/dist/css/bootstrap.min.css'
import i18n from './js/i18n';
import store from './js/store'
import router from './js/router.js';
import mavonEditor from 'mavon-editor'
import "mavon-editor/dist/css/index.css"



const app = createApp(App)
    .use(i18n)
    .use(store)
    .use(router)
    .use(mavonEditor)
    .mount('#app')

