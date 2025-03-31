package com.language.service.common;

public class ConstantAuditCode {

    public static final String CREATED_DATE = "AUDIT.CREATED_DATE";
    public static final String LAST_MODIFIED_DATE = "AUDIT.LAST_MODIFIED_DATE";
    public static final String CREATED_BY = "AUDIT.CREATED_BY";
    public static final String LAST_MODIFIED_BY = "AUDIT.LAST_MODIFIED_BY";
    public static final String DELETED = "AUDIT.DELETED";
    public static final String DELETED_BY = "AUDIT.DELETED_BY";
    public static final String DELETED_DATE = "AUDIT.DELETED_DATE";


    public static class WORK_LIST {
        public static final String NAME = "WORK_LIST.NAME";

        public static final String BARCODE = "WORK_LIST.BARCODE";

        public static final String ID = "WORK_LIST.ID";

        public static final String DESCRIPTION = "WORK_LIST.DESCRIPTION";

        public static final String STATUS = "WORK_LIST.STATUS";

        public static final String CODE = "WORK_LIST.CODE";
        public static final String STEP = "WORK_LIST.STEP";
        public static final String KIT_TEST_CODE = "WORK_LIST.KIT_TEST_CODE";
        public static final String MADE_BY = "WORK_LIST.MADE_BY";
        public static final String IMPLEMENTATION_DATE = "WORK_LIST.IMPLEMENTATION_DATE";
        public static final String INVESTIGATION_REQUEST_CODE = "WORK_LIST.INVESTIGATION_REQUEST_CODE";
    }

    public static class WORK_LIST_DETAIL {
        public static final String NAME = "WORK_LIST_DETAIL.NAME";
        public static final String SUBJECT_BARCODE = "WORK_LIST_DETAIL.SUBJECT_BARCODE";
        public static final String SUBJECT_ID = "WORK_LIST_DETAIL.SUBJECT_ID";
        public static final String ID = "WORK_LIST_DETAIL.ID";
        public static final String DESCRIPTION = "WORK_LIST_DETAIL.DESCRIPTION";
        public static final String STATUS = "WORK_LIST_DETAIL.STATUS";
        public static final String CODE = "WORK_LIST_DETAIL.CODE";
        public static final String STEP = "WORK_LIST_DETAIL.STEP";
    }

    public static class ADN_SUBJECT_PROVISIONAL {
        public static final String LAST_NAME = "ADN_SUBJECT_PROVISIONAL.LAST_NAME";
        public static final String MIDDLE_NAME = "ADN_SUBJECT_PROVISIONAL.MIDDLE_NAME";
        public static final String FIRST_NAME = "ADN_SUBJECT_PROVISIONAL.FIRST_NAME";
        public static final String FULLNAME = "ADN_SUBJECT_PROVISIONAL.FULLNAME";
        public static final String FULLNAMEVN = "ADN_SUBJECT_PROVISIONAL.FULLNAMEVN";
        public static final String GENDER = "ADN_SUBJECT_PROVISIONAL.GENDER";
        public static final String ETHNICITY = "ADN_SUBJECT_PROVISIONAL.ETHNICITY";
        public static final String RELIGION = "ADN_SUBJECT_PROVISIONAL.RELIGION";
        public static final String IDENTIFICATION_NUMBER = "ADN_SUBJECT_PROVISIONAL.IDENTIFICATION_NUMBER";
        public static final String INVESTIGATION_CODE = "ADN_SUBJECT_PROVISIONAL.INVESTIGATION_CODE";
        public static final String PHONE_NUMBER = "ADN_SUBJECT_PROVISIONAL.PHONE_NUMBER";
        public static final String PRECINCT = "ADN_SUBJECT_PROVISIONAL.PRECINCT";
        public static final String PROVINCE = "ADN_SUBJECT_PROVISIONAL.PROVINCE";
        public static final String DISTRICT = "ADN_SUBJECT_PROVISIONAL.DISTRICT";
        public static final String STATUS = "ADN_SUBJECT_PROVISIONAL.STATUS";
        public static final String BARCODE = "ADN_SUBJECT_PROVISIONAL.BARCODE";
        public static final String MANAGER_BY = "ADN_SUBJECT_PROVISIONAL.MANAGER_BY";
        public static final String MANAGER_INVESTIGATION = "ADN_SUBJECT_PROVISIONAL.MANAGER_INVESTIGATION";
        public static final String BIRTH_DATE = "ADN_SUBJECT_PROVISIONAL.BIRTH_DATE";
        public static final String CCCD = "ADN_SUBJECT_PROVISIONAL.CCCD";
        public static final String AREA_NAME = "ADN_SUBJECT_PROVISIONAL.AREA_NAME";
        public static final String PROVINCE_NAME = "ADN_SUBJECT_PROVISIONAL.PROVINCE_NAME";
        public static final String DISTRICT_NAME = "ADN_SUBJECT_PROVISIONAL.DISTRICT_NAME";
        public static final String ATTRIBUTE_CODE = "ADN_SUBJECT_PROVISIONAL.ATTRIBUTE_CODE";
        public static final String VALUE_CODE = "ADN_SUBJECT_PROVISIONAL.VALUE_CODE";
        public static final String ADDRESS = "ADN_SUBJECT_PROVISIONAL.ADDRESS";
        public static final String PRECINCT_NAME = "ADN_SUBJECT_PROVISIONAL.PRECINCT_NAME";
        public static final String SUBJECT_KEY = "ADN_SUBJECT_PROVISIONAL.SUBJECT_KEY";
    }

    public static class ADN_SUBJECT_MAPPING {
        public static final String SUBJECT_CODE = "ADN_SUBJECT_MAPPING.SUBJECT_CODE";
            public static final String VALUE_LABEL = "ADN_SUBJECT_MAPPING.VALUE_LABEL";
        public static final String VALUE = "ADN_SUBJECT_MAPPING.VALUE";
        public static final String VALUE_CODE = "ADN_SUBJECT_MAPPING.VALUE_CODE";
        public static final String STATUS = "ADN_SUBJECT_MAPPING.STATUS";
    }

    public static class FILE {
        public static final String NAME = "FILE.NAME";
    }

    public static class ADN_REQUEST {
        public static final String REQUEST_NAME = "ADN_REQUEST.REQUEST_NAME";

        public static final String REQUEST_CODE = "ADN_REQUEST.REQUEST_CODE";
        public static final String INVESTIGATION_REQUEST_CODE = "ADN_REQUEST.INVESTIGATION_REQUEST_CODE";

        public static final String ID = "ADN_REQUEST.ID";

        public static final String DESCRIPTION = "ADN_REQUEST.DESCRIPTION";

        public static final String STATUS = "ADN_REQUEST.STATUS";
        public static final String DUE_DATE = "ADN_REQUEST.DUE_DATE";
        public static final String DUE_REQUEST = "ADN_REQUEST.DUE_REQUEST";
    }

    public static class ADN_REQUEST_DETAIL {
        public static final String NAME = "ADN_REQUEST_DETAIL.NAME";
        public static final String BARCODE = "ADN_REQUEST_DETAIL.SUBJECT_BARCODE";
        public static final String SUBJECT_ID = "ADN_REQUEST_DETAIL.SUBJECT_ID";
        public static final String ID = "ADN_REQUEST_DETAIL.ID";
        public static final String DESCRIPTION = "ADN_REQUEST_DETAIL.DESCRIPTION";
        public static final String STATUS = "ADN_REQUEST_DETAIL.STATUS";
        public static final String CODE = "ADN_REQUEST_DETAIL.CODE";
        public static final String OBJECT_ID = "ADN_REQUEST_DETAIL.OBJECT_ID";
        public static final String OBJECT_CODE = "ADN_REQUEST_DETAIL.OBJECT_CODE";
        public static final String SUITCASE_ID = "ADN_REQUEST_DETAIL.SUITCASE_ID";
        public static final String SUITCASE_BARCODE = "ADN_REQUEST_DETAIL.SUITCASE_BARCODE";
    }

    public static class ADN_STORE_REQUEST {
        public static final String CODE = "ADN_STORE_REQUEST.CODE";
        public static final String INVESTIGATION_REQUEST_CODE = "ADN_STORE_REQUEST.INVESTIGATION_REQUEST_CODE";
        public static final String DESCRIPTOIN = "ADN_STORE_REQUEST.DESCRIPTOIN";
        public static final String STATUS = "ADN_STORE_REQUEST.STATUS";
        public static final String ID = "ADN_STORE_REQUEST.ID";
        public static final String NAME = "ADN_STORE_REQUEST.NAME";
        public static final String MANAGER_BY = "ADN_STORE_REQUEST.MANAGER_BY";
        public static final String REQEUST_TYPE = "ADN_STORE_REQUEST.REQEUST_TYPE";
        public static final String MANAGER_INVESTIGATION = "ADN_STORE_REQUEST.MANAGER_INVESTIGATION";
        public static final String INVESTIGATION_TYPE = "ADN_STORE_REQUEST.INVESTIGATION_TYPE";
        public static final String IMPORT_DATE = "ADN_STORE_REQUEST.IMPORT_DATE";
        public static final String IMPORT_BY = "ADN_STORE_REQUEST.IMPORT_BY";
        public static final String EXPORT_DATE = "ADN_STORE_REQUEST.EXPORT_DATE";
        public static final String EXPORT_BY = "ADN_STORE_REQUEST.EXPORT_BY";
        public static final String ADN_REQUEST_CODE = "ADN_STORE_REQUEST.ADN_REQUEST_CODE";
        public static final String TYPE = "ADN_STORE_REQUEST.ADN_REQUEST_TYPE";
        public static final String INVESTIGATION_EXPORT = "ADN_STORE_REQUEST.INVESTIGATION_EXPORT";
        public static final String INVESTIGATION_IMPORT = "ADN_STORE_REQUEST.INVESTIGATION_IMPORT";
        public static final String STORE_EXPORT = "ADN_STORE_REQUEST.STORE_EXPORT";
        public static final String STORE_IMPORT = "ADN_STORE_REQUEST.STORE_IMPORT";
        public static final String REASON_EXPORT = "ADN_STORE_REQUEST.REASON_EXPORT";
        public static final String REASON_CONFIRM = "ADN_STORE_REQUEST.REASON_CONFIRM";
        public static final String KIT_TEST_CODE = "ADN_STORE_REQUEST.KIT_TEST_CODE";
    }

    public static class ADN_STORE {
        public static final String INVESTIGATION_REQUEST_CODE = "ADN_STORE.INVESTIGATION_REQUEST_CODE";
        public static final String STORE_REQUEST_CODE = "ADN_STORE.STORE_REQUEST_CODE";
        public static final String LAST_NAME = "ADN_STORE.LAST_NAME";
        public static final String MIDDLE_NAME = "ADN_STORE.MIDDLE_NAME";
        public static final String FIRST_NAME = "ADN_STORE.FIRST_NAME";
        public static final String GENDER = "ADN_STORE.GENDER";
        public static final String ETHNICITY = "ADN_STORE.ETHNICITY";
        public static final String PHONE_NUMBER = "ADN_STORE.PHONE_NUMBER";
        public static final String PRECINCT = "ADN_STORE.PRECINCT";
        public static final String PRECINCT_NAME = "ADN_STORE.PRECINCT_NAME";
        public static final String PROVINCE = "ADN_STORE.PROVINCE";
        public static final String DISTRICT = "ADN_STORE.DISTRICT";
        public static final String STATUS = "ADN_STORE.STATUS";
        public static final String BARCODE = "ADN_STORE.BARCODE";
        public static final String MANAGER_BY = "ADN_STORE.MANAGER_BY";
        public static final String MANAGER_INVESTIGATION = "ADN_STORE.MANAGER_INVESTIGATION";
        public static final String FULLNAMEVN = "ADN_STORE.FULLNAMEVN";
        public static final String AREA_NAME = "ADN_STORE.AREA_NAME";
        public static final String PROVINCE_NAME = "ADN_STORE.PROVINCE_NAME";
        public static final String DISTRICT_NAME = "ADN_STORE.DISTRICT_NAME";
        public static final String INVESTIGATION_CODE = "ADN_STORE.INVESTIGATION_CODE";
        public static final String ADDRESS = "ADN_STORE.ADDRESS";
        public static final String IDENTIFICATION_NUMBER = "ADN_STORE.IDENTIFICATION_NUMBER";
        public static final String ATTRIBUTE_CODE = "ADN_STORE.ATTRIBUTE_CODE";
        public static final String VALUE_CODE = "ADN_STORE.VALUE_CODE";
        public static final String STORE_NAME = "ADN_STORE.STORE_NAME";
        public static final String STORE_CODE = "ADN_STORE.STORE_CODE";
        public static final String BIRTH_DATE = "ADN_STORE.BIRTH_DATE";
    }

    public static class ADN_STORE_ATTRIBUTE_MAPPING {
        public static final String BARCODE_CODE = "ADN_STORE_ATTRIBUTE_MAPPING.BARCODE_CODE";
        public static final String VALUE_LABEL = "ADN_STORE_ATTRIBUTE_MAPPING.VALUE_LABEL";
        public static final String VALUE = "ADN_STORE_ATTRIBUTE_MAPPING.VALUE";
        public static final String VALUE_CODE = "ADN_STORE_ATTRIBUTE_MAPPING.VALUE_CODE";
        public static final String STATUS = "ADN_STORE_ATTRIBUTE_MAPPING.STATUS";
    }


}
