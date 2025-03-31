package com.language.service.domain.mapper;

import com.language.service.domain.dtos.UserGroupMapDTO;
import com.language.service.domain.entities.UserGroupMap;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserGroupMapMapper {
    UserGroupMapDTO toDto(UserGroupMap model);
    List<UserGroupMapDTO> toDtoList(List<UserGroupMap> lsModels);
    UserGroupMap toEntity(UserGroupMapDTO dto);
    List<UserGroupMap> toEntityList(List<UserGroupMapDTO> lsUserGroupMapDto);
}
