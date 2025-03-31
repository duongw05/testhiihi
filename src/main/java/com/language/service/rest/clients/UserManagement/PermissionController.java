package com.language.service.rest.clients.UserManagement;


import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.language.service.common.Constants;
import com.language.service.common.ResponseFactory;
import com.language.service.common.utils.Utils;
import com.language.service.domain.dtos.PermissionDTO;
import com.language.service.service.abs.permission.PermissionService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping(Constants.CLIENT_API + "/permissions")
public class PermissionController {

    private final PermissionService permissionService;
    private final MessageSource messageSource;

    private final ResponseFactory responseFactory;

    public PermissionController(PermissionService permissionService, MessageSource messageSource, ResponseFactory responseFactory) {
        this.permissionService = permissionService;
        this.messageSource = messageSource;
        this.responseFactory = responseFactory;
    }

    @GetMapping("all")
    public List<PermissionDTO> allPermissions() {
        List<PermissionDTO> unmodifiableList = permissionService.findAllDTO();
        List<PermissionDTO> permissionList = new ArrayList<>(unmodifiableList.size());
        permissionList.addAll(unmodifiableList);
        Locale locale = LocaleContextHolder.getLocale();
        if (permissionList.size() > 0) {
            for (PermissionDTO perm : permissionList) {
                String permissionGroupName =
                        messageSource.getMessage(perm.getPermissionGroup(), null, "Other", locale);
                String name =
                        messageSource.getMessage(perm.getCode(), null, "N/A", locale);
                perm.setPermissionGroup(permissionGroupName);
                perm.setName(name);
            }
            permissionList.sort(Comparator.comparing(PermissionDTO::getPermissionGroup));
        }
        return permissionList;
    }

    @GetMapping("/byPage/{groupId}")
    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.CONFIG_MENU_PERMISSION).SEARCH_CONFIG_DETAIL_MENU_PER)")
    public ResponseEntity<?> allPermissions(@PathVariable("groupId") long groupId, Pageable pageable) {
        pageable = Utils.getDefaultSortPageable(pageable);
        return responseFactory.success(permissionService.findAllDTO(groupId, pageable));
    }

    @PutMapping("/linkWithGroup/{id}/{groupId}")
    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.CONFIG_MENU_PERMISSION).LINK_CONFIG_PER)")
    public ResponseEntity<?> linkWithGroup(@PathVariable("id") long id, @PathVariable("groupId") long groupId) {
        return responseFactory.success(permissionService.linkPermissionWithGroup(id, groupId));
    }

    @PutMapping("/unlinkWithGroup/{id}/{groupId}")
    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.CONFIG_MENU_PERMISSION).LINK_CONFIG_PER)")
    public ResponseEntity<?> unlinkWithGroup(@PathVariable("id") long id, @PathVariable("groupId") long groupId) {
        return responseFactory.success(permissionService.unlinkPermissionWithGroup(id, groupId));
    }


    @PostMapping(value = "/search")
    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.CONFIG_PERMISSION).SEARCH)")
    public ResponseEntity<?> search(@RequestBody PermissionDTO request, @PageableDefault Pageable pageable) {
        return responseFactory.success(permissionService.search(request, pageable));
    }

    @PostMapping(value = "/add")
    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.CONFIG_PERMISSION).ADD)")
    public ResponseEntity<?> add(@RequestBody @Valid PermissionDTO request) {
        return responseFactory.success(permissionService.savePermission(request));
    }

    @PostMapping(value = "/update")
    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.CONFIG_PERMISSION).UPDATE)")
    public ResponseEntity<?> update(@RequestBody @Valid PermissionDTO request) {
        return responseFactory.success(permissionService.updatePermission(request));
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.CONFIG_PERMISSION).DELETE)")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return responseFactory.success(permissionService.deletePermission(id));
    }

    @GetMapping(value = "/search-all-permission-by-menu-code")
    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.CONFIG_PERMISSION).SEARCH)")
    public ResponseEntity<?> searchAllPermissionByMenuCode(
            @RequestParam("code") String menuCode,
            @PageableDefault Pageable pageable
    ) {
        var permissionsPage = permissionService.findAllByPermissionGroupToPage(menuCode, pageable);
        return responseFactory.success(permissionsPage);
    }
}
