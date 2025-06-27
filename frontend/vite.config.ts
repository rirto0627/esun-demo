import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from "path"
import tailwindcss from '@tailwindcss/vite'

export default defineConfig({
  plugins: [vue(), tailwindcss()],
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"),
    },
  },
  server: {
    host: '0.0.0.0',
    proxy: {
      '/api': {
        target: 'http://localhost:8080', // Spring Boot server
        changeOrigin: true,

      }
    }
  },
  build: {
    outDir: '../src/main/resources/static', // 打包輸出到 Spring Boot 的 static 資料夾
    emptyOutDir: true
  }
})
