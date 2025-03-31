package com.language.service.service.file;


import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.language.service.config.FileTransferConfig;
import com.language.service.domain.dtos.DownloadFileSFTP;
import com.language.service.storage.StorageProperties;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class FileTransferFactory {
    private final StorageProperties storage;
    private final FileTransferConfig config;

    @Autowired
    public FileTransferFactory(StorageProperties storage, FileTransferConfig config) {
        this.storage = storage;
        this.config = config;
    }

    public FileTransferUtil createFileTransfer(boolean isFtp) {
        if (isFtp) {
            return new FtpFileTransferUtil(config, storage);
        } else {
            return new SftpFileTransferUtil(config, storage);
        }
    }

    // FTP File Transfer Utility
    private record FtpFileTransferUtil(FileTransferConfig config,
                                       StorageProperties storage) implements FileTransferUtil {

        @Override
        public void uploadFile(InputStream inputStream, String remoteFilePath) throws IOException {
            FTPClient ftpClient = new FTPClient();
            String remoteDir = remoteFilePath.substring(0, remoteFilePath.lastIndexOf("/"));
            remoteFilePath = storage.getLocation() + "/" + remoteFilePath;
            try {
                ftpClient.connect(config.getFtpServer(), config.getFtpPort());
                ftpClient.login(config.getFtpUsername(), config.getFtpPassword());
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                if (!ftpClient.changeWorkingDirectory(storage.getLocation() + "/" + remoteDir)) {
                    // Tạo thư mục trên server
                    String[] dirs = (storage.getLocation() + "/" + remoteDir).split("/");
                    StringBuilder dirPath = new StringBuilder();
                    for (String dir : dirs) {
                        dirPath.append("/").append(dir);
                        if (!ftpClient.changeWorkingDirectory(dirPath.toString())) {
                            ftpClient.makeDirectory(dirPath.toString());
                        }
                    }
                }
                if (!ftpClient.storeFile(remoteFilePath, inputStream)) {
                    throw new IOException("Failed to upload the file to FTP server.");
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                throw new IOException("Error occurred while uploading the file to FTP server: " + e.getMessage(), e);
            } finally {
                try {
                    if (ftpClient.isConnected()) {
                        ftpClient.logout();
                        ftpClient.disconnect();
                    }
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }

        @Override
        public void downloadFile(String remoteFilePath, OutputStream outputStream) throws IOException {
            FTPClient ftpClient = new FTPClient();
            remoteFilePath = storage.getLocation() + "/" + remoteFilePath;
            try {
                ftpClient.connect(config.getFtpServer(), config.getFtpPort());
                ftpClient.login(config.getFtpUsername(), config.getFtpPassword());
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                if (!ftpClient.retrieveFile(remoteFilePath, outputStream)) {
                    throw new IOException("Failed to download the file from FTP server.");
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                throw new IOException("Error occurred while downloading the file from FTP server: " + e.getMessage(), e);
            } finally {
                try {
                    if (ftpClient.isConnected()) {
                        ftpClient.logout();
                        ftpClient.disconnect();
                    }
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }

        @Override
        public void downloadFileAsync(List<String> remoteFilePathLst, List<DownloadFileSFTP> downloadFileSFTPList) throws Exception {
        }
    }

    // SFTP File Transfer Utility
    private record SftpFileTransferUtil(FileTransferConfig config,
                                        StorageProperties storage) implements FileTransferUtil {

        @Override
        public void uploadFile(InputStream inputStream, String remoteFilePath) throws Exception {
            JSch jsch = new JSch();
            Session session = null;
            ChannelSftp sftpChannel = null;
            String remoteDir = remoteFilePath.substring(0, remoteFilePath.lastIndexOf("/"));
            remoteFilePath = storage.getLocation() + "/" + remoteFilePath;
            try {
                session = jsch.getSession(config.getSftpUsername(), config.getSftpServer(), config.getSftpPort());
                session.setPassword(config.getSftpPassword());
                session.setConfig("StrictHostKeyChecking", "no");
                session.connect();

                sftpChannel = (ChannelSftp) session.openChannel("sftp");
                sftpChannel.connect();
                try {
                    sftpChannel.cd(storage.getLocation() + "/" + remoteDir); // Nếu thư mục tồn tại, chuyển đến nó
                } catch (Exception e) {
                    // Nếu thư mục không tồn tại, tạo thư mục
                    String[] dirs = (storage.getLocation() + "/" + remoteDir).split("/");
                    StringBuilder dirPath = new StringBuilder();
                    for (String dir : dirs) {
                        dirPath.append("/").append(dir);
                        try {
                            sftpChannel.cd(dirPath.toString());
                        } catch (Exception ex) {
                            sftpChannel.mkdir(dirPath.toString());
                            sftpChannel.cd(dirPath.toString());
                        }
                    }
                }
                sftpChannel.put(inputStream, remoteFilePath);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new Exception("Error occurred while uploading the file to SFTP server: " + e.getMessage(), e);
            } finally {
                closeConnection(session, sftpChannel);
            }
        }

        private void closeConnection(Session session, ChannelSftp sftpChannel) {
            try {
                if (sftpChannel != null && sftpChannel.isConnected()) {
                    sftpChannel.exit();
                }
                if (session != null && session.isConnected()) {
                    session.disconnect();
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        @Override
        public void downloadFile(String remoteFilePath, OutputStream outputStream) throws Exception {
            JSch jsch = new JSch();
            Session session = null;
            ChannelSftp sftpChannel = null;
            remoteFilePath = storage.getLocation() + "/" + remoteFilePath;
            log.info("storage.getLocation() : = " + storage.getLocation());
            try {
                session = jsch.getSession(config.getSftpUsername(), config.getSftpServer(), config.getSftpPort());
                session.setPassword(config.getSftpPassword());
                session.setConfig("StrictHostKeyChecking", "no");
                session.connect();

                sftpChannel = (ChannelSftp) session.openChannel("sftp");
                sftpChannel.connect();
                sftpChannel.get(remoteFilePath, outputStream);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new Exception("Error occurred while downloading the file from SFTP server: " + e.getMessage(), e);
            } finally {
                closeConnection(session, sftpChannel);
            }
        }

        @Override
        public void downloadFileAsync(List<String> remoteFilePathLst, List<DownloadFileSFTP> downloadFileSFTPList) {
            JSch jsch = new JSch();
            ExecutorService executorService = Executors.newFixedThreadPool(10); // Tùy chỉnh số luồng
            Session session = null;
            ChannelSftp sftpChannel = null;

            try {
                // Thiết lập kết nối SFTP duy nhất
                session = jsch.getSession(config.getSftpUsername(), config.getSftpServer(), config.getSftpPort());
                session.setPassword(config.getSftpPassword());
                session.setConfig("StrictHostKeyChecking", "no");
                session.connect();

                sftpChannel = (ChannelSftp) session.openChannel("sftp");
                sftpChannel.connect();

                for (String remoteFilePath : remoteFilePathLst) {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    try {
                        String fullPath = storage.getLocation() + "/" + remoteFilePath;

                        sftpChannel.get(fullPath, outputStream);


                        String fileContent = outputStream.toString(StandardCharsets.UTF_8); // Chuyển nội dung sang String

                        DownloadFileSFTP downloadFileSFTP = new DownloadFileSFTP();
                        downloadFileSFTP.setRemoteFilePath(remoteFilePath);
                        downloadFileSFTP.setOutputStream(fileContent);

                        downloadFileSFTPList.add(downloadFileSFTP);
                    } catch (Exception e) {
                        log.error("Error occurred while downloading file: {}", remoteFilePath, e);
                    } finally {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            log.error("Error closing output stream for file: {}", remoteFilePath, e);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Error occurred during SFTP file download", e);
                throw new RuntimeException("Error occurred while downloading files: " + e.getMessage(), e);
            } finally {
                // Đóng tài nguyên
                executorService.shutdown();
                closeConnection(session, sftpChannel);
            }
        }
    }
}

