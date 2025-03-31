package com.language.service.service.abs.menumanagement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.language.service.domain.dtos.*;
import com.language.service.rest.dto.response.BaseResponseDTO;

import java.util.List;
import java.util.Map;

public interface MenuManagementService {

    BaseResponseDTO saveMenusManagement(MenuManagementDTO menuDTO);

    BaseResponseDTO updateMenusManagement(MenuManagementDTO menuDTO);

    BaseResponseDTO deleteMenusManagement(Long id);

    List<MenuPermissionTreeDTO>  searchTreeMenuV2() throws Exception;

    List<MenuManagementDTO> searchAllChild(String code);

    Page<MenuManagementDTO> searchAll(SearchMenuDTO request, Pageable pageable) throws Exception;

    List<MenuTreeDTO> searchTreeMenu() throws Exception;

    /** Lấy cây menu*/
    List<MenuTreeDTOV2> getMenusTree(Long userId) throws Exception;
}
