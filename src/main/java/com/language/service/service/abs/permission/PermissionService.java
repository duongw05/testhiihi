package com.language.service.service.abs.permission;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.language.service.domain.dtos.PermissionDTO;
import com.language.service.rest.dto.response.BaseResponseDTO;
import com.language.service.rest.dto.response.GroupPermissionResponseDTO;

import java.util.List;

public interface PermissionService {
    List<String> allPermCode(long userId);

    List<PermissionDTO> findAllDTO();

    Page<GroupPermissionResponseDTO> findAllDTO(long groupId, Pageable pageable);

    BaseResponseDTO linkPermissionWithGroup(long id, long groupId);

    BaseResponseDTO unlinkPermissionWithGroup(long id, long groupId);

    BaseResponseDTO savePermission(PermissionDTO request);

    BaseResponseDTO updatePermission(PermissionDTO request);

    BaseResponseDTO deletePermission(Long id);

    Page<PermissionDTO> search(PermissionDTO request, Pageable pageable);

    Page<PermissionDTO> findAllByPermissionGroupToPage(String menuCode, Pageable pageable);
}
