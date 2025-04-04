package com.language.service.repo.jpa.grouppermissionmap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.language.service.domain.entities.GroupPermissionMap;
import com.language.service.repo.dao.grouppermissionmap.GroupPermissionMapCustomRepo;

import java.util.Optional;

public interface GroupPermissionMapRepo extends JpaRepository<GroupPermissionMap, Long>, GroupPermissionMapCustomRepo {

    @Query("from GroupPermissionMap  g where g.groupId = :groupId and g.permissionId = :permissionId and g.deleted = :deleted")
    Optional<GroupPermissionMap> findByGroupAndPermission(Long groupId, Long permissionId, Integer deleted);

    @Query("from GroupPermissionMap  g where g.groupId = :groupId and g.permissionId = :permissionId")
    Optional<GroupPermissionMap> findByGroupAndPermissionWithoutDelete(Long groupId, Long permissionId);
}
