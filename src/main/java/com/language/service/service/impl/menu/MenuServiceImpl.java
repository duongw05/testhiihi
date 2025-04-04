package com.language.service.service.impl.menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.language.service.common.Constants;
import com.language.service.common.ConstantsErrorCode;
import com.language.service.common.utils.AuthenticationUtils;
import com.language.service.common.utils.DataUtils;
import com.language.service.domain.dtos.MenuDTO;
import com.language.service.domain.entities.Group;
import com.language.service.domain.entities.GroupMenuMap;
import com.language.service.domain.entities.Menu;
import com.language.service.exception.BusinessException;
import com.language.service.repo.group.GroupRepo;
import com.language.service.repo.jpa.groupmenumap.GroupMenuMapRepo;
import com.language.service.repo.menu.MenuRepo;
import com.language.service.rest.dto.request.SearchMenuPermissionDTO;
import com.language.service.rest.dto.response.BaseResponseDTO;
import com.language.service.rest.dto.response.GroupPermissionResponseDTO;
import com.language.service.service.AbstractService;
import com.language.service.service.abs.menu.MenuService;
import com.language.service.service.abs.permission.PermissionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
@Transactional
public class MenuServiceImpl extends AbstractService<Menu, Long> implements MenuService {
    protected final Logger LOG = LogManager.getLogger(this.getClass());

    private final MenuRepo menuRepo;
    private final GroupRepo groupRepo;
    private final GroupMenuMapRepo groupMenuMapRepo;
    private final AuthenticationUtils authenticationUtils;
    private final PermissionService permissionService;

    @Autowired
    public MenuServiceImpl(MenuRepo menuRepo, MenuRepo menuRepo1, GroupRepo groupRepo, GroupMenuMapRepo groupMenuMapRepo, AuthenticationUtils authenticationUtils, PermissionService permissionService) {
        super(menuRepo);
        this.menuRepo = menuRepo1;
        this.groupRepo = groupRepo;
        this.groupMenuMapRepo = groupMenuMapRepo;
        this.authenticationUtils = authenticationUtils;
        this.permissionService = permissionService;
    }


    @Override
    public List<MenuDTO> getAll() {
        List<MenuDTO> allMenus = menuRepo.getAll();
        List<MenuDTO> level1Menus = new ArrayList<>();
        if (allMenus.size() > 0) {
            List<Integer> toRemoveLevel1 = new ArrayList<>();
            int index = 0;
            for (MenuDTO m : allMenus) {
                if (m.getLevel() == 1) {
                    level1Menus.add(m);
                    toRemoveLevel1.add(index);
                }

                index++;
            }
            if (toRemoveLevel1.size() > 0) {
                remove(allMenus, toRemoveLevel1);
            }


            // Add all level 2 to level 1
            if (level1Menus.size() > 0) {
                List<Integer> toRemoveLevel2 = new ArrayList<>();
                for (MenuDTO m1 : level1Menus) {
                    int indexLevel2 = 0;
                    for (MenuDTO iterateMenu : allMenus) {
                        if (m1.getCode().equals(iterateMenu.getParentCode())) {
                            m1.addChild(iterateMenu);
                        }
                        indexLevel2++;
                    }
                }
                remove(allMenus, toRemoveLevel2);
            }

            // Finally return list of level1 menus,
            // which include all level2 and level3 menus
            return level1Menus;
        } else {
            return allMenus;
        }
    }
    

    @Override
    public Page<MenuDTO> searchPageMenu(long groupId, SearchMenuPermissionDTO searchMenuPermissionDTO, Pageable pageable) {
        // Lấy menu cấp cao nhất (cha)
        Page<MenuDTO> menuPage = groupMenuMapRepo.getMenuUserGroupAll(groupId, searchMenuPermissionDTO, pageable);
        List<MenuDTO> allMenus = menuPage.getContent();

        // Lấy toàn bộ menu từ cấp 2 trở lên
        List<MenuDTO> allChildMenus = groupMenuMapRepo.getAllChildMenus(groupId);

        // Tạo map phân loại menu theo parentCode
        Map<String, List<MenuDTO>> menuMap = allChildMenus.stream()
                .collect(Collectors.groupingBy(MenuDTO::getParentCode));

        // Lấy tất cả quyền
        List<GroupPermissionResponseDTO> groupPermissionResponseDTOS = permissionService.findAllDTO(groupId, null).getContent();

        // Gán quyền cho menu cấp cao nhất và xây dựng cấu trúc menu
        for (MenuDTO allMenu : allMenus) {
            List<GroupPermissionResponseDTO> listToAdd = groupPermissionResponseDTOS.stream()
                    .filter(s -> allMenu.getCode().equals(s.getPermissionGroup()))
                    .toList();
            allMenu.setGroupPermissionResponseDTOS(listToAdd);

            // Xây dựng cấu trúc menu trong bộ nhớ
            buildMenuHierarchyFromMap(allMenu, menuMap, groupPermissionResponseDTOS);
        }

        return new PageImpl<>(allMenus, pageable, menuPage.getTotalElements());
    }


    /**
     * Xây dựng cấu trúc menu
     */
    private void buildMenuHierarchyFromMap(
            MenuDTO parentMenu,
            Map<String, List<MenuDTO>> menuMap,
            List<GroupPermissionResponseDTO> groupPermissionResponseDTOS) {

        // Lấy danh sách menu con từ map
        List<MenuDTO> childMenus = menuMap.getOrDefault(parentMenu.getCode(), new ArrayList<>());

        for (MenuDTO childMenu : childMenus) {
            // Gán quyền cho menu con
            List<GroupPermissionResponseDTO> permissions = groupPermissionResponseDTOS.stream()
                    .filter(s -> childMenu.getCode().equals(s.getPermissionGroup()))
                    .toList();
            childMenu.setGroupPermissionResponseDTOS(permissions);

            // Gọi đệ quy để xây dựng cấu trúc con
            buildMenuHierarchyFromMap(childMenu, menuMap, groupPermissionResponseDTOS);
        }

        // Gán danh sách menu con vào menu cha
        parentMenu.setChildren(childMenus);
    }



    @Override
    @Cacheable(cacheNames = {Constants.CACHE_KEY_NAME.CACHE_MENU_ALL_KEY})
    public List<String> getMenuCodesByUser(long userId) {
        return menuRepo.getMenuCodesByUser(userId);
    }

    private List<Integer> reverse(List<Integer> originalList) {
        if (originalList == null) {
            return null;
        }
        if (originalList.isEmpty()) {
            return originalList;
        }

        List<Integer> result = new ArrayList<>();
        for (int i = originalList.size() - 1; i > 0; i--) {
            result.add(originalList.get(i));
        }
        return result;
    }

    private void remove(List<MenuDTO> allMenus, List<Integer> toRemoveIndexes) {
        if (allMenus == null || allMenus.isEmpty() || toRemoveIndexes == null || toRemoveIndexes.isEmpty()) {
            return;
        }
        toRemoveIndexes = reverse(toRemoveIndexes);
        for (Integer rmIndex : toRemoveIndexes) {
            allMenus.remove(rmIndex.intValue());
        }
    }


    @Override
    @Transactional(rollbackFor = Throwable.class)
    @CacheEvict(cacheNames = {Constants.CACHE_KEY_NAME.CACHE_MENU_ALL_KEY}, allEntries = true)
    public BaseResponseDTO linkMenuWithGroup(long id, long groupId) {
        try {
            System.out.println("MENU  ID: " + id);
            System.out.println("GROUP ID: " + groupId);
//            GroupMenuMap groupMenuMap = groupMenuMapRepo.findByGroupAndMenu(groupId, id, Constants.DELETE.ACTIVE).orElse(null);;
            GroupMenuMap groupMenuMap = groupMenuMapRepo.findByGroupAndMenuWithoutDelete(groupId, id).orElse(null);
            if (groupMenuMap == null) {
                System.out.println("NULL");
                Menu menu = menuRepo.findById(id).orElseThrow(() -> new BusinessException(ConstantsErrorCode.MENU.ERROR_MENU_NOT_FOUND));
                Group group = groupRepo.findById(groupId).orElseThrow(() -> new BusinessException(ConstantsErrorCode.GROUP.ERROR_GROUP_NOT_FOUND));
                groupMenuMap = new GroupMenuMap();
                groupMenuMap.setMenuId(menu.getId());
                groupMenuMap.setGroupId(group.getId());
            } else {
                System.out.println("NOT NULL");
            }
            groupMenuMap.setDeleted(Constants.DELETE.INACTIVE);
            groupMenuMapRepo.save(groupMenuMap);
            return new BaseResponseDTO();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    @CacheEvict(cacheNames = {Constants.CACHE_KEY_NAME.CACHE_MENU_ALL_KEY}, allEntries = true)
    public BaseResponseDTO unlinkMenuWithGroup(long id, long groupId) {
        try {
//            GroupMenuMap groupMenuMap = groupMenuMapRepo.findByGroupAndMenu(groupId, id, Constants.DELETE.INACTIVE).orElseThrow(()
//                    -> new BusinessException(ConstantsErrorCode.GROUP_PERMISSION.ERROR_PERMISSION_GROUP_NOT_FOUND));
            GroupMenuMap groupMenuMap = groupMenuMapRepo.findByGroupAndMenuWithoutDelete(groupId, id).orElseThrow(()
                    -> new BusinessException(ConstantsErrorCode.GROUP_PERMISSION.ERROR_PERMISSION_GROUP_NOT_FOUND));
            groupMenuMap.setDeleted(Constants.DELETE.ACTIVE);
            groupMenuMap.setDeletedBy(authenticationUtils.currentUserName());
            groupMenuMap.setDeletedDate(new Date());
            return new BaseResponseDTO();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

}
