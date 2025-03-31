package com.language.service.repo.permission;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import com.language.service.common.Constants;
import com.language.service.domain.dtos.PermissionCodeDTO;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PermissionRepoCustomImpl implements PermissionRepoCustom {

    private final EntityManager em;

    @Autowired
    public PermissionRepoCustomImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean hasPermission(long userId, String permissionCode) {
        Query query = em.createNativeQuery("select count(*) " +
                        " from tbl_user u " +
                        "  inner join user_group_map ugm on u.id = ugm.user_id " +
                        "  inner join group_permission_map gpm on ugm.group_id = gpm.group_id " +
                        "  inner join permission p on gpm.permission_id = p.id " +
                        " where u.id = :userId and p.code = :permCode" +
                        " and gpm.deleted = :deleted and p.deleted = :deleted and gpm.deleted = :deleted and ugm.deleted = :deleted")
                .setParameter("userId", userId)
                .setParameter("deleted", Constants.DELETE.INACTIVE)
                .setParameter("permCode", permissionCode);
        Number count = (Number) query.getSingleResult();

        return count.intValue() > 0;
    }

    @Override
    public List<String> allPermCode(long userId) {
        TypedQuery<PermissionCodeDTO> query = em.createNamedQuery("allPermCode", PermissionCodeDTO.class)
                .setParameter("userId", userId)
                .setParameter("deleted", Constants.DELETE.INACTIVE);

        List<PermissionCodeDTO> permissionCodeDTOList = query.getResultList();
        final List<String> codes = new ArrayList<>();
        if (permissionCodeDTOList != null && !permissionCodeDTOList.isEmpty()) {
            permissionCodeDTOList.forEach(dto -> codes.add(dto.getCode()));
        }
        return codes;
    }
}
