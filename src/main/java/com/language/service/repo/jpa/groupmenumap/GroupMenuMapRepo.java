package com.language.service.repo.jpa.groupmenumap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.language.service.domain.entities.GroupMenuMap;
import com.language.service.repo.dao.groupmenumap.GroupMenuMapCustomRepo;

import java.util.Optional;

public interface GroupMenuMapRepo extends JpaRepository<GroupMenuMap, Long>, GroupMenuMapCustomRepo {
    @Query("from GroupMenuMap  g where g.groupId = :groupId and g.menuId = :menuId and g.deleted = :deleted")
    Optional<GroupMenuMap> findByGroupAndMenu(Long groupId, Long menuId, Integer deleted);

    @Query("from GroupMenuMap  g where g.groupId = :groupId and g.menuId = :menuId")
    Optional<GroupMenuMap> findByGroupAndMenuWithoutDelete(Long groupId, Long menuId);
}
