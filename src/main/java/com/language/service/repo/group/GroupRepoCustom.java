package com.language.service.repo.group;

import com.language.service.rest.dto.request.AddMenusToGroup;
import com.language.service.rest.dto.request.AddPermissionsToGroup;
import com.language.service.domain.dtos.GroupDTO;
import com.language.service.domain.dtos.MenuDTO;
import com.language.service.domain.dtos.PermissionDTO;
import com.language.service.rest.dto.request.UnlinkMenuRequest;
import com.language.service.rest.dto.request.UnlinkPermissionsFromGroup;

import java.util.List;

public interface GroupRepoCustom {
    List<PermissionDTO> getGroupPermissions(long groupId);

    List<PermissionDTO> getNotGrantedPermissions(long groupId);

    void addPermissions(long groupId, AddPermissionsToGroup command);

    List<GroupDTO> getGroupsOfUser(long userId);

    List<MenuDTO> getNotGrantedMenus(long groupId);

    void addMenus(long groupId, AddMenusToGroup command);

    void unlinkMenu(long groupId, UnlinkMenuRequest command);

    void unlinkPermission(long groupId, UnlinkPermissionsFromGroup command);
}
