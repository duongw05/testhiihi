package com.language.service.storage;

public class MimeTypeNotAllowedException extends RuntimeException {
    private String mimeType;

    public MimeTypeNotAllowedException(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public String getMessage() {
        return "Mime type '" + mimeType + "' is not allowed";
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
