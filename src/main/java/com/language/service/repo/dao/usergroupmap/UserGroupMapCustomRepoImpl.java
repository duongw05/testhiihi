package com.language.service.repo.dao.usergroupmap;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import com.language.service.common.Constants;
import com.language.service.domain.dtos.GroupUserDTO;

import java.util.List;

@Repository
public class UserGroupMapCustomRepoImpl implements UserGroupMapCustomRepo {
    protected final Logger LOG = LogManager.getLogger(this.getClass());
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<GroupUserDTO> groupUserDTOS(List<Long> userIds) {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select g.id, gmm.user.id, g.name, g.description from Group g inner join UserGroupMap gmm on g.id = gmm.group.id where" +
                    " gmm.status = :status and gmm.deleted = :deleted and g.deleted = :deleted and gmm.user.id in :usersId ");
            TypedQuery<GroupUserDTO> query = em.createQuery(sql.toString(), GroupUserDTO.class);
            query.setParameter("usersId", userIds);
            query.setParameter("status", Constants.STATUS.ACTIVE);
            query.setParameter("deleted", Constants.DELETE.INACTIVE);

            return query.getResultList();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }
}
