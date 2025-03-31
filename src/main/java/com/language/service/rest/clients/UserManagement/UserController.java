package com.language.service.rest.clients.UserManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.language.service.common.Constants;
import com.language.service.common.ResponseFactory;
import com.language.service.common.utils.Utils;
import com.language.service.domain.dtos.GroupDTO;
import com.language.service.domain.dtos.UserDTO;
import com.language.service.domain.dtos.UserDetailsDTO;
import com.language.service.rest.dto.request.*;
import com.language.service.rest.dto.request.searchparams.UserSearchParams;
import com.language.service.service.abs.user.UserDetailsService;
import com.language.service.service.abs.user.UserSearchService;
import com.language.service.service.abs.user.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(Constants.CLIENT_API + "/users")
@SecurityRequirement(name = "BearerAuth")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final MessageSource messageSource;
    private final UserService userService;
    private final UserSearchService userSearchService;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;
    private final ResponseFactory responseFactory;

    public UserController(MessageSource messageSource, UserService userService, UserSearchService userSearchService, UserDetailsService userDetailsService, ObjectMapper objectMapper, ResponseFactory responseFactory) {
        this.messageSource = messageSource;
        this.userService = userService;
        this.userSearchService = userSearchService;
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
        this.responseFactory = responseFactory;
    }

    @PostMapping("/search")
    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.USER).SEARCH_USER)")
    public ResponseEntity<?> getUsers(@RequestBody UserSearchParams params, Pageable pageable) {
        pageable = Utils.getDefaultSortPageable(pageable);
        Page<UserDTO> dtoPage = userSearchService.searchUser(params, pageable);
        return responseFactory.success(dtoPage);
    }

    @PostMapping("/update")
    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.USER).UPDATE_USER)")
    @CacheEvict(cacheNames = {Constants.CACHE_KEY_NAME.CACHE_MENU_ALL_KEY, Constants.CACHE_KEY_NAME.CACHE_ME_PERMISSION_KEY}, allEntries = true)
    public ResponseEntity<?> createUser(@Valid @RequestBody UpdateUserRequest cmd) {
        return responseFactory.success(userService.update(cmd));
    }

    @PostMapping("/add")
    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.USER).ADD_USER)")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest cmd) {
        return responseFactory.success(userService.create(cmd));
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.USER).DELETE_USER)")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return responseFactory.success(userService.deleteUser(id));
    }

    @PutMapping("/changePassword")
    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.USER).CHANGE_PASS_USER)")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangeUserPasswordRequest command) {
        return responseFactory.success(userService.changeUserPassword(command));
    }

    @PutMapping("/changePasswordByUser")
    public ResponseEntity<?> changePasswordByUser(@Valid@RequestBody ChangeUserPasswordRequestByUser command) {
        return responseFactory.success(userService.changeUserPasswordByUser(command));
    }


    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long userId) {
        userService.deleteById(userId);
    }


    @GetMapping("/{userId}")
    public UserDetailsDTO getUserDetailsDTOById(@PathVariable("userId") Long userId) {
        Optional<UserDetailsDTO> optionalUserDetailsDTO = userDetailsService.findDetailsDTOById(userId);
        return optionalUserDetailsDTO.orElse(null);
    }

    @GetMapping("/{id}/groups")
    public List<GroupDTO> getGroupsOfUser(@PathVariable("id") long id) {
        return userService.getGroupsOfUser(id);
    }

    @GetMapping("/{id}/notLinkedGroups")
    public List<GroupDTO> notLinkedGroups(@PathVariable("id") long id) {
        return userService.getNotLinkedGroups(id);
    }

    @PreAuthorize("hasPermission('UNLINK_USER_GROUP')")
    @PutMapping("/{id}/unlinkGroup")
    public void unlinkGroup(@PathVariable("id") long id, @Valid @RequestBody UnlinkUserGroupRequest command) {
        userService.unlinkGroup(id, command);
    }

    @PreAuthorize("hasPermission('LINK_USER_GROUP')")
    @PutMapping("/{id}/linkGroups")
    public void linkGroups(@PathVariable("id") long userId, @Valid @RequestBody LinkGroupsRequest command) {
        userService.linkGroups(userId, command);
    }

    @PutMapping("update-profile")
    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.USER).UPDATE_USER)")
    public ResponseEntity<?> updateProfileUser(@RequestBody UpdateUserProfileRequest command) {
        return responseFactory.success(userService.updateUserProfile(command));
    }

    @PostMapping("get-all")
    public ResponseEntity<?> getAllUser() {
        return responseFactory.success(userService.getAllUser());
    }
}
