import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// 正式环境部署路径，如果你部署在子目录下，记得设置 base
// 比如部署在 https://www.xxx.com/myapp/，base 就写成 '/myapp/'
// 否则默认 '/' 即可
export default defineConfig({
  plugins: [vue()],
  base: '/', // 如果部署在根路径可以保持为 '/'，部署到子目录改为 '/your-path/'
  build: {
    outDir: 'dist',      // 打包输出目录
    assetsDir: 'assets', // 静态资源目录
    sourcemap: false,    // 是否生成 source map，生产建议关闭
    minify: 'terser',    // 使用 terser 压缩（默认 esbuild 更快，但 terser 更细致）
    terserOptions: {
      compress: {
        drop_console: true,  // 移除 console.
        drop_debugger: true  // 移除 debugger
      }
    }
  },
  server: {
    host: '127.0.0.1',
    //host: '192.168.1.149',
    //host: 'cfh5.ceshias.cc',
    port: 8080,
    open: true,
    proxy: {
      '/api': {
        target: 'https://cfapi.ceshias.cc/api/', // 注意：这里 target 包含了 /api
        changeOrigin: true,
        rewrite: path => path.replace(/^\/api/, '') // 去掉本地请求的 /api，避免变成 /api/api
      }
    }
  },
  css: {
    postcss: './postcss.config.js', // 明确指明配置文件路径
  },
})
