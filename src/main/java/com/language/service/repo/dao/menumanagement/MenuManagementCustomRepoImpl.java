package com.language.service.repo.dao.menumanagement;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import com.language.service.common.Constants;
import com.language.service.common.utils.DataUtils;
import com.language.service.domain.dtos.*;

import java.util.HashMap;
import java.util.List;

public class MenuManagementCustomRepoImpl implements MenuManagementCustomRepo {
    protected final Logger LOG = LogManager.getLogger(this.getClass());
    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<MenuManagementDTO> searchAllMenu(SearchMenuDTO request, Pageable pageable) throws Exception {
        List<String> attConvert = DataUtils.changeParamTypeSqlToJava("id,code,order,level,parentCode,description, path, name, title, icon, component,hasChildren");
        HashMap<String, Object> mapParam = new HashMap<>();
        String sql = "select distinct  m.id , m.code, m.order, m.level, m.parentCode,m.description, m.path, m.name, m.title, m.icon, m.component, (select case when count(1) = 0 then false else true end from Menu mm where mm.parentCode = m.code and mm.deleted =:deleted) as hasChildren from Menu m where #sqlWhere and m.deleted = :deleted \n";
        String sqlWhere = " 1=1 ";

        if (DataUtils.isNullOrEmpty(request.getCode())) {
            sqlWhere += " and m.parentCode is null \n";
        } else {
            sqlWhere += " and m.code like :code escape '\\' \n";
        }

        DataUtils.trimObject(request, true, null, null);

        if (!DataUtils.isNullOrEmpty(request.getCode())) {
            mapParam.put("code", DataUtils.makeLikeQuery(request.getCode()));
        }
        mapParam.put("deleted", Constants.DELETE.INACTIVE);
        sql = sql.replace("#sqlWhere", sqlWhere);

        sql += " order by m.order \n";

        Query query = em.createQuery(sql);

        for (HashMap.Entry<String, Object> param : mapParam.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        long allItem = countAllItem();

        List<Object[]> listResult = query.getResultList();
        List<MenuManagementDTO> ls = DataUtils.convertListObjectsToClass(attConvert, listResult, MenuManagementDTO.class);
        return new PageImpl<>(ls, pageable, allItem);
    }

    @Override
    public List<MenuTreeDTO> searchTreeMenu() throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append(" select m.id as value, m.code as label, m.level , m.parentCode from Menu m where m.deleted = :deleted \n");
        TypedQuery<MenuTreeDTO> query = em.createQuery(sql.toString(), MenuTreeDTO.class);
        query.setParameter("deleted", Constants.DELETE.INACTIVE);
        List<MenuTreeDTO> listResult = query.getResultList();
        return listResult;
    }

    @Override
    public List<MenuTreeDTOV2> getTreeMenu() {
        String sql = """
                    SELECT m.id, m.code, m.parentCode, m.name, m.title, m.path, m.component, m.order, m.level, m.hideMenu, m.icon, m.activeRouter FROM Menu m where m.deleted = :deleted order by m.order
                """;
        TypedQuery<MenuTreeDTOV2> query = em.createQuery(sql, MenuTreeDTOV2.class);
        query.setParameter("deleted", Constants.DELETE.INACTIVE);
        List<MenuTreeDTOV2> listResult = query.getResultList();
        return listResult;
    }

    @Override
    public List<MenuTreeDTOV2> getTreeMenuByIdGroup(List<Long> groupIds) {
        String sql = """
                    SELECT DISTINCT m.id, m.code, m.parentCode, m.name, m.title, m.path, m.component, m.order, m.level, m.hideMenu, m.icon, m.activeRouter
                    FROM Menu m
                    INNER JOIN GroupMenuMap gmm on gmm.menu.id = m.id
                    INNER JOIN Group g on g.id = gmm.group.id
                    WHERE
                         m.deleted = :deleted
                    AND
                         gmm.deleted = :deleted
                    AND
                         g.deleted = :deleted
                    AND gmm.group.id IN (:groupIds)
                    ORDER BY m.order
                """;
        TypedQuery<MenuTreeDTOV2> query = em.createQuery(sql, MenuTreeDTOV2.class);
        query.setParameter("deleted", Constants.DELETE.INACTIVE);
        query.setParameter("groupIds", groupIds);
        List<MenuTreeDTOV2> listResult = query.getResultList();
        return listResult;
    }

    @Override
    public List<MenuPermissionTreeDTO> searchTreeMenuV2() throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append(" select m.id as id, m.code as menuCode, m.parentCode as parentMenuCode, m.level as level, m.order as order from Menu m where m.deleted = :deleted \n");
        TypedQuery<MenuPermissionTreeDTO> query = em.createQuery(sql.toString(), MenuPermissionTreeDTO.class);
        query.setParameter("deleted", Constants.DELETE.INACTIVE);
        List<MenuPermissionTreeDTO> listResult = query.getResultList();
        return listResult;
    }

    private long countAllItem() {
        String sql = "SELECT COUNT(*) FROM Menu p where p.deleted = :deleted and p.parentCode is null";

        Query query = em.createQuery(sql);
        query.setParameter("deleted", Constants.DELETE.INACTIVE);

        Object result = query.getSingleResult();

        return DataUtils.safeToLong(result);
    }
}
