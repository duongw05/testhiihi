package com.language.service.service.impl.tbluser;

import com.language.service.domain.mapper.TblUserMapper;
import com.language.service.rest.dto.response.BaseResponseDTO;
import com.language.service.domain.dtos.TblUserDTO;
import com.language.service.domain.entities.TblUser;
import com.language.service.repo.jpa.tbluser.TblUserRepo;
import com.language.service.service.abs.tbluser.TblUserService;
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
public class TblUserServiceImpl implements TblUserService {
    private static final Logger logger = LoggerFactory.getLogger(TblUserServiceImpl.class);
    private final TblUserMapper mapper;
    private final TblUserRepo repo;

    public TblUserServiceImpl(TblUserMapper mapper, TblUserRepo repo) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponseDTO saveTblUser(TblUserDTO request) {
        try {
            TblUser entity = mapper.toEntity(request);
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
    public BaseResponseDTO updateTblUser(TblUserDTO request) {
        try {
            if (request.getId() == null) throw new BusinessException("Id is required");
            TblUser domain = repo.findById(request.getId())
                    .orElseThrow(() -> new BusinessException("Could not find id " + request.getId()));
            TblUser entity = mapper.toEntity(request);
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
    public BaseResponseDTO deleteTblUser(Long id) {
        try {
            TblUser entity = repo.findById(id).orElseThrow(() -> new BusinessException("Could not find id " + id));
            entity.setDeleted(Constants.DELETE.ACTIVE);
            return new BaseResponseDTO();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<TblUserDTO> getAll() {
        List<TblUser> list = repo.findAllEmployee();
        return mapper.toDtoList(list);
    }

    @Override
    public Page<TblUserDTO> search(TblUserDTO request, Pageable pageable) {
        try {
            Page<TblUser> rs = repo.findAll(pageable);
            return new PageImpl<>(mapper.toDtoList(rs.getContent()), pageable, rs.getTotalElements());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }
}
