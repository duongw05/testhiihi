package com.language.service.service.abs.tbluser;

import com.language.service.rest.dto.response.BaseResponseDTO;
import com.language.service.domain.dtos.TblUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TblUserService {
    List<TblUserDTO> getAll();
    Page<TblUserDTO> search(TblUserDTO tblUserDTO, Pageable pageable);

    BaseResponseDTO saveTblUser(TblUserDTO tblUserDTO);

    BaseResponseDTO updateTblUser(TblUserDTO tblUserDTO);

    BaseResponseDTO deleteTblUser(Long id);

}
