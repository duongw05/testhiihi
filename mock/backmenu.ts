/*
 * @Date: 2023-03-10 19:57:44
 * @Description:
 */
import { MockMethod } from 'vite-plugin-mock'

export default [
  {
    url: `/mock/menu/list`,
    method: 'post',
    response: () => {
      return {
        code: 200,
        data: {
          list: [
            {
              "path": "/",
              "redirect": "/dashboard",
              "meta": {
                "title": "dashboard",
                "icon": "sfont system-home"
              },
              "children": [
                {
                  "path": "dashboard",
                  "meta": {
                    "title": "首页",
                    "icon": "sfont system-home",
                    "hideClose": true
                  },
                  "component": "dashboard_dashboard"
                }
              ]
            },
            {
              "path": "/staffManage",
              "redirect": "/staffManage",
              "meta": {
                "title": "Quản lý nhân viên",
                "icon": "sfont system-home"
              },
              "children": [
                {
                  "path": "dashboard",
                  "meta": {
                    "title": "首页",
                    "icon": "sfont system-home",
                    "hideClose": true
                  },
                  "component": "dashboard_dashboard"
                }
              ]
            },
            {
              "path": "/component",
              "redirect": "/component/table",
              "meta": {
                "title": "组件",
                "icon": "sfont system-component"
              },
              "alwayShow": true,
              "children": [
                {
                  "path": "button",
                  "meta": {
                    "title": "按钮"
                  },
                  "component": "component_button"
                },
                {
                  "path": "wordEditor",
                  "meta": {
                    "title": "文本编辑器"
                  },
                  "component": "component_wordEditor"
                },
                {
                  "path": "mdEditor",
                  "meta": {
                    "title": "md编辑器"
                  },
                  "component": "component_mdEditor"
                },
                {
                  "path": "codeEditor",
                  "meta": {
                    "title": "代码编辑器"
                  },
                  "component": "component_codeEditor"
                },
                {
                  "path": "jsonEditor",
                  "meta": {
                    "title": "JSON编辑器"
                  },
                  "component": "component_jsonEditor"
                },
                {
                  "path": "dragPane",
                  "meta": {
                    "title": "可拖拽面板"
                  },
                  "component": "component_dragPane"
                },
                {
                  "path": "map",
                  "meta": {
                    "title": "地图组件"
                  },
                  "component": "component_map"
                },
                {
                  "path": "cutPhoto",
                  "meta": {
                    "title": "弹窗图片裁剪"
                  },
                  "component": "component_cutPhoto"
                },
                {
                  "path": "rightMenu",
                  "meta": {
                    "title": "右键菜单"
                  },
                  "component": "component_rightMenu"
                },
                {
                  "path": "exportExcel",
                  "meta": {
                    "title": "导出文件"
                  },
                  "component": "component_exportExcel"
                }
              ]
            },
            {
              "path": "/pages",
              "redirect": "/pages/crudTable",
              "meta": {
                "title": "页面",
                "icon": "sfont system-pageCard"
              },
              "alwayShow": true,
              "children": [
                {
                  "path": "crudTable",
                  "meta": {
                    "title": "业务表格",
                    "cache": false
                  },
                  "component": "pages_crudTable"
                },
                {
                  "path": "categoryTable",
                  "meta": {
                    "title": "分类联动表格",
                    "cache": true
                  },
                  "component": "pages_categoryTable"
                },
                {
                  "path": "treeTable",
                  "meta": {
                    "title": "树联动表格",
                    "cache": true
                  },
                  "component": "pages_treeTable"
                },
                {
                  "path": "card",
                  "meta": {
                    "title": "卡片列表",
                    "cache": true
                  },
                  "component": "pages_card"
                },
                {
                  "path": "work",
                  "meta": {
                    "title": "工作进度"
                  },
                  "component": "pages_work"
                },
                {
                  "path": "blog",
                  "meta": {
                    "title": "外部链接"
                  },
                  "component": "pages_blog"
                }
              ]
            },
            {
              "path": "/echarts",
              "redirect": "/echarts/dragable",
              "meta": {
                "title": "echarts图表",
                "icon": "sfont system-chart"
              },
              "children": [
                {
                  "path": "bar",
                  "meta": {
                    "title": "柱状图"
                  },
                  "component": "echarts_bar"
                },
                {
                  "path": "line",
                  "meta": {
                    "title": "折线图"
                  },
                  "component": "echarts_line"
                },
                {
                  "path": "pie",
                  "meta": {
                    "title": "饼图"
                  },
                  "component": "echarts_pie"
                },
                {
                  "path": "radar",
                  "meta": {
                    "title": "雷达图"
                  },
                  "component": "echarts_radar"
                },
                {
                  "path": "map",
                  "meta": {
                    "title": "地图"
                  },
                  "component": "echarts_map"
                }
              ]
            },
            {
              "path": "/systemManage",
              "redirect": "/systemManage/menu",
              "meta": {
                "title": "系统管理",
                "icon": "sfont system-xitongzhuangtai"
              },
              "alwayShow": true,
              "children": [
                {
                  "path": "menu",
                  "meta": {
                    "title": "菜单"
                  },
                  "component": "systemManage_menu"
                },
                {
                  "path": "role",
                  "meta": {
                    "title": "角色"
                  },
                  "component": "systemManage_role"
                },
                {
                  "path": "user",
                  "meta": {
                    "title": "用户"
                  },
                  "component": "systemManage_user"
                }
              ]
            },
            {
              "path": "/print",
              "redirect": "/print/js",
              "meta": {
                "title": "打印功能",
                "icon": "sfont system-24gl-printer"
              },
              "children": [
                {
                  "path": "js",
                  "meta": {
                    "title": "JS打印事件",
                    "icon": "sfont system-24gl-printer"
                  },
                  "component": "print_js"
                }
              ]
            },
            {
              "path": "/community",
              "redirect": "/community/qq",
              "meta": {
                "title": "社区的力量",
                "icon": "sfont system-shequ"
              },
              "children": [
                {
                  "path": "qq",
                  "meta": {
                    "title": "QQ交流群"
                  },
                  "component": "community_qq"
                },
                {
                  "path": "site",
                  "meta": {
                    "title": "vue3最新资源库"
                  },
                  "component": "community_site"
                }
              ]
            },
            {
              "path": "/tab",
              "redirect": "/tab/index",
              "meta": {
                "title": "打印功能",
                "icon": "sfont system-24gl-printer"
              },
              "children": [
                {
                  "path": "index",
                  "meta": {
                    "title": "tab事件公用",
                    "icon": "sfont system-24gl-printer"
                  },
                  "component": "tab_index"
                }
              ]
            }
          ]
        },
        msg: ''
      };
    }
  },
]
