import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { AntDesignVueResolver } from 'unplugin-vue-components/resolvers'
import path from 'path'

export default defineConfig({
  plugins: [
    vue(),
    AutoImport({
      imports: ['vue', 'vue-router', 'pinia'],
      dts: 'dist/auto-imports.d.ts'
    }),
    Components({
      resolvers: [
        AntDesignVueResolver({
            importStyle: false
        })
      ],
      dts: 'dist/components.d.ts'
    })
  ],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src') ,
      '@design':  path.resolve(__dirname, 'design') 
    }
  },
   base: './',
  server: {
    port: 3000,
    proxy: { 
        
       '^/org/[^/]+/data': {
          target: 'http://localhost:8080',
          changeOrigin: true 
       },
        '^/org/[^/]+/file': {
          target: 'http://localhost:8080',
          changeOrigin: true 
       },
       '/api': {
          target: 'http://localhost:8080/api/',
          changeOrigin: true,
             rewrite: (path) => path.replace(/^\/api/, '')
       }, 

       
       '/DeviceWeb/web/': {
            target: 'http://192.168.10.104:8733/DeviceWeb/web/', 
            changeOrigin: true,
            rewrite: (path) => path.replace(/^\/DeviceWeb\/web/, '')
       } ,
        '/ws': {
            target: 'ws://localhost:8080',
            ws: true, // 关键：开启 WebSocket 代理
         },
    }
  },
  build: { 
    outDir: path.resolve(__dirname, '../resources/static-vue'), 
    emptyOutDir: true, 
    assetsDir: 'assets',
    sourcemap: false,
    chunkSizeWarningLimit: 1500,
    rollupOptions: {
      input: {
          main: path.resolve(__dirname, 'index.html')  
      },
      output: {
        manualChunks: {
            'ant-design-vue': ['ant-design-vue'],
            'echarts': ['echarts'],
            'vendor': ['vue', 'vue-router', 'pinia', 'axios']
        }
      }
    }
  }
})
