package com.language.service.repo.dao.school;

import com.language.service.domain.dtos.SchoolDTO;
import com.language.service.domain.dtos.SearchMenuDTO;
import com.language.service.domain.dtos.SearchSchoolDTO;
import jakarta.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SchoolCustomRepoImpl implements SchoolCustomRepo {
    protected final Logger LOG = LogManager.getLogger(this.getClass());
    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<SchoolDTO> searchAll(SearchSchoolDTO request, Pageable pageable) {
        String baseQuery = """
        SELECT new com.language.service.domain.dtos.SchoolDTO(
            s.id, s.code, s.name, s.status,
            wo.id, wo.name,
            st.id, st.name,
            s.isPremium, s.map, s.latitude, s.longtitude,
            s.area, s.address, s.programs, s.areaCvct,
            s.minuteNum, s.rhta, s.scheduler, s.schedulerCumTa,
            s.startYear, s.schoolDiagram,
            s.createdBy, s.createdDate,
            s.lastModifiedBy, s.lastModifiedDate,
            s.deleted, s.deletedBy, s.deletedDate,
            s.description
        )
        FROM School s
        LEFT JOIN WorkingOffsite wo ON s.workingOffsite.id = wo.id
        LEFT JOIN SchoolType st ON s.schoolType.id = st.id
        WHERE 1=1 and s.deleted = 0
    """;

        StringBuilder whereClause = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (request.getSchoolTypeId() != null) {
            whereClause.append(" AND s.schoolType.id = :schoolTypeId ");
            params.put("schoolTypeId", request.getSchoolTypeId());
        }

        if (request.getName() != null && !request.getName().isBlank()) {
            whereClause.append(" AND s.name LIKE :name ");
            params.put("name", "%" + request.getName().trim() + "%");
        }

        if (request.getCode() != null && !request.getCode().isBlank()) {
            whereClause.append(" AND s.code LIKE :code ");
            params.put("code", "%" + request.getCode().trim() + "%");
        }

        if (request.getWorkingOffsiteName() != null && !request.getWorkingOffsiteName().isBlank()) {
            whereClause.append(" AND wo.name LIKE :workingOffsiteName ");
            params.put("workingOffsiteName", "%" + request.getWorkingOffsiteName().trim() + "%");
        }

        if (request.getAreaCvct() != null && !request.getAreaCvct().isBlank()) {
            whereClause.append(" AND s.areaCvct LIKE :areaCvct ");
            params.put("areaCvct", "%" + request.getAreaCvct().trim() + "%");
        }

        if (request.getPrograms() != null && !request.getPrograms().isBlank()) {
            whereClause.append(" AND s.programs LIKE :programs ");
            params.put("programs", "%" + request.getPrograms().trim() + "%");
        }

        if (request.getRhta() != null) {
            whereClause.append(" AND s.rhta = :rhta ");
            params.put("rhta", request.getRhta());
        }

        if (request.getScheduler() != null) {
            whereClause.append(" AND s.scheduler = :scheduler ");
            params.put("scheduler", request.getScheduler());
        }

        if (request.getSchedulerCumTa() != null) {
            whereClause.append(" AND s.schedulerCumTa = :schedulerCumTa ");
            params.put("schedulerCumTa", request.getSchedulerCumTa());
        }

        if (request.getStatus() != null) {
            whereClause.append(" AND s.status = :status ");
            params.put("status", request.getStatus());
        }

        String fullQuery = baseQuery + whereClause + " ORDER BY s.id DESC";

        TypedQuery<SchoolDTO> query = em.createQuery(fullQuery, SchoolDTO.class);
        params.forEach(query::setParameter);

        if(!pageable.isUnpaged()) {
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }

        String countQueryStr = "SELECT COUNT(s.id) FROM School s LEFT JOIN WorkingOffsite wo ON s.workingOffsite.id = wo.id WHERE 1=1 and s.deleted = 0 " + whereClause;
        TypedQuery<Long> countQuery = em.createQuery(countQueryStr, Long.class);
        params.forEach(countQuery::setParameter);
        Long total = countQuery.getSingleResult();
        List<SchoolDTO> content = query.getResultList();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<SchoolDTO> xuatExcel(SearchSchoolDTO request) {
        Pageable pageable = Pageable.unpaged();
        Page<SchoolDTO> list = searchAll(request,pageable);
        List<SchoolDTO> schoolDTOList = list.getContent();
        return schoolDTOList;
    }

}
