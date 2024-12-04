import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import prismjsPlugin from "vite-plugin-prismjs";

// https://vite.dev/config/
export default defineConfig({
  plugins: [
      react(),
      prismjsPlugin({
        languages: ['java', 'javascript', 'css', 'html', 'json', 'sass', 'md', 'bash', 'shell', 'ts', 'json',],
        plugins: [
            'toolbar',
            'show-language',
            'copy-to-clipboard',
            'normalize-whitespace',
            'line-numbers',
            'unescaped-markup',
        ],
        theme: 'default',
      })
  ],
})
