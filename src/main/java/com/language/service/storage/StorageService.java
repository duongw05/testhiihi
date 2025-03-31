package com.language.service.storage;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.language.service.common.Constants;
import com.language.service.common.ConstantsErrorCode;
import com.language.service.common.utils.DataUtils;
import com.language.service.config.BeanCommonUtils;
import com.language.service.domain.dtos.UploadedFileRequestDTO;
import com.language.service.domain.entities.UploadedFile;
import com.language.service.exception.BusinessException;
import com.language.service.repo.uploadedfile.UploadedFileRepo;
import com.language.service.service.abs.uploadedfile.UploadedFileService;
import com.language.service.service.file.FileTransferService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class StorageService {
    private static final Logger logger = LoggerFactory.getLogger(StorageService.class);

    private Storage storage;
    private UploadedFileService uploadedFileService;
    private StorageProperties storageProperties;


    @Autowired
    private UploadedFileRepo uploadedFileRepo;

    @Transactional
    public UploadedFile store(UploadedFileRequestDTO uploadedFileRequestDTO) throws IOException {
        MultipartFile part = uploadedFileRequestDTO.getPart();
        Access accessType = uploadedFileRequestDTO.getAccessType();
        String[] allowedMimeTypes = uploadedFileRequestDTO.getAllowedMimeTypes();
        if (part == null) {
            throw new IllegalStateException("MultipartFile is null");
        }
        if (part.getOriginalFilename() == null) {
            throw new IllegalStateException("File name is null");
        }
        String originalName = StringUtils.cleanPath(part.getOriginalFilename());

        String storedName = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = getFileExtension(originalName);
        if (extension != null) {
            storedName = storedName + "." + extension;
        }
        String externalId = UUID.randomUUID().toString().replaceAll("-", "");
        String folderName;
        if (accessType == Access.PUBLIC) {
            folderName = Constants.PUBLIC_FOLDER;
        } else {
            folderName = storedName.substring(0, 2);
        }
        // Store it to disk
        storage.store(part, folderName, storedName);

        // Create a record to represent the actual file
        UploadedFile uFile = new UploadedFile();
        uFile.setFolderName(folderName);
        uFile.setOriginalName(originalName);
        uFile.setStoredName(storedName);
        uFile.setUuid(externalId);
        uFile.setDeleted(false);
        uFile.setFileSize(part.getSize());
        uFile.setAccessType(accessType);
        uFile.setObjectId(uploadedFileRequestDTO.getObjectId());
        uFile.setType(uploadedFileRequestDTO.getType());

//        Tika tika = new Tika();
//        String mimeType = tika.detect(getUploadedFilePath(uFile));
//        uFile.setMimeType(mimeType);
//
//        if (allowedMimeTypes != null && allowedMimeTypes.length > 0) {
//            boolean allowed = Arrays.asList(allowedMimeTypes).contains(mimeType);
//            if (!allowed) {
//                // TODO
//                // Delete the file
//                throw new MimeTypeNotAllowedException(mimeType);
//            }
//        }
//        System.out.println(uFile.toString());
        uploadedFileService.save(uFile);

        return uFile;
    }

    public UploadedFile storeUploadFile(MultipartFile part, Long id, String type) throws Exception {
        try {
            String contentType = part.getContentType();

            if (contentType == null ||
                    !(contentType.startsWith(Constants.UPLOAD_FILE_TYPE.FILE_IMAGES) || contentType.equals(Constants.UPLOAD_FILE_TYPE.FILE_PDF))) {
                throw new BusinessException(ConstantsErrorCode.ERROR_UPLOAD_FILE.REQUIRED_UPLOAD_FILE);
            }

            UploadedFileRequestDTO uploadedFileRequestDTO = new UploadedFileRequestDTO();
            uploadedFileRequestDTO.setPart(part);
            uploadedFileRequestDTO.setType(type);
            uploadedFileRequestDTO.setObjectId(id);
            uploadedFileRequestDTO.setAccessType(Access.PUBLIC);
            return store(uploadedFileRequestDTO);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.length() == 0) {
            return null;
        }
        int indexOfDot = fileName.lastIndexOf(".");
        if (indexOfDot < 0) {
            return null;
        }
        String s = fileName.substring(indexOfDot);
        if (s.startsWith(".")) {
            s = s.substring(1);
        }
        return s;
    }

    private String getMimeType(UploadedFile uf) throws IOException {
        return Files.probeContentType(getUploadedFilePath(uf));
    }

    public Path getUploadedFilePath(UploadedFile uFile) {
        String rootLocation = storageProperties.getLocation();
        Path p = Paths.get(rootLocation, uFile.getFolderName(), uFile.getStoredName());
        logger.debug("Uploaded path: {}", p);
        return p;
    }

    public InputStreamResource downloadFile(Long id, String type) throws IOException {
        try {
            List<UploadedFile> uploadedFile = uploadedFileRepo.findByObjectIdAndDeleted(id, type, false);
            if (DataUtils.isNullOrEmpty(uploadedFile)) {
                throw new BusinessException(ConstantsErrorCode.FILE.NOT_FOUND);
            }
            return getInputStreamResource(uploadedFile);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage(), e);
            throw e;
        }
    }

    public InputStreamResource getInputStreamResource(List<UploadedFile> uploadedFile) {
        UploadedFile uploadedFile1 = uploadedFile.get(0);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BeanCommonUtils.getBean(FileTransferService.class)
                .downloadFile(false, uploadedFile1.getFolderName() + "/" + uploadedFile1.getStoredName(), outputStream);
        return new InputStreamResource(new java.io.ByteArrayInputStream(outputStream.toByteArray()));
    }

    public InputStreamResource downloadFileByName(Long id, String name) throws IOException {
        try {
            List<UploadedFile> uploadedFile = uploadedFileRepo.findByObjectIdAndNameAndDeleted(id, name, false);
            if (DataUtils.isNullOrEmpty(uploadedFile)) {
                throw new BusinessException(ConstantsErrorCode.FILE.NOT_FOUND);
            }
            return getInputStreamResource(uploadedFile);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage(), e);
            throw e;
        }
    }


    public void storeTempFile(MultipartFile file, String destName) {
        storage.storeTempFile(file, destName);
    }

    public void deleteTempFile(String storedFileName) {
        storage.deleteTempFile(storedFileName);
    }

    public void moveFile(String absoluteFilePath) throws IOException {
        storage.moveFile(absoluteFilePath);
    }


    @Autowired
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Autowired
    public void setUploadedFileService(UploadedFileService uploadedFileService) {
        this.uploadedFileService = uploadedFileService;
    }

    @Autowired
    public void setStorageProperties(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }
}
