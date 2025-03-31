package com.language.service.repo.permission;

import com.language.service.domain.dtos.PermissionDetailsDTO;
import com.language.service.domain.entities.Permission;
import com.language.service.domain.entities.Permission_;
import com.language.service.repo.AbstractDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;

@Repository
public class PermissionDetailsDTORepoImpl extends AbstractDetailsRepo<PermissionDetailsDTO, Permission, Long> implements PermissionDetailsDTORepo {

    @Autowired
    public PermissionDetailsDTORepoImpl(EntityManager em) {
        super(em, PermissionDetailsDTO.class, Permission.class);
    }

    @Override
    public Selection[] getDetailsSelections(Root<Permission> root) {
        return new Selection[]{
                root.get(Permission_.ID),
                root.get(Permission_.CODE)
        };
    }
}
