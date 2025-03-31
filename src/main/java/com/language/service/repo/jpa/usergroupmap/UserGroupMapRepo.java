package com.language.service.repo.jpa.usergroupmap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.language.service.domain.entities.UserGroupMap;
import com.language.service.repo.dao.usergroupmap.UserGroupMapCustomRepo;

import java.util.List;

public interface UserGroupMapRepo extends JpaRepository<UserGroupMap, Long>, UserGroupMapCustomRepo {

    @Query("from UserGroupMap  u where u.user.id = :id and u.status = :status and u.deleted = :deleted")
    List<UserGroupMap> findAllByUserAndStatusAndDeleted(Long id, Integer status, Integer deleted);
}
