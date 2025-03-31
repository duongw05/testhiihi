package com.language.service.common.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExcelUtil {

    public static CellStyle getCellStyleRight(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    public static CellStyle getCellStyleLeft(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    public static CellStyle getCellStyleCenter(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    public static CellStyle getCellStyleLeftNoBorder(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        return style;
    }

    public static CellStyle getCellStyleLeftCurrency(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        CreationHelper ch = wb.getCreationHelper();
        style.setDataFormat(ch.createDataFormat().getFormat("#,##0"));
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    public static String genFileName(String type) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String excelFileName = type + "_" + formatter.format(LocalDateTime.now());
        return excelFileName;
    }

    public static ByteArrayOutputStream getOutputArrayStreamFromXSFFWorkbook(String filePath) throws IOException {
        try (InputStream generatedInputStream = new FileInputStream(filePath) ) {
            Workbook workbook = new XSSFWorkbook(generatedInputStream);
            ByteArrayOutputStream resizedOutputStream = new ByteArrayOutputStream();
            workbook.write(resizedOutputStream);
            return resizedOutputStream;
        }
    }
}
