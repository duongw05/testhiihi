import store from "@/store";
import {
    ALLOCATE_TYPE,
    CLASS_RUN_JOB,
    BCCS_TRANS_CODE, BCCS_TRANS_ID,
    DM_PARTNER_TYPE,
    DM_STATUS,
    DM_STATUS_COMMON,
    DM_TYPE_IDENTIFICATION, PROCESS_CRON_JOB, PROCESS_JOB_GROUP,
    PRODUCT_GROUP_ID, PRODUCT_OFFER_TYPE, STATUS_PROCESS_JOB, PRODUCT_TYPE, PROVISION_TYPE, SYSTEM_CONFIG_REQUIRE, SYSTEM_CONFIG_TYPE,
    SYSTEM_CUSTOMER_TYPE, SYSTEM_FEATURE
} from "@/constants/const";

export const appParams = {
    listStatusCommon: () => store.state.globalParam.globalValue.filter((item: any) => item.attCode === DM_STATUS_COMMON && item.modelCode === DM_STATUS),
    listPartnerType: () => store.state.globalParam.globalValue.filter((item: any) => item.attCode === DM_PARTNER_TYPE && item.modelCode === DM_PARTNER_TYPE),
    listIdentification: () => store.state.globalParam.globalValue.filter((item: any) => item.attCode === DM_TYPE_IDENTIFICATION && item.modelCode === DM_TYPE_IDENTIFICATION),
    fetchCustomerType: () => store.state.globalParam.globalValue.filter((item: any) => item.attCode === SYSTEM_CUSTOMER_TYPE && item.modelCode === SYSTEM_CUSTOMER_TYPE),
    fetchProductGroupId: () => store.state.globalParam.globalValue.filter((item: any) => item.attCode === PRODUCT_GROUP_ID && item.modelCode === PRODUCT_GROUP_ID),
    fetchProductType: () => store.state.globalParam.globalValue.filter((item: any) => item.attCode === PRODUCT_TYPE && item.modelCode === PRODUCT_TYPE),
    fetchBccsTransCode: () => store.state.globalParam.globalValue.filter((item: any) => item.attCode === BCCS_TRANS_CODE && item.modelCode === BCCS_TRANS_CODE),
    fetchBccsTransID: () => store.state.globalParam.globalValue.filter((item: any) => item.attCode === BCCS_TRANS_ID && item.modelCode === BCCS_TRANS_ID),
    fetchSystemFeature: () => store.state.globalParam.globalValue.filter((item: any) => item.attCode === SYSTEM_FEATURE && item.modelCode === SYSTEM_FEATURE),
    fetchSystemConfigRequire: () => store.state.globalParam.globalValue.filter((item: any) => item.attCode === SYSTEM_CONFIG_REQUIRE && item.modelCode === SYSTEM_CONFIG_REQUIRE),
    fetchSystemConfigType: () => store.state.globalParam.globalValue.filter((item: any) => item.attCode === SYSTEM_CONFIG_TYPE && item.modelCode === SYSTEM_CONFIG_TYPE),
    fetchClassRunJob: () => store.state.globalParam.globalValue.filter((item: any) => item.attCode === CLASS_RUN_JOB && item.modelCode === CLASS_RUN_JOB),
    fetchProcessJobStatus: () => store.state.globalParam.globalValue.filter((item: any) => item.attCode === STATUS_PROCESS_JOB && item.modelCode === STATUS_PROCESS_JOB),
    fetchProcessJobGroup: () => store.state.globalParam.globalValue.filter((item: any) => item.attCode === PROCESS_JOB_GROUP && item.modelCode === PROCESS_JOB_GROUP),
    fetchProcessCronJob: () => store.state.globalParam.globalValue.filter((item: any) => item.attCode === PROCESS_CRON_JOB && item.modelCode === PROCESS_CRON_JOB),
    fetchProductOfferType: () => store.state.globalParam.globalValue.filter((item: any) => item.attCode === PRODUCT_OFFER_TYPE && item.modelCode === PRODUCT_OFFER_TYPE),
    fetchProvisionType: () => store.state.globalParam.globalValue.filter((item: any) => item.attCode === PROVISION_TYPE && item.modelCode === PROVISION_TYPE),
    fetchAllocateType: () => store.state.globalParam.globalValue.filter((item: any) => item.attCode === ALLOCATE_TYPE && item.modelCode === ALLOCATE_TYPE),
}
