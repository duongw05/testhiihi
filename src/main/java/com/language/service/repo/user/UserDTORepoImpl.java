package com.language.service.repo.user;

import com.language.service.rest.dto.request.searchparams.UserSearchParams;
import com.language.service.domain.dtos.UserDTO;
import com.language.service.domain.entities.User;
import com.language.service.domain.entities.User_;
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
public class UserDTORepoImpl extends DynamicSearchRepo<UserDTO, UserSearchParams, User> implements UserDTORepo {

    @Autowired
    public UserDTORepoImpl(EntityManager em) {
        super(em, UserDTO.class, User.class);
    }

    @Override
    public List<Predicate> getPredicates(CriteriaBuilder cb, Root<User> root, UserSearchParams params) {
        List<Predicate> predicates = new ArrayList<>();
        if (params != null) {
            String username = params.getUsername();
            if (username != null && username.trim().length() > 0) {
                predicates.add(cb.equal(root.get(User_.username), username));
            }

        }
        return predicates;
    }

    @Override
    public Selection[] getSelections(Root<User> root) {
        return new Selection[]{
                root.get(User_.ID),
                root.get(User_.USERNAME),
                root.get(User_.EMAIL),
                root.get(User_.PHONE),
                root.get(User_.FULL_NAME),
                root.get(User_.LOCALE),
                root.get(User_.TIMEZONE),
                root.get(User_.ENABLED),
        };
    }

}
