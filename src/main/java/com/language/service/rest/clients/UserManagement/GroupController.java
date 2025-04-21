package com.language.service.rest.clients.UserManagement;

import jakarta.validation.Valid;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.language.service.common.Constants;
import com.language.service.common.ResponseFactory;
import com.language.service.common.utils.Utils;
import com.language.service.domain.dtos.*;
import com.language.service.domain.entities.Group;
import com.language.service.domain.entities.Menu;
import com.language.service.domain.mapper.GroupMapper;
import com.language.service.domain.mapper.MenuMapper;
import com.language.service.rest.dto.request.*;
import com.language.service.rest.dto.request.searchparams.GroupSearchParams;
import com.language.service.rest.dto.request.searchparams.GroupUserSearchParams;
import com.language.service.rest.dto.response.CreatedIdResponse;
import com.language.service.service.abs.group.GroupDetailsService;
import com.language.service.service.abs.group.GroupSearchService;
import com.language.service.service.abs.group.GroupService;
import com.language.service.validation.constraint.GroupId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(Constants.CLIENT_API + "/groups")
public class GroupController {

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    private final GroupService groupService;
    private final GroupSearchService groupSearchService;
    private final GroupDetailsService groupDetailsService;
    private final ResponseFactory responseFactory;

    public GroupController(GroupService groupService, GroupSearchService groupSearchService, GroupDetailsService groupDetailsService, ResponseFactory responseFactory) {
        this.groupService = groupService;
        this.groupSearchService = groupSearchService;
        this.groupDetailsService = groupDetailsService;
        this.responseFactory = responseFactory;
    }

    @GetMapping
    public ResponseEntity<?> getUsers(GroupSearchParams params, Pageable pageable) {
        pageable = Utils.getDefaultSortPageable(pageable);
        Page<GroupDTO> dtoPage = groupSearchService.searchAll(params, pageable);
        return responseFactory.success(dtoPage);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllGroups() {
        List<GroupDTO> dtoList = groupSearchService.getAllGroups();
        return responseFactory.success(dtoList);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedIdResponse create(@Valid @RequestBody CreateGroupRequest cmd) {
        Group group = groupService.create(cmd);
        return new CreatedIdResponse(group.getId());
    }


    @GetMapping("{id}")
    public GroupDetailsDTO details(@PathVariable(name = "id") Long id) {
        Optional<GroupDetailsDTO> optGroup = groupDetailsService.findDetailsDTOById(id);
        return optGroup.orElse(null);
    }


    @PutMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(
            @PathVariable(name = "id") long groupId,
            @RequestBody UpdateGroupRequest cmd) {
        cmd.setGroupId(groupId);
        groupService.update(cmd);
    }


    @GetMapping("{id}/allowedMenus")
    public List<MenuDTO> getAllowedMenus(@PathVariable(name = "id") Long id) {
        Optional<Group> optGroup = groupService.findByIdWithAllowedMenus(id);
        if (optGroup.isPresent()) {
            Group group = optGroup.get();
            Set<Menu> allowedMenus = group.getAllowedMenus();
            List<MenuDTO> result = new ArrayList<>();
            MenuMapper menuMapper = Mappers.getMapper(MenuMapper.class);
            if (allowedMenus != null) {
                for (Menu menu : allowedMenus) {
                    MenuDTO menuDTO = menuMapper.toMenuDTO(menu);
                    result.add(menuDTO);
                }
            }
            return result;
        } else {
            return null;
        }
    }


    @GetMapping("{id}/permissions")
    public List<PermissionDTO> getGroupPermissions(@PathVariable("id") long id) {
        return groupService.getGroupPermissions(id);
    }


    @PostMapping("{id}/addPermissions")
    public void addPermissions(@PathVariable("id") long id, @RequestBody AddPermissionsToGroup command) {
        groupService.addPermissions(id, command);
    }


    @PostMapping("{id}/addMenus")
    public void addMenus(@PathVariable("id") long id, @RequestBody AddMenusToGroup command) {
        groupService.addMenus(id, command);
    }


    @PostMapping("{id}/unlinkMenu")
    public void unlinkMenu(@PathVariable("id") long groupId, @RequestBody UnlinkMenuRequest command) {
        groupService.unlinkMenu(groupId, command);
    }


    @PostMapping("{id}/unlinkPermissions")
    public void unlinkPermission(@PathVariable("id") long groupId, @RequestBody UnlinkPermissionsFromGroup command) {
        groupService.unlinkPermission(groupId, command);
    }


    @GetMapping("{id}/notGrantedPermissions")
    public List<PermissionDTO> notGrantedPermissions(@PathVariable("id") long id) {
        return groupService.notGrantedPermissions(id);
    }


    @GetMapping("{id}/notGrantedMenus")
    public List<MenuDTO> notGrantedMenus(@PathVariable("id") long id) {
        return groupService.notGrantedMenus(id);
    }

    @PreAuthorize("hasPermission('DELETE_GROUP')")
    @DeleteMapping("{id}")
    public void delete(@Valid @GroupId @PathVariable("id") long id) {
        groupService.deleteById(id);
    }

    @PostMapping(value = "/search-group")
    public ResponseEntity<?> searchAllGroup(@RequestBody GroupUserSearchParams params, Pageable pageable) throws Exception {
        return responseFactory.success(groupService.searchByFilter(params, pageable));
    }

    @PostMapping(value = "/quick-search-group")
    public ResponseEntity<?> quickSearchGroup(@RequestBody GroupUserSearchParams params, Pageable pageable) throws Exception {
        return responseFactory.success(groupService.quickSearchGroup(params, pageable));
    }

    @PostMapping(value = "/add-group")
//    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.GROUP_USER_MANAGEMENT).ADD_GROUP_USER)")
    public ResponseEntity<?> add(@RequestBody @Valid GroupUserManagementDTO request) throws Exception {
        return responseFactory.success(groupService.saveGroupUser(request));
    }

    @PostMapping(value = "/update-group")
//    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.GROUP_USER_MANAGEMENT).UPDATE_GROUP_USER)")
    public ResponseEntity<?> update(@RequestBody @Valid GroupUserManagementDTO request) throws Exception {
        return responseFactory.success(groupService.updateGroupUser(request));
    }

    @DeleteMapping(value = "/delete-group/{id}")
//    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.GROUP_USER_MANAGEMENT).DELETE_GROUP_USER)")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws Exception {
        return responseFactory.success(groupService.deleteGroupUser(id));
    }

    @PostMapping("/search-users")
    public ResponseEntity<?> searchUsersInGroup(@RequestBody UserGroupMapDTO request, Pageable pageable) {
        pageable = Utils.getDefaultSortPageable(pageable);
        Page<UserDTO> usersPage = groupService.findUsersInGroup(request, pageable);
        return responseFactory.success(usersPage);
    }

    @PostMapping("/search-users-not-in-group")
    public ResponseEntity<?> searchUsersNotInGroup(@RequestBody UserGroupMapDTO request) {
        List<UserDTO> usersPage = groupService.findUsersNotInGroup(request);
        return responseFactory.success(usersPage);
    }
    @PostMapping(value = "/add-user-group-maps")
    public ResponseEntity<?> addUserGroupMaps(@RequestBody @Valid List<UserGroupMapManagementDTO> request) throws Exception {
        return responseFactory.success(groupService.saveUserGroupMaps(request));
    }
    @DeleteMapping("/delete-user-from-group/{groupId}/{userId}")
//    @DeleteMapping(value = "/delete-user-from-group/{userId}/{groupId}")
    public ResponseEntity<?> deleteUserFromGroup(@PathVariable Long userId, @PathVariable Long groupId) throws Exception {
        return responseFactory.success(groupService.deleteUserFromGroup(userId, groupId));
    }
    @GetMapping("/searchGroupById/{id}")
    public ResponseEntity<?> searchGroupById(@PathVariable Long id) throws Exception{
        GroupDTO groupDTO = groupService.getGroupById(id);
        return responseFactory.success(groupDTO);
    }
}
