package com.language.service.repo.jpa.groupfunction;

import com.language.service.domain.entities.GroupFunction;
import com.language.service.repo.dao.groupfunction.GroupFunctionCustomRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface GroupFunctionRepo extends JpaRepository< GroupFunction, Long>, GroupFunctionCustomRepo  {
    @Query("SELECT gf FROM GroupFunction gf WHERE gf.groupId = :groupId AND gf.deleted = 0")
    List<GroupFunction> findByGroupId(Long groupId);

    @Query("SELECT gf FROM GroupFunction gf WHERE gf.groupId = :groupId AND gf.functionId = :functionId AND gf.deleted = 0")
    GroupFunction findByGroupIdAndFunctionId(Long groupId, Long functionId);
}
