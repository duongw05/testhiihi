package com.language.service.repo.user;

import com.language.service.domain.dtos.UserDetailsDTO;
import com.language.service.domain.entities.UploadedFile_;
import com.language.service.domain.entities.User;
import com.language.service.domain.entities.User_;
import com.language.service.repo.AbstractDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;

@Repository
public class UserDetailsDTORepoImpl extends AbstractDetailsRepo<UserDetailsDTO, User, Long> implements UserDetailsDTORepo {

    @Autowired
    public UserDetailsDTORepoImpl(EntityManager em) {
        super(em, UserDetailsDTO.class, User.class);
    }

    @Override
    public Selection[] getDetailsSelections(Root<User> root) {
        return new Selection[]{
                root.get(User_.ID),
                root.get(User_.USERNAME),
                root.get(User_.EMAIL),
                root.get(User_.PHONE),
                root.get(User_.FULL_NAME),
                root.get(User_.LOCALE),
                root.get(User_.TIMEZONE),
                root.get(User_.ENABLED),
                root.join(User_.CREATED_BY, JoinType.LEFT).get(User_.ID),
                root.join(User_.CREATED_BY, JoinType.LEFT).get(User_.FULL_NAME),
                root.get(User_.CREATED_DATE),
                root.join(User_.LAST_MODIFIED_BY, JoinType.LEFT).get(User_.ID),
                root.join(User_.LAST_MODIFIED_BY, JoinType.LEFT).get(User_.FULL_NAME),
                root.get(User_.LAST_MODIFIED_DATE),
        };
    }
}
