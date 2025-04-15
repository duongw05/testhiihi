import locale from 'element-plus/dist/locale/vi.mjs'
import {dashboard} from "@/locale/modules/dashboard/dashboard_vn";
import {settings} from "@/locale/modules/settings/settings_vn";
import {permissionManage} from "@/locale/modules/permissionManage/permissionManage_vn";

const lang = {
    el: locale.el, // element-plus i18 setting
    message: {
        language: 'Tiếng Việt',
        common: {
            delConfirm: 'Bạn có muốn xoá bản ghi này không',
            success: 'Thành công',
            err: 'Thất bại',
            advancedSearch: 'Tìm kiếm nâng cao',
            searchInfo: 'Thông tin tìm kiếm',
            search: 'Tìm kiếm',
            searchTip: 'Vui lòng nhập từ khóa',
            add: 'Thêm mới',
            update: 'Cập nhật',
            del: 'Xóa',
            view: 'Xem chi tiết',
            delName: 'Xóa {name}',
            delBat: 'Xóa chọn',
            delTip: 'Bạn có chắc chắn xóa dữ liệu đã chọn không?',
            handle: 'xử lý',
            importData: 'Nhập dữ liệu chấm công',
            exportExcel:'Xuất dữ liệu chấm công',
            exportExcelTip:'please input file name',
            login: 'Đăng nhập thành công',
            placeholderAll: 'Tất cả',
            reEnter: 'Nhập lại',
            export: 'Xuất báo cáo',
            searchResult: 'Kết quả tìm kiếm',
            chooseValue: 'Chọn giá trị',
            close: 'Đóng',
            authorize: 'Phân quyền',
            createInfo: 'Thêm mới {name}',
            updateInfo: 'Cập nhật {name} ',
            editInfo: 'Sửa {name}',
            viewInfo: 'Xem chi tiết {name}',
            addSuccess:'Thêm mới {name} thành công',
            updateSuccess: 'Cập nhật {name} thành công',
            errServer: 'Lỗi hệ thống, vui lòng thử lại sau',
            placeholder: 'Nhập {name}',
            searchResults: 'Kết quả tìm kiếm',
            actions: 'Hành động',
            checkSearchConditions: 'Vui lòng kiểm tra điều kiện tìm kiếm',
            fetchDataError: "Lỗi khi lấy dữ liệu",
            status: {
                active: 'Hiệu lực',
                inactive: 'Hết hiệu lực',
            },
            date: {
                start: 'Ngày bắt đầu',
                end: 'Ngày kết thúc',
                to: 'Đến',
            },
        },
        menu: {
            ...dashboard,
            ...settings,
            ...permissionManage,
            system: {
                name: 'Hệ thống',
                redirect: 'redirect',
                '404': '404',
                '401': '401'
            },
            component: {
                name: 'component',
                button: 'button',
                wordEditor: 'wordEditor',
                mdEditor: 'mdEditor',
                codeEditor: 'codeEditor',
                jsonEditor: 'jsonEditor',
                dragPane: 'dragPane',
                map: 'map',
                cutPhoto: 'cutPhoto',
                rightMenu: 'rightMenu',
                exportExcel:'exportExcel'
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
                name: 'echarts',
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
            welcome: 'Chào mừng đến trang đăng nhập',
            login: 'Đăng Nhập',
            userName: 'Tài khoản',
            password: 'Mật khẩu',
            newPassword: 'Mật khẩu mới',
            confirmPassword: 'Xác nhận lại mật khẩu',
            userNameValidate: 'Tài khoản không được để trống',
            passwordValidate: 'Mật khẩu không được để trống',
            newPasswordValidate: 'Mật khẩu mới không được để trống',
            confPasswordValidate: 'Xác nhận mật khẩu không được để trống',
            captchaValidate: 'Captcha không được để trống',
            captchaCheck: 'Captcha không hợp lệ',
            checkConfPassword: 'Xác nhận mật khẩu không hợp lệ',
            contentScreen: 'content full screen',
            fullScreen: 'fullscreen',
            fullScreenBack: 'back fullscreen',
            github: 'visit github',
            changePassword: 'Đổi mật khẩu',
            forgotPassword: 'Quên mật khẩu',
            captcha: 'Nhập mã captcha',
            loginOut: 'Đăng xuất',
            user: 'admin',
            oops: 'Trang không tồn tại !!!',
            oops_info: 'Vui lòng kiểm tra xem URL bạn đã nhập có chính xác không hoặc nhấp vào nút bên dưới để quay lại trang chủ',
            oops_go: 'Chuyển đến trang chủ',
            size: {
                default: 'default',
                large: 'large',
                small: 'small',
            },
            setting: {
                name: 'Cài đặt',
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
                reload: 'Làm mới',
                closeAll: 'Đóng tất cả các trang',
                closeOther: 'Đóng tất cả trang khác',
                closeCurrent: 'Đóng trang hiện tại'
            }
        },
    },
    validate: {
        required: '{name} không được để trống',
        max: '{name} không lớn hơn {value} ký tự',
        min: '{name} phải nhỏ hơn {value} ký tự',
        regex: '{name} không đúng định dạng'
    }
}

export default lang
