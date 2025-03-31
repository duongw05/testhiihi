package com.language.service.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.language.service.domain.entities.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.language.service.common.Constants;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static synchronized String trim(String s) {
        if (s == null) return null;
        return s.trim();
    }

    /**
     * Get the given name in Vietnamese name style
     *
     * @param fullName Eg. Nguyen Quang Tien
     * @return last token, eg: Tien
     */
    public static synchronized String getGivenName(String fullName) {
        if (fullName == null) {
            return null;
        }
        fullName = fullName.trim();
        String[] tokens = fullName.split(" ");
        return tokens[tokens.length - 1];
    }

    public static synchronized String getSurnameAndMiddle(String fullName) {
        if (fullName == null) {
            return null;
        }
        fullName = fullName.trim();
        if (!fullName.contains(" ")) {
            return fullName;
        }
        String name = fullName.substring(0, fullName.lastIndexOf(" "));
        name = name.trim();
        return name;
    }

    public static synchronized void writeObjectNodeToResponse(ObjectNode node, HttpServletResponse response) throws IOException {
        response.addHeader("Content-Type", "application/json; charset=utf-8");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
        ObjectMapper mapper = new ObjectMapper();
        writer.write(mapper.writeValueAsString(node));
        writer.flush();
        writer.close();
    }

    public static synchronized void writeToResponse(String content, HttpServletResponse response) throws IOException {
        response.addHeader("Content-Type", "application/json; charset=utf-8");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
        writer.write(content);
        writer.flush();
        writer.close();
    }

    /**
     * Get default sort by id and DESC direction
     *
     * @param pageable
     * @return
     */
    public static synchronized Pageable getDefaultSortPageable(Pageable pageable) {
        if (pageable == null) {
            return PageRequest.of(0, 20);
        }
        Sort sort = pageable.getSort();
        boolean sorted = sort.isSorted();
        if (!sorted) {
            Sort defaultSort = Sort.by(Sort.Direction.DESC, "id");
            return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), defaultSort);
        }
        return pageable;
    }

    public static synchronized String getRealIp(HttpServletRequest request) {
        String realIp = request.getHeader("X-Real-IP");
        if (realIp == null) {
            realIp = request.getRemoteAddr();
        }
        return realIp;
    }

    /**
     * proxy_set_header Host $host;
     */
    public static synchronized String baseHost(HttpServletRequest request) {
        String scheme = getScheme(request);
        String host = request.getHeader("HOST");
        if (host == null) {
            host = request.getServerName();
        }
        return scheme + "://" + host;
    }

    /**
     * nginx configuration:
     * proxy_set_header        X-Forwarded-Proto $scheme;
     */
    public static synchronized String getScheme(HttpServletRequest request) {
        String proto = request.getHeader("X-Forwarded-Proto");
        if (proto == null) {
            proto = request.getScheme();
        }
        return proto;
    }

    private static synchronized String getFileExtension(String mimeType) {
        switch (mimeType) {
            case "image/png":
                return "png";
            case "image/jpeg":
            case "image/jpg":
                return "jpg";
            default:
                return "";
        }
    }

    public static synchronized String buildPublicFileUrl(HttpServletRequest request, UploadedFile uf) {
        return baseHost(request) + Constants.CLIENT_API + "/files/public/" + uf.getStoredName();
    }

    public static synchronized String buildPublicFileUrl(HttpServletRequest request, String storedName) {
        return baseHost(request) + Constants.CLIENT_API + "/files/public/" + storedName;
    }

    public static synchronized List<Long> potentialNumberListToLongList(List<Object> objList) {
        List<Long> ids = new LinkedList<>();
        if (objList != null && !objList.isEmpty()) {
            for (Object obj : objList) {
                ids.add(((Number) obj).longValue());
            }
        }
        return ids;
    }

    // Turn 1 --> 00001
    public static synchronized String padNumber(long number, int length) {
        if (("" + number).length() >= length) {
            return "" + number;
        }

        StringBuffer sb = new StringBuffer("" + number);
        while (sb.toString().length() < length) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }

    public static void printRequestHeaders(HttpServletRequest request) {
        if (request != null) {
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    logger.debug(name + ": \t" + request.getHeader(name));
                }
            }
        }
    }

    public static Object convertImportDataToDTO(Object importData, Class dtoClass, List<String> timeTempField, List<String> monthField ) throws Exception {
        Object dto = dtoClass.getConstructor().newInstance();
        Class importDataClass = importData.getClass();
        Field fieldImport;
        String value = "";
        for (Field field : dtoClass.getDeclaredFields()) {
            field.setAccessible(true);
            value = "";
            try {
                fieldImport = importDataClass.getDeclaredField(field.getName());
                fieldImport.setAccessible(true);
                if (fieldImport.getType() == Date.class) {
                    if(!DataUtils.isNullOrEmpty(timeTempField) && timeTempField.contains(field.getName())){
                        value = DataUtils.safeToString(date2ddMMyyyyHHMMss((Date) fieldImport.get(importData)));
                    }else if(!DataUtils.isNullOrEmpty(monthField) && monthField.contains(field.getName())){
                        value = DataUtils.safeToString(date2MMyyString((Date) fieldImport.get(importData)));
                    }
                    else{
                        value = DataUtils.safeToString(date2ddMMyyyyString((Date) fieldImport.get(importData)));
                    }
                } else if (fieldImport.getType() == Long.class) {
                    value = DataUtils.safeToString(fieldImport.get(importData));
                } else if (fieldImport.getType() == BigDecimal.class) {
                    value = DataUtils.safeToString(fieldImport.get(importData));
                } else if (fieldImport.getType() == String.class) {
                    value = DataUtils.safeToString(fieldImport.get(importData));
                } else {
                    value = DataUtils.safeToString(fieldImport.get(importData));
                }


            } catch (Exception e) {
                logger.debug(e.getMessage(), e);
            }
            if (!DataUtils.isNullOrEmpty(value)) {
                Class<?> t = field.getType();
                switch (t.getName()) {
                    case "java.lang.String":
                        field.set(dto, value);
                        break;
                    case "java.lang.Long":
                    case "long":
                        field.set(dto, DataUtils.safeToLong(value));
                        break;
                    case "java.lang.Double":
                    case "double":
                        field.set(dto, DataUtils.safeToDouble(value));
                        break;
                    case "java.lang.Boolean":
                    case "boolean":
                        field.set(dto, value);
                        break;
                    case "java.util.Date":
                        field.set(dto, DataUtils.safeToDate(value));
                        break;
                    case "java.time.LocalDate":
                        field.set(dto, DataUtils.safeToLocalDate(value));
                        break;
                    case "java.sql.Timestamp":
                        field.set(dto, DataUtils.safeToTimestamp(value));
                        break;
                    case "java.lang.Integer":
                    case "int":
                        field.set(dto, DataUtils.safeToInteger(value));
                        break;
                    case "java.math.BigInteger":
                        field.set(dto, DataUtils.safeToBigInteger(value));
                        break;
                    case "java.math.BigDecimal":
                        field.set(dto, DataUtils.safeToBigDecimal(value));
                        break;
                    default:
                        break;
                }
//
//                if (field.getType() == Date.class) {
//                    if(!DataUtils.isNullOrEmpty(timeTempField) && timeTempField.contains(field.getName())){
//                        field.set(dto, string2Date(value, "dd/MM/yyyy HH:mm:ss"));
//                    }else if(!DataUtils.isNullOrEmpty(monthField) && monthField.contains(field.getName())){
//                        field.set(dto, string2Date(value, "MM/yyyy"));
//                    }else{
//                        field.set(dto, string2Date(value, "dd/MM/yyyy"));
//                    }
//                } else if (field.getType() == Long.class) {
//                    field.set(dto, DataUtils.safeToLong(value));
//                } else if (field.getType() == BigDecimal.class) {
//                    field.set(dto, DataUtils.safeToBigDecimal(value));
//                } else if (field.getType() == String.class) {
//                    field.set(dto, value);
//                }
            }
        }

        return dto;
    }

    public static Date string2Date(String value, String format) {
        try {
            SimpleDateFormat dbUpdateDateTime = new SimpleDateFormat(format);
            return dbUpdateDateTime.parse(value);
        } catch (ParseException ex) {
        }

        return new Date();
    }

    public static String date2MMyyString(Date value) {
        if (value != null) {
            SimpleDateFormat date = new SimpleDateFormat("MM/yyyy");
            return date.format(value);
        }
        return "";
    }
    public static String date2ddMMyyyyString(Date value) {
        if (value != null) {
            SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
            return ddMMyyyy.format(value);
        }
        return "";
    }

    /**
     * @param value Date
     * @return String
     */
    public static String date2ddMMyyyyHHMMss(Date value) {
        if (value != null) {
            SimpleDateFormat dateTimeNoSlash = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            return dateTimeNoSlash.format(value);
        }
        return "";
    }
}
