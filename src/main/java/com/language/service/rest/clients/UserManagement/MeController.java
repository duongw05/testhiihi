package com.language.service.rest.clients.UserManagement;

import com.language.service.service.abs.functioncatalog.FunctionCatalogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.language.service.common.Constants;
import com.language.service.common.utils.Utils;
import com.language.service.domain.dtos.NotificationDTO;
import com.language.service.domain.dtos.UserDTO;
import com.language.service.domain.dtos.UserMinDTO;
import com.language.service.rest.dto.request.searchparams.NotificationSearchParams;
import com.language.service.rest.dto.response.PaginationDataResponse;
import com.language.service.service.abs.notification.NotificationSearchService;
import com.language.service.service.abs.permission.PermissionService;
import com.language.service.service.abs.user.UserService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(Constants.CLIENT_API + "/me")
public class MeController {

    private final UserService userService;
    private final PermissionService permissionService;
    private final FunctionCatalogService functionCatalogService;
    private final NotificationSearchService notificationSearchService;

    public MeController(UserService userService, PermissionService permissionService, FunctionCatalogService functionCatalogService, NotificationSearchService notificationSearchService) {
        this.userService = userService;
        this.permissionService = permissionService;
        this.functionCatalogService = functionCatalogService;
        this.notificationSearchService = notificationSearchService;

    }

    @GetMapping("/profile")
    public UserDTO getMyProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) auth;
            UserMinDTO userMinDTO = (UserMinDTO) token.getPrincipal();
            return userService.findByIdReturnDTO(userMinDTO.getId());
        }
        return null;
    }

    @GetMapping("/permissions")
    public List<String> myPermissions() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) auth;
            UserMinDTO userMinDTO = (UserMinDTO) token.getPrincipal();
            return functionCatalogService.allPermCode(userMinDTO.getId());
        }
        return Collections.emptyList();
    }

    @GetMapping("/notifications")
    public PaginationDataResponse<NotificationDTO> myNotification(Pageable pageable) {
        pageable = Utils.getDefaultSortPageable(pageable);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) auth;
            UserMinDTO userMinDTO = (UserMinDTO) token.getPrincipal();
            long userId = userMinDTO.getId();
            NotificationSearchParams params = new NotificationSearchParams();
            params.setUserId(userId);
            Page<NotificationDTO> page = notificationSearchService.search(params, pageable);
            return new PaginationDataResponse<>(page);
        }
        return new PaginationDataResponse<>(new PageImpl<>(Collections.emptyList()));
    }
}
