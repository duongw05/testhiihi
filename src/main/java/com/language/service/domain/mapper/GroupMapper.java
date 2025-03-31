package com.language.service.domain.mapper;

import com.language.service.rest.dto.request.CreateGroupRequest;
import com.language.service.rest.dto.request.UpdateGroupRequest;
import com.language.service.domain.dtos.GroupDTO;
import com.language.service.domain.entities.Group;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface GroupMapper {

    default Group fromId(long id) {
        Group group = new Group();
        group.setId(id);
        return group;
    }

    GroupDTO toDTO(Group group);
    List<GroupDTO> toDTO(List<Group> group);

    Group create(CreateGroupRequest command);

    void update(UpdateGroupRequest command, @MappingTarget Group group);
}
