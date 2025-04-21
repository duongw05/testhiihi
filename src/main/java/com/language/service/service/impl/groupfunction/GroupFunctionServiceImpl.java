package com.language.service.service.impl.groupfunction;

import com.language.service.domain.dtos.FunctionCatalogDTO;
import com.language.service.domain.dtos.FunctionTreeDTO;
import com.language.service.domain.entities.FunctionCatalog;
import com.language.service.domain.mapper.GroupFunctionMapper;
import com.language.service.repo.jpa.functioncatalog.FunctionCatalogRepo;
import com.language.service.rest.dto.response.BaseResponseDTO;
import com.language.service.domain.dtos.GroupFunctionDTO;
import com.language.service.domain.entities.GroupFunction;
import com.language.service.repo.jpa.groupfunction.GroupFunctionRepo;
import com.language.service.service.abs.groupfunction.GroupFunctionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.language.service.exception.BusinessException;
import com.language.service.common.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GroupFunctionServiceImpl implements GroupFunctionService {
    private static final Logger logger = LoggerFactory.getLogger(GroupFunctionServiceImpl.class);
    private final GroupFunctionMapper mapper;
    private final GroupFunctionRepo repo;

    private FunctionCatalogRepo functionCatalogRepository;

    public GroupFunctionServiceImpl(GroupFunctionMapper mapper, GroupFunctionRepo repo, FunctionCatalogRepo functionCatalogRepository) {
        this.repo = repo;
        this.mapper = mapper;
        this.functionCatalogRepository = functionCatalogRepository;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponseDTO addGroupFunction(GroupFunctionDTO request) {
        try {
            GroupFunction entity = mapper.toEntity(request);
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
    public BaseResponseDTO updateGroupFunction(GroupFunctionDTO request) {
        try {
            if (request.getId() == null) throw new BusinessException("Id is required");
            GroupFunction domain = repo.findById(request.getId())
                    .orElseThrow(() -> new BusinessException("Could not find id " + request.getId()));
            GroupFunction entity = mapper.toEntity(request);
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
    public BaseResponseDTO deleteGroupFunction(Long id) {
        try {
            GroupFunction entity = repo.findById(id).orElseThrow(() -> new BusinessException("Could not find id " + id));
            entity.setDeleted(Constants.DELETE.ACTIVE);
            return new BaseResponseDTO();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<GroupFunctionDTO> search(GroupFunctionDTO request, Pageable pageable) {
        try {
            Page<GroupFunction> rs = repo.findAll(pageable);
            return new PageImpl<>(mapper.toDtoList(rs.getContent()), pageable, rs.getTotalElements());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    public List<GroupFunctionDTO> getGroupFunctionData(GroupFunctionDTO request) {
        // Lấy tất cả menu cấp cao từ function_catalog
        List<FunctionCatalog> topLevelFunctions = functionCatalogRepository.findTopLevelFunctions();

        // Lấy tất cả group_function cho groupId (nếu có)
        List<GroupFunction> allGroupFunctions = repo.findAll();

        // Tạo map để tra cứu nhanh group_function
        Map<Long, GroupFunction> groupFunctionMap = allGroupFunctions.stream()
                .filter(gf -> gf.getGroupId().equals(request.getGroupId()))
                .collect(Collectors.toMap(GroupFunction::getFunctionId, gf -> gf));

        // Xây dựng cây chức năng (3 cấp)
        return topLevelFunctions.stream()
                .map(fc -> buildFunctionTree(fc, groupFunctionMap, request.getGroupId(), 1))
                .collect(Collectors.toList());
    }

    private GroupFunctionDTO buildFunctionTree(FunctionCatalog fc, Map<Long, GroupFunction> groupFunctionMap, Long groupId, int level) {
        GroupFunctionDTO dto = new GroupFunctionDTO();
        dto.setGroupId(groupId);
        dto.setFunctionCode(fc.getFunctionCode());
        dto.setFunctionName(fc.getFunctionName());
        dto.setFunctionId(fc.getId());
        dto.setDescription(fc.getDescription());
        dto.setParentCode(fc.getParentId() != null ? functionCatalogRepository.findParentFunctionCode(fc.getParentId()) : null);

        // Lấy status từ group_function, nếu không có thì mặc định là 0
        GroupFunction groupFunction = groupFunctionMap.get(fc.getId());
        dto.setId(groupFunction != null ? groupFunction.getId() : null);
        dto.setStatus(groupFunction != null ? groupFunction.getStatus() : 0);
        dto.setPermissionFunction(groupFunction != null && groupFunction.getStatus() == 1);

        // Chỉ thêm children nếu đang ở cấp 1 (menu cấp cao)
        List<GroupFunctionDTO> children = new ArrayList<>();
        if (level == 1) {
            List<FunctionCatalog> childFunctions = functionCatalogRepository.findChildrenByParentId(fc.getId());
            children = childFunctions.stream()
                    .map(child -> buildFunctionTree(child, groupFunctionMap, groupId, level + 1))
                    .collect(Collectors.toList());
        }
        dto.setChildren(children);

        // Tạo functionDTOS cho quyền chi tiết
        List<FunctionCatalogDTO> functionDTOS = new ArrayList<>();
        List<FunctionCatalog> subFunctions = functionCatalogRepository.findChildrenByParentId(fc.getId());
        for (FunctionCatalog subFc : subFunctions) {
            FunctionCatalogDTO subDto = new FunctionCatalogDTO();
            subDto.setFunctionId(subFc.getId());
            subDto.setGroupId(groupId);
            GroupFunction subGroupFunction = groupFunctionMap.get(subFc.getId());
            subDto.setGroupFunctionId(subGroupFunction != null ? subGroupFunction.getId() : null);
            subDto.setFunctionCode(subFc.getFunctionCode());
            subDto.setFunctionName(subFc.getFunctionName());
            subDto.setDescription(subFc.getDescription() != null ? subFc.getDescription() : "");
            subDto.setStatus(subGroupFunction != null ? subGroupFunction.getStatus() : 0);
            subDto.setPermissionFunction(subGroupFunction != null && subDto.getStatus() == 1);
            functionDTOS.add(subDto);
        }
        dto.setFunctionDTOS(functionDTOS);

        return dto;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponseDTO saveGroupFunction(GroupFunctionDTO request) {
        try {
            GroupFunction entity;
            boolean isUpdate = request.getId() != null && repo.existsById(request.getId());

            if (isUpdate) {
                entity = repo.findById(request.getId())
                        .orElseThrow(() -> new IllegalArgumentException("GroupFunction not found with id: " + request.getId()));
                entity.setStatus(request.getStatus());
            } else {
                entity = mapper.toEntity(request);
                entity.setStatus(Constants.STATUS.ACTIVE);
                entity.setDeleted(Constants.DELETE.INACTIVE);
            }

            repo.save(entity);

            return new BaseResponseDTO();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }
}
