package com.language.service.common.utils.excel;

import com.language.service.common.utils.ExcelUtil;
import com.language.service.domain.dtos.SchoolDTO;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class SchoolExcelExporter {
    private static final String[] HEADERS = {
            "ID", "Mã trường", "Tên trường", "Trạng thái", "ID cơ sở", "Tên cơ sở",
            "ID loại trường", "Loại trường", "Premium", "Bản đồ", "Vĩ độ", "Kinh độ", "Khu vực",
            "Địa chỉ", "Chương trình", "Khu vực CVCT", "Số phút", "RHTA", "Lịch", "Lịch CUMTA",
            "Năm bắt đầu", "Sơ đồ trường", "Người tạo", "Ngày tạo", "Người sửa", "Ngày sửa",
            "Đã xóa", "Người xóa", "Ngày xóa", "Ghi chú"
    };

    public static ByteArrayOutputStream export(List<SchoolDTO> schoolList) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Schools");

        // Create styles
        CellStyle headerStyle = ExcelUtil.getCellStyleCenter(workbook);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        CellStyle textStyle = ExcelUtil.getCellStyleLeft(workbook);
        CellStyle dateStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
        dateStyle.setAlignment(HorizontalAlignment.CENTER);
        dateStyle.setBorderBottom(BorderStyle.THIN);
        dateStyle.setBorderTop(BorderStyle.THIN);
        dateStyle.setBorderLeft(BorderStyle.THIN);
        dateStyle.setBorderRight(BorderStyle.THIN);

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < HEADERS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(HEADERS[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowIdx = 1;
        for (SchoolDTO dto : schoolList) {
            Row row = sheet.createRow(rowIdx++);

            int col = 0;
            row.createCell(col++).setCellValue(dto.getId() != null ? dto.getId() : 0);
            row.createCell(col++).setCellValue(dto.getCode());
            row.createCell(col++).setCellValue(dto.getName());
            row.createCell(col++).setCellValue(dto.getStatus() != null ? dto.getStatus() : 0);
            row.createCell(col++).setCellValue(dto.getWorkingOffsiteId() != null ? dto.getWorkingOffsiteId() : 0);
            row.createCell(col++).setCellValue(dto.getWorkingOffsiteName());
            row.createCell(col++).setCellValue(dto.getSchoolTypeId() != null ? dto.getSchoolTypeId() : 0);
            row.createCell(col++).setCellValue(dto.getSchoolTypeName());
            row.createCell(col++).setCellValue(dto.getIsPremium() != null ? dto.getIsPremium() : 0);
            row.createCell(col++).setCellValue(dto.getMap());
            row.createCell(col++).setCellValue(dto.getLatitude());
            row.createCell(col++).setCellValue(dto.getLongtitude());
            row.createCell(col++).setCellValue(dto.getArea());
            row.createCell(col++).setCellValue(dto.getAddress());
            row.createCell(col++).setCellValue(dto.getPrograms());
            row.createCell(col++).setCellValue(dto.getAreaCvct());
            row.createCell(col++).setCellValue(dto.getMinuteNum() != null ? dto.getMinuteNum() : 0);
            row.createCell(col++).setCellValue(dto.getRhta() != null ? dto.getRhta() : 0);
            row.createCell(col++).setCellValue(dto.getScheduler() != null ? dto.getScheduler() : 0);
            row.createCell(col++).setCellValue(dto.getSchedulerCumTa() != null ? dto.getSchedulerCumTa() : 0);

            Cell startYearCell = row.createCell(col++);
            setDateCellValue(startYearCell, dto.getStartYear(), dateStyle);

            row.createCell(col++).setCellValue(dto.getSchoolDiagram());
            row.createCell(col++).setCellValue(dto.getCreatedBy());

            Cell createdDateCell = row.createCell(col++);
            setDateCellValue(createdDateCell, dto.getCreatedDate(), dateStyle);

            row.createCell(col++).setCellValue(dto.getLastModifiedBy());

            Cell modifiedDateCell = row.createCell(col++);
            setDateCellValue(modifiedDateCell, dto.getLastModifiedDate(), dateStyle);

            row.createCell(col++).setCellValue(dto.getDeleted() != null ? dto.getDeleted() : 0);
            row.createCell(col++).setCellValue(dto.getDeletedBy());

            Cell deletedDateCell = row.createCell(col++);
            setDateCellValue(deletedDateCell, dto.getDeletedDate(), dateStyle);

            row.createCell(col++).setCellValue(dto.getDescription());
        }

        for (int i = 0; i < HEADERS.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream;
    }

    private static void setDateCellValue(Cell cell, Date date, CellStyle style) {
        if (date != null) {
            cell.setCellValue(date);
            cell.setCellStyle(style);
        } else {
            cell.setCellValue("");
            cell.setCellStyle(style);
        }
    }
}
