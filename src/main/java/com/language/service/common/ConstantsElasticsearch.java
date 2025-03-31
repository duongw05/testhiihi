package com.language.service.common;

public class ConstantsElasticsearch {

    // thong tin index doi tuong
    public static final class ATTR_ADN_SUBJECTS {
        public static final String INVEST_TYPE = "invest_type"; // loại (vien/ dia phuong)
        public static final String INVEST_CODE = "investigation_code"; // Ma code dia phuong/ vien
        public static final String INVEST_REQ_CODE = "invest_req_code"; // Ma code dia phuong/ vien
        public static final String STORE_CODE = "store_code"; // Ma code dia phuong/ vien
        public static final String DELETED = "deleted"; // xoa
        public static final String GENDER = "gender"; // gioi tinh
        public static final String PROVINCE_CODE = "province"; // ma tinh thanh pho
        public static final String ID = "id"; // id
        public static final String BARCODE = "barcode"; // Ma code dia phuong/ vien
        public static final String FULL_NAME_VN = "full_name_vn";
        public static final String FULL_NAME = "full_name_vn"; // Ma code dia phuong/ vien
        public static final String MANAGER_INVEST = "manager_investigation";
        public static final String IDENTIFICATION_NUMBER = "identification_number";
        public static final String BIRTH_DATE = "birth_date";
        public static final String CREATED_DATE = "created_date";
    }

    // thong tin index adn
    public static final class ATTR_ADN {
        public static final String BARCODE = "barcode";
        public static final String KIT_TEST_CODE = "kit_test_code"; // kit code
        public static final String KIT_TEST_CODE_FIELD = "kitTestCode"; // kit code
        public static final String INVEST_CODE = "invest_code"; // kit code
        public static final String ADN_FULL_KIT_PREFIX = "adnFullKit."; // kit code
        public static final String ADN_FULL_KIT_PREFIX_NO_DOT = "adnFullKit"; // kit code

    }

    public static final class ATTR_LOCUS {
        public static final String D8S1179A1 = "d8s1179a1";
        public static final String D8S1179A2 = "d8s1179a2";
        public static final String D21S11A1 = "d21s11a1";
        public static final String D21S11A2 = "d21s11a2";
        public static final String D7S820A1 = "d7s820a1";
        public static final String D7S820A2 = "d7s820a2";
        public static final String CSF1POA1 = "csf1poa1";
        public static final String CSF1POA2 = "csf1poa2";
        public static final String D3S1358A1 = "d3s1358a1";
        public static final String D3S1358A2 = "d3s1358a2";
        public static final String TH01A1 = "th01a1";
        public static final String TH01A2 = "th01a2";
        public static final String D13S317A1 = "d13s317a1";
        public static final String D13S317A2 = "d13s317a2";
        public static final String D16S539A1 = "d16s539a1";
        public static final String D16S539A2 = "d16s539a2";
        public static final String D2S1338A1 = "d2s1338a1";
        public static final String D2S1338A2 = "d2s1338a2";
        public static final String D19S433A1 = "d19s433a1";
        public static final String D19S433A2 = "d19s433a2";
        public static final String VWAA1 = "vwaa1";
        public static final String VWAA2 = "vwaa2";
        public static final String TPOXA1 = "tpoxa1";
        public static final String TPOXA2 = "tpoxa2";
        public static final String D18S51A1 = "d18s51a1";
        public static final String D18S51A2 = "d18s51a2";
        public static final String AMELOGENINA1 = "amelogenina1";
        public static final String AMELOGENINA2 = "amelogenina2";
        public static final String D5S818A1 = "d5s818a1";
        public static final String D5S818A2 = "d5s818a2";
        public static final String FGAA1 = "fgaa1";
        public static final String FGAA2 = "fgaa2";
        public static final String YINDELA1 = "yindela1";
        public static final String YINDELA2 = "yindela2";
        public static final String DYS391A1 = "dys391a1";
        public static final String DYS391A2 = "dys391a2";
        public static final String D2S441A1 = "d2s441a1";
        public static final String D2S441A2 = "d2s441a2";
        public static final String D22S1045A1 = "d22s1045a1";
        public static final String D22S1045A2 = "d22s1045a2";
        public static final String SE33A1 = "se33a1";
        public static final String SE33A2 = "se33a2";
        public static final String D10S1248A1 = "d10s1248a1";
        public static final String D10S1248A2 = "d10s1248a2";
        public static final String D1S1656A1 = "d1s1656a1";
        public static final String D1S1656A2 = "d1s1656a2";
        public static final String D12S391A1 = "d12s391a1";
        public static final String D12S391A2 = "d12s391a2";
        public static final String D6S1043A1 = "d6s1043a1";
        public static final String D6S1043A2 = "d6s1043a2";
        public static final String PENTAEA1 = "pentaea1";
        public static final String PENTAEA2 = "pentaea2";
        public static final String PENTADA1 = "pentada1";
        public static final String PENTADA2 = "pentada2";
        public static final String DYS576A1 = "dys576a1";
        public static final String DYS576A2 = "dys576a2";
        public static final String DYS570A1 = "dys570a1";
        public static final String DYS570A2 = "dys570a2";
        public static final String DYS389IA1 = "dys389ia1";
        public static final String DYS389IA2 = "dys389ia2";
        public static final String DYS635A1 = "dys635a1";
        public static final String DYS635A2 = "dys635a2";
        public static final String DYS389IIA1 = "dys389iia1";
        public static final String DYS389IIA2 = "dys389iia2";
        public static final String DYS627A1 = "dys627a1";
        public static final String DYS627A2 = "dys627a2";
        public static final String DYS460A1 = "dys460a1";
        public static final String DYS460A2 = "dys460a2";
        public static final String DYS458A1 = "dys458a1";
        public static final String DYS458A2 = "dys458a2";
        public static final String DYS19A1 = "dys19a1";
        public static final String DYS19A2 = "dys19a2";
        public static final String YGATAH4A1 = "ygatah4a1";
        public static final String YGATAH4A2 = "ygatah4a2";
        public static final String DYS448A1 = "dys448a1";
        public static final String DYS448A2 = "dys448a2";
        public static final String DYS456A1 = "dys456a1";
        public static final String DYS456A2 = "dys456a2";
        public static final String DYS390A1 = "dys390a1";
        public static final String DYS390A2 = "dys390a2";
        public static final String DYS438A1 = "dys438a1";
        public static final String DYS438A2 = "dys438a2";
        public static final String DYS392A1 = "dys392a1";
        public static final String DYS392A2 = "dys392a2";
        public static final String DYS518A1 = "dys518a1";
        public static final String DYS518A2 = "dys518a2";
        public static final String DYS437A1 = "dys437a1";
        public static final String DYS437A2 = "dys437a2";
        public static final String DYS385A1 = "dys385a1";
        public static final String DYS385A2 = "dys385a2";
        public static final String DYS449A1 = "dys449a1";
        public static final String DYS449A2 = "dys449a2";
        public static final String DYS393A1 = "dys393a1";
        public static final String DYS393A2 = "dys393a2";
        public static final String DYS439A1 = "dys439a1";
        public static final String DYS439A2 = "dys439a2";
        public static final String DYS481A1 = "dys481a1";
        public static final String DYS481A2 = "dys481a2";
        public static final String DYF387S1A1 = "dyf387s1a1";
        public static final String DYF387S1A2 = "dyf387s1a2";
        public static final String DYS533A1 = "dys533a1";
        public static final String DYS533A2 = "dys533a2";
    }
}
