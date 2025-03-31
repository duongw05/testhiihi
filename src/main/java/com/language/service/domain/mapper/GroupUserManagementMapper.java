package com.language.service.domain.mapper;

import org.mapstruct.Mapper;
import com.language.service.domain.dtos.GroupUserManagementDTO;
import com.language.service.domain.entities.Group;

import java.util.List;


@Mapper
public interface GroupUserManagementMapper {

    GroupUserManagementDTO toDTO(Group group);
    List<GroupUserManagementDTO> toDTO(List<Group> group);

    Group toEntity(GroupUserManagementDTO dto);
    List<Group> toEntityList(List<GroupUserManagementDTO> lsDto);
}
