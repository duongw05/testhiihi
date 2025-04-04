package com.language.service.service.impl.permission;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.language.service.common.Constants;
import com.language.service.common.ConstantsErrorCode;
import com.language.service.common.utils.AuthenticationUtils;
import com.language.service.common.utils.DataUtils;
import com.language.service.domain.dtos.PermissionDTO;
import com.language.service.domain.entities.Group;
import com.language.service.domain.entities.GroupPermissionMap;
import com.language.service.domain.entities.Permission;
import com.language.service.domain.mapper.PermissionMapper;
import com.language.service.exception.BusinessException;
import com.language.service.repo.group.GroupRepo;
import com.language.service.repo.jpa.grouppermissionmap.GroupPermissionMapRepo;
import com.language.service.repo.permission.PermissionDTORepo;
import com.language.service.repo.permission.PermissionRepo;
import com.language.service.rest.dto.request.searchparams.PermissionSearchParams;
import com.language.service.rest.dto.response.BaseResponseDTO;
import com.language.service.rest.dto.response.GroupPermissionResponseDTO;
import com.language.service.service.abs.permission.PermissionService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    protected final Logger LOG = LogManager.getLogger(this.getClass());

    private final PermissionRepo permissionRepo;
    private final GroupPermissionMapRepo groupPermissionMapRepo;
    private final PermissionDTORepo permissionDTORepo;
    private final GroupRepo groupRepo;
    private final AuthenticationUtils authenticationUtils;

    @Autowired
    public PermissionServiceImpl(PermissionRepo permissionRepo, GroupPermissionMapRepo groupPermissionMapRepo, PermissionDTORepo permissionDTORepo, GroupRepo groupRepo, AuthenticationUtils authenticationUtils) {
        this.permissionRepo = permissionRepo;
        this.groupPermissionMapRepo = groupPermissionMapRepo;
        this.permissionDTORepo = permissionDTORepo;
        this.groupRepo = groupRepo;
        this.authenticationUtils = authenticationUtils;
    }

    @Override
    @Cacheable(key = "#userId", cacheNames = {Constants.CACHE_KEY_NAME.CACHE_ME_PERMISSION_KEY})
    public List<String> allPermCode(long userId) {
        return permissionRepo.allPermCode(userId);
    }

    @Override
    @Cacheable(cacheNames = {Constants.CACHE_KEY_NAME.CACHE_PERMISSION_ALL_KEY})
    public List<PermissionDTO> findAllDTO() {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        PermissionSearchParams params = new PermissionSearchParams();
        return permissionDTORepo.search(params, pageable, false).getContent();
    }

    @Override
    public Page<GroupPermissionResponseDTO> findAllDTO(long groupId, Pageable pageable) {
        return groupPermissionMapRepo.getPermissionUserGroupAll(groupId, pageable);
    }

    /**
     * Hàm gán quyền cho nhóm người dùng
     * @param id Quyền cần gán
     * @param groupId Group cần gán
     * */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    @CacheEvict(cacheNames = {Constants.CACHE_KEY_NAME.CACHE_PERMISSION_ALL_KEY, Constants.CACHE_KEY_NAME.CACHE_ME_PERMISSION_KEY}, allEntries = true)
    public BaseResponseDTO linkPermissionWithGroup(long id, long groupId) {
        try {
            GroupPermissionMap groupPermissionMap = groupPermissionMapRepo.findByGroupAndPermissionWithoutDelete(groupId, id).orElse(null);
            if (groupPermissionMap == null){
                Permission permission = permissionRepo.findById(id).orElseThrow(() -> new BusinessException(ConstantsErrorCode.PERMISSION.ERROR_PERMISSION_NOT_FOUND));
                Group group = groupRepo.findById(groupId).orElseThrow(() -> new BusinessException(ConstantsErrorCode.GROUP.ERROR_GROUP_NOT_FOUND));
                groupPermissionMap = new GroupPermissionMap();
                groupPermissionMap.setPermissionId(permission.getId());
                groupPermissionMap.setGroupId(group.getId());
            }
            groupPermissionMap.setDeleted(Constants.DELETE.INACTIVE);
            groupPermissionMapRepo.save(groupPermissionMap);
            return new BaseResponseDTO();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    @CacheEvict(cacheNames = {Constants.CACHE_KEY_NAME.CACHE_PERMISSION_ALL_KEY, Constants.CACHE_KEY_NAME.CACHE_ME_PERMISSION_KEY}, allEntries = true)
    public BaseResponseDTO unlinkPermissionWithGroup(long id, long groupId) {
        try {
//          GroupPermissionMap groupPermissionMap = groupPermissionMapRepo.findByGroupAndPermission(groupId, id, Constants.DELETE.INACTIVE).orElseThrow(()
//                  -> new BusinessException(ConstantsErrorCode.GROUP_PERMISSION.ERROR_PERMISSION_GROUP_NOT_FOUND));
            GroupPermissionMap groupPermissionMap = groupPermissionMapRepo.findByGroupAndPermissionWithoutDelete(groupId, id).orElseThrow(()
                    -> new BusinessException(ConstantsErrorCode.GROUP_PERMISSION.ERROR_PERMISSION_GROUP_NOT_FOUND));
            groupPermissionMap.setDeleted(Constants.DELETE.ACTIVE);
            groupPermissionMap.setDeletedBy(authenticationUtils.currentUserName());
            groupPermissionMap.setDeletedDate(new Date());
            return new BaseResponseDTO();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    @CacheEvict(cacheNames = {Constants.CACHE_KEY_NAME.CACHE_PERMISSION_ALL_KEY}, allEntries = true)
    public BaseResponseDTO savePermission(PermissionDTO request) {
        try {
            Permission entity = Mappers.getMapper(PermissionMapper.class).toEntity(request);
            List<Permission> investigations = permissionRepo.checkCodeExist(entity.getCode(), null);
            if (!DataUtils.isNullOrEmpty(investigations))
                throw new BusinessException(ConstantsErrorCode.PERMISSION.ERROR_PERMISSION_EXIST);
            entity.setDeleted(Constants.DELETE.INACTIVE);
            permissionRepo.save(entity);
            return new BaseResponseDTO();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    @CacheEvict(cacheNames = {Constants.CACHE_KEY_NAME.CACHE_PERMISSION_ALL_KEY}, allEntries = true)
    public BaseResponseDTO updatePermission(PermissionDTO request) {
        try {
            if (request.getId() == null)
                throw new BusinessException(ConstantsErrorCode.PERMISSION.ERROR_PERMISSION_NOT_FOUND);
            Permission domain = permissionRepo.findById(request.getId())
                    .orElseThrow(() -> new BusinessException(ConstantsErrorCode.PERMISSION.ERROR_PERMISSION_EXIST));
            domain.setName(request.getName());
            domain.setPermissionGroup(request.getPermissionGroup());
            permissionRepo.save(domain);
            return new BaseResponseDTO();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    @CacheEvict(cacheNames = {Constants.CACHE_KEY_NAME.CACHE_PERMISSION_ALL_KEY}, allEntries = true)
    public BaseResponseDTO deletePermission(Long id) {
        try {
            Permission entity = permissionRepo.findById(id).orElseThrow(() -> new BusinessException(ConstantsErrorCode.PERMISSION.ERROR_PERMISSION_EXIST));
            entity.setDeleted(Constants.DELETE.ACTIVE);
            entity.setDeletedBy(authenticationUtils.currentUserName());
            entity.setDeletedDate(new Date());
            return new BaseResponseDTO();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<PermissionDTO> search(PermissionDTO request, Pageable pageable) {
        try {
            Page<Permission> rs = permissionRepo.searchAllByMultipleFilter(DataUtils.makeLikeQuery(request.getCode()), DataUtils.makeLikeQuery(request.getName()), Constants.DELETE.INACTIVE, pageable);
            return new PageImpl<>(Mappers.getMapper(PermissionMapper.class).toDTO(rs.getContent()), pageable, rs.getTotalElements());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<PermissionDTO> findAllByPermissionGroupToPage(String menuCode, Pageable pageable) {
        try {
            Page<Permission> rs = permissionRepo.findAllByPermissionGroupToPage(menuCode, Constants.DELETE.INACTIVE, pageable);
            return rs.map(s -> Mappers.getMapper(PermissionMapper.class).toDTO(s));

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

}
