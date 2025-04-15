package com.language.service.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "notification")
@EntityListeners(AuditingEntityListener.class)
public class Notification extends BaseEntity implements Serializable {


    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "object_id")
    private Long objectId;

    @Column(name = "object_type")
    private String objectType;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "type")
    private String type;

    @Column(name = "pushed")
    private boolean pushed;

    @Column(name = "seen")
    private boolean seen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static class Builder {
        private final Notification notification;

        public Builder() {
            notification = new Notification();
            notification.setPushed(false);
            notification.setSeen(false);
        }

        public Builder objectId(long objectId) {
            notification.setObjectId(objectId);
            return this;
        }

        public Builder objectType(String type) {
            notification.setObjectType(type);
            return this;
        }

        public Builder title(String title) {
            notification.setTitle(title);
            return this;
        }


        public Builder content(String cont) {
            notification.setContent(cont);
            return this;
        }

        public Builder type(String type) {
            notification.setType(type);
            return this;
        }

        public Builder user(User u) {
            notification.setUser(u);
            return this;
        }

        public Builder createdBy(User u) {
            notification.setCreatedBy(u.getUsername());
            notification.setLastModifiedBy(u.getUsername());
            return this;
        }

        public Notification build() {
            if (notification.objectId == null) {
                throw new RuntimeException("Object ID must not be null");
            }
            if (notification.objectType == null) {
                throw new RuntimeException("Object type must not be null");
            }
            if (notification.title == null) {
                throw new RuntimeException("Title must not be null");
            }

            if (notification.content == null) {
                throw new RuntimeException("Content must not be null");
            }
            if (notification.type == null) {
                throw new RuntimeException("Notification type must not be null");
            }
            if (notification.user == null) {
                throw new RuntimeException("User type must not be null");
            }
            if (notification.getCreatedBy() == null) {
                throw new RuntimeException("Creator of this notification must be set");
            }

            return notification;
        }
    }


    public boolean getPushed() {
        return this.pushed;
    }

    public boolean getSeen() {
        return this.seen;
    }

}
