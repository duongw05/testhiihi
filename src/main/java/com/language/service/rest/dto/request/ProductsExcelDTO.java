package com.language.service.rest.dto.request;

import lombok.Data;
import com.language.service.common.utils.excel.ExcelDTO;
import com.language.service.common.utils.excel.filter.ExcelColumn;

import java.util.Date;

@Data
public class ProductsExcelDTO extends ExcelDTO {

    @ExcelColumn(columnName = "MÃ SẢN PHẨM", required = true, maxLength = 10, regex = "^[A-Za-z0-9]+$")
    private String productCode;
    @ExcelColumn(columnName = "TÊN SẢN PHẨM", required = true, maxLength = 100)
    private String productName;
    @ExcelColumn(columnName = "KIỂU SẢN PHẨM", required = true, data = {"MAIN", "SUB"})
    private String productType;
    private Date expireContractDate;
    @ExcelColumn(columnName = "MÃ NHÓM SẢN PHẨM", required = true)
    private String productGroupsCode;
    @ExcelColumn(columnName = "LOẠI SẢN PHẨM", required = true)
    private String appParamCode;
}
