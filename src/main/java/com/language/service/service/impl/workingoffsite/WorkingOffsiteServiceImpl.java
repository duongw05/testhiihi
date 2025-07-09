package com.language.service.service.impl.workingoffsite;

import com.language.service.domain.mapper.WorkingOffsiteMapper;
import com.language.service.rest.dto.response.BaseResponseDTO;
import com.language.service.domain.dtos.WorkingOffsiteDTO;
import com.language.service.domain.entities.WorkingOffsite;
import com.language.service.repo.jpa.workingoffsite.WorkingOffsiteRepo;
import com.language.service.service.abs.workingoffsite.WorkingOffsiteService;
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
public class WorkingOffsiteServiceImpl implements WorkingOffsiteService {
    private static final Logger logger = LoggerFactory.getLogger(WorkingOffsiteServiceImpl.class);
    private final WorkingOffsiteMapper mapper;
    private final WorkingOffsiteRepo repo;

    public WorkingOffsiteServiceImpl(WorkingOffsiteMapper mapper, WorkingOffsiteRepo repo) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponseDTO saveWorkingOffsite(WorkingOffsiteDTO request) {
        try {
            WorkingOffsite entity = mapper.toEntity(request);
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
    public BaseResponseDTO updateWorkingOffsite(WorkingOffsiteDTO request) {
        try {
            if (request.getId() == null) throw new BusinessException("Id is required");
            WorkingOffsite domain = repo.findById(request.getId())
                    .orElseThrow(() -> new BusinessException("Could not find id " + request.getId()));
            WorkingOffsite entity = mapper.toEntity(request);
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
    public BaseResponseDTO deleteWorkingOffsite(Long id) {
        try {
            WorkingOffsite entity = repo.findById(id).orElseThrow(() -> new BusinessException("Could not find id " + id));
            entity.setDeleted(Constants.DELETE.ACTIVE);
            return new BaseResponseDTO();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<WorkingOffsiteDTO> getAll() {
        List<WorkingOffsite> list = repo.getAll();
        return mapper.toDtoList(list);
    }

    @Override
    public Page<WorkingOffsiteDTO> search(WorkingOffsiteDTO request, Pageable pageable) {
        try {
            Page<WorkingOffsite> rs = repo.findAll(pageable);
            return new PageImpl<>(mapper.toDtoList(rs.getContent()), pageable, rs.getTotalElements());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }
}
