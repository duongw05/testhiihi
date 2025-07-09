package com.language.service.domain.mapper;

import com.language.service.domain.dtos.SchoolTypeDTO;
import com.language.service.domain.entities.SchoolType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SchoolTypeMapper {
    SchoolTypeDTO toDto(SchoolType model);
    List<SchoolTypeDTO> toDtoList(List<SchoolType> lsModels);
    SchoolType toEntity(SchoolTypeDTO dto);
    List<SchoolType> toEntityList(List<SchoolTypeDTO> lsSchoolTypeDto);
}
