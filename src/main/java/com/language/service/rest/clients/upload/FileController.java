package com.language.service.rest.clients.upload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.language.service.common.Constants;
import com.language.service.storage.StorageService;

import java.io.IOException;

@RestController
@RequestMapping(Constants.CLIENT_API + "/files")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private final StorageService storageService;

    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping(value = "/downloadFile/{id}/{type}")
    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.TEMPLATE_MANAGEMENT).DOWNLOAD_TEMPLATE)")
    public ResponseEntity<?> downloadFile(@PathVariable Long id, @PathVariable String type) throws IOException {

        InputStreamResource inputStreamResource = storageService.downloadFile(id, type);
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.add("File", inputStreamResource.getFilename());
        responseHeader.add("Content-Dispositon", "attachment; filename=" + inputStreamResource.getFilename());
        responseHeader.add("Access-Control-Expose-Headers", "File");
        return new ResponseEntity<>(inputStreamResource, responseHeader, HttpStatus.OK);
    }

    @GetMapping(value = "/downloadFileByName/{id}/{name}")
    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.TEMPLATE_MANAGEMENT).DOWNLOAD_TEMPLATE)")
    public ResponseEntity<?> downloadFileByName(@PathVariable Long id, @PathVariable String name) throws IOException {

        InputStreamResource inputStreamResource = storageService.downloadFileByName(id, name);
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.add("File", inputStreamResource.getFilename());
        responseHeader.add("Content-Dispositon", "attachment; filename=" + inputStreamResource.getFilename());
        responseHeader.add("Access-Control-Expose-Headers", "File");
        return new ResponseEntity<>(inputStreamResource, responseHeader, HttpStatus.OK);
    }
}

