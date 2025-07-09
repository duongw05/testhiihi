package com.language.service.repo.jpa.school;

import com.language.service.domain.entities.School;
import com.language.service.repo.dao.school.SchoolCustomRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SchoolRepo extends JpaRepository< School, Long>, SchoolCustomRepo  {

    @Query("""
    SELECT s FROM School s
    LEFT JOIN FETCH s.workingOffsite
    LEFT JOIN FETCH s.schoolType
    WHERE s.id = :id
""")
    Optional<School> findWithJoinsById(@Param("id") Long id);

}
