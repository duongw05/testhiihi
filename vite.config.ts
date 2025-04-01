/*
 * @Author: luoxi
 * @Date: 2022-01-25 09:51:12
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2023-02-21 22:57:42
 * @FilePath: \vue-admin-box\vite.config.ts
 * @Description:
 */
import {ConfigEnv, loadEnv, UserConfigExport} from 'vite'
import vue from '@vitejs/plugin-vue'
// import {vitePluginSvg} from "@webxrd/vite-plugin-svg"
import {resolve} from 'path'

const pathResolve = (dir: string): any => {
  return resolve(__dirname, ".", dir)
}

const alias: Record<string, string> = {
  '@': pathResolve("src")
}

/**
 * @description-en vite document address
 * @description-cn vite官网
 * https://vitejs.cn/config/ */
export default ({ command }: ConfigEnv): UserConfigExport => {
  const prodMock = true;
  const env = loadEnv(command, process.cwd());
  const PORT = `${env.VITE_PORT ?? '3000'}`;
  return {
    base: '/',
    resolve: {
      alias
    },
    server: {
      port: Number(PORT),
    },
    build: {
      rollupOptions: {
        output: {
          manualChunks: {
            'echarts': ['echarts']
          }
        }
      }
    },
    plugins: [
      vue(),
      // vitePluginSvg({
      //   iconDirs: [
      //       resolve(__dirname, 'src/assets/svg'),
      //   ],
      //   main: resolve(__dirname, 'src/main.js'),
      //   symbolIdFormat: 'icon-[name]'
      // }),
    ],
    // define: {
    //   'global': {}
    // },
    css: {
      postcss: {
        plugins: [
            {
              postcssPlugin: 'internal:charset-removal',
              AtRule: {
                charset: (atRule) => {
                  if (atRule.name === 'charset') {
                    atRule.remove();
                  }
                }
              }
            }
        ],
      },
    }
  };
}
