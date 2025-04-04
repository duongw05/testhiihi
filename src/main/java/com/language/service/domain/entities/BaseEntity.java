package com.language.service.domain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.language.service.common.AuditDelete;
import com.language.service.common.ColumnDisplayInfo;
import com.language.service.common.ConstantAuditCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {
    @CreatedDate
    @Column(name = "created_date")
    @ColumnDisplayInfo(displayName = ConstantAuditCode.CREATED_DATE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @ColumnDisplayInfo(displayName = ConstantAuditCode.LAST_MODIFIED_DATE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @CreatedBy
    @Column(name = "created_by")
    @ColumnDisplayInfo(displayName = ConstantAuditCode.CREATED_BY)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    @ColumnDisplayInfo(displayName = ConstantAuditCode.LAST_MODIFIED_BY)
    private String lastModifiedBy;

    @Column(name = "deleted")
    @ColumnDisplayInfo(displayName = ConstantAuditCode.DELETED, mapResource = "Constants.DELETE_DISPLAY", packagePath = "com.language.service.common")
    private Integer deleted;

    @Column(name = "deleted_by")
    @ColumnDisplayInfo(displayName = ConstantAuditCode.DELETED_BY)
    private String deletedBy;

    @Column(name = "deleted_date")
    @ColumnDisplayInfo(displayName = ConstantAuditCode.DELETED_DATE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;

    @Transient
    @AuditDelete
    private boolean isDelete;
}
