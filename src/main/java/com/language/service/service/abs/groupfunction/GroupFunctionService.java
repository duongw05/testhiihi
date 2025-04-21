package com.language.service.service.abs.groupfunction;

import com.language.service.rest.dto.response.BaseResponseDTO;
import com.language.service.domain.dtos.GroupFunctionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GroupFunctionService {
    Page<GroupFunctionDTO> search(GroupFunctionDTO groupFunctionDTO, Pageable pageable);

    BaseResponseDTO addGroupFunction(GroupFunctionDTO groupFunctionDTO);

    BaseResponseDTO updateGroupFunction(GroupFunctionDTO groupFunctionDTO);

    BaseResponseDTO deleteGroupFunction(Long id);
    BaseResponseDTO saveGroupFunction(GroupFunctionDTO request);
    List<GroupFunctionDTO> getGroupFunctionData(GroupFunctionDTO request);
}
