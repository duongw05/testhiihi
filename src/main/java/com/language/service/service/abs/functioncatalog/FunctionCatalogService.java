package com.language.service.service.abs.functioncatalog;

import com.language.service.domain.dtos.FunctionTreeDTO;
import com.language.service.rest.dto.response.BaseResponseDTO;
import com.language.service.domain.dtos.FunctionCatalogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FunctionCatalogService {
    List<String> allPermCode(long userId);

    Page<FunctionCatalogDTO> search(FunctionCatalogDTO functionCatalogDTO, Pageable pageable);

    BaseResponseDTO saveFunctionCatalog(FunctionCatalogDTO functionCatalogDTO);

    BaseResponseDTO updateFunctionCatalog(FunctionCatalogDTO functionCatalogDTO);

    BaseResponseDTO deleteFunctionCatalog(Long id);
    BaseResponseDTO getFunctionCatalogTree();
}
