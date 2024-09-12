import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import ElementPlus from 'unplugin-element-plus/vite'


// https://vitejs.dev/config/
export default defineConfig({
    plugins: [
        ElementPlus({
            // options
        }),
        vue()
    ],

    server: {
        proxy: {
            // 代理规则
            '/api': {
                target: 'http://localhost:7777',
                changeOrigin: true,
                secure: false, // 忽略 https 证书校验
                rewrite: (path) => {
                    return path.replace(/\/api/, '')
                }
            },
            '/sse': {
                target: 'http://localhost:7777',
                changeOrigin: true,
                secure: false, // 忽略 https 证书校验
                pathRewrite: { '^/sse': '/sse' }
            },
        }
    }

})
