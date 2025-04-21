package com.language.service.repo.dao.functioncatalog;

import com.language.service.common.Constants;
import com.language.service.domain.dtos.PermissionCodeDTO;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FunctionCatalogCustomRepoImpl implements FunctionCatalogCustomRepo {
    protected final Logger LOG = LogManager.getLogger(this.getClass());
    @PersistenceContext
    private EntityManager em;
    @Override
    public boolean hasPermission(long userId, String permissionCode) {
        Query query = em.createNativeQuery("select count(*)\n" +
                        "from tbl_user u\n" +
                        "         inner join user_group_map ugm on u.id = ugm.user_id\n" +
                        "         inner join group_function gf on ugm.group_id = gf.group_id\n" +
                        "         inner join function_catalog p on gf.FUNCTION_ID = p.id\n" +
                        "where u.id = :userId\n" +
                        "  and p.FUNCTION_CODE = :permCode\n" +
                        "  and u.deleted = :deleted\n" +
                        "  and ugm.deleted = :deleted\n" +
                        "  and gf.deleted = :deleted\n" +
                        "  and p.deleted = :deleted")
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
