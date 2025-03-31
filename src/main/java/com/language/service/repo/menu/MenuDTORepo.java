package com.language.service.repo.menu;

import com.language.service.rest.dto.request.searchparams.MenuSearchParams;
import com.language.service.domain.dtos.MenuDTO;
import com.language.service.repo.BaseSearchRepo;

import java.util.List;

public interface MenuDTORepo extends BaseSearchRepo<MenuDTO, MenuSearchParams> {
    List<MenuDTO> getAll();

    List<String> getMenuCodesByUser(long userId);

}
