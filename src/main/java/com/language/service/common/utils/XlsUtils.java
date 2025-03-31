package com.language.service.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class XlsUtils {

    public static Double getDoubleCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        try {
            return cell.getNumericCellValue();
        } catch (NumberFormatException ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }


    public static Integer getIntegerCellValue(Cell cell) {
        Double value = getDoubleCellValue(cell);
        if (value == null) {
            return null;
        }
        return value.intValue();
    }

    public static BigDecimal validateDecimal(Cell cell, CellType cellType) {
        //		if (cellType.equals(CellType.BLANK)) {
        //			return BigDecimal.valueOf(-1d);
        //		}
        if (cellType.equals(CellType.FORMULA)) {
            try {
                Double value = cell.getNumericCellValue();
                return new BigDecimal(value);
            } catch (Exception ex) {
                return null;
            }
        }
        if (cellType.equals(CellType.STRING)) {
            try {
                String value = cell.getStringCellValue().trim();
                return new BigDecimal(value);
            } catch (Exception ex) {
                return null;
            }
        }
        try {
            return new BigDecimal(cell.getNumericCellValue());
        } catch (Exception ex) {
            return null;
        }
    }

    public static Double validateValueNegative(String value) {
        Double number = null;
        try {
            boolean hasParens = false;
            value = value.replaceAll(",", "");

            if (value.contains("(")) {
                value = value.replaceAll("[()]", "");
                hasParens = true;
            }
            number = Double.parseDouble(value);

            if (hasParens) {
                number = -number;
            }
        } catch (Exception ex) {
            return null;
        }
        return number;
    }

    public static Timestamp validateDate(Cell cell, String value) {

        if (value.isEmpty()) {
            return null;
        }
        if (StringUtils.isNumeric(value.replaceAll(",", ""))) {
            return new Timestamp(-1L);
        }
        try {
            Date dt = cell.getDateCellValue();
            return new Timestamp(dt.getTime());
        } catch (Exception e) {
            try {
                DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                Date date = formatter.parse(value);
                return new Timestamp(date.getTime());
            } catch (Exception e1) {
                log.error(e1.getMessage(), e);
                try {
                    DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                    Date date = formatter.parse(value);
                    return new Timestamp(date.getTime());
                } catch (Exception ex) {
                    try {
                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = formatter.parse(value);
                        return new Timestamp(date.getTime());
                    } catch (Exception ex1) {
                        try {
                            DateFormat formatter = new SimpleDateFormat("M/dd/yyyy");
                            Date date = formatter.parse(value);
                            return new Timestamp(date.getTime());
                        } catch (Exception ex2) {
                            return new Timestamp(-1L);
                        }
                    }
                }
            }
        }
    }

    public static String generalId(String contractNo, Timestamp dt) {
        try {
            String id = new SimpleDateFormat("yyyyMMdd").format(dt);
            return contractNo != null ? (id + "_" + contractNo) : "";
        } catch (Exception e) {
            return "";
        }
    }

    public static Date validateDate(Cell cell, String value, String datePattern) {
        if (value.isEmpty()) {
            return null;
        }
        try {
            return cell.getDateCellValue();
        } catch (Exception e) {
            try {
                DateFormat formatter = new SimpleDateFormat(datePattern);
                return formatter.parse(value);
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return null;
    }
}
