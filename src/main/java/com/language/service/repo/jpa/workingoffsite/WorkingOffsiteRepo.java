package com.language.service.repo.jpa.workingoffsite;

import com.language.service.domain.entities.WorkingOffsite;
import com.language.service.repo.dao.workingoffsite.WorkingOffsiteCustomRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkingOffsiteRepo extends JpaRepository< WorkingOffsite, Long>, WorkingOffsiteCustomRepo  {
    @Query("""
    from WorkingOffsite w where w.deleted = 1
""")
    List<WorkingOffsite> getAll();
}
