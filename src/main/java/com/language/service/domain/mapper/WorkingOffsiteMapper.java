package com.language.service.domain.mapper;

import com.language.service.domain.dtos.WorkingOffsiteDTO;
import com.language.service.domain.entities.WorkingOffsite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkingOffsiteMapper {
    WorkingOffsiteDTO toDto(WorkingOffsite model);
    List<WorkingOffsiteDTO> toDtoList(List<WorkingOffsite> lsModels);
    WorkingOffsite toEntity(WorkingOffsiteDTO dto);
    List<WorkingOffsite> toEntityList(List<WorkingOffsiteDTO> lsWorkingOffsiteDto);
}
