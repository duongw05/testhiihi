package com.language.service.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

/**
 * Generated at 2020/11/27 11:05:50
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationDetailsDTO {

    private Long id;

    private String title;

    private String content;

    private boolean pushed;

    private boolean seen;

    private Long objectId;

    private String objectType;

    private String type;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private Long userId;

    private String userFullName;

    private Long createdById;

    private String createdByFullName;

    private Long lastModifiedById;

    private String lastModifiedByFullName;

    public NotificationDetailsDTO() {
    }

    public NotificationDetailsDTO(
            Long id, String title, String content, boolean pushed, boolean seen, Long objectId, String objectType,
            String type, LocalDateTime createdDate, LocalDateTime lastModifiedDate, Long userId,
            String userFullName, Long createdById, String createdByFullName, Long lastModifiedById,
            String lastModifiedByFullName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.pushed = pushed;
        this.seen = seen;
        this.objectId = objectId;
        this.objectType = objectType;
        this.type = type;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.userId = userId;
        this.userFullName = userFullName;
        this.createdById = createdById;
        this.createdByFullName = createdByFullName;
        this.lastModifiedById = lastModifiedById;
        this.lastModifiedByFullName = lastModifiedByFullName;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getPushed() {
        return this.pushed;
    }

    public void setPushed(boolean pushed) {
        this.pushed = pushed;
    }

    public boolean getSeen() {
        return this.seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public Long getObjectId() {
        return this.objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getObjectType() {
        return this.objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return this.userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }


    public Long getCreatedById() {
        return this.createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public String getCreatedByFullName() {
        return this.createdByFullName;
    }

    public void setCreatedByFullName(String createdByFullName) {
        this.createdByFullName = createdByFullName;
    }


    public Long getLastModifiedById() {
        return this.lastModifiedById;
    }

    public void setLastModifiedById(Long lastModifiedById) {
        this.lastModifiedById = lastModifiedById;
    }

    public String getLastModifiedByFullName() {
        return this.lastModifiedByFullName;
    }

    public void setLastModifiedByFullName(String lastModifiedByFullName) {
        this.lastModifiedByFullName = lastModifiedByFullName;
    }

}
