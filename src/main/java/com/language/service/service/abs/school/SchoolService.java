package com.language.service.service.abs.school;

import com.language.service.domain.dtos.SearchSchoolDTO;
import com.language.service.rest.dto.response.BaseResponseDTO;
import com.language.service.domain.dtos.SchoolDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.Optional;

public interface SchoolService {
    Page<SchoolDTO> search(SearchSchoolDTO schoolDTO, Pageable pageable);
    SchoolDTO detail(Long id);
    BaseResponseDTO saveSchool(SchoolDTO schoolDTO);

    BaseResponseDTO updateSchool(SchoolDTO schoolDTO);
    BaseResponseDTO deleteSchool(Long id);
     void xuatExcel(SearchSchoolDTO dto, HttpServletResponse response) throws IOException;

}
