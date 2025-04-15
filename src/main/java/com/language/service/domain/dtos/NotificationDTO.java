package com.language.service.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Generated at 2020/11/27 11:05:20
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationDTO {


    private Long id;

    private String title;

    private String content;

    private boolean pushed;

    private boolean seen;

    private String type;

    private Long objectId;

    private String objectType;

    private String statusDisplay;

    public NotificationDTO() {
    }

    public NotificationDTO(Long id, String title, String content, boolean pushed, boolean seen, String type, Long objectId, String objectType) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.pushed = pushed;
        this.seen = seen;
        this.type = type;
        this.objectId = objectId;
        this.objectType = objectType;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusDisplay() {
        return statusDisplay;
    }

    public void setStatusDisplay(String statusDisplay) {
        this.statusDisplay = statusDisplay;
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

    public boolean isPushed() {
        return pushed;
    }

    public boolean isSeen() {
        return seen;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }
}
