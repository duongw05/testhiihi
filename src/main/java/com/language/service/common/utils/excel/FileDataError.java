package com.language.service.common.utils.excel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDataError {
    private int rowIndex;
    private String description;
    private Timestamp createdAt;
    private String createdBy;
}
