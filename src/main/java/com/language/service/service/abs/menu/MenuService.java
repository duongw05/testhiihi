package com.language.service.service.abs.menu;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.language.service.domain.dtos.MenuDTO;
import com.language.service.domain.entities.Menu;
import com.language.service.rest.dto.request.SearchMenuPermissionDTO;
import com.language.service.rest.dto.response.BaseResponseDTO;
import com.language.service.service.abs.base.BaseService;

import java.util.List;

public interface MenuService extends BaseService<Menu, Long> {

    List<MenuDTO> getAll();

    List<String> getMenuCodesByUser(long userId);

    Page<MenuDTO> searchPageMenu(long groupId, SearchMenuPermissionDTO searchMenuPermissionDTO, Pageable pageable);

    BaseResponseDTO linkMenuWithGroup(long id, long groupId);

    BaseResponseDTO unlinkMenuWithGroup(long id, long groupId);
}
