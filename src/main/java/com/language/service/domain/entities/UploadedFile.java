package com.language.service.domain.entities;

import com.language.service.storage.Access;
import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "uploaded_file")
@EntityListeners(AuditingEntityListener.class)
public class UploadedFile implements Serializable {

    public UploadedFile() {
        deleted = false;
    }

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UPLOADED_FILE_SEQ")
    @SequenceGenerator(name = "UPLOADED_FILE_SEQ", sequenceName = "UPLOADED_FILE_SEQ", allocationSize = 1)    private long id;

    @NaturalId
    @Column(name = "uuid", updatable = false, unique = true, nullable = false)
    private String uuid;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "stored_name")
    private String storedName;

    @Column(name = "folder_name")
    private String folderName;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "access_type")
    @Enumerated(EnumType.ORDINAL)
    private Access accessType;

    @Column(name = "file_size")
    private long fileSize;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "object_id")
    private Long objectId;

    @Column(name = "type")
    private String type;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getStoredName() {
        return storedName;
    }

    public void setStoredName(String storedName) {
        this.storedName = storedName;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Access getAccessType() {
        return accessType;
    }

    public void setAccessType(Access accessType) {
        this.accessType = accessType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
