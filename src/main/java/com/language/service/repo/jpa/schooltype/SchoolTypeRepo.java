package com.language.service.repo.jpa.schooltype;

import com.language.service.domain.dtos.SchoolTypeDTO;
import com.language.service.domain.entities.SchoolType;
import com.language.service.repo.dao.schooltype.SchoolTypeCustomRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SchoolTypeRepo extends JpaRepository< SchoolType, Long>, SchoolTypeCustomRepo  {
    @Query("""
    from SchoolType st where st.deleted = 1
           """)
    List<SchoolTypeDTO> getAll();
}
