package com.language.service.service.impl.functioncatalog;

import com.language.service.common.utils.DataUtils;
import com.language.service.domain.dtos.FunctionTreeDTO;
import com.language.service.domain.mapper.FunctionCatalogMapper;
import com.language.service.rest.dto.response.BaseResponseDTO;
import com.language.service.domain.dtos.FunctionCatalogDTO;
import com.language.service.domain.entities.FunctionCatalog;
import com.language.service.repo.jpa.functioncatalog.FunctionCatalogRepo;
import com.language.service.service.abs.functioncatalog.FunctionCatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.language.service.exception.BusinessException;
import com.language.service.common.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Override
    public BaseResponseDTO getFunctionCatalogTree() {
        BaseResponseDTO response = new BaseResponseDTO();
        try {
            List<FunctionCatalog> topLevelFunctions = repo.findTopLevelFunctions();
            logger.info("Found {} top-level functions", topLevelFunctions.size());

            List<FunctionCatalog> validTopLevelFunctions = topLevelFunctions.stream()
                    .filter(f -> f.getStatus() == Constants.STATUS.ACTIVE)
                    .collect(Collectors.toList());

            if (validTopLevelFunctions.isEmpty()) {
                response.setMessage("Không tìm thấy chức năng cấp 1 hợp lệ!");
                return response;
            }

            // Xây dựng cây
            List<FunctionTreeDTO> treeDTOS = validTopLevelFunctions.stream()
                    .map(this::convertToFunctionTreeNode)
                    .collect(Collectors.toList());

            response.setData(treeDTOS);
            response.setMessage("Lấy danh sách chức năng thành công!");
            return response;

        } catch (Exception e) {
            logger.error("Lỗi khi lấy danh sách chức năng: {}", e.getMessage(), e);
            response.setMessage("Đã xảy ra lỗi khi lấy danh sách chức năng!");
            return response;
        }
    }

    private FunctionTreeDTO convertToFunctionTreeNode(FunctionCatalog function) {
        FunctionTreeDTO functionTreeDTO = new FunctionTreeDTO();
        functionTreeDTO.setValue(function.getId());
        functionTreeDTO.setLabel(function.getFunctionName());

        // Lấy các node con (level 2, DELETED = 0)
        List<FunctionCatalog> children = repo.findChildrenByParentId(function.getId());

        // Lọc các node con có STATUS = 1
        List<FunctionTreeDTO> childNodes = children.stream()
                .filter(child -> child.getStatus() == Constants.STATUS.ACTIVE)
                .map(child -> new FunctionTreeDTO(child.getId(), child.getFunctionName(), new ArrayList<>()))
                .collect(Collectors.toList());

        functionTreeDTO.setChildren(childNodes);
        return functionTreeDTO;
    }
    @Override
    @Cacheable(key = "#userId", cacheNames = {Constants.CACHE_KEY_NAME.CACHE_ME_PERMISSION_KEY})
    public List<String> allPermCode(long userId) {
        return repo.allPermCode(userId);
    }
}
