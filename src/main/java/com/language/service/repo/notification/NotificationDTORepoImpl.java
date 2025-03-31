package com.language.service.repo.notification;

import com.language.service.rest.dto.request.searchparams.NotificationSearchParams;
import com.language.service.domain.dtos.NotificationDTO;
import com.language.service.domain.entities.Notification;
import com.language.service.domain.entities.Notification_;
import com.language.service.domain.entities.User;
import com.language.service.domain.mapper.UserMapper;
import com.language.service.repo.DynamicSearchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationDTORepoImpl extends DynamicSearchRepo<NotificationDTO, NotificationSearchParams, Notification> implements NotificationDTORepo {

    @Autowired
    public NotificationDTORepoImpl(EntityManager em) {
        super(em, NotificationDTO.class, Notification.class);
    }

    @Override
    public List<Predicate> getPredicates(CriteriaBuilder cb, Root<Notification> root, NotificationSearchParams params) {
        List<Predicate> predicates = new ArrayList<>();
        if (params != null) {
            Long userId = params.getUserId();
            if (userId != null) {
                User user = UserMapper.INSTANCE.fromId(userId);
                Predicate predicate = cb.or(cb.equal(root.get(Notification_.USER), user), cb.equal(root.get(Notification_.CREATED_BY), user));
                predicates.add(predicate);
            }
        }
        return predicates;
    }

    @Override
    public Selection[] getSelections(Root<Notification> root) {
        return new Selection[]{
                root.get(Notification_.ID),
                root.get(Notification_.TITLE),
                root.get(Notification_.CONTENT),
                root.get(Notification_.PUSHED),
                root.get(Notification_.SEEN),
                root.get(Notification_.TYPE),
                root.get(Notification_.OBJECT_ID),
                root.get(Notification_.OBJECT_TYPE)
        };
    }

    @Override
    public void markAsSent(long id) {
        em.createNativeQuery("update notification set pushed = true, last_modified_date = :date " +
                " where id = :id")
                .setParameter("id", id)
                .setParameter("date", LocalDateTime.now())
                .executeUpdate();
    }
}
