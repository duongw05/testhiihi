package com.language.service.repo.jpa.actiondetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.language.service.domain.entities.ActionDetail;
import com.language.service.repo.dao.actiondetail.ActionDetailCustomRepo;

import java.util.List;

public interface ActionDetailRepo extends JpaRepository<ActionDetail, Long>, ActionDetailCustomRepo {

    @Query("from ActionDetail a where a.actionAuditId in :ids")
    List<ActionDetail> findAllByActionAudit(List<Long> ids);
}
