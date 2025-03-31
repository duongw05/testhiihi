package com.language.service.common.utils.excel;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ExcelDTO {
    private int indexRow;
    private String messageError;
    private boolean isError;
    Map<String, Object> dynamicColumns = new HashMap<>();

}
