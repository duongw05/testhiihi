package com.language.service.service.impl.group;

import com.language.service.domain.entities.User;
import com.language.service.domain.entities.UserGroupMap;
import com.language.service.domain.mapper.UserMapper;
import com.language.service.repo.jpa.usergroupmap.UserGroupMapRepo;
import com.language.service.repo.user.UserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import com.language.service.common.Constants;
import com.language.service.common.ConstantsErrorCode;
import com.language.service.common.utils.AuthenticationUtils;
import com.language.service.common.utils.DataUtils;
import com.language.service.common.utils.PageUtils;
import com.language.service.domain.dtos.*;
import com.language.service.domain.mapper.GroupUserManagementMapper;
import com.language.service.exception.BusinessException;
import com.language.service.rest.dto.request.AddMenusToGroup;
import com.language.service.rest.dto.request.AddPermissionsToGroup;
import com.language.service.rest.dto.request.CreateGroupRequest;
import com.language.service.rest.dto.request.UnlinkMenuRequest;
import com.language.service.rest.dto.request.UnlinkPermissionsFromGroup;
import com.language.service.rest.dto.request.UpdateGroupRequest;
import com.language.service.domain.entities.Group;
import com.language.service.domain.entities.Menu;
import com.language.service.domain.mapper.GroupMapper;
import com.language.service.repo.group.GroupRepo;
import com.language.service.repo.menu.MenuRepo;
import com.language.service.rest.dto.request.searchparams.GroupUserSearchParams;
import com.language.service.rest.dto.response.BaseResponseDTO;
import com.language.service.service.AbstractService;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.language.service.service.abs.group.GroupService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Component
@Transactional
public class GroupServiceImpl extends AbstractService<Group, Long> implements GroupService {

    private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

    private final GroupRepo groupRepo;
    private final UserRepo userRepo;
    private final MenuRepo menuRepo;
    private final UserGroupMapRepo userGroupMapRepository;
    private final AuthenticationUtils auth;

    @Autowired
    public GroupServiceImpl(GroupRepo groupRepo, UserRepo userRepo, MenuRepo menuRepo, MessageSource messageSource, AuthenticationUtils auth, UserMapper userMapper, UserGroupMapRepo userGroupMapRepository) {
        super(groupRepo);
        this.groupRepo = groupRepo;
        this.userRepo = userRepo;
        this.menuRepo = menuRepo;
        this.auth = auth;
        this.userGroupMapRepository = userGroupMapRepository;
    }

    @Override
    public Group create(CreateGroupRequest cmd) {
        GroupMapper mapper = Mappers.getMapper(GroupMapper.class);
        Group group = mapper.create(cmd);
        save(group);
        return group;
    }


    @Override
    public void update(UpdateGroupRequest cmd) {
        long groupId = cmd.getGroupId();
        Optional<Group> optGroup = groupRepo.findByIdWithAllowedMenus(groupId);
        if (optGroup.isPresent()) {
            Group group = optGroup.get();
            GroupMapper groupMapper = Mappers.getMapper(GroupMapper.class);
            groupMapper.update(cmd, group);
            mergeMenus(group, cmd.getAllowedMenuIds());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UserGroup(" + groupId + ") does not exist");
        }
    }

    @Override
    public Optional<Group> findByIdWithAllowedMenus(long userGroupId) {
        return groupRepo.findByIdWithAllowedMenus(userGroupId);
    }

    @Override
    public List<PermissionDTO> getGroupPermissions(long groupId) {
        return groupRepo.getGroupPermissions(groupId);
    }

    @Override
    public List<PermissionDTO> notGrantedPermissions(long groupId) {
        return groupRepo.getNotGrantedPermissions(groupId);
    }


    @Override
    public void addPermissions(long groupId, AddPermissionsToGroup command) {
        groupRepo.addPermissions(groupId, command);
    }

    @Override
    public List<MenuDTO> notGrantedMenus(long id) {
        return groupRepo.getNotGrantedMenus(id);
    }

    @Override
    public void addMenus(long groupId, AddMenusToGroup command) {
        groupRepo.addMenus(groupId, command);
    }

    @Override
    public void unlinkMenu(long groupId, UnlinkMenuRequest command) {
        groupRepo.unlinkMenu(groupId, command);
    }

    @Override
    public void unlinkPermission(long groupId, UnlinkPermissionsFromGroup command) {
        groupRepo.unlinkPermission(groupId, command);
    }

    private void mergeMenus(Group group, List<Long> requestMenuIds) {
        logger.debug("Merging menus .... ");
        Set<Menu> groupMenus = group.getAllowedMenus();
        // Which menus are newly added
        if (requestMenuIds != null && requestMenuIds.size() > 0) {
            for (Long requestMenuId : requestMenuIds) {
                if (!isAlreadyInMenuSet(groupMenus, requestMenuId)) {
                    Optional<Menu> optMenu = menuRepo.findById(requestMenuId);
                    if (optMenu.isPresent()) {
                        Menu menu = optMenu.get();
                        logger.debug("Adding new menu to group: Menu ID = {}", requestMenuId);
                        group.addMenu(menu);
                    } else {
                        throw new IllegalStateException("Menu(" + requestMenuId + ") does not exist");
                    }
                }
            }
        }


        // Which ones are removed
        if (groupMenus != null && groupMenus.size() > 0) {
            List<Menu> toRemove = new ArrayList<>();
            for (Menu m : groupMenus) {
                long menuId = m.getId();
                if (!inList(menuId, requestMenuIds)) {
                    logger.debug("Marking menu ID = {} to detach from UserGroup", menuId);
                    toRemove.add(m);
                }
            }
            if (toRemove.size() > 0) {
                groupMenus.removeAll(toRemove);
            }
        }
    }

    private boolean inList(long id, List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return false;
        }
        boolean inList = false;
        for (Long iterateId : ids) {
            if (iterateId == id) {
                inList = true;
                break;
            }
        }
        return inList;
    }


    private boolean isAlreadyInMenuSet(Set<Menu> groupMenus, long menuId) {
        if (groupMenus == null || groupMenus.size() == 0) {
            return false;
        }
        boolean inSet = false;
        for (Menu m : groupMenus) {
            if (m.getId() == menuId) {
                inSet = true;
                break;
            }
        }
        return inSet;
    }

    @Override
    public Page<GroupUserManagementDTO> searchByFilter(GroupUserSearchParams params, Pageable pageable) {
        try {
            Page<Group> rs = groupRepo.searchByFilter(DataUtils.makeLikeQuery(params.getCode()),
                    DataUtils.makeLikeQuery(params.getName()), Constants.DELETE.INACTIVE, pageable);
            List<GroupUserManagementDTO> dtos = Mappers.getMapper(GroupUserManagementMapper.class).toDTO(rs.getContent());
            List<GroupUserManagementDTO> list = PageUtils.setSerialNumbersList(dtos, pageable, GroupUserManagementDTO::setStt);

            return new PageImpl<>(list, pageable, rs.getTotalElements());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<GroupUserManagementDTO> quickSearchGroup(GroupUserSearchParams params, Pageable pageable) {
        try {
            Page<Group> rs = groupRepo.quickSearchGroup(DataUtils.makeLikeQuery(params.getQuickSearch()), Constants.DELETE.INACTIVE, pageable);
            List<GroupUserManagementDTO> dtos = Mappers.getMapper(GroupUserManagementMapper.class).toDTO(rs.getContent());
            List<GroupUserManagementDTO> list = PageUtils.setSerialNumbersList(dtos, pageable, GroupUserManagementDTO::setStt);

            return new PageImpl<>(list, pageable, rs.getTotalElements());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponseDTO saveGroupUser(GroupUserManagementDTO request) {
        BaseResponseDTO response = new BaseResponseDTO();
        try {
            if (request == null)
                throw new BusinessException(ConstantsErrorCode.GROUP_USER_MANAGEMENT_ERRORS.GROUP_USER_NOT_FOUND);
            if (groupRepo.getGroupUserByCodeOrName(request.getCode(), null).isPresent()) {
                throw new BusinessException(ConstantsErrorCode.GROUP_USER_MANAGEMENT_ERRORS.GROUP_USER_CODE_EXISTED);
            }
            if (groupRepo.getGroupUserByCodeOrName(null, request.getName()).isPresent()) {
                throw new BusinessException(ConstantsErrorCode.GROUP_USER_MANAGEMENT_ERRORS.GROUP_USER_NAME_EXISTED);
            }
            Group entity = Mappers.getMapper(GroupUserManagementMapper.class).toEntity(request);
            entity.setDeleted(Constants.DELETE.INACTIVE);
            groupRepo.save(entity);
            response.setMessage("Thêm mới nhóm người dùng thành công!");
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponseDTO updateGroupUser(GroupUserManagementDTO request) {
        BaseResponseDTO response = new BaseResponseDTO();
        try {
            if (request == null)
                throw new BusinessException(ConstantsErrorCode.GROUP_USER_MANAGEMENT_ERRORS.GROUP_USER_NOT_FOUND);
            if (DataUtils.isNullOrZero(request.getId())) {
                throw new BusinessException(ConstantsErrorCode.GROUP_USER_MANAGEMENT_ERRORS.GROUP_USER_ID_REQUIRED);
            }
            Group entity = groupRepo.findGroupById(request.getId(), Constants.DELETE.INACTIVE).orElseThrow(() -> new BusinessException(ConstantsErrorCode.GROUP_USER_MANAGEMENT_ERRORS.GROUP_USER_NOT_FOUND));
            if (groupRepo.getGroupUserByCodeOrNameAndIdNotEquasl(entity.getId(), request.getCode(), null, Constants.DELETE.INACTIVE)) {
                throw new BusinessException(ConstantsErrorCode.GROUP_USER_MANAGEMENT_ERRORS.GROUP_USER_CODE_EXISTED);
            }
            if (groupRepo.getGroupUserByCodeOrNameAndIdNotEquasl(entity.getId(), null, request.getName(), Constants.DELETE.INACTIVE)) {
                throw new BusinessException(ConstantsErrorCode.GROUP_USER_MANAGEMENT_ERRORS.GROUP_USER_NAME_EXISTED);
            }
            entity.setCode(request.getCode());
            entity.setName(request.getName());
            entity.setDescription(request.getDescription());
            groupRepo.save(entity);
            response.setMessage("Cập nhật nhóm người dùng thành công!");
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponseDTO deleteGroupUser(Long id) {
        BaseResponseDTO response = new BaseResponseDTO();
        try {
            if (DataUtils.isNullOrZero(id))
                throw new BusinessException(ConstantsErrorCode.GROUP_USER_MANAGEMENT_ERRORS.GROUP_USER_ID_REQUIRED);
            Group entity = groupRepo.findGroupById(id, Constants.DELETE.INACTIVE).orElseThrow(() -> new BusinessException(ConstantsErrorCode.GROUP_USER_MANAGEMENT_ERRORS.GROUP_USER_NOT_FOUND));
            String user = auth.currentUserName();
            if (DataUtils.isNullOrEmpty(user))
                throw new BusinessException(ConstantsErrorCode.GROUP_USER_MANAGEMENT_ERRORS.GROUP_USER_USERNAME_NOT_FOUND);
            entity.setDeleted(Constants.DELETE.ACTIVE);
            entity.setDeletedBy(user);
            groupRepo.save(entity);
            response.setMessage("Xóa nhóm người dùng thành công!");
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Group> findGroupByUserId(Long userId) {
        return groupRepo.findGroupByUserId(userId, Constants.DELETE.INACTIVE);
    }

    @Override
    public List<UserDTO> findUsersNotInGroup(UserGroupMapDTO request) {
        try {
            if (DataUtils.isNullOrZero(request.getGroupId())) {
                throw new BusinessException(ConstantsErrorCode.GROUP_USER_MANAGEMENT_ERRORS.GROUP_USER_ID_REQUIRED);
            }
            List<User> users = userRepo.findUsersNotInGroup(request.getGroupId(), DataUtils.makeLikeQuery(request.getQuickSearch()), Constants.DELETE.INACTIVE);

            return Mappers.getMapper(UserMapper.class).toDto(users);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<UserDTO> findUsersInGroup(UserGroupMapDTO request, Pageable pageable) {
        try {
            if (request.getGroupId() == null || request.getGroupId() <= 0) {
                throw new BusinessException(ConstantsErrorCode.GROUP_USER_MANAGEMENT_ERRORS.GROUP_USER_ID_REQUIRED);
            }
            Page<User> search = userRepo.findUsersByGroupId(request.getGroupId(), DataUtils.makeLikeQuery(request.getQuickSearch()), Constants.DELETE.INACTIVE, pageable);
            return new PageImpl<>(Mappers.getMapper(UserMapper.class).toDto(search.getContent()), pageable, search.getTotalElements());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponseDTO saveUserGroupMaps(List<UserGroupMapManagementDTO> userGroupMaps) {
        BaseResponseDTO response = new BaseResponseDTO();
        try {
            for (UserGroupMapManagementDTO userGroupMapDTO : userGroupMaps) {
                UserGroupMap entity = new UserGroupMap();
                entity.setUserId(userGroupMapDTO.getUserId());
                entity.setGroupId(userGroupMapDTO.getGroupId());
                entity.setStatus(Constants.STATUS.ACTIVE);
                entity.setDeleted(Constants.DELETE.INACTIVE);
                userGroupMapRepository.save(entity);
            }
            response.setMessage("Thêm mới nhóm người dùng thành công!");
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponseDTO deleteUserFromGroup(Long userId, Long groupId) {
        BaseResponseDTO response = new BaseResponseDTO();
        try {
            if (DataUtils.isNullOrZero(userId) || DataUtils.isNullOrZero(groupId)) {
                throw new BusinessException(ConstantsErrorCode.GROUP_USER_MANAGEMENT_ERRORS.INVALID_INPUT);
            }
            Optional<UserGroupMap> userGroupMap = userGroupMapRepository.findByUserIdAndGroupId(userId, groupId, Constants.STATUS.ACTIVE, Constants.DELETE.INACTIVE);
            if (userGroupMap.isPresent()) {
                userGroupMapRepository.delete(userGroupMap.get());
                response.setMessage("Xóa người dùng khỏi nhóm thành công!");
            } else {
                response.setMessage("Người dùng không tồn tại trong nhóm!");
            }
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public GroupDTO getGroupById(Long groupId) {
        Group entity = groupRepo.findGroupById(groupId, Constants.DELETE.INACTIVE).orElseThrow(()
                -> new BusinessException(ConstantsErrorCode.GROUP_USER_MANAGEMENT_ERRORS.GROUP_USER_NOT_FOUND));
        return Mappers.getMapper(GroupMapper.class).toDTO(entity);
    }
}
