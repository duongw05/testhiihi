package com.language.service.service.impl.schooltype;

import com.language.service.domain.mapper.SchoolTypeMapper;
import com.language.service.rest.dto.response.BaseResponseDTO;
import com.language.service.domain.dtos.SchoolTypeDTO;
import com.language.service.domain.entities.SchoolType;
import com.language.service.repo.jpa.schooltype.SchoolTypeRepo;
import com.language.service.service.abs.schooltype.SchoolTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.language.service.exception.BusinessException;
import com.language.service.common.Constants;

import java.util.List;

@Service
public class SchoolTypeServiceImpl implements SchoolTypeService {
    private static final Logger logger = LoggerFactory.getLogger(SchoolTypeServiceImpl.class);
    private final SchoolTypeMapper mapper;
    private final SchoolTypeRepo repo;

    public SchoolTypeServiceImpl(SchoolTypeMapper mapper, SchoolTypeRepo repo) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponseDTO saveSchoolType(SchoolTypeDTO request) {
        try {
            SchoolType entity = mapper.toEntity(request);
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
    public BaseResponseDTO updateSchoolType(SchoolTypeDTO request) {
        try {
            if (request.getId() == null) throw new BusinessException("Id is required");
            SchoolType domain = repo.findById(request.getId())
                    .orElseThrow(() -> new BusinessException("Could not find id " + request.getId()));
            SchoolType entity = mapper.toEntity(request);
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
    public BaseResponseDTO deleteSchoolType(Long id) {
        try {
            SchoolType entity = repo.findById(id).orElseThrow(() -> new BusinessException("Could not find id " + id));
            entity.setDeleted(Constants.DELETE.ACTIVE);
            return new BaseResponseDTO();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<SchoolTypeDTO> getAll() {
        List<SchoolType> entities = repo.findAll();
        return mapper.toDtoList(entities);
    }

    @Override
    public Page<SchoolTypeDTO> search(SchoolTypeDTO request, Pageable pageable) {
        try {
            Page<SchoolType> rs = repo.findAll(pageable);
            return new PageImpl<>(mapper.toDtoList(rs.getContent()), pageable, rs.getTotalElements());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }
}
