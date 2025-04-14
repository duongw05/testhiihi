import locale from 'element-plus/dist/locale/en.mjs'
import {dashboard} from "@/locale/modules/dashboard/dashboard_en";
import {settings} from "@/locale/modules/settings/settings_en";
import {permissionManage} from "@/locale/modules/permissionManage/permissionManage_en";

const lang = {
    el: locale.el, // element-plus i18 setting
    message: {
        language: 'English',
        common: {
            delConfirm: 'Do you want to delete this record?',
            success: 'Success',
            err: 'Error',
            advancedSearch: 'Advanced Search',
            searchInfo: 'Search Info',
            search: 'Search',
            searchTip: 'Please input keyword',
            add: 'Create',
            update: 'Update',
            del: 'Delete',
            view: 'View',
            delName: 'Delete {name}',
            delBat: 'Delete choose',
            delTip: 'Are you sure delete the selection data ?',
            handle: 'Handle',
            importData: 'Import Data',
            exportExcel: 'Export Excel',
            exportExcelTip: 'please input file name',
            login: 'Login successful',
            placeholderAll: 'All item',
            reEnter: 'Re-enter',
            export: 'Export report',
            searchResult: 'Search results',
            chooseValue: 'Choose Value',
            close: 'Close',
            authorize: 'Authorization',
            createInfo: 'Create {name}',
            updateInfo: 'Update {name}',
            viewInfo: 'View {name}',
            addSuccess: 'Create {name} successfully',
            updateSuccess: 'Update {name} successfully',
            errServer: 'System error, please try again later',
            placeholder: 'Input {name}',
            searchResults: 'Search results',
            actions: 'Actions',
            status: {
                active: 'Active',
                inactive: 'Inactive',
            }
        },
        menu: {
            ...dashboard,
            ...settings,
            ...permissionManage,
            system: {
                name: 'system',
                redirect: 'redirect',
                '404': '404',
                '401': '401'
            },
            component: {
                name: 'Component',
                button: 'button',
                wordEditor: 'wordEditor',
                mdEditor: 'mdEditor',
                codeEditor: 'codeEditor',
                jsonEditor: 'jsonEditor',
                dragPane: 'dragPane',
                map: 'map',
                cutPhoto: 'cutPhoto',
                rightMenu: 'rightMenu',
                exportExcel: 'exportExcel'
            },
            page: {
                name: 'page',
                crudTable: 'crudTable',
                categoryTable: 'categoryTable',
                treeTable: 'treeTable',
                card: 'card',
                work: 'work',
                baidu: 'iframe case',
                jump: 'open in new window'
            },
            menu: {
                name: 'menu',
                menu_1: 'menu-1',
                menu_1_1: 'menu-1-1',
                menu_1_1_1: 'menu-1-1-1',
                menu_1_1_2: 'menu-1-1-2',
                menu_1_2: 'menu-1-2',
                menu_2: 'menu-2',
                menu_3: 'menu-3'
            },
            directive: {
                name: 'directive',
                dragable: 'v-dragable',
                copy: 'v-copy',
                waterMarker: 'v-waterMarker',
                longpress: 'v-longpress',
                debounce: 'v-debounce',
                scroll: 'v-infinite-scroll',
                clickOutside: 'v-click-outside',
            },
            echarts: {
                name: 'Echarts',
                bar: 'bar',
                line: 'line chart',
                pie: 'pie chart',
                radar: 'radar chart',
                map: 'map',
            },
            systemManage: {
                name: 'systemManage',
                menu: 'menu',
                role: 'role',
                user: 'user'
            },
            print: {
                name: 'print',
                jsPrint: 'print in JS'
            },
            community: {
                name: 'community',
                qq: 'qqGroup',
                site: 'vue3 resource'
            },
            document: {
                name: 'document',
                intro: 'intro',
                function: 'function',
                menu: 'diy route menus',
                keepAlive: 'how to use keepAlive',
                crud: 'crud table and form',
                theme: 'how to diy your theme',
                systemfont: 'icon in project',
                api: 'api document'
            },
            tab: {
                name: 'tab',
            },
        },
        system: {
            title: 'Language Link',
            subTitle: 'few lines to write beautiful admin',
            welcome: 'welcome login',
            login: 'Login',
            userName: 'User Name',
            password: 'Password',
            newPassword: 'New Password',
            confirmPassword: 'Confirm Password',
            userNameValidate: 'UserName not empty',
            passwordValidate: 'Password not empty',
            newPasswordValidate: 'New password not empty',
            confPasswordValidate: 'Confirm password not empty',
            captchaValidate: 'Captcha not empty',
            captchaCheck: 'Invalid Captcha',
            checkConfPassword: 'Invalid password confirmation',
            contentScreen: 'content full screen',
            fullScreen: 'fullscreen',
            fullScreenBack: 'back fullscreen',
            github: 'Visit github',
            changePassword: 'Change password',
            forgotPassword: 'Forgot password',
            loginOut: 'login out',
            user: 'admin',
            oops: 'The page does not exist !!!',
            oops_info: 'Please check whether the URL you entered is correct, or click the button below to return to the homepage',
            oops_go: 'Jump to homepage',
            size: {
                default: 'default',
                large: 'large',
                small: 'small',
            },
            setting: {
                name: 'setting',
                style: {
                    name: 'full style setting',
                    default: 'default menu style',
                    light: 'light menu style',
                    chinese: 'chinese menu style',
                    dark: 'dark menu style'
                },
                primaryColor: {
                    name: 'primary color',
                    blue: 'default blue',
                    red: 'rose red',
                    violet: 'grace violet',
                    green: 'story green',
                    cyan: 'cyan',
                    black: 'geek black'
                },
                other: {
                    name: 'other setting',
                    showLogo: 'show logo',
                    showBreadcrumb: 'show breadcrumb',
                    keepOnlyOneMenu: 'keep only one menu open',
                }
            },
            tab: {
                reload: 'refresh',
                closeAll: 'close all tags',
                closeOther: 'close other tags',
                closeCurrent: 'close current tag'
            }
        },
    },
    validate: {
        required: '{name} is required',
        max: '{name} should be more than {value} characters',
        min: '{name} should be less than {value} characters',
        regex: '{name} invalidate',
        staff: {
            validateAge: '{}'
        }
    }
}

export default lang
