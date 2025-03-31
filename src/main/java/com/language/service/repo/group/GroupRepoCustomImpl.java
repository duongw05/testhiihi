package com.language.service.repo.group;

import com.language.service.rest.dto.request.AddMenusToGroup;
import com.language.service.rest.dto.request.AddPermissionsToGroup;
import com.language.service.rest.dto.request.UnlinkMenuRequest;
import com.language.service.rest.dto.request.UnlinkPermissionsFromGroup;
import com.language.service.rest.dto.request.searchparams.GroupSearchParams;
import com.language.service.domain.dtos.GroupDTO;
import com.language.service.domain.dtos.GroupIdDTO;
import com.language.service.domain.dtos.MenuDTO;
import com.language.service.domain.dtos.PermissionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class GroupRepoCustomImpl implements GroupRepoCustom {

    private final EntityManager em;
    private final GroupDTORepo groupDTORepo;

    public GroupRepoCustomImpl(EntityManager em, GroupDTORepo groupDTORepo) {
        this.em = em;
        this.groupDTORepo = groupDTORepo;
    }

    @Override
    public List<PermissionDTO> getGroupPermissions(long groupId) {
        TypedQuery<PermissionDTO> query = em.createNamedQuery("getGroupPermissions", PermissionDTO.class)
                .setParameter("groupId", groupId);
        return query.getResultList();
    }

    @Override
    public List<PermissionDTO> getNotGrantedPermissions(long groupId) {
        TypedQuery<PermissionDTO> query = em.createNamedQuery("getNotGrantedPermissions", PermissionDTO.class)
                .setParameter("groupId", groupId);
        return query.getResultList();
    }


    @Override
    public List<MenuDTO> getNotGrantedMenus(long groupId) {
        TypedQuery<MenuDTO> typedQuery = em.createNamedQuery("getNotGrantedMenus", MenuDTO.class);
        typedQuery.setParameter("groupId", groupId);
        return typedQuery.getResultList();
    }

    @Override
    public void addMenus(long groupId, AddMenusToGroup command) {
        if (command != null && command.getMenuIds() != null && command.getMenuIds().size() > 0) {
            Query query = em.createNativeQuery(
                    "insert into group_menu_map (group_id, menu_id) " +
                            " VALUES (:groupId, :menuId) ");
            for (Long menuId : command.getMenuIds()) {
                query.setParameter("groupId", groupId);
                query.setParameter("menuId", menuId);
                query.executeUpdate();
            }
        }
    }

    @Override
    public void unlinkMenu(long groupId, UnlinkMenuRequest command) {
        if (command != null && command.getMenuId() != null) {
            Query query = em.createNativeQuery(
                    "delete from group_menu_map where group_id = :groupId and menu_id = :menuId")
                    .setParameter("groupId", groupId)
                    .setParameter("menuId", command.getMenuId());
            query.executeUpdate();
        }
    }

    @Override
    public void unlinkPermission(long groupId, UnlinkPermissionsFromGroup command) {
        if (command != null) {
            List<Long> permissionIds = command.getPermissionIds();
            if (permissionIds != null && permissionIds.size() > 0) {
                for (Long permId : permissionIds) {
                    Query query = em.createNativeQuery(
                            "delete from group_permission_map where group_id = :groupId and permission_id = :permissionId")
                            .setParameter("groupId", groupId)
                            .setParameter("permissionId", permId);
                    query.executeUpdate();
                }
            }
        }
    }

    @Override
    public void addPermissions(long groupId, AddPermissionsToGroup command) {
        Query query = em.createNativeQuery("insert into group_permission_map (group_id, permission_id) " +
                " VALUES (:groupId, :permissionId) ");
        if (command != null && command.getPermissionIds() != null && !command.getPermissionIds().isEmpty()) {
            for (Long permId : command.getPermissionIds()) {
                query.setParameter("groupId", groupId);
                query.setParameter("permissionId", permId);
                query.executeUpdate();
            }
        }
    }

    @Override
    public List<GroupDTO> getGroupsOfUser(long userId) {
        TypedQuery<GroupIdDTO> query = em.createNamedQuery("getGroupsOfUser", GroupIdDTO.class)
                .setParameter("userId", userId);
        List<GroupIdDTO> res = query.getResultList();
        if (res == null || res.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> groupIds = res.stream().map(GroupIdDTO::getId).collect(Collectors.toList());
        GroupSearchParams params = new GroupSearchParams();
        params.setIdIn(groupIds);
        Page<GroupDTO> page = groupDTORepo.search(params, PageRequest.of(0, Integer.MAX_VALUE), false);
        return page.getContent();
    }


}
