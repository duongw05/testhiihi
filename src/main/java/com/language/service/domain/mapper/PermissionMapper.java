package com.language.service.domain.mapper;

import org.mapstruct.Mapper;
import com.language.service.domain.dtos.PermissionDTO;
import com.language.service.domain.entities.Permission;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    default Permission fromId(long id) {
        Permission Permission = new Permission();
        Permission.setId(id);
        return Permission;
    }

    PermissionDTO toDTO(Permission Permission);

    List<PermissionDTO> toDTOs(List<Permission> Permission);

    Permission toEntity(PermissionDTO Permission);

    List<PermissionDTO> toDTO(List<Permission> Permission);

    List<Permission> toEntity(List<PermissionDTO> Permission);

}
