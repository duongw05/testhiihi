package com.language.service.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class FileTransferConfig {

    // FTP Configuration
    @Value("${ftp.server}")
    private String ftpServer;

    @Value("${ftp.username}")
    private String ftpUsername;

    @Value("${ftp.password}")
    private String ftpPassword;

    @Value("${ftp.port}")
    private int ftpPort;

    // SFTP Configuration
    @Value("${sftp.server}")
    private String sftpServer;

    @Value("${sftp.username}")
    private String sftpUsername;

    @Value("${sftp.password}")
    private String sftpPassword;

    @Value("${sftp.port}")
    private int sftpPort;
}
