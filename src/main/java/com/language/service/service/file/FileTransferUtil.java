package com.language.service.service.file;


import com.language.service.domain.dtos.DownloadFileSFTP;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface FileTransferUtil {

    void uploadFile(InputStream inputStream, String remoteFilePath) throws Exception;

    void downloadFile(String remoteFilePath, OutputStream outputStream) throws Exception;

    void downloadFileAsync(List<String> remoteFilePathLst, List<DownloadFileSFTP> downloadFileSFTPList) throws Exception;
}

