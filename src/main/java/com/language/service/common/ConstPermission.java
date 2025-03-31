package com.language.service.common;

public class ConstPermission {

    public static final class CONFIG_LOCUS {
        public static final String ADD = "ADD_LOCUS";
        public static final String UPDATE = "UPDATE_LOCUS";
        public static final String DELETE = "DELETE_LOCUS";
        public static final String SEARCH = "SEARCH_LOCUS";
    }

    public static final class USER {
        public static final String ADD_USER = "ADD_USER";
        public static final String UPDATE_USER = "UPDATE_USER";
        public static final String DELETE_USER = "DELETE_USER";
        public static final String SEARCH_USER = "SEARCH_USER";
        public static final String CHANGE_PASS_USER = "CHANGE_PASS_USER";
    }

    public static final class ERRORS_MANAGEMENT {
        public static final String ADD_ERRORS = "ADD_ERRORS";
        public static final String UPDATE_ERRORS = "UPDATE_ERRORS";
        public static final String DELETE_ERRORS = "DELETE_ERRORS";
        public static final String SEARCH_ERRORS = "SEARCH_ERRORS";
    }

    public static final class CONFIG_MENU_PERMISSION {
        public static final String SEARCH_CONFIG_MENU_PER = "SEARCH_CONFIG_MENU_PER";
        public static final String SEARCH_CONFIG_DETAIL_MENU_PER = "SEARCH_CONFIG_DETAIL_MENU_PER";
        public static final String LINK_CONFIG_MENU = "LINK_CONFIG_MENU";
        public static final String LINK_CONFIG_PER = "LINK_CONFIG_PER";
    }

    public static final class CONFIG_PERMISSION {
        public static final String ADD = "ADD_PERMISSION";
        public static final String UPDATE = "UPDATE_PERMISSION";
        public static final String DELETE = "DELETE_PERMISSION";
        public static final String SEARCH = "SEARCH_CONFIG_PERMISSION";
    }

    public static final class GROUP_USER_MANAGEMENT {
        public static final String ADD_GROUP_USER = "ADD_GROUP_USER";
        public static final String UPDATE_GROUP_USER = "UPDATE_GROUP_USER";
        public static final String DELETE_GROUP_USER = "DELETE_GROUP_USER";
        public static final String SEARCH_GROUP_USER = "SEARCH_GROUP_USER";
    }


    public static final class ADN_INVES_REQ {
        public static final String CREATE = "CREATE_INVESTIGATION_REQUEST";
        public static final String SEARCH = "SEARCH_INVESTIGATION_REQUEST";
        public static final String VIEW = "VIEW_INVESTIGATION_REQUEST";
        public static final String UPDATE = "UPDATE_INVESTIGATION_REQUEST";
        public static final String DELETE = "DELETE_INVESTIGATION_REQUEST";
        public static final String FINDALLFILTER = "FINDALLFILTER_INVESTIGATION_REQUEST";
        public static final String EXPORT = "EXPORT_INVESTIGATION_REQUEST";
    }

    public static final class ADN_SUBJECT_PROVISIONAL {
        public static final String CREATE = "ADD_SUBJECT";
        public static final String SEARCH = "SEARCH_SUBJECT";
        public static final String VIEW = "VIEW_SUBJECT";
        public static final String UPDATE = "UPDATE_SUBJECT";
        public static final String DELETE = "DELETE_SUBJECT";
        public static final String PRINT = "PRINT_SUBJECT";
        public static final String PRINT_TEM_ADN_REQUEST = "PRINT_TEM_ADN_REQUEST";
        public static final String PRINT_BARCODE_ADN_REQUEST = "PRINT_BARCODE_ADN_REQUEST";
        public static final String EXPORT_REPORT_SUBJECT_PROVISIONAL = "EXPORT_REPORT_SUBJECT_PROVISIONAL";
        public static final String EXPORT = "EXPORT_EXCEL_ADN_SUBJECT_PROVISIONAL";
        public static final String IMPORT = "IMPORT_EXCEL_ADN_SUBJECT_PROVISIONAL";
        public static final String DOWNLOAD_FILE_ERROR = "DOWNLOAD_FILE_ERROR_ADN_SUBJECT_PROVISIONAL";

        public static final String SEARCH_INCIDENT_CASE_SUBJECT = "SEARCH_INCIDENT_CASE_SUBJECT";
        public static final String VIEW_INCIDENT_CASE_SUBJECT = "VIEW_INCIDENT_CASE_SUBJECT";
        public static final String ADD_INCIDENT_CASE_SUBJECT = "ADD_INCIDENT_CASE_SUBJECT";
    }

    public static final class ADN_STORE {
        public static final String EXPORT = "EXPORT_SUBJECT_STORE";
        public static final String CREATE = "ADD_SUBJECT_STORE";
        public static final String SEARCH = "SEARCH_SUBJECT_STORE";
        public static final String VIEW = "VIEW_SUBJECT_STORE";
        public static final String UPDATE = "UPDATE_SUBJECT_STORE";
        public static final String DELETE = "DELETE_SUBJECT_STORE";

    }

    public static final class MANAGER_REPORT {
        public static final String REPORT_NUMBER_SUBJECT_IN_STORE = "REPORT_NUMBER_SUBJECT_IN_STORE";
        public static final String REPORT_FLUCTUATION_SUBJECT_IN_STORE = "REPORT_FLUCTUATION_SUBJECT_IN_STORE";
        public static final String REPORT_SUBJECT_NO_GEN_IN_STORE = "REPORT_SUBJECT_NO_GEN_IN_STORE";
        public static final String REPORT_SUBJECT_GEN_IN_STORE = "REPORT_SUBJECT_GEN_IN_STORE";
    }

    public static final class ADN_REQUEST {
        public static final String CREATE = "CREATE_REQUEST";
        public static final String SEARCH = "SEARCH_REQUEST";
        public static final String VIEW = "VIEW_REQUEST";
        public static final String UPDATE = "UPDATE_REQUEST";
        public static final String DELETE = "DELETE_REQUEST";
        public static final String CONFIRM = "CONFIRM_REQUEST";
        public static final String CONFIRM_ADD_SUBJECT = "CONFIRM_ADD_TO_REQUEST";
        public static final String IMPORT = "IMPORT_EXCEL_ADN_REQUEST";
        public static final String EXPORT = "EXPORT_EXCEL_ADN_REQUEST";
    }

    public static final class MENUS_MANAGEMENT {
        public static final String ADD_MENUS = "ADD_MENUS";
        public static final String UPDATE_MENUS = "UPDATE_MENUS";
        public static final String DELETE_MENUS = "DELETE_MENUS";
        public static final String SEARCH_MENUS = "SEARCH_MENUS";
    }

    public static final class DM_STORE {
        public static final String ADD_DM_STORE = "ADD_DM_STORE";
        public static final String VIEW_DM_STORE = "VIEW_DM_STORE";
        public static final String UPDATE_DM_STORE = "UPDATE_DM_STORE";
        public static final String DELETE_DM_STORE = "DELETE_DM_STORE";
        public static final String SEARCH_DM_STORE = "SEARCH_DM_STORE";
        public static final String EXPORT_DM_STORE = "EXPORT_DM_STORE";
    }

    public static final class TEMPLATE_MANAGEMENT {
        public static final String ADD_TEMPLATE = "ADD_TEMPLATE";
        public static final String UPDATE_TEMPLATE = "UPDATE_TEMPLATE";
        public static final String DELETE_TEMPLATE = "DELETE_TEMPLATE";
        public static final String SEARCH_TEMPLATE = "SEARCH_TEMPLATE";
        public static final String DOWNLOAD_TEMPLATE = "DOWNLOAD_TEMPLATE";
    }
    public static final class WORK_LIST {
        public static final String SEARCH_WORK_LIST = "SEARCH_WORK_LIST";
        public static final String ADD_WORK_LIST = "ADD_WORK_LIST";
        public static final String EDIT_WORK_LIST = "EDIT_WORK_LIST";
        public static final String VIEW_WORK_LIST = "VIEW_WORK_LIST";
        public static final String IMPORT_WORK_LIST = "IMPORT_WORK_LIST";
        public static final String DELETE_WORK_LIST = "DELETE_WORK_LIST";

        public static final String EXPORT_FILE = "WORK_LIST_EXPORT_FILE";
        public static final String EXPORT_EXCEL_HAD_ADN = "EXPORT_EXCEL_HAD_ADN";  /// quyền xuất báo cáo đối tượng có adn
    }

    public static final class STORE_REQUEST {
        public static final String SEARCH = "SEARCH_STORE_REQUEST";
        public static final String ADD = "ADD_STORE_REQUEST";
        public static final String EDIT = "EDIT_STORE_REQUEST";
        public static final String VIEW = "VIEW_STORE_REQUEST";
        public static final String IMPORT = "IMPORT_STORE_REQUEST";
        public static final String EXPORT = "EXPORT_STORE_REQUEST";
        public static final String DELETE = "DELETE_STORE_REQUEST";
        public static final String CONFIRM_ADD = "CONFIRM_ADD_SUBJECT_STORE_REQUEST";
        public static final String CONFIRM_STORE_REQUEST = "CONFIRM_STORE_REQUEST";
        public static final String ADD_SUBJECT = "ADD_SUBJECT_STORE_REQUEST";
        public static final String EXPORT_DETAIL_ADN_STORE_REQUEST_IMPORT = "EXPORT_DETAIL_ADN_STORE_REQUEST_IMPORT";  // Quyền xuất chi tiết phiếu nhập kho
    }

    public static final class DM_VALUES {
        public static final String SEARCH_DM_VALUE = "SEARCH_DM_VALUE";
        public static final String ADD_DM_VALUE = "ADD_DM_VALUE";
        public static final String EDIT_DM_VALUE = "EDIT_DM_VALUE";
        public static final String VIEW_DM_VALUE = "VIEW_DM_VALUE";
        public static final String IMPORT_DM_VALUE = "IMPORT_DM_VALUE";
        public static final String DELETE_DM_VALUE = "DELETE_DM_VALUE";
        public static final String EXPORT_DM_VALUE = "EXPORT_DM_VALUE";
    }



    public static final class INVESTIGATION_MANAGEMENT {
        public static final String ADD_INVESTIGATION = "ADD_INVESTIGATION";
        public static final String UPDATE_INVESTIGATION = "UPDATE_INVESTIGATION";
        public static final String VIEW_INVESTIGATION = "VIEW_INVESTIGATION";
        public static final String DELETE_INVESTIGATION = "DELETE_INVESTIGATION";
        public static final String SEARCH_INVESTIGATION = "SEARCH_INVESTIGATION";
    }

    public static final class DM_AREA {
        public static final String ADD_DM_AREA = "ADD_DM_AREA";
        public static final String VIEW_DM_AREA = "VIEW_DM_AREA";
        public static final String UPDATE_DM_AREA = "UPDATE_DM_AREA";
        public static final String DELETE_DM_AREA = "DELETE_DM_AREA";
        public static final String SEARCH_DM_AREA = "SEARCH_DM_AREA";
        public static final String EXPORT_DM_AREA = "EXPORT_DM_AREA";
    }


    public static final class STORE_REQUEST_EXPORT {
        public static final String SEARCH_STORE_REQUEST_EXPORT = "SEARCH_STORE_REQUEST_EXPORT";
        public static final String DELETE_STORE_REQUEST_EXPORT = "DELETE_STORE_REQUEST_EXPORT";
        public static final String ADD_STORE_REQUEST_EXPORT = "ADD_STORE_REQUEST_EXPORT";
        public static final String EXPORT_STORE_REQUEST_EXPORT = "EXPORT_STORE_REQUEST_EXPORT";
        public static final String UPDATE_STORE_REQUEST_EXPORT = "UPDATE_STORE_REQUEST_EXPORT";
        public static final String APPROVE_STORE_REQUEST_EXPORT = "APPROVE_STORE_REQUEST_EXPORT";
        public static final String SEARCH_DETAIL_STORE_REQUEST_EXPORT = "SEARCH_DETAIL_STORE_REQUEST_EXPORT";
    }

    public static final class DM_ALLELE_ANCESTRY_RATE {
        public static final String ADD = "ADD_ALLELE_ANCESTRY_RATE";
        public static final String UPDATE = "UPDATE_ALLELE_ANCESTRY_RATE";
        public static final String DELETE = "DELETE_ALLELE_ANCESTRY_RATE";
        public static final String SEARCH = "SEARCH_ALLELE_ANCESTRY_RATE";
    }

    public static final class DNA_SUBJECT_STORAGE {
        public static final String SEARCH_ADN_INFO = "SEARCH_ADN_INFO";
        public static final String VIEW_ADN_INFO = "VIEW_ADN_INFO";
        public static final String EXPORT_ADN_INFO = "EXPORT_ADN_INFO";
    }

    public static final class ADN_SUBJECT_REVIEW_MAPPING {
        public static final String ADD_ADN_SUB_REVIEW = "ADD_ADN_SUB_REVIEW";
        public static final String SEARCH_ADN_SUB_REVIEW = "SEARCH_ADN_SUB_REVIEW";
//        public static final String VIEW_ADN_INFO = "VIEW_ADN_INFO";
    }

    /** Các quyền của Menu Danh sách đổi tuọng nghi trùng*/
    public static final class LIST_OF_ADN_SUBJECT_DUPLICATES {
        public static final String UPDATE = "UPDATE_ADN_SUBJECT_DUPLICATES";
        public static final String VIEW = "VIEW_ADN_SUBJECT_DUPLICATES";
        public static final String DELETE = "DELETE_ADN_SUBJECT_DUPLICATES";
        public static final String SEARCH = "SEARCH_ADN_SUBJECT_DUPLICATES";
        public static final String EXPORT = "EXPORT_ADN_SUBJECT_DUPLICATES";
    }

    public static final class BARCODE_PRINT_SESSION {
        public static final String VIEW = "VIEW_BARCODE_PRINT_SESSION";
        public static final String SEARCH = "SEARCH_BARCODE_PRINT_SESSION";
        public static final String EXPORT = "EXPORT_BARCODE_PRINT_SESSION";
    }

    public static final class INCIDENT_CASE {
        public static final String SEARCH_INCIDENT_CASE = "SEARCH_INCIDENT_CASE";
        public static final String ADD_INCIDENT_CASE = "ADD_INCIDENT_CASE";
        public static final String EDIT_INCIDENT_CASE = "EDIT_INCIDENT_CASE";
        public static final String DELETE_INCIDENT_CASE = "DELETE_INCIDENT_CASE";
        public static final String PROCESS_TESTING_INCIDENT_CASE = "PROCESS_TESTING_INCIDENT_CASE";
        public static final String VIEW_INCIDENT_CASE = "VIEW_INCIDENT_CASE";
        public static final String APPROVE_INCIDENT_CASE = "APPROVE_INCIDENT_CASE";
        public static final String REJECT_INCIDENT_CASE = "REJECT_INCIDENT_CASE";
        public static final String TEST_INCIDENT_CASE = "TEST_INCIDENT_CASE";
        public static final String IMPORT_GEN_INCIDENT_CASE = "IMPORT_GEN_INCIDENT_CASE";
        public static final String DETAIL_ADN_SAMPLE = "DETAIL_ADN_SAMPLE";
    }

    public static final class DEVICE_TEMPLATE_MANAGEMENT {
        public static final String ADD_DEVICES = "ADD_DEVICES";
        public static final String EDIT_DEVICES = "EDIT_DEVICES";
        public static final String DELETE_DEVICES = "DELETE_DEVICES";
        public static final String SEARCH_DEVICES = "SEARCH_DEVICES";
        public static final String DOWNLOAD_DEVICE_TEMPLATE = "DOWNLOAD_DEVICE_TEMPLATE";
        public static final String VIEW_DEVICES = "VIEW_DEVICES";
    }
}
