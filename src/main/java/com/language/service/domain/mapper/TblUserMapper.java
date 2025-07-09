package com.language.service.domain.mapper;

import com.language.service.domain.dtos.TblUserDTO;
import com.language.service.domain.entities.TblUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TblUserMapper {
    TblUserDTO toDto(TblUser model);
    List<TblUserDTO> toDtoList(List<TblUser> lsModels);
    TblUser toEntity(TblUserDTO dto);
    List<TblUser> toEntityList(List<TblUserDTO> lsTblUserDto);
}
