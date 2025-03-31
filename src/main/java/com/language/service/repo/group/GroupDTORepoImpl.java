package com.language.service.repo.group;

import com.language.service.rest.dto.request.searchparams.GroupSearchParams;
import com.language.service.domain.dtos.GroupDTO;
import com.language.service.domain.entities.Group;
import com.language.service.domain.entities.Group_;
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
public class GroupDTORepoImpl extends DynamicSearchRepo<GroupDTO, GroupSearchParams, Group> implements GroupDTORepo {

    @Autowired
    public GroupDTORepoImpl(EntityManager em) {
        super(em, GroupDTO.class, Group.class);
    }


    @Override
    public List<Predicate> getPredicates(CriteriaBuilder cb, Root<Group> root, GroupSearchParams params) {
        List<Predicate> predicates = new ArrayList<>();
        if (params != null) {
            String name = params.getName();
            if (name != null) {
                predicates.add(cb.like(root.get(Group_.NAME), "%" + name.trim() + "%"));
            }

            List<Long> ids = params.getIdIn();
            if (ids != null && !ids.isEmpty()) {
                CriteriaBuilder.In<Long> idIn = cb.in(root.get(Group_.ID));
                for (Long id : ids) {
                    idIn.value(id);
                }
                predicates.add(idIn);
            }

            List<Long> currentIds = params.getIdNotIn();
            if (currentIds != null && !currentIds.isEmpty()) {
                CriteriaBuilder.In<Long> idIn = cb.in(root.get(Group_.ID));
                for (Long id : currentIds) {
                    idIn.value(id);
                }
                predicates.add(cb.not(idIn));
            }
        }

        return predicates;
    }

    @Override
    public Selection[] getSelections(Root<Group> root) {
        return new Selection[]{
                root.get(Group_.ID),
                root.get(Group_.NAME),
                root.get(Group_.DESCRIPTION)
        };
    }

}
