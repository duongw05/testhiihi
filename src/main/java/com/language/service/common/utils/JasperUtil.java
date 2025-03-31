package com.language.service.common.utils;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.ByteArrayOutputStream;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by Trong Phu on 10/10/2024 09:36
 * Tiện ích cho thư viện Jasper
 * @author Trong Phu
 */
public class JasperUtil {
    private static Logger logger = LoggerFactory.getLogger(JasperUtil.class);

    /**
     * Hàm thực hiện gen file docx theo template
     * @param objects danh sách đối tượng cần in
     * @param parameters các tham số cần truyền vào template
     * @param inputStream template cần in
     * */
    public static ByteArrayOutputStream generateDocxReport(
            List<?> objects,
            Map<String, Object> parameters,
            InputStream inputStream
    ) throws Exception {

        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        // Tạo DataSource cho JasperReports
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(objects);

        // Tạo JasperPrint
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Tạo ByteArrayOutputStream để xuất báo cáo ra đó
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Tạo và cấu hình exporter cho DOCX
        JRDocxExporter exporter = new JRDocxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));

        SimpleDocxReportConfiguration reportConfig = new SimpleDocxReportConfiguration();
        reportConfig.setFlexibleRowHeight(true);
        reportConfig.setFramesAsNestedTables(true);
        exporter.setConfiguration(reportConfig);

        // Xuất báo cáo ra ByteArrayOutputStream
        exporter.exportReport();

        // Trả về ByteArrayOutputStream để xử lý sau ở controller
        return byteArrayOutputStream;
    }

}
