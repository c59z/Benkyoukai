// router.js
import { createRouter, createWebHistory } from 'vue-router';
import Index from '../views/Index.vue';
import Login from "../views/user/Login.vue";
import Register from "../views/user/Register.vue";
import UserCenter from "../views/user/UserCenter.vue";
import ArticlesDetail from "../views/articles/ArticlesDetail.vue";
import DictIndex from "../views/dictionary/DictIndex.vue";
import Speak from "../views/speak/Speak.vue";
import PlayList from "../views/speak/PlayList.vue";
import LessonInfo from "../views/speak/LessonInfo.vue";
import store from "./store.js";
import Write from "../views/articles/Write.vue";


const routes = [
    {
        path: '/',
        name: 'Index',
        component: Index ,
    },
    {
        path: '/search',
        name: 'Search',
        component: Index
    },
    {
        path: '/dict',
        name: 'DictIndex',
        component: DictIndex,
    },
    {
        path: '/searchWord',
        name: 'searchWord',
        component: DictIndex,
    },
    {
        path: '/speak',
        component: Speak,
        children: [
            {
                path: '',
                component: LessonInfo,
            },
            {
                path: 'playlist/:id*',
                component: PlayList,
            },
        ],
    },
    { path: '/article/:id', name: 'ArticlesDetail', component: ArticlesDetail, props:true },
    { path: '/login', name: 'Login', component: Login },
    { path: '/register', name: 'Register', component: Register },
    { path: '/user/:id', name: 'UserCenter', component: UserCenter},
    { path: '/write', name: 'Write', component: Write},
    { path: '/edit/:id*', name: 'Edit', component: Write},

];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

router.beforeEach((to,from, next) => {
    const token = store.state.token;
    if(to.path === '/user/:id'){
        if(!token){
            next("/login")
        }else{
            next();
        }
    }
    next()
})

export default router;
