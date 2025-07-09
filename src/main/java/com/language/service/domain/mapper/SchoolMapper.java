package com.language.service.domain.mapper;

import com.language.service.domain.dtos.SchoolDTO;
import com.language.service.domain.entities.School;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SchoolMapper {

    @Mapping(target = "workingOffsiteId", source = "workingOffsite.id")
    @Mapping(target = "schoolTypeId", source = "schoolType.id")
    @Mapping(target = "workingOffsiteName", source = "workingOffsite.name")
    @Mapping(target = "schoolTypeName", source = "schoolType.name")
    SchoolDTO toDto(School model);

    List<SchoolDTO> toDtoList(List<School> lsModels);

    School toEntity(SchoolDTO dto);

    List<School> toEntityList(List<SchoolDTO> lsSchoolDto);
}
