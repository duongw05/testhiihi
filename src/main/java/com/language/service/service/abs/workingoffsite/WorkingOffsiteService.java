package com.language.service.service.abs.workingoffsite;

import com.language.service.rest.dto.response.BaseResponseDTO;
import com.language.service.domain.dtos.WorkingOffsiteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WorkingOffsiteService {
    List<WorkingOffsiteDTO> getAll();
    Page<WorkingOffsiteDTO> search(WorkingOffsiteDTO workingOffsiteDTO, Pageable pageable);

    BaseResponseDTO saveWorkingOffsite(WorkingOffsiteDTO workingOffsiteDTO);

    BaseResponseDTO updateWorkingOffsite(WorkingOffsiteDTO workingOffsiteDTO);

    BaseResponseDTO deleteWorkingOffsite(Long id);

}
