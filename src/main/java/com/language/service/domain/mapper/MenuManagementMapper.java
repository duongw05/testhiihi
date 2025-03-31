package com.language.service.domain.mapper;

import org.mapstruct.Mapper;
import com.language.service.domain.dtos.MenuManagementDTO;
import com.language.service.domain.entities.Menu;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuManagementMapper {
    MenuManagementDTO toDto(Menu model);

    List<MenuManagementDTO> toDtoList(List<Menu> lsModels);

    Menu toEntity(MenuManagementDTO dto);

    List<Menu> toEntityList(List<MenuManagementDTO> lsMenuManagementDto);
}
