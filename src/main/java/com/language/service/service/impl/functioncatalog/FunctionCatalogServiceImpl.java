package com.language.service.service.impl.functioncatalog;

import com.language.service.common.utils.DataUtils;
import com.language.service.domain.mapper.FunctionCatalogMapper;
import com.language.service.rest.dto.response.BaseResponseDTO;
import com.language.service.domain.dtos.FunctionCatalogDTO;
import com.language.service.domain.entities.FunctionCatalog;
import com.language.service.repo.jpa.functioncatalog.FunctionCatalogRepo;
import com.language.service.service.abs.functioncatalog.FunctionCatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.language.service.exception.BusinessException;
import com.language.service.common.Constants;

@Service
public class FunctionCatalogServiceImpl implements FunctionCatalogService {
    private static final Logger logger = LoggerFactory.getLogger(FunctionCatalogServiceImpl.class);
    private final FunctionCatalogMapper mapper;
    private final FunctionCatalogRepo repo;

    public FunctionCatalogServiceImpl(FunctionCatalogMapper mapper, FunctionCatalogRepo repo) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponseDTO saveFunctionCatalog(FunctionCatalogDTO request) {
        try {
            FunctionCatalog entity = mapper.toEntity(request);
            entity.setStatus(Constants.STATUS.ACTIVE);
            entity.setDeleted(Constants.DELETE.INACTIVE);
            repo.save(entity);
            return new BaseResponseDTO();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponseDTO updateFunctionCatalog(FunctionCatalogDTO request) {
        try {
            if (request.getId() == null) throw new BusinessException("Id is required");
            FunctionCatalog domain = repo.findById(request.getId())
                    .orElseThrow(() -> new BusinessException("Could not find id " + request.getId()));
            FunctionCatalog entity = mapper.toEntity(request);
            entity.setCreatedBy(domain.getCreatedBy());
            entity.setCreatedDate(domain.getCreatedDate());
            repo.save(entity);
            return new BaseResponseDTO();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponseDTO deleteFunctionCatalog(Long id) {
        try {
            FunctionCatalog entity = repo.findById(id).orElseThrow(() -> new BusinessException("Could not find id " + id));
            entity.setDeleted(Constants.DELETE.ACTIVE);
            return new BaseResponseDTO();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<FunctionCatalogDTO> search(FunctionCatalogDTO request, Pageable pageable) {
        try {
            Page<FunctionCatalog> rs = repo.findByFunctionCodeAndFunctionName(DataUtils.makeLikeQuery(request.getFunctionCode()),
                    DataUtils.makeLikeQuery(request.getFunctionName()), Constants.STATUS.ACTIVE, Constants.DELETE.INACTIVE, pageable);
            return new PageImpl<>(mapper.toDtoList(rs.getContent()), pageable, rs.getTotalElements());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }
}
