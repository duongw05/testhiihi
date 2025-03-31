package com.language.service.domain.mapper;

import com.language.service.domain.dtos.MenuDTO;
import com.language.service.domain.entities.Menu;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {

    default Menu fromId(long id) {
        Menu m = new Menu();
        m.setId(id);
        return m;
    }

    MenuDTO toMenuDTO(Menu menu);
    List<MenuDTO> toMenuDTO(List<Menu> menu);
}
