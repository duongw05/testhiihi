package com.language.service.repo.dao.actiondetail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ActionDetailCustomRepoImpl implements ActionDetailCustomRepo {
    protected final Logger LOG = LogManager.getLogger(this.getClass());
    @PersistenceContext
    private EntityManager em;
}
