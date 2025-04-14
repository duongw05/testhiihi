package com.language.service.repo.user;


import com.language.service.domain.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Repository;
import com.language.service.domain.dtos.GroupIdDTO;
import com.language.service.rest.dto.request.LinkGroupsRequest;
import com.language.service.rest.dto.request.UnlinkUserGroupRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepoCustomImpl implements UserRepoCustom {

    private final EntityManager em;
    private final AuditorAware<String> auditorAware;

    @Autowired
    public UserRepoCustomImpl(EntityManager em, AuditorAware<String> auditorAware) {
        this.em = em;
        this.auditorAware = auditorAware;
    }

    @Override
    public void unlinkGroup(long userId, UnlinkUserGroupRequest command) {
        if (command != null && command.getGroupId() != null) {
            em.createNativeQuery("delete from user_group_map where user_id = :userId and group_id = :groupId")
                    .setParameter("userId", userId)
                    .setParameter("groupId", command.getGroupId())
                    .executeUpdate();
        }

    }

    @Override
    public List<Long> getGroupIdsOfUser(long userId) {
        TypedQuery<GroupIdDTO> query = em.createNamedQuery("getGroupIdsOfUser", GroupIdDTO.class)
                .setParameter("userId", userId);
        List<GroupIdDTO> res = query.getResultList();
        List<Long> groupIds = new ArrayList<>();
        if (res != null && !res.isEmpty()) {
            res.forEach(group -> groupIds.add(group.getId()));
        }
        return groupIds;
    }

    @Override
    public void linkGroups(long userId, LinkGroupsRequest command) {
        Query query = em.createNativeQuery("insert into user_group_map (user_id, group_id) VALUES (:userId, :groupId)");
        if (command != null && command.getGroupIds() != null && !command.getGroupIds().isEmpty()) {
            for (Long groupId : command.getGroupIds()) {
                query.setParameter("userId", userId);
                query.setParameter("groupId", groupId);
                query.executeUpdate();
            }

            Optional<String> optActor = auditorAware.getCurrentAuditor();
            if (optActor.isPresent()) {
                Query updateLastModified = em.createNativeQuery(
                        "update tbl_user set last_modified_by = :user, last_modified_date = :date " +
                                " where id = :userId");
                updateLastModified.setParameter("user", optActor.get());
                updateLastModified.setParameter("date", LocalDateTime.now());
                updateLastModified.setParameter("userId", userId);
                updateLastModified.executeUpdate();
            }

        }
    }
@Override
public List<User> findUsersNotInGroup(Long groupId, String quickSearch, int deleted) {
    String queryStr = "SELECT u.* " +
            "FROM tbl_user u " +
            "WHERE u.id NOT IN (SELECT ugm.USER_ID FROM user_group_map ugm WHERE ugm.GROUP_ID = :groupId) " +
            "AND u.deleted = :deleted " +
            "AND ((u.username LIKE :quickSearch OR u.full_name LIKE :quickSearch) OR :quickSearch IS NULL) " +
            "LIMIT 50";
    Query query = em.createNativeQuery(queryStr, User.class);
    query.setParameter("groupId", groupId);
    query.setParameter("deleted", deleted);
    query.setParameter("quickSearch", quickSearch);
    return query.getResultList();
}
}
