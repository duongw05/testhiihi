package com.language.service.repo.dao.menumanagement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.language.service.domain.dtos.*;

import java.util.List;

public interface MenuManagementCustomRepo {
    Page<MenuManagementDTO> searchAllMenu(SearchMenuDTO request, Pageable pageable) throws Exception;

    List<MenuTreeDTO> searchTreeMenu() throws Exception;

    List<MenuTreeDTOV2> getTreeMenu();

    List<MenuTreeDTOV2> getTreeMenuByIdGroup(List<Long> idGroups) throws Exception;

    List<MenuPermissionTreeDTO> searchTreeMenuV2() throws Exception;
}
