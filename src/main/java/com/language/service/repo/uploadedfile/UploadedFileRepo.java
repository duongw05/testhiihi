package com.language.service.repo.uploadedfile;

import org.springframework.data.jpa.repository.Query;
import com.language.service.domain.entities.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UploadedFileRepo extends JpaRepository<UploadedFile, Long> {
    Optional<UploadedFile> findByStoredName(String storeName);

    @Query("from UploadedFile u where u.objectId =:objectId and u.type =:type and u.deleted=:deleted")
    List<UploadedFile> findByObjectIdAndDeleted(Long objectId,String type,boolean deleted);

    @Query("from UploadedFile u where u.objectId =:objectId and u.originalName =:name and u.deleted=:deleted")
    List<UploadedFile> findByObjectIdAndNameAndDeleted(Long objectId, String name, boolean deleted);

    @Query("from UploadedFile u where u.objectId =:objectId and u.type in :type and u.deleted=:deleted")
    Optional<UploadedFile> findImageByObjectIdAndDeleted(Long objectId, List<String> type, boolean deleted);
}
