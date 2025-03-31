package com.language.service.repo.group;

import com.language.service.domain.dtos.GroupDetailsDTO;
import com.language.service.domain.entities.Group;
import com.language.service.domain.entities.Group_;
import com.language.service.domain.entities.User_;
import com.language.service.repo.AbstractDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;

@Repository
public class GroupDetailsDTORepoImpl extends AbstractDetailsRepo<GroupDetailsDTO, Group, Long> implements GroupDetailsDTORepo {

    @Autowired
    public GroupDetailsDTORepoImpl(EntityManager em) {
        super(em, GroupDetailsDTO.class, Group.class);
    }

    @Override
    public Selection[] getDetailsSelections(Root<Group> root) {
        return new Selection[]{
                root.get(Group_.ID),
                root.get(Group_.CODE),
                root.get(Group_.NAME),
                root.get(Group_.DESCRIPTION),
                root.get(Group_.CREATED_BY),
                root.get(Group_.LAST_MODIFIED_BY),
        };
    }
}
