package com.language.service.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface Storage {
    void init();

    void store(MultipartFile file, String folderName, String destName);

    void storeTempFile(MultipartFile file, String destName);

    void deleteTempFile(String storedFileName);

    Stream<Path> loadAll();

    Path load(String folderName, String filename);

    Resource loadAsResource(String folderName, String filename);

    void deleteAll();

    void moveFile(String absoluteFilePath);

}
