package com.language.service.service.file;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;

@Service
public class FileTransferService {

    private final FileTransferFactory fileTransferFactory;

    @Autowired
    public FileTransferService(FileTransferFactory fileTransferFactory) {
        this.fileTransferFactory = fileTransferFactory;
    }

    public void uploadFile(boolean isFtp, InputStream inputStream, String remoteFilePath) {
        try {
            FileTransferUtil fileTransferUtil = fileTransferFactory.createFileTransfer(isFtp);
            fileTransferUtil.uploadFile(inputStream, remoteFilePath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage(), e);
        }
    }

    public OutputStream downloadFile(boolean isFtp, String remoteFilePath, OutputStream outputStream) {
        try {
            FileTransferUtil fileTransferUtil = fileTransferFactory.createFileTransfer(isFtp);
            fileTransferUtil.downloadFile(remoteFilePath, outputStream);
            return outputStream;
        } catch (Exception e) {
            throw new RuntimeException("Failed to download file: " + e.getMessage(), e);
        }
    }
}
