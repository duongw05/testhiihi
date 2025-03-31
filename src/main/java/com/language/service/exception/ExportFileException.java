package com.language.service.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExportFileException extends RuntimeException{
    private String errorCode;

    public ExportFileException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }
}
