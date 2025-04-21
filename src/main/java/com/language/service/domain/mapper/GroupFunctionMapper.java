package com.language.service.domain.mapper;

import com.language.service.domain.dtos.GroupFunctionDTO;
import com.language.service.domain.entities.GroupFunction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupFunctionMapper {
    GroupFunctionDTO toDto(GroupFunction model);
    List<GroupFunctionDTO> toDtoList(List<GroupFunction> lsModels);
    GroupFunction toEntity(GroupFunctionDTO dto);
    List<GroupFunction> toEntityList(List<GroupFunctionDTO> lsGroupFunctionDto);
}
