package com.language.service.domain.mapper;

import com.language.service.domain.dtos.FunctionCatalogDTO;
import com.language.service.domain.entities.FunctionCatalog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FunctionCatalogMapper {
    FunctionCatalogDTO toDto(FunctionCatalog model);
    List<FunctionCatalogDTO> toDtoList(List<FunctionCatalog> lsModels);
    FunctionCatalog toEntity(FunctionCatalogDTO dto);
    List<FunctionCatalog> toEntityList(List<FunctionCatalogDTO> lsFunctionCatalogDto);
}
