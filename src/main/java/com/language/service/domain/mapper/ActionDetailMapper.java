package com.language.service.domain.mapper;

import com.language.service.domain.dtos.ActionDetailDTO;
import com.language.service.domain.entities.ActionDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActionDetailMapper {
    @Mapping(source = "actionAuditId", target = "actionAuditId")
    ActionDetailDTO toDto(ActionDetail model);

    List<ActionDetailDTO> toDtoList(List<ActionDetail> lsModels);
    ActionDetail toEntity(ActionDetailDTO dto);
    List<ActionDetail> toEntityList(List<ActionDetailDTO> lsActionDetailDto);
}
