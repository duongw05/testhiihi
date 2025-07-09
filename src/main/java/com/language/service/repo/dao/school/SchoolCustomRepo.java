package com.language.service.repo.dao.school;

import com.language.service.domain.dtos.SchoolDTO;
import com.language.service.domain.dtos.SearchMenuDTO;
import com.language.service.domain.dtos.SearchSchoolDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SchoolCustomRepo {
    Page<SchoolDTO> searchAll(SearchSchoolDTO request, Pageable pageable) throws Exception;
    List<SchoolDTO> xuatExcel(SearchSchoolDTO request);

}
