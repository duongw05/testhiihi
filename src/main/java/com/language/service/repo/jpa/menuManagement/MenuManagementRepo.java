package com.language.service.repo.jpa.menuManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.language.service.domain.entities.Menu;
import com.language.service.repo.dao.menumanagement.MenuManagementCustomRepo;

import java.util.List;
import java.util.Optional;

public interface MenuManagementRepo extends JpaRepository<Menu, Long>, MenuManagementCustomRepo {

    @Query("from Menu m where lower(m.code) = lower(:code) and m.deleted = :deleted")
    Optional<Menu> findByCode(String code, int deleted);

    @Query("from Menu m where m.id <> :id and m.code = :code and m.deleted =:deleted")
    Optional<Menu> checkExistsByIdAndErrorCode(Long id, String code, int deleted);

    @Query("from Menu m where  m.parentCode = :code and m.deleted =:deleted")
    List<Menu> findAllMenuChildByCode(String code, int deleted);

    @Query("from Menu m where (m.code is not null)  and (m.parentCode = :parentCode) and m.deleted =:deleted ")
    List<Menu> searchAlLChildMenu(@Param("parentCode") String parentCode, int deleted);

}
