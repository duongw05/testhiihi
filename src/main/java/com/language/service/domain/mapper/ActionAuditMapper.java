package com.language.service.domain.mapper;

import com.language.service.domain.dtos.ActionAuditDTO;
import com.language.service.domain.entities.ActionAudit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActionAuditMapper {
    ActionAuditDTO toDto(ActionAudit model);
    List<ActionAuditDTO> toDtoList(List<ActionAudit> lsModels);
    ActionAudit toEntity(ActionAuditDTO dto);
    List<ActionAudit> toEntityList(List<ActionAuditDTO> lsActionAuditDto);
}
