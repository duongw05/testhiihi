package com.language.service.repo.notification;

import com.language.service.domain.dtos.NotificationDetailsDTO;
import com.language.service.domain.entities.Notification;
import com.language.service.domain.entities.Notification_;
import com.language.service.domain.entities.User_;
import com.language.service.repo.AbstractDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;

@Repository
public class NotificationDetailsDTORepoImpl extends AbstractDetailsRepo<NotificationDetailsDTO, Notification, Long> implements NotificationDetailsDTORepo {

    @Autowired
    public NotificationDetailsDTORepoImpl(EntityManager em) {
        super(em, NotificationDetailsDTO.class, Notification.class);
    }

    @Override
    public Selection[] getDetailsSelections(Root<Notification> root) {
        return new Selection[]{
                root.get(Notification_.ID),
                root.get(Notification_.TITLE),
                root.get(Notification_.CONTENT),
                root.get(Notification_.PUSHED),
                root.get(Notification_.SEEN),
                root.get(Notification_.OBJECT_ID),
                root.get(Notification_.OBJECT_TYPE),
                root.get(Notification_.TYPE),
                root.get(Notification_.CREATED_DATE),
                root.get(Notification_.LAST_MODIFIED_DATE),
                root.join(Notification_.USER, JoinType.LEFT).get(User_.ID),
                root.join(Notification_.USER, JoinType.LEFT).get(User_.FULL_NAME),
                root.join(Notification_.CREATED_BY, JoinType.LEFT).get(User_.ID),
                root.join(Notification_.CREATED_BY, JoinType.LEFT).get(User_.FULL_NAME),
                root.join(Notification_.LAST_MODIFIED_BY, JoinType.LEFT).get(User_.ID),
                root.join(Notification_.LAST_MODIFIED_BY, JoinType.LEFT).get(User_.FULL_NAME)
        };
    }
}
