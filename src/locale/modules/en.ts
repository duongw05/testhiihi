import locale from 'element-plus/dist/locale/en.mjs'
import {dashboard} from "@/locale/modules/dashboard/dashboard_en";
import {settings} from "@/locale/modules/settings/settings_en";
import {permissionManage} from "@/locale/modules/permissionManage/permissionManage_en";
import {schoolManage} from "@/locale/modules/schoolManage/schoolManage_en";

const lang = {
    el: locale.el, // element-plus i18 setting
    message: {
        language: 'English',
        menu: {
            ...dashboard,
            ...settings,
            ...permissionManage,
            ...schoolManage,
            system: {
                name: 'System', // Capitalized for consistency
                redirect: 'Redirect', // Capitalized
                '404': '404',
                '401': '401'
            },
            component: {
                name: 'Component',
                button: 'Button', // Capitalized
                wordEditor: 'Word Editor', // Capitalized
                mdEditor: 'MD Editor', // Capitalized
                codeEditor: 'Code Editor', // Capitalized
                jsonEditor: 'JSON Editor', // Capitalized
                dragPane: 'Drag Pane', // Capitalized
                map: 'Map', // Capitalized
                cutPhoto: 'Cut Photo', // Capitalized
                rightMenu: 'Right Click Menu', // More descriptive
                exportExcel: 'Export Excel'
            },
            page: {
                name: 'Page', // Capitalized
                crudTable: 'CRUD Table', // Capitalized
                categoryTable: 'Category Table', // Capitalized
                treeTable: 'Tree Table', // Capitalized
                card: 'Card', // Capitalized
                work: 'Work', // Capitalized
                baidu: 'Iframe Case', // Capitalized
                jump: 'Open in New Window' // Capitalized
            },
            // Giữ nguyên menu nếu bạn muốn nó là một phần của menu chính
            // Nếu không, có thể xóa hoặc di chuyển nó
            menu: {
                name: 'Menu',
                menu_1: 'Menu 1',
                menu_1_1: 'Menu 1-1',
                menu_1_1_1: 'Menu 1-1-1',
                menu_1_1_2: 'Menu 1-1-2',
                menu_1_2: 'Menu 1-2',
                menu_2: 'Menu 2',
                menu_3: 'Menu 3'
            },
            directive: {
                name: 'Directive', // Capitalized
                dragable: 'Draggable', // Corrected spelling
                copy: 'Copy', // Capitalized
                waterMarker: 'Watermark', // More common term
                longpress: 'Long Press', // Capitalized
                debounce: 'Debounce', // Capitalized
                scroll: 'Infinite Scroll', // More descriptive
                clickOutside: 'Click Outside', // Capitalized
            },
            echarts: {
                name: 'Echarts',
                bar: 'Bar Chart', // More descriptive
                line: 'Line Chart',
                pie: 'Pie Chart',
                radar: 'Radar Chart',
                map: 'Map',
            },
            systemManage: {
                name: 'System Management', // More descriptive
                menu: 'Menu',
                role: 'Role',
                user: 'User'
            },
            print: {
                name: 'Print',
                jsPrint: 'Print in JS'
            },
            community: {
                name: 'Community',
                qq: 'QQ Group',
                site: 'Vue3 Resource'
            },
            document: {
                name: 'Document',
                intro: 'Introduction', // More descriptive
                function: 'Functionality', // More descriptive
                menu: 'DIY Route Menus', // Capitalized
                keepAlive: 'How to use KeepAlive', // Capitalized
                crud: 'CRUD Table and Form', // Capitalized
                theme: 'How to DIY Your Theme', // Capitalized
                systemfont: 'Icon in Project', // More descriptive
                api: 'API Document' // Capitalized
            },
            tab: {
                name: 'Tab',
            },
        },
        common: {
            // General buttons and actions
            delConfirm: 'Do you want to delete this record?',
            success: 'Success',
            err: 'Error',
            info: 'Information', // Added for general info messages
            add: 'Create',
            update: 'Update',
            del: 'Delete',
            view: 'View Details', // More descriptive
            handle: 'Handle',
            close: 'Close',
            authorize: 'Authorization',
            agree: 'Agree', // Added for confirm buttons
            cancel: 'Cancel', // Added for confirm buttons

            // --- START: Import/Export keys (MOVED HERE) ---
            importData: 'Import {name} Data', // For dialog title, takes {name} from config.title
            exportExcel: 'Export Excel',
            noFileSelected: 'Please select an Excel file.',
            dropFileHere: 'Drag file here or',
            clickToUpload: 'click to select an Excel file',
            importFileTip: 'Only Excel files (.xls, .xlsx) are accepted. Please select one file.',
            or: 'Or',
            downloadTemplateFile: 'Download Sample Data',
            uploading: 'Uploading...',
            upload: 'Upload',
            importErrorsFound: 'Errors found during import. Error file has been downloaded.', // General error for import with file
            importSuccess: 'Data imported successfully!',
            importFailed: 'Data import failed!',
            importFunctionNotConfigured: 'Import function is not configured!', // General notification
            downloadSuccess: 'Sample file downloaded successfully!',
            downloadFailed: 'Failed to download sample file!',

            dragOr: 'Drag the file here or',
            clickToChooseExcel: 'click to select an Excel file',
            uploadTip: 'Only Excel files (.xls, .xlsx) are accepted. Please choose one.',
            downloadTemplate: 'Download Sample Template',

            exportConfirm: 'Are you sure you want to export data to Excel?',
            exportTitle: 'Confirm Excel Export',
            exportSuccess: 'Excel exported successfully!',
            exportCanceled: 'Excel export operation cancelled.', // More descriptive
            exportFailed: 'Excel export failed!',
            exportNotConfigured: 'Export functionality is not configured.', // Added for notification
            closeConfirm: 'Are you sure you want to close?', // For closing dialogs
            // --- END: Import/Export keys ---

            // Search and table
            advancedSearch: 'Advanced Search',
            searchInfo: 'Search Information', // More descriptive
            search: 'Search',
            searchTip: 'Please input keyword',
            reEnter: 'Reset', // More common term for re-enter form
            export: 'Export Report', // For general export button
            searchResult: 'Search Results', // Consistent capitalization
            searchResults: 'Search Results', // Consistent capitalization
            checkSearchConditions: 'Please check the search conditions',
            fetchDataError: 'Failed to fetch data',
            stt: 'No.', // For sequential number column
            actions: 'Actions',

            // Popup/Form specific
            chooseValue: 'Select Value',
            createInfo: 'Create {name}',
            updateInfo: 'Update {name}',
            viewInfo: 'View {name} Details', // More descriptive
            addSuccess: 'Created {name} successfully', // Corrected grammar
            updateSuccess: 'Updated {name} successfully', // Corrected grammar
            delName: 'Delete {name}', // Renamed to avoid confusion with delConfirm
            delBat: 'Delete Selected', // More descriptive
            delTip: 'Are you sure you want to delete the selected data?',
            deleteFailed: 'Deletion failed!', // Added for delete error
            errServer: 'System error, please try again later',
            placeholder: 'Enter {name}',
            placeholderAll: 'All',

            // Status
            status: {
                label: 'Status',
                active: 'Active',
                inactive: 'Inactive',
            },
            date: {
                start: 'Start Date',
                end: 'End Date',
                to: 'To',
            },
        },
        system: {
            title: 'Language Link',
            subTitle: 'A few lines to write beautiful admin', // Corrected grammar
            welcome: 'Welcome Login', // More natural
            login: 'Login',
            userName: 'Username', // More common term
            password: 'Password',
            newPassword: 'New Password',
            confirmPassword: 'Confirm Password',
            userNameValidate: 'Username cannot be empty', // More natural
            passwordValidate: 'Password cannot be empty', // More natural
            newPasswordValidate: 'New password cannot be empty', // More natural
            confPasswordValidate: 'Confirm password cannot be empty', // More natural
            captchaValidate: 'Captcha cannot be empty', // More natural
            captchaCheck: 'Invalid Captcha',
            checkConfPassword: 'Invalid password confirmation',
            contentScreen: 'Content Full Screen', // Capitalized
            fullScreen: 'Fullscreen',
            fullScreenBack: 'Exit Fullscreen', // More natural
            github: 'Visit GitHub', // Capitalized
            changePassword: 'Change Password',
            forgotPassword: 'Forgot Password',
            loginOut: 'Logout', // More common term
            user: 'Admin', // Capitalized
            oops: 'Page Not Found !!!', // More natural
            oops_info: 'Please check whether the URL you entered is correct, or click the button below to return to the homepage',
            oops_go: 'Go to Homepage', // More natural
            size: {
                default: 'Default', // Capitalized
                large: 'Large', // Capitalized
                small: 'Small', // Capitalized
            },
            setting: {
                name: 'Settings', // More common term
                style: {
                    name: 'Full Style Settings', // More descriptive
                    default: 'Default Menu Style', // Capitalized
                    light: 'Light Menu Style', // Capitalized
                    chinese: 'Chinese Menu Style', // Capitalized
                    dark: 'Dark Menu Style' // Capitalized
                },
                primaryColor: {
                    name: 'Primary Color', // Capitalized
                    blue: 'Default Blue', // Capitalized
                    red: 'Rose Red', // Capitalized
                    violet: 'Grace Violet', // Capitalized
                    green: 'Story Green', // Capitalized
                    cyan: 'Cyan', // Capitalized
                    black: 'Geek Black' // Capitalized
                },
                other: {
                    name: 'Other Settings', // Capitalized
                    showLogo: 'Show Logo', // Capitalized
                    showBreadcrumb: 'Show Breadcrumb', // Capitalized
                    keepOnlyOneMenu: 'Keep Only One Menu Open', // Capitalized
                }
            },
            tab: {
                reload: 'Refresh', // More common term
                closeAll: 'Close All Tabs', // More descriptive
                closeOther: 'Close Other Tabs', // More descriptive
                closeCurrent: 'Close Current Tab' // More descriptive
            }
        },
    },
    validate: {
        required: '{name} is required',
        max: '{name} cannot exceed {value} characters', // Corrected grammar
        min: '{name} must be at least {value} characters', // Corrected grammar (assuming min length)
        regex: '{name} is invalid', // More natural
        staff: {
            validateAge: '{}' // This still needs specific content
        }
    }
}

export default lang