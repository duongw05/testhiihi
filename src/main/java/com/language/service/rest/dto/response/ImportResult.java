package com.language.service.rest.dto.response;

import lombok.Data;

@Data
public class ImportResult {
    private int totalUpload;
    private int totalSuccess;
    private int totalFalse;
    private String fileError;
}
