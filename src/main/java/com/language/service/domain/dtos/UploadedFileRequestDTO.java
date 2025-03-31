package com.language.service.domain.dtos;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import com.language.service.storage.Access;

@Getter
@Setter
public class UploadedFileRequestDTO {
    private MultipartFile part;
    private Access accessType;
    private String[] allowedMimeTypes;
    private Long objectId;
    private String type;
}
