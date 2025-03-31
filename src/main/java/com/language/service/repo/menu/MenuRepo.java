package com.language.service.repo.menu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import com.language.service.domain.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepo extends JpaRepository<Menu, Long>, MenuDTORepo {
    @Query("from Menu m where m.deleted = :deleted and m.parentCode is null")
    Page<Menu> searchByMenu(Integer deleted, Pageable pageable);


    @Query("from Menu m where m.deleted = :deleted and m.parentCode is null and m.level = :level and m.parentCode in :parentCode")
    List<Menu> searchByMenuLevel(Integer deleted, int level ,List<String> parentCode);
}
