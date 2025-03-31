package com.language.service.storage;

import com.language.service.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import jakarta.annotation.PostConstruct;
import com.language.service.service.file.FileTransferService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileSystemStorage implements Storage {
    private final Path rootLocation;
    private final FileTransferService fileTransferService;
    private static final Logger logger = LoggerFactory.getLogger(FileSystemStorage.class);

    @Autowired
    public FileSystemStorage(StorageProperties properties, FileTransferService fileTransferService) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.fileTransferService = fileTransferService;
    }

    @Override
    public void store(MultipartFile filePart, String folderName, String destName) {
        String filename = StringUtils.cleanPath(filePart.getOriginalFilename());
        try {
            checkMultipartFile(filePart);
            if (folderName == null || folderName.trim().equals("")) {
                folderName = destName.substring(0, 2);
            }
            savePartFileToFolder(filePart, folderName, destName);
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public void storeTempFile(MultipartFile file, String destName) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            checkMultipartFile(file);
            String folderName = Constants.TEMP_FOLDER;
            savePartFileToFolder(file, folderName, destName);
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public void deleteTempFile(String storedFileName) {
        Path path = this.rootLocation.resolve("temp").resolve(storedFileName);
        File file = path.toFile();
        if (file.exists() && file.canWrite()){
            file.delete();
        } else {
            logger.debug("Could not delete temp file: {}", storedFileName);
        }
    }

    private void checkMultipartFile(MultipartFile filePart){
        String filename = StringUtils.cleanPath(filePart.getOriginalFilename());
        if (filePart.isEmpty()) {
            throw new StorageException("Failed to store empty file " + filename);
        }
        if (filename.contains("..")) {
            // This is a security check
            throw new StorageException(
                    "Cannot store file with relative path outside current directory "
                            + filename);
        }
    }

    private void savePartFileToFolder(MultipartFile filePart, String folderName, String destFileName) throws IOException{
        try (InputStream inputStream = filePart.getInputStream()) {
            // String folderName = destName.substring(0, 2);
//            Path folderPath = this.rootLocation.resolve(folderName);
//            if (!folderPath.toFile().exists()) {
//                folderPath.toFile().mkdir();
//            }
//            Files.copy(inputStream, folderPath.resolve(destFileName),
//                    StandardCopyOption.REPLACE_EXISTING);

            fileTransferService.uploadFile(false, inputStream, folderName + "/" + destFileName);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String folderName, String filename) {
        if (folderName == null || folderName.trim().equals("")) {
            folderName = filename.substring(0, 2);
        }
        return rootLocation.resolve(folderName).resolve(filename);
    }

    @Override
    public Resource loadAsResource(String folderName, String storedFileName) {
        try {
            Path file = load(folderName, storedFileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + storedFileName);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + storedFileName, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void moveFile(String absoluteFilePath) {

    }

    @PostConstruct
    @Override
    public void init() {
//        try {
//            if (!Files.exists(rootLocation)) {
//                logger.info("Creating upload directory: {}", rootLocation);
//                Files.createDirectories(rootLocation);
//            }
//        }
//        catch (IOException e) {
//            throw new StorageException("Could not initialize storage", e);
//        }
    }
}
