package com.language.service.repo.dao.groupmenumap;

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
import com.language.service.domain.dtos.MenuDTO;
import com.language.service.rest.dto.request.SearchMenuPermissionDTO;

import java.util.List;

@Repository
public class GroupMenuMapCustomRepoImpl implements GroupMenuMapCustomRepo {
    protected final Logger LOG = LogManager.getLogger(this.getClass());
    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<MenuDTO> getMenuUserGroupAll(long groupId, SearchMenuPermissionDTO searchMenuPermissionDTO, Pageable pageable) {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select distinct p.id, p.code, p.name, p.order, p.level, p.parentCode, p.description ,a.group_id, p.title ")
                    .append(" from Menu p ")
                    .append(" left join (select gp.group.id as group_id, gp.menu.id as menu_id")
                    .append(" from GroupMenuMap gp")
                    .append(" inner join UserGroupMap ug on gp.group.id = ug.group.id ")
                    .append(" where gp.deleted = :deleted and ug.deleted = :deleted and ug.group.id = :groupId) a on p.id = a.menu_id")
                    .append(" left join Permission per on per.permissionGroup = p.code ")
                    .append(" where (p.name like :menuName or :menuName is null) and")
                    .append(" (p.code like :menuCode or :menuCode is null) and")
                    .append(" (per.name like :permissionName or :permissionName is null) and")
                    .append(" (per.code like :permissionCode or :permissionCode is null) and")
                    .append(" p.deleted = :deleted and p.parentCode is null order by p.order");
            // Tạo truy vấn và thiết lập tham số
            TypedQuery<MenuDTO> query = em.createQuery(sql.toString(), MenuDTO.class);
            query.setParameter("groupId", groupId);
            query.setParameter("deleted", Constants.DELETE.INACTIVE);
            query.setParameter("menuName", DataUtils.makeLikeQuery(searchMenuPermissionDTO.getMenuName()));
            query.setParameter("menuCode", DataUtils.makeLikeQuery(searchMenuPermissionDTO.getMenuCode()));
            query.setParameter("permissionName", DataUtils.makeLikeQuery(searchMenuPermissionDTO.getPermissionName()));
            query.setParameter("permissionCode", DataUtils.makeLikeQuery(searchMenuPermissionDTO.getPermissionCode()));
            query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
            long allItem = countAllItem();
            List<MenuDTO> listResult = query.getResultList();
            return new PageImpl<>(listResult, pageable, allItem);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    private long countAllItem() {
        String sql = "SELECT COUNT(*) FROM Menu p where p.deleted = :deleted and p.parentCode is null";

        Query query = em.createQuery(sql);
        query.setParameter("deleted", Constants.DELETE.INACTIVE);

        Object result = query.getSingleResult();

        return DataUtils.safeToLong(result);
    }

    public List<MenuDTO> getAllChildMenus(long groupId) {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select distinct p.id, p.code, p.name, p.order, p.level, p.parentCode, p.description ,a.group_id, p.title ")
                    .append("from Menu p ")
                    .append("left join (select gp.group.id as group_id, gp.menu.id as menu_id ")
                    .append("from GroupMenuMap gp ")
                    .append("inner join UserGroupMap ug on gp.group.id = ug.group.id ")
                    .append("where gp.deleted = :deleted and ug.deleted = :deleted and ug.group.id = :groupId) a ")
                    .append("on p.id = a.menu_id ")
                    .append("where p.deleted = :deleted and p.level >= 2 ")
                    .append("order by p.level, p.order");

            TypedQuery<MenuDTO> query = em.createQuery(sql.toString(), MenuDTO.class);
            query.setParameter("groupId", groupId);
            query.setParameter("deleted", Constants.DELETE.INACTIVE);
            return query.getResultList();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }


    public List<MenuDTO> getMenuUserGroupByLevel(List<String> parentCode, long groupId, int level) {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select distinct p.id, p.code, p.name, p.order, p.level, p.parentCode, p.description ,a.group_id ")
                    .append("   from Menu p ").append("  left join (select gp.group.id as group_id, gp.menu.id as menu_id")
                    .append("   from GroupMenuMap gp")
                    .append(" inner join UserGroupMap ug on gp.group.id = ug.group.id ")
                    .append("  where gp.deleted = :deleted and ug.deleted = :deleted" +
                            " and ug.group.id = :groupId) a on p.id = a.menu_id")
                    .append("   where p.deleted = :deleted and p.parentCode in :parentCode and p.level = :level order by p.order ");
            TypedQuery<MenuDTO> query = em.createQuery(sql.toString(), MenuDTO.class);
            query.setParameter("groupId", groupId);
            query.setParameter("deleted", Constants.DELETE.INACTIVE);
            query.setParameter("parentCode", parentCode);
            query.setParameter("level", level);
            return query.getResultList();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    /** Query lấy danh sách các menu cấp thấp nhất*/
    public Page<MenuDTO> getMenuLowLevel(int deleted, long groupId, Pageable pageable) {
        try {
            // Truy vấn chính với phân trang
            String sql = """
            SELECT distinct p.id, p.code, p.name, p.order, p.level, p.parentCode, p.description, a.group_id, p.title
            FROM Menu p
            LEFT JOIN (
                SELECT gp.group.id AS group_id, gp.menu.id AS menu_id
                FROM GroupMenuMap gp
                INNER JOIN UserGroupMap ug ON gp.group.id = ug.group.id
                WHERE gp.deleted = :deleted AND ug.deleted = :deleted AND ug.group.id = :groupId
            ) a ON p.id = a.menu_id
            LEFT JOIN Permission per ON per.permissionGroup = p.code
            WHERE p.deleted = :deleted
              AND p.parentCode IS NOT NULL
              AND NOT EXISTS (
                SELECT 1
                FROM Menu m2
                WHERE m2.parentCode = p.code
                  AND m2.deleted = :deleted
            )
            ORDER BY p.parentCode
        """;

            TypedQuery<MenuDTO> query = em.createQuery(sql, MenuDTO.class);
            query.setParameter("deleted", deleted);
            query.setParameter("groupId", groupId);
            query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());

            List<MenuDTO> listResult = query.getResultList();

            // Truy vấn đếm tổng số item
            String countSql = """
            SELECT COUNT(distinct p.id)
            FROM Menu p
            LEFT JOIN GroupMenuMap gp ON p.id = gp.menu.id
            LEFT JOIN UserGroupMap ug ON gp.group.id = ug.group.id
            WHERE p.deleted = :deleted
              AND p.parentCode IS NOT NULL
              AND NOT EXISTS (
                SELECT 1
                FROM Menu m2
                WHERE m2.parentCode = p.code
                  AND m2.deleted = :deleted
            )
              AND ug.group.id = :groupId
        """;

            TypedQuery<Long> countQuery = em.createQuery(countSql, Long.class);
            countQuery.setParameter("deleted", deleted);
            countQuery.setParameter("groupId", groupId);
            long allItem = countQuery.getSingleResult();

            // Trả về trang kết quả
            return new PageImpl<>(listResult, pageable, allItem);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }


}