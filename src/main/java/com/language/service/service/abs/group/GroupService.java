package com.language.service.service.abs.group;


import com.language.service.domain.dtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.language.service.rest.dto.request.AddMenusToGroup;
import com.language.service.rest.dto.request.AddPermissionsToGroup;
import com.language.service.rest.dto.request.CreateGroupRequest;
import com.language.service.rest.dto.request.UnlinkMenuRequest;
import com.language.service.rest.dto.request.UnlinkPermissionsFromGroup;
import com.language.service.rest.dto.request.UpdateGroupRequest;
import com.language.service.domain.entities.Group;
import com.language.service.rest.dto.request.searchparams.GroupUserSearchParams;
import com.language.service.rest.dto.response.BaseResponseDTO;
import com.language.service.service.abs.base.BaseService;

import java.util.List;
import java.util.Optional;

public interface GroupService extends BaseService<Group, Long> {

    Group create(CreateGroupRequest cmd);

    void update(UpdateGroupRequest cmd);

    Optional<Group> findByIdWithAllowedMenus(long userGroupId);

    List<PermissionDTO> getGroupPermissions(long groupId);

    List<PermissionDTO> notGrantedPermissions(long groupId);

    void addPermissions(long groupId, AddPermissionsToGroup command);

    List<MenuDTO> notGrantedMenus(long id);

    void addMenus(long groupId, AddMenusToGroup command);

    void unlinkMenu(long groupId, UnlinkMenuRequest command);

    void unlinkPermission(long groupId, UnlinkPermissionsFromGroup command);

    Page<GroupUserManagementDTO> searchByFilter(GroupUserSearchParams params, Pageable pageable);

    Page<GroupUserManagementDTO> quickSearchGroup(GroupUserSearchParams params, Pageable pageable);

    BaseResponseDTO saveGroupUser(GroupUserManagementDTO request);

    BaseResponseDTO updateGroupUser(GroupUserManagementDTO request);

    BaseResponseDTO deleteGroupUser(Long id);

    List<Group> findGroupByUserId(Long userId);

    Page<UserDTO> findUsersInGroup(UserGroupMapDTO request, Pageable pageable);

    List<UserDTO> findUsersNotInGroup(UserGroupMapDTO request);

    BaseResponseDTO saveUserGroupMaps(List<UserGroupMapManagementDTO> userGroupMaps);

    BaseResponseDTO deleteUserFromGroup(Long userId, Long groupId);

    GroupDTO getGroupById(Long groupId);

}
