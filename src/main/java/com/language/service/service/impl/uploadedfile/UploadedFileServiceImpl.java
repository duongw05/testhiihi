package com.language.service.service.impl.uploadedfile;


import com.language.service.domain.entities.UploadedFile;
import com.language.service.repo.uploadedfile.UploadedFileRepo;
import com.language.service.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.language.service.service.abs.uploadedfile.UploadedFileService;


@Component
@Transactional
public class UploadedFileServiceImpl extends AbstractService<UploadedFile, Long> implements UploadedFileService {


    @Autowired
    public UploadedFileServiceImpl(UploadedFileRepo uploadedFileRepo) {
        super(uploadedFileRepo);
    }

}
