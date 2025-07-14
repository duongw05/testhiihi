import locale from 'element-plus/dist/locale/vi.mjs'; // Giữ nguyên dòng này nếu bạn đang import Element Plus locale
import {dashboard} from "@/locale/modules/dashboard/dashboard_vn";
import {settings} from "@/locale/modules/settings/settings_vn";
import {permissionManage} from "@/locale/modules/permissionManage/permissionManage_vn";
import {schoolManage} from "@/locale/modules/schoolManage/schoolManage_vn";

const lang = {
    el: locale.el, // element-plus i18 setting
    message: {
        language: 'Tiếng Việt',
        menu: {
            ...dashboard,
            ...settings,
            ...permissionManage,
            ...schoolManage, // Đảm bảo schoolManage đã được import và thêm vào đây
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
        common: {
            // Các nút và hành động chung
            delConfirm: 'Bạn có muốn xoá bản ghi này không',
            success: 'Thành công',
            err: 'Thất bại',
            info: 'Thông báo', // Thêm thông báo chung
            add: 'Thêm mới',
            update: 'Cập nhật',
            del: 'Xóa',
            view: 'Xem chi tiết',
            handle: 'Xử lý',
            close: 'Đóng',
            authorize: 'Phân quyền',
            agree: 'Đồng ý',
            cancel: 'Hủy',

            // Nút và chức năng Import/Export
            exportExcel: 'Xuất Excel', // Thay đổi từ 'Xuất dữ liệu chấm công' cho mục đích chung
            exportExcelTip: 'Vui lòng nhập tên file',
            importConfirm: 'Bạn có chắc chắn muốn nhập dữ liệu không? Thao tác này có thể thay đổi dữ liệu hiện có.',
            importTitle: 'Xác nhận nhập dữ liệu',
            importSuccess: 'Nhập dữ liệu thành công!',
            importCanceled: 'Đã hủy thao tác nhập dữ liệu.',
            importFailed: 'Nhập dữ liệu thất bại!',
            importNotConfigured: 'Chức năng Import chưa được cấu hình.', // Thêm thông báo
            exportConfirm: 'Bạn có chắc chắn muốn xuất dữ liệu ra Excel không?',
            exportTitle: 'Xác nhận xuất Excel',
            exportSuccess: 'Xuất Excel thành công!',
            exportCanceled: 'Đã hủy thao tác xuất Excel.',
            exportFailed: 'Xuất Excel thất bại!',
            exportNotConfigured: 'Chức năng Xuất Excel chưa được cấu hình.', // Thêm thông báo

            importFileRequired: 'Vui lòng chọn file Excel.',
            invalidImportData: 'Dữ liệu không hợp lệ. Vui lòng kiểm tra.',
            importErrorFileDownloaded: 'Có lỗi trong quá trình nhập. File lỗi đã được tải xuống.',
            downloadTemplateSuccess: 'Tải file mẫu thành công!',
            downloadTemplateFailed: 'Không thể tải file mẫu!',
            warning: 'Cảnh báo',

            importData: 'Nhập dữ liệu {name}',
            dragOr: 'Kéo file vào đây hoặc',
            clickToChooseExcel: 'nhấn để chọn file Excel',
            uploadTip: 'Chỉ chấp nhận file Excel (.xls, .xlsx). Vui lòng chọn một file.',
            or: 'Hoặc',
            downloadTemplate: 'Tải dữ liệu mẫu',
            upload: 'Tải lên',
            uploading: 'Đang tải lên...',

            // Tìm kiếm và bảng
            advancedSearch: 'Tìm kiếm nâng cao',
            searchInfo: 'Thông tin tìm kiếm',
            search: 'Tìm kiếm',
            searchTip: 'Vui lòng nhập từ khóa',
            reEnter: 'Nhập lại',
            export: 'Xuất báo cáo',
            searchResult: 'Kết quả tìm kiếm',
            searchResults: 'Kết quả tìm kiếm',
            checkSearchConditions: 'Vui lòng kiểm tra điều kiện tìm kiếm',
            fetchDataError: 'Lỗi khi lấy dữ liệu',
            stt: 'STT', // Thêm cho cột STT
            actions: 'Hành động',

            // Popup/Form
            chooseValue: 'Chọn giá trị',
            createInfo: 'Thêm mới {name}',
            updateInfo: 'Cập nhật {name}',
            editInfo: 'Sửa {name}',
            viewInfo: 'Xem chi tiết {name}',
            addSuccess: 'Thêm mới {name} thành công',
            updateSuccess: 'Cập nhật {name} thành công',
            delName: 'Xóa {name}',
            delBat: 'Xóa chọn',
            delTip: 'Bạn có chắc chắn xóa dữ liệu đã chọn không?',
            deleteFailed: 'Xóa thất bại!', // Thêm thông báo lỗi xóa
            errServer: 'Lỗi hệ thống, vui lòng thử lại sau',
            placeholder: 'Nhập {name}',
            placeholderAll: 'Tất cả',


            // Các trường thông tin chung (ví dụ từ SchoolManagement)
            schoolCode: 'Mã trường học',
            schoolName: 'Tên trường học',
            schoolTypeId: 'Cấp học',
            workingOffsite: 'Working Offsite',
            areaCvct: 'Khu vực theo CVCT',
            programs: 'Chương trình',
            math: 'Toán', // Thêm
            science: 'Khoa học', // Thêm
            isPremium: 'Premium School', // Thêm
            yes: 'Có', // Thêm
            no: 'Không', // Thêm
            mapLink: 'Link bản đồ', // Thêm
            areaPhuong: 'Khu vực (Phường)', // Thêm
            address: 'Địa chỉ', // Thêm
            minuteNum: 'Số phút chấm công/Số phút thực tế', // Thêm
            startYear: 'Năm học bắt đầu hợp tác', // Thêm
            description: 'Mô tả', // Thêm
            schoolDiagram: 'Sơ đồ trường học', // Thêm


            // Trạng thái
            status: {
                label: 'Trạng thái', // Đổi key từ status sang status.label
                active: 'Hiệu lực',
                inactive: 'Hết hiệu lực',
            },
            date: {
                start: 'Ngày bắt đầu',
                end: 'Ngày kết thúc',
                to: 'Đến',
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

export default lang;