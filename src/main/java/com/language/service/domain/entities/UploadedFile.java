package com.language.service.domain.entities;

import com.language.service.storage.Access;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "uploaded_file")
@EntityListeners(AuditingEntityListener.class)
public class UploadedFile implements Serializable {

    public UploadedFile() {
        deleted = false;
    }

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

}
