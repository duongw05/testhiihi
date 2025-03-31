package com.language.service.repo.dao.grouppermissionmap;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.language.service.common.Constants;
import com.language.service.common.utils.DataUtils;
import com.language.service.rest.dto.response.GroupPermissionResponseDTO;

import java.util.List;

@Repository
public class GroupPermissionMapCustomRepoImpl implements GroupPermissionMapCustomRepo {
    protected final Logger LOG = LogManager.getLogger(this.getClass());
    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<GroupPermissionResponseDTO> getPermissionUserGroupAll(long groupId, Pageable pageable) {
        try {
            System.out.println("=======");
            StringBuilder sql = new StringBuilder();
            sql.append("select distinct p.id, p.code as permissionCode, p.name as permissionName, p.permissionGroup as permissionGroup, a.group_id ")
                    .append("   from Permission p ").append("   left join (select gp.group.id as group_id, gp.permission.id as permission_id")
                    .append("   from GroupPermissionMap gp")
                    .append(" inner join UserGroupMap ug on gp.group.id = ug.group.id ")
                    .append("  where gp.deleted = :deleted and ug.deleted = :deleted" +
                            " and ug.group.id = :groupId) a on p.id = a.permission_id")
                    .append("   where p.deleted = :deleted order by p.id desc ");
            TypedQuery<GroupPermissionResponseDTO> query = em.createQuery(sql.toString(), GroupPermissionResponseDTO.class);
            query.setParameter("groupId", groupId);
            query.setParameter("deleted", Constants.DELETE.INACTIVE);

            if (pageable != null) {
                query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
                query.setMaxResults(pageable.getPageSize());
                long allItem = countAllItem();
                List<GroupPermissionResponseDTO> listResult = query.getResultList();
                return new PageImpl<>(listResult, pageable, allItem);
            } else {
                List<GroupPermissionResponseDTO> listResult = query.getResultList();
                return new PageImpl<>(listResult, Pageable.unpaged(), listResult.size());
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    private long countAllItem() {
        String sql = "SELECT COUNT(*) FROM Permission p where p.deleted = :deleted";

        Query query = em.createQuery(sql);
        query.setParameter("deleted", Constants.DELETE.INACTIVE);

        Object result = query.getSingleResult();

        return DataUtils.safeToLong(result);
    }
}
