package com.language.service.common.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import com.language.service.common.utils.excel.ExcelDTO;
import com.language.service.common.utils.excel.FileDataError;
import com.language.service.exception.BusinessException;

import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReadExcelFactory {

    final ReadExcelCommon readExcel;

    public <T extends ExcelDTO> List<T> processImport(Workbook workbook, Class<T> clazz, Sheet sheet,
                                                      List<FileDataError> lstError, Consumer<T> consumer, ImportType importType) {
        try {
            if (importType == ImportType.PRODUCT){
                return readExcel.processSheet(workbook, clazz, sheet, 0, 3, lstError, consumer , ImportType.PRODUCT);
            }else if(importType == ImportType.ADN_SUBJECT_PROVISIONAL){
                return readExcel.processSheet(workbook, clazz, sheet, 0, 1, lstError, consumer ,  ImportType.ADN_SUBJECT_PROVISIONAL);
            }else if(importType == ImportType.ADN_SUBJECT_NOT_SAMPLED){
                return readExcel.processSheet(workbook, clazz, sheet, 0, 1, lstError, consumer ,  ImportType.ADN_SUBJECT_NOT_SAMPLED);
            }
            throw new UnsupportedOperationException("unsupported");
        } catch (BusinessException e) {
            throw e;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new BusinessException("Đọc file excel bị lỗi");
        }
    }

}
