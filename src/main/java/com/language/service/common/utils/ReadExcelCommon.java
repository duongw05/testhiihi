package com.language.service.common.utils;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.language.service.common.utils.excel.DataConditionRequired;
import com.language.service.common.utils.excel.ExcelDTO;
import com.language.service.common.utils.excel.FileDataError;
import com.language.service.common.utils.excel.filter.ExcelColumn;
import com.language.service.exception.BusinessException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;


@Component
public class ReadExcelCommon {

    protected static Logger log = LoggerFactory.getLogger(ReadExcelCommon.class);

    public <T extends ExcelDTO> List<T> processSheet(Workbook workbook, Class<T> clazz, Sheet sheet, int headerRow, int startRow,
                                                     List<FileDataError> lstError, Consumer<T> consumer, ImportType typeObject) throws Exception {
        Iterator<Row> rowIterator = sheet.iterator();
        Map<Integer, Field> fieldMaps = new HashMap<>();
        List<T> items = new ArrayList<>();
        Map<String, Integer> columnInfoMap = null;

        // Danh sách cột động
        List<String> dynamicColumnHeaders = new ArrayList<>();


        CellStyle cellStyleLeft = null;
        boolean writeError = isWriteError();
        int colError = 0;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            int rowNum = row.getRowNum();
            if (rowNum == headerRow) {
                columnInfoMap = new HashMap<>();
                for (Cell cell : row) {
                    colError++;
                    if (!DataUtils.isNullOrEmpty(getColumnName(cell))) {
                        columnInfoMap.put(getColumnName(cell), cell.getColumnIndex());
                    }
                }
                if (writeError) {
                    Cell cell = row.createCell(colError);
                    sheet.setColumnWidth(cell.getColumnIndex(), 256 * 50);
                    cell.setCellValue("Thông báo lỗi");
                    CellStyle cellStyleCenter = getCellStyle(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
                    cell.setCellStyle(cellStyleCenter);
                }

                Field[] fieldList = clazz.getDeclaredFields();
                for (Field field : fieldList) {
                    if (field.isAnnotationPresent(ExcelColumn.class)) {
                        field.setAccessible(true);
                        ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                        String columnName = annotation.columnName().trim().toLowerCase();
                        if (columnInfoMap.containsKey(columnName)) {
                            fieldMaps.put(columnInfoMap.get(columnName), field);
                        } else {
                            log.debug("Column Error:{}", columnName);
                            throw new BusinessException("File excel tải lên không đúng định dạng " + columnName);
                        }
                    }
                }

                // Lưu cột động
                for (String columnName : columnInfoMap.keySet()) {
                    if (!fieldMaps.containsKey(columnInfoMap.get(columnName))) {
                        dynamicColumnHeaders.add(columnName);
                    }
                }
                log.info("Danh sách cột động: {}", dynamicColumnHeaders);
                continue;
            }
            if (rowNum < startRow)
                continue;

            T item = clazz.newInstance();
            Map<String, Object> dynamicColumns = new HashMap<>();
            StringBuilder error = new StringBuilder();
            int countError = 0;

            for (Map.Entry<Integer, Field> entry : fieldMaps.entrySet()) {
                int columnIndex = entry.getKey();
                Cell cell = row.getCell(columnIndex);
                Field field = entry.getValue();
                if (field == null) {
                    continue;
                }
                ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                boolean required = annotation.required();
                String columnName = annotation.columnName();
                String regex = annotation.regex();
                com.language.service.common.utils.excel.filter.DataValidation[] dataValidations = annotation.dataValidations();
                DataConditionRequired dataConditionRequired = annotation.dataConditionRequired();
                int columnSize = annotation.columnSize();
                int maxLength = annotation.maxLength();
                int minLength = annotation.minLength();
                long minValue = annotation.minValue();
                long maxValue = annotation.maxValue();
                String[] data = annotation.data();
                field.setAccessible(true);
                Class<?> type = field.getType();
                try {
                    final String value = getValueOfCell(cell);
                    boolean isCellOrValueNull = cell == null || DataUtils.isNullOrEmpty(value);
                    if (isCellOrValueNull) {
                        if (required) {
                            buildError(error, columnName + " không được để trống");
                        } else if (dataConditionRequired != null
                                && !DataUtils.isNullOrEmpty(dataConditionRequired.nameField())
                                && dataConditionRequired.value() != null
                                && dataConditionRequired.value().length > 0
                                && columnInfoMap.containsKey(dataConditionRequired.nameField().trim().toLowerCase())) {
                            Field tmp = fieldMaps.get(columnInfoMap.get(dataConditionRequired.nameField().trim().toLowerCase()));
                            if (tmp != null) {
                                tmp.setAccessible(true);
                                Object fieldValue = tmp.get(item);
                                tmp.setAccessible(false);
                                if (fieldValue != null && Arrays.stream(dataConditionRequired.value()).anyMatch(a -> a.equals(fieldValue.toString()))) {
                                    buildError(error, columnName + " không được để trống");
                                }
                            }
                        }
                        countError++;
                        continue;
                    } else {
                        if (!DataUtils.isNullOrEmpty(regex)) {
                            Pattern pattern = Pattern.compile(regex);
                            if (!pattern.matcher(value).matches()) {
                                buildError(error, columnName + " không đúng định dạng");
                                continue;
                            }
                        }
                        if (dataValidations != null && dataValidations.length > 0) {
                            boolean checkData = false;
                            for (com.language.service.common.utils.excel.filter.DataValidation dataValidation : dataValidations) {
                                if (value.equalsIgnoreCase(dataValidation.value())) {
                                    checkData = true;
                                    field.set(item, dataValidation.code().trim());
                                    break;
                                }
                            }
                            if (!checkData) {
                                buildError(error, columnName + " không đúng quy định");
                            }
                            continue;
                        }
                        if (columnSize != -1 && value.length() != columnSize) {
                            buildError(error, columnName + " phải có " + columnSize + " ký tự");
                            continue;
                        }
                        if (maxLength != -1 && value.length() > maxLength) {
                            buildError(error, columnName + " phải nhỏ hơn hoặc bằng " + maxLength + " ký tự");
                            continue;
                        }
                        if (minLength != -1 && value.length() < minLength) {
                            buildError(error, columnName + " phải lơn hơn hoặc bằng " + minLength + " ký tự");
                            continue;
                        }
                        if (data != null && data.length > 0) {
                            boolean checkData = Arrays.stream(data).anyMatch(tmp -> value.equalsIgnoreCase(tmp.trim()));
                            if (!checkData) {
                                buildError(error, columnName + " không đúng quy định");
                                continue;
                            }
                        }
                    }
                    try {
                        if (type.equals(String.class)) {
                            field.set(item, value);
                        } else if (type.equals(Timestamp.class)) {
                            Timestamp dateValue = XlsUtils.validateDate(cell, value);
                            field.set(item, dateValue);
                        } else if (type.equals(Double.class)) {
                            BigDecimal tmp = new BigDecimal(value);
                            if (tmp.compareTo(BigDecimal.valueOf(-1L)) == 0) {
                                buildError(error, columnName + " phải là số");
                                continue;
                            }
                            field.set(item, tmp.doubleValue());
                        } else if (type.equals(Integer.class)) {
                            Integer tmp = Integer.valueOf(value);
                            field.set(item, tmp);
                        } else if (type.equals(BigInteger.class)) {
                            BigInteger tmp = new BigInteger(value);
                            field.set(item, tmp);
                        } else if (type.equals(Date.class)) {
                            String datePattern = annotation.datePattern();
                            Date date = getCellDate(cell, value, datePattern);
                            if (date == null) {
                                throw new IllegalArgumentException();
                            }
                            if (DateUtil.getYear(date) > 9999) {
                                buildError(error, "Hệ thống không hỗ trợ '" + columnName + " là thời gian vượt quá năm 9999");
                                continue;
                            }
                            field.set(item, date);
                        }
                        if (type.equals(Integer.class) || type.equals(BigInteger.class) || type.equals(Double.class)) {
                            BigDecimal tmp = new BigDecimal(value);
                            if (minValue != -1 && tmp.compareTo(new BigDecimal(minValue)) < 0) {
                                buildError(error, columnName + " phải lớn hơn hoặc bằng " + minValue);
                                continue;
                            }
                            if (maxValue != -1 && tmp.compareTo(new BigDecimal(maxValue)) > 0) {
                                buildError(error, columnName + " phải nhỏ hơn hoặc bằng " + maxValue);
                                continue;
                            }
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        buildError(error, columnName + " không đúng định dạng");
                        continue;
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    buildError(error, columnName + ":" + (row.getRowNum() + 1) + " " + getStackTrace(e));
                    break;
                }

                field.setAccessible(false);

                // Xử lý cột động
                for (String dynamicColumn : dynamicColumnHeaders) {
                    Integer columnIndex1 = columnInfoMap.get(dynamicColumn);
                    Cell cell1 = row.getCell(columnIndex1);
                    String value = getValueOfCell(cell1);
                    if (value != null && !value.isEmpty()) {
                        dynamicColumns.put(dynamicColumn, value);
                    }
                }
                // Gán cột động vào item
                item.setDynamicColumns(dynamicColumns);

            }


            if (countError == fieldMaps.size())
                continue;
            item.setMessageError("");
            if (consumer != null) {
                consumer.accept(item);
            }
            item.setIndexRow(rowNum);
            if (error.length() > 0 || !item.getMessageError().isEmpty()) {
                item.setError(true);
                item.setMessageError(error.append(item.getMessageError()).toString());
                lstError.add(errorFromItem(item));
                if (writeError) {
                    Cell cell = row.createCell(colError);
                    cell.setCellValue(item.getMessageError().trim());
                    cellStyleLeft = Optional.ofNullable(cellStyleLeft).orElseGet(() -> getCellStyle(workbook, HorizontalAlignment.LEFT, VerticalAlignment.TOP));
                    cell.setCellStyle(cellStyleLeft);
                }
            } else {
                Cell cell = row.createCell(colError);
                cell.setCellValue("");
                items.add(item);
            }
        }
        return items;
    }

    protected boolean isWriteError() {
        return true;
    }

    public void buildError(StringBuilder stringBuilder, String error) {
        stringBuilder.append(error + "\n");
    }

    protected String getValueOfCell(Cell cell) {
        if (cell == null)
            return null;
        String value = null;
        switch (cell.getCellType()) {
            case STRING:
                value = cell.getStringCellValue().trim();
                break;
            case NUMERIC:
                value = NumberToTextConverter.toText(cell.getNumericCellValue());
                break;
            default:
                break;
        }
        return value;
    }

    public String getStackTrace(Exception ex) throws Exception {
        if (ex == null)
            return "";
        String trace;
        try (StringWriter sWriter = new StringWriter(); PrintWriter pWriter = new PrintWriter(sWriter)) {
            ex.printStackTrace(pWriter);
            trace = sWriter.toString();
        }
        return trace;
    }

    public String getColumnName(Cell cell) {
        //                    return cell.getStringCellValue().trim().toLowerCase();
        return DataUtils.safeToString(getValueOfCell(cell)).trim().toLowerCase();
    }

    public Date getCellDate(Cell cell, String value, String datePattern) throws Exception {
        return XlsUtils.validateDate(cell, value, datePattern);
    }

    public CellStyle getCellStyle(Workbook workbook, HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Times New Roman");
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setAlignment(horizontalAlignment);
        cellStyle.setVerticalAlignment(verticalAlignment);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setWrapText(true);
        cellStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("@"));
        return cellStyle;
    }

    public <T extends ExcelDTO> FileDataError errorFromItem(T item) {
        FileDataError dataError = new FileDataError();
        dataError.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        dataError.setRowIndex(item.getIndexRow());
        dataError.setDescription(item.getMessageError());
        return dataError;
    }
}
