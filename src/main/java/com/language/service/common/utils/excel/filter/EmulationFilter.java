package com.language.service.common.utils.excel.filter;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class EmulationFilter {
    private List<Long> areaIds;
    private List<Long> branchIds;
    private List<Long> transactionOfficeIds;
    private String areaCodeList;
    private String branchCodeList;
    private String transactionOfficeCodeList;
}
