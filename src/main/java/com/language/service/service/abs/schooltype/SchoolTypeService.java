package com.language.service.service.abs.schooltype;

import com.language.service.rest.dto.response.BaseResponseDTO;
import com.language.service.domain.dtos.SchoolTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SchoolTypeService {
    List<SchoolTypeDTO> getAll();
    Page<SchoolTypeDTO> search(SchoolTypeDTO schoolTypeDTO, Pageable pageable);

    BaseResponseDTO saveSchoolType(SchoolTypeDTO schoolTypeDTO);

    BaseResponseDTO updateSchoolType(SchoolTypeDTO schoolTypeDTO);

    BaseResponseDTO deleteSchoolType(Long id);

}
