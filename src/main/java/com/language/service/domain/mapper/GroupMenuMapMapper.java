package com.language.service.domain.mapper;

import com.language.service.domain.dtos.GroupMenuMapDTO;
import com.language.service.domain.entities.GroupMenuMap;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMenuMapMapper {
    GroupMenuMapDTO toDto(GroupMenuMap model);
    List<GroupMenuMapDTO> toDtoList(List<GroupMenuMap> lsModels);
    GroupMenuMap toEntity(GroupMenuMapDTO dto);
    List<GroupMenuMap> toEntityList(List<GroupMenuMapDTO> lsGroupMenuMapDto);
}
