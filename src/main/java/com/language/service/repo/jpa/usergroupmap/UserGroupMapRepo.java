package com.language.service.repo.jpa.usergroupmap;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.language.service.domain.entities.UserGroupMap;
import com.language.service.repo.dao.usergroupmap.UserGroupMapCustomRepo;

import java.util.List;
import java.util.Optional;

public interface UserGroupMapRepo extends JpaRepository<UserGroupMap, Long>, UserGroupMapCustomRepo {

    @Query("from UserGroupMap  u where u.userId = :id and u.status = :status and u.deleted = :deleted")
    List<UserGroupMap> findAllByUserAndStatusAndDeleted(Long id, Integer status, Integer deleted);

    @Query("SELECT u FROM UserGroupMap u WHERE u.userId = :userId AND u.groupId = :groupId AND u.deleted = :deleted AND u.status = :status")
    Optional<UserGroupMap> findByUserIdAndGroupId(Long userId, Long groupId, Integer status, Integer deleted);
}
