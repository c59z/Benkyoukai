import axios from 'axios'
import store from "./store.js";
import router from "./router.js";
import {ElMessage} from "element-plus";

const request = axios.create({
    baseURL: '/api',  // 注意！！ 这里是全局统一加上了 '/api' 前缀，也就是说所有接口都会加上'/api'前缀在，页面里面写接口的时候就不要加 '/api'了，否则会出现2个'/api'，类似 '/api/api/user'这样的报错，切记！！！
    timeout: 30000,
    validateStatus: function (status) {
        return status >= 200 && status <= 400; // 将状态码为 400 视为有效响应
    },
})

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    if (!(config.data instanceof FormData)) {
        config.headers['Content-Type'] = 'application/json;charset=utf-8';
    }
    config.headers['token'] = localStorage.getItem("token")
    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        if(response.data.code === 401){
            ElMessage({
                message: "用户未登录或会话已过期,请重新登录",
                type: 'error',
                plain: true,
            })
            store.commit("setToken",'')
            localStorage.removeItem('token')
            store.commit("setUserId",'')
            localStorage.removeItem('userId')
            router.push('/login')
        }
        // console.log(response)
        let res = response.data;
        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }
        // 兼容服务端返回的字符串数据
        // if (typeof res === 'string') {
        //     res = res ? JSON.parse(res) : res
        // }
        return res;
    },
    error => {
        console.log('err' + error) // for debug
        return Promise.reject(error)
    }
)

export default request