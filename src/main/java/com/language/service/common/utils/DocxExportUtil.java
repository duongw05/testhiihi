package com.language.service.common.utils;

import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.xwpf.usermodel.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class DocxExportUtil {

    /**
     * Hàm thực hiện gen file docx theo template
     * @param templateStream đường dẫn tới template
     * @param data các tham số cần truyền vào template
     * @throws Exception Nếu xảy ra lỗi khi tạo tài liệu
     * */
    public static ByteArrayOutputStream generateDocumentFromTemplate(InputStream templateStream, Map<String, String> data) throws Exception {
        try (XWPFDocument document = new XWPFDocument(templateStream);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            // Thay thế các placeholder trong các đoạn văn
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    if (text != null) {
                        for (Map.Entry<String, String> entry : data.entrySet()) {
                            if (text.contains(entry.getKey())) {
                                text = text.replace(entry.getKey(), entry.getValue());
                                run.setText(text, 0);
                            }
                        }
                    }
                }
            }

            // Thay thế các placeholder trong bảng
            for (XWPFTable table : document.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph paragraph : cell.getParagraphs()) {
                            for (XWPFRun run : paragraph.getRuns()) {
                                String text = run.getText(0);
                                if (text != null) {
                                    for (Map.Entry<String, String> entry : data.entrySet()) {
                                        if (text.contains(entry.getKey())) {
                                            text = text.replace(entry.getKey(), entry.getValue());
                                            run.setText(text, 0);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Ghi tài liệu vào ByteArrayOutputStream và trả về
            document.write(baos);
            return baos;
        }
    }


    /**
     * Hàm này chuẩn bị dữ liệu từ đối tượng
     * Tự động lấy tất cả các phương thức getter và thêm giá trị vào Map<String, String>
     * @param request Đối tượng chứa dữ liệu cần thay thế
     * @return Map<String, String> chứa dữ liệu
     */
    public static Map<String, String> prepareDynamicData(Object request) {
        Map<String, String> data = new HashMap<>();

        // Lấy tất cả các phương thức của đối tượng
        Method[] methods = request.getClass().getMethods();//lấy cả các phương thức kế thừa từ lớp cha
        for (Method method : methods) {
            // Kiểm tra xem phương thức có phải là getter không (tên phương thức bắt đầu bằng "get")
            if (method.getName().startsWith("get")) {
                try {
                    // Gọi phương thức getter để lấy giá trị trả về
                    Object value = method.invoke(request);

                    // Nếu giá trị trả về là null, đặt giá trị là một chuỗi rỗng
                    if (value == null) {
                        data.put(getKeyFromMethodName(method), "");
                    } else {
                        // Nếu giá trị là một kiểu Date, cần định dạng lại trước khi đưa vào map
                        if (value instanceof java.util.Date) {
                            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
                            data.put(getKeyFromMethodName(method), dateFormatter.format((java.util.Date) value));
                        }else if(value instanceof LocalDateTime) {
                            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            data.put(getKeyFromMethodName(method), ((LocalDateTime) value).format(dateTimeFormatter));
                        } else {
                            // Nếu không phải Date, trực tiếp thêm vào map dưới dạng chuỗi
                            data.put(getKeyFromMethodName(method), value.toString());
                        }
                    }
                } catch (Exception e) {
                    // Nếu có lỗi khi gọi phương thức getter, in ra lỗi
                    e.printStackTrace();
                }
            }
        }

        return data;
    }

    /**
     * Lấy tên khóa từ tên phương thức getter
     * @param method Phương thức getter
     * @return Tên khóa cho Map
     */
    public static String getKeyFromMethodName(Method method) {
        // Lấy tên phương thức và loại bỏ tiền tố "get"
        String methodName = method.getName();
        String key = methodName.substring(3); // Loại bỏ "get"

        // Chuyển chữ cái đầu tiên thành chữ thường để làm khóa map
        return key.substring(0, 1).toLowerCase() + key.substring(1);
    }
}
