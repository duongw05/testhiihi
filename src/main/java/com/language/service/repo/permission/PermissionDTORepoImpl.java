package com.language.service.repo.permission;

import com.language.service.rest.dto.request.searchparams.PermissionSearchParams;
import com.language.service.domain.dtos.PermissionDTO;
import com.language.service.domain.entities.Permission;
import com.language.service.domain.entities.Permission_;
import com.language.service.repo.DynamicSearchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import java.util.ArrayList;
import java.util.List;

@Component
public class PermissionDTORepoImpl extends DynamicSearchRepo<PermissionDTO, PermissionSearchParams, Permission> implements PermissionDTORepo {

    @Autowired
    public PermissionDTORepoImpl(EntityManager em) {
        super(em, PermissionDTO.class, Permission.class);
    }

    @Override
    public List<Predicate> getPredicates(CriteriaBuilder cb, Root<Permission> root, PermissionSearchParams params) {
        List<Predicate> predicates = new ArrayList<>();
        return predicates;
    }

    @Override
    public Selection[] getSelections(Root<Permission> root) {
        return new Selection[]{
                root.get(Permission_.ID),
                root.get(Permission_.PERMISSION_GROUP),
                root.get(Permission_.CODE),
                root.get(Permission_.NAME),
        };
    }
}
