package com.language.service.repo.permission;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.language.service.domain.entities.Permission;

import java.util.List;


public interface PermissionRepo extends JpaRepository<Permission, Long>, PermissionRepoCustom {

    @Query("from Permission i where 1=1 and " +
            " (i.code like :code or :code is null) and (i.name like :name escape '\\' or :name is null) and i.deleted = :deleted order by i.createdDate desc ")
    Page<Permission> searchAllByMultipleFilter(String code, String name, int deleted ,Pageable pageable);


    @Query("from Permission i where lower(i.code) = lower(:code) and (i.id = :id or :id is null)")
    List<Permission> checkCodeExist(String code, Long id);

    @Query("from Permission p where p.permissionGroup in :codes")
    List<Permission> findAllByPermissionGroup(List<String> codes);

    @Query("from Permission p where p.permissionGroup = :code and p.deleted = :deleted")
    Page<Permission> findAllByPermissionGroupToPage(String code, int deleted, Pageable pageable);


}
