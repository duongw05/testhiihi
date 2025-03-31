package com.language.service.repo.dao.groupmenumap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.language.service.domain.dtos.MenuDTO;
import com.language.service.rest.dto.request.SearchMenuPermissionDTO;

import java.util.List;

public interface GroupMenuMapCustomRepo {

    Page<MenuDTO> getMenuUserGroupAll(long groupId, SearchMenuPermissionDTO searchMenuPermissionDTO, Pageable pageable);
    Page<MenuDTO> getMenuLowLevel(int deleted,long groupId, Pageable pageable);
    List<MenuDTO> getAllChildMenus(long groupId);
    List<MenuDTO> getMenuUserGroupByLevel(List<String> parentCode, long groupId, int level);
}