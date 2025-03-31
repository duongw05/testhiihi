package com.language.service.repo.jpa.actionaudit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.language.service.domain.entities.ActionAudit;
import com.language.service.repo.dao.actionaudit.ActionAuditCustomRepo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActionAuditRepo extends JpaRepository< ActionAudit, Long>, ActionAuditCustomRepo  {
    @Query("SELECT a FROM ActionAudit a WHERE a.objectParentId = :objectId " +
            " and a.tableName = :tableName and a.deleted = :deleted order by a.createdDate desc ")
    Page<ActionAudit> findByObjectId(@Param("objectId") Long objectId, @Param("tableName") String tableName, @Param("deleted") int deleted, Pageable pageable);
}
