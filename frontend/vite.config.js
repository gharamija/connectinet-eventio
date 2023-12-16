import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// ovo se pokrece samo za lokalno testiranje (npm run dev)
export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000,
    proxy: {
      "/api": {
        target: "http://localhost:8080",
        changeOrigin: true,
      },
    },
  },
});
