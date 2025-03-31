package com.language.service.rest.clients.Systems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.language.service.common.Constants;
import com.language.service.common.ResponseFactory;
import com.language.service.domain.dtos.MenuDTO;
import com.language.service.domain.dtos.UserMinDTO;
import com.language.service.rest.dto.request.SearchMenuPermissionDTO;
import com.language.service.service.abs.menu.MenuService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(Constants.CLIENT_API)
public class MenuController {

    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    private final MenuService menuService;
    private final ResponseFactory responseFactory;

    public MenuController(MenuService menuService, ResponseFactory responseFactory) {
        this.menuService = menuService;
        this.responseFactory = responseFactory;
    }

    @GetMapping("/menus/all")
    public List<MenuDTO> getAllMenu() {
        return menuService.getAll();
    }


    @GetMapping("/me/menus")
    public List<String> myMenus() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) auth;
            UserMinDTO userMinDTO = (UserMinDTO) token.getPrincipal();
            long userId = userMinDTO.getId();
            return menuService.getMenuCodesByUser(userId);
        }
        return Collections.emptyList();
    }

    @PostMapping("/menus/{id}")
    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.CONFIG_MENU_PERMISSION).SEARCH_CONFIG_DETAIL_MENU_PER)")
    public ResponseEntity<?> getMenu(@PathVariable("id") long groupId, @RequestBody SearchMenuPermissionDTO searchMenuPermissionDTO, Pageable pageable) {
        return responseFactory.success(menuService.searchPageMenu(groupId, searchMenuPermissionDTO, pageable));
    }

    @PutMapping("menus/linkWithGroup/{id}/{groupId}")
    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.CONFIG_MENU_PERMISSION).LINK_CONFIG_MENU)")
    public ResponseEntity<?> linkWithGroup(@PathVariable("id") long id, @PathVariable("groupId") long groupId) {

        return responseFactory.success(menuService.linkMenuWithGroup(id, groupId));
    }

    @PutMapping("menus/unlinkWithGroup/{id}/{groupId}")
    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.CONFIG_MENU_PERMISSION).LINK_CONFIG_MENU)")
    public ResponseEntity<?> unlinkWithGroup(@PathVariable("id") long id, @PathVariable("groupId") long groupId) {
        return responseFactory.success(menuService.unlinkMenuWithGroup(id, groupId));
    }

    /** Api fake menu*/
    @GetMapping("menus/menu-tree")
    public ResponseEntity<?> getMenuTree() {
        return responseFactory.success(menuService.getAll());
    }

}
