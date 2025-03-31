package com.language.service.rest.clients.menumanagement;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.language.service.common.Constants;
import com.language.service.common.ResponseFactory;
import com.language.service.domain.dtos.MenuManagementDTO;
import com.language.service.domain.dtos.MenuPermissionTreeDTO;
import com.language.service.domain.dtos.MenuTreeDTO;
import com.language.service.domain.dtos.SearchMenuDTO;
import com.language.service.service.abs.menumanagement.MenuManagementService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(Constants.CLIENT_API + "/menu_mangagement")
public class MenuManagementController {
    private static final Logger logger = LoggerFactory.getLogger(MenuManagementController.class);

    private final MenuManagementService menuManagementService;
    final ResponseFactory responseFactory;

    public MenuManagementController(MenuManagementService menuManagementService, ResponseFactory responseFactory) {
        this.menuManagementService = menuManagementService;
        this.responseFactory = responseFactory;
    }

    @PostMapping(value = "/add")
    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.MENUS_MANAGEMENT).ADD_MENUS)")
    public ResponseEntity<?> add(@RequestBody @Valid MenuManagementDTO request) throws Exception {
        return responseFactory.success(menuManagementService.saveMenusManagement(request));
    }

    @PostMapping(value = "/update")
    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.MENUS_MANAGEMENT).UPDATE_MENUS)")
    public ResponseEntity<?> update(@RequestBody @Valid MenuManagementDTO request) throws Exception {
        return responseFactory.success(menuManagementService.updateMenusManagement(request));
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.MENUS_MANAGEMENT).DELETE_MENUS)")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws Exception {
        return responseFactory.success(menuManagementService.deleteMenusManagement(id));
    }

    @PostMapping(value = "/searchAllChild")
    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.MENUS_MANAGEMENT).SEARCH_MENUS)")
    public ResponseEntity<?> searchAllChild(@RequestBody MenuManagementDTO request) throws Exception {
        return responseFactory.success(menuManagementService.searchAllChild(request.getCode()));
    }


    @PostMapping(value = "/searchAll")
    @PreAuthorize("hasPermission(T(com.language.service.common.ConstPermission.MENUS_MANAGEMENT).SEARCH_MENUS)")
    public ResponseEntity<?> searchAll(@RequestBody SearchMenuDTO request, @PageableDefault Pageable pageable) throws Exception {
        return responseFactory.success(menuManagementService.searchAll(request, pageable));
    }

    @GetMapping("/searchTreeMenu/all")
    public List<MenuTreeDTO> getAllMenu() throws Exception {
        return menuManagementService.searchTreeMenu();
    }

    @GetMapping("/searchTreeMenu/v2/")
    public List<MenuPermissionTreeDTO>  getAllMenuV2() throws Exception {
        return menuManagementService.searchTreeMenuV2();
    }

    @GetMapping("/get-tree/{userId}")
    public ResponseEntity<?> getMenuTree(
            @PathVariable Long userId
    ) throws Exception {
        return responseFactory.success(menuManagementService.getMenusTree(userId));
    }
}

