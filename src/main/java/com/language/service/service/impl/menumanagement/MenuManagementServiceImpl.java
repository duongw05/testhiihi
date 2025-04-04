package com.language.service.service.impl.menumanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.language.service.common.Constants;
import com.language.service.common.ConstantsErrorCode;
import com.language.service.common.utils.AuthenticationUtils;
import com.language.service.common.utils.DataUtils;
import com.language.service.domain.dtos.*;
import com.language.service.domain.entities.*;
import com.language.service.domain.mapper.MenuManagementMapper;
import com.language.service.domain.mapper.PermissionMapper;
import com.language.service.exception.BusinessException;
import com.language.service.repo.group.GroupRepo;
import com.language.service.repo.jpa.menuManagement.MenuManagementRepo;
import com.language.service.repo.permission.PermissionRepo;
import com.language.service.repo.user.UserRepo;
import com.language.service.rest.dto.response.BaseResponseDTO;
import com.language.service.service.abs.menumanagement.MenuManagementService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuManagementServiceImpl implements MenuManagementService {
    private static final Logger logger = LoggerFactory.getLogger(MenuManagementServiceImpl.class);
    private final MenuManagementMapper mapper;
    private final MenuManagementRepo repo;
    private final PermissionRepo permissionRepo;
    private final AuthenticationUtils authenticationUtils;
    private final PermissionMapper permissionMapper;
    private final UserRepo userRepo;
    private final GroupRepo groupRepo;

    public MenuManagementServiceImpl(MenuManagementMapper mapper, MenuManagementRepo repo, AuthenticationUtils authenticationUtils, PermissionRepo permissionRepo, PermissionMapper permissionMapper, UserRepo userRepo, GroupRepo groupRepo) {
        this.mapper = mapper;
        this.repo = repo;
        this.authenticationUtils = authenticationUtils;
        this.permissionRepo = permissionRepo;
        this.permissionMapper = permissionMapper;
        this.userRepo = userRepo;
        this.groupRepo = groupRepo;
    }

    @Override
    public Page<MenuManagementDTO> searchAll(SearchMenuDTO request, Pageable pageable) throws Exception {
        try {
            Page<MenuManagementDTO> rs = repo.searchAllMenu(request, pageable);
            return new PageImpl<>(rs.getContent(), pageable, rs.getTotalElements());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<MenuTreeDTO> searchTreeMenu() throws Exception {
        List<MenuTreeDTO> allMenus = repo.searchTreeMenu();
        List<MenuTreeDTO> parentMenus = allMenus.stream().filter(item -> DataUtils.isNullOrEmpty(item.getParent())).collect(Collectors.toList());
        Map<String, List<MenuTreeDTO>> childrenMap = buildTreeChildrenMap(allMenus);

        parentMenus.forEach(menu -> setTreeChildMenus(menu, childrenMap));
        return parentMenus;
    }

    @Override
    public List<MenuTreeDTOV2> getMenusTree(Long userId) throws Exception {
        // Lấy user theo id
        List<Group> groups = groupRepo.findGroupByUserId(userId, Constants.DELETE.INACTIVE);
        List<Long> groupIds = groups.stream().map(Group::getId).collect(Collectors.toList());

//        List<MenuTreeDTOV2> allMenus = repo.getTreeMenu();
        List<MenuTreeDTOV2> allMenus = repo.getTreeMenuByIdGroup(groupIds);

        //Nhóm các menu theo parentCode
        Map<String, List<MenuTreeDTOV2>> menuByParentCode = new HashMap<>();
        for (MenuTreeDTOV2 menu : allMenus) {
            menuByParentCode
                    .computeIfAbsent(menu.getParentCode(), k -> new ArrayList<>())
                    .add(menu);
        }

        List<MenuTreeDTOV2> rootMenus = new ArrayList<>();
        for (MenuTreeDTOV2 menu : allMenus) {
            if (menu.getParentCode() == null) {
                rootMenus.add(menu);
            }
        }

        for (MenuTreeDTOV2 menu : rootMenus) {
            buildMenuTree(menu, menuByParentCode);
        }
        return rootMenus;
    }

    /**
     * Hàm đệ quy để gán danh sách children cho từng menu.
     */
    private void buildMenuTree(MenuTreeDTOV2 parentMenu, Map<String, List<MenuTreeDTOV2>> menuByParentCode) {
        List<MenuTreeDTOV2> children = menuByParentCode.get(parentMenu.getCode());
        if (children != null && !children.isEmpty()) {
            parentMenu.setChildren(children);
            for (MenuTreeDTOV2 child : children) {
                buildMenuTree(child, menuByParentCode);
            }
        } else {
            parentMenu.setChildren(null);
        }
    }

    @Override
    public List<MenuPermissionTreeDTO> searchTreeMenuV2() throws Exception {
        List<MenuPermissionTreeDTO> allMenus = repo.searchTreeMenuV2();
        List<String> permissionCodeList = allMenus.stream().map(MenuPermissionTreeDTO::getMenuCode).toList();
        List<Permission> allPermission = permissionRepo.findAllByPermissionGroup(permissionCodeList);
        System.out.println("allPermission");
        System.out.println(allPermission);

        Map<String, List<MenuPermissionTreeDTO>> childrenMap = new HashMap<>();
        for (MenuPermissionTreeDTO node : allMenus) {
            String parentCode = node.getParentMenuCode();
            childrenMap.computeIfAbsent(parentCode, k -> new ArrayList<>()).add(node);
        }

        List<MenuPermissionTreeDTO> parentMenusPermission = allMenus.stream()
                .filter(item -> DataUtils.isNullOrEmpty(item.getParentMenuCode()))
                .collect(Collectors.toList());
        System.out.println("Tạo danh sách các mục cha");
        System.out.println(parentMenusPermission);

        for (MenuPermissionTreeDTO parent : parentMenusPermission) {
            System.out.println(parent.getMenuCode());
            List<MenuPermissionTreeDTO> children = childrenMap.get(parent.getMenuCode());
            parent.setMenus(children != null ? children : new ArrayList<>());
            parent.setPermissions(permissionMapper.toDTOs(
                    allPermission.stream()
                            .filter(s -> s.getPermissionGroup() != null && s.getPermissionGroup().equals(parent.getMenuCode()))
                            .toList()
            ));

        }

        System.out.println("Thiết lập lại cây");
        System.out.println(parentMenusPermission);

        return parentMenusPermission;
    }


    public Map<String, List<MenuManagementDTO>> buildChildrenMap(List<MenuManagementDTO> allMenus) {
        Map<String, List<MenuManagementDTO>> childrenMap = new HashMap<>();
        for (MenuManagementDTO menu : allMenus) {
            if (DataUtils.isNullOrEmpty(menu.getParentCode())) continue;
            childrenMap.computeIfAbsent(menu.getParentCode(), k -> new ArrayList<>()).add(menu);
        }
        return childrenMap;
    }

    public void setChildMenus(MenuManagementDTO parent, Map<String, List<MenuManagementDTO>> childrenMap) {
        List<MenuManagementDTO> children = childrenMap.get(parent.getCode());
        if (children != null && !children.isEmpty()) {
            parent.setHasChildren(true);
            for (MenuManagementDTO child : children) {
                setChildMenus(child, childrenMap);
            }
        } else {
            parent.setHasChildren(false);
        }
    }

    public Map<String, List<MenuTreeDTO>> buildTreeChildrenMap(List<MenuTreeDTO> allMenus) {
        Map<String, List<MenuTreeDTO>> childrenMap = new HashMap<>();
        for (MenuTreeDTO node : allMenus) {
            String parentCode = node.getParent();
            childrenMap.computeIfAbsent(parentCode, k -> new ArrayList<>()).add(node);
        }
        return childrenMap;
    }


    public void setTreeChildMenus(MenuTreeDTO parent, Map<String, List<MenuTreeDTO>> childrenMap) {
        List<MenuTreeDTO> children = childrenMap.get(parent.getLabel());
        if (children != null && !children.isEmpty()) {
            parent.setChildren(children);
            parent.setHasChildren(true);
            for (MenuTreeDTO child : children) {
                setTreeChildMenus(child, childrenMap);
            }
        } else {
            parent.setHasChildren(false);
        }
    }


    @Override
    public List<MenuManagementDTO> searchAllChild(String code) {
        try {
            List<MenuManagementDTO> rsDTO = mapper.toDtoList(repo.searchAlLChildMenu(code, Constants.DELETE.INACTIVE));
            List<MenuManagementDTO> allMenus = mapper.toDtoList(repo.findAll());
            Map<String, List<MenuManagementDTO>> childrenMap = buildChildrenMap(allMenus);
            rsDTO.forEach(menu -> setChildMenus(menu, childrenMap));
            return rsDTO;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponseDTO saveMenusManagement(MenuManagementDTO request) {
        BaseResponseDTO response = new BaseResponseDTO();
        try {
            if (request == null) throw new BusinessException(ConstantsErrorCode.MENU_MANAGEMENT_ERRORS.NOT_FOUND);
            if (repo.findByCode(request.getCode(), Constants.DELETE.INACTIVE).isPresent()) {
                throw new BusinessException(ConstantsErrorCode.MENU_MANAGEMENT_ERRORS.CODE_EXISTED);
            }
            if (DataUtils.isNullOrZero(request.getLevel()))
                throw new BusinessException(ConstantsErrorCode.MENU_MANAGEMENT_ERRORS.LEVEL_NULL_OR_ZERO);
            if (!DataUtils.isInteger(request.getLevel().toString()))
                throw new BusinessException(ConstantsErrorCode.MENU_MANAGEMENT_ERRORS.LEVEL_IS_NOT_NUMBER);

            if (!DataUtils.isNullOrEmpty(request.getParentCode())) {
                if (repo.findByCode(request.getParentCode(), Constants.DELETE.INACTIVE).isEmpty()) {
                    throw new BusinessException(ConstantsErrorCode.MENU_MANAGEMENT_ERRORS.PARENT_CODE_NOT_EXISTED);
                }
            }
            Menu entity = mapper.toEntity(request);
            String userName = authenticationUtils.currentUserName();
            entity.setCreatedBy(userName);
            entity.setCreatedDate(new Date());
            entity.setDeleted(Constants.DELETE.INACTIVE);
            repo.save(entity);
            response.setMessage("Thêm mới menu thành công!");
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponseDTO updateMenusManagement(MenuManagementDTO request) {
        BaseResponseDTO response = new BaseResponseDTO();
        try {
            if (request == null) throw new BusinessException(ConstantsErrorCode.MENU_MANAGEMENT_ERRORS.NOT_FOUND);
            Menu domain = repo.findById(request.getId())
                    .orElseThrow(() -> new BusinessException(ConstantsErrorCode.MENU_MANAGEMENT_ERRORS.NOT_FOUND));
            if (repo.checkExistsByIdAndErrorCode(request.getId(), request.getCode(), Constants.DELETE.INACTIVE).isPresent())
                throw new BusinessException(ConstantsErrorCode.MENU_MANAGEMENT_ERRORS.CODE_EXISTED);

            if (DataUtils.isNullOrZero(request.getLevel()))
                throw new BusinessException(ConstantsErrorCode.MENU_MANAGEMENT_ERRORS.LEVEL_NULL_OR_ZERO);
            if (!DataUtils.isInteger(request.getLevel().toString()))
                throw new BusinessException(ConstantsErrorCode.MENU_MANAGEMENT_ERRORS.LEVEL_IS_NOT_NUMBER);

            if (!DataUtils.isNullOrEmpty(request.getParentCode())) {
                if (repo.findByCode(request.getParentCode(), Constants.DELETE.INACTIVE).isEmpty()) {
                    throw new BusinessException(ConstantsErrorCode.MENU_MANAGEMENT_ERRORS.PARENT_CODE_NOT_EXISTED);
                }
                if (request.getParentCode().equals(domain.getCode()))
                    throw new BusinessException(ConstantsErrorCode.MENU_MANAGEMENT_ERRORS.MENU_CODE_EQUAL_PARENT_CODE);
            }
            domain.setCode(request.getCode());
            domain.setParentCode(request.getParentCode());
            domain.setLevel(request.getLevel());
            domain.setOrder(request.getOrder());
            domain.setPath(request.getPath());
            domain.setName(request.getName());
            domain.setIcon(request.getIcon());
            domain.setTitle(request.getTitle());
            domain.setComponent(request.getComponent());
            domain.setDescription(request.getDescription());
            repo.save(domain);
            response.setMessage("Cập nhật menu thành công!");
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponseDTO deleteMenusManagement(Long id) {
        BaseResponseDTO response = new BaseResponseDTO();
        try {
            if (DataUtils.isNullOrZero(id))
                throw new BusinessException(ConstantsErrorCode.MENU_MANAGEMENT_ERRORS.ID_REQUIRED);
            Menu entity = repo.findById(id).orElseThrow(() -> new BusinessException(ConstantsErrorCode.MENU_MANAGEMENT_ERRORS.NOT_FOUND));
            if (!DataUtils.isNullOrEmpty(repo.findAllMenuChildByCode(entity.getCode(), Constants.DELETE.INACTIVE)))
                throw new BusinessException(ConstantsErrorCode.MENU_MANAGEMENT_ERRORS.MENU_HAS_CHILD);
            String userName = authenticationUtils.currentUserName();
            if (DataUtils.isNullOrEmpty(userName))
                throw new BusinessException(ConstantsErrorCode.MENU_MANAGEMENT_ERRORS.USERNAME_NOT_FOUND);
            entity.setDeletedBy(userName);
            entity.setDeletedDate(new Date());
            entity.setDeleted(Constants.DELETE.ACTIVE);
            repo.save(entity);
            response.setMessage("Xóa menu thành công!");
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }
}
