package com.language.service.common;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;

public class Constants {
    public static final String SUCCESS = "SUCCESS";
    public static final String INTEGRATE_API = "/integrate/api/v1";
    public static final String INTERNAL_API = "/internal/api/v1";
    public static final String CLIENT_API = "/client/api/v1";
    public static final String LOGIN_URL = CLIENT_API + "/login";
    public static final String REFRESH_TOKEN_URL = CLIENT_API + "/refreshToken";
    public static final String TEMP_FOLDER = "temp";
    public static final String PUBLIC_FOLDER = "public";
    public static final String PRIVATE_FOLDER = "private";

    public static final String[] FILE_DEVICE_TEMPLATE_EXTEND = new String[]{"csv","txt"};
    public static final long FILE_DEVICE_TEMPLATE_SIZE_MAX = 2L * 1024L * 1024L; //file size theo bytes

    public static final String[] PNG_JPG = new String[]{"image/jpeg", "image/png"};

    public static final String DESCRIPTION = "description";
    public static final String CODE_UNAUTHORIZED = "UNAUTHORIZED";
    public static final String CODE_REFRESH_TOKEN_EXPIRED = "REFRESH_TOKEN_EXPIRED";
    public static final String CODE = "code";
    public static final String CLAIM_SCOPE = "scope";
    public static final String CLAIM_SCOPE_ACCESS = "access";
    public static final String CLAIM_SCOPE_REFRESH_TOKEN = "refresh";
    public static final String CLAIM_USERNAME = "username";
    public static final String CLAIM_USER_ID = "userId";
    public static final String CLAIM_INVESTIGATION_CODE = "investigationCode";
    public static final String CLAIM_INVESTIGATION_TYPE = "investigationType";
    public static final String DM_STEP = "DM_STEP";
    public static final String SOURCE_EXPORT = "SOURCE_EXPORT";
    public static final int MAX_SIZE_SUBJECT_IN_WORK_LIST = 90;
    public static final String SOURCE_OTHER = "SOURCE_OTHER";
    public static final String SOURCE_WORK_LIST = "SOURCE_WORK_LIST";
    public static final Integer APPROVE_INFO_REPLACE = 1;

    public static final class ACTION_AUDIT {
        public static final String ADD = "ADD";
        public static final String ASSIGN = "ASSIGN";
        public static final String UPDATE = "UPDATE";
        public static final String DELETE = "DELETE";
        public static final String CANCEL = "CANCEL";
        public static final String UN_ASSIGN = "UN_ASSIGN";
        public static final String APPROVE = "APPROVE";
        public static final String UPLOAD_EVD = "UPLOAD_EVD";
        public static final class TABLE {
            public static final String ADN_WORK_LIST = "ADN_WORK_LIST";
            public static final String ADN_REQUEST = "ADN_REQUEST";
            public static final String ADN_WORK_LIST_DETAIL = "ADN_WORK_LIST_DETAIL";
            public static final String ADN_STORE_REQUEST = "ADN_STORE_REQUEST";
            public static final String ADN_SUBJECT_PROVISIONAL = "ADN_SUBJECT_PROVISIONAL";
            public static final String ADN_STORE = "ADN_STORE";
            public static final String ADN_STORE_ATTRIBUTE_MAPPING = "ADN_STORE_ATTRIBUTE_MAPPING";
        }

    }

    public static final class STATUS {
        public static final int ACTIVE = 1;
        public static final int INACTIVE = 0;
    }
    public static final class INVESTIGATION_TYPE {
        public static final String DIA_PHUONG = "DIA_PHUONG";
        public static final String VIEN = "VIEN";
    }

    public static final class DELETE {
        public static final Integer ACTIVE = 1;
        public static final Integer INACTIVE = 0;
    }

    public static final class DELETE_BOOLEAN {
        public static final boolean ACTIVE = true;
        public static final boolean INACTIVE = false;
    }

    public static final class CACHE_KEY_NAME {
        public static final String CACHE_ME_PERMISSION_KEY = "CACHE_ME_PERMISSION";
        public static final String CACHE_PERMISSION_ALL_KEY = "CACHE_PERMISSION_ALL";
        public static final String CACHE_MENU_ALL_KEY = "CACHE_MENU_ALL_KEY";
        public static final String CACHE_EXPORT_ALL_KEY = "CACHE_EXPORT_ALL_KEY";
        public static final String CACHE_SYSTEM_ERROR_MANAGEMENT = "CACHE_SYSTEM_ERROR_MANAGEMENT_ALL";
        public static final String CACHE_DM_ATTRIBUTE_BY_MODEL_KEY = "CACHE_DM_ATTRIBUTE_BY_MODEL";
        public static final String CACHE_DM_VALUES_BY_MODEL_AND_ATTRIBUTE_KEY = "CACHE_DM_VALUES_BY_MODEL_AND_ATTRIBUTE";
        public static final String CACHE_SYSTEM_ADN_POSITION_MAPPING = "CACHE_SYSTEM_ADN_POSITION_MAPPING_ALL";
        public static final String CACHE_WORK_LIST = "CACHE_WORK_LIST";
        public static final String CACHE_ADD_SUBJECT = "CACHE_ADD_SUBJECT";
        public static final String CACHE_AREA_KEY = "CACHE_AREA_KEY";

    }

    public static final class STATUS_WORK {
        public static final int NEW = 1;
        public static final int PROCESS = 2;
        public static final int DONE = 3;
        public static final int DELETE = 4;
    }

    public static final class STEP {

        public static final String BSD_PUNCHING = "BSD_PUNCHING";
        public static final String SETUP_PCR = "SETUP_PCR";
        public static final String PCR_RUN = "PCR_RUN";
        public static final String SETUP_CE = "SETUP_CE";
        public static final String CE_RUN = "CE_RUN";
        public static final String GEN_MAPPER_RESULTS = "GEN_MAPPER_RESULTS";
    }


    public static final class STATUS_SUBJECT_PROVISIONAL {
        public static final Integer NEW = 1;
        public static final Integer RECEIVED = 2;

        public static final Integer DELETE = 0;
        public static final Integer COMPLETE = 3;
        public static final String NEW_STRING = "Mới tạo";
        public static final String RECEIVED_STRING = "Đã có mẫu";
    }

    public static class BAR_CODE_CFG {
        public static final String SUB_FOLDER = "BAR_CODE";
        public static final int POSTFIX_LENGTH = 6;
        public static final String PREFIX_SAMPLE_GROUP = "9";
        public static final String PREFIX_SAMPLE = "2";
        public static final String PREFIX_GROUP_STEP = "9";
        public static final String PREFIX_DM_STORE = "WH";
        public static final String PREFIX_BARCODE_SESSION = "BPS";
        public static final String PREFIX_SUITCASE = "3";
        public static final String PREFIX_PARCEL = "4";
        public static final String PREFIX_BARCODE_INCIDENT = "INC";
        public static final String PREFIX_BARCODE_INCIDENT_SAMPLE = "INCSA";
        public static final String PREFIX_BARCODE_INCIDENT_SUBJECT = "INCSU";
    }

    public static class BAR_CODE_TYPE {
        public static final String BARCODE_SUBJECT = "BARCODE_SUBJECT";
        public static final String BARCODE_WORK_LIST = "BARCODE_WORK_LIST";
        public static final String BARCODE_STEP = "BARCODE_STEP";
        public static final String BARCODE_DM_STORE = "BARCODE_STORE";
        public static final String BARCODE_STORE_RQ = "BARCODE_STORE_RQ";
        public static final String BARCODE_PRINT_SESSION = "BARCODE_PRINT_SESSION";
        public static final String BARCODE_SUITCASE = "BARCODE_SUITCASE";
        public static final String BARCODE_PARCEL = "BARCODE_PARCEL";
        public static final String BARCODE_INCIDENT = "BARCODE_INCIDENT";
        public static final String BARCODE_INCIDENT_SAMPLE = "BARCODE_INCIDENT_SAMPLE";
        public static final String BARCODE_INCIDENT_SUBJECT = "BARCODE_INCIDENT_SUBJECT";
    }

    public static class STATUS_SUBJECT_STORE {
        public static final int NEW = 1;
        public static final int DONE = 2;
    }

    public static class IMPORT_CFG {
        public static final String SUB_FOLDER = "ERROR";

    }

    public static final class TYPE_STORE_SUBJECT {
        public static final String REQUEST = "REQUEST";
        public static final String STORE_REQUEST = "STORE_REQUEST";
        public static final String EXPORT = "EXPORT";
        public static final String IMPORT = "IMPORT";
        public static final String SOURCE_WORK_LIST = "SOURCE_WORK_LIST";

    }


    public static final class REASON_EXPORT_STORE {
        public static final String REASON_TRANSFER_STORE = "REASON_TRANSFER_STORE";
        public static final String REASON_SEND_STORE = "REASON_SEND_STORE";
    }
    public static final class STATUS_STORE {
        public static final Integer EXPORTED = 2;
        public static final Integer IN_STORE = 1;
        public static final Integer DELETE = 0;

    }

    public static final class TYPE_APPROVAL_NOTE_EXPORT_STORE {
        public static final String APPROVE = "APPROVE";
        public static final String NOPE = "NOPE";
    }

    public static final class TYPE_DELETE_REQUEST {
        public static final String DELETE = "DELETE";
        public static final String DELETE_ALL = "DELETE_ALL";
        public static final String CANCEL = "CANCEL";
    }
    public static final class TYPE_STORE_REQUEST {
        public static final String IMPORT = "IMPORT";
        public static final String EXPORT = "EXPORT";
        public static final String SOURCE_WORK_LIST = "SOURCE_WORK_LIST";
        public static final class TYPE_STORE_UPDATE {
            public static final String CHANGE_STORE_EXPORT = "CHANGE_STORE_EXPORT";
        }
    }

    public static final class DM_MODEL {
        public static final String DM_LOCUS = "DM_LOCUS";
        public static final String MODEL_TYPE_1 = "1";
        public static final Integer MODEL_TYPE_0 = 0;
    }

    public static class IMPORT_WORK_LIST {
        public static final String SUB_FOLDER = "WORK_LIST";
        public static final String UPLOAD_FILE_TYPE ="WORK_LIST_BSD";

    }

    public static class NameAdnSubjectMapping{
        public static final String TYPE_NAME_HUSBAND = "Tên chồng";
        public static final String TYPE_NAME_WIFE = "Tên vợ";
        public static final String TYPE_MIDDLE_BODY = "Trung thân";
        public static final String TYPE_DEATH_PENALTY = "Tử hình";
        public static final String TYPE_MARTYRS = "Liệt sĩ vô danh";
        public static final String TYPE_MARTYRS_ACTIVE = "Liệt sĩ được ghi nhận";
    }

    public static class SheetExecl{
        public static final String SHEET_GENDER = "Giới tính";
        public static final String SHEET_GROUP = "Nhóm";
        public static final String SHEET_ETHNICITY = "Dân tộc";
        public static final String SHEET_RELIGION = "Tôn giáo";
        public static final String SHEET_INVESTIGATION = "Đơn vị yêu cầu";
        public static final String SHEET_PROVINCE = "Tỉnh thành phố";
        public static final String SHEET_DISTRICT = "Quận huyện";
        public static final String SHEET_COMMUNE = "Xã phường";
        public static final String SHEET_STORE = "Thông tin đối tượng";
    }

    public static class  ParamFilter{
        public static final String TYPE_GENDER = "DM_GENDER";
        public static final String TYPE_ETHNICITY= "DM_ETHNICITY";
        public static final String TYPE_RELIGION= "DM_RELIGION";
        public static final String TYPE_ATTRIBUTE= "DM_NHOM_DOI_TUONG";
        public static final String TYPE_STATUS_AND_SUBJECT_PRO= "DM_STATUS_AND_SUBJECT_PRO";
    }

    public static class Attributes{
        public static final String ATTRIBUTE_COUPLE = "DM_NHOM_DT_1";
        public static final String ATTRIBUTE_PRISONER = "DM_NHOM_DT_2";
        public static final String ATTRIBUTE_MARTYRS = "DM_NHOM_DT_3";
    }

    public static class DMAttrubuteGroupCivilians{
        public static final String DM_WIFE = "DM_DAN_THUONG_1";
        public static final String DM_HUSBAND = "DM_DAN_THUONG_2";
    }

    public static class DMAttrubuteGroupCriminal{
        public static final String DM_MIDDLE_BODY = "DM_TOI_PHAM_1" ;
        public static final String DM_DEATH_PENALTY = "DM_TOI_PHAM_2";
    }

    public static class DMAttrubuteGroupMartyrs{
        public static final String DM_MARTYRS = "DM_LIET_SI_1" ;
        public static final String DM_MARTYRS_ACTIVE = "DM_LIET_SI_2";
    }

    public static class DataEthnicity{
        public static final String DM_VALUE_CODE_ETHNICITY_KINH = "KINH";
        public static final String DM_VALUE_CODE_ETHNICITY_DAO = "DAO";
        public static final String DM_VALUE_CODE_ETHNICITY_TAY = "TAY";
    }

    public static final class STATUS_WORK_LIST {
        public static final int APPROVE = 4;
        public static final int DONE = 2;
        public static final int CANCEL = 3;
        public static final int IN_PROCESS = 1;
    }
    public static final class STATUS_SUBJECT_WORK_LIST {
        public static final int PASS = 1;
        public static final int FAIL = 0;
    }

    public static final class WARNING_DUP_LEVEL {
        public static final int NEED_CHECK = 0;
        public static final int EXCIST_BY_BARCODE = 1;
        public static final int EXCIST_BY_INFO = 2;
        public static final int EXCIST_BY_GEN = 3;
        public static final int CLEAN = 8;
    }

    public static final class IS_CHECK {
        public static final int UNCHECKED = 0;
        public static final int CHECKED = 1;
    }

    public static final class SAMPLE_TYPE {
        public static final String SAMPLE = "SAMPLE";
        public static final String CONTROL = "CONTROL";
        public static final String STANDARD = "STANDARD";
        public static final String UNUSED = "UNUSED";
    }

    public static final class KIT_TYPE {
        public static final String KIT_IDENTIFIER_PLUS = "KIT_IDENTIFIER_PLUS";
        public static final String KIT_GLOBAL_FILTER = "KIT_GLOBAL_FILTER";
        public static final String KIT_VERIFILER = "KIT_VERIFILER";
        public static final String KIT_POWERPLEX_FUSION = "KIT_POWERPLEX_FUSION";
        public static final String KIT_POWERPLEX_FUSION_6C = "KIT_POWERPLEX_FUSION_6C";
        public static final String KIT_INVESTIGATOR_24PLEX_GO = "KIT_INVESTIGATOR_24PLEX_GO";
        public static final String YFILER_PLUS = "YFILER_PLUS";
    }

    public static final class TYPE_SUBJECT_DUPLICATE {
        public static final String TYPE_DATA_SUBJECT = "ADN_SUBJECT";
        public static final String TYPE_DATA_PROVISIONAL = "ADN_SUBJECT_PROVISIONAL";
    }

    public static final class TYPE_EXPORT {
        public static final String TRACE_ADN = "TRACE_ADN";
        public static final String PROBABILITY_PARENT_CHILDREN = "PROBABILITY_PARENT_CHILDREN";
    }

    public static final HashMap<String, String> STATUS_ADN_REQUEST_DISPLAY  = new HashMap<String, String>() {{
        put("1", "Mới tạo");
        put("2", "Đang lấy mẫu");
        put("3", "Hoàn thành");
        put("4", "Hủy");
    }};

    public static final HashMap<String, String> STATUS_WORK_LIST_DISPLAY  = new HashMap<String, String>() {{
        put("1", "Đang xét nghiệm");
        put("2", "Hoàn thành");
        put("3", "Hủy");
        put("4", "Duyệt");
    }};
    public static final HashMap<String, String> DELETE_DISPLAY  = new HashMap<String, String>() {{
        put("1", "Không hoạt động");
        put("0", "Hoạt động");
    }};

    public static final HashMap<String, String> STATUS_ADN_SUBJECT_PROVISIONAL  = new HashMap<String, String>() {{
        put("1", "Mới tạo");
        put("2", "Đã có mẫu");
    }};
    public static final HashMap<String, String> STATUS_ADN_SUBJECT_MAPPING  = new HashMap<String, String>() {{
        put("0", "Đã xóa");
        put("1", "Mới tạo");
    }};

    public static final HashMap<String, String> STATUS_ADN_STORE  = new HashMap<String, String>() {{
        put("1", "Nhập kho");
        put("2", "Xuất kho");
    }};

    public static final class TEMPLATE {

        public static final String TEMPLATE_REQUEST_EXPORT = "DETAIL_EXPORT_REQUEST";
        public static final String TEMPLATE_IMPORT_REQUEST = "DETAIL_IMPORT_REQUEST";
        public static final String TEMPLATE_ADN_REQUEST = "DETAIL_ADN_REQUEST";
        public static final String TEMPLATE_ADN_WORK_LIST = "DETAIL_WORK_LIST";
        public static final String TEMPLATE_ADN_SUBJECT_ENVELOP = "DETAIL_ENVELOP_ADN_SUBJECT";
        public static final String EXCEL_TEMPLATE_ADN_SUBJECTS = "EXCEL_TEMP_IMPORT_SUBJECTS";
        public static final String TYPE_3500_TEMPLATE = "3500_SERIES";
        public static final String TYPE_SEQFLEX_TEMPLATE = "SEQFLEX";
    }
    public static final class TITLE_EXPORT {
        public static final String TITLE_STORE_REQUEST = "DANH SÁCH ĐỐI TƯỢNG TRONG PHIẾU NHẬP KHO";
        public static final String TITLE_EXPORT = "DANH SÁCH ĐỐI TƯỢNG TRONG PHIẾU XUẤT KHO";
    }

    public static final class UPLOAD_FILE_TYPE {
        public static final String DM_TEMPLATE = "DM_TEMPLATE";
        public static final String TYPE_UPLOAD_AVT = "SUBJECT_UPLOAD_AVATAR";
        public static final String TYPE_UPLOAD_FINGER = "SUBJECT_UPLOAD_FINGER";
        public static final String EVIDENCE_ADN_REQUEST = "EVIDENCE_ADN_REQUEST";
        public static final String EVIDENCE_ADN_WORK_LIST = "EVIDENCE_ADN_WORK_LIST";
        public static final String EVIDENCE_ADN_STORE_REQUEST_IMPORT = "EVIDENCE_ADN_STORE_REQUEST_IMPORT";
        public static final String EVIDENCE_ADN_STORE_REQUEST_EXPORT = "EVIDENCE_ADN_STORE_REQUEST_EXPORT";
        public static final String FILE_PDF = "application/pdf";
        public static final String FILE_IMAGES = "image/";
    }

    public static final HashMap<String, String> STATUS_ADN_STORE_REQUEST  = new HashMap<String, String>() {{
        put("1", "Mới tạo");
        put("3", "Đã nhập kho");
        put("4", "Hủy");
    }};

    public static final HashMap<String , String> STATUS_ADN_SUBJECT_OTHER_MAPPING = new HashMap<String,String>(){{
       put("1" , "Mới tạo");
       put("2" , "Qúa trình");
       put("3" , "Hoàn thành");
       put("4" , "Đã xóa");
    }};
    public static final class TYPE_FORM_REQUEST {
        public static final String NEW = "NEW";
        public static final String IN_PROGRESS = "IN_PROGRESS";
    }

    public static final class TYPE_STORE_SUITCASE {
        public static final String REQUEST = "REQUEST"; // phuc vu cho ra soat doi tuong
    }

    public static final class SUITCASE_TYPE {
        public static final String SUITCASE = "SUITCASE"; // vali
        public static final String PARCEL = "PARCEL"; // lo
        public static final String SUBJECT = "SUBJECT";
        public static final String SUBJECT_COLLECTED = "SUBJECTS_COLLECTED";//da ra soat
        public static final String SUBJECT_NOT_SAMPLED = "SUBJECTS_NOT_SAMPLED";//du kien thu thap
    }

    /**Trạng thái của đơn vị yêu cầu*/
    public static final class STATUS_ADN_INVEST_REQ {
        public static final class ATTRIBUTE {
            public static final String DM_ADN_INVEST_REQ_STATUS = "DM_ADN_INVEST_REQ_STATUS";
        }
        public static final class MODEL {
            public static final String DM_ADN_INVEST_REQ = "DM_ADN_INVEST_REQ";
        }
    }
    /**Trạng thái của phiếu yêu cầu*/
    public static final class STATUS_ADN_REQUEST {
        public static final Integer PROCESSING = 2;
        public static final Integer DONE = 3;

        public static final class ATTRIBUTE {
            public static final String DM_TRANG_THAI_YC = "DM_TRANG_THAI_YC";
        }
        public static final class MODEL {
            public static final String DM_TRANG_THAI_YEU_CAU = "DM_TRANG_THAI_YEU_CAU";
        }
    }
    /** Cấu hình danh mục Trạng thái của đối tượng trong kho*/
    public static final class DM_STATUS_ADN_STORE_CODE {
        public static final class ATTRIBUTE {
            public static final String DM_TRANG_THAI_ADN_STORE = "DM_TRANG_THAI_ADN_STORE";
        }
        public static final class MODEL {
            public static final String DM_TRANG_THAI_ADN_STORE = "DM_TRANG_THAI_ADN_STORE";
        }
    }
    /** Cấu hình danh mục Trạng thái của đối tượng thu thap*/
    public static final class DM_STATUS_ADN_SUBJECT {
        public static final class ATTRIBUTE {
            public static final String DM_TRANG_THAI_ADN_SUBJECT = "DM_TRANG_THAI_DT";
        }
        public static final class MODEL {
            public static final String DM_TRANG_THAI_ADN_SUBJECT = "DM_TRANG_THAI_DOI_TUONG";
        }
    }
    /**Trạng thái của  nhập kho*/
    public static final class STATUS_ADN_STORE_REQUEST_CODE {
        public static final class ATTRIBUTE {
            public static final String DM_STATUS_IMP_SREQ = "DM_STATUS_IMP_SREQ";
        }
        public static final class MODEL {
            public static final String DM_STATUS_IMP_STO_REQ = "DM_STATUS_IMP_STO_REQ";
        }
    }
    /**Trạng thái của  xuat kho*/
    public static final class STATUS_ADN_STORE_EXPORT_CODE {
        public static final class ATTRIBUTE {
            public static final String DM_STATUS_STORE_EXPORT = "DM_TRANG_THAI_EXPORT_STORE";
        }
        public static final class MODEL {
            public static final String DM_STATUS_STORE_EXPORT = "DM_TRANG_THAI_EXPORT_STORE";
        }
    }
    /**Lý do xuất kho*/
    public static final class REASON_EXPORT_STORE_CODE {
        public static final class ATTRIBUTE {
            public static final String DM_REASON_EXPORT_STORE = "DM_REASON_EXPORT_STORE";
        }
        public static final class MODEL {
            public static final String DM_EXPORT_STORE = "DM_EXPORT_STORE";
        }
    }
    /**Trạng thái của danh sách đối tượng*/
    public static final class STATUS_ADN_SUBJECT_PROVISIONAL_CODE {
        public static final class ATTRIBUTE {
            public static final String DM_TRANG_THAI_DT = "DM_TRANG_THAI_DT";
        }
        public static final class MODEL {
            public static final String DM_TRANG_THAI_DOI_TUONG = "DM_TRANG_THAI_DOI_TUONG";
        }
    }
    /**Trạng thái của danh mục kho*/
    public static final class STATUS_DM_STORE {
        public static final class ATTRIBUTE {
            public static final String DM_TRANG_THAI_KHO = "DM_TRANG_THAI_KHO";
        }
        public static final class MODEL {
            public static final String DM_TRANG_THAI_KHO = "DM_TRANG_THAI_KHO";
        }
    }

    /**Cấu hình lô sai*/
    public static final class CONFIG_LOSIDE {
        public static final class ATTRIBUTE {
            public static final String ATT = "CONFIG_LOSIDE";
        }
        public static final class MODEL {
            public static final String MD = "CONFIG_LOSIDE";
        }
    }

    public static final class GENDER_CODE {
        public static final class ATTRIBUTE {
            public static final String DM_GENDER = "DM_GENDER";
        }
        public static final class MODEL {
            public static final String DM_GENDER = "DM_GENDER";
        }
    }

    public static final class SUBJECT_TABLE_ASSIGN_TYPE {
        public static final String LEFT = "LEFT";
        public static final String RIGHT = "RIGHT";
    }

    public static final class SUITCASE_BARCODE_PREFIX {
        public static final String SUITCASE = "3";
        public static final String PARCEL = "4";
        public static final String SUBJECT = "2";
    }

    public static final class INVEST_TYPE {
        public static final String INVEST_ALL = "INVEST_ALL";
        public static final String SUB_NO_INVEST = "SUB_NO_INVEST";

    }

    public static final class FORM_TYPE {
        public static final String CREATE = "create";
    }

    // Mã nhóm quyền
    public static final class GROUP_CODE {
        public static final String SYSTEM = "SYSTEM";
    }
    public static final class ADN_SUBJECT_TYPE {
        public static final String STORE = "STORE";
        public static final String PROVISIONAL = "PROVISIONAL";
    }
    public static final class MAX_DIVIDE_PARTITION {
        public static final int MAX_SIZE = 500;
        public static final int IN_SUB_LIST = 200;
    }

    public static final class UPLOAD_DATA_SESSION {
        public static final class STATUS {
            public static final int PENDDING = 2;
            public static final int ERROR = 0;
            public static final int ERROR_JOB = 4;
            public static final int DONE_JOB = 1;
            public static final int SUBJECT_DONE = 2;
            public static final int ADN_REQUEST_DONE = 3;
        }
        public static final int LIMIT_FILE = 5;
        public static final String PREFIX_MAIN_FOLDER = "xml-data";
        public static final String PREFIX_IMAGE_FOLDER = "subject-img";
        public static final String URL_XML_PRIVATE_KEY = "xml_keys/private_key.pem";
        public static final String PREFIX_REQ = "adn_request";
    }
    public static final class RELIGION_CODE {
        public static final class ATTRIBUTE {
            public static final String DM_RELIGION = "DM_RELIGION";
        }
        public static final class MODEL {
            public static final String DM_RELIGION = "DM_RELIGION";
        }
    }

    public static final class ETHNICITY_CODE {
        public static final class ATTRIBUTE {
            public static final String DM_ETHNICITY = "DM_ETHNICITY";
        }
        public static final class MODEL {
            public static final String DM_ETHNICITY = "DM_ETHNICITY";
        }
    }

    public static final class MATERIALS_CODE {
        public static final class ATTRIBUTE {
            public static final String DM_MATERIALS = "DM_MATERIALS";
        }
        public static final class MODEL {
            public static final String DM_MATERIALS = "DM_MATERIALS";
        }
    }

    public static final class CHEMICAL_CODE {
        public static final class ATTRIBUTE {
            public static final String DM_CHEMICAL = "DM_CHEMICAL";
        }
        public static final class MODEL {
            public static final String DM_CHEMICAL = "DM_CHEMICAL";
        }
    }

    public static final class STATUS_ASSIGN_SUBJECTS {
        public static final Integer SUCCESS = 1; // Gán thành công đối tượng vào phiếu yêu cầu
        public static final Integer CANCLE = 4; // Hủy gán đối tượng vào phiếu yêu cầu
    }

    public static final class TYPE_BARCODE_SESSION {
        public static final String BARCODE_PARCEL = "TYPE_BARCODE_PARCEL"; // loại barcode lo
        public static final String BARCODE_SUBJECT = "TYPE_BARCODE_SUBJECT"; // loai barcode doi tuong
    }

    public static final class TYPE_REPORT_MATERIALS_CHEMICAL {
        public static final String REPORT_MATERIALS = "MATERIALS"; // loại barcode lo
        public static final String REPORT_CHEMICAL = "CHEMICAL"; // loai barcode doi tuong
    }

    public static final class GEN_RESULT_STEP{
        public static final String BSD_PUNCHING = "BSD_PUNCHING";
    }

    public static final class GEN_RESULT_VIEW_TYPE{
        public static final String GRID = "GRID";
    }

    public static final class DEVICE_TYPE_TEMPLATE {
        public static final class ATTRIBUTE {
            public static final String DM_DEVICES = "DM_DEVICES";
        }
        public static final class MODEL {
            public static final String DM_DEVICES = "DM_DEVICES";
        }
    }
    public static final class TYPE_FILTER_REPORT_SUBJECT_INVESTIGATION{
        public static final String FILTER_ADN_SUBJECT_COLLECT = "FILTER_ADN_SUBJECT_COLLECT";
        public static final String FILTER_ADN_SUBJECT_NOT_SAMPLED = "FILTER_ADN_SUBJECT_NOT_SAMPLED";
        public static final String FILTER_ADN_SUBJECT_STORE = "FILTER_ADN_SUBJECT_STORE";
        public static final String FILTER_ADN_SUBJECT = "FILTER_ADN_SUBJECT";
    }

    public static final class TYPE_SUBJECT_PROVISIONAL {
        public static final Integer INCIDENT = 1; // Đối tượng từ vụ án/vụ việc
    }

    public static final class INCIDENT_CASE_STATUS {
        public static final int ACTIVE = 1; // Mới tạo
        public static final int TESTING = 2; // Đang xét nghiệm
        public static final int TESTED = 3; // Đã xét nghiệm
        public static final int INACTIVE = 0; // Đã hủy
        public static final int APPROVE = 4; // Phê duyệt
        public static final int REJECT = 5; // Từ chối
    }

    public static final class DM_TYPE_SUBJECT_INVESTIGATION {
        public static final String FILTER_ADN_SUBJECT = "FILTER_ADN_SUBJECT"; //Đã có ADN
        public static final String FILTER_ADN_SUBJECT_STORE = "FILTER_ADN_SUBJECT_STORE"; // Đã xét nghiệm
        public static final String FILTER_ADN_SUBJECT_COLLECT = "FILTER_ADN_SUBJECT_COLLECT"; // Đã có mẫu
        public static final String FILTER_ADN_SUBJECT_NOT_SAMPLED = "FILTER_ADN_SUBJECT_NOT_SAMPLED"; // Tất cả đối tượng
    }

    public static final class MODEL_TYPE {
        public static final String CREATE = "CREATE";
        public static final String UPDATE = "UPDATE";
    }
}
