package com.language.service.common;


public class ConstantsApiRequest {

    public static final class API_REQUEST_LOG_NAME {
        public static final String AND_INVESTIGATION_REQUEST = "Quản lý yêu cầu thu thập";
        public static final String ADN_REQUEST = "Danh sách yêu cầu";
        public static final String ADN_STORE = "Quản lý đối tượng";
        public static final String ADN_STORE_IMPORT_EXPORT = "Quản lý phiếu xuất/nhập kho";
        public static final String CHECK_REQUEST = "Rà soát yêu cầu";
        public static final String SUBJECT_VALUD = "Xác minh đối tượng";
        public static final String ADN_SUBJECT_PROVISIONAL = "Danh sách đối tượng";
        public static final String ADN_WORK_LIST = "Yêu cầu xét nghiệm";
        public static final String INCIDENT_CASE = "Vụ án/vụ việc";
    }

    public static final class API_REQUEST_LOG_ACTION {
        public static final String CREATE = "Thêm mới";
        public static final String UPDATE = "Cập nhật";
        public static final String DELETE = "Xóa";
        public static final String TOGGLE_STATUS = "Đổi trạng thái";
        public static final String CONFIRM = "Xác nhận";
        public static final String ADD_ON = "Gán";
        public static final String ADD = "Thêm";
        public static final String APPROVE = "Duyệt";
        public static final String VERIFY = "Xác minh";
        public static final String REJECT = "Từ chối";
        public static final String TEST = "Xét nghiệm";
    }


    public static final class API_COMMON_REQUEST {
        public static final String SEARCH = "search";
        public static final String ADD = "add";
        public static final String UPDATE = "update";
        public static final String DELETE = "delete";
        public static final String DETAIL = "findById";
        public static final String APPROVE = "approve";
        public static final String REJECT = "reject";
    }

    public static final class API_ADN_REQUEST {
        public static final String DELETE = "deleteAdnRequestNoDeleteSubject";
        public static final String CONFIRM = "confirmRequest";
        public static final String CONFIRM_SUBJECT = "confirmAddSubjectToRequest";
        public static final String CONFIRM_SUBJECT_V2 = "confirmAddStoreRequestV2";
    }
    public static final class ADN_INVESTIGATION_REQUEST {
        public static final String UPDATE_STATUS = "updateStatus";
        public static final String DELETE = "delete";
    }

    public static final class ADN_SUBJECT_PROVISIONAL {
        public static final String GET_DETAIL = "getDetail";
        public static final String CREATE = "create";
    }

    public static final class ADN_STORE_REQUEST {
        public static final String LINK_SUBJECT_TO_STORE_REQUEST = "confirmAddSubjectToStoreRequest";
        public static final String ADD_SUBJECT_TO_STORE_REQUEST = "addSubjectToStoreRequest";
        public static final String DELETE_SUBJECT_IN_STORE_REQUEST = "deleteSubjectInStoreRequest";
        public static final String CONFIRM_ADN_STORE_REQUEST = "confirmAdnStoreRequest";
        public static final String REJECT_EXPORT_REQUEST = "rejectExportRequest";
        public static final String APPROVE_EVIDENCE_REQUEST = "approveEvidenceRequest";
        public static final String DELETE_EXPORT = "deleteExport";
        public static final String ADD_SUBJECT_OF_REVIEW = "addSubjectOfReview";
        public static final String CONFIRM_ADD_SUBJECT_OR_SUITCASE_OR_PARCEL_TO_STORE_REQUEST = "confirmAddSubjectOrSuitcaseOrParcelToStoreRequest";
    }

    public static final class ADN_WORK_LIST {
        public static final String UNLINK_SUBJECT = "unlinkSubject";
        public static final String LINK_SUBJECT = "linkSubject";
        public static final String CANCEL = "cancel";
        public static final String APPROVE = "approve";
    }

    public static final class ADN_SUBJECT {
        public static final String VERIFY_SUBJECT = "verifySubject";
    }

    public static final class ADN_STORE {
        public static final String REJECT_ADN_SUBJECT_WARNING = "rejectAdnSubjectWarning";
        public static final String DELETE_SUBJECT_DUPLICATE = "deleteSubjectDuplicate";
        public static final String REMOVE_SUITCASE_IN_ADNSTORE_TO_EXPORT = "removeSuitcaseInAdnStoreToExport";
        public static final String DELETE_ALL_EXPORT = "deleteAllExport";
    }
}

