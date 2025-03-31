package com.language.service.common.utils;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.language.service.common.Constants;
import com.language.service.common.utils.excel.FileDataError;
import com.language.service.exception.BusinessException;
import com.language.service.rest.dto.request.ProductsExcelDTO;
import com.language.service.rest.dto.response.ImportResult;
import com.language.service.storage.StorageProperties;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Component
public class CommonUtil {
    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    private final StorageProperties storageProperties;
    private final ReadExcelFactory readExcelFactory;

    public CommonUtil(StorageProperties storageProperties, ReadExcelFactory readExcelFactory) {
        this.storageProperties = storageProperties;
        this.readExcelFactory = readExcelFactory;
    }


    public ImportResult imports(MultipartFile attachFile) throws Exception {
        Workbook workbook = null;
        ImportResult rs = new ImportResult();
        try {
            List<FileDataError> fileDataErrors = new ArrayList<>();
            try {
                workbook = WorkbookFactory.create(attachFile.getInputStream());
            } catch (Exception e) {
                logger.debug(e.getMessage(), e);
                throw new BusinessException("File tải lên không đúng template mẫu");
            }
            Sheet sheet = workbook.getSheetAt(0);
            Consumer<ProductsExcelDTO> consumer = o -> {
            };

            List<ProductsExcelDTO> products = readExcelFactory.processImport(workbook, ProductsExcelDTO.class, sheet, fileDataErrors, consumer, ImportType.PRODUCT);
            rs.setTotalUpload(fileDataErrors.size() + (products == null ? 0 : products.size()));
            String fileNameError = "file-loi" + System.currentTimeMillis() + ".xlsx";
            if ((fileDataErrors.isEmpty()) && !products.isEmpty()) {
                System.out.println("vào dây" + products);
            } else {
                try (FileOutputStream outputStream = new FileOutputStream(storageProperties.getLocation() + Constants.IMPORT_CFG.SUB_FOLDER + "/" + fileNameError)) {
                    workbook.write(outputStream);
                }
            }
            int totalFalse = fileDataErrors.size();
            rs.setTotalFalse(totalFalse);
            rs.setTotalSuccess(rs.getTotalUpload() - rs.getTotalFalse());
            rs.setFileError(fileNameError);
            return rs;
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }

}
