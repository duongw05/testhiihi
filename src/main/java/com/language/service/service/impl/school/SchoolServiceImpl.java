package com.language.service.service.impl.school;

import com.language.service.common.utils.excel.SchoolExcelExporter;
import com.language.service.domain.dtos.SearchSchoolDTO;
import com.language.service.domain.entities.SchoolType;
import com.language.service.domain.entities.WorkingOffsite;
import com.language.service.domain.mapper.SchoolMapper;
import com.language.service.repo.jpa.schooltype.SchoolTypeRepo;
import com.language.service.repo.jpa.workingoffsite.WorkingOffsiteRepo;
import com.language.service.rest.dto.response.BaseResponseDTO;
import com.language.service.domain.dtos.SchoolDTO;
import com.language.service.domain.entities.School;
import com.language.service.repo.jpa.school.SchoolRepo;
import com.language.service.service.abs.school.SchoolService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.language.service.exception.BusinessException;
import com.language.service.common.Constants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class SchoolServiceImpl implements SchoolService {
    private static final Logger logger = LoggerFactory.getLogger(SchoolServiceImpl.class);
    private final SchoolMapper mapper;
    private final SchoolRepo repo;
    private final SchoolTypeRepo schoolTypeRepo;
    private final WorkingOffsiteRepo workingOffsiteRepo;

    public SchoolServiceImpl(SchoolMapper mapper, SchoolRepo repo, SchoolTypeRepo schoolTypeRepo, WorkingOffsiteRepo workingOffsiteRepo) {
        this.repo = repo;
        this.mapper = mapper;
        this.schoolTypeRepo = schoolTypeRepo;
        this.workingOffsiteRepo = workingOffsiteRepo;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponseDTO saveSchool(SchoolDTO request) {
        try {
            School entity = mapper.toEntity(request);

            if (request.getWorkingOffsiteId() != null) {
                WorkingOffsite wo = workingOffsiteRepo.findById(request.getWorkingOffsiteId())
                        .orElseThrow(() -> new BusinessException("Không tìm thấy WorkingOffsite với id: " + request.getWorkingOffsiteId()));
                entity.setWorkingOffsite(wo);
            }

            if (request.getSchoolTypeId() != null) {
                SchoolType type = schoolTypeRepo.findById(request.getSchoolTypeId())
                        .orElseThrow(() -> new BusinessException("Không tìm thấy SchoolType với id: " + request.getSchoolTypeId()));
                entity.setSchoolType(type);
            }

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
    public BaseResponseDTO updateSchool(SchoolDTO request) {
        try {
            if (request.getId() == null) throw new BusinessException("Id is required");

            School domain = repo.findById(request.getId())
                    .orElseThrow(() -> new BusinessException("Không tìm thấy School với id: " + request.getId()));

            School entity = mapper.toEntity(request);

            if (request.getWorkingOffsiteId() != null) {
                WorkingOffsite wo = workingOffsiteRepo.findById(request.getWorkingOffsiteId())
                        .orElseThrow(() -> new BusinessException("Không tìm thấy WorkingOffsite với id: " + request.getWorkingOffsiteId()));
                entity.setWorkingOffsite(wo);
            }

            if (request.getSchoolTypeId() != null) {
                SchoolType type = schoolTypeRepo.findById(request.getSchoolTypeId())
                        .orElseThrow(() -> new BusinessException("Không tìm thấy SchoolType với id: " + request.getSchoolTypeId()));
                entity.setSchoolType(type);
            }

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
    public BaseResponseDTO deleteSchool(Long id) {
        try {
            School entity = repo.findById(id).orElseThrow(() -> new BusinessException("Could not find id " + id));
            entity.setDeleted(Constants.DELETE.ACTIVE);
            return new BaseResponseDTO();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void xuatExcel(SearchSchoolDTO dto, HttpServletResponse response) throws IOException {
        List<SchoolDTO> list = repo.xuatExcel(dto);

        try (ByteArrayOutputStream excelStream = SchoolExcelExporter.export(list)) {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=school_export.xlsx");
            response.getOutputStream().write(excelStream.toByteArray());
            response.flushBuffer();
        }
    }

    @Override
    public Page<SchoolDTO> search(SearchSchoolDTO request, Pageable pageable) {
        try {
            return repo.searchAll(request, pageable);
        } catch (Exception e) {
            logger.error("Lỗi khi tìm kiếm trường học: {}", e.getMessage(), e);
            throw new RuntimeException("Không thể tìm kiếm danh sách trường học", e);
        }
    }

    @Override
    public SchoolDTO detail(Long id) {
        School school = repo.findWithJoinsById(id).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy trường"));
        return mapper.toDto(school);
    }

}
