package com.language.service.domain.mapper;

import com.language.service.domain.dtos.GroupPermissionMapDTO;
import com.language.service.domain.entities.GroupPermissionMap;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupPermissionMapMapper {
    GroupPermissionMapDTO toDto(GroupPermissionMap model);
    List<GroupPermissionMapDTO> toDtoList(List<GroupPermissionMap> lsModels);
    GroupPermissionMap toEntity(GroupPermissionMapDTO dto);
    List<GroupPermissionMap> toEntityList(List<GroupPermissionMapDTO> lsGroupPermissionMapDto);
}
